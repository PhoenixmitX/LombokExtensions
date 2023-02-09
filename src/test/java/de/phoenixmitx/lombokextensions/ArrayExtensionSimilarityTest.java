package de.phoenixmitx.lombokextensions;

import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

import de.phoenixmitx.lombokextensions.array.CharArrayExtension;
import de.phoenixmitx.lombokextensions.array.DoubleArrayExtension;
import de.phoenixmitx.lombokextensions.array.GenericArrayExtension;
import de.phoenixmitx.lombokextensions.array.IntArrayExtension;
import de.phoenixmitx.lombokextensions.array.LongArrayExtension;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({ ArrayExtension.class, GenericArrayExtension.class })
class ArrayExtensionSimilarityTest {
  
  private Class<?>[][] similarPrimitivClasses = {
    {IntArrayExtension.class,     int.class,    Integer.class,    IntPredicate.class,     IntFunction.class,    IntConsumer.class,    IntSupplier.class,    IntUnaryOperator.class,     IntBinaryOperator.class,    null,                       IntToLongFunction.class,    IntToDoubleFunction.class},
    {LongArrayExtension.class,    long.class,   Long.class,       LongPredicate.class,    LongFunction.class,   LongConsumer.class,   LongSupplier.class,   LongUnaryOperator.class,    LongBinaryOperator.class,   LongToIntFunction.class,    null,                       LongToDoubleFunction.class},
    {DoubleArrayExtension.class,  double.class, Double.class,     DoublePredicate.class,  DoubleFunction.class, DoubleConsumer.class, DoubleSupplier.class, DoubleUnaryOperator.class,  DoubleBinaryOperator.class, DoubleToIntFunction.class,  DoubleToLongFunction.class, null},
    {CharArrayExtension.class,    char.class,   Character.class,  Predicate.class,        Function.class,       Consumer.class,       Supplier.class,       UnaryOperator.class,        BinaryOperator.class,       ToIntFunction.class,        ToLongFunction.class,       ToDoubleFunction.class},
    {GenericArrayExtension.class, Object.class, Object.class,     Predicate.class,        Function.class,       Consumer.class,       Supplier.class,       UnaryOperator.class,        BinaryOperator.class,       ToIntFunction.class,        ToLongFunction.class,       ToDoubleFunction.class},
  };

  private boolean isMethodExcluded(Class<?> originalType, Class<?> testedType, Method originalMethod) {
    String methodName = originalMethod.getName();
    Class<?> parameterTypes[] = originalMethod.getParameterTypes();

    // ignore compiler magic
    if (methodName.startsWith("lambda$")) {
      return true;
    }

    switch (methodName) {
      // mapTO<same primitiv type> makes no sense and is therefore not implemented
      case "mapToObj": return testedType == Object.class;
      case "mapToInt": return testedType == int.class;
      case "mapToLong": return testedType == long.class;
      case "mapToDouble": return testedType == double.class;

      // not implemented for char
      case "stream":
        return testedType == char.class;
      
      // only implemented for char
      case "asString":
        return (originalType == char.class);

      // only implemented for generic types
      case "toList":
      case "toMutableList":
      case "iterator":
      case "spliterator":
        return true;

      // map to other generic types is not implemented in primitive extensions
      case "map":
      case "flatMap":
      return (originalType == Object.class && parameterTypes[parameterTypes.length - 1] == IntFunction.class);
      
      // not implemented for genetic types
      case "boxed":
      case "unboxed":
        return (testedType == Object.class);

      // numbers only
      case "sum":
        return isNumber(originalType) && !isNumber(testedType);

      // generic types in primitive extensions cross compatibility with generic extension
      case "reduce": {
          // only incompatible if the last parameter is a BiFunction
          if (parameterTypes[parameterTypes.length - 1] != BiFunction.class) {
            return false;
          }
        }
        return (testedType == Object.class ^ originalType == Object.class);
    }

    return false;
  }

  private boolean isNumber(Class<?> type) {
    return type == int.class || type == long.class || type == double.class;
  }

  @Test
  void testAllClassesHaveSimilarMethods() throws SecurityException {
    for (Class<?>[] baseClasses : similarPrimitivClasses) {
      Class<?> baseClass = baseClasses[0];
      if (baseClass == IntArrayExtension.class) {
        // TODO find a way to test similarity with IntArrayExtension as base class
        continue;
      }

      for (Method baseMethod : baseClass.getDeclaredMethods()) {
        for (Class<?>[] similarClasses : similarPrimitivClasses) {
          Class<?> similarClass = similarClasses[0];
          if (baseClass == similarClass) {
            // we dont want to test the similarity of the same class
            continue;
          }

          Class<?>[] similarMethodParameterTypes = getSimilarParameterTypes(baseClasses, similarClasses, baseMethod.getParameterTypes());
          try {
            similarClass.getDeclaredMethod(baseMethod.getName(), similarMethodParameterTypes);
          } catch (NoSuchMethodException e) {
            if (isMethodExcluded(baseClasses[1], similarClasses[1], baseMethod)) {
              continue;
            }
            throw new AssertionError("Method " + baseMethod.getName() + " from " + baseClass.getSimpleName() + " with parameter types " + getClassNames(baseMethod.getParameterTypes()) + " not found in " + similarClass.getSimpleName() + " with parameter types " + getClassNames(similarMethodParameterTypes));
          }
        }
      }
    }
  }

  private Class<?>[] getSimilarParameterTypes(Class<?>[] baseClasses, Class<?>[] similarClasses, Class<?>[] baseParametersTypes) {
    return baseParametersTypes.map(baseParameterType -> getSimilarParameterType(baseClasses, similarClasses, baseParameterType), Class[]::new);
  }
  
  private Class<?> getSimilarParameterType(Class<?>[] baseClasses, Class<?>[] similarClasses, Class<?> baseParametersType) {
    if (baseParametersType.isArray()) {
      return TestUtils.getArrayType(getSimilarParameterType(baseClasses, similarClasses, baseParametersType.getComponentType()));
    }
    int index = baseClasses.indexOf(baseParametersType);
    if (index == -1) {
      return baseParametersType;
    }
    return similarClasses[index];
  }

  private String getClassNames(Class<?>[] classes) {
    String[] names = classes.map(c -> c.getSimpleName(), String[]::new);
    return String.join(", ", names);
  }
}
