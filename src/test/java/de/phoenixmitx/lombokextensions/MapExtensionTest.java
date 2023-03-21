package de.phoenixmitx.lombokextensions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MapExtensionTest {

	@Test
	void testTransformerWorked() {
		assertTrue(MapExtension.class.getDeclaredMethods().length >= 9, "DefaultValueTransformer didn't work (only " + MapExtension.class.getDeclaredMethods().length + " methods found)");
	}
}
