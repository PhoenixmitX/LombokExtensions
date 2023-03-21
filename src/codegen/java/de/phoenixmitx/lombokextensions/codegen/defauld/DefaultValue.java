package de.phoenixmitx.lombokextensions.codegen.defauld;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.PARAMETER})
public @interface DefaultValue {
	String value();
}
