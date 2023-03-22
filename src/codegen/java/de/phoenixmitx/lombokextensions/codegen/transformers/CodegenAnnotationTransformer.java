package de.phoenixmitx.lombokextensions.codegen.transformers;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import de.phoenixmitx.lombokextensions.codegen.utils.AnnotationUtils;
import de.phoenixmitx.lombokextensions.codegen.utils.ArrayUtils;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.NoSuchClassError;

public abstract class CodegenAnnotationTransformer<T extends java.lang.annotation.Annotation> implements CodegenTransformer {

  protected final Class<T> annotationType;
	protected final String fullyQualifiedAnnotationName;
	protected final ElementType[] target;

  protected CodegenAnnotationTransformer(Class<T> annotationType) {
		this.annotationType = annotationType;
    this.fullyQualifiedAnnotationName = annotationType.getName();
		this.target = annotationType.getAnnotation(Target.class).value();
  }

  @Override
  public final boolean transform(CtClass ctClass, ClassPool classPool) throws IOException, CannotCompileException, NotFoundException, BadBytecode, ClassNotFoundException, NoSuchClassError {
		boolean modified = false;
		for (ElementType elementType : target) {
			switch (elementType) {
				case TYPE: {
					modified |= transformType(ctClass, classPool);
					break;
				}
				case METHOD: {
					modified |= transformMethod(ctClass, classPool);
					break;
				}
				case PARAMETER: {
					modified |= transformParameter(ctClass, classPool);
					break;
				}
				case TYPE_PARAMETER: {
					modified |= transformTypePartameter(ctClass, classPool);
					break;
				}
				default: {
					throw new UnsupportedOperationException("Unsupported target type: " + elementType);
				}
			}
		}

		return modified;
  }

	protected void transformType(CtClass ctClass, ClassPool classPool, T annotation) throws IOException, CannotCompileException, NotFoundException, BadBytecode { /* implemented by super class */ }
	protected void transformMethod(CtClass ctClass, ClassPool classPool, CtMethod method, T annotation) throws IOException, CannotCompileException, NotFoundException, BadBytecode { /* implemented by super class */ }
	protected void transformMethodWithParameterAnnotations(CtClass ctClass, ClassPool classPool, CtMethod method, Optional<T>[] annotations) throws IOException, CannotCompileException, NotFoundException, BadBytecode { /* implemented by super class */ }
	protected void transformMethodWithTypeParameterAnnotations(CtClass ctClass, ClassPool classPool, CtMethod method, Optional<T>[] annotations) throws IOException, CannotCompileException, NotFoundException, BadBytecode { /* implemented by super class */ }

	@SuppressWarnings({"null"})
	private boolean transformType(CtClass ctClass, ClassPool classPool) throws ClassNotFoundException, IOException, CannotCompileException, NotFoundException, BadBytecode, NoSuchClassError {
		AnnotationsAttribute annotationsAttribute = getAttribute(ctClass.getClassFile().getAttributes(), AnnotationsAttribute.class);
		Annotation annotation = getAnnotation(annotationsAttribute);
		if (annotation != null) {
			transformType(ctClass, classPool, AnnotationUtils.getJavaAnnotation(annotation, classPool));
			annotationsAttribute.removeAnnotation(fullyQualifiedAnnotationName);
			return true;
		}
		return false;
	}

	@SuppressWarnings({"null"})
	private boolean transformMethod(CtClass ctClass, ClassPool classPool) throws ClassNotFoundException, IOException, CannotCompileException, NotFoundException, BadBytecode, NoSuchClassError {
		boolean modified = false;
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			AnnotationsAttribute annotationsAttribute = getAttribute(ctMethod.getMethodInfo().getAttributes(), AnnotationsAttribute.class);
			Annotation annotation = getAnnotation(annotationsAttribute);
			if (annotation != null) {
				transformMethod(ctClass, classPool, ctMethod, AnnotationUtils.getJavaAnnotation(annotation, classPool));
				annotationsAttribute.removeAnnotation(fullyQualifiedAnnotationName);
				modified = true;
			}
		}
		return modified;
	}

	private boolean transformParameter(CtClass ctClass, ClassPool classPool) throws ClassNotFoundException, NoSuchClassError, IOException, CannotCompileException, NotFoundException, BadBytecode {
		boolean modified = false;
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			ParameterAnnotationsAttribute parameterAnnotationsAttribute = getAttribute(ctMethod.getMethodInfo().getAttributes(), ParameterAnnotationsAttribute.class);
			if (parameterAnnotationsAttribute == null) {
				continue;
			}
			Annotation[][] annotations = parameterAnnotationsAttribute.getAnnotations();
			Optional<T>[] annotation = getAnnotations(annotations, ctClass.getClassPool());
			if (isAnnotationFound(annotation)) {
				transformMethodWithParameterAnnotations(ctClass, classPool, ctMethod, annotation);
				removeAnnotations(annotations);
				parameterAnnotationsAttribute.setAnnotations(annotations);
				modified = true;
			}
		}
		return modified;
	}

	private boolean transformTypePartameter(CtClass ctClass, ClassPool classPool) throws IOException, CannotCompileException, NotFoundException, BadBytecode, ClassNotFoundException, NoSuchClassError {
		boolean modified = false;
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			ParameterAnnotationsAttribute parameterAnnotationsAttribute = getAttribute(ctMethod.getMethodInfo().getAttributes(), ParameterAnnotationsAttribute.class);
			if (parameterAnnotationsAttribute == null) {
				continue;
			}
			Annotation[][] annotations = parameterAnnotationsAttribute.getAnnotations();
			Optional<T>[] annotation = getAnnotations(annotations, ctClass.getClassPool());
			if (isAnnotationFound(annotation)) {
				transformMethodWithTypeParameterAnnotations(ctClass, classPool, ctMethod, annotation);
				removeAnnotations(annotations);
				parameterAnnotationsAttribute.setAnnotations(annotations);
				modified = true;
			}
		}
		return modified;
	}

	@SuppressWarnings("unchecked")
	private Optional<T>[] getAnnotations(Annotation[][] annotations, ClassPool classPool) throws ClassNotFoundException, NoSuchClassError {
		Optional<T>[] result = new Optional[annotations.length];
		Arrays.fill(result, Optional.empty());
		for (int i = 0; i < annotations.length; i++) {
			for (Annotation a : annotations[i]) {
				if (Objects.equals(a.getTypeName(), fullyQualifiedAnnotationName)) {
					result[i] = Optional.of(AnnotationUtils.getJavaAnnotation(a, classPool));
					break;
				}
			}
		}
		return result;
	}

	private boolean isAnnotationFound(Optional<T>[] annotations) {
		for (Optional<T> annotation : annotations) {
			if (annotation.isPresent()) {
				return true;
			}
		}
		return false;
	}

	private void removeAnnotations(Annotation[][] annotations) {
		for (int i = 0; i < annotations.length; i++) {
			for (Annotation a : annotations[i]) {
				if (Objects.equals(a.getTypeName(), fullyQualifiedAnnotationName)) {
					annotations[i] = ArrayUtils.remove(annotations[i], a);
					break;
				}
			}
		}
	}

	private final <A> A getAttribute(List<AttributeInfo> attributes, Class<A> attributeType) {
		return attributes.stream()
				.filter(attributeType::isInstance)
				.map(attributeType::cast)
				.findFirst()
				.orElse(null);
	}

	private final Annotation getAnnotation(AnnotationsAttribute annotationsAttribute) {
		return Optional.ofNullable(annotationsAttribute)
				.map(attr -> attr.getAnnotation(fullyQualifiedAnnotationName))
				.orElse(null);
	}
}
