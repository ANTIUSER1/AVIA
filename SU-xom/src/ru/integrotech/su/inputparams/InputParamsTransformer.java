package ru.integrotech.su.inputparams;

import java.util.HashSet;
import java.util.Set;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.su.common.LocationType;
import ru.integrotech.su.inputparams.route.RoutesInput;
import ru.integrotech.su.utils.ValidatorInputData;

/**
 * Class for transform string to object data use for transform input parameters <br />
 * The main class, which retrieves the data from the requests
 * 
 * data ( private RegisterCache registerCache; )
 */
public class InputParamsTransformer {

	/**
	 * Static constructor creates the class instance, then sets the
	 * registerCache value
	 *
	 * @param registerCache
	 * @return
	 */

	public static InputParamsTransformer of(RegisterCache registerCache) {
		InputParamsTransformer res = new InputParamsTransformer();
		res.setRegisterCache(registerCache);
		return res;
	}

	private RegisterCache registerCache;

	private InputParamsTransformer() {
		this.registerCache = registerCache;
	}

	private void setRegisterCache(RegisterCache registerCache) {
		this.registerCache = registerCache;
	}

	/**
	 * Retrieve the origin Airport set from the input parameters
	 *
	 * <br />
	 * This method use in
	 * {@link ru.integrotech.su.outputparams.route.RoutesBuilder}
	 *
	 * @param routesInput
	 * @return
	 */
	public Set<Airport> getOrigins(RoutesInput routesInput) throws Exception {
		String originType = routesInput.getOriginType();
		String originCode = routesInput.getOriginCode();
		return this.getEndpoints(originCode, originType);
	}

	/**
	 * Retrieve the destination Airport set from the input parameters
	 *
	 * <br />
	 * This method use in
	 * {@link ru.integrotech.su.outputparams.route.RoutesBuilder}
	 *
	 * @param routesInput
	 * @return
	 */
	public Set<Airport> getDestinations(RoutesInput routesInput)
			throws Exception {
		String destinationType = routesInput.getDestinationType();

		String destinationCode = routesInput.getDestinationCode();
		return this.getEndpoints(destinationCode, destinationType);
	}

	/**
	 * Retrieve the information on the existence of destination data in request
	 *
	 * @param routesInput
	 * @return
	 */
	public boolean exactLocation(RoutesInput routesInput) {
		String locationCode = routesInput.getDestinationCode();
		String locationType = routesInput.getDestinationType();
		return locationCode != null
				&& (LocationType.airport.name().equals(locationType) || LocationType.city
						.name().equals(locationType));
	}

	/**
	 * Getting through the RegisterCache the data from the directory of airline
	 * (core project)
	 *
	 * @param routesInput
	 * @return
	 */
	public Airline getAirline(RoutesInput routesInput) {
		return this.registerCache.getAirline(routesInput.getAirlineCode());
	}

	/**
	 * Retrieve the core-project version of Airports' into the set<br / >
	 * (use RegisterCache)
	 *
	 * @param locationCode
	 * @param locationType
	 * @return
	 */
	private Set<Airport> getEndpoints(String locationCode, String locationType)
			throws Exception {
		Set<Airport> result = new HashSet<>();

		if (locationCode != null) {
			if (locationType.equals(LocationType.airport.toString())) {
				ValidatorInputData.testAirport(registerCache, locationCode);
				result.add(registerCache.getAirport(locationCode));
			} else if (locationType.equals(LocationType.city.toString())) {
				ValidatorInputData.testCity(registerCache, locationCode);
				result.addAll(registerCache.getCity(locationCode)
						.getAirportMap().values());
			} else if (locationType.equals(LocationType.country.toString())) {
				ValidatorInputData.testCountry(registerCache, locationCode);
				result.addAll(registerCache.getCountry(locationCode)
						.getAirportMap().values());
			} else if (locationType.equals(LocationType.region.toString())
						|| locationType.equals(LocationType.worldregion.toString())) {
				ValidatorInputData.testRegion(registerCache, locationCode);
				result.addAll(registerCache.getRegion(locationCode)
						.getAirportMap().values());
			}
		}
		return result;
	}
}
