package de.phoenixmitx.lombokextensions.codegen.transformers;

import javassist.ClassPool;
import javassist.CtClass;

public interface CodegenTransformer {
  public abstract boolean transform(CtClass ctClass, ClassPool classPool) throws Exception;
}
