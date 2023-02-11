package de.phoenixmitx.lombokextensions.functions.shorts;

@FunctionalInterface
public interface ShortBinaryOperator {
	short applyAsShort(short left, short right);
}
