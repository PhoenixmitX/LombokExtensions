package de.phoenixmitx.lombokextensions.functions.bytes;

@FunctionalInterface
public interface BytePredicate {
  boolean test(byte value);
}
