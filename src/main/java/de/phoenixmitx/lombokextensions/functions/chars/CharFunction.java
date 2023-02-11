package de.phoenixmitx.lombokextensions.functions.chars;

@FunctionalInterface
public interface CharFunction<R> {
	R apply(char value);
}
