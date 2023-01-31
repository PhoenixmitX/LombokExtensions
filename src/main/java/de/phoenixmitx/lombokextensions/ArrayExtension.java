package de.phoenixmitx.lombokextensions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayExtension {

	/*
	 * Implemeted:
	 * 
	 * [OBJ] List<T> toList()
	 * [OBJ] List<T> toModifyableList()
	 * 
	 * [OBJ] Iterator<T> iterator()
	 * [OBJ] Spliterator<T> spliterator()
	 * Stream<T> stream()
	 * 
	 * [PRIMITIVE] boxed()
	 * [PRIMITIVE] unboxed()
	 * 
	 * T[] add(T element)
	 * T[] add(int index, T element)
	 * T[] addAll(t[] c)
	 * T[] addAll(int index, t[] c)
	 * 
	 * T[] range(int fromIndex, int toIndex)
	 * T[] removeRange(int fromIndex, int toIndex)
	 * 
	 * T[] remove(T element)
	 * T[] removeIndex(int index)
	 * T[] removeAll(t[] c)
	 * T[] removeIf(Predicate<? super T> filter)
	 * T[] retainAll(t[] c)
	 * T[] retainIf(Predicate<? super T> filter)
	 * 
	 * Optional<T> find(Predicate<T> condition)
	 * int findIndex(Predicate<T> condition)
	 * int findLastIndex(Predicate<T> condition)
	 * int indexOf(T obj)
	 * int lastIndexOf(T obj)
	 * 
	 * boolean contains(T obj)
	 * boolean containsAll(T... objs)
	 * boolean containsAny(T... objs)
	 * 
	 * T[] filter(Predicate<T> condition)
	 * 
	 * [OBJ] R[] map(Function<T,R> fuction, IntFunction<R[]> supplier)
	 * T[] map(UnaryOperator<T> function)
	 * T[] flattened()
	 * T[] flatMap(Function<T,T[]> function)
	 * [OBJ] R[] flatMap(Function<T,R> function, IntFunction<R[]> supplier)
	 * int[] mapToInt(ToIntFunction<T> function)
	 * long[] mapToLong(ToLongFunction<T> function)
	 * double[] mapToDouble(ToDoubleFunction<T> function)
	 * 
	 * Optional<T> reduce(BinaryOperator<T> accumulator)
	 * T reduce(T identity, BinaryOperator<T> accumulator) // TODO is this redundant?
	 * R reduce(R identity, BiFunction<R,T,R> accumulator)
	 * 
	 * T[] reverse()
	 * 
	 * TODO:
	 * 
	 * add javadoc to explain the difference to the stream API
	 * add javadoc for each method
	 * add all methods from java.util.Arrays
	 * use java.util.Arrays to implement methods where possible
	 * test whats the fastes way to add elements into to an array
	 */

	// GENERIC ARRAYS

	public <T> List<T> toList(T[] arr) {
		return Arrays.asList(arr);
	}

	public <T> List<T> toModifyableList(T[] arr) {
		return new ArrayList<>(toList(arr));
	}

	public <T> Iterator<T> iterator(T[] arr) {
		return toList(arr).iterator();
	}

	public <T> Spliterator<T> spliterator(T[] arr) {
		return toList(arr).spliterator();
	}

	public <T> Stream<T> stream(T[] arr) {
		return Stream.of(arr);
	}

	public <T> T[] add(T[] arr, T element) {
		return add(arr, arr.length, element);
	}

	public <T> T[] add(T[] arr, int index, T element) {
		T[] newArr = Arrays.copyOf(arr, arr.length + 1);
		newArr[index] = element;
		return newArr;
	}

	public <T> T[] addAll(T[] arr, T[] c) {
		T[] newArr = Arrays.copyOf(arr, arr.length + c.length);
		System.arraycopy(c, 0, newArr, arr.length, c.length);
		return newArr;
	}

	public <T> T[] addAll(T[] arr, int index, T[] elements) {
		T[] result = Arrays.copyOf(arr, arr.length + elements.length);
		System.arraycopy(arr, index, result, index + elements.length, arr.length - index);
		System.arraycopy(elements, 0, result, index, elements.length);
		return result;
	}

	public <T> T[] range(T[] arr, int fromIndex, int toIndex) {
		return Arrays.copyOfRange(arr, fromIndex, toIndex);
	}

	public <T> T[] removeRange(T[] arr, int fromIndex, int toIndex) {
		T[] result = Arrays.copyOf(arr, arr.length - (toIndex - fromIndex));
		System.arraycopy(arr, toIndex, result, fromIndex, arr.length - toIndex);
		return result;
	}

	public <T> T[] remove(T[] arr, T element) {
		int index = indexOf(arr, element);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public <T> T[] removeIndex(T[] arr, int index) {
		T[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public <T> T[] removeAll(T[] arr, T[] c) {
		for (int i = 0; i < c.length; i++) {
			arr = removeIndex(arr, i--);
		}
		return arr;
	}

	public <T> T[] removeIf(T[] arr, Predicate<? super T> filter) {
		for (int i = 0; i < arr.length; i++) {
			if (filter.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public <T> T[] retainAll(T[] arr, T[] c) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(c, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public <T> T[] retainIf(T[] arr, Predicate<? super T> filter) {
		for (int i = 0; i < arr.length; i++) {
			if (!filter.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public <T> Optional<T> find(T[] arr, Predicate<T> condition) {
		for (T ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public <T> int findIndex(T[] arr, Predicate<T> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public <T> int findLastIndex(T[] arr, Predicate<T> condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public <T> int indexOf(T[] arr, T element) {
		for (int i = 0; i < arr.length; i++) {
			if (Objects.equals(arr[i], element)) return i;
		}
		return -1;
	}

	public <T> int lastIndexOf(T[] arr, T element) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (Objects.equals(arr[i], element)) return i;
		}
		return -1;
	}

	public <T> boolean contains(T[] arr, T element) {
		return indexOf(arr, element) != -1;
	}

	@SuppressWarnings("unchecked")
	public <T> boolean containsAll(T[] arr, T... elements) {
		for (T element : elements) {
			if (!contains(arr, element)) return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public <T> boolean containsAny(T[] arr, T... elements) {
		for (T element : elements) {
			if (contains(arr, element)) return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] filter(T[] arr, Predicate<T> condition) {
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

	public <T> void forEach(T[] arr, Consumer<T> consumer) {
		for (T ele : arr) {
			consumer.accept(ele);
		}
	}

	public <T,R> R[] map(T[] arr, Function<T,R> function, IntFunction<R[]> supplier) {
		R[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] map(T[] arr, UnaryOperator<T> function) {
		return map(arr, function, length -> (T[]) Array.newInstance(arr.getClass().getComponentType(), length));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] filterMap(T[] arr, Function<T, Optional<T>> function) {
		T[] tmp = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
		int i = 0;
		for (T ele : arr) {
			Optional<T> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public <T> T[] filterMap(T[] arr, Predicate<T> condition, UnaryOperator<T> function) {
		T[] tmp = Arrays.copyOf(arr, arr.length);
		int i = 0;
		for (T ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public <T> T[] mapFilter(T[] arr, UnaryOperator<T> function, Predicate<T> condition) {
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
	public <T> T[] flattened(T[][] arr) {
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

	public <T,R> R[] flatMap(T[] arr, Function<T,R[]> function, IntFunction<R[][]> supplier) {
		return flattened(map(arr, function, supplier));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] flatMap(T[] arr, Function<T,T[]> function) {
		return flattened(map(arr, function, length -> (T[][]) Array.newInstance(arr.getClass(), length)));
	}

	public <T> int[] mapToInt(T[] arr, ToIntFunction<T> function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public <T> long[] mapToLong(T[] arr, ToLongFunction<T> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public <T> double[] mapToDouble(T[] arr, ToDoubleFunction<T> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public <T> Optional<T> reduce(T[] arr, BinaryOperator<T> accumulator) {
		if (arr.length == 0) return Optional.empty();
		T result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public <T> T reduce(T[] arr, T identity, BinaryOperator<T> accumulator) {
		T result = identity;
		for (T ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public <T, R> Optional<R> reduce(T[] arr, R identity, BiFunction<R,T,R> accumulator) {
		R result = identity;
		for (T ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return Optional.of(result);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] reverse(T[] arr) {
		T[] result = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] distinct(T[] arr) {
		Set<T> set = new HashSet<>();
		for (T ele : arr) {
			set.add(ele);
		}
		return set.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(), set.size()));
	}

	public <T> T[] distinctSorted(T[] arr) {
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

	// INT ARRAYS

	public IntStream stream(int[] arr) {
		return IntStream.of(arr);
	}

	public Integer[] boxed(int[] arr) {
		Integer[] result = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public int[] unboxed(Integer[] arr) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public int[] add(int[] arr, int i) {
		int[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = i;
		return result;
	}

	public int[] add(int[] arr, int index, int i) {
		int[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = i;
		return result;
	}

	public int[] addAll(int[] arr, int... ints) {
		int[] result = Arrays.copyOf(arr, arr.length + ints.length);
		System.arraycopy(ints, 0, result, arr.length, ints.length);
		return result;
	}

	public int[] addAll(int[] arr, int index, int... ints) {
		int[] result = Arrays.copyOf(arr, arr.length + ints.length);
		System.arraycopy(arr, index, result, index + ints.length, arr.length - index);
		System.arraycopy(ints, 0, result, index, ints.length);
		return result;
	}

	public int[] range(int[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public int[] removeRange(int[] arr, int start, int end) {
		int[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public int[] remove(int[] arr, int i) {
		int index = indexOf(arr, i);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public int[] removeIndex(int[] arr, int index) {
		int[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public int[] removeAll(int[] arr, int... ints) {
		for (int i = 0; i < ints.length; i++) {
			if (contains(arr, ints[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public int[] removeIf(int[] arr, IntPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public int[] retainAll(int[] arr, int... ints) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(ints, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public int[] retainIf(int[] arr, IntPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public OptionalInt find(int[] arr, IntPredicate condition) {
		for (int ele : arr) {
			if (condition.test(ele)) return OptionalInt.of(ele);
		}
		return OptionalInt.empty();
	}

	public int findIndex(int[] arr, IntPredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(int[] arr, IntPredicate condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(int[] arr, int i) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == i) return index;
		}
		return -1;
	}

	public int lastIndexOf(int[] arr, int i) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == i) return index;
		}
		return -1;
	}

	public boolean contains(int[] arr, int i) {
		return indexOf(arr, i) != -1;
	}

	public boolean containsAll(int[] arr, int... ints) {
		for (int i : ints) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public boolean containsAny(int[] arr, int... ints) {
		for (int i : ints) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public int[] filter(int[] arr, IntPredicate condition) {
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

	public void forEach(int[] arr, IntConsumer consumer) {
		for (int ele : arr) {
			consumer.accept(ele);
		}
	}

	public int[] map(int[] arr, IntUnaryOperator function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public int[] filterMap(int[] arr, IntFunction<OptionalInt> function) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			OptionalInt optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsInt();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public int[] filterMap(int[] arr, IntPredicate condition, IntUnaryOperator function) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsInt(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public int[] mapFilter(int[] arr, IntUnaryOperator function, IntPredicate condition) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			int mapped = function.applyAsInt(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public int[] flattened(int[][] arr) {
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

	public int[] flatMap(int[] arr, IntFunction<int[]> function) {
		return flattened(mapToObj(arr, function, int[][]::new));
	}

	public <T> T[] mapToObj(int[] arr, IntFunction<T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public long[] mapToLong(int[] arr, IntToLongFunction function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(int[] arr, IntToDoubleFunction function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public OptionalInt reduce(int[] arr, IntBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalInt.empty();
		int result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsInt(result, arr[i]);
		}
		return OptionalInt.of(result);
	}

	public int reduce(int[] arr, int identity, IntBinaryOperator accumulator) {
		int result = identity;
		for (int ele : arr) {
			result = accumulator.applyAsInt(result, ele);
		}
		return result;
	}

	public <R> R reduce(int[] arr, R identity, BiFunction<R,Integer,R> accumulator) {
		R result = identity;
		for (int ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public int[] reverse(int[] arr) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public int[] distinct(int[] arr) {
		int[] tmp = new int[arr.length];
		int i = 0;
		for (int ele : arr) {
			if (indexOf(tmp, ele) == -1) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public int[] distinctSorted(int[] arr) {
		int[] tmp = new int[arr.length];
		int j = 0;
		for (int i = 0, n = arr.length; i < n; i++) {
			if (i == 0 || arr[i] != arr[i - 1]) {
					arr[j++] = arr[i];
			}
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(arr, j);
	}

	// LONG ARRAYS

	public LongStream stream(long[] arr) {
		return LongStream.of(arr);
	}

	public Long[] boxed(long[] arr) {
		Long[] result = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public long[] unboxed(Long[] arr) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public long[] add(long[] arr, long l) {
		long[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = l;
		return result;
	}

	public long[] add(long[] arr, int index, long l) {
		long[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = l;
		return result;
	}

	public long[] addAll(long[] arr, long... longs) {
		long[] result = Arrays.copyOf(arr, arr.length + longs.length);
		System.arraycopy(longs, 0, result, arr.length, longs.length);
		return result;
	}

	public long[] addAll(long[] arr, int index, long... longs) {
		long[] result = Arrays.copyOf(arr, arr.length + longs.length);
		System.arraycopy(arr, index, result, index + longs.length, arr.length - index);
		System.arraycopy(longs, 0, result, index, longs.length);
		return result;
	}

	public long[] range(long[] arr, int from, int to) {
		long[] result = new long[to - from];
		System.arraycopy(arr, from, result, 0, to - from);
		return result;
	}

	public long[] removeRange(long[] arr, int from, int to) {
		long[] result = new long[arr.length - (to - from)];
		System.arraycopy(arr, 0, result, 0, from);
		System.arraycopy(arr, to, result, from, arr.length - to);
		return result;
	}

	public long[] remove(long[] arr, long l) {
		int index = indexOf(arr, l);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public long[] removeIndex(long[] arr, int index) {
		long[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public long[] removeAll(long[] arr, long... longs) {
		for (int i = 0; i < longs.length; i++) {
			arr = remove(arr, longs[i--]);
		}
		return arr;
	}

	public long[] removeIf(long[] arr, LongPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public long[] retainAll(long[] arr, long... longs) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(longs, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public long[] retainIf(long[] arr, LongPredicate predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public OptionalLong find(long[] arr, LongPredicate condition) {
		for (long ele : arr) {
			if (condition.test(ele)) return OptionalLong.of(ele);
		}
		return OptionalLong.empty();
	}

	public int findIndex(long[] arr, LongPredicate condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(long[] arr, LongPredicate condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(long[] arr, long l) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == l) return index;
		}
		return -1;
	}

	public int lastIndexOf(long[] arr, long l) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == l) return index;
		}
		return -1;
	}

	public boolean contains(long[] arr, long l) {
		return indexOf(arr, l) != -1;
	}

	public boolean containsAll(long[] arr, long... longs) {
		for (long l : longs) {
			if (!contains(arr, l)) return false;
		}
		return true;
	}

	public boolean containsAny(long[] arr, long... longs) {
		for (long l : longs) {
			if (contains(arr, l)) return true;
		}
		return false;
	}

	public long[] filter(long[] arr, LongPredicate condition) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		long[] result = new long[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public void forEach(long[] arr, LongConsumer consumer) {
		for (long ele : arr) {
			consumer.accept(ele);
		}
	}

	public long[] map(long[] arr, LongUnaryOperator function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public long[] filterMap(long[] arr, LongFunction<OptionalLong> function) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			OptionalLong optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.getAsLong();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public long[] filterMap(long[] arr, LongPredicate condition, LongUnaryOperator function) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.applyAsLong(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public long[] mapFilter(long[] arr, LongUnaryOperator function, LongPredicate condition) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			long mapped = function.applyAsLong(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public long[] flattened(long[][] arr) {
		int size = 0;
		for (long[] a : arr) {
			size += a.length;
		}
		long[] result = new long[size];
		int index = 0;
		for (long[] a : arr) {
			System.arraycopy(a, 0, result, index, a.length);
			index += a.length;
		}
		return result;
	}

	public long[] flatMap(long[] arr, LongFunction<long[]> function) {
		return flattened(mapToObj(arr, function, long[][]::new));
	}

	public <T> T[] mapToObj(long[] arr, LongFunction<T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(long[] arr, LongToIntFunction function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(long[] arr, LongToDoubleFunction function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public OptionalLong reduce(long[] arr, LongBinaryOperator accumulator) {
		if (arr.length == 0) return OptionalLong.empty();
		long result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.applyAsLong(result, arr[i]);
		}
		return OptionalLong.of(result);
	}

	public long reduce(long[] arr, long identity, LongBinaryOperator accumulator) {
		long result = identity;
		for (long ele : arr) {
			result = accumulator.applyAsLong(result, ele);
		}
		return result;
	}

	public <R> R reduce(long[] arr, R identity, BiFunction<R,Long,R> accumulator) {
		R result = identity;
		for (long ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public long[] reverse(long[] arr) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public long[] distinct(long[] arr) {
		long[] tmp = new long[arr.length];
		int i = 0;
		for (long ele : arr) {
			if (indexOf(tmp, ele) == -1) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		long[] result = new long[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public long[] distinctSorted(long[] arr) {
		long[] tmp = new long[arr.length];
		int j = 0;
		for (long ele : arr) {
			if (Arrays.binarySearch(tmp, 0, j, ele) < 0) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}

	// DOUBLE ARRAYS

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

	public double[] removeIndex(double[] arr, int index) {
		double[] result = new double[arr.length - 1];
		System.arraycopy(arr, 0, result, 0, index);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public double[] remove(double[] arr, double d) {
		int index = indexOf(arr, d);
		if (index == -1) return arr;
		return removeIndex(arr, index);
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
			if (indexOf(tmp, ele) == -1) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		double[] result = new double[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public double[] distinctSorted(double[] arr) {
		double[] tmp = new double[arr.length];
		int j = 0;
		for (double ele : arr) {
			if (Arrays.binarySearch(tmp, 0, j, ele) < 0) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}

	// CHAR

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

	public char[] add(char[] arr, char c) {
		char[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = c;
		return result;
	}

	public char[] add(char[] arr, char index, char c) {
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

	public char[] removeIf(char[] arr, Predicate<Character> predicate) {
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

	public char[] retainIf(char[] arr, Predicate<Character> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public Optional<Character> find(char[] arr, Predicate<Character> condition) {
		for (char ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public int findIndex(char[] arr, Predicate<Character> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(char[] arr, Predicate<Character> condition) {
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

	public char[] filter(char[] arr, Predicate<Character> condition) {
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

	public void forEach(char[] arr, Consumer<Character> consumer) {
		for (char ele : arr) {
			consumer.accept(ele);
		}
	}

	public char[] map(char[] arr, UnaryOperator<Character> function) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public char[] filterMap(char[] arr, Function<Character, Optional<Character>> function) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			Optional<Character> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public char[] filterMap(char[] arr, Predicate<Character> condition, UnaryOperator<Character> function) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public char[] mapFilter(char[] arr, UnaryOperator<Character> function, Predicate<Character> condition) {
		char[] tmp = new char[arr.length];
		int i = 0;
		for (char ele : arr) {
			char mapped = function.apply(ele);
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

	public char[] flatMap(char[] arr, Function<Character,char[]> function) {
		return flattened(mapToObj(arr, function, char[][]::new));
	}

	public <T> T[] mapToObj(char[] arr, Function<Character, T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
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

	public Optional<Character> reduce(char[] arr, BinaryOperator<Character> accumulator) {
		if (arr.length == 0) return Optional.empty();
		char result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public char reduce(char[] arr, char identity, BinaryOperator<Character> accumulator) {
		char result = identity;
		for (char ele : arr) {
			result = accumulator.apply(result, ele);
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

	// CLASS SPECIFIC ARRAYS

	public String join(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (String ele : arr) {
			sb.append(ele);
		}
		return sb.toString();
	}

	public String join(String[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length - 1) sb.append(separator);
		}
		return sb.toString();
	}

	public String join(String[] arr, String separator, String prefix, String suffix) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length - 1) sb.append(separator);
		}
		sb.append(suffix);
		return sb.toString();
	}
}
