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

public class ChargeValidationRoundTripTest {

	@BeforeClass
	public static void updateRegisters() {
		MockLoader.getInstance().updateRegisters(
				MockLoader.REGISTERS_TYPE.MOCK,
				ChargeBuilder.getRegisterNames());
	}

	// private RegisterCache cache;
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
	public void ISROUDDTRIP_NULL() throws Exception {

		ChargeInput chargeInput = new ChargeInput();

		chargeInput.setAirline(airline);
		chargeInput.setOrigin(locationOrigin);
		chargeInput.setDestination(locationDestination);
		chargeInput.setTierLevel(tierLevel);
		chargeInput.setIsRoundTrip(null);
		validatorCharge.validateHardDataCharge(chargeInput);
	}

}
