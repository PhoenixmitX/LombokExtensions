package de.phoenixmitx.lombokextensions.array;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.phoenixmitx.lombokextensions.functions.shorts.OptionalShort;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortConsumer;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortFunction;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortPredicate;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortUnaryOperator;
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

	public OptionalShort first(short[] arr) {
		if (arr.length == 0) return OptionalShort.empty();
		return OptionalShort.of(arr[0]);
	}

	public OptionalShort last(short[] arr) {
		if (arr.length == 0) return OptionalShort.empty();
		return OptionalShort.of(arr[arr.length - 1]);
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

	public short[] removeIf(short[] arr, ShortPredicate predicate) {
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

	public short[] retainIf(short[] arr, ShortPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public OptionalShort find(short[] arr, ShortPredicate condition) {
		for (short ele : arr) {
			if (condition.test(ele)) return OptionalShort.of(ele);
		}
		return OptionalShort.empty();
	}

	public int findIndex(short[] arr, ShortPredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(short[] arr, ShortPredicate condition) {
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

	public short[] filter(short[] arr, ShortPredicate condition) {
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

	public void forEach(short[] arr, ShortConsumer consumer) {
		for (short ele : arr) {
			consumer.accept(ele);
		}
	}

	public short[] map(short[] arr, ShortUnaryOperator function) {
		short[] result = new short[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsShort(arr[i]);
		}
		return result;
	}

	public short[] filterMap(short[] arr, ShortFunction<OptionalShort> function) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			OptionalShort optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsShort();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public short[] filterMap(short[] arr, ShortPredicate condition, ShortUnaryOperator function) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsShort(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public short[] mapFilter(short[] arr, ShortUnaryOperator function, ShortPredicate condition) {
		short[] tmp = new short[arr.length];
		int i = 0;
		for (short ele : arr) {
			short mapped = function.applyAsShort(ele);
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

	public short[] flatMap(short[] arr, ShortFunction<short[]> function) {
		return flattened(mapToObj(arr, function, short[][]::new));
	}

	public <T> T[] mapToObj(short[] arr, ShortFunction<T> function, IntFunction<T[]> supplier) {
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

	public OptionalShort reduce(short[] arr, ShortBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalShort.empty();
		short result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsShort(result, arr[i]);
		}
		return OptionalShort.of(result);
	}

	public short reduce(short[] arr, short identity, ShortBinaryOperator accumulator) {
		short result = identity;
		for (short ele : arr) {
			result = accumulator.applyAsShort(result, ele);
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

	public int sum(short[] arr) {
		int result = 0;
		for (double ele : arr) {
			result += ele;
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
