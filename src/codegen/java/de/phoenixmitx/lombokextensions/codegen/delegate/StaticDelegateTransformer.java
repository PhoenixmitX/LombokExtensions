package de.phoenixmitx.lombokextensions.codegen.delegate;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.MemberValue;

public class StaticDelegateTransformer implements ClassFileTransformer {

  private final ClassPool classPool = ClassPool.getDefault();

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
    if (!className.startsWith("de/phoenixmitx/lombokextensions/")) {
      return classfileBuffer;
    }
    try {
      // Get the class using javassist
      CtClass newClass = classPool.get(className.replace("/", "."));

      // Check if the class has the StaticDelegate annotation
      AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) newClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
      if (annotationsAttribute == null) {
        return classfileBuffer;
      }
      Annotation annotation = annotationsAttribute.getAnnotation(StaticDelegate.class.getName());
      if (annotation == null) {
        return classfileBuffer;
      }
      // Get the classes to delegate to from the annotation
      MemberValue[] classValues = ((ArrayMemberValue) annotation.getMemberValue("value")).getValue();

      // Delegate all static methods from the delegated classes to the annotated class
      for (MemberValue classValue : classValues) {
        System.out.println("Delegating static methods from " + classValue + " to " + className);
        String oldClassName = ((ClassMemberValue) classValue).getValue();
        CtClass oldClass = classPool.get(oldClassName);

        for (CtMethod oldMethod : oldClass.getDeclaredMethods()) {

          // Check if the method is static
          if (Modifier.isStatic(oldMethod.getModifiers()) && Modifier.isPublic(oldMethod.getModifiers())) {

            CtMethod newMethod = new CtMethod(oldMethod.getReturnType(), oldMethod.getName(), oldMethod.getParameterTypes(), newClass);
            newMethod.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
            if (oldMethod.getGenericSignature() != null) newMethod.setGenericSignature(oldMethod.getGenericSignature());
            newMethod.setBody("return " + oldClassName + "." + oldMethod.getName() + "($$);");

            newClass.addMethod(newMethod);
          }
        }
      }

      // Remove the StaticDelegate annotation
      annotationsAttribute.removeAnnotation("de.phoenixmitx.lombokextensions.codegen.delegate.StaticDelegate");

      // Return the modified bytecode
      newClass.getClassFile().write(new DataOutputStream(new FileOutputStream("build/classes/java/main/" + className + ".class")));
      return newClass.toBytecode();
    } catch (Exception e) {
      IllegalClassFormatException ifx = new IllegalClassFormatException("Error transforming class " + className);
      ifx.initCause(e);
      // Print the stacktrace to the console - the javaagent does not print the stacktrace
      e.printStackTrace();
      throw ifx;
    }
  }
}