package de.phoenixmitx.lombokextensions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CollectorsExtensionTest {

	@Test
	void testTrasformerWorked() {
		assertTrue(CollectorsExtension.class.getDeclaredMethods().length >= 37, "CollectorsTransformer didn't work (only " + CollectorsExtension.class.getDeclaredMethods().length + " methods found)");
	}
}
