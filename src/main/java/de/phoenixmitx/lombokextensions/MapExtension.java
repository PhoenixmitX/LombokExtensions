package de.phoenixmitx.lombokextensions;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import de.phoenixmitx.lombokextensions.codegen.defauld.DefaultValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MapExtension {
	
	public <K,V> Optional<V> find(Map<K,V> map, K key) {
		return Optional.ofNullable(map.get(key));
	}

	public static <K, O, N> Map<K, N> mapValues(Map<K, O> map, Function<O, N> mapper, @DefaultValue("new java.util.HashMap()") Map<K, N> newMap, @DefaultValue("false") boolean makeImmutable) {
		for (Map.Entry<K, O> entry : map.entrySet()) {
			newMap.put(entry.getKey(), mapper.apply(entry.getValue()));
		}
		return makeImmutable ? Collections.unmodifiableMap(newMap) : newMap;
	}

	public static <K, O, N> Map<K, N> mapValues(Map<K, O> map, BiFunction<K, O, N> mapper, @DefaultValue("new java.util.HashMap()") Map<K, N> newMap, @DefaultValue("false") boolean makeImmutable) {
		for (Map.Entry<K, O> entry : map.entrySet()) {
			newMap.put(entry.getKey(), mapper.apply(entry.getKey(), entry.getValue()));
		}
		return makeImmutable ? Collections.unmodifiableMap(newMap) : newMap;
	}
}
