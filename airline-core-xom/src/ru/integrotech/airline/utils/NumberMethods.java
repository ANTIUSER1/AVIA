package ru.integrotech.airline.utils;

public class NumberMethods {
	
	public static int getPercent(int value, int percent) {
		return (int) Math.round((double) value * percent / 100);
	}

}
