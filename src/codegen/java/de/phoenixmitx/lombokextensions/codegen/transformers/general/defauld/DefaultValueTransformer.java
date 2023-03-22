package de.phoenixmitx.lombokextensions.codegen.transformers.general.defauld;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import de.phoenixmitx.lombokextensions.codegen.transformers.CodegenAnnotationTransformer;
import de.phoenixmitx.lombokextensions.codegen.utils.ArrayUtils;
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
import javassist.bytecode.SignatureAttribute.Type;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({ OrdinalParameter.class })
public class DefaultValueTransformer extends CodegenAnnotationTransformer<DefaultValue> {

	public DefaultValueTransformer() {
		super(DefaultValue.class);
	}



	@Override
	@SuppressWarnings("unchecked")
	protected void transformMethodWithParameterAnnotations(CtClass ctClass, ClassPool classPool, CtMethod method, Optional<DefaultValue>[] allAnnotations) throws IOException, CannotCompileException, NotFoundException, BadBytecode {
		Optional<String>[] allDefaultValues = Arrays.stream(allAnnotations)
		.map(annotation -> annotation.map(DefaultValue::value))
		.toArray(Optional[]::new);
		
		// TODO parameter names
		for (Optional<String>[] defaultValues : VariationUtils.getAllPosibleEmptyVariations(allDefaultValues)) {
			CtClass[] parameterTypeClasses = ArrayUtils.zipFilter(defaultValues, method.getParameterTypes(), (defaultValue, type) -> !defaultValue.isPresent(), (defaultValue, type) -> type, CtClass[]::new);
			MethodSignature methodSignature = SignatureAttribute.toMethodSignature(method.getGenericSignature());
			Type[] parameterTypeGenerics = ArrayUtils.zipFilter(defaultValues, methodSignature.getParameterTypes(), (defaultValue, type) -> !defaultValue.isPresent(), (defaultValue, type) -> type, Type[]::new);
			MethodSignature newMethodSignature = new MethodSignature(methodSignature.getTypeParameters(), parameterTypeGenerics, methodSignature.getReturnType(), methodSignature.getExceptionTypes());
			CtMethod newMethod = new CtMethod(method.getReturnType(), method.getName(), parameterTypeClasses, method.getDeclaringClass());
			newMethod.setGenericSignature(newMethodSignature.encode());
			newMethod.setModifiers(method.getModifiers());
			int[] parameterIndex = {0};
			newMethod.setBodyWithOrdinalParameters(
					Arrays.stream(defaultValues)
					.map(value -> 
						value.isPresent()
						? value.get()
						: "$" + parameterIndex[0]++
					)
					.collect(Collectors.joining(
						",",
						"return " + method.getName() + "(",
						");"
					))
				);
			ctClass.addMethod(newMethod);
		}
	}
}
