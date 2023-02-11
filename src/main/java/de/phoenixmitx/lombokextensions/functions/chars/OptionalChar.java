package de.phoenixmitx.lombokextensions.functions.chars;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

public final class OptionalChar {
  private static final OptionalChar EMPTY = new OptionalChar();
  private final char value;

  private OptionalChar() {
    this.value = 0;
  }

  private OptionalChar(char value) {
    this.value = value;
  }

  public static OptionalChar empty() {
    return EMPTY;
  }

  public static OptionalChar of(char value) {
    return new OptionalChar(value);
  }

  public char getAsChar() {
    if (this == EMPTY) {
      throw new NoSuchElementException("No value present");
    }
    return value;
  }

  public boolean isPresent() {
    return this != EMPTY;
  }

  public boolean isEmpty() {
    return this == EMPTY;
  }

  public void ifPresent(CharConsumer consumer) {
    if (this != EMPTY) {
      consumer.accept(value);
    }
  }

  public OptionalChar or(Supplier<OptionalChar> supplier) {
    if (this != EMPTY) {
      return this;
    } else {
      return supplier.get();
    }
  }

  public char orElse(char other) {
    return this == EMPTY ? other : value;
  }

  public char orElseGet(CharSupplier supplier) {
    return this == EMPTY ? supplier.getAsChar() : value;
  }

  public <X extends Throwable> char orElseThrow(Supplier<? extends X> supplier) throws X {
    if (this == EMPTY) {
      throw supplier.get();
    }
    return value;
  }

  public <U> Optional<U> map(CharFunction<? extends U> mapper) {
    if (this == EMPTY) {
      return Optional.empty();
    }
    return Optional.ofNullable(mapper.apply(value));
  }

  public OptionalChar filter(CharPredicate predicate) {
    if (this == EMPTY) {
      return this;
    }
    return predicate.test(value) ? this : EMPTY;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    OptionalChar other = (OptionalChar) obj;
    if (this == EMPTY ^ other == EMPTY) {
      return false;
    }
    return value == other.value;
  }

  @Override
  public int hashCode() {
    return Character.hashCode(value);
  }

  @Override
  public String toString() {
    return this == EMPTY ? "OptionalChar.empty" : "OptionalChar[" + value + "]";
  }
}
