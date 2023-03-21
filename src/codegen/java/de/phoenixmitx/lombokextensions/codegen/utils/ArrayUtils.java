package de.phoenixmitx.lombokextensions.codegen.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtils {
	
	// sadly we cant use ArrayExtensions in the CodeGen so we have to copy the addAll method here
	public <T> T[] merge(T[] arr, T[] c) {
		T[] newArr = Arrays.copyOf(arr, arr.length + c.length);
		System.arraycopy(c, 0, newArr, arr.length, c.length);
		return newArr;
	}

	public <T> int indexOf(T[] arr, T element) {
		for (int i = 0; i < arr.length; i++) {
			if (Objects.equals(arr[i], element)) return i;
		}
		return -1;
	}
	
	public <T> T[] removeIndex(T[] arr, int index) {
		T[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}
	
	public <T> T[] remove(T[] arr, T element) {
		int index = indexOf(arr, element);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	// TODO add to array extensions
	public <A, B, R> R[] zip(A[] a, B[] b, Zipper<A, B, R> zipper, IntFunction<R[]> generator) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Arrays must have the same length");
		}
		R[] result = generator.apply(a.length);
		for (int i = 0; i < a.length; i++) {
			result[i] = zipper.zip(a[i], b[i]);
		}
		return result;
	}

	public <A, B, R> R[] zipFilter(A[] a, B[] b, ZippingFilter<A, B> filter, Zipper<A, B, R> zipper, IntFunction<R[]> generator) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Arrays must have the same length");
		}
		R[] result = generator.apply(a.length);
		int length = 0;
		for (int i = 0; i < a.length; i++) {
			if (filter.filter(a[i], b[i])) {
				result[length++] = zipper.zip(a[i], b[i]);
			}
		}
		return Arrays.copyOf(result, length);
	}

	@FunctionalInterface
	public interface Zipper<A, B, R> {
		R zip(A a, B b);
	}

	@FunctionalInterface
	public interface ZippingFilter<A, B> {
		boolean filter(A a, B b);
	}
}
