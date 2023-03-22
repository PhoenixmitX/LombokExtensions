package de.phoenixmitx.lombokextensions.codegen.transformers.general.primitive;

import java.io.IOException;
import java.util.Optional;

import de.phoenixmitx.lombokextensions.codegen.transformers.CodegenAnnotationTransformer;
import de.phoenixmitx.lombokextensions.codegen.utils.VariationUtils;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;

public class PrimitiveTransformer extends CodegenAnnotationTransformer<Primitive> {

	public PrimitiveTransformer() {
		super(Primitive.class);
	}
	
	@Override
	protected void transformMethodWithTypeParameterAnnotations(CtClass ctClass, ClassPool classPool, CtMethod method, Optional<Primitive>[] allAnnotations) throws IOException, CannotCompileException, NotFoundException, BadBytecode {
		for (Optional<Primitive>[] annotations : VariationUtils.getAllPosibleEmptyVariations(allAnnotations)) {

		}
	}
}
