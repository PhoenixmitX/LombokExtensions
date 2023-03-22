package de.phoenixmitx.lombokextensions.codegen.utils;

import java.util.Arrays;
import java.util.stream.Stream;

import javassist.ClassPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.NoSuchClassError;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AnnotationUtils {

	@SuppressWarnings("unchecked")
	public <T extends java.lang.annotation.Annotation> T getJavaAnnotation(Annotation annotation, ClassPool classPool) throws ClassNotFoundException, NoSuchClassError {
		ensureAllClassesRecompiled(annotation, classPool);
		return (T) annotation.toAnnotationType(AnnotationUtils.class.getClassLoader(), classPool);
	}

	public void ensureAllClassesRecompiled(Annotation annotation, ClassPool classPool) throws ClassNotFoundException {
		annotation.getMemberNames().stream()
				.map(annotation::getMemberValue)
				.flatMap(AnnotationUtils::getClassMemberValues)
				.map(ClassMemberValue::getValue)
				.forEach(classPool::getOrNull);
	}

	private Stream<ClassMemberValue> getClassMemberValues(MemberValue memberValue) {
		if (memberValue instanceof ClassMemberValue) {
			return Stream.of((ClassMemberValue) memberValue);
		} else if (memberValue instanceof ArrayMemberValue) {
			return Arrays.stream(((ArrayMemberValue) memberValue).getValue())
					.flatMap(AnnotationUtils::getClassMemberValues);
		} else {
			return Stream.empty();
		}
	}
}
