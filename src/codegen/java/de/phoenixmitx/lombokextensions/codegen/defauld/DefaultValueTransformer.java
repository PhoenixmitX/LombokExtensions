package de.phoenixmitx.lombokextensions.codegen.defauld;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import de.phoenixmitx.lombokextensions.codegen.transformer.annotation.CodegenAnnotationTransformer;
import de.phoenixmitx.lombokextensions.codegen.utils.ArrayUtils;
import de.phoenixmitx.lombokextensions.codegen.utils.OrdinalParameter;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.MethodSignature;
import javassist.bytecode.SignatureAttribute.Type;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({ OrdinalParameter.class })
public class DefaultValueTransformer extends CodegenAnnotationTransformer {

	public DefaultValueTransformer() {
		super(DefaultValue.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void transformMethodWithParameterAnnotations(CtClass ctClass, CtMethod method, Annotation[] ctAnnotation, ParameterAnnotationsAttribute annotationsAttribute) throws IOException, CannotCompileException, NotFoundException, BadBytecode {
		Optional<String>[] allDefaultValues = Arrays.stream(ctAnnotation)
				.map(annotation -> annotation == null ? Optional.empty() : Optional.of(((StringMemberValue) annotation.getMemberValue("value")).getValue()))
				.toArray(Optional[]::new);
		
		// TODO parameter names
		for (Optional<String>[] defaultValues : getAllPosibleEmptyVariations(allDefaultValues)) {
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

	@SuppressWarnings("unchecked")
	private Optional<String>[][] getAllPosibleEmptyVariations(Optional<String>[] defaultValues) {
		int presentValues = 0;
		boolean[] presentAt = new boolean[defaultValues.length];
		for (int i = 0; i < defaultValues.length; i++) {
			if (defaultValues[i].isPresent()) {
				presentValues++;
				presentAt[i] = true;
			}
		}
		int posibleVariations = (int) Math.pow(2, presentValues) - 1; // -1 because we don't want all default values to be empty because that method does already exist
		Optional<String>[][] result = new Optional[posibleVariations][defaultValues.length];
		for (int i = 0; i < posibleVariations; i++) {
			int j = 0;
			for (int k = 0; k < defaultValues.length; k++) {
				if (presentAt[k]) {
					result[i][k] = (i & (1 << j++)) != 0 ? Optional.empty() : defaultValues[k];
				} else {
					result[i][k] = Optional.empty();
				}
			}
		}
		return result;
	}
}
