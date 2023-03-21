package de.phoenixmitx.lombokextensions.codegen;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Path;

import de.phoenixmitx.lombokextensions.codegen.defauld.DefaultValueTransformer;
import de.phoenixmitx.lombokextensions.codegen.delegate.StaticDelegateTransformer;
import de.phoenixmitx.lombokextensions.codegen.singleuse.collectors.CollectorsTransformer;
import de.phoenixmitx.lombokextensions.codegen.transformer.JavaAgentTransformer;
import de.phoenixmitx.lombokextensions.codegen.utils.ClassScanner;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class CodegenAgent {

  private static boolean transformerInitialized = false;
	private static JavaAgentTransformer transformer;
  
  public static void premain(String args, Instrumentation inst) {
    log.info("CodegenAgent: Initalizing transformer");
    inst.addTransformer(transformer = new JavaAgentTransformer(
			new StaticDelegateTransformer(),
			new CollectorsTransformer(),
			new DefaultValueTransformer()
		));
    transformerInitialized = true;
  }

  public static void main(String[] args) throws ClassNotFoundException, IllegalClassFormatException, IOException {
    if (!transformerInitialized) {
      throw new IllegalStateException("CodegenAgent: Transformer not initialized");
    }
    log.info("CodegenAgent: Transforming classes");
		ClassScanner.scan(Path.of("build/classes/java/main/"), CodegenAgent::transformClass);
		log.info("CodegenAgent: Done");
  }

	@SneakyThrows
	private static void transformClass(String className) {
		Class.forName(className);
		if (transformer.getLastException() != null) {
			throw transformer.getLastException();
		}
	}
}
