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

	public Optional<Boolean> first(boolean[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[0]);
	}

	public Optional<Boolean> last(boolean[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[arr.length - 1]);
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

	public boolean[] removeIf(boolean[] arr, Predicate<Boolean> predicate) {
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

	public boolean[] retainIf(boolean[] arr, Predicate<Boolean> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public Optional<Boolean> find(boolean[] arr, Predicate<Boolean> condition) {
		for (boolean ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public int findIndex(boolean[] arr, Predicate<Boolean> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(boolean[] arr, Predicate<Boolean> condition) {
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

	public boolean[] filter(boolean[] arr, Predicate<Boolean> condition) {
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

	public void forEach(boolean[] arr, Consumer<Boolean> consumer) {
		for (boolean ele : arr) {
			consumer.accept(ele);
		}
	}

	public boolean[] map(boolean[] arr, UnaryOperator<Boolean> function) {
		boolean[] result = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public boolean[] filterMap(boolean[] arr, Function<Boolean, Optional<Boolean>> function) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			Optional<Boolean> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public boolean[] filterMap(boolean[] arr, Predicate<Boolean> condition, UnaryOperator<Boolean> function) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public boolean[] mapFilter(boolean[] arr, UnaryOperator<Boolean> function, Predicate<Boolean> condition) {
		boolean[] tmp = new boolean[arr.length];
		int i = 0;
		for (boolean ele : arr) {
			boolean mapped = function.apply(ele);
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

	public boolean[] flatMap(boolean[] arr, Function<Boolean,boolean[]> function) {
		return flattened(mapToObj(arr, function, boolean[][]::new));
	}

	public <T> T[] mapToObj(boolean[] arr, Function<Boolean, T> function, IntFunction<T[]> supplier) {
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

	public Optional<Boolean> reduce(boolean[] arr, BinaryOperator<Boolean> accumulator) {
		if (arr.length == 0) return Optional.empty();
		boolean result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public boolean reduce(boolean[] arr, boolean identity, BinaryOperator<Boolean> accumulator) {
		boolean result = identity;
		for (boolean ele : arr) {
			result = accumulator.apply(result, ele);
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
