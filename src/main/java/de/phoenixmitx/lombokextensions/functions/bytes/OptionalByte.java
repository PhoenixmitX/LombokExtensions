package de.phoenixmitx.lombokextensions.functions.bytes;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

public final class OptionalByte {
  private static final OptionalByte EMPTY = new OptionalByte();
  private final byte value;

  private OptionalByte() {
    this.value = 0;
  }

  private OptionalByte(byte value) {
    this.value = value;
  }

  public static OptionalByte empty() {
    return EMPTY;
  }

  public static OptionalByte of(byte value) {
    return new OptionalByte(value);
  }

  public byte getAsByte() {
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

  public void ifPresent(ByteConsumer consumer) {
    if (this != EMPTY) {
      consumer.accept(value);
    }
  }

  public OptionalByte or(Supplier<OptionalByte> supplier) {
    if (this != EMPTY) {
      return this;
    } else {
      return supplier.get();
    }
  }

  public byte orElse(byte other) {
    return this == EMPTY ? other : value;
  }

  public byte orElseGet(ByteSupplier supplier) {
    return this == EMPTY ? supplier.getAsByte() : value;
  }

  public <X extends Throwable> byte orElseThrow(Supplier<? extends X> supplier) throws X {
    if (this == EMPTY) {
      throw supplier.get();
    }
    return value;
  }

  public <U> Optional<U> map(ByteFunction<? extends U> mapper) {
    if (this == EMPTY) {
      return Optional.empty();
    }
    return Optional.ofNullable(mapper.apply(value));
  }

  public OptionalByte filter(BytePredicate predicate) {
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
    OptionalByte other = (OptionalByte) obj;
    if (this == EMPTY ^ other == EMPTY) {
      return false;
    }
    return value == other.value;
  }

  @Override
  public int hashCode() {
    return Byte.hashCode(value);
  }

  @Override
  public String toString() {
    return this == EMPTY ? "OptionalByte.empty" : "OptionalByte[" + value + "]";
  }
}
