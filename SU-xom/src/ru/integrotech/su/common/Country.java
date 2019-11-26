package ru.integrotech.su.common;

import java.util.Objects;

public class Country {

    public static Country of(String countryCode) {
        return new Country(countryCode);
    }

    private String countryCode;

    private Country(String countryCode) {
        this.countryCode = countryCode;
    }

    private Country() {
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(countryCode, country.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode);
    }
}

