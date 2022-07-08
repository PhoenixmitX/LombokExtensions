package de.phoenixmitx.lombokextensions;

import java.util.Collection;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ListExtension {
	
	public <T> boolean isIn(T t, Collection<T> collection) {
		return collection.contains(t);
	}
}
