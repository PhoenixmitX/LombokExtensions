package de.phoenixmitx.lombokextensions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

  public <T, R> void testArrayMethod(Function<T[], R> function, T[] arr, R expected) {
    T[] original = Arrays.copyOf(arr, arr.length);
    R result = function.apply(arr);
    if (!Arrays.equals(original, arr)) {
      throw new AssertionError("Original array was modified");
    }
    if (result == arr) {
      throw new AssertionError("Original array was returned");
    }
    assertEquals(result, expected);
  }
}
