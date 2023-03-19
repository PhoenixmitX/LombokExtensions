package de.phoenixmitx.lombokextensions;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class CollectorsExtensionTest {

	@Test
	public void testTrasformerWorked() {
		for (Method m : CollectorsExtension.class.getDeclaredMethods()) {
			System.out.println(m);
		}
	}
}
