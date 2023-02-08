package de.phoenixmitx.lombokextensions.codegen.transformer;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;

public abstract class CodegenAnnotationTransformer extends CodegenTransformer {

  protected final String fullyQualifiedAnnotationName;

  protected CodegenAnnotationTransformer(Class<?> annotationClass) {
    this.fullyQualifiedAnnotationName = annotationClass.getName();
  }

  protected CodegenAnnotationTransformer(String fullyQualifiedAnnotationName) {
    this.fullyQualifiedAnnotationName = fullyQualifiedAnnotationName;
  }

  @Override
  public final boolean transform(CtClass ctClass, byte[] classfileBuffer) throws IOException, CannotCompileException, NotFoundException {
    AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) ctClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
    if (annotationsAttribute == null) {
      return false;
    }
    Annotation ctAnnotation = annotationsAttribute.getAnnotation(fullyQualifiedAnnotationName);
    if (ctAnnotation == null) {
      return false;
    }
    return transform(ctClass, ctAnnotation, annotationsAttribute, classfileBuffer);
  }

  protected abstract boolean transform(CtClass ctClass, Annotation ctAnnotation, AnnotationsAttribute annotationsAttribute, byte[] classfileBuffer) throws IOException, CannotCompileException, NotFoundException;
}
