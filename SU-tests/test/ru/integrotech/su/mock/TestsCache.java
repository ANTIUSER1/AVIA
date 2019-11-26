package ru.integrotech.su.mock;

import com.google.gson.JsonElement;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.airline.core.bonus.Loyalty;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.core.location.City;
import ru.integrotech.airline.core.location.Country;
import ru.integrotech.airline.core.location.WorldRegion;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.outputparams.charge.ChargeRoute;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;
import ru.integrotech.su.outputparams.spend.SpendRoute;
import ru.integrotech.su.records.RouteRecord;







import java.util.*;

/*class is used in tests for load some classes from JSONs that
uses only in tests and so cant be load by RegisterCache */
public class TestsCache{

    private RegisterCache registerCache;

    public TestsCache(RegisterCache registerCache) {
        this.registerCache = registerCache;
    }

    public TestsCache() {
        this.registerCache = new RegisterCache();
    }

    public RegisterCache getRegisters() {
        return registerCache;
    }

    public List<ru.integrotech.airline.core.flight.Route> loadRoutes(JsonElement jsonElement, RegisterCache registerCache) {
        RouteRecord[] routeRecords = this.registerCache.parseJsonElement(RouteRecord[].class, jsonElement);
        List<ru.integrotech.airline.core.flight.Route> result = new ArrayList<>();
        for (RouteRecord record : routeRecords) {
            result.add(record.toRoute(registerCache));
        }
        return result;
    }

    public List<ChargeRoute> loadChargeRoutes(JsonElement jsonElement) {
        ChargeRoute[] chargeRouteRecords = this.registerCache.parseJsonElement(ChargeRoute[].class, jsonElement);
        return Arrays.asList(chargeRouteRecords);
    }

    public List<SpendRoute> loadSpendRoutes(JsonElement jsonElement) {
        SpendRoute[] spendRouteRecords = this.registerCache.parseJsonElement(SpendRoute[].class, jsonElement);
        return Arrays.asList(spendRouteRecords);
    }

    public List<SpendLkRoute> loadSpendLkRoutes(JsonElement jsonElement) {
        SpendLkRoute[] spendLkRouteRecords = this.registerCache.parseJsonElement(SpendLkRoute[].class, jsonElement);
        return Arrays.asList(spendLkRouteRecords);
    }

    public ChargeInput loadChargeInputParams(JsonElement jsonElement) {
        return this.registerCache.parseJsonElement(ChargeInput.class, jsonElement);
    }

    public SpendInput loadSpendInputParams(JsonElement jsonElement) {
        return this.registerCache.parseJsonElement(SpendInput.class, jsonElement);
    }
    
    public AttractionAbInput loadAttractionAbInputParams(JsonElement jsonElement) {
        return this.registerCache.parseJsonElement(AttractionAbInput.class, jsonElement);
    }

    public Airline getAirline(String airlineCode) {
        return this.registerCache.getAirline(airlineCode);
    }

    public <E> Map<String, Loyalty> getLoyaltyMap() {
        return this.registerCache.getLoyaltyMap();
    }

    public Map<String, List<City>> getWrongRouteMap() {
        return this.registerCache.getWrongRouteMap();
    }

    public Collection<WorldRegion> getRegions() {
        return this.registerCache.getRegions();
    }

    public Collection<Country> getCountries() {
        return this.registerCache.getCountries();
    }

    public Collection<City> getCities() {
        return this.registerCache.getCities();
    }

    public Collection<Airport> getAirports() {
        return this.registerCache.getAirports();
    }

    public Collection<Loyalty> getLoyalties() {
        return this.registerCache.getLoyalties();
    }

    public Map<String, Set<Bonus>> getBonusRouteMap() {
        return this.registerCache.getBonusRouteMap();
    }

    public Collection<Airline> getAirlines() {
        return this.registerCache.getAirlines();
    }

    public void update(RegisterCache.Type type, JsonElement jsonElement) {
        this.registerCache.update(type, jsonElement);
    }
}
