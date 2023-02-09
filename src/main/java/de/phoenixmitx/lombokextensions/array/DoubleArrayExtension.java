package de.phoenixmitx.lombokextensions.array;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.stream.DoubleStream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DoubleArrayExtension {
  
  public DoubleStream stream(double[] arr) {
		return DoubleStream.of(arr);
	}

	public Double[] boxed(double[] arr) {
		Double[] result = new Double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public double[] unboxed(Double[] arr) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public OptionalDouble first(double[] arr) {
		if (arr.length == 0) return OptionalDouble.empty();
		return OptionalDouble.of(arr[0]);
	}

	public OptionalDouble last(double[] arr) {
		if (arr.length == 0) return OptionalDouble.empty();
		return OptionalDouble.of(arr[arr.length - 1]);
	}

	public double[] add(double[] arr, double d) {
		double[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = d;
		return result;
	}

	public double[] add(double[] arr, int index, double d) {
		double[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = d;
		return result;
	}

	public double[] addAll(double[] arr, double... doubles) {
		double[] result = Arrays.copyOf(arr, arr.length + doubles.length);
		System.arraycopy(doubles, 0, result, arr.length, doubles.length);
		return result;
	}

	public double[] addAll(double[] arr, int index, double... doubles) {
		double[] result = Arrays.copyOf(arr, arr.length + doubles.length);
		System.arraycopy(arr, index, result, index + doubles.length, arr.length - index);
		System.arraycopy(doubles, 0, result, index, doubles.length);
		return result;
	}

	public double[] range(double[] arr, int from, int to) {
		double[] result = new double[to - from];
		System.arraycopy(arr, from, result, 0, to - from);
		return result;
	}

	public double[] removeRange(double[] arr, int from, int to) {
		double[] result = new double[arr.length - (to - from)];
		System.arraycopy(arr, 0, result, 0, from);
		System.arraycopy(arr, to, result, from, arr.length - to);
		return result;
	}

	public double[] remove(double[] arr, double d) {
		int index = indexOf(arr, d);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public double[] removeIndex(double[] arr, int index) {
		double[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public double[] removeAll(double[] arr, double... doubles) {
		for (int i = 0; i < arr.length; i++) {
			if (contains(doubles, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public double[] removeIf(double[] arr, DoublePredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public double[] retainAll(double[] arr, double... doubles) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(doubles, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public double[] retainIf(double[] arr, DoublePredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public OptionalDouble find(double[] arr, DoublePredicate condition) {
		for (double ele : arr) {
			if (condition.test(ele)) return OptionalDouble.of(ele);
		}
		return OptionalDouble.empty();
	}

	public int findIndex(double[] arr, DoublePredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(double[] arr, DoublePredicate condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(double[] arr, double d) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == d) return index;
		}
		return -1;
	}

	public int lastIndexOf(double[] arr, double d) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == d) return index;
		}
		return -1;
	}

	public boolean contains(double[] arr, double d) {
		return indexOf(arr, d) != -1;
	}

	public boolean containsAll(double[] arr, double[] doubles) {
		for (double d : doubles) {
			if (!contains(arr, d)) return false;
		}
		return true;
	}

	public boolean containsAny(double[] arr, double... doubles) {
		for (double d : doubles) {
			if (contains(arr, d)) return true;
		}
		return false;
	}

	public double[] filter(double[] arr, DoublePredicate condition) {
		double[] tmp = new double[arr.length];
		int i = 0;
		for (double ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		double[] result = new double[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public void forEach(double[] arr, DoubleConsumer consumer) {
		for (double ele : arr) {
			consumer.accept(ele);
		}
	}

	public double[] map(double[] arr, DoubleUnaryOperator function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public double[] filterMap(double[] arr, DoubleFunction<OptionalDouble> function) {
		double[] tmp = new double[arr.length];
		int i = 0;
		for (double ele : arr) {
			OptionalDouble optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsDouble();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public double[] filterMap(double[] arr, DoublePredicate condition, DoubleUnaryOperator function) {
		double[] tmp = new double[arr.length];
		int i = 0;
		for (double ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsDouble(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public double[] mapFilter(double[] arr, DoubleUnaryOperator function, DoublePredicate condition) {
		double[] tmp = new double[arr.length];
		int i = 0;
		for (double ele : arr) {
			double mapped = function.applyAsDouble(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public double[] flattened(double[][] arr) {
		int size = 0;
		for (double[] a : arr) {
			size += a.length;
		}
		double[] result = new double[size];
		int i = 0;
		for (double[] a : arr) {
			System.arraycopy(a, 0, result, i, a.length);
			i += a.length;
		}
		return result;
	}

	public double[] flatMap(double[] arr, DoubleFunction<double[]> function) {
		return flattened(mapToObj(arr, function, double[][]::new));
	}

	public <T> T[] mapToObj(double[] arr, DoubleFunction<T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(double[] arr, DoubleToIntFunction function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public long[] mapToLong(double[] arr, DoubleToLongFunction function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public OptionalDouble reduce(double[] arr, DoubleBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalDouble.empty();
		double result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsDouble(result, arr[i]);
		}
		return OptionalDouble.of(result);
	}

	public double reduce(double[] arr, double identity, DoubleBinaryOperator accumulator) {
		double result = identity;
		for (double ele : arr) {
			result = accumulator.applyAsDouble(result, ele);
		}
		return result;
	}

	public <R> R reduce(double[] arr, R identity, BiFunction<R,Double,R> accumulator) {
		R result = identity;
		for (double ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public double sum(double[] arr) {
		double result = 0;
		for (double ele : arr) {
			result += ele;
		}
		return result;
	}

	public double[] reverse(double[] arr) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public double[] distinct(double[] arr) {
		double[] tmp = new double[arr.length];
		int i = 0;
		for (double ele : arr) {
			if (!contains(tmp, ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public double[] distinctSorted(double[] arr) {
		double[] tmp = new double[arr.length];
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
