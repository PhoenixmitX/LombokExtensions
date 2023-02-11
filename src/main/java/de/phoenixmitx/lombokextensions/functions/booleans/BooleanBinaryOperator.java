package de.phoenixmitx.lombokextensions.functions.booleans;

@FunctionalInterface
public interface BooleanBinaryOperator {
	boolean applyAsBoolean(boolean left, boolean right);
}
