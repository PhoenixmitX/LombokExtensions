package de.phoenixmitx.lombokextensions.codegen.transformers;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.phoenixmitx.lombokextensions.codegen.utils.ClassScanner;
import javassist.ClassPool;
import javassist.CtClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class CodegenAgent {
	
	@Getter
	private final ClassPool classPool = new ClassPool(true) {
		@Override
		protected CtClass createCtClass(String classname, boolean useCache) {
			CtClass ctClass = super.createCtClass(classname, useCache);
			if (ctClass != null) {
				CodegenAgent.this.transform(ctClass);
			}
			return ctClass;
		};
	};
	private final CodegenTransformer[] transformers;
	private final List<String> transformedClasses = new ArrayList<>();

	@SneakyThrows
	private void transform(CtClass ctClass) {
		if (!ctClass.getName().startsWith("de.phoenixmitx.lombokextensions") || ctClass.getName().contains("codegen") || transformedClasses.contains(ctClass.getName())) {
			return;
		}
		transformedClasses.add(ctClass.getName());
		boolean modified = false;
		for (CodegenTransformer transformer : transformers) {
			modified |= transformer.transform(ctClass, classPool);
		}
		if (modified) {
			log.info("CodegenAgent: Writing transformed class " + ctClass.getName());
			try (
				FileOutputStream fos = new FileOutputStream("build/classes/java/main/" + ctClass.getName().replace('.', '/') + ".class");
				DataOutputStream dos = new DataOutputStream(fos)
			) {
				ctClass.getClassFile().write(dos);
			} catch (Exception e) {
				IOException icfx = new IOException("Error transforming class " + ctClass.getName());
				icfx.initCause(e);
				throw icfx;
			}
		}
	}

	public void transformAll() throws IOException {
    log.info("CodegenAgent: Transforming classes");
		ClassScanner.scan(Paths.get("build/classes/java/main/"), classPool::getOrNull);
		log.info("CodegenAgent: Done");
	}
}
