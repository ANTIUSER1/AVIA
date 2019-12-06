package ru.integrotech.su.outputparams.spend;

import ru.integrotech.airline.core.location.Airport;

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
}
