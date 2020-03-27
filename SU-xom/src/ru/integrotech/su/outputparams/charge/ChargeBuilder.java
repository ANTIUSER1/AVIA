package ru.integrotech.su.outputparams.charge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.flight.FlightCarrier;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.info.PassengerChargeInfo;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.utils.NumberMethods;
import ru.integrotech.airline.utils.Releaser;
import ru.integrotech.su.exceptions.UnsupportedParamException;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.outputparams.route.RoutesBuilder;
import ru.integrotech.su.utils.ValidatorChargeData;

/**
 * this class takes input params (ChargeInput) and returns back output params
 * (ResultMilesCharge) logic is:
 *
 * var 1: ResultMilesCharge result = buildResult(ChargeInput chargeInput)<br />
 *
 * var 2:<br />
 * 
 * 1. List<Route> routes = getRoutes(ChargeInput chargeInput) <br />
 * int factor = getFactor(ChargeInput chargeInput);<br />
 * boolean isRound = chargeInput.isRoundTrip()<br />
 * 2. ODM rules with(routes, factor, isRound) - instead of method <br />
 * initFields(int factor, boolean isRound) in class PassengerChargeInfo <br />
 * 3. ResultMilesCharge result = buildResult(List<Route> routes, ChargeInput
 * chargeInput) <br />
 * 
 * 
 * <hr />
 * class for build chargeResponse from chargeRequest
 *
 * data(private RegisterCache cache; private RoutesBuilder routesBuilder;)
 */
public class ChargeBuilder {

	/**
	 * names of necessary registers
	 */
	public static String[] getRegisterNames() {
		return REGISTER_NAMES;
	}

	/**
	 * names of necessary registers
	 */
	private static final String[] REGISTER_NAMES = new String[] { "airline",
			"region", "country", "city", "airport", "pair",
			"serviceClassLimit", "tariff", "bonusRoute", "award", "tierLevel" };

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param cache
	 * @return
	 */
	public static ChargeBuilder of(RegisterCache cache) {
		ChargeBuilder res = new ChargeBuilder();
		res.setCache(cache);
		res.setRoutesBuilder(RoutesBuilder.of(cache));
		return res;
	}

	private RegisterCache cache;

	private RoutesBuilder routesBuilder;

	public void setRoutesBuilder(RoutesBuilder routesBuilder) {
		this.routesBuilder = routesBuilder;
	}

	public void setCache(RegisterCache cache) {
		this.cache = cache;
	}

	/* method for use in ODM */
	public ResultMilesCharge buildResult(ChargeInput chargeInput)
			throws Exception {
		List<ChargeRoute> routes = this.getChargeRoutes(chargeInput);
		return ResultMilesCharge.of(routes);
	}

	/* method for use in ODM */
	public ResultMilesCharge buildResult(List<Route> routes,
			ChargeInput chargeInput) {
		List<ChargeRoute> chargeRoutes = this.buildChargeRoutes(routes,
				chargeInput);
		return ResultMilesCharge.of(chargeRoutes);
	}

	/* method for use in TESTS */
	public List<ChargeRoute> getChargeRoutes(ChargeInput chargeInput)
			throws Exception {
		List<Route> routes = this.getRoutes(chargeInput);
		this.buildPassengerCharges(routes, chargeInput, true);
		return this.buildChargeRoutes(routes, chargeInput);
	}

	public List<Route> getRoutes(ChargeInput chargeInput) throws Exception {
		ValidatorChargeData.testValidAirline(cache, chargeInput);
		RoutesInput routesInput = RoutesInput.of(chargeInput);
		List<Route> routes = this.routesBuilder.getRoutes(routesInput);
		this.buildPassengerCharges(routes, chargeInput, false);
		return routes;
	}

	public int getFactor(ChargeInput chargeInput) throws Exception {

		String tierLevelCode = null;

		if (chargeInput.getTierLevel() != null) {
			tierLevelCode = chargeInput.getTierLevel().getTierLevelCode();
		}

		if (cache.getLoyaltyMap().get(tierLevelCode) == null) {
			Releaser.release();
			throw new UnsupportedParamException(
					" Given unknown  tierLevelCode  " + tierLevelCode
							+ "  ; expected one of "
							+ cache.getLoyaltyMap().keySet() + "");

		}
		return this.cache.getLoyaltyMap().get(tierLevelCode).getFactor();
	}

	public int getPercent(int value, int percent) {
		return NumberMethods.getPercent(value, percent);
	}

	private List<ChargeRoute> buildChargeRoutes(List<Route> routes,
			ChargeInput chargeInput) {

		String airlineCode = this.getAirlineCode(chargeInput);
		Airline airline = this.cache.getAirline(airlineCode);

		List<ChargeRoute> result = new ArrayList<>();

		for (Route route : routes) {
			result.add(ChargeRoute.of(route, airline));
		}

		Collections.sort(result);
		return result;
	}

	private void buildPassengerCharges(List<Route> routes,
			ChargeInput chargeInput, boolean initFields) throws Exception {

		String airlineCode = this.getAirlineCode(chargeInput);
		Airline airline = this.cache.getAirline(airlineCode);
		if (airline != null) {
			for (Route route : routes) {
				this.buildPassengerCharges(route, chargeInput, airline,
						initFields);
			}
		} else {

			for (Route route : routes) {
				for (Airline carrier : route.getAirlines()) {
					this.buildPassengerCharges(route, chargeInput, carrier,
							initFields);
				}
			}
		}

	}

	private void buildPassengerCharges(Route route, ChargeInput chargeInput,
			Airline airline, boolean initFields) throws Exception {

		List<ServiceClass> allowedClasses = this.getServiceClasses(route,
				airline);
		for (Flight flight : route.getFlights(airline)) {
			this.buildPassengerCharges(flight, allowedClasses, chargeInput,
					airline, initFields);
		}
	}

	private void buildPassengerCharges(Flight flight,
			List<ServiceClass> allowedClasses, ChargeInput chargeInput,
			Airline airline, boolean initFields) throws Exception {

		boolean isRound = chargeInput.getIsRoundTrip();
		int factor;
		if (initFields) {
			factor = this.getFactor(chargeInput);
		} else {
			factor = -1;
		}

		this.buildPassengerCharges(flight.getCarriers().get(airline),
				allowedClasses, flight, factor, isRound);
	}

	private void buildPassengerCharges(Flight flight,
			List<ServiceClass> allowedClasses, ChargeInput chargeInput,
			boolean initFields) throws Exception {

		boolean isRound = chargeInput.getIsRoundTrip();
		int factor;
		if (initFields) {
			factor = this.getFactor(chargeInput);
		} else {
			factor = -1;
		}

		for (FlightCarrier carrier : flight.getCarriers().values()) {
			this.buildPassengerCharges(carrier, allowedClasses, flight, factor,
					isRound);
		}
	}

	private void buildPassengerCharges(FlightCarrier carrier,
			List<ServiceClass> allowedClasses, Flight flight, int factor,
			boolean isRound) {
		carrier.setPassengerChargeInfos(PassengerChargeInfo.listOf(flight,
				allowedClasses, factor, isRound, carrier.getCarrier()));
	}

	private String getAirlineCode(ChargeInput chargeInput) {
		String result = null;
		if (chargeInput.getAirline() != null) {
			result = chargeInput.getAirline().getAirlineCode();
		}
		return result;
	}

	private List<ServiceClass> getServiceClasses(Route route, Airline airline) {

		List<ServiceClass.SERVICE_CLASS_TYPE> serviceClassTypes = new ArrayList<>(
				route.getCommonRouteClasses(airline));

		if (serviceClassTypes.isEmpty()) {
			serviceClassTypes.addAll(airline.getServiceClassMap().keySet());
		}

		List<ServiceClass> result = new ArrayList<>();

		for (ServiceClass.SERVICE_CLASS_TYPE type : serviceClassTypes) {
			result.add(airline.getServiceClassMap().get(type));
		}

		Collections.sort(result, Collections.reverseOrder());

		return result;
	}

}
