package ru.integrotech.airline.register;

class DirectFlightRecord {

    private String ap_from_code;

    private String ap_to_code;

    private String airline_code;

    private String distance;

    String getApFromCode() {
        return ap_from_code;
    }

    String getApToCode() {
        return ap_to_code;
    }

    String getAirlineCode() {
        return airline_code;
    }

    String getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "DirectFlightRecord{" +
                "ap_from_code='" + ap_from_code + '\'' +
                ", ap_to_code='" + ap_to_code + '\'' +
                ", airline_code='" + airline_code + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
