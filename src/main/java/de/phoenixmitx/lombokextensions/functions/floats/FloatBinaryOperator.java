package de.phoenixmitx.lombokextensions.functions.floats;

@FunctionalInterface
public interface FloatBinaryOperator {
	float applyAsFloat(float left, float right);
}
