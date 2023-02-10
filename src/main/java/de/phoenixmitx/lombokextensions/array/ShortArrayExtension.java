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
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ShortArrayExtension {
  
	public Short[] boxed(short[] arr) {
		Short[] result = new Short[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public short[] unboxed(Short[] arr) {
		short[] result = new short[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public Optional<Short> first(short[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[0]);
	}

	public Optional<Short> last(short[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[arr.length - 1]);
	}

	public short[] add(short[] arr, short c) {
		short[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = c;
		return result;
	}

	public short[] add(short[] arr, int index, short c) {
		short[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = c;
		return result;
	}

	public short[] addAll(short[] arr, short... shorts) {
		short[] result = Arrays.copyOf(arr, arr.length + shorts.length);
		System.arraycopy(shorts, 0, result, arr.length, shorts.length);
		return result;
	}

	public short[] addAll(short[] arr, int index, short... shorts) {
		short[] result = Arrays.copyOf(arr, arr.length + shorts.length);
		System.arraycopy(arr, index, result, index + shorts.length, arr.length - index);
		System.arraycopy(shorts, 0, result, index, shorts.length);
		return result;
	}

	public short[] range(short[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public short[] removeRange(short[] arr, int start, int end) {
		short[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public short[] remove(short[] arr, short c) {
		int index = indexOf(arr, c);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public short[] removeIndex(short[] arr, int index) {
		short[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public short[] removeAll(short[] arr, short... shorts) {
		for (int i = 0; i < shorts.length; i++) {
			if (contains(arr, shorts[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public short[] removeIf(short[] arr, Predicate<Short> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public short[] retainAll(short[] arr, short... shorts) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(shorts, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public short[] retainIf(short[] arr, Predicate<Short> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public Optional<Short> find(short[] arr, Predicate<Short> condition) {
		for (short ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public int findIndex(short[] arr, Predicate<Short> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(short[] arr, Predicate<Short> condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(short[] arr, short c) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public int lastIndexOf(short[] arr, short c) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public boolean contains(short[] arr, short c) {
		return indexOf(arr, c) != -1;
	}

	public boolean containsAll(short[] arr, short... shorts) {
		for (short i : shorts) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public boolean containsAny(short[] arr, short... shorts) {
		for (short i : shorts) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public short[] filter(short[] arr, Predicate<Short> condition) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		short[] result = new short[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public void forEach(short[] arr, Consumer<Short> consumer) {
		for (short ele : arr) {
			consumer.accept(ele);
		}
	}

	public short[] map(short[] arr, UnaryOperator<Short> function) {
		short[] result = new short[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public short[] filterMap(short[] arr, Function<Short, Optional<Short>> function) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			Optional<Short> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public short[] filterMap(short[] arr, Predicate<Short> condition, UnaryOperator<Short> function) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public short[] mapFilter(short[] arr, UnaryOperator<Short> function, Predicate<Short> condition) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			short mapped = function.apply(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public short[] flattened(short[][] arr) {
		int size = 0;
		for (short[] i : arr) {
			size += i.length;
		}
		short[] result = new short[size];
		int index = 0;
		for (short[] i : arr) {
			System.arraycopy(i, 0, result, index, i.length);
			index += i.length;
		}
		return result;
	}

	public short[] flatMap(short[] arr, Function<Short,short[]> function) {
		return flattened(mapToObj(arr, function, short[][]::new));
	}

	public <T> T[] mapToObj(short[] arr, Function<Short, T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(short[] arr, ToIntFunction<Short> function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public long[] mapToLong(short[] arr, ToLongFunction<Short> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(short[] arr, ToDoubleFunction<Short> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public Optional<Short> reduce(short[] arr, BinaryOperator<Short> accumulator) {
		if (arr.length == 0) return Optional.empty();
		short result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public short reduce(short[] arr, short identity, BinaryOperator<Short> accumulator) {
		short result = identity;
		for (short ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public <R> R reduce(short[] arr, R identity, BiFunction<R,Short,R> accumulator) {
		R result = identity;
		for (short ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public short[] reverse(short[] arr) {
		short[] result = new short[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public short[] distinct(short[] arr) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			if (!contains(tmp, ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		short[] result = new short[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public short[] distinctSorted(short[] arr) {
		short[] tmp = new short[arr.length];
		int j = 0;
		for (short ele : arr) {
			if (!contains(tmp, ele)) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
