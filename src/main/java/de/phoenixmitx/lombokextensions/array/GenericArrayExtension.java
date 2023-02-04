package de.phoenixmitx.lombokextensions.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
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
import java.util.stream.Stream;

public interface GenericArrayExtension {

  public static <T> List<T> toList(T[] arr) {
		return Arrays.asList(arr);
	}

	public static <T> List<T> toModifyableList(T[] arr) {
		return new ArrayList<>(toList(arr));
	}

	public static <T> Iterator<T> iterator(T[] arr) {
		return toList(arr).iterator();
	}

	public static <T> Spliterator<T> spliterator(T[] arr) {
		return toList(arr).spliterator();
	}

	public static <T> Stream<T> stream(T[] arr) {
		return Stream.of(arr);
	}

	public static <T> T[] add(T[] arr, T element) {
		return add(arr, arr.length, element);
	}

	public static <T> T[] add(T[] arr, int index, T element) {
		T[] newArr = Arrays.copyOf(arr, arr.length + 1);
		newArr[index] = element;
		return newArr;
	}

	public static <T> T[] addAll(T[] arr, T[] c) {
		T[] newArr = Arrays.copyOf(arr, arr.length + c.length);
		System.arraycopy(c, 0, newArr, arr.length, c.length);
		return newArr;
	}

	public static <T> T[] addAll(T[] arr, int index, T[] elements) {
		T[] result = Arrays.copyOf(arr, arr.length + elements.length);
		System.arraycopy(arr, index, result, index + elements.length, arr.length - index);
		System.arraycopy(elements, 0, result, index, elements.length);
		return result;
	}

	public static <T> T[] range(T[] arr, int fromIndex, int toIndex) {
		return Arrays.copyOfRange(arr, fromIndex, toIndex);
	}

	public static <T> T[] removeRange(T[] arr, int fromIndex, int toIndex) {
		T[] result = Arrays.copyOf(arr, arr.length - (toIndex - fromIndex));
		System.arraycopy(arr, toIndex, result, fromIndex, arr.length - toIndex);
		return result;
	}

	public static <T> T[] remove(T[] arr, T element) {
		int index = indexOf(arr, element);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public static <T> T[] removeIndex(T[] arr, int index) {
		T[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public static <T> T[] removeAll(T[] arr, T[] c) {
		for (int i = 0; i < c.length; i++) {
			arr = removeIndex(arr, i--);
		}
		return arr;
	}

	public static <T> T[] removeIf(T[] arr, Predicate<? super T> filter) {
		for (int i = 0; i < arr.length; i++) {
			if (filter.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static <T> T[] retainAll(T[] arr, T[] c) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(c, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static <T> T[] retainIf(T[] arr, Predicate<? super T> filter) {
		for (int i = 0; i < arr.length; i++) {
			if (!filter.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public static <T> Optional<T> find(T[] arr, Predicate<T> condition) {
		for (T ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public static <T> int findIndex(T[] arr, Predicate<T> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public static <T> int findLastIndex(T[] arr, Predicate<T> condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public static <T> int indexOf(T[] arr, T element) {
		for (int i = 0; i < arr.length; i++) {
			if (Objects.equals(arr[i], element)) return i;
		}
		return -1;
	}

	public static <T> int lastIndexOf(T[] arr, T element) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (Objects.equals(arr[i], element)) return i;
		}
		return -1;
	}

	public static <T> boolean contains(T[] arr, T element) {
		return indexOf(arr, element) != -1;
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean containsAll(T[] arr, T... elements) {
		for (T element : elements) {
			if (!contains(arr, element)) return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean containsAny(T[] arr, T... elements) {
		for (T element : elements) {
			if (contains(arr, element)) return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] filter(T[] arr, Predicate<T> condition) {
		T[] tmp = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
		int i = 0;
		for (T ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		T[] result = (T[]) Array.newInstance(arr.getClass().getComponentType(), i);
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public static <T> void forEach(T[] arr, Consumer<T> consumer) {
		for (T ele : arr) {
			consumer.accept(ele);
		}
	}

	public static <T,R> R[] map(T[] arr, Function<T,R> function, IntFunction<R[]> supplier) {
		R[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] map(T[] arr, UnaryOperator<T> function) {
		return map(arr, function, length -> (T[]) Array.newInstance(arr.getClass().getComponentType(), length));
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] filterMap(T[] arr, Function<T, Optional<T>> function) {
		T[] tmp = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
		int i = 0;
		for (T ele : arr) {
			Optional<T> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static <T> T[] filterMap(T[] arr, Predicate<T> condition, UnaryOperator<T> function) {
		T[] tmp = Arrays.copyOf(arr, arr.length);
		int i = 0;
		for (T ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public static <T> T[] mapFilter(T[] arr, UnaryOperator<T> function, Predicate<T> condition) {
		T[] tmp = Arrays.copyOf(arr, arr.length);
		int i = 0;
		for (T ele : arr) {
			T mapped = function.apply(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] flattened(T[][] arr) {
		int size = 0;
		for (T[] a : arr) {
			size += a.length;
		}
		T[] result = (T[]) Array.newInstance(arr.getClass().getComponentType().getComponentType(), size);
		int i = 0;
		for (T[] t : arr) {
			System.arraycopy(t, 0, result, i, t.length);
			i += t.length;
		}
		return result;
	}

	public static <T,R> R[] flatMap(T[] arr, Function<T,R[]> function, IntFunction<R[][]> supplier) {
		return flattened(map(arr, function, supplier));
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] flatMap(T[] arr, Function<T,T[]> function) {
		return flattened(map(arr, function, length -> (T[][]) Array.newInstance(arr.getClass(), length)));
	}

	public static <T> int[] mapToInt(T[] arr, ToIntFunction<T> function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public static <T> long[] mapToLong(T[] arr, ToLongFunction<T> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public static <T> double[] mapToDouble(T[] arr, ToDoubleFunction<T> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public static <T> Optional<T> reduce(T[] arr, BinaryOperator<T> accumulator) {
		if (arr.length == 0) return Optional.empty();
		T result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public static <T> T reduce(T[] arr, T identity, BinaryOperator<T> accumulator) {
		T result = identity;
		for (T ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public static <T, R> Optional<R> reduce(T[] arr, R identity, BiFunction<R,T,R> accumulator) {
		R result = identity;
		for (T ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return Optional.of(result);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] reverse(T[] arr) {
		T[] result = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] distinct(T[] arr) {
		Set<T> set = new HashSet<>();
		for (T ele : arr) {
			set.add(ele);
		}
		return set.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(), set.size()));
	}

	public static <T> T[] distinctSorted(T[] arr) {
		int j = 0;
		Arrays.sort(arr);
		for (int i = 0, n = arr.length; i < n; i++) {
			if (i == 0 || Objects.equals(arr[i], arr[i - 1])) {
				arr[j++] = arr[i];
			}
		}
		if (j == arr.length) return arr;
		return Arrays.copyOf(arr, j);
	}
  
}
