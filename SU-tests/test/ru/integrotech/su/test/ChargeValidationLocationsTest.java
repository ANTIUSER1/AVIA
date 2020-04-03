package ru.integrotech.su.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.integrotech.su.common.Airline;
import ru.integrotech.su.common.Airport;
import ru.integrotech.su.common.Location;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.inputparams.charge.TierLevel;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.charge.ChargeBuilder;
import ru.integrotech.su.utils.ValidatorCharge;

public class ChargeValidationLocationsTest {
	@BeforeClass
	public static void updateRegisters() {
		MockLoader.getInstance().updateRegisters(
				MockLoader.REGISTERS_TYPE.MOCK,
				ChargeBuilder.getRegisterNames());
	}

	private ChargeBuilder chargeBuilder;
	private ValidatorCharge validatorCharge;

	private Airline airline = new Airline();
	private Airport airportOrigin = new Airport();
	private Airport airportDestination = new Airport();
	private Location locationOrigin = new Location();
	private Location locationDestination = new Location();
	private TierLevel tierLevel = new TierLevel();
	private Boolean isRoundTrip;

	@Before
	public void init() {

		this.chargeBuilder = ChargeBuilder.of(MockLoader.getInstance()
				.getRegisterCache());
		this.validatorCharge = new ValidatorCharge();

		this.airline.setAirlineCode("SU");
		this.tierLevel.setTierLevelCode("gold");
		this.isRoundTrip = false;

		this.airportOrigin.setAirportCode("SVO");
		this.locationOrigin.setAirport(this.airportOrigin);
		this.airportDestination.setAirportCode("VVO");
		this.locationDestination.setAirport(airportDestination);

	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(null);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(locationOrigin);
		chargeInput.setDestination(null);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIG_DEST_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(null);
		chargeInput.setDestination(null);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_AIRPORT_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		locationOrigin.setAirport(null);
		chargeInput.setOrigin(locationOrigin);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_AIRPORT_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(locationOrigin);
		locationDestination.setAirport(null);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_AIRPORT_CODE_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		locationOrigin.getAirport().setAirportCode(null);
		chargeInput.setOrigin(locationOrigin);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_AIRPORT_CODE_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(locationOrigin);
		locationDestination.getAirport().setAirportCode(null);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_AIRPORT_CODE_EMPTY() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		locationOrigin.getAirport().setAirportCode("");
		chargeInput.setOrigin(locationOrigin);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_AIRPORT_CODE_EMPTY() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(locationOrigin);
		locationDestination.getAirport().setAirportCode("      ");
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void ORIGIN_AIRPORT_CODE_INCORRECT() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		locationOrigin.getAirport().setAirportCode("++INCORRECT   ");
		chargeInput.setOrigin(locationOrigin);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		chargeBuilder.getChargeRoutes(chargeInput);
	}

	@Test(expected = UnsupportedParamException.class)
	public void DEST_AIRPORT_CODE_INCORRECT() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(locationOrigin);
		locationDestination.getAirport().setAirportCode("   INCORRECT+++   ");
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(isRoundTrip);
		chargeBuilder.getChargeRoutes(chargeInput);
		;
	}

}
