package ru.integrotech.airline.utils;

import java.util.Properties;

/*class for hold any values that could be loaded from .properties file
* use in any classes that works with property values*/
public class PropertyHolder {

    private final String encoding;

    private final String airlinesApi;

    private final String worldRegionsApi;

    private final String countriesApi;

    private final String citiesApi;

    private final String airportApi;

    private final String flightsApi;

    private final String serviceClassLimitApi;

    private final String tariffApi;

    private final String bonusRoutesApi;

    private final String bonusesApi;

    private final String wrongRoutesApi;

    private final String loyaltyApi;

    private final String registryServiceBase;

    private final String registryServiceKey;

    private final String registryServiceValue;

    private final String sslContext;

    private final int cacheRecheckMinutes;
    
    private final String chargeRulesApi;

    private final String ticketDesignatorApi;


    public PropertyHolder(Properties properties) {
        this.encoding = properties.getProperty("REGISTRY.SERVICE.ENCODING");
        this.airlinesApi = properties.getProperty("API.AIRLINES");
        this.worldRegionsApi = properties.getProperty("API.WORLD_REGIONS");
        this.countriesApi = properties.getProperty("API.COUNTRIES");
        this.citiesApi = properties.getProperty("API.CITIES");
        this.airportApi = properties.getProperty("API.AIRPORTS");
        this.flightsApi = properties.getProperty("API.FLIGHTS");
        this.serviceClassLimitApi = properties.getProperty("API.SERVICE_CLASS_LIMITS");
        this.tariffApi = properties.getProperty("API.TARIFF");
        this.bonusRoutesApi = properties.getProperty("API.BONUS_ROUTES");
        this.bonusesApi = properties.getProperty("API.BONUSES");
        this.wrongRoutesApi = properties.getProperty("API.WRONG_ROUTES");
        this.loyaltyApi = properties.getProperty("API.LOYALTY");
        this.registryServiceBase = properties.getProperty("REGISTRY.SERVICE.URL");
        this.registryServiceKey = properties.getProperty("REGISTRY.SERVICE.KEY");
        this.registryServiceValue = properties.getProperty("REGISTRY.SERVICE.VALUE");
        this.sslContext = properties.getProperty("SSL.CONTEXT");
        String recheckTime = properties.getProperty("CACHE_RECHECK_MINUTES");
        this.cacheRecheckMinutes = recheckTime == null ? 0 : Integer
                .valueOf(recheckTime);
        this.chargeRulesApi = properties.getProperty("API.CHARGE_RULES");
        this.ticketDesignatorApi = properties.getProperty("API.TICKET_DESIGNATORS");
    }

    public String getEncoding() {
        return encoding;
    }

    public String getAirlinesApi() {
        return airlinesApi;
    }

    public String getWorldRegionsApi() {
        return worldRegionsApi;
    }

    public String getCountriesApi() {
        return countriesApi;
    }

    public String getCitiesApi() {
        return citiesApi;
    }

    public String getAirportApi() {
        return airportApi;
    }

    public String getFlightsApi() {
        return flightsApi;
    }

    public String getServiceClassLimitApi() {
        return serviceClassLimitApi;
    }

    public String getTariffApi() {
        return tariffApi;
    }

    public String getBonusRoutesApi() {
        return bonusRoutesApi;
    }

    public String getBonusesApi() {
        return bonusesApi;
    }

    public String getWrongRoutesApi() {
        return wrongRoutesApi;
    }

    public String getLoyaltyApi() {
        return loyaltyApi;
    }

    public String getRegistryServiceBase() {
        return registryServiceBase;
    }

    public String getRegistryServiceKey() {
        return registryServiceKey;
    }

    public String getRegistryServiceValue() {
        return registryServiceValue;
    }

    public String getSslContext() {
        return sslContext;
    }

    public int getCacheRecheckMinutes() {
        return cacheRecheckMinutes;
    }

	public String getChargeRulesApi() {
		return chargeRulesApi;
	}

    public String getTicketDesignatorApi() {
        return ticketDesignatorApi;
    }
}
