package de.phoenixmitx.lombokextensions.codegen.utils;

import java.util.Arrays;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtils {
	
	// sadly we cant use ArrayExtensions in the CodeGen so we have to copy the addAll method here
	public <T> T[] merge(T[] arr, T[] c) {
		T[] newArr = Arrays.copyOf(arr, arr.length + c.length);
		System.arraycopy(c, 0, newArr, arr.length, c.length);
		return newArr;
	}
}
