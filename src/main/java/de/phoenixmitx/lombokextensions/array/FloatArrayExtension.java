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
public class FloatArrayExtension {
  
	public Float[] boxed(float[] arr) {
		Float[] result = new Float[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public float[] unboxed(Float[] arr) {
		float[] result = new float[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public Optional<Float> first(float[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[0]);
	}

	public Optional<Float> last(float[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[arr.length - 1]);
	}

	public float[] add(float[] arr, float c) {
		float[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = c;
		return result;
	}

	public float[] add(float[] arr, int index, float c) {
		float[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = c;
		return result;
	}

	public float[] addAll(float[] arr, float... floats) {
		float[] result = Arrays.copyOf(arr, arr.length + floats.length);
		System.arraycopy(floats, 0, result, arr.length, floats.length);
		return result;
	}

	public float[] addAll(float[] arr, int index, float... floats) {
		float[] result = Arrays.copyOf(arr, arr.length + floats.length);
		System.arraycopy(arr, index, result, index + floats.length, arr.length - index);
		System.arraycopy(floats, 0, result, index, floats.length);
		return result;
	}

	public float[] range(float[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public float[] removeRange(float[] arr, int start, int end) {
		float[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public float[] remove(float[] arr, float c) {
		int index = indexOf(arr, c);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public float[] removeIndex(float[] arr, int index) {
		float[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public float[] removeAll(float[] arr, float... floats) {
		for (int i = 0; i < floats.length; i++) {
			if (contains(arr, floats[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public float[] removeIf(float[] arr, Predicate<Float> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public float[] retainAll(float[] arr, float... floats) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(floats, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public float[] retainIf(float[] arr, Predicate<Float> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public Optional<Float> find(float[] arr, Predicate<Float> condition) {
		for (float ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public int findIndex(float[] arr, Predicate<Float> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(float[] arr, Predicate<Float> condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(float[] arr, float c) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public int lastIndexOf(float[] arr, float c) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public boolean contains(float[] arr, float c) {
		return indexOf(arr, c) != -1;
	}

	public boolean containsAll(float[] arr, float... floats) {
		for (float i : floats) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public boolean containsAny(float[] arr, float... floats) {
		for (float i : floats) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public float[] filter(float[] arr, Predicate<Float> condition) {
		float[] tmp = new float[arr.length];
		int i = 0;
		for (float ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		float[] result = new float[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public void forEach(float[] arr, Consumer<Float> consumer) {
		for (float ele : arr) {
			consumer.accept(ele);
		}
	}

	public float[] map(float[] arr, UnaryOperator<Float> function) {
		float[] result = new float[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public float[] filterMap(float[] arr, Function<Float, Optional<Float>> function) {
		float[] tmp = new float[arr.length];
		int i = 0;
		for (float ele : arr) {
			Optional<Float> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public float[] filterMap(float[] arr, Predicate<Float> condition, UnaryOperator<Float> function) {
		float[] tmp = new float[arr.length];
		int i = 0;
		for (float ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public float[] mapFilter(float[] arr, UnaryOperator<Float> function, Predicate<Float> condition) {
		float[] tmp = new float[arr.length];
		int i = 0;
		for (float ele : arr) {
			float mapped = function.apply(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public float[] flattened(float[][] arr) {
		int size = 0;
		for (float[] i : arr) {
			size += i.length;
		}
		float[] result = new float[size];
		int index = 0;
		for (float[] i : arr) {
			System.arraycopy(i, 0, result, index, i.length);
			index += i.length;
		}
		return result;
	}

	public float[] flatMap(float[] arr, Function<Float,float[]> function) {
		return flattened(mapToObj(arr, function, float[][]::new));
	}

	public <T> T[] mapToObj(float[] arr, Function<Float, T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(float[] arr, ToIntFunction<Float> function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public long[] mapToLong(float[] arr, ToLongFunction<Float> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(float[] arr, ToDoubleFunction<Float> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public Optional<Float> reduce(float[] arr, BinaryOperator<Float> accumulator) {
		if (arr.length == 0) return Optional.empty();
		float result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public float reduce(float[] arr, float identity, BinaryOperator<Float> accumulator) {
		float result = identity;
		for (float ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public <R> R reduce(float[] arr, R identity, BiFunction<R,Float,R> accumulator) {
		R result = identity;
		for (float ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public float[] reverse(float[] arr) {
		float[] result = new float[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public float[] distinct(float[] arr) {
		float[] tmp = new float[arr.length];
		int i = 0;
		for (float ele : arr) {
			if (!contains(tmp, ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		float[] result = new float[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public float[] distinctSorted(float[] arr) {
		float[] tmp = new float[arr.length];
		int j = 0;
		for (float ele : arr) {
			if (!contains(tmp, ele)) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
