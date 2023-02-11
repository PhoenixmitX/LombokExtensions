package de.phoenixmitx.lombokextensions.array;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.phoenixmitx.lombokextensions.functions.chars.CharBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.chars.CharConsumer;
import de.phoenixmitx.lombokextensions.functions.chars.CharFunction;
import de.phoenixmitx.lombokextensions.functions.chars.CharPredicate;
import de.phoenixmitx.lombokextensions.functions.chars.CharUnaryOperator;
import de.phoenixmitx.lombokextensions.functions.chars.OptionalChar;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CharArrayExtension {
	
	public Character[] boxed(char[] arr) {
		Character[] result = new Character[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public char[] unboxed(Character[] arr) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public String asString(char[] arr) {
		return new String(arr);
	}

	public OptionalChar first(char[] arr) {
		if (arr.length == 0) return OptionalChar.empty();
		return OptionalChar.of(arr[0]);
	}

	public OptionalChar last(char[] arr) {
		if (arr.length == 0) return OptionalChar.empty();
		return OptionalChar.of(arr[arr.length - 1]);
	}

	public char[] add(char[] arr, char c) {
		char[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = c;
		return result;
	}

	public char[] add(char[] arr, int index, char c) {
		char[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = c;
		return result;
	}

	public char[] addAll(char[] arr, char... chars) {
		char[] result = Arrays.copyOf(arr, arr.length + chars.length);
		System.arraycopy(chars, 0, result, arr.length, chars.length);
		return result;
	}

	public char[] addAll(char[] arr, int index, char... chars) {
		char[] result = Arrays.copyOf(arr, arr.length + chars.length);
		System.arraycopy(arr, index, result, index + chars.length, arr.length - index);
		System.arraycopy(chars, 0, result, index, chars.length);
		return result;
	}

	public char[] range(char[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public char[] removeRange(char[] arr, int start, int end) {
		char[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public char[] remove(char[] arr, char c) {
		int index = indexOf(arr, c);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public char[] removeIndex(char[] arr, int index) {
		char[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public char[] removeAll(char[] arr, char... chars) {
		for (int i = 0; i < chars.length; i++) {
			if (contains(arr, chars[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public char[] removeIf(char[] arr, CharPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public char[] retainAll(char[] arr, char... chars) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(chars, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public char[] retainIf(char[] arr, CharPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public OptionalChar find(char[] arr, CharPredicate condition) {
		for (char ele : arr) {
			if (condition.test(ele)) return OptionalChar.of(ele);
		}
		return OptionalChar.empty();
	}

	public int findIndex(char[] arr, CharPredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(char[] arr, CharPredicate condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(char[] arr, char c) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public int lastIndexOf(char[] arr, char c) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public boolean contains(char[] arr, char c) {
		return indexOf(arr, c) != -1;
	}

	public boolean containsAll(char[] arr, char... chars) {
		for (char i : chars) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public boolean containsAny(char[] arr, char... chars) {
		for (char i : chars) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public char[] filter(char[] arr, CharPredicate condition) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public void forEach(char[] arr, CharConsumer consumer) {
		for (char ele : arr) {
			consumer.accept(ele);
		}
	}

	public char[] map(char[] arr, CharUnaryOperator function) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsChar(arr[i]);
		}
		return result;
	}

	public char[] filterMap(char[] arr, CharFunction<OptionalChar> function) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			OptionalChar optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsChar();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public char[] filterMap(char[] arr, CharPredicate condition, CharUnaryOperator function) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsChar(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public char[] mapFilter(char[] arr, CharUnaryOperator function, CharPredicate condition) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			char mapped = function.applyAsChar(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public char[] flattened(char[][] arr) {
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

	public char[] flatMap(char[] arr, CharFunction<char[]> function) {
		return flattened(mapToObj(arr, function, char[][]::new));
	}

	public <T> T[] mapToObj(char[] arr, CharFunction<T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(char[] arr, ToIntFunction<Character> function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public long[] mapToLong(char[] arr, ToLongFunction<Character> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(char[] arr, ToDoubleFunction<Character> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public OptionalChar reduce(char[] arr, CharBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalChar.empty();
		char result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsChar(result, arr[i]);
		}
		return OptionalChar.of(result);
	}

	public char reduce(char[] arr, char identity, CharBinaryOperator accumulator) {
		char result = identity;
		for (char ele : arr) {
			result = accumulator.applyAsChar(result, ele);
		}
		return result;
	}

	public <R> R reduce(char[] arr, R identity, BiFunction<R,Character,R> accumulator) {
		R result = identity;
		for (char ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public char[] reverse(char[] arr) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public char[] distinct(char[] arr) {
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

	public char[] distinctSorted(char[] arr) {
		char[] tmp = new char[arr.length];
		int j = 0;
		for (char ele : arr) {
			if (!contains(tmp, ele)) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
