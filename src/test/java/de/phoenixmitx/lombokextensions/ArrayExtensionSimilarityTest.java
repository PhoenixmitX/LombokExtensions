package de.phoenixmitx.lombokextensions;

import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
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

import de.phoenixmitx.lombokextensions.array.BooleanArrayExtension;
import de.phoenixmitx.lombokextensions.array.ByteArrayExtension;
import de.phoenixmitx.lombokextensions.array.CharArrayExtension;
import de.phoenixmitx.lombokextensions.array.DoubleArrayExtension;
import de.phoenixmitx.lombokextensions.array.FloatArrayExtension;
import de.phoenixmitx.lombokextensions.array.GenericArrayExtension;
import de.phoenixmitx.lombokextensions.array.IntArrayExtension;
import de.phoenixmitx.lombokextensions.array.LongArrayExtension;
import de.phoenixmitx.lombokextensions.array.ShortArrayExtension;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanConsumer;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanFunction;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanPredicate;
import de.phoenixmitx.lombokextensions.functions.booleans.BooleanUnaryOperator;
import de.phoenixmitx.lombokextensions.functions.bytes.ByteBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.bytes.ByteConsumer;
import de.phoenixmitx.lombokextensions.functions.bytes.ByteFunction;
import de.phoenixmitx.lombokextensions.functions.bytes.BytePredicate;
import de.phoenixmitx.lombokextensions.functions.bytes.ByteSupplier;
import de.phoenixmitx.lombokextensions.functions.bytes.ByteUnaryOperator;
import de.phoenixmitx.lombokextensions.functions.chars.CharBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.chars.CharConsumer;
import de.phoenixmitx.lombokextensions.functions.chars.CharFunction;
import de.phoenixmitx.lombokextensions.functions.chars.CharPredicate;
import de.phoenixmitx.lombokextensions.functions.chars.CharSupplier;
import de.phoenixmitx.lombokextensions.functions.chars.CharUnaryOperator;
import de.phoenixmitx.lombokextensions.functions.floats.FloatBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.floats.FloatConsumer;
import de.phoenixmitx.lombokextensions.functions.floats.FloatFunction;
import de.phoenixmitx.lombokextensions.functions.floats.FloatPredicate;
import de.phoenixmitx.lombokextensions.functions.floats.FloatSupplier;
import de.phoenixmitx.lombokextensions.functions.floats.FloatUnaryOperator;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortBinaryOperator;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortConsumer;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortFunction;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortPredicate;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortSupplier;
import de.phoenixmitx.lombokextensions.functions.shorts.ShortUnaryOperator;
import de.phoenixmitx.lombokextensions.utils.BaseSimilarityTest;
import lombok.Getter;

class ArrayExtensionSimilarityTest extends BaseSimilarityTest {
	
	@Getter
	private Class<?>[][] similarClasses = {
		{ BooleanArrayExtension.class, boolean.class, Boolean.class,    BooleanPredicate.class, BooleanFunction.class, BooleanConsumer.class, BooleanSupplier.class, BooleanUnaryOperator.class, BooleanBinaryOperator.class, ToDoubleFunction.class,     ToIntFunction.class,       ToLongFunction.class,      },
		{ ByteArrayExtension.class,    byte.class,    Byte.class,       BytePredicate.class,    ByteFunction.class,    ByteConsumer.class,    ByteSupplier.class,    ByteUnaryOperator.class,    ByteBinaryOperator.class,    ToDoubleFunction.class,     ToIntFunction.class,       ToLongFunction.class,      },
		{ CharArrayExtension.class,    char.class,    Character.class,  CharPredicate.class,    CharFunction.class,    CharConsumer.class,    CharSupplier.class,    CharUnaryOperator.class,    CharBinaryOperator.class,    ToDoubleFunction.class,     ToIntFunction.class,       ToLongFunction.class,      },
		{ DoubleArrayExtension.class,  double.class,  Double.class,     DoublePredicate.class,  DoubleFunction.class,  DoubleConsumer.class,  DoubleSupplier.class,  DoubleUnaryOperator.class,  DoubleBinaryOperator.class,  null,                       DoubleToIntFunction.class, DoubleToLongFunction.class },
		{ FloatArrayExtension.class,   float.class,   Float.class,      FloatPredicate.class,   FloatFunction.class,   FloatConsumer.class,   FloatSupplier.class,   FloatUnaryOperator.class,   FloatBinaryOperator.class,   ToDoubleFunction.class,     ToIntFunction.class,       ToLongFunction.class,      },
		{ GenericArrayExtension.class, Object.class,  Object.class,     Predicate.class,        Function.class,        Consumer.class,        Supplier.class,        UnaryOperator.class,        BinaryOperator.class,        ToDoubleFunction.class,     ToIntFunction.class,       ToLongFunction.class,      },
		{ IntArrayExtension.class,     int.class,     Integer.class,    IntPredicate.class,     IntFunction.class,     IntConsumer.class,     IntSupplier.class,     IntUnaryOperator.class,     IntBinaryOperator.class,     IntToDoubleFunction.class,  null,                      IntToLongFunction.class,   },
		{ LongArrayExtension.class,    long.class,    Long.class,       LongPredicate.class,    LongFunction.class,    LongConsumer.class,    LongSupplier.class,    LongUnaryOperator.class,    LongBinaryOperator.class,    LongToDoubleFunction.class, LongToIntFunction.class,   null,                      },
		{ ShortArrayExtension.class,   short.class,   Short.class,      ShortPredicate.class,   ShortFunction.class,   ShortConsumer.class,   ShortSupplier.class,   ShortUnaryOperator.class,   ShortBinaryOperator.class,   ToDoubleFunction.class,     ToIntFunction.class,       ToLongFunction.class,      },
	};

	protected boolean isMethodExcluded(Class<?> originalType, Class<?> testedType, Method originalMethod) {
		String methodName = originalMethod.getName();
		Class<?> parameterTypes[] = originalMethod.getParameterTypes();

		switch (methodName) {
			// mapTo<same type> makes no sense and is therefore not implemented
			case "mapToObj": return testedType == Object.class;
			case "mapToInt": return testedType == int.class;
			case "mapToLong": return testedType == long.class;
			case "mapToDouble": return testedType == double.class;

			// only implemented types with native functions
			case "stream":
				return hasNativeFunctions(originalType) && !hasNativeFunctions(testedType);
			
			// only implemented for char
			case "asString":
				return originalType == char.class;

			// only implemented for generic types
			case "toList":
			case "toMutableList":
			case "iterator":
			case "spliterator":
				return originalType == Object.class;

			// map to other generic types is not implemented in primitive extensions
			case "map":
			case "flatMap":
				return originalType == Object.class && parameterTypes[parameterTypes.length - 1] == IntFunction.class;
			
			// not implemented for genetic types
			case "boxed":
			case "unboxed":
				return testedType == Object.class;

			// numbers only
			case "sum":
				return isNumber(originalType) && !isNumber(testedType);

			// GenericArrayExtension.reduce(Object[], Object, BiFunction) gets wrongly converted to BooleanArrayExtension.reduce(boolean[], boolean, BiFunction) instead of BooleanArrayExtension.reduce(boolean[], Object, BiFunction)
			case "reduce":
				return (parameterTypes[parameterTypes.length - 1] == BiFunction.class) && (testedType != Object.class && originalType == Object.class);
		}

		return false;
	}

	private boolean isNumber(Class<?> type) {
		return type == int.class || type == long.class || type == double.class || type == float.class || type == short.class;
	}

	private boolean hasNativeFunctions(Class<?> type) {
		return type == Object.class || type == int.class || type == long.class || type == double.class;
	}
}
