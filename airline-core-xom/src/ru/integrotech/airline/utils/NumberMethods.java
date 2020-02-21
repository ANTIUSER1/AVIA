package ru.integrotech.airline.utils;

public class NumberMethods {
	
	public static int getPercent(int value, int percent) {
		return (int) Math.round((double) value * percent / 100);
	}

	public static int getMultiplication(double...args) {
	    double result = 1;
	    for (double arg : args) {
	        result = result * arg;
        }
        return (int) result;
    }

}
