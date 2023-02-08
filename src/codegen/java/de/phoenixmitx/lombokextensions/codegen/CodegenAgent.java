package de.phoenixmitx.lombokextensions.codegen;

import java.lang.instrument.Instrumentation;

import de.phoenixmitx.lombokextensions.codegen.delegate.StaticDelegateTransformer;
import de.phoenixmitx.lombokextensions.codegen.transformer.JavaAgentTransformer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodegenAgent {

  private static boolean transformerInitialized = false;
  
  public static void premain(String args, Instrumentation inst) {
    System.out.println("CodegenAgent: Initalizing transformer");
    StaticDelegateTransformer transformer = new StaticDelegateTransformer();
    inst.addTransformer(new JavaAgentTransformer(transformer), true);
    transformerInitialized = true;
  }

  public static void main(String[] args) throws ClassNotFoundException {
    if (!transformerInitialized) {
      throw new IllegalStateException("CodegenAgent: Transformer not initialized");
    }
    System.out.println("CodegenAgent: Transforming classes");
    // Initalize the class to trigger the transformer
    try {
      Class.forName("de.phoenixmitx.lombokextensions.ArrayExtension");
    } catch (Exception e) {
      // Print the stacktrace to the console
      e.printStackTrace();
      throw e;
    }
  }
}
