package de.phoenixmitx.lombokextensions.functions.shorts;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

public final class OptionalShort {
  private static final OptionalShort EMPTY = new OptionalShort();
  private final short value;

  private OptionalShort() {
    this.value = 0;
  }

  private OptionalShort(short value) {
    this.value = value;
  }

  public static OptionalShort empty() {
    return EMPTY;
  }

  public static OptionalShort of(short value) {
    return new OptionalShort(value);
  }

  public short getAsShort() {
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

  public void ifPresent(ShortConsumer consumer) {
    if (this != EMPTY) {
      consumer.accept(value);
    }
  }

  public OptionalShort or(Supplier<OptionalShort> supplier) {
    if (this != EMPTY) {
      return this;
    } else {
      return supplier.get();
    }
  }

  public short orElse(short other) {
    return this == EMPTY ? other : value;
  }

  public short orElseGet(ShortSupplier supplier) {
    return this == EMPTY ? supplier.getAsShort() : value;
  }

  public <X extends Throwable> short orElseThrow(Supplier<? extends X> supplier) throws X {
    if (this == EMPTY) {
      throw supplier.get();
    }
    return value;
  }

  public <U> Optional<U> map(ShortFunction<? extends U> mapper) {
    if (this == EMPTY) {
      return Optional.empty();
    }
    return Optional.ofNullable(mapper.apply(value));
  }

  public OptionalShort filter(ShortPredicate predicate) {
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
    OptionalShort other = (OptionalShort) obj;
    if (this == EMPTY ^ other == EMPTY) {
      return false;
    }
    return value == other.value;
  }

  @Override
  public int hashCode() {
    return this == EMPTY ? -1 : Short.hashCode(value);
  }

  @Override
  public String toString() {
    return this == EMPTY ? "OptionalShort.empty" : "OptionalShort[" + value + "]";
  }
}
