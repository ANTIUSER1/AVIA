package ru.integrotech.su.outputparams.route;

import java.util.List;
import java.util.Set;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.flight.Route;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.searcher.RouteSearcher;
import ru.integrotech.su.inputparams.InputParamsTransformer;
import ru.integrotech.su.inputparams.route.RoutesInput;

/**
 * container for RoutBuilder
 *
 * data( private RegisterCache cache; private InputParamsTransformer
 * paramsTransformer; private RouteSearcher routeSearcher; )
 */
public class RoutesBuilder {

	/**
	 * names of necessary registers
	 * */
	public static String[] getRegisterNames() {
		return REGISTER_NAMES;
	}

	/**
	 * names of necessary registers
	 * */
	private static final String[] REGISTER_NAMES = new String[] { "airline",
			"region", "country", "city", "airport", "pair",
			"serviceClassLimit", "bonusRoute" };

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param cache
	 * @return
	 */
	public static RoutesBuilder of(RegisterCache cache) {
		RoutesBuilder res = new RoutesBuilder();
		res.setCache(cache);
		res.setParamsTransformer(InputParamsTransformer.of(cache));
		res.setRouteSearcher(RouteSearcher.of());
		return res;
	}

	public void setCache(RegisterCache cache) {
		this.cache = cache;
	}

	public void setParamsTransformer(InputParamsTransformer paramsTransformer) {
		this.paramsTransformer = paramsTransformer;
	}

	public void setRouteSearcher(RouteSearcher routeSearcher) {
		this.routeSearcher = routeSearcher;
	}

	private RegisterCache cache;

	private InputParamsTransformer paramsTransformer;

	private RouteSearcher routeSearcher;

	public List<Route> getRoutes(RoutesInput routesInput) throws Exception {
		Set<Airport> origins = this.paramsTransformer.getOrigins(routesInput);
		Set<Airport> destinations = this.paramsTransformer
				.getDestinations(routesInput);
		boolean exactLocation = this.paramsTransformer
				.exactLocation(routesInput);
		Airline airline = this.paramsTransformer.getAirline(routesInput);
		List<Route> result = this.routeSearcher.searchRoutes(origins,
				destinations, airline, exactLocation);
		if (this.cache.getWrongRouteMap() != null) { // init wrong routes not in
														// all cases
			for (Route route : result) {
				if (!route.isDirect()) {
					route.setWrong(this.cache.getWrongRouteMap().containsKey(
							route.getCityCodes()));
				}
			}
		}
		return result;
	}
}
