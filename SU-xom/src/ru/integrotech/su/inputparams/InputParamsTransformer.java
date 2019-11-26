package ru.integrotech.su.inputparams;


import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.su.common.LocationType;
import ru.integrotech.su.inputparams.route.RoutesInput;

import java.util.HashSet;
import java.util.Set;

/* class for transform string to object data
* use for transform input parameters*/
public class InputParamsTransformer {

    public static InputParamsTransformer of(RegisterCache registerCache) {
        return new InputParamsTransformer(registerCache);
    }

    private final RegisterCache registerCache;

    private InputParamsTransformer(RegisterCache registerCache) {
        this.registerCache = registerCache;
    }

    public Set<Airport> getOrigins(RoutesInput routesInput) {
        String originType = routesInput.getOriginType();
        String originCode = routesInput.getOriginCode();
        return this.getEndpoints(originCode, originType);
    }

    public Set<Airport> getDestinations(RoutesInput routesInput) {
        String destinationType = routesInput.getDestinationType();
        String destinationCode = routesInput.getDestinationCode();
        return this.getEndpoints(destinationCode, destinationType);
    }

    public boolean exactLocation(RoutesInput routesInput) {
        String locationCode = routesInput.getDestinationCode();
        String locationType = routesInput.getDestinationType();
        return  locationCode != null &&
                (LocationType.airport.name().equals(locationType)
                || LocationType.city.name().equals(locationType));
    }

    public Airline getAirline(RoutesInput routesInput) {
        return this.registerCache.getAirline(routesInput.getAirlineCode());
    }

    private Set<Airport> getEndpoints(String locationCode, String locationType) {
        Set<Airport> result = new HashSet<>();
        if (locationCode != null) {
            if (locationType.equals(LocationType.airport.toString())) {
                result.add(registerCache.getAirport(locationCode));
            } else if (locationType.equals(LocationType.city.toString())) {
                result.addAll(registerCache.getCity(locationCode).getAirportMap().values());
            } else if (locationType.equals(LocationType.country.toString())) {
                result.addAll(registerCache.getCountry(locationCode).getAirportMap().values());
            } else if (locationType.equals(LocationType.region.toString())) {
                result.addAll(registerCache.getRegion(locationCode).getAirportMap().values());
            }
        }
        return result;
    }


}
