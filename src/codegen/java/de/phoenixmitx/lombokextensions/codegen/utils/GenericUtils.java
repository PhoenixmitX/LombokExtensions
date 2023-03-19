package de.phoenixmitx.lombokextensions.codegen.utils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute.ArrayType;
import javassist.bytecode.SignatureAttribute.ClassType;
import javassist.bytecode.SignatureAttribute.MethodSignature;
import javassist.bytecode.SignatureAttribute.ObjectType;
import javassist.bytecode.SignatureAttribute.TypeParameter;
import javassist.bytecode.SignatureAttribute.TypeVariable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GenericUtils {
	
	public CtClass getCtClass(ClassPool classPool, MethodSignature sig, ObjectType type) throws NotFoundException {
		if (type instanceof ClassType) {
			return classPool.get(((ClassType) type).getName());
		} else if (type instanceof TypeVariable) {
			TypeVariable typeVariable = (TypeVariable) type;
			TypeParameter[] typeArguments = sig.getTypeParameters();
			for (TypeParameter typeParameter : typeArguments) {
				if (typeParameter.getName().equals(typeVariable.getName())) {
					ObjectType bound = typeParameter.getClassBound();
					return bound == null ? classPool.get("java.lang.Object") : getCtClass(classPool, sig, bound);
				}
			}
			throw new NotFoundException("Type variable not found on method signature: " + typeVariable.getName());
		} else if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return classPool.get(arrayType.encode());
		} else {
			throw new NotFoundException("Unknown type: " + type.getClass().getName());
		}
	}
}
