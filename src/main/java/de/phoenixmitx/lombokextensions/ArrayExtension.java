package de.phoenixmitx.lombokextensions;

import de.phoenixmitx.lombokextensions.array.CharArrayExtension;
import de.phoenixmitx.lombokextensions.array.DoubleArrayExtension;
import de.phoenixmitx.lombokextensions.array.GenericArrayExtension;
import de.phoenixmitx.lombokextensions.array.IntArrayExtension;
import de.phoenixmitx.lombokextensions.array.LongArrayExtension;
import de.phoenixmitx.lombokextensions.codegen.delegate.StaticDelegate;
import lombok.experimental.UtilityClass;

@UtilityClass
@StaticDelegate({ GenericArrayExtension.class, IntArrayExtension.class, LongArrayExtension.class, DoubleArrayExtension.class, CharArrayExtension.class })
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
	 * T sum()
	 * Optional<T> last()
	 * 
	 * FloatArrayExtension, ShortArrayExtension, BooleanArrayExtension, ByteArrayExtension
	 * 
	 * update list of implemented methods
	 * write tests for all methods
	 * 
	 * add javadoc to explain the difference to the stream API
	 * add javadoc for each method
	 * add all methods from java.util.Arrays
	 * use java.util.Arrays to implement methods where possible
	 * test whats the fastes way to add elements into to an array
	 */

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
