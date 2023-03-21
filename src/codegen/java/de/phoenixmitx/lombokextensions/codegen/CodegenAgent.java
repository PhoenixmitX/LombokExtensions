package de.phoenixmitx.lombokextensions.codegen;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;

import de.phoenixmitx.lombokextensions.codegen.defauld.DefaultValueTransformer;
import de.phoenixmitx.lombokextensions.codegen.delegate.StaticDelegateTransformer;
import de.phoenixmitx.lombokextensions.codegen.singleuse.collectors.CollectorsTransformer;
import de.phoenixmitx.lombokextensions.codegen.transformer.JavaAgentTransformer;
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

  public static void main(String[] args) throws ClassNotFoundException, IllegalClassFormatException {
    if (!transformerInitialized) {
      throw new IllegalStateException("CodegenAgent: Transformer not initialized");
    }
    log.info("CodegenAgent: Transforming classes");
		transforClass("de.phoenixmitx.lombokextensions.ArrayExtension");
		transforClass("de.phoenixmitx.lombokextensions.CollectorsExtension");
		transforClass("de.phoenixmitx.lombokextensions.MapExtension");
		log.info("CodegenAgent: Done");
  }

	private static void transforClass(String className) throws ClassNotFoundException, IllegalClassFormatException {
		log.info("CodegenAgent: Transforming class " + className);
		Class.forName(className);
		if (transformer.getLastException() != null) {
			throw transformer.getLastException();
		}
		log.info("CodegenAgent: Transformed class " + className);
	}
}
