package ru.aeroflot.fmc.utils;

import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.location.Airport;
import ru.aeroflot.fmc.register.RegisterCache;

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

    public Set<Airport> getEndpoints(String locationCode, String locationType) {
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

    public boolean exactLocation(String locationCode, String locationType) {
        return  locationCode != null &&
                (LocationType.airport.name().equals(locationType)
                || LocationType.city.name().equals(locationType));
    }

    public Airline getAirline(String airlineCode) {
        return this.registerCache.getAirline(airlineCode);
    }

    /* corresponds to location.Airport, location.City, location.Country and location.WorldRegion types*/
    public enum LocationType {airport, city, country, region}
}
