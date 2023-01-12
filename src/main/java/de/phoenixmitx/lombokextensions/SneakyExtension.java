package de.phoenixmitx.lombokextensions;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SneakyExtension {

	public static <T, R> R callSneaky(T obj, CheckedFunction<T, R> func) {
		return func.applySneaky(obj);
	}

	public static <T> void callSneaky(T obj, CheckedConsumer<T> func) {
		func.acceptSneaky(obj);
	}

	@FunctionalInterface
	public static interface CheckedFunction<T, R> {
		R apply(T t) throws Exception;

		@SneakyThrows
		default R applySneaky(T t) {
			return apply(t);
		}

		default Function<T, R> toFunction() {
			return this::applySneaky;
		}
	}

	@FunctionalInterface
	public static interface CheckedConsumer<T> {
		void accept(T t) throws Exception;

		@SneakyThrows
		default void acceptSneaky(T t) {
			accept(t);
		}

		default Consumer<T> toConsumer() {
			return this::acceptSneaky;
		}
	}
}
