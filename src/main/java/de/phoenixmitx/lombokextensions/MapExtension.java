package de.phoenixmitx.lombokextensions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MapExtension {
	
	public <K,V> Optional<V> find(Map<K,V> map, K key) {
		return Optional.ofNullable(map.get(key));
	}

	public static <K, O, N> Map<K, N> mapValues(Map<K, O> map, Function<O, N> mapper) {
		return mapValues(map, mapper, new HashMap<>(), false);
	}
	
	public static <K, O, N> Map<K, N> mapValues(Map<K, O> map, Function<O, N> mapper, Map<K, N> newMap) {
		return mapValues(map, mapper, newMap, false);
	}

	public static <K, O, N> Map<K, N> mapValues(Map<K, O> map, Function<O, N> mapper, boolean makeImmutable) {
		return mapValues(map, mapper, new HashMap<>(), makeImmutable);
	}

	public static <K, O, N> Map<K, N> mapValues(Map<K, O> map, Function<O, N> mapper, Map<K, N> newMap, boolean makeImmutable) {
		for (Map.Entry<K, O> entry : map.entrySet()) {
			newMap.put(entry.getKey(), mapper.apply(entry.getValue()));
		}
		return makeImmutable ? Collections.unmodifiableMap(newMap) : newMap;
	}
}
