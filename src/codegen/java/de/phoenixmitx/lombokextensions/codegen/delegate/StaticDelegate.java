package de.phoenixmitx.lombokextensions.codegen.delegate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
  public @interface StaticDelegate {
  Class<?>[] value();
}