package de.phoenixmitx.lombokextensions.functions.bytes;

@FunctionalInterface
public interface ByteFunction<R> {
	R apply(byte value);
}
