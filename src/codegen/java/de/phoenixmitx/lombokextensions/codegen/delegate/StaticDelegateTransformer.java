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
  private final ClassPool pool = ClassPool.getDefault();

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
    if (!className.startsWith("de/phoenixmitx/lombokextensions/")) {
      return classfileBuffer;
    }
    try {
      // Get the class using javassist
      CtClass clazz = pool.get(className.replace("/", "."));

      // Check if the class has the StaticDelegate annotation
      AnnotationsAttribute attr = (AnnotationsAttribute) clazz.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
      if (attr == null) {
        return classfileBuffer;
      }
      Annotation annotation = attr.getAnnotation(StaticDelegate.class.getName());
      if (annotation != null) {
        // Get the classes to delegate to from the annotation
        MemberValue value = annotation.getMemberValue("value");
        ArrayMemberValue classes = (ArrayMemberValue) value;
        MemberValue[] classValues = classes.getValue();

        // Delegate all static methods from the delegated classes to the annotated class
        for (MemberValue classValue : classValues) {
          System.out.println("Delegating static methods from " + classValue + " to " + className);
          ClassMemberValue delegateClass = (ClassMemberValue) classValue;
          String delegateClassName = delegateClass.getValue();
          CtClass delegate = pool.get(delegateClassName);

          for (CtMethod method : delegate.getDeclaredMethods()) {

            // Check if the method is static
            if (Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers())) {

              CtMethod newMethod = new CtMethod(method.getReturnType(), method.getName(), method.getParameterTypes(), clazz);
              newMethod.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
              if (method.getGenericSignature() != null) newMethod.setGenericSignature(method.getGenericSignature());
              newMethod.setBody("return " + delegateClassName + "." + method.getName() + "($$);");
              clazz.addMethod(newMethod);
            }
          }
        }

        // Remove the StaticDelegate annotation
        attr.removeAnnotation("de.phoenixmitx.lombokextensions.codegen.delegate.StaticDelegate");

        // Return the modified bytecode
        clazz.getClassFile().write(new DataOutputStream(new FileOutputStream("build/classes/java/main/" + className + ".class")));
        return clazz.toBytecode();
      }
    } catch (Exception e) {
      IllegalClassFormatException ifx = new IllegalClassFormatException("Error transforming class " + className);
      ifx.initCause(e);
      // Print the stacktrace to the console - the javaagent does not print the stacktrace
      e.printStackTrace();
      throw ifx;
    }
    return classfileBuffer;
  }
}