package ru.integrotech.su.utils;

import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.utils.AWARD_TYPE;
import ru.integrotech.airline.utils.EnumMethods;
import ru.integrotech.airline.utils.StringMethods;
import ru.integrotech.su.common.LocationType;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.spend.SpendInput;

/**
 * 
 * Validator of the request spend data, that are independent of directory data
 * (e.g. correct names of location, existing fields etc )
 *
 */
public class ValidatorSpend {
	private static final String SOURCE_OF__MESSAGE = ValidatorSpend.class
			.toString();

	public void validateHardDataSpend(SpendInput spendInput) throws Exception {
		spendInput.initDefaultParameters();
		testNotNullLocation(spendInput);
		testNotNullServiceClass(spendInput);
		testValidLocation(spendInput);
		testValidMiles(spendInput);
		testValidAwards(spendInput);
		testValidLang(spendInput);
	}

	private void testNotNullServiceClass(SpendInput spendInput)
			throws Exception {
		if (spendInput.getClassOfService() != null) {
			String serviceClassName = spendInput.getClassOfService().getClassOfServiceName();
			if (StringMethods.isEmpty(serviceClassName)) {
				throw new UnsupportedParamException(SOURCE_OF__MESSAGE
						+ " : Given incorrect ClassOfService value is empty ");
			} else {
				try {
					ServiceClass.SERVICE_CLASS_TYPE.valueOf(serviceClassName);
				} catch (IllegalArgumentException e) {
					throw new UnsupportedParamException(SOURCE_OF__MESSAGE
							+ " : Incorrect input classOfServiceName value." + " Given '"
							+ serviceClassName + "' ; expected one of "
							+ EnumMethods.enumToString(ServiceClass.SERVICE_CLASS_TYPE.class));
				}
			}
		}
	}

	private void testValidLang(SpendInput spendInput) throws Exception {
		if (spendInput.getLang() == null) {
			throw new UnsupportedParamException(SOURCE_OF__MESSAGE
					+ " : The Lang point data is empty");
		}
	}

	private void testValidMiles(SpendInput spendInput) throws Exception {
		if (spendInput.getMilesInterval() != null) {
			int minMil = spendInput.getMilesInterval().getMilesMin();
			int maxMil = spendInput.getMilesInterval().getMilesMax();
			if (maxMil < 0 || maxMil <= minMil)
				throw new UnsupportedParamException(SOURCE_OF__MESSAGE
						+ " : Given incorrect milesInterval data: milesMin="
						+ minMil + "   milesMax=" + maxMil);
		}
	}

	private void testNotNullLocation(SpendInput spendInput) throws Exception {

		testNotNullOrigin(spendInput);

		testNotNullDestination(spendInput);
	}

	private void testNotNullDestination(SpendInput spendInput)
			throws UnsupportedParamException {
		if (spendInput.getDestination() != null) {
			if (StringMethods.isEmpty(spendInput.getDestination().getLocationType()) &&
                    !StringMethods.isEmpty(spendInput.getDestination().getLocationCode())) {
				throw new UnsupportedParamException(SOURCE_OF__MESSAGE
						+ " : The type of destination point data is empty");
			}
			if (StringMethods.isEmpty(spendInput.getDestination().getLocationCode()) &&
                    !StringMethods.isEmpty(spendInput.getDestination().getLocationType())) {
				throw new UnsupportedParamException(SOURCE_OF__MESSAGE
						+ " : The code of destination point data is empty");
			}
		}
	}

	private void testNotNullOrigin(SpendInput spendInput)
			throws UnsupportedParamException {
		if (spendInput.getOrigin() == null) {
			throw new UnsupportedParamException(SOURCE_OF__MESSAGE
					+ " : The origin point data is empty");
		}
		if (StringMethods.isEmpty(spendInput.getOrigin().getLocationType())) {
			throw new UnsupportedParamException(SOURCE_OF__MESSAGE
					+ " : The type of origin point data does not exists");
		}

		if (StringMethods.isEmpty(spendInput.getOrigin().getLocationCode())) {
			throw new UnsupportedParamException(SOURCE_OF__MESSAGE
					+ " : The code of origin point data is empty");
		}
	}

	private void testValidLocation(SpendInput spendInput) throws Exception {

		String locationType = spendInput.getOrigin().getLocationType();
		if (!EnumMethods.containsLower(LocationType.class, locationType))
			throw new UnsupportedParamException(
					SOURCE_OF__MESSAGE
							+ " : Given "
							+ locationType
							+ " for origin locationType value; expected one of the values: "
							+ EnumMethods.enumToString(LocationType.class));

		if (spendInput.getDestination() != null) {
			locationType = spendInput.getDestination().getLocationType();

			if (locationType != null) {

                if (!EnumMethods.containsLower(LocationType.class, locationType))
                    throw new UnsupportedParamException(
                            " Given  "
                                    + locationType
                                    + " for destination locationType value; expected one of the values: "
                                    + EnumMethods.enumToString(LocationType.class));
            }
		}
	}

	private void testValidAwards(SpendInput spendInput) throws Exception {
		try {
			AWARD_TYPE.valueOf(spendInput.getAwardType());
		} catch (IllegalArgumentException e) {
			throw new UnsupportedParamException(SOURCE_OF__MESSAGE
					+ " : Incorrect input AWARD_TYPE value." + " Given '"
					+ spendInput.getAwardType() + "' ; expected one of "
					+ EnumMethods.enumToString(AWARD_TYPE.class));
		}
	}
}
