package de.phoenixmitx.lombokextensions.codegen.transformer;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;

public abstract class CodegenTransformer {
  protected final ClassPool classPool = ClassPool.getDefault();
  public abstract boolean transform(CtClass ctClass) throws IOException, CannotCompileException, NotFoundException, BadBytecode;
}
