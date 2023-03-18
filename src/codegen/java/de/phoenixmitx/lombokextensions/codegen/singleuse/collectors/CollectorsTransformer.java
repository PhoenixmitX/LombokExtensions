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

public class CollectorsTransformer extends CodegenTransformer {

	@Override
	public boolean transform(CtClass ctClass, byte[] classfileBuffer) throws IOException, CannotCompileException, NotFoundException {
		if (!ctClass.getName().equals("de.phoenixmitx.lombokextensions.CollectorsExtension")) {
			return false;
		}

		CtClass collectorsClass = classPool.get("java.util.stream.Collectors");
		CtClass collectorClass = classPool.get("java.util.stream.Collector");
		CtClass streamClass = classPool.get("java.util.stream.Stream");
		CtClass objectClass = classPool.get("java.lang.Object");

		for (CtMethod collectorsMethod : collectorsClass.getMethods()) {
			CtClass returnType = collectorsMethod.getReturnType();
			if (!returnType.equals(collectorClass) || collectorsMethod.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC)) {
				continue;
			}

			CtClass[] parameterTypes = collectorsMethod.getParameterTypes();
			// TODO add generics
			// TODO add return type
			// TODO add parameter names
			CtMethod ctMethod = new CtMethod(objectClass, collectorsMethod.getName(), merge(new CtClass[] {streamClass}, parameterTypes), ctClass);
			ctMethod.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
			ctMethod.setBody("return $1.collect(java.util.stream.Collectors." + collectorsMethod.getName() + "(" + IntStream.rangeClosed(2, parameterTypes.length+1).mapToObj(i -> "$"+i).collect(Collectors.joining(",")) + "));");
			ctClass.addMethod(ctMethod);
		}

		return true;
	}

	public <T> T[] merge(T[] arr, T[] c) {
		T[] newArr = Arrays.copyOf(arr, arr.length + c.length);
		System.arraycopy(c, 0, newArr, arr.length, c.length);
		return newArr;
	}
}
