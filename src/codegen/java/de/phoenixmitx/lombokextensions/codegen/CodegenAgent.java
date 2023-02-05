package de.phoenixmitx.lombokextensions.codegen;

import java.lang.instrument.Instrumentation;

import de.phoenixmitx.lombokextensions.codegen.delegate.StaticDelegateTransformer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodegenAgent {
  
  public static void premain(String args, Instrumentation inst) {
    System.out.println("CodegenAgent started");
    StaticDelegateTransformer transformer = new StaticDelegateTransformer();
    inst.addTransformer(transformer, true);
  }
}
