package de.phoenixmitx.lombokextensions;

import java.util.Map;
import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MapExtension {
	
	public <K,V> Optional<V> find(Map<K,V> map, K key) {
		return Optional.ofNullable(map.get(key));
	}
}
