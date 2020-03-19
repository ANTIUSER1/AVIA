package ru.integrotech.su.mock;

import com.google.gson.JsonElement;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.su.inputparams.attractionAB.AttractionAbInput;
import ru.integrotech.su.inputparams.charge.ChargeInput;
import ru.integrotech.su.inputparams.spend.SpendInput;
import ru.integrotech.su.outputparams.charge.ChargeRoute;
import ru.integrotech.su.outputparams.spend.SpendLkRoute;
import ru.integrotech.su.outputparams.spend.SpendRoute;
import ru.integrotech.su.records.RouteRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class is used in tests for load some classes from JSONs that
 * uses only in tests and so cant be load by RegisterCache
 */
public class TestsCache{

    private RegisterCache registerCache;

    public TestsCache(RegisterCache registerCache) {
        this.registerCache = registerCache;
    }

    public TestsCache() {
        this.registerCache = new RegisterCache();
    }

    RegisterCache getRegisterCache() {
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

    public void update(String registerName, JsonElement jsonElement) {
        this.registerCache.update(registerName, jsonElement);
    }
}
