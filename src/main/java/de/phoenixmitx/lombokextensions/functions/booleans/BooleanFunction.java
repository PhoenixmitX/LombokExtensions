package de.phoenixmitx.lombokextensions.functions.booleans;

@FunctionalInterface
public interface BooleanFunction<T> {
  T apply(boolean input);
}
