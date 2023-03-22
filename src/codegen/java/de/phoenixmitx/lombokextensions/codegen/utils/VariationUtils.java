package de.phoenixmitx.lombokextensions.codegen.utils;

import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VariationUtils {
	
	@SuppressWarnings("unchecked")
	public <T> Optional<T>[][] getAllPosibleEmptyVariations(Optional<T>[] defaultValues) {
		int presentValues = 0;
		boolean[] presentAt = new boolean[defaultValues.length];
		for (int i = 0; i < defaultValues.length; i++) {
			if (defaultValues[i].isPresent()) {
				presentValues++;
				presentAt[i] = true;
			}
		}
		int posibleVariations = (int) Math.pow(2, presentValues) - 1; // -1 because we don't want all default values to be empty because that method does already exist
		Optional<T>[][] result = new Optional[posibleVariations][defaultValues.length];
		for (int i = 0; i < posibleVariations; i++) {
			int j = 0;
			for (int k = 0; k < defaultValues.length; k++) {
				if (presentAt[k]) {
					result[i][k] = (i & (1 << j++)) != 0 ? Optional.empty() : defaultValues[k];
				} else {
					result[i][k] = Optional.empty();
				}
			}
		}
		return result;
	}
}
