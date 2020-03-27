package ru.integrotech.su.utils;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.Releaser;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.charge.ChargeInput;

/**
 * 
 * Validator of the request charge data , that depends of directory data
 *
 */
public class ValidatorChargeData {

	public static void testValidAirline(RegisterCache cache,
			ChargeInput chargeInput) throws UnsupportedParamException {
		if (chargeInput.getAirline() != null
				&& chargeInput.getAirline().getAirlineCode() != null) {
			Airline aln = cache.getAirline(chargeInput.getAirline()
					.getAirlineCode());
			if (aln == null) {
				Releaser.release();
				throw new UnsupportedParamException(
						" Given unknown airline code  "
								+ chargeInput.getAirline().getAirlineCode());
			}
		}
	}
}
