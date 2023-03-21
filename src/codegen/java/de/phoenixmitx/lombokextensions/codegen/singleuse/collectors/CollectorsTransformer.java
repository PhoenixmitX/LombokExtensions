package de.phoenixmitx.lombokextensions.codegen.singleuse.collectors;

import java.io.IOException;

import de.phoenixmitx.lombokextensions.codegen.transformer.CodegenTransformer;
import de.phoenixmitx.lombokextensions.codegen.utils.ArrayUtils;
import de.phoenixmitx.lombokextensions.codegen.utils.GenericUtils;
import de.phoenixmitx.lombokextensions.codegen.utils.OrdinalParameter;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ClassType;
import javassist.bytecode.SignatureAttribute.MethodSignature;
import javassist.bytecode.SignatureAttribute.ObjectType;
import javassist.bytecode.SignatureAttribute.Type;
import javassist.bytecode.SignatureAttribute.TypeArgument;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({ OrdinalParameter.class })
public class CollectorsTransformer extends CodegenTransformer {

	@Override
	public boolean transform(CtClass ctClass) throws IOException, CannotCompileException, NotFoundException, BadBytecode {
		if (!ctClass.getName().equals("de.phoenixmitx.lombokextensions.CollectorsExtension")) {
			return false;
		}

		CtClass collectorsClass = classPool.get("java.util.stream.Collectors");
		CtClass collectorClass = classPool.get("java.util.stream.Collector");

		for (CtMethod collectorsMethod : collectorsClass.getMethods()) {
			CtClass collectorReturnType = collectorsMethod.getReturnType();
			if (!collectorReturnType.equals(collectorClass) || (~collectorsMethod.getModifiers() | (Modifier.PUBLIC | Modifier.STATIC)) == 0) {
				continue;
			}
			ctClass.addMethod(createExtensionMethod(collectorsMethod, ctClass));
		}

		return true;
	}

	private CtMethod createExtensionMethod(CtMethod collectorsMethod, CtClass extensionClass) throws NotFoundException, CannotCompileException, BadBytecode {
		MethodSignature collectorsMethodSignature = SignatureAttribute.toMethodSignature(collectorsMethod.getGenericSignature());

		TypeArgument[] collectorTypeArguments = ((ClassType) collectorsMethodSignature.getReturnType()).getTypeArguments();
		TypeArgument streamTypeArgument = collectorTypeArguments[0];

		ClassType streamType = new ClassType("java.util.stream.Stream", new TypeArgument[] {streamTypeArgument});

		ObjectType newReturnType = collectorTypeArguments[2].getType();
		CtClass newReturnTypeClass = GenericUtils.getCtClass(classPool, collectorsMethodSignature, newReturnType);
		
		MethodSignature newSignature = new MethodSignature(collectorsMethodSignature.getTypeParameters(), ArrayUtils.merge(new Type[] {streamType}, collectorsMethodSignature.getParameterTypes()), newReturnType, collectorsMethodSignature.getExceptionTypes());
		
		CtClass streamClass = classPool.get("java.util.stream.Stream");
		CtClass[] collectorsMethodParameterTypes = collectorsMethod.getParameterTypes();

	// TODO add parameter names
		CtMethod ctMethod = new CtMethod(newReturnTypeClass, collectorsMethod.getName(), ArrayUtils.merge(new CtClass[] {streamClass}, collectorsMethodParameterTypes), extensionClass);
		ctMethod.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		ctMethod.setGenericSignature(newSignature.encode());
		ctMethod.setBodyWithOrdinalParameters("return (" + newReturnTypeClass.getName() + ") $0.collect(java.util.stream.Collectors." + collectorsMethod.getName() + "($$1-" + collectorsMethodParameterTypes.length + "));");

		return ctMethod;
	}
}
