package de.phoenixmitx.lombokextensions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CollectorsExtensionTest {

	@Test
	public void testTrasformerWorked() {
		assertTrue(CollectorsExtension.class.getDeclaredMethods().length > 40, "CollectorsTransformer didn't work (only " + CollectorsExtension.class.getDeclaredMethods().length + " methods found)");
	}
}
