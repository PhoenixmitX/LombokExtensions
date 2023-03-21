package de.phoenixmitx.lombokextensions.codegen.utils;

import java.util.Arrays;

import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AnnotationUtils {
	public Object getValue(MemberValue memberValue) {
		if (memberValue instanceof AnnotationMemberValue) {
			return ((AnnotationMemberValue) memberValue).getValue();
		} else if (memberValue instanceof ArrayMemberValue) {
			return Arrays.stream(((ArrayMemberValue) memberValue).getValue())
					.map(AnnotationUtils::getValue)
					.toArray();
		} else if (memberValue instanceof BooleanMemberValue) {
			return ((BooleanMemberValue) memberValue).getValue();
		} else if (memberValue instanceof ByteMemberValue) {
			return ((ByteMemberValue) memberValue).getValue();
		} else if (memberValue instanceof CharMemberValue) {
			return ((CharMemberValue) memberValue).getValue();
		} else if (memberValue instanceof DoubleMemberValue) {
			return ((DoubleMemberValue) memberValue).getValue();
		} else if (memberValue instanceof EnumMemberValue) {
			return ((EnumMemberValue) memberValue).getValue();
		} else if (memberValue instanceof FloatMemberValue) {
			return ((FloatMemberValue) memberValue).getValue();
		} else if (memberValue instanceof IntegerMemberValue) {
			return ((IntegerMemberValue) memberValue).getValue();
		} else if (memberValue instanceof LongMemberValue) {
			return ((LongMemberValue) memberValue).getValue();
		} else if (memberValue instanceof ShortMemberValue) {
			return ((ShortMemberValue) memberValue).getValue();
		} else if (memberValue instanceof StringMemberValue) {
			return ((StringMemberValue) memberValue).getValue();
		} else if (memberValue instanceof ClassMemberValue) {
			return ((ClassMemberValue) memberValue).getValue();
		} else {
			throw new IllegalArgumentException("Unknown MemberValue type: " + memberValue.getClass().getName());
		}
	}
}
