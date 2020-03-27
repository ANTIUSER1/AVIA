package ru.integrotech.airline.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionsMethods {

	public static <T> T get(Collection<T> collection, int n) {
		List<T> tmp = new ArrayList<T>(collection);
		return tmp.get(n);
	}
}
