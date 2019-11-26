package ru.integrotech.airline.register;


import ru.integrotech.airline.core.airline.Airline;

class AirlineRecord {

    private String code;

    private String name;

    private int min_miles_charge;

    private String min_miles_limit;

    Airline toAirline() {
        return Airline.of(this.code, this.name, this.min_miles_charge, this.min_miles_limit);
    }

    String getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    int getMinMilesCharge() {
        return min_miles_charge;
    }

    String getMinMilesLimit() {
        return min_miles_limit;
    }

    boolean isEmptyCode() {
        return this.code == null || this.code.isEmpty();
    }

    @Override
    public String toString() {
        return "AirlineRecord{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", min_miles_charge=" + min_miles_charge +
                ", min_miles_limit='" + min_miles_limit + '\'' +
                '}';
    }
}
