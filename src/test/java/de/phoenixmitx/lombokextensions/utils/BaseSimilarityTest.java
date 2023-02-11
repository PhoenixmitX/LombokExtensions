package de.phoenixmitx.lombokextensions.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import de.phoenixmitx.lombokextensions.ArrayExtension;
import de.phoenixmitx.lombokextensions.array.GenericArrayExtension;
import de.phoenixmitx.lombokextensions.array.IntArrayExtension;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod({ ArrayExtension.class, GenericArrayExtension.class })
public abstract class BaseSimilarityTest {
	
	protected abstract Class<?>[][] getSimilarClasses();
	protected abstract boolean isMethodExcluded(Class<?> originalType, Class<?> testedType, Method originalMethod);
	
  @Test
  void testAllClassesHaveSimilarMethods() throws SecurityException {
    boolean failed = false;
    for (Class<?>[] baseClasses : getSimilarClasses()) {
      Class<?> baseClass = baseClasses[0];
      if (baseClass == IntArrayExtension.class) {
        // TODO find a way to test similarity with IntArrayExtension as base class
        continue;
      }

      for (Method baseMethod : baseClass.getDeclaredMethods()) {
        for (Class<?>[] similarClasses : getSimilarClasses()) {
          Class<?> similarClass = similarClasses[0];
          if (baseClass == similarClass) {
            // we dont want to test the similarity of the same class
            continue;
          }

          if (!Modifier.isStatic(baseMethod.getModifiers()) || !Modifier.isPublic(baseMethod.getModifiers())) {
            // we only want to test public static methods
            continue;
          }

          // TODO check return types 
          Class<?>[] similarMethodParameterTypes = getSimilarParameterTypes(baseClasses, similarClasses, baseMethod.getParameterTypes());
          boolean isExcluded = isMethodExcluded(baseClasses[1], similarClasses[1], baseMethod);
          try {
            similarClass.getDeclaredMethod(baseMethod.getName(), similarMethodParameterTypes);
            if (isExcluded) {
              failed = true;
              System.err.println("Method " + baseClass.getSimpleName() + "." + baseMethod.getName() + "(" + getClassNames(baseMethod.getParameterTypes()) + ") is excluded but found as " + similarClass.getSimpleName() + "." + baseMethod.getName() + "(" + getClassNames(similarMethodParameterTypes) + ")");
            }
          } catch (NoSuchMethodException e) {
            if (isExcluded) {
              continue;
            }
            failed = true;
            System.err.println("Method " + baseClass.getSimpleName() + "." + baseMethod.getName() + "(" + getClassNames(baseMethod.getParameterTypes()) + ") not found as " + similarClass.getSimpleName() + "." + baseMethod.getName() + "(" + getClassNames(similarMethodParameterTypes) + ")");
          }
        }
      }
    }
    assertFalse(failed, "Missing methods in similar classes");
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
