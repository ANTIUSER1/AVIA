package ru.integrotech.su.utils;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.Releaser;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.charge.ChargeInput;

/**
 * 
 * Validator of the request spend data, that are relating of directory data
 */
public class ValidatorInputData {

	public static void testAirport(RegisterCache cache, String locationCode)
			throws Exception {

		if (cache.getAirport(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input Airport LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

	public static void testCity(RegisterCache cache, String locationCode)
			throws Exception {
		if (cache.getCity(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input City LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

	public static void testCountry(RegisterCache cache, String locationCode)
			throws Exception {
		if (cache.getCountry(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input Country LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

	public static void testRegion(RegisterCache cache, String locationCode)
			throws Exception {
		if (cache.getRegion(locationCode) == null) {
			throw new UnsupportedParamException(
					" Incorrect input Region LocationType value."
							+ " and locationCode=" + locationCode);
		}
	}

	public static void testValidAirline(RegisterCache cache,
			ChargeInput chargeInput) throws UnsupportedParamException {

		if (chargeInput.getAirline() != null
				&& chargeInput.getAirline().getAirlineCode() != null) {
			Airline aln = cache.getAirline(chargeInput.getAirline()
					.getAirlineCode());
			if (aln == null) {

				throw new UnsupportedParamException(
						" Given unknown airline code  '"
								+ chargeInput.getAirline().getAirlineCode()
								+ "'");
			} else {
			}
		}
	}
}
