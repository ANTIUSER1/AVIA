package ru.integrotech.airline.utils;

import ru.integrotech.airline.register.RegisterLoader;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * class for hold any values that could be loaded from .properties files use in
 * any classes that works with property values
 *
 *
 * Used in {@link ru.integrotech.airline.register.RegisterLoader} in field prop
 * and constructor
 *
 */

public class PropertyHolder {

    private static final Logger log = Logger.getLogger(RegisterLoader.class.getName());

    private String encoding;

    private String registryServiceBase;

    private int cacheRecheckMinutes;

    public PropertyHolder(Properties properties) {

        this.registryServiceBase = properties.getProperty("REGISTRY.SERVICE.URL");
        this.encoding = properties.getProperty("REGISTRY.SERVICE.ENCODING");
        String recheckTime = properties.getProperty("CACHE_RECHECK_MINUTES");
        this.cacheRecheckMinutes = recheckTime == null ? 0 : Integer
                .valueOf(recheckTime);

    }


    public String getEncoding() {
        return encoding;
    }

    public String getRegistryServiceBase() {
        return registryServiceBase;
    }

    public int getCacheRecheckMinutes() {
        return cacheRecheckMinutes;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setRegistryServiceBase(String registryServiceBase) {
        this.registryServiceBase = registryServiceBase;
    }

    public void setCacheRecheckMinutes(int cacheRecheckMinutes) {
        this.cacheRecheckMinutes = cacheRecheckMinutes;
    }
}
