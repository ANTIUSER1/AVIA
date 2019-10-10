package ru.aeroflot.fmc.register;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import ru.aeroflot.fmc.model.airline.Airline;
import ru.aeroflot.fmc.model.airline.ServiceClass;
import ru.aeroflot.fmc.model.airline.SubTariff;
import ru.aeroflot.fmc.model.airline.Tariff;
import ru.aeroflot.fmc.model.bonus.Bonus;
import ru.aeroflot.fmc.model.bonus.Loyalty;
import ru.aeroflot.fmc.model.flight.Flight;
import ru.aeroflot.fmc.model.location.Airport;
import ru.aeroflot.fmc.model.location.City;
import ru.aeroflot.fmc.model.location.Country;
import ru.aeroflot.fmc.model.location.WorldRegion;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/* very important class that holds all registers */
public class RegisterCache {

	private static final Logger log = Logger.getLogger(RegisterCache.class
			.getName());

	private Map<String, Airline> airlineMap;

	private Map<String, Set<Bonus>> bonusRouteMap;

	private Map<String, Airport> airportMap;

	private Map<String, City> cityMap;

	private Map<String, Country> countryMap;

	private Map<String, WorldRegion> worldRegionMap;

	private Map<String, List<City>> wrongRouteMap;

	private Map<String, Loyalty> loyaltyMap;

	public Collection<Airline> getAirlines() {
		return this.airlineMap.values();
	}

	public Map<String, Set<Bonus>> getBonusRouteMap() {
		return bonusRouteMap;
	}

	public Collection<Airport> getAirports() {
		return this.airportMap.values();
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

	public void update(Type registerType, JsonElement jsonElement) {
		switch (registerType) {
		case AIRLINES:
			this.initAirlineMap(jsonElement);
			break;
		case CITIES:
			this.initCityMap(jsonElement);
			break;
		case BONUSES:
			this.initBonuses(jsonElement);
			break;
		case FLIGHTS:
			this.initFlights(jsonElement);
			break;
		case TARIFFS:
			this.initTariff(jsonElement);
			break;
		case AIRPORTS:
			this.initAirportMaps(jsonElement);
			break;
		case COUNTRIES:
			this.initCountryMap(jsonElement);
			break;
		case BONUS_ROUTES:
			this.initBonusRouteMap(jsonElement);
			break;
		case WORLD_REGIONS:
			this.initWorldRegionMap(jsonElement);
			break;
		case WRONG_ROUTES:
			this.initWrongRoute(jsonElement);
			break;
		case SERVICE_CLASS_LIMITS:
			this.initClassLimit(jsonElement);
			break;
		case LOYALTY:
			this.initLoyalty(jsonElement);
			break;
		}
	}

	private void initAirlineMap(JsonElement jsonElement) {
		AirlineRecord[] airlineRecords = parseJsonElement(
				AirlineRecord[].class, jsonElement);
		this.airlineMap = new HashMap<>();
		for (AirlineRecord record : airlineRecords) {
			this.airlineMap.put(record.code, record.toAirline());
		}
	}

	private void initWorldRegionMap(JsonElement jsonElement) {
		RegionRecord[] regionRecords = parseJsonElement(RegionRecord[].class,
				jsonElement);
		this.worldRegionMap = new HashMap<>();
		for (RegionRecord record : regionRecords) {
			this.worldRegionMap.put(record.code, record.toWorldRegion());
		}
	}

	private void initCountryMap(JsonElement jsonElement) {
		CountryRecord[] countryRecords = parseJsonElement(
				CountryRecord[].class, jsonElement);
		this.countryMap = new HashMap<>();
		for (CountryRecord record : countryRecords) {
			this.countryMap.put(record.code, record
					.toCountry(this.worldRegionMap.get(record.region_code)));
		}
	}

	private void initCityMap(JsonElement jsonElement) {
		CityRecord[] cityRecords = parseJsonElement(CityRecord[].class,
				jsonElement);
		this.cityMap = new HashMap<>();
		for (CityRecord record : cityRecords) {
			this.cityMap.put(record.code,
					record.toCity(this.countryMap.get(record.country_code)));
		}

		for (Country country : this.countryMap.values()) {
			country.setCapital(this.cityMap.get(country.getCapitalCode()));
		}
	}

	private void initAirportMaps(JsonElement jsonElement) {
		AirportRecord[] airportRecords = parseJsonElement(
				AirportRecord[].class, jsonElement);
		this.airportMap = new HashMap<>();
		for (AirportRecord record : airportRecords) {
			String airportCode = record.code;
			City city = this.cityMap.get(record.city_code);
			Airport airport = record.toAirport(city);

			this.airportMap.put(airportCode, airport);
			city.getAirportMap().put(airportCode, airport);

			Country country = city.getCountry();
			this.countryMap.get(country.getCode()).getAirportMap()
					.put(airportCode, airport);

			WorldRegion worldRegion = country.getWorldRegion();
			if (worldRegion == null) {
				log.warning("Could not find region for country "
						+ country.getCode());
			} else if (this.worldRegionMap.containsKey(worldRegion.getCode())) {
				this.worldRegionMap.get(country.getWorldRegion().getCode())
						.getAirportMap().put(airportCode, airport);
			} else {
				log.warning("Could not find region airports for region "
						+ worldRegion.getCode() + " and country "
						+ country.getCode());
			}
		}
	}

	private void initTariff(JsonElement jsonElement) {
		TariffRecord[] tariffRecords = parseJsonElement(TariffRecord[].class,
				jsonElement);

		for (TariffRecord record : tariffRecords) {
			Airline airline = this.airlineMap.get(record.airline_code);
			ServiceClass.SERVICE_CLASS_TYPE serviceClassType = ServiceClass.SERVICE_CLASS_TYPE
					.valueOf(record.svc_class_code);
			ServiceClass serviceClass = airline.getServiceClassMap().get(
					serviceClassType);

			if (serviceClass == null) {
				ServiceClass.SERVICE_CLASS_TYPE classType = ServiceClass.SERVICE_CLASS_TYPE
						.valueOf(record.svc_class_code);
				airline.getServiceClassMap().put(classType,
						record.toServiceClass());
			} else {
				Tariff tariff = serviceClass.getTariffMap().get(
						record.fare_group);

				if (tariff == null) {
					serviceClass.getTariffMap().put(record.fare_group,
							record.toTariff());
				} else {
					Set<SubTariff> subTariffs = tariff.getSubTariffsMap().get(
							Integer.valueOf(record.charge_coef));
					if (subTariffs != null) {
						subTariffs.add(record.toSubTariff());
					} else {
						subTariffs = new HashSet<>();
						subTariffs.add(record.toSubTariff());
						tariff.getSubTariffsMap()
								.put(Integer.valueOf(record.charge_coef),
										subTariffs);
					}
				}
			}
		}
	}

	private void initBonusRouteMap(JsonElement jsonElement) {
		BonusRouteRecord[] bonusRouteRecords = parseJsonElement(
				BonusRouteRecord[].class, jsonElement);
		this.bonusRouteMap = new HashMap<>();
		for (BonusRouteRecord record : bonusRouteRecords) {
			this.bonusRouteMap.put(record.getNativeCode(), new HashSet<>());
			this.bonusRouteMap.put(record.getReverseCode(), new HashSet<>());
		}
	}

	private void initBonuses(JsonElement jsonElement) {
		BonusRecord[] bonusRecords = parseJsonElement(BonusRecord[].class,
				jsonElement);
		for (BonusRecord record : bonusRecords) {
			String routeKey = record.createRouteKey();
			if (this.bonusRouteMap.containsKey(routeKey)) {
				Set<Bonus> bonuses = this.bonusRouteMap.get(routeKey);
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
				} else
					return;
			}
		}
		bonuses.add(newBonus);
	}

	private void initFlights(JsonElement jsonElement) {
		DirectFlightRecord[] directFlightRecords = parseJsonElement(
				DirectFlightRecord[].class, jsonElement);
		for (DirectFlightRecord record : directFlightRecords) {

			Airport from = this.airportMap.get(record.ap_from_code);
			from.addOutcomeFlight(this.airportMap.get(record.ap_to_code),
					this.airlineMap.get(record.airline_code),
					Integer.valueOf(record.distance));

			Airport to = this.airportMap.get(record.ap_to_code);
			to.addOutcomeFlight(this.airportMap.get(record.ap_from_code),
					this.airlineMap.get(record.airline_code),
					Integer.valueOf(record.distance));

		}

	}

	private void initWrongRoute(JsonElement jsonElement) {
		WrongRouteRecord[] wrongRouteRecords = parseJsonElement(
				WrongRouteRecord[].class, jsonElement);
		this.wrongRouteMap = new HashMap<>();
		for (WrongRouteRecord record : wrongRouteRecords) {
			this.wrongRouteMap.put(record.createNaturalKey(), this
					.createCityList(record.city_from, record.city_via,
							record.city_to));
			this.wrongRouteMap.put(record.createReverseKey(), this
					.createCityList(record.city_to, record.city_via,
							record.city_from));
		}
	}

	private List<City> createCityList(String cityCode1, String... cityCodes) {
		List<City> result = new ArrayList<>();
		result.add(this.cityMap.get(cityCode1));
		for (String cityCode : cityCodes) {
			result.add(this.cityMap.get(cityCode));
		}
		return result;
	}

	private void initClassLimit(JsonElement jsonElement) {
		ServiceClassLimitRecord[] serviceClassLimits = parseJsonElement(
				ServiceClassLimitRecord[].class, jsonElement);
		for (ServiceClassLimitRecord record : serviceClassLimits) {
			Airport origin = this.airportMap.get(record.ap_from_code);
			Airport destination = this.airportMap.get(record.ap_to_code);
			Airline airline = this.airlineMap.get(record.airline_code);

			Flight flight = origin.getOutcomeFlight(destination, airline);
			if (flight != null) {
				flight.getCarriers().get(airline)
						.addExtraClasses(record.svc_class_code);
			}

			flight = destination.getOutcomeFlight(origin, airline);
			if (flight != null) {
				flight.getCarriers().get(airline)
						.addExtraClasses(record.svc_class_code);
			}
		}
	}

	private void initLoyalty(JsonElement jsonElement) {
		LoyaltyRecord[] loyaltyRecords = parseJsonElement(
				LoyaltyRecord[].class, jsonElement);
		this.loyaltyMap = new HashMap<>();
		for (LoyaltyRecord record : loyaltyRecords) {
			loyaltyMap.put(record.code, record.toLoyalty());
		}
	}

	public <T> T parseJsonElement(Class<T> toClass, JsonElement jsonElement)
			throws JsonIOException, JsonSyntaxException {
		Gson gson = new Gson();
		return gson.fromJson(jsonElement, toClass);
	}

	// @Override
	// public String toString() {
	// StringBuilder builder = new StringBuilder();
	// builder.append("RegisterCache [").append(System.lineSeparator());
	// builder.append("airlineMap=");
	// builder.append(airlineMap).append(System.lineSeparator());
	// builder.append(", bonusRouteMap=");
	// builder.append(bonusRouteMap).append(System.lineSeparator());
	// builder.append(", airportMap=");
	// builder.append(airportMap).append(System.lineSeparator());
	// builder.append(", cityMap=");
	// builder.append(cityMap).append(System.lineSeparator());
	// builder.append(", countryMap=");
	// builder.append(countryMap).append(System.lineSeparator());
	// builder.append(", worldRegionMap=");
	// builder.append(worldRegionMap).append(System.lineSeparator());
	// builder.append(", wrongRouteMap=");
	// builder.append(wrongRouteMap).append(System.lineSeparator());
	// builder.append(", loyaltyMap=");
	// builder.append(loyaltyMap).append(System.lineSeparator());
	// builder.append("]");
	// return builder.toString();
	// }

	/**
	 * 
	 * static classes
	 *
	 */

	private static class WrongRouteRecord {

		private String city_from;

		private String city_via;

		private String city_to;

		private String createNaturalKey() {
			return (new StringBuilder()).append(city_from).append(city_via)
					.append(city_to).toString();
		}

		private String createReverseKey() {
			return (new StringBuilder()).append(city_to).append(city_via)
					.append(city_from).toString();
		}

	}

	private static class DirectFlightRecord {

		private String ap_from_code;

		private String ap_to_code;

		private String airline_code;

		private String distance;

	}

	private static class BonusRecord {

		private String type;

		private String svc_class_code_1;

		private String svc_class_code_2;

		private int value;

		private String bonus_route_code;

		private String transferer_type;

		private int is_light_award;

		private Date valid_from;

		private Date valid_to;

		private boolean isLight() {
			return this.is_light_award == 1;
		}

		private String createRouteKey() {
			return this.bonus_route_code + this.transferer_type;
		}

		private Bonus toBonus() {
			return Bonus.of(
					type,
					ServiceClass.SERVICE_CLASS_TYPE.valueOf(svc_class_code_1),
					svc_class_code_2.equals("-") ? null
							: ServiceClass.SERVICE_CLASS_TYPE
									.valueOf(svc_class_code_2), value, this
							.isLight(), valid_from, valid_to);
		}

	}

	private static class BonusRouteRecord {

		private String code;

		private String zone_from;

		private String zone_to;

		private String zone_via;

		private String transferer_type;

		private String getNativeCode() {
			if (zone_via != null && zone_via.isEmpty()) {
				return String.format("%s%s%s", zone_from, zone_to,
						transferer_type);
			} else {
				return String.format("%s%s%s%s", zone_from, zone_via, zone_to,
						transferer_type);
			}
		}

		private String getReverseCode() {
			if (zone_via != null && zone_via.isEmpty()) {
				return String.format("%s%s%s", zone_to, zone_from,
						transferer_type);
			} else {
				return String.format("%s%s%s%s", zone_to, zone_via, zone_from,
						transferer_type);
			}
		}

	}

	private static class TariffRecord {

		private String fare_group;

		private String svc_class_code;

		private String airline_code;

		private String fare_code;

		private String charge_coef;

		private String booking_class;

		private String weight_svc_class;

		private String weight_fare_group;

		private ServiceClass toServiceClass() {
			return ServiceClass.of(this.svc_class_code,
					Integer.valueOf(this.weight_svc_class), this.fare_group,
					Integer.valueOf(this.weight_fare_group), this.fare_code,
					this.booking_class, Integer.valueOf(this.charge_coef));
		}

		private Tariff toTariff() {
			return Tariff.of(this.fare_group,
					Integer.valueOf(this.weight_fare_group), this.fare_code,
					this.booking_class, Integer.valueOf(this.charge_coef));
		}

		private SubTariff toSubTariff() {
			return SubTariff.of(this.fare_code, this.booking_class,
					Integer.valueOf(this.charge_coef));
		}

	}

	private static class AirportRecord {

		private String code;

		private String name;

		private String city_code;

		private String zone_afl;

		private String zone_st;

		private double longitude;

		private double latitude;

		private int is_uc_available;

		private Airport toAirport(City city) {

			boolean isUcAvailable = this.is_uc_available == 1;

			return Airport.of(this.code, this.name, city, this.zone_afl,
					this.zone_st, isUcAvailable, this.longitude, this.latitude);
		}
	}

	private static class CityRecord {

		private String code;

		private String name;

		private double longitude;

		private double latitude;

		private String country_code;

		private int weight;

		private City toCity(Country country) {
			return City.of(this.code, this.name, country, this.weight,
					this.longitude, this.latitude);
		}

	}

	private static class CountryRecord {

		private String code;

		private String name;

		private String longitude;

		private String latitude;

		private String capital;

		private String region_code;

		private Country toCountry(WorldRegion worldRegion) {
			if ((this.longitude.isEmpty()) && (this.latitude.isEmpty())) {
				return Country.of(this.code, this.name, worldRegion, capital);
			} else {
				double longitude = Double.valueOf(this.longitude);
				double latitude = Double.valueOf(this.latitude);
				return Country.of(this.code, this.name, worldRegion, capital,
						longitude, latitude);
			}
		}
	}

	private static class AirlineRecord {

		private String code;

		private String name;

		private int min_miles_charge;

		private String min_miles_limit;

		private Airline toAirline() {
			return Airline.of(this.code, this.name, this.min_miles_charge,
					this.min_miles_limit);
		}
	}

	private static class RegionRecord {

		private String code;

		private String name;

		private double longitude;

		private double latitude;

		private WorldRegion toWorldRegion() {
			return WorldRegion.of(this.code, this.name, this.longitude,
					this.latitude);
		}
	}

	private static class ServiceClassLimitRecord {

		private String svc_class_code;

		private String ap_from_code;

		private String ap_to_code;

		private String airline_code;
	}

	private static class LoyaltyRecord {

		private String code;

		private int miles;

		private int segments;

		private int business_segments;

		private int factor;

		private Loyalty toLoyalty() {
			return Loyalty.of(Loyalty.LOYALTY_TYPE.valueOf(this.code),
					this.miles, this.segments, this.business_segments,
					this.factor);
		}
	}

	public enum Type {
		AIRLINES, WORLD_REGIONS, COUNTRIES, CITIES, AIRPORTS, FLIGHTS, TARIFFS, BONUS_ROUTES, BONUSES, WRONG_ROUTES, SERVICE_CLASS_LIMITS, LOYALTY
	}
}
