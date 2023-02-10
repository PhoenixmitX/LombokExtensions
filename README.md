# LombokExtensions

LombokExtensions is a library designed to provide useful classes for Lombok's @ExtensionMethod annotation.

## Installation

[![](https://jitpack.io/v/PhoenixmitX/LombokExtensions.svg)](https://jitpack.io/#PhoenixmitX/LombokExtensions)

LombokExtensions can be installed using JitPack.

### Gradle

To include LombokExtensions in your project, add the following to your build.gradle file:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.PhoenixmitX:LombokExtensions:VERSION'
}
```

### Maven

For those who are still using Maven as their build tool, here's an example of how to include LombokExtensions in your project:

First, you need to add the JitPack repository to your pom.xml:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Next, you can add the LombokExtensions library as a dependency:

```xml
<dependency>
  <groupId>com.github.PhoenixmitX</groupId>
  <artifactId>LombokExtensions</artifactId>
  <version>VERSION</version>
</dependency>
```

## Usage

LombokExtensions provides a variety of utility classes to simplify your code. Here is a example of how you can use the library:

```java
import lombok.experimental.ExtensionMethod;
import de.pheonixmitx.lombokextensions.ArrayExtension;

@ExtensionMethod({ ArrayExtension.class })
public class MyClass {
    public static void main(String[] args) {
        int[] array = new int[] {1, 2, 3, 4, 5};
        int sum = array.sum();
        System.out.println("Sum of array elements: " + sum);
    }
}
```

## Contributing

If you're interested in contributing to LombokExtensions, we'd love to have your help!

## License

LombokExtensions is licensed under the [MIT License](LICENSE.md).
