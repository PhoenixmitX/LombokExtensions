package de.phoenixmitx.lombokextensions.codegen.singleuse.collectors;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.phoenixmitx.lombokextensions.codegen.transformer.CodegenTransformer;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ArrayType;
import javassist.bytecode.SignatureAttribute.ClassType;
import javassist.bytecode.SignatureAttribute.MethodSignature;
import javassist.bytecode.SignatureAttribute.ObjectType;
import javassist.bytecode.SignatureAttribute.Type;
import javassist.bytecode.SignatureAttribute.TypeArgument;
import javassist.bytecode.SignatureAttribute.TypeParameter;
import javassist.bytecode.SignatureAttribute.TypeVariable;
import lombok.SneakyThrows;

public class CollectorsTransformer extends CodegenTransformer {

	@Override
	@SneakyThrows({ BadBytecode.class })
	public boolean transform(CtClass ctClass) throws IOException, CannotCompileException, NotFoundException {
		if (!ctClass.getName().equals("de.phoenixmitx.lombokextensions.CollectorsExtension")) {
			return false;
		}

		CtClass collectorsClass = classPool.get("java.util.stream.Collectors");
		CtClass collectorClass = classPool.get("java.util.stream.Collector");
		CtClass streamClass = classPool.get("java.util.stream.Stream");

		for (CtMethod collectorsMethod : collectorsClass.getMethods()) {
			CtClass collectorReturnType = collectorsMethod.getReturnType();
			if (!collectorReturnType.equals(collectorClass) || (~collectorsMethod.getModifiers() | (Modifier.PUBLIC | Modifier.STATIC)) == 0) {
				continue;
			}

			// TODO why are these methods not working?
			// are all of these methods declared at least twice?
			// other methods are declared twice, but they work
			// switch (collectorsMethod.getName()) {
			// 	case "minBy":
			// 	case "joining":
			// 	case "reducing":
			// 	case "counting":
			// 	case "maxBy":
			// 	case "summarizingInt":
			// 	case "averagingLong":
			// 		continue;
			// }

			// if (collectorsMethod.getName().contains("Int") || collectorsMethod.getName().contains("Long") || collectorsMethod.getName().contains("Double")) {
			// 	continue;
			// }

			MethodSignature signature = SignatureAttribute.toMethodSignature(collectorsMethod.getGenericSignature());
			ClassType collector = (ClassType) signature.getReturnType();
			TypeArgument[] typeArguments = collector.getTypeArguments();
			TypeArgument streamTypeArgument = typeArguments[0];
			ObjectType returnType = typeArguments[2].getType();
			CtClass returnTypeClass = getCtClass(signature, returnType);
			
			ClassType streamType = new ClassType("java.util.stream.Stream", new TypeArgument[] {streamTypeArgument});
			
			MethodSignature newSignature = new MethodSignature(signature.getTypeParameters(), merge(new Type[] {streamType}, signature.getParameterTypes()), returnType, signature.getExceptionTypes());
			
			CtClass[] parameterTypes = collectorsMethod.getParameterTypes();
			// TODO add parameter names
			CtMethod ctMethod = new CtMethod(returnTypeClass, collectorsMethod.getName(), merge(new CtClass[] {streamClass}, parameterTypes), ctClass);
			ctMethod.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
			ctMethod.setGenericSignature(newSignature.encode());
			ctMethod.setBody("return (" + returnTypeClass.getName() + ") $1.collect(java.util.stream.Collectors." + collectorsMethod.getName() + "(" + IntStream.rangeClosed(2, parameterTypes.length+1).mapToObj(i -> "$"+i).collect(Collectors.joining(",")) + "));");
			ctClass.addMethod(ctMethod);

			System.out.println();
			System.out.println(collectorsMethod.getName());
			System.out.println(signature.encode());
			System.out.println(newSignature.encode());
			System.out.println(ctMethod.getGenericSignature());
			System.out.println(ctMethod.getLongName());
		}

		return true;
	}

	public <T> T[] merge(T[] arr, T[] c) {
		T[] newArr = Arrays.copyOf(arr, arr.length + c.length);
		System.arraycopy(c, 0, newArr, arr.length, c.length);
		return newArr;
	}

	public CtClass getCtClass(MethodSignature sig, ObjectType type) throws NotFoundException {
		// May be ArrayType, ClassType or TypeVariable
		if (type instanceof ClassType) {
			return classPool.get(((ClassType) type).getName());
		} else if (type instanceof TypeVariable) {
			TypeVariable typeVariable = (TypeVariable) type;
			TypeParameter[] typeArguments = sig.getTypeParameters();
			for (TypeParameter typeParameter : typeArguments) {
				if (typeParameter.getName().equals(typeVariable.getName())) {
					ObjectType bound = typeParameter.getClassBound();
					return bound == null ? classPool.get("java.lang.Object") : getCtClass(sig, bound);
				}
			}
			throw new NotFoundException("Type variable not found: " + typeVariable.getName());
		} else if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return classPool.get(arrayType.encode());
		} else {
			throw new NotFoundException("Unknown type: " + type.getClass().getName());
		}
	}
	
	public boolean isReturnTypeGeneric(MethodSignature sig) {
		return sig.getReturnType() instanceof ClassType;
	}
}
