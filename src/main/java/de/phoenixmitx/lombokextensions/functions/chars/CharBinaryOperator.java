package de.phoenixmitx.lombokextensions.functions.chars;

@FunctionalInterface
public interface CharBinaryOperator {
	public char applyAsChar(char left, char right);
}
