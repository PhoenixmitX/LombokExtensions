package de.phoenixmitx.lombokextensions.codegen.transformer.annotation;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import de.phoenixmitx.lombokextensions.codegen.transformer.CodegenTransformer;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;

public abstract class CodegenAnnotationTransformer extends CodegenTransformer {

  protected final Class<?> annotationType;
	protected final String fullyQualifiedAnnotationName;
	protected final ElementType[] target;

  protected CodegenAnnotationTransformer(Class<?> annotationType) {
		this.annotationType = annotationType;
    this.fullyQualifiedAnnotationName = annotationType.getName();
		this.target = annotationType.getAnnotation(Target.class).value();
  }

  @Override
  public final boolean transform(CtClass ctClass, byte[] classfileBuffer) throws IOException, CannotCompileException, NotFoundException {
		boolean modified = false;
		for (ElementType elementType : target) {
			switch (elementType) {
				case TYPE: {
					AnnotationsAttribute annotationsAttribute = getAttribute(ctClass.getClassFile().getAttributes(), AnnotationsAttribute.class);
					Annotation annotation = getAnnotation(annotationsAttribute);
					if (annotation != null) {
						modified |= transformType(ctClass, annotation, annotationsAttribute, classfileBuffer);
					}
					break;
				}
				case METHOD: {
					for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
						AnnotationsAttribute annotationsAttribute = getAttribute(ctMethod.getMethodInfo().getAttributes(), AnnotationsAttribute.class);
						Annotation annotation = getAnnotation(annotationsAttribute);
						if (annotation != null) {
							modified |= transformMethod(ctClass, ctMethod, annotation, annotationsAttribute, classfileBuffer);
						}
					}
					break;
				}
				case PARAMETER: {
					for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
						ParameterAnnotationsAttribute parameterAnnotationsAttribute = getAttribute(ctMethod.getMethodInfo().getAttributes(), ParameterAnnotationsAttribute.class);
						if (parameterAnnotationsAttribute == null) {
							continue;
						}
						Annotation[][] annotations = parameterAnnotationsAttribute.getAnnotations();
						Annotation[] annotation = new Annotation[annotations.length];
						boolean annotationFound = false;
						for (int i = 0; i < annotations.length; i++) {
							for (Annotation a : annotations[i]) {
								if (Objects.equals(a.getTypeName(), fullyQualifiedAnnotationName)) {
									annotation[i] = a;
									annotationFound = true;
									break;
								}
							}
						}
						if (annotationFound) {
							modified |= transformMethodWithParameters(ctClass, ctMethod, annotation, parameterAnnotationsAttribute, classfileBuffer);
						}
					}
					break;
				}
				default: {
					throw new UnsupportedOperationException("Unsupported target type: " + elementType);
				}
			}
		}

		return modified;
  }

	private final <T> T getAttribute(List<AttributeInfo> attributes, Class<T> attributeType) {
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

  protected boolean transformType(CtClass ctClass, Annotation ctAnnotation, AnnotationsAttribute annotationsAttribute, byte[] classfileBuffer) throws IOException, CannotCompileException, NotFoundException { return false; }
	protected boolean transformMethod(CtClass ctClass, CtMethod method, Annotation ctAnnotation, AnnotationsAttribute annotationsAttribute, byte[] classfileBuffer) throws IOException, CannotCompileException, NotFoundException { return false; }
	protected boolean transformMethodWithParameters(CtClass ctClass, CtMethod method, Annotation[] ctAnnotation, ParameterAnnotationsAttribute annotationsAttribute, byte[] classfileBuffer) throws IOException, CannotCompileException, NotFoundException { return false; }
}
