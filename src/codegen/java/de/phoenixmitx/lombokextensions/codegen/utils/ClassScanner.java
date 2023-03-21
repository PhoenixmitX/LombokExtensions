package de.phoenixmitx.lombokextensions.codegen.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassScanner {
	
	public void scan(Path path, Consumer<String> classNameConsumer) throws IOException {
		String root = path.toString();
		int rootLength = root.length() + 1;
		Files.walk(path)
				.filter(Files::isRegularFile)
				.filter(p -> p.toString().endsWith(".class"))
				.map(p -> p.toString()
					.substring(rootLength, p.toString().length() - 6)
					.replace('/', '.')
				)
				.forEach(classNameConsumer);
	}
}