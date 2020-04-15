package ru.integrotech.su.utils;

import ru.integrotech.airline.core.bonus.Loyalty.LOYALTY_TYPE;
import ru.integrotech.airline.utils.EnumMethods;
import ru.integrotech.airline.utils.StringMethods;
import ru.integrotech.su.common.Airport;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.charge.ChargeInput;

/**
 * 
 * Validator of the request charge data, that are independent of directory data
 * (e.g. correct names of location, existing fields etc )
 *
 */
public class ValidatorCharge {
	private static final String SOURCE_OF_MESSAGE = ValidatorCharge.class
			.toString();

	public void validateHardDataCharge(ChargeInput chargeInput)
			throws Exception {

		testNotNullLocation(chargeInput);
		testNotNullAirline(chargeInput);
		testNotNullTierLevel(chargeInput);
		testNotNullIsRoundTrip(chargeInput);

	}

	private void testNotNullIsRoundTrip(ChargeInput chargeInput)
			throws Exception {
		if (chargeInput.getIsRoundTrip() == null) {
			throw new UnsupportedParamException(
					SOURCE_OF_MESSAGE
							+ " : The isRoundTrip data is empty ; expected one of [false, true]");
		} else {
			try {
				chargeInput.setIsRoundTrip(chargeInput.getIsRoundTrip());
			} catch (IllegalArgumentException e) {
				throw new UnsupportedParamException(
						SOURCE_OF_MESSAGE
								+ " : The isRoundTrip data is empty ; expected one of [false, true]");
			}
		}
	}

	private void testNotNullTierLevel(ChargeInput chargeInput) throws Exception {

		if (chargeInput.getTierLevel() == null) {
			throw new UnsupportedParamException(SOURCE_OF_MESSAGE
					+ " : The TierLevel data is empty");
		} else {
			String tlCode = chargeInput.getTierLevel().getTierLevelCode();
			if (StringMethods.isEmpty(tlCode)) {
				throw new UnsupportedParamException(
						" Given emty tierLevel code    ;  expected one  of "
								+ EnumMethods.enumToString(LOYALTY_TYPE.class));
			}
			if (!(EnumMethods.containsLower(LOYALTY_TYPE.class, tlCode) || EnumMethods
					.containsUpper(LOYALTY_TYPE.class, tlCode))
					&& !StringMethods.isEmpty(tlCode)) {
				throw new UnsupportedParamException(
						" Given unknown tierLevel code  '"
								+ chargeInput.getTierLevel().getTierLevelCode()
								+ "' ;" + " expected one  of "
								+ EnumMethods.enumToString(LOYALTY_TYPE.class));
			}
		}
	}

	private void testNotNullLocation(ChargeInput chargeInput) throws Exception {
		if (chargeInput.getOrigin() == null
				|| chargeInput.getDestination() == null) {
			throw new UnsupportedParamException(
					SOURCE_OF_MESSAGE
							+ " : One of the origin or  destination points data are empty");
		}
		testNotNullOrigin(chargeInput);

		testNotNullDestination(chargeInput);
	}

	private void testNotNullDestination(ChargeInput chargeInput)
			throws Exception {
		testNotNullAirport(chargeInput.getDestination().getAirport());

		// testNotNullAirport(chargeInput.getDestination().getCity() );
		// testNotNullAirport(chargeInput.getDestination().getCountry() );
		// testNotNullAirport(chargeInput.getDestination().getRegion() );

	}

	private void testNotNullOrigin(ChargeInput chargeInput) throws Exception {
		testNotNullAirport(chargeInput.getOrigin().getAirport());

		// testNotNullAirport(chargeInput.getOrigin().getCity() );
		// testNotNullAirport(chargeInput.getOrigin().getCountry() );
		// testNotNullAirport(chargeInput.getOrigin().getRegion() );

	}

	private void testNotNullAirline(ChargeInput chargeInput) throws Exception {

		if (chargeInput.getAirline() != null) {
			if (StringMethods
					.isEmpty(chargeInput.getAirline().getAirlineCode())) {
				throw new UnsupportedParamException(SOURCE_OF_MESSAGE
						+ " : Given incorrect Airline's code value is empty ");

			}
		}
	}

	private void testNotNullAirport(Airport airport) throws Exception {
		if (airport == null) {
			throw new UnsupportedParamException(SOURCE_OF_MESSAGE
					+ " : Given    origin or destination values are empty ");
		} else {
			if (StringMethods.isEmpty(airport.getAirportCode())) {
				throw new UnsupportedParamException(
						SOURCE_OF_MESSAGE
								+ " : Given   origin's or destination's code  values are emty ");
			}
		}
	}
}
