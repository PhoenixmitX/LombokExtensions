package de.phoenixmitx.lombokextensions.codegen;

import java.io.IOException;

import de.phoenixmitx.lombokextensions.codegen.transformers.CodegenAgent;
import de.phoenixmitx.lombokextensions.codegen.transformers.CodegenTransformer;
import de.phoenixmitx.lombokextensions.codegen.transformers.general.defauld.DefaultValueTransformer;
import de.phoenixmitx.lombokextensions.codegen.transformers.general.primitive.PrimitiveTransformer;
import de.phoenixmitx.lombokextensions.codegen.transformers.general.staticdelegate.StaticDelegateTransformer;
import de.phoenixmitx.lombokextensions.codegen.transformers.singleuse.CollectorsTransformer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Codegen {

  public static void main(String[] args) throws IOException {
    new CodegenAgent(new CodegenTransformer[] {
			
			// Single use transformers
			new CollectorsTransformer(),

			// Transformers that can be used multiple times (order matters for classes that use multiple transformers)
			new PrimitiveTransformer(),
			new DefaultValueTransformer(),
			new StaticDelegateTransformer()

		}).transformAll();
  }
}
