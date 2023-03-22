package de.phoenixmitx.lombokextensions.codegen.transformers.general.primitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.SOURCE)
@interface Primitive {

	public static Class<?>[] JAVA_DEFAULT = { int.class, long.class, double.class };

	Class<?>[] value() default {int.class, long.class, double.class, float.class, boolean.class, byte.class, char.class, short.class};
}