package de.phoenixmitx.lombokextensions.array;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.phoenixmitx.lombokextensions.functions.booleans.BooleanBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanConsumer;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanFunction;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanPredicate;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanUnaryOperator;
import de.phoenixmitx.lombokextensions.functions.booleans.OptionalBoolean;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BooleanArrayExtension {
	
	public Boolean[] boxed(boolean[] arr) {
		Boolean[] result = new Boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public boolean[] unboxed(Boolean[] arr) {
		boolean[] result = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public OptionalBoolean first(boolean[] arr) {
		if (arr.length == 0) return OptionalBoolean.empty();
		return OptionalBoolean.of(arr[0]);
	}

	public OptionalBoolean last(boolean[] arr) {
		if (arr.length == 0) return OptionalBoolean.empty();
		return OptionalBoolean.of(arr[arr.length - 1]);
	}

	public boolean[] add(boolean[] arr, boolean c) {
		boolean[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = c;
		return result;
	}

	public boolean[] add(boolean[] arr, int index, boolean c) {
		boolean[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = c;
		return result;
	}

	public boolean[] addAll(boolean[] arr, boolean... booleans) {
		boolean[] result = Arrays.copyOf(arr, arr.length + booleans.length);
		System.arraycopy(booleans, 0, result, arr.length, booleans.length);
		return result;
	}

	public boolean[] addAll(boolean[] arr, int index, boolean... booleans) {
		boolean[] result = Arrays.copyOf(arr, arr.length + booleans.length);
		System.arraycopy(arr, index, result, index + booleans.length, arr.length - index);
		System.arraycopy(booleans, 0, result, index, booleans.length);
		return result;
	}

	public boolean[] range(boolean[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public boolean[] removeRange(boolean[] arr, int start, int end) {
		boolean[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public boolean[] remove(boolean[] arr, boolean c) {
		int index = indexOf(arr, c);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public boolean[] removeIndex(boolean[] arr, int index) {
		boolean[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public boolean[] removeAll(boolean[] arr, boolean... booleans) {
		for (int i = 0; i < booleans.length; i++) {
			if (contains(arr, booleans[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public boolean[] removeIf(boolean[] arr, BooleanPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public boolean[] retainAll(boolean[] arr, boolean... booleans) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(booleans, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public boolean[] retainIf(boolean[] arr, BooleanPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public OptionalBoolean find(boolean[] arr, BooleanPredicate condition) {
		for (boolean ele : arr) {
			if (condition.test(ele)) return OptionalBoolean.of(ele);
		}
		return OptionalBoolean.empty();
	}

	public int findIndex(boolean[] arr, BooleanPredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(boolean[] arr, BooleanPredicate condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(boolean[] arr, boolean c) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public int lastIndexOf(boolean[] arr, boolean c) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public boolean contains(boolean[] arr, boolean c) {
		return indexOf(arr, c) != -1;
	}

	public boolean containsAll(boolean[] arr, boolean... booleans) {
		for (boolean i : booleans) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public boolean containsAny(boolean[] arr, boolean... booleans) {
		for (boolean i : booleans) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public boolean[] filter(boolean[] arr, BooleanPredicate condition) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		boolean[] result = new boolean[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public void forEach(boolean[] arr, BooleanConsumer consumer) {
		for (boolean ele : arr) {
			consumer.accept(ele);
		}
	}

	public boolean[] map(boolean[] arr, BooleanUnaryOperator function) {
		boolean[] result = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsBoolean(arr[i]);
		}
		return result;
	}

	public boolean[] filterMap(boolean[] arr, BooleanFunction<OptionalBoolean> function) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			OptionalBoolean optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsBoolean();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public boolean[] filterMap(boolean[] arr, BooleanPredicate condition, BooleanUnaryOperator function) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsBoolean(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public boolean[] mapFilter(boolean[] arr, BooleanUnaryOperator function, BooleanPredicate condition) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			boolean mapped = function.applyAsBoolean(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public boolean[] flattened(boolean[][] arr) {
		int size = 0;
		for (boolean[] i : arr) {
			size += i.length;
		}
		boolean[] result = new boolean[size];
		int index = 0;
		for (boolean[] i : arr) {
			System.arraycopy(i, 0, result, index, i.length);
			index += i.length;
		}
		return result;
	}

	public boolean[] flatMap(boolean[] arr, BooleanFunction<boolean[]> function) {
		return flattened(mapToObj(arr, function, boolean[][]::new));
	}

	public <T> T[] mapToObj(boolean[] arr, BooleanFunction<T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(boolean[] arr, ToIntFunction<Boolean> function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public long[] mapToLong(boolean[] arr, ToLongFunction<Boolean> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(boolean[] arr, ToDoubleFunction<Boolean> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public OptionalBoolean reduce(boolean[] arr, BooleanBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalBoolean.empty();
		boolean result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsBoolean(result, arr[i]);
		}
		return OptionalBoolean.of(result);
	}

	public boolean reduce(boolean[] arr, boolean identity, BooleanBinaryOperator accumulator) {
		boolean result = identity;
		for (boolean ele : arr) {
			result = accumulator.applyAsBoolean(result, ele);
		}
		return result;
	}

	public <R> R reduce(boolean[] arr, R identity, BiFunction<R,Boolean,R> accumulator) {
		R result = identity;
		for (boolean ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public boolean[] reverse(boolean[] arr) {
		boolean[] result = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public boolean[] distinct(boolean[] arr) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			if (!contains(tmp, ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		boolean[] result = new boolean[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public boolean[] distinctSorted(boolean[] arr) {
		boolean[] tmp = new boolean[arr.length];
		int j = 0;
		for (boolean ele : arr) {
			if (!contains(tmp, ele)) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
