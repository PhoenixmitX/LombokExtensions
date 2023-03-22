package de.phoenixmitx.lombokextensions.codegen.transformers.general.staticdelegate;

import de.phoenixmitx.lombokextensions.codegen.transformers.CodegenAnnotationTransformer;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

public class StaticDelegateTransformer extends CodegenAnnotationTransformer<StaticDelegate> {

  public StaticDelegateTransformer() {
    super(StaticDelegate.class);
  }

  @Override
  public void transformType(CtClass newClass, ClassPool classPool, StaticDelegate annotation) throws CannotCompileException, NotFoundException {

    for (Class<?> oldJavaClass : annotation.value()) {
			String oldClassName = oldJavaClass.getName();
      CtClass oldClass = classPool.get(oldClassName);

      for (CtMethod oldMethod : oldClass.getDeclaredMethods()) {
        if ((~oldMethod.getModifiers() & (Modifier.PUBLIC | Modifier.STATIC)) == 0) {

					// TODO add parameter names
          CtMethod newMethod = new CtMethod(oldMethod.getReturnType(), oldMethod.getName(), oldMethod.getParameterTypes(), newClass);
          newMethod.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
          if (oldMethod.getGenericSignature() != null) newMethod.setGenericSignature(oldMethod.getGenericSignature());
          newMethod.setBody("return " + oldClassName + "." + oldMethod.getName() + "($$);");

          newClass.addMethod(newMethod);
        }
      }
    }
  }
}
