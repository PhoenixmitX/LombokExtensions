package de.phoenixmitx.lombokextensions.functions.floats;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

public final class OptionalFloat {
  private static final OptionalFloat EMPTY = new OptionalFloat();
  private final float value;

  private OptionalFloat() {
    this.value = 0;
  }

  private OptionalFloat(float value) {
    this.value = value;
  }

  public static OptionalFloat empty() {
    return EMPTY;
  }

  public static OptionalFloat of(float value) {
    return new OptionalFloat(value);
  }

  public float getAsFloat() {
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

  public void ifPresent(FloatConsumer consumer) {
    if (this != EMPTY) {
      consumer.accept(value);
    }
  }

  public OptionalFloat or(Supplier<OptionalFloat> supplier) {
    if (this != EMPTY) {
      return this;
    } else {
      return supplier.get();
    }
  }

  public float orElse(float other) {
    return this == EMPTY ? other : value;
  }

  public float orElseGet(FloatSupplier supplier) {
    return this == EMPTY ? supplier.getAsFloat() : value;
  }

  public <X extends Throwable> float orElseThrow(Supplier<? extends X> supplier) throws X {
    if (this == EMPTY) {
      throw supplier.get();
    }
    return value;
  }

  public <U> Optional<U> map(FloatFunction<? extends U> mapper) {
    if (this == EMPTY) {
      return Optional.empty();
    }
    return Optional.ofNullable(mapper.apply(value));
  }

  public OptionalFloat filter(FloatPredicate predicate) {
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
    OptionalFloat other = (OptionalFloat) obj;
    if (this == EMPTY ^ other == EMPTY) {
      return false;
    }
    return value == other.value;
  }

  @Override
  public int hashCode() {
    return this == EMPTY ? -1 : Float.hashCode(value);
  }

  @Override
  public String toString() {
    return this == EMPTY ? "OptionalFloat.empty" : "OptionalFloat[" + value + "]";
  }
}
