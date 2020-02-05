package ru.integrotech.su.outputparams.spend;

import ru.integrotech.airline.core.location.Airport;

import java.util.Objects;

class AirportWithZone {

    public static AirportWithZone of(Airport airport, boolean isAfl) {
        AirportWithZone airportWithZone = new AirportWithZone();
        airportWithZone.setAirportCode(airport.getCode());
        if (isAfl) {
            airportWithZone.setZoneCode(airport.getAflZone().name());
        } else {
            airportWithZone.setZoneCode(airport.getScyteamZone().name());
        }
        return airportWithZone;
    }

    private String airportCode;

    private String zoneCode;

    private AirportWithZone() {
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirportWithZone that = (AirportWithZone) o;
        return Objects.equals(airportCode, that.airportCode) &&
                Objects.equals(zoneCode, that.zoneCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportCode, zoneCode);
    }
}
