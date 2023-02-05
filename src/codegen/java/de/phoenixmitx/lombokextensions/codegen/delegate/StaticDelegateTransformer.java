package de.phoenixmitx.lombokextensions.codegen.delegate;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

public class StaticDelegateTransformer implements ClassFileTransformer {
  private final ClassPool pool = ClassPool.getDefault();

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
    if (className.startsWith("de/phoenixmitx")) {
      System.out.println("Found own class " + className);
    }
    try {
      // Get the class using javassist
      CtClass clazz = pool.get(className.replace("/", "."));

      // Check if the class has the StaticDelegate annotation
      AnnotationsAttribute attr = (AnnotationsAttribute) clazz.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
      if (attr == null) {
        return classfileBuffer;
      }
      System.out.println("Annotation found on " + className);
      Annotation annotation = attr.getAnnotation(StaticDelegate.class.getName());
      if (annotation != null) {
        System.out.println("StaticDelegate annotation found on " + className);
        // Get the classes to delegate to from the annotation
        MemberValue value = annotation.getMemberValue("value");
        ArrayMemberValue classes = (ArrayMemberValue) value;
        MemberValue[] classValues = classes.getValue();

        // Delegate all static methods from the delegated classes to the annotated class
        for (MemberValue classValue : classValues) {
          StringMemberValue delegateClass = (StringMemberValue) classValue;
          String delegateClassName = delegateClass.getValue();
          CtClass delegate = pool.get(delegateClassName);

          for (CtMethod method : delegate.getDeclaredMethods()) {
            if (method.hasAnnotation(StaticDelegate.class)) {
              continue;
            }

            // Check if the method is static
            if (Modifier.isStatic(method.getModifiers())) {
              // Create a new static method in the annotated class
              String methodName = method.getName();
              String methodSignature = method.getSignature();
              StringBuilder sb = new StringBuilder();
              sb.append("public static ").append(methodSignature).append(" { ")
                .append("return ").append(delegateClassName).append(".").append(methodName).append("($$);")
                .append(" }");
              CtMethod newMethod = CtMethod.make(sb.toString(), clazz);
              clazz.addMethod(newMethod);
            }
          }
        }

        // Return the modified bytecode
        return clazz.toBytecode();
      }
    } catch (NotFoundException | IOException | CannotCompileException e) {
      IllegalClassFormatException ifx = new IllegalClassFormatException(e.getMessage());
      ifx.initCause(e);
      throw ifx;
    }
    return classfileBuffer;
  }
}