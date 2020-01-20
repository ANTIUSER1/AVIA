package ru.integrotech.airline.utils;

import ru.integrotech.airline.register.RegisterLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/*class for hold any values that could be loaded from .properties file
* use in any classes that works with property values*/
public class PropertyHolder {

    private static final Logger log = Logger.getLogger(RegisterLoader.class.getName());



    private static Properties getInnerProperties() {
        Properties props = new Properties();

        try {
            InputStream is = PropertyHolder.class.getClassLoader().getResourceAsStream(
                    "inner.properties");
            log.fine("using properties from resources");

            if (is != null) {
                props.load(is);
                is.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props;
    }

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

    private final int cacheRecheckMinutes;
    
    private final String chargeRulesApi;

    private final String ticketDesignatorApi;

    public PropertyHolder(Properties properties) {

        this.registryServiceBase = properties.getProperty("REGISTRY.SERVICE.URL");
        this.encoding = properties.getProperty("REGISTRY.SERVICE.ENCODING");
        String recheckTime = properties.getProperty("CACHE_RECHECK_MINUTES");
        this.cacheRecheckMinutes = recheckTime == null ? 0 : Integer
                .valueOf(recheckTime);

        Properties innerProperties = getInnerProperties();
        this.airlinesApi = innerProperties.getProperty("API.AIRLINES");
        this.worldRegionsApi = innerProperties.getProperty("API.WORLD_REGIONS");
        this.countriesApi = innerProperties.getProperty("API.COUNTRIES");
        this.citiesApi = innerProperties.getProperty("API.CITIES");
        this.airportApi = innerProperties.getProperty("API.AIRPORTS");
        this.flightsApi = innerProperties.getProperty("API.FLIGHTS");
        this.serviceClassLimitApi = innerProperties.getProperty("API.SERVICE_CLASS_LIMITS");
        this.tariffApi = innerProperties.getProperty("API.TARIFF");
        this.bonusRoutesApi = innerProperties.getProperty("API.BONUS_ROUTES");
        this.bonusesApi = innerProperties.getProperty("API.BONUSES");
        this.wrongRoutesApi = innerProperties.getProperty("API.WRONG_ROUTES");
        this.loyaltyApi = innerProperties.getProperty("API.LOYALTY");
        this.chargeRulesApi = innerProperties.getProperty("API.CHARGE_RULES");
        this.ticketDesignatorApi = innerProperties.getProperty("API.TICKET_DESIGNATORS");
    }

    public PropertyHolder(Properties properties, Properties innerProperties) {

        this.registryServiceBase = properties.getProperty("REGISTRY.SERVICE.URL");
        this.encoding = properties.getProperty("REGISTRY.SERVICE.ENCODING");
        String recheckTime = properties.getProperty("CACHE_RECHECK_MINUTES");
        this.cacheRecheckMinutes = recheckTime == null ? 0 : Integer
                .valueOf(recheckTime);

        this.airlinesApi = innerProperties.getProperty("API.AIRLINES");
        this.worldRegionsApi = innerProperties.getProperty("API.WORLD_REGIONS");
        this.countriesApi = innerProperties.getProperty("API.COUNTRIES");
        this.citiesApi = innerProperties.getProperty("API.CITIES");
        this.airportApi = innerProperties.getProperty("API.AIRPORTS");
        this.flightsApi = innerProperties.getProperty("API.FLIGHTS");
        this.serviceClassLimitApi = innerProperties.getProperty("API.SERVICE_CLASS_LIMITS");
        this.tariffApi = innerProperties.getProperty("API.TARIFF");
        this.bonusRoutesApi = innerProperties.getProperty("API.BONUS_ROUTES");
        this.bonusesApi = innerProperties.getProperty("API.BONUSES");
        this.wrongRoutesApi = innerProperties.getProperty("API.WRONG_ROUTES");
        this.loyaltyApi = innerProperties.getProperty("API.LOYALTY");
        this.chargeRulesApi = innerProperties.getProperty("API.CHARGE_RULES");
        this.ticketDesignatorApi = innerProperties.getProperty("API.TICKET_DESIGNATORS");
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
