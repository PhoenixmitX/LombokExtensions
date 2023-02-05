package de.phoenixmitx.lombokextensions.array;

import java.util.Arrays;
import java.util.OptionalLong;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LongArrayExtension {

  public LongStream stream(long[] arr) {
		return LongStream.of(arr);
	}

	public Long[] boxed(long[] arr) {
		Long[] result = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public long[] unboxed(Long[] arr) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public long[] add(long[] arr, long l) {
		long[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = l;
		return result;
	}

	public long[] add(long[] arr, int index, long l) {
		long[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = l;
		return result;
	}

	public long[] addAll(long[] arr, long... longs) {
		long[] result = Arrays.copyOf(arr, arr.length + longs.length);
		System.arraycopy(longs, 0, result, arr.length, longs.length);
		return result;
	}

	public long[] addAll(long[] arr, int index, long... longs) {
		long[] result = Arrays.copyOf(arr, arr.length + longs.length);
		System.arraycopy(arr, index, result, index + longs.length, arr.length - index);
		System.arraycopy(longs, 0, result, index, longs.length);
		return result;
	}

	public long[] range(long[] arr, int from, int to) {
		long[] result = new long[to - from];
		System.arraycopy(arr, from, result, 0, to - from);
		return result;
	}

	public long[] removeRange(long[] arr, int from, int to) {
		long[] result = new long[arr.length - (to - from)];
		System.arraycopy(arr, 0, result, 0, from);
		System.arraycopy(arr, to, result, from, arr.length - to);
		return result;
	}

	public long[] remove(long[] arr, long l) {
		int index = indexOf(arr, l);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public long[] removeIndex(long[] arr, int index) {
		long[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public long[] removeAll(long[] arr, long... longs) {
		for (int i = 0; i < longs.length; i++) {
			arr = remove(arr, longs[i--]);
		}
		return arr;
	}

	public long[] removeIf(long[] arr, LongPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public long[] retainAll(long[] arr, long... longs) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(longs, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public long[] retainIf(long[] arr, LongPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public OptionalLong find(long[] arr, LongPredicate condition) {
		for (long ele : arr) {
			if (condition.test(ele)) return OptionalLong.of(ele);
		}
		return OptionalLong.empty();
	}

	public int findIndex(long[] arr, LongPredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(long[] arr, LongPredicate condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(long[] arr, long l) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == l) return index;
		}
		return -1;
	}

	public int lastIndexOf(long[] arr, long l) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == l) return index;
		}
		return -1;
	}

	public boolean contains(long[] arr, long l) {
		return indexOf(arr, l) != -1;
	}

	public boolean containsAll(long[] arr, long... longs) {
		for (long l : longs) {
			if (!contains(arr, l)) return false;
		}
		return true;
	}

	public boolean containsAny(long[] arr, long... longs) {
		for (long l : longs) {
			if (contains(arr, l)) return true;
		}
		return false;
	}

	public long[] filter(long[] arr, LongPredicate condition) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		long[] result = new long[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public void forEach(long[] arr, LongConsumer consumer) {
		for (long ele : arr) {
			consumer.accept(ele);
		}
	}

	public long[] map(long[] arr, LongUnaryOperator function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public long[] filterMap(long[] arr, LongFunction<OptionalLong> function) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			OptionalLong optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsLong();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public long[] filterMap(long[] arr, LongPredicate condition, LongUnaryOperator function) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsLong(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public long[] mapFilter(long[] arr, LongUnaryOperator function, LongPredicate condition) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			long mapped = function.applyAsLong(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public long[] flattened(long[][] arr) {
		int size = 0;
		for (long[] a : arr) {
			size += a.length;
		}
		long[] result = new long[size];
		int index = 0;
		for (long[] a : arr) {
			System.arraycopy(a, 0, result, index, a.length);
			index += a.length;
		}
		return result;
	}

	public long[] flatMap(long[] arr, LongFunction<long[]> function) {
		return flattened(mapToObj(arr, function, long[][]::new));
	}

	public <T> T[] mapToObj(long[] arr, LongFunction<T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(long[] arr, LongToIntFunction function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(long[] arr, LongToDoubleFunction function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public OptionalLong reduce(long[] arr, LongBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalLong.empty();
		long result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsLong(result, arr[i]);
		}
		return OptionalLong.of(result);
	}

	public long reduce(long[] arr, long identity, LongBinaryOperator accumulator) {
		long result = identity;
		for (long ele : arr) {
			result = accumulator.applyAsLong(result, ele);
		}
		return result;
	}

	public <R> R reduce(long[] arr, R identity, BiFunction<R,Long,R> accumulator) {
		R result = identity;
		for (long ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public long[] reverse(long[] arr) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public long[] distinct(long[] arr) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			if (indexOf(tmp, ele) == -1) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		long[] result = new long[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public long[] distinctSorted(long[] arr) {
		long[] tmp = new long[arr.length];
		int j = 0;
		for (long ele : arr) {
			if (Arrays.binarySearch(tmp, 0, j, ele) < 0) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
