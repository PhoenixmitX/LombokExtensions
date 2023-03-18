package de.phoenixmitx.lombokextensions;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ListExtension {
	
	public <T> boolean isIn(T t, Collection<T> collection) {
		return collection.contains(t);
	}

	public <T> Optional<T> find(Collection<T> collection, Predicate<T> condition) {
		for (T t : collection) {
			if (condition.test(t)) {
				return Optional.of(t);
			}
		}
		return Optional.empty();
	}

	public <T, O> Optional<T> find(Collection<T> collection, Function<T, O> function, O o) {
		return find(collection, t -> o.equals(function.apply(t)));
	}
}
