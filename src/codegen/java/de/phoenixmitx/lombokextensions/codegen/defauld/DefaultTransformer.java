package de.phoenixmitx.lombokextensions.codegen.defauld;

import java.io.IOException;

import de.phoenixmitx.lombokextensions.codegen.transformer.annotation.CodegenAnnotationTransformer;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;

public class DefaultTransformer extends CodegenAnnotationTransformer {

	protected DefaultTransformer() {
		super(DefaultBoolean.class);
	}

	@Override
	protected boolean transformType(CtClass ctClass, Annotation ctAnnotation, AnnotationsAttribute annotationsAttribute) throws IOException, CannotCompileException, NotFoundException {
		return false;
	}
}
