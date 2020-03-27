package ru.integrotech.su.utils;

import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.Releaser;
import ru.integrotech.su.exceptions.UnsupportedParamException;

/**
 * 
 * Validator of the request spend data, that are relating of directory data
 */
public class ValidatorSpendData {

	public static void testAirport(RegisterCache cache, String locationCode) throws Exception {

		if (cache.getAirport(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input Airport LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

	public static void testCity(RegisterCache cache, String locationCode) throws Exception {
		if (cache.getCity(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input City LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

	public static void testCountry(RegisterCache cache, String locationCode) throws Exception {
		if (cache.getCountry(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input Country LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

	public static void testRegion(RegisterCache cache, String locationCode) throws Exception {
		if (cache.getRegion(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input Region LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

}
