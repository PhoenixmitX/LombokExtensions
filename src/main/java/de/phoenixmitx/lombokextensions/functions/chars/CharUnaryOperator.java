package de.phoenixmitx.lombokextensions.functions.chars;

@FunctionalInterface
public interface CharUnaryOperator {
	char applyAsChar(char operand);
}
