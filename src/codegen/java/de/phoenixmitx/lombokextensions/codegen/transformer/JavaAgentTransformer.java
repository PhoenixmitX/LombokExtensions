package de.phoenixmitx.lombokextensions.codegen.transformer;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
public class JavaAgentTransformer implements ClassFileTransformer {

  private final ClassPool classPool = ClassPool.getDefault();

  private final CodegenTransformer[] transformers;

	@Getter
	private IllegalClassFormatException lastException;

  public JavaAgentTransformer(CodegenTransformer... transformers) {
    this.transformers = transformers;
  }
  
  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> nullClass, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
    if (!className.startsWith("de/phoenixmitx/lombokextensions/")) {
      return classfileBuffer;
    }
    try {
      CtClass ctClass = classPool.get(className.replace("/", "."));

      boolean modified = false;

      for (CodegenTransformer transformer : transformers) {
        modified |= transformer.transform(ctClass);
      }
      
      if (modified) {
				log.info("CodegenAgent: Transformed class " + className);
        try (FileOutputStream fos = new FileOutputStream("build/classes/java/main/" + className + ".class"); DataOutputStream dos = new DataOutputStream(fos)) {
          ctClass.getClassFile().write(dos);
          return ctClass.toBytecode();
        } finally {
					ctClass.detach();
				}
      }
      return classfileBuffer;
    } catch (Exception e) {
      IllegalClassFormatException icfx = lastException = new IllegalClassFormatException("Error transforming class " + className);
      icfx.initCause(e);
      throw icfx;
    }
  }
}
