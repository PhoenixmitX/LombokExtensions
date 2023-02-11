package de.phoenixmitx.lombokextensions.functions.bytes;

@FunctionalInterface
public interface ByteBinaryOperator {
	byte applyAsByte(byte left, byte right);
}
