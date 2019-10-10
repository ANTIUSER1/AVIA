package ru.aeroflot.fmc.io.charge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.aeroflot.fmc.io.RoutesBuilder;
import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.bonus.Loyalty;
import ru.aeroflot.fmc.model.flight.Flight;
import ru.aeroflot.fmc.model.flight.Route;
import ru.aeroflot.fmc.register.RegisterCache;
import ru.aeroflot.fmc.register.RegisterLoader;

public class ChargeBuilder {

	public static ChargeBuilder of(RegisterCache cache) {
		return new ChargeBuilder(cache);
	}

	public static ChargeBuilder of() {
		return new ChargeBuilder();
	}

	private RegisterCache cache;

	private RoutesBuilder routesBuilder;

	public RoutesBuilder getRoutesBuilder() {
		return routesBuilder;
	}

	public void setRoutesBuilder(RoutesBuilder routesBuilder) {
		this.routesBuilder = routesBuilder;
	}

	public RegisterCache getCache() {
		return cache;
	}

	private ChargeBuilder(RegisterCache cache) {
		this.cache = cache;
		this.routesBuilder = RoutesBuilder.of(cache);
	}

	private ChargeBuilder() {
		RegisterLoader loader = RegisterLoader.getInstance();
		loader.lock();
		this.cache = loader.getRegisterCache();
		this.routesBuilder = RoutesBuilder.of(cache);
		loader.release();
	}

	public List<ChargeRoute> getChargeRoutes(String from, String fromType,
			String to, String toType, String airlineCode, String loyaltyLevel,
			boolean isRound) {

		List<Route> routes = this.routesBuilder.getRoutes(from, fromType, to,
				toType, airlineCode);
		Airline airline = this.cache.getAirline(airlineCode);
		Loyalty loyalty = this.cache.getLoyaltyMap().get(loyaltyLevel);

		for (Route route : routes) {
			for (Flight flight : route.getFlights(airline)) {
				flight.buildPassengerCharges(loyalty.getFactor(), isRound,
						airline);
			}
		}
		return this.getChargeRoutes(routes, airline);
	}

	public List<ChargeRoute> getChargeRoutes(ChargeInput chargeInput) {

		return this.getChargeRoutes(chargeInput.getOriginCode(), // from
				chargeInput.getOriginType(), // from type
				chargeInput.getDestinationCode(), // to
				chargeInput.getDestinationType(), // to type
				chargeInput.getAirlineCode(), // airline
				chargeInput.getTierLevelCode(), // tierLevelCode
				chargeInput.isRoundTrip() // is round
				);
	}

	public ResultMilesCharge buildResult(ChargeInput chargeInput) {
		List<ChargeRoute> routes = this.getChargeRoutes(chargeInput);
		return ResultMilesCharge.of(routes);
	}

	public ResultMilesCharge buildResult(List<Route> routes,
			ChargeInput chargeInput) {
		Airline airline = this.cache.getAirline(chargeInput.getAirlineCode());
		List<ChargeRoute> chargeRoutes = this.getChargeRoutes(routes, airline);
		return ResultMilesCharge.of(chargeRoutes);
	}

	public List<Route> getRoutes(ChargeInput chargeInput) {
		System.out
				.println("+++++++++++++++   +++++++++++++++   " + chargeInput);
		List<Route> routes = this.routesBuilder.getRoutes(
				chargeInput.getOriginCode(), chargeInput.getOriginType(),
				chargeInput.getDestinationCode(),
				chargeInput.getDestinationType(), chargeInput.getAirlineCode());
		Airline airline = this.cache.getAirline(chargeInput.getAirlineCode());

		for (Route route : routes) {
			for (Flight flight : route.getFlights(airline)) {
				flight.buildPassengerCharges(this.getFactor(chargeInput),
						chargeInput.isRoundTrip(), airline);
			}
		}

		return routes;
	}

	private List<ChargeRoute> getChargeRoutes(List<Route> routes,
			Airline airline) {
		List<ChargeRoute> result = new ArrayList<>();

		for (Route route : routes) {
			result.add(ChargeRoute.of(route, airline));
		}

		Collections.sort(result);
		return result;
	}

	public int getFactor(ChargeInput chargeInput) {

		return this.cache.getLoyaltyMap().get(chargeInput.getTierLevelCode())
				.getFactor();
	}
}
