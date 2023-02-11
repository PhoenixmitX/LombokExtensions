package de.phoenixmitx.lombokextensions.functions.booleans;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public final class OptionalBoolean {
  private static final OptionalBoolean EMPTY = new OptionalBoolean();
  private final boolean value;

  private OptionalBoolean() {
    this.value = false;
  }

  private OptionalBoolean(boolean value) {
    this.value = value;
  }

  public static OptionalBoolean empty() {
    return EMPTY;
  }

  public static OptionalBoolean of(boolean value) {
    return new OptionalBoolean(value);
  }

  public boolean getAsBoolean() {
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

  public void ifPresent(BooleanConsumer consumer) {
    if (this != EMPTY) {
      consumer.accept(value);
    }
  }

  public OptionalBoolean or(Supplier<OptionalBoolean> supplier) {
    if (this != EMPTY) {
      return this;
    } else {
      return supplier.get();
    }
  }

  public boolean orElse(boolean other) {
    return this == EMPTY ? other : value;
  }

  public boolean orElseGet(BooleanSupplier supplier) {
    return this == EMPTY ? supplier.getAsBoolean() : value;
  }

  public <X extends Throwable> boolean orElseThrow(Supplier<? extends X> supplier) throws X {
    if (this == EMPTY) {
      throw supplier.get();
    }
    return value;
  }

  public <U> Optional<U> map(BooleanFunction<? extends U> mapper) {
    if (this == EMPTY) {
      return Optional.empty();
    }
    return Optional.ofNullable(mapper.apply(value));
  }

  public OptionalBoolean filter(BooleanPredicate predicate) {
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
    OptionalBoolean other = (OptionalBoolean) obj;
    if (this == EMPTY ^ other == EMPTY) {
      return false;
    }
    return value == other.value;
  }

  @Override
  public int hashCode() {
    return this == EMPTY ? -1 : Boolean.hashCode(value);
  }

  @Override
  public String toString() {
    return this == EMPTY ? "OptionalBoolean.empty" : "OptionalBoolean[" + value + "]";
  }
}
