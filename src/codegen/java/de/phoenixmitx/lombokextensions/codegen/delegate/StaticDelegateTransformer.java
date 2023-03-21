package de.phoenixmitx.lombokextensions.codegen.delegate;

import de.phoenixmitx.lombokextensions.codegen.transformer.annotation.CodegenAnnotationTransformer;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.MemberValue;

public class StaticDelegateTransformer extends CodegenAnnotationTransformer {

  public StaticDelegateTransformer() {
    super(StaticDelegate.class);
  }

  @Override
  public void transformType(CtClass newClass, Annotation ctAnnotation, AnnotationsAttribute annotationsAttribute) throws CannotCompileException, NotFoundException {
    // Get the classes to delegate to from the annotation
    MemberValue[] classValues = ((ArrayMemberValue) ctAnnotation.getMemberValue("value")).getValue();

    // Delegate all static methods from the delegated classes to the annotated class
    for (MemberValue classValue : classValues) {
      String oldClassName = ((ClassMemberValue) classValue).getValue();
      CtClass oldClass = classPool.get(oldClassName);

      for (CtMethod oldMethod : oldClass.getDeclaredMethods()) {

        // Check if the method is static
        if (Modifier.isStatic(oldMethod.getModifiers()) && Modifier.isPublic(oldMethod.getModifiers())) {

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