package de.phoenixmitx.lombokextensions;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import de.phoenixmitx.lombokextensions.codegen.transformers.general.staticdelegate.StaticDelegate;
import lombok.experimental.UtilityClass;

@UtilityClass
@StaticDelegate({ CollectorsExtension.class })
public class StreamExtension {

	// GENERIC STREAMS

	public <T> Stream<T> filterOptional(Stream<Optional<T>> stream) {
		return stream.filter(Optional::isPresent).map(Optional::get);
	}

	// INT STREAMS

	public IntStream unboxedInt(Stream<Integer> stream) {
		return stream.mapToInt(Integer::intValue);
	}
	
	public IntStream filterOptionalInt(Stream<OptionalInt> stream) {
		return stream.filter(OptionalInt::isPresent).mapToInt(OptionalInt::getAsInt);
	}

	// LONG STREAMS

	public LongStream unboxedLong(Stream<Long> stream) {
		return stream.mapToLong(Long::longValue);
	}

	public LongStream filterOptionalLong(Stream<OptionalLong> stream) {
		return stream.filter(OptionalLong::isPresent).mapToLong(OptionalLong::getAsLong);
	}

	// DOUBLE STREAMS

	public DoubleStream unboxedDouble(Stream<Double> stream) {
		return stream.mapToDouble(Double::doubleValue);
	}

	public DoubleStream filterOptionalDouble(Stream<OptionalDouble> stream) {
		return stream.filter(OptionalDouble::isPresent).mapToDouble(OptionalDouble::getAsDouble);
	}
}
