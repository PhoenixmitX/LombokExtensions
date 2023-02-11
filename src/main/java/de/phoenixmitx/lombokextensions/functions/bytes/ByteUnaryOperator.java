package de.phoenixmitx.lombokextensions.functions.bytes;

@FunctionalInterface
public interface ByteUnaryOperator {
  public byte applyAsByte(byte operand);
}
