package ru.integrotech.su.common;

import java.util.Objects;

public class City {

    public static City of(ru.integrotech.airline.core.location.Airport airport) {
        return new City(airport.getCity().getCode(), airport.getCity().getWeight());
    }

    public static City of(String cityCode) {
        City city = new City();
        city.setCityCode(cityCode);
        return city;
    }

    private String cityCode;

    private int weight;

    private City(String cityCode, int weight) {
        this.cityCode = cityCode;
        this.weight = weight;
    }

    private City() {}

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {

        if (cityCode != null) {
            cityCode = cityCode.toUpperCase();
        }

        this.cityCode = cityCode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(cityCode, city.cityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityCode);
    }
}

