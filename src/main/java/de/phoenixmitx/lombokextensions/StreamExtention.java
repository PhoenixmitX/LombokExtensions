package de.phoenixmitx.lombokextensions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamExtention { // TODO write methods for all types of streams

	// GENERIC STREAMS

	@FunctionalInterface
	public interface IntReducer<T> {
		int reduce(T obj, int i);
	}

	public <T> int reduceToInt(Stream<T> stream, int i, IntReducer<T> reducer) {
		int[] result = { i };
		stream.forEach(ele -> result[0] = reducer.reduce(ele, result[0]));
		return result[0];
	}

	public <T> int reduceToInt(Stream<T> stream, IntReducer<T> reducer) {
		return reduceToInt(stream, 0, reducer);
	}

	// INT STREAMS

	// LONG STREAMS

	// DOUBLE STREAMS

	// CLASS SPECIFIC STREAMS

	public String join(Stream<String> stream) {
		return join(stream, "", "", "");
	}

	public String join(Stream<String> stream, String delimiter) {
		return join(stream, delimiter, "", "");
	}

	public String join(Stream<String> stream, String delimiter, String prefix, String suffix) {
		return stream.collect(Collectors.joining(delimiter, prefix, suffix));
	}
}
