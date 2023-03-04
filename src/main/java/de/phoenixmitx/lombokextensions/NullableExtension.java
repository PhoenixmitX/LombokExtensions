package de.phoenixmitx.lombokextensions;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NullableExtension {

	public <T> Optional<T> asOptional(T t) {
		return Optional.ofNullable(t);
	}

	public <T> T ifNull(T t, T defaultValue) {
		return t == null ? defaultValue : t;
	}

	public <T> void ifNull(T t, Runnable run) {
		if (t == null) {
			run.run();
		}
	}

	public <T> T mapIfNull(T t, Supplier<T> supplier) {
		return t == null ? supplier.get() : t;
	}

	public <T,R> R mapIfNotNull(T t, Function<T,R> function) {
		return t != null ? function.apply(t) : null;
	}

	public <T> void ifNotNull(T t, Consumer<T> function) {
		if (t != null) {
			function.accept(t);
		}
	}

	public <T> T makeNullIf(T t, boolean condition) {
		return condition ? null : t;
	}

	public <T> T makeNullIf(T t, Predicate<T> condition) {
		return t == null ? null : condition.test(t) ? null : t;
	}

	public <T,E extends Throwable> T ifNullThrow(T t, Supplier<E> exceptionSupplier) throws E {
		if (t == null) {
			throw exceptionSupplier.get();
		}
		return t;
	}
}
