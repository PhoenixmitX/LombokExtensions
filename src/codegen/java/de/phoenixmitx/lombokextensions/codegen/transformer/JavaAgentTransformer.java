package de.phoenixmitx.lombokextensions.codegen.transformer;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;

public class JavaAgentTransformer implements ClassFileTransformer {

  private final ClassPool classPool = ClassPool.getDefault();

  private final CodegenTransformer[] transformers;

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
				System.out.println("Transformed class " + className);
        try (FileOutputStream fos = new FileOutputStream("build/classes/java/main/" + className + ".class"); DataOutputStream dos = new DataOutputStream(fos)) {
          ctClass.getClassFile().write(dos);
					System.out.println("Wrote class " + className);
          return ctClass.toBytecode();
        } finally {
					ctClass.detach();
				}
      }
      return classfileBuffer;
    } catch (Exception e) {
      IllegalClassFormatException icfx = new IllegalClassFormatException("Error transforming class " + className);
      icfx.initCause(e);
      // Print the stacktrace to the console - the javaagent does not print the stacktrace
      e.printStackTrace();
      throw icfx;
    }
  }
}
