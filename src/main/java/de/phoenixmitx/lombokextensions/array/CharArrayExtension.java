package de.phoenixmitx.lombokextensions.array;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

public interface CharArrayExtension {
  
	public static Character[] boxed(char[] arr) {
		Character[] result = new Character[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public static char[] unboxed(Character[] arr) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public static String asString(char[] arr) {
		return new String(arr);
	}

	public static char[] add(char[] arr, char c) {
		char[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = c;
		return result;
	}

	public static char[] add(char[] arr, char index, char c) {
		char[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = c;
		return result;
	}

	public static char[] addAll(char[] arr, char... chars) {
		char[] result = Arrays.copyOf(arr, arr.length + chars.length);
		System.arraycopy(chars, 0, result, arr.length, chars.length);
		return result;
	}

	public static char[] addAll(char[] arr, int index, char... chars) {
		char[] result = Arrays.copyOf(arr, arr.length + chars.length);
		System.arraycopy(arr, index, result, index + chars.length, arr.length - index);
		System.arraycopy(chars, 0, result, index, chars.length);
		return result;
	}

	public static char[] range(char[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public static char[] removeRange(char[] arr, int start, int end) {
		char[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public static char[] remove(char[] arr, char c) {
		int index = indexOf(arr, c);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public static char[] removeIndex(char[] arr, int index) {
		char[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public static char[] removeAll(char[] arr, char... chars) {
		for (int i = 0; i < chars.length; i++) {
			if (contains(arr, chars[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static char[] removeIf(char[] arr, Predicate<Character> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static char[] retainAll(char[] arr, char... chars) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(chars, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static char[] retainIf(char[] arr, Predicate<Character> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static Optional<Character> find(char[] arr, Predicate<Character> condition) {
		for (char ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public static int findIndex(char[] arr, Predicate<Character> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public static int findLastIndex(char[] arr, Predicate<Character> condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public static int indexOf(char[] arr, char c) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public static int lastIndexOf(char[] arr, char c) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public static boolean contains(char[] arr, char c) {
		return indexOf(arr, c) != -1;
	}

	public static boolean containsAll(char[] arr, char... chars) {
		for (char i : chars) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public static boolean containsAny(char[] arr, char... chars) {
		for (char i : chars) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public static char[] filter(char[] arr, Predicate<Character> condition) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		char[] result = new char[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public static void forEach(char[] arr, Consumer<Character> consumer) {
		for (char ele : arr) {
			consumer.accept(ele);
		}
	}

	public static char[] map(char[] arr, UnaryOperator<Character> function) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public static char[] filterMap(char[] arr, Function<Character, Optional<Character>> function) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			Optional<Character> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static char[] filterMap(char[] arr, Predicate<Character> condition, UnaryOperator<Character> function) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static char[] mapFilter(char[] arr, UnaryOperator<Character> function, Predicate<Character> condition) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			char mapped = function.apply(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static char[] flattened(char[][] arr) {
		int size = 0;
		for (char[] i : arr) {
			size += i.length;
		}
		char[] result = new char[size];
		int index = 0;
		for (char[] i : arr) {
			System.arraycopy(i, 0, result, index, i.length);
			index += i.length;
		}
		return result;
	}

	public static char[] flatMap(char[] arr, Function<Character,char[]> function) {
		return flattened(mapToObj(arr, function, char[][]::new));
	}

	public static <T> T[] mapToObj(char[] arr, Function<Character, T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public static long[] mapToLong(char[] arr, ToLongFunction<Character> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public static double[] mapToDouble(char[] arr, ToDoubleFunction<Character> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public static Optional<Character> reduce(char[] arr, BinaryOperator<Character> accumulator) {
		if (arr.length == 0) return Optional.empty();
		char result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public static char reduce(char[] arr, char identity, BinaryOperator<Character> accumulator) {
		char result = identity;
		for (char ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public static <R> R reduce(char[] arr, R identity, BiFunction<R,Character,R> accumulator) {
		R result = identity;
		for (char ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public static char[] reverse(char[] arr) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public static char[] distinct(char[] arr) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			if (!contains(tmp, ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		char[] result = new char[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public static char[] distinctSorted(char[] arr) {
		char[] tmp = new char[arr.length];
		int j = 0;
		for (char ele : arr) {
			if (!contains(tmp, ele)) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
