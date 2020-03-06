package ru.integrotech.airline.register;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.airline.SubTariff;
import ru.integrotech.airline.core.airline.Tariff;
import ru.integrotech.airline.core.bonus.*;
import ru.integrotech.airline.core.flight.Flight;
import ru.integrotech.airline.core.location.Airport;
import ru.integrotech.airline.core.location.City;
import ru.integrotech.airline.core.location.Country;
import ru.integrotech.airline.core.location.WorldRegion;
import ru.integrotech.airline.utils.StringMethods;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/* very important class that holds all registers */
public class RegisterCache {

    private static final Logger log = Logger.getLogger(RegisterCache.class.getName());

    private Map<String, Airline> airlineMap;

    private Map<String, Set<Bonus>> bonusRouteMap;

    private Map<String, Airport> airportMap;

    private Map<String, City> cityMap;

    private Map<String, Country> countryMap;

    private Map<String, WorldRegion> worldRegionMap;

    private Map<String, List<City>> wrongRouteMap;

    private Map<String, Loyalty> loyaltyMap;

    private Set<String> invalidAirportCodes;
    
    private List<MilesRule> milesRules;

    private Map<String, Integer> loyaltyLevelCodeMap;

    public Collection<Airline> getAirlines() {
        return this.airlineMap.values();
    }

    public Map<String, Set<Bonus>> getBonusRouteMap() {
        return bonusRouteMap;
    }

    public Collection<Airport> getAirports() {
        return  this.airportMap.values();
    }

    public Collection<City> getCities() {
        return this.cityMap.values();
    }

    public Collection<Country> getCountries() {
        return this.countryMap.values();
    }

    public Collection<WorldRegion> getRegions() {
        return this.worldRegionMap.values();
    }

    public Map<String, List<City>> getWrongRouteMap() {
        return wrongRouteMap;
    }

    public Collection<Loyalty> getLoyalties() {
        return this.loyaltyMap.values();
    }

    public Airline getAirline(String code) {
        return this.airlineMap.get(code);
    }

    public Airport getAirport(String code) {
        return this.airportMap.get(code);
    }

    public City getCity(String code) {
        return this.cityMap.get(code);
    }

    public Country getCountry(String code) {
        return this.countryMap.get(code);
    }

    public WorldRegion getRegion(String code) {
        return this.worldRegionMap.get(code);
    }

    public boolean isWrongRoute(String code) {
        return this.wrongRouteMap.containsKey(code);
    }

    public Set<Bonus> getBonusRoute(String code) {
        return this.bonusRouteMap.get(code);
    }

    public Map<String, Loyalty> getLoyaltyMap() {
        return loyaltyMap;
    }
    
    public List<MilesRule> getMilesRules() {
		return milesRules;
	}

    public Map<String, Integer> getLoyaltyLevelCodeMap() {
        return loyaltyLevelCodeMap;
    }

    public void update(String registerName, JsonElement jsonElement) {
        switch (registerName) {
            case "airline":
                this.initAirlineMap(jsonElement);
                break;
            case "city":
                this.initCityMap(jsonElement);
                break;
            case "award":
                this.initBonuses(jsonElement);
                break;
            case "pair":
                this.initFlights(jsonElement);
                break;
            case "tariff":
                this.initTariff(jsonElement);
                break;
            case "airport":
                this.initAirportMaps(jsonElement);
                break;
            case "country":
                this.initCountryMap(jsonElement);
                break;
            case "bonusRoute":
                this.initBonusRouteMap(jsonElement);
                break;
            case "region":
                this.initWorldRegionMap(jsonElement);
                break;
            case "wrongRoute":
                this.initWrongRoute(jsonElement);
                break;
            case "serviceClassLimit":
                this.initClassLimit(jsonElement);
                break;
            case "tierLevel":
                this.initLoyalty(jsonElement);
                break;
            case "mileAccrualRule":
                this.initMilesRules(jsonElement);
                break;
            case "localLoyaltyLevelCode":
                this.initLoyaltyLevelCodes(jsonElement);
                break;
        }
    }

    public void clean() {
        this.airlineMap = null;
        this.bonusRouteMap = null;
        this.airportMap = null;
        this.cityMap = null;
        this.countryMap = null;
        this.worldRegionMap = null;
        this.wrongRouteMap = null;
        this.loyaltyMap = null;
        this.invalidAirportCodes = null;
        this.milesRules = null;
        this.loyaltyLevelCodeMap = null;
    }


    private void initAirlineMap(JsonElement jsonElement) {
        AirlineRecord[] airlineRecords = parseJsonElement(AirlineRecord[].class, jsonElement);
        this.airlineMap = new HashMap<>();
        for (AirlineRecord record : airlineRecords) {
            if (record.isEmptyCode()) {
                log.log(Level.WARNING, "missing code in " + record.toString());
                continue;
            }
            this.airlineMap.put(record.getCode(), record.toAirline());
        }
    }

    private void initWorldRegionMap(JsonElement jsonElement) {
        RegionRecord[] regionRecords = parseJsonElement(RegionRecord[].class, jsonElement);
        this.worldRegionMap = new HashMap<>();
        for (RegionRecord record : regionRecords) {
            if (record.isEmptyCode()) {
                log.log(Level.WARNING, "missing code in " + record.toString());
                continue;
            }
            this.worldRegionMap.put(record.getCode(), record.toWorldRegion());
        }
    }

    private void initCountryMap(JsonElement jsonElement) {
        CountryRecord[] countryRecords = parseJsonElement(CountryRecord[].class, jsonElement);
        this.countryMap = new HashMap<>();
        for (CountryRecord record : countryRecords) {
            if (record.isEmptyCode()) {
                log.log(Level.WARNING, "missing code in " + record.toString());
                continue;
            }
            if (record.isEmptyRegionCode()) {
                log.log(Level.WARNING, "missing region code in " + record.toString());
            }
            this.countryMap.put(record.getCode(), record.toCountry(this.worldRegionMap.get(record.getRegionCode())));
        }
    }

    private void initCityMap(JsonElement jsonElement) {
        CityRecord[] cityRecords = parseJsonElement(CityRecord[].class, jsonElement);
        this.cityMap = new HashMap<>();
        for (CityRecord record : cityRecords) {
            if (record.isEmptyCode()) {
                log.log(Level.WARNING, "missing code in " + record.toString());
                continue;
            }
            this.cityMap.put(record.getCode(), record.toCity(this.countryMap.get(record.getCountryCode())));
        }

        for (Country country : this.countryMap.values()) {
            country.setCapital(this.cityMap.get(country.getCapitalCode()));
        }
    }

    private void initAirportMaps(JsonElement jsonElement) {
        AirportRecord[] airportRecords = parseJsonElement(AirportRecord[].class, jsonElement);
        this.airportMap = new HashMap<>();
        this.invalidAirportCodes = new HashSet<>();

        for (AirportRecord record : airportRecords) {
            if (record.isEmptyCode()) {
                log.log(Level.WARNING, "missing code in " + record.toString());
                continue;
            }
            if (record.getCityCode() == null || record.getCityCode().isEmpty()) {
                this.invalidAirportCodes.add(record.getCode());
                log.log(Level.WARNING, "missing city_code in " + record.toString());
                continue;
            }

            String airportCode = record.getCode();
            City city = this.cityMap.get(record.getCityCode());
            Airport airport = record.toAirport(city);

            this.airportMap.put(airportCode, airport);
            city.getAirportMap().put(airportCode, airport);

            Country country = city.getCountry();
            this.countryMap.get(country.getCode()).getAirportMap().put(airportCode, airport);

            WorldRegion worldRegion = country.getWorldRegion();
            if (worldRegion == null) {
                log.warning("Could not find region for country " + country.getCode());
            } else if (this.worldRegionMap.containsKey(worldRegion.getCode())) {
                this.worldRegionMap.get(country.getWorldRegion().getCode()).getAirportMap().put(airportCode, airport);
            } else {
                log.warning("Could not find region airports for region " + worldRegion.getCode() + " and country " + country.getCode());
            }
        }
    }

    private void initTariff(JsonElement jsonElement) {
        TariffRecord[] tariffRecords = parseJsonElement(TariffRecord[].class, jsonElement);

        for (TariffRecord record : tariffRecords) {
            Airline airline = this.airlineMap.get(record.getAirlineCode());
            ServiceClass.SERVICE_CLASS_TYPE serviceClassType = ServiceClass.SERVICE_CLASS_TYPE.valueOf(record.getSvcClassCode());
            ServiceClass serviceClass = airline.getServiceClassMap().get(serviceClassType);

            if (serviceClass == null) {
                ServiceClass.SERVICE_CLASS_TYPE classType = ServiceClass.SERVICE_CLASS_TYPE.valueOf(record.getSvcClassCode());
                airline.getServiceClassMap().put(classType, record.toServiceClass());
            } else {
                Tariff tariff = serviceClass.getTariffMap().get(record.getFareGroup());

                if (tariff == null) {
                    serviceClass.getTariffMap().put(record.getFareGroup(), record.toTariff());
                } else {
                    Set<SubTariff> subTariffs = tariff.getSubTariffsMap().get(Integer.valueOf(record.getChargeCoef()));
                    if (subTariffs != null) {
                        subTariffs.add(record.toSubTariff());
                    } else {
                        subTariffs = new HashSet<>();
                        subTariffs.add(record.toSubTariff());
                        tariff.getSubTariffsMap().put(Integer.valueOf(record.getChargeCoef()), subTariffs);                    }
                }
            }
        }
    }

    private void initBonusRouteMap(JsonElement jsonElement) {
        BonusRouteRecord[] bonusRouteRecords = parseJsonElement(BonusRouteRecord[].class, jsonElement);
        this.bonusRouteMap = new HashMap<>();
        for (BonusRouteRecord record : bonusRouteRecords) {
            this.bonusRouteMap.put(record.getNativeCode(), new HashSet<>());
            this.bonusRouteMap.put(record.getReverseCode(), new HashSet<>());
        }
    }

    private void initBonuses(JsonElement jsonElement) {
        BonusRecord[] bonusRecords = parseJsonElement(BonusRecord[].class, jsonElement);
        for (BonusRecord record : bonusRecords) {
            String routeKey = record.createRouteKey();
            if (this.bonusRouteMap.containsKey(routeKey)) {
                Set<Bonus> bonuses =  this.bonusRouteMap.get(routeKey);
                Bonus newBonus = record.toBonus();
                addIfFresh(bonuses, newBonus);
            }
        }
    }

    private void addIfFresh(Set<Bonus> bonuses, Bonus newBonus) {
        Iterator<Bonus> iterator = bonuses.iterator();
        while (iterator.hasNext()) {
            Bonus savedBonus = iterator.next();
            if (savedBonus.equalsIgnoreValue(newBonus)) {
                if (newBonus.getValidFrom().after(savedBonus.getValidFrom())) {
                    iterator.remove();
                } else return;
            }
        }
        bonuses.add(newBonus);
    }

    private void initFlights(JsonElement jsonElement) {
        DirectFlightRecord[] directFlightRecords = parseJsonElement(DirectFlightRecord[].class, jsonElement);
        for (DirectFlightRecord record : directFlightRecords) {

            if (!this.invalidAirportCodes.contains(record.getApFromCode())
                && !this.invalidAirportCodes.contains(record.getApToCode())) {

                    Airport from = this.airportMap.get(record.getApFromCode());
                    from.addOutcomeFlight(this.airportMap.get(record.getApToCode()),
                            this.airlineMap.get(record.getAirlineCode()),
                            Integer.valueOf(record.getDistance()));

                    Airport to = this.airportMap.get(record.getApToCode());
                    to.addOutcomeFlight(this.airportMap.get(record.getApFromCode()),
                            this.airlineMap.get(record.getAirlineCode()),
                            Integer.valueOf(record.getDistance()));
            }
        }
    }

    private void initWrongRoute(JsonElement jsonElement) {
        WrongRouteRecord[] wrongRouteRecords = parseJsonElement(WrongRouteRecord[].class, jsonElement);
        this.wrongRouteMap = new HashMap<>();
        for (WrongRouteRecord record : wrongRouteRecords) {
            this.wrongRouteMap.put(record.createNaturalKey(), this.createCityList(record.getCityFrom(), record.getCityVia(), record.getCityTo()));
            this.wrongRouteMap.put(record.createReverseKey(), this.createCityList(record.getCityTo(), record.getCityVia(), record.getCityFrom()));
        }
    }

    private List<City> createCityList(String cityCode1, String...cityCodes) {
        List<City> result = new ArrayList<>();
        result.add(this.cityMap.get(cityCode1));
        for (String cityCode : cityCodes) {
            result.add(this.cityMap.get(cityCode));
        }
        return result;
    }

    private void initClassLimit(JsonElement jsonElement) {
        ServiceClassLimitRecord[] serviceClassLimits = parseJsonElement(ServiceClassLimitRecord[].class, jsonElement);
        for (ServiceClassLimitRecord record : serviceClassLimits) {
            Airport origin = this.airportMap.get(record.getApFromCode());
            Airport destination = this.airportMap.get(record.getApToCode());
            Airline airline = this.airlineMap.get(record.getAirlineCode());

            Flight flight = origin.getOutcomeFlight(destination, airline);
            if (flight != null) {
                flight.getCarriers().get(airline).addExtraClasses(record.getSvcClassCode());
            }

            flight = destination.getOutcomeFlight(origin, airline);
            if (flight != null) {
                flight.getCarriers().get(airline).addExtraClasses(record.getSvcClassCode());
            }
        }
    }

    private void initLoyalty(JsonElement jsonElement) {
        LoyaltyRecord[] loyaltyRecords = parseJsonElement(LoyaltyRecord[].class, jsonElement);
        this.loyaltyMap = new HashMap<>();
        for (LoyaltyRecord record : loyaltyRecords) {
            if (record.isEmptyCode()) {
                log.log(Level.WARNING, "missing code in " + record.toString());
                continue;
            }
            loyaltyMap.put(record.getCode(), record.toLoyalty());
        }
    }
    
    private void initMilesRules(JsonElement jsonElement) {
        MilesRule[] milesRules = parseJsonElement(MilesRule[].class, jsonElement);

        for (MilesRule milesRule : milesRules) {

            List<String> newFareCodeMasks = new ArrayList<>();
            if (milesRule.getFareCodeMasks() != null) {
                for (String mask : milesRule.getFareCodeMasks()) {
                    newFareCodeMasks.add(StringMethods.milesRuleToRegexTransform(mask));
                }

                milesRule.setFareCodeMasks(newFareCodeMasks);
            }

            List<String> newTickedDesignatorMasks = new ArrayList<>();
            if (milesRule.getTickedDesignatorMasks() != null) {
                for (String mask : milesRule.getTickedDesignatorMasks()) {
                    newTickedDesignatorMasks.add(StringMethods.milesRuleToRegexTransform(mask));
                }

                milesRule.setTickedDesignatorMasks(newTickedDesignatorMasks);
            }
        }

        this.milesRules = new ArrayList<>(Arrays.asList(milesRules));
    }

    private void initLoyaltyLevelCodes(JsonElement jsonElement) {
        LoyaltyLevelCodeRecord[] records = parseJsonElement(LoyaltyLevelCodeRecord[].class, jsonElement);
        this.loyaltyLevelCodeMap = new HashMap<>();
        for (LoyaltyLevelCodeRecord record : records) {
            this.loyaltyLevelCodeMap.put(record.getLevel(), record.getPercent());
        }
    }

    public   <T> T parseJsonElement(Class<T> toClass, JsonElement jsonElement) throws JsonIOException, JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(jsonElement, toClass);
    }

}
