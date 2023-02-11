package de.phoenixmitx.lombokextensions.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.function.Function;

import lombok.SneakyThrows;
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

  // https://stackoverflow.com/a/22171963/13908458
  @SneakyThrows
  public Class<?> getArrayType(Class<?> componentType) {
    ClassLoader classLoader = componentType.getClassLoader();
    String name;
    if (componentType.isArray()) {
      // just add a leading "["
      name = "["+componentType.getName();
    } else if (componentType == boolean.class) {
      name = "[Z";
    } else if (componentType == byte.class) {
      name = "[B";
    } else if (componentType == char.class) {
      name = "[C";
    } else if (componentType == double.class) {
      name = "[D";
    } else if (componentType == float.class) {
      name = "[F";
    } else if (componentType == int.class) {
      name = "[I";
    } else if (componentType == long.class) {
      name = "[J";
    } else if (componentType == short.class) {
      name = "[S";
    } else {
      // must be an object non-array class
      name = "[L"+componentType.getName()+";";
    }
    return classLoader != null ? classLoader.loadClass(name) : Class.forName(name);
  }
}
