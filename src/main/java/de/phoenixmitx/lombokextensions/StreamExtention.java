package de.phoenixmitx.lombokextensions;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamExtention {

	// GENERIC STREAMS

	public <T> Stream<T> asStream(T t) {
		return Stream.of(t);
	}

	public static <T> Stream<T> concat(Stream<T> t, Stream<T> next) {
		return Stream.concat(t, next);
	}

	// INT STREAMS

	public static IntStream asStream(int t) {
		return IntStream.of(t);
	}

	public static IntStream concat(IntStream t, IntStream next) {
		return IntStream.concat(t, next);
	}

	// LONG STREAMS

	public static LongStream asStream(long t) {
		return LongStream.of(t);
	}

	public static LongStream concat(LongStream t, LongStream next) {
		return LongStream.concat(t, next);
	}

	// DOUBLE STREAMS

	public static DoubleStream asStream(double t) {
		return DoubleStream.of(t);
	}

	public static DoubleStream concat(DoubleStream t, DoubleStream next) {
		return DoubleStream.concat(t, next);
	}

	// CLASS SPECIFIC STREAMS

	public static <T> Stream<T> asStream(Iterable<T> t) {
		return StreamSupport.stream(t.spliterator(), false);
	}
}
