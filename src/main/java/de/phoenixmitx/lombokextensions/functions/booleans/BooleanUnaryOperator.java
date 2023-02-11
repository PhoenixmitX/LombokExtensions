package de.phoenixmitx.lombokextensions.functions.booleans;

@FunctionalInterface
public interface BooleanUnaryOperator {
  boolean applyAsBoolean(boolean value);
}
