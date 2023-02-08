package de.phoenixmitx.lombokextensions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ArrayExtensionTest {

  @Test
  public void testStaticDelegateWorked() {
    // This test is just here to make sure the static delegate transformer worked
    // If it didn't, the class would only contain a few methods
    assertTrue(ArrayExtension.class.getDeclaredMethods().length > 100, "Static delegate transformer didn't work (only " + ArrayExtension.class.getDeclaredMethods().length + " methods found)");
  }
}
