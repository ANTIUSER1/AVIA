package ru.integrotech.airline.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * the util methods of for enums
 *
 */
public class EnumMethods {

	public static boolean containsUpper(Class c, String s) {
		try {
			Enum.valueOf(c, s.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public static boolean containsLower(Class c, String s) {
		try {
			Enum.valueOf(c, s.toLowerCase());
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public static String enumToString(Class<? extends Enum<?>> e) {
		String[] st = getNames(e);
		List<String> strList = Arrays.asList(st);
		return strList.toString();
	}

	public static String[] getNames(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name)
				.toArray(String[]::new);
	}
}
