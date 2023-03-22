package de.phoenixmitx.lombokextensions.codegen.transformers.general.staticdelegate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
  public @interface StaticDelegate {
  Class<?>[] value();
}