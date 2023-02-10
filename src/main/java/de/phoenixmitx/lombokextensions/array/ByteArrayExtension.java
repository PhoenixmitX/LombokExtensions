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
public class ByteArrayExtension {
  
	public Byte[] boxed(byte[] arr) {
		Byte[] result = new Byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public byte[] unboxed(Byte[] arr) {
		byte[] result = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		return result;
	}

	public Optional<Byte> first(byte[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[0]);
	}

	public Optional<Byte> last(byte[] arr) {
		if (arr.length == 0) return Optional.empty();
		return Optional.of(arr[arr.length - 1]);
	}

	public byte[] add(byte[] arr, byte c) {
		byte[] result = Arrays.copyOf(arr, arr.length + 1);
		result[arr.length] = c;
		return result;
	}

	public byte[] add(byte[] arr, int index, byte c) {
		byte[] result = Arrays.copyOf(arr, arr.length + 1);
		System.arraycopy(arr, index, result, index + 1, arr.length - index);
		result[index] = c;
		return result;
	}

	public byte[] addAll(byte[] arr, byte... bytes) {
		byte[] result = Arrays.copyOf(arr, arr.length + bytes.length);
		System.arraycopy(bytes, 0, result, arr.length, bytes.length);
		return result;
	}

	public byte[] addAll(byte[] arr, int index, byte... bytes) {
		byte[] result = Arrays.copyOf(arr, arr.length + bytes.length);
		System.arraycopy(arr, index, result, index + bytes.length, arr.length - index);
		System.arraycopy(bytes, 0, result, index, bytes.length);
		return result;
	}

	public byte[] range(byte[] arr, int start, int end) {
		return Arrays.copyOfRange(arr, start, end);
	}

	public byte[] removeRange(byte[] arr, int start, int end) {
		byte[] result = Arrays.copyOf(arr, arr.length - (end - start));
		System.arraycopy(arr, end, result, start, arr.length - end);
		return result;
	}

	public byte[] remove(byte[] arr, byte c) {
		int index = indexOf(arr, c);
		if (index == -1) return arr;
		return removeIndex(arr, index);
	}

	public byte[] removeIndex(byte[] arr, int index) {
		byte[] result = Arrays.copyOf(arr, arr.length - 1);
		System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
		return result;
	}

	public byte[] removeAll(byte[] arr, byte... bytes) {
		for (int i = 0; i < bytes.length; i++) {
			if (contains(arr, bytes[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public byte[] removeIf(byte[] arr, Predicate<Byte> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public byte[] retainAll(byte[] arr, byte... bytes) {
		for (int i = 0; i < arr.length; i++) {
			if (!contains(bytes, arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public byte[] retainIf(byte[] arr, Predicate<Byte> predicate) {
		for (int i = 0; i < arr.length; i++) {
			if (!predicate.test(arr[i])) {
				arr = removeIndex(arr, i--);
			}
		}
		return arr;
	}

	public Optional<Byte> find(byte[] arr, Predicate<Byte> condition) {
		for (byte ele : arr) {
			if (condition.test(ele)) return Optional.of(ele);
		}
		return Optional.empty();
	}

	public int findIndex(byte[] arr, Predicate<Byte> condition) {
		for (int i = 0; i < arr.length; i++) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int findLastIndex(byte[] arr, Predicate<Byte> condition) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (condition.test(arr[i])) return i;
		}
		return -1;
	}

	public int indexOf(byte[] arr, byte c) {
		for (int index = 0; index < arr.length; index++) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public int lastIndexOf(byte[] arr, byte c) {
		for (int index = arr.length - 1; index >= 0; index--) {
			if (arr[index] == c) return index;
		}
		return -1;
	}

	public boolean contains(byte[] arr, byte c) {
		return indexOf(arr, c) != -1;
	}

	public boolean containsAll(byte[] arr, byte... bytes) {
		for (byte i : bytes) {
			if (!contains(arr, i)) return false;
		}
		return true;
	}

	public boolean containsAny(byte[] arr, byte... bytes) {
		for (byte i : bytes) {
			if (contains(arr, i)) return true;
		}
		return false;
	}

	public byte[] filter(byte[] arr, Predicate<Byte> condition) {
		byte[] tmp = new byte[arr.length];
		int i = 0;
		for (byte ele : arr) {
			if (condition.test(ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		byte[] result = new byte[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public void forEach(byte[] arr, Consumer<Byte> consumer) {
		for (byte ele : arr) {
			consumer.accept(ele);
		}
	}

	public byte[] map(byte[] arr, UnaryOperator<Byte> function) {
		byte[] result = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public byte[] filterMap(byte[] arr, Function<Byte, Optional<Byte>> function) {
		byte[] tmp = new byte[arr.length];
		int i = 0;
		for (byte ele : arr) {
			Optional<Byte> optional = function.apply(ele);
			if (optional.isPresent()) tmp[i++] = optional.get();
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public byte[] filterMap(byte[] arr, Predicate<Byte> condition, UnaryOperator<Byte> function) {
		byte[] tmp = new byte[arr.length];
		int i = 0;
		for (byte ele : arr) {
			if (condition.test(ele)) tmp[i++] = function.apply(ele);
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public byte[] mapFilter(byte[] arr, UnaryOperator<Byte> function, Predicate<Byte> condition) {
		byte[] tmp = new byte[arr.length];
		int i = 0;
		for (byte ele : arr) {
			byte mapped = function.apply(ele);
			if (condition.test(mapped)) tmp[i++] = mapped;
		}
		if (tmp.length == i) return tmp;
		return Arrays.copyOf(tmp, i);
	}

	public byte[] flattened(byte[][] arr) {
		int size = 0;
		for (byte[] i : arr) {
			size += i.length;
		}
		byte[] result = new byte[size];
		int index = 0;
		for (byte[] i : arr) {
			System.arraycopy(i, 0, result, index, i.length);
			index += i.length;
		}
		return result;
	}

	public byte[] flatMap(byte[] arr, Function<Byte,byte[]> function) {
		return flattened(mapToObj(arr, function, byte[][]::new));
	}

	public <T> T[] mapToObj(byte[] arr, Function<Byte, T> function, IntFunction<T[]> supplier) {
		T[] result = supplier.apply(arr.length);
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.apply(arr[i]);
		}
		return result;
	}

	public int[] mapToInt(byte[] arr, ToIntFunction<Byte> function) {
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsInt(arr[i]);
		}
		return result;
	}

	public long[] mapToLong(byte[] arr, ToLongFunction<Byte> function) {
		long[] result = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsLong(arr[i]);
		}
		return result;
	}

	public double[] mapToDouble(byte[] arr, ToDoubleFunction<Byte> function) {
		double[] result = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = function.applyAsDouble(arr[i]);
		}
		return result;
	}

	public Optional<Byte> reduce(byte[] arr, BinaryOperator<Byte> accumulator) {
		if (arr.length == 0) return Optional.empty();
		byte result = arr[0];
		for (int i = 1; i < arr.length; i++) {
			result = accumulator.apply(result, arr[i]);
		}
		return Optional.of(result);
	}

	public byte reduce(byte[] arr, byte identity, BinaryOperator<Byte> accumulator) {
		byte result = identity;
		for (byte ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public <R> R reduce(byte[] arr, R identity, BiFunction<R,Byte,R> accumulator) {
		R result = identity;
		for (byte ele : arr) {
			result = accumulator.apply(result, ele);
		}
		return result;
	}

	public byte[] reverse(byte[] arr) {
		byte[] result = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[arr.length - i - 1];
		}
		return result;
	}

	public byte[] distinct(byte[] arr) {
		byte[] tmp = new byte[arr.length];
		int i = 0;
		for (byte ele : arr) {
			if (!contains(tmp, ele)) tmp[i++] = ele;
		}
		if (tmp.length == i) return tmp;
		byte[] result = new byte[i];
		System.arraycopy(tmp, 0, result, 0, i);
		return result;
	}

	public byte[] distinctSorted(byte[] arr) {
		byte[] tmp = new byte[arr.length];
		int j = 0;
		for (byte ele : arr) {
			if (!contains(tmp, ele)) tmp[j++] = ele;
		}
		if (tmp.length == j) return tmp;
		return Arrays.copyOf(tmp, j);
	}
}
