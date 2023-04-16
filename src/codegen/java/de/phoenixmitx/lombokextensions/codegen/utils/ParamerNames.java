package de.phoenixmitx.lombokextensions.codegen.utils;

import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.ConstPool;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParamerNames {
	
	public String[] getPapameterNames(CtMethod method) throws NotFoundException {
		MethodInfo methodInfo = method.getMethodInfo();
  	LocalVariableAttribute table = (LocalVariableAttribute) methodInfo.getCodeAttribute().getAttribute(LocalVariableAttribute.tag);
		int parameterCount = method.getParameterTypes().length;
		int offset = ((method.getModifiers() & Modifier.STATIC) != 0) ? 0 : 1;
		String[] parameterNames = new String[parameterCount];
		for (int i = 0; i < parameterCount; i++) {
			int nameIndex = table.nameIndex(i + offset);
			parameterNames[i] = methodInfo.getConstPool().getUtf8Info(nameIndex);
		}
		return parameterNames;
	}

	public void setPapameterNames(CtMethod method, String[] parameterNames) throws NotFoundException {
		if (parameterNames.length != method.getParameterTypes().length) {
			return;
		}
		MethodInfo methodInfo = method.getMethodInfo();
		LocalVariableAttribute table = (LocalVariableAttribute) methodInfo.getCodeAttribute().getAttribute(LocalVariableAttribute.tag);
		int offset = ((method.getModifiers() & Modifier.STATIC) != 0) ? 0 : 1;
		for (int i = 0; i < parameterNames.length; i++) {
			int nameIndex = methodInfo.getConstPool().addUtf8Info(parameterNames[i]);
			// addEntry Parameters:
			// 	startPc start_pc
			// 	length length
			// 	nameIndex name_index
			// 	descriptorIndex descriptor_index
			// 	index index
			// TODO check if this works
			table.addEntry(i + offset, 1, nameIndex, 0, 0);
		}
	}
}
