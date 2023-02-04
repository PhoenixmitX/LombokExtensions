package de.phoenixmitx.lombokextensions.array;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public interface IntArrayExtension {
  
  public static IntStream stream(int[] arr) {
		return IntStream.of(arr);
	}

	public static Integer[] boxed(int[] arr) {
		Integer[] result = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public static int[] unboxed(Integer[] arr) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public static int[] add(int[] arr, int i) {
		int[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = i;
		return result;
	}

	public static int[] add(int[] arr, int index, int i) {
		int[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = i;
		return result;
	}

	public static int[] addAll(int[] arr, int... ints) {
		int[] result = Arrays.copyOf(arr, arr.length + ints.length);
		System.arraycopy(ints, 0, result, arr.length, ints.length);
		return result;
	}

	public static int[] addAll(int[] arr, int index, int... ints) {
		int[] result = Arrays.copyOf(arr, arr.length + ints.length);
		System.arraycopy(arr, index, result, index + ints.length, arr.length - index);
		System.arraycopy(ints, 0, result, index, ints.length);
		return result;
	}

	public static int[] range(int[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public static int[] removeRange(int[] arr, int start, int end) {
		int[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public static int[] remove(int[] arr, int i) {
		int index = indexOf(arr, i);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public static int[] removeIndex(int[] arr, int index) {
		int[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public static int[] removeAll(int[] arr, int... ints) {
		for (int i = 0; i < ints.length; i++) {
			if (contains(arr, ints[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static int[] removeIf(int[] arr, IntPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static int[] retainAll(int[] arr, int... ints) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(ints, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static int[] retainIf(int[] arr, IntPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static OptionalInt find(int[] arr, IntPredicate condition) {
		for (int ele : arr) {
			if (condition.test(ele)) return OptionalInt.of(ele);
		}
		return OptionalInt.empty();
	}

	public static int findIndex(int[] arr, IntPredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public static int findLastIndex(int[] arr, IntPredicate condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public static int indexOf(int[] arr, int i) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == i) return index;
		}
		return -1;
	}

	public static int lastIndexOf(int[] arr, int i) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == i) return index;
		}
		return -1;
	}

	public static boolean contains(int[] arr, int i) {
		return indexOf(arr, i) != -1;
	}

	public static boolean containsAll(int[] arr, int... ints) {
		for (int i : ints) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public static boolean containsAny(int[] arr, int... ints) {
		for (int i : ints) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public static int[] filter(int[] arr, IntPredicate condition) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		int[] result = new int[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public static void forEach(int[] arr, IntConsumer consumer) {
		for (int ele : arr) {
			consumer.accept(ele);
		}
	}

	public static int[] map(int[] arr, IntUnaryOperator function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public static int[] filterMap(int[] arr, IntFunction<OptionalInt> function) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			OptionalInt optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsInt();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static int[] filterMap(int[] arr, IntPredicate condition, IntUnaryOperator function) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsInt(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static int[] mapFilter(int[] arr, IntUnaryOperator function, IntPredicate condition) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			int mapped = function.applyAsInt(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static int[] flattened(int[][] arr) {
		int size = 0;
		for (int[] a : arr) {
			size += a.length;
		}
		int[] result = new int[size];
		int i = 0;
		for (int[] a : arr) {
			System.arraycopy(a, 0, result, i, a.length);
			i += a.length;
		}
		return result;
	}

	public static int[] flatMap(int[] arr, IntFunction<int[]> function) {
		return flattened(mapToObj(arr, function, int[][]::new));
	}

	public static <T> T[] mapToObj(int[] arr, IntFunction<T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public static long[] mapToLong(int[] arr, IntToLongFunction function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public static double[] mapToDouble(int[] arr, IntToDoubleFunction function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public static OptionalInt reduce(int[] arr, IntBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalInt.empty();
		int result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsInt(result, arr[i]);
		}
		return OptionalInt.of(result);
	}

	public static int reduce(int[] arr, int identity, IntBinaryOperator accumulator) {
		int result = identity;
		for (int ele : arr) {
			result = accumulator.applyAsInt(result, ele);
		}
		return result;
	}

	public static <R> R reduce(int[] arr, R identity, BiFunction<R,Integer,R> accumulator) {
		R result = identity;
		for (int ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public static int[] reverse(int[] arr) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public static int[] distinct(int[] arr) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			if (!contains(tmp, ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static int[] distinctSorted(int[] arr) {
		int[] tmp = new int[arr.length];
		int j = 0;
		for (int i = 0, n = arr.length; i < n; i++) {
			if (i == 0 || arr[i] != arr[i - 1]) {
				tmp[j++] = arr[i];
			}
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
