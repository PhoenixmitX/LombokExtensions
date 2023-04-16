package de.phoenixmitx.lombokextensions.codegen.transformers.general.primitive;

import java.io.IOException;
import java.util.Optional;

import de.phoenixmitx.lombokextensions.codegen.transformers.CodegenAnnotationTransformer;
import de.phoenixmitx.lombokextensions.codegen.utils.OrdinalParameter;
import de.phoenixmitx.lombokextensions.codegen.utils.VariationUtils;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.MethodSignature;
import javassist.bytecode.SignatureAttribute.TypeParameter;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({ OrdinalParameter.class })
public class PrimitiveTransformer extends CodegenAnnotationTransformer<Primitive> {

	public PrimitiveTransformer() {
		super(Primitive.class);
	}
	
	@Override
	protected void transformMethodWithTypeParameterAnnotations(CtClass ctClass, ClassPool classPool, CtMethod method, Optional<Primitive>[] allAnnotations) throws IOException, CannotCompileException, NotFoundException, BadBytecode {
		MethodSignature originalMethodSignature = SignatureAttribute.toMethodSignature(method.getGenericSignature());
		TypeParameter[] originalParameterTypes = originalMethodSignature.getTypeParameters();
		for (Optional<Primitive>[] annotations : VariationUtils.getAllPosibleEmptyVariations(allAnnotations)) {
			
		}
		// copy parameter annotations to allow cross support with @DefaultValue
	}
}
