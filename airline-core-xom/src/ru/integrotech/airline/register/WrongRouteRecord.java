package ru.integrotech.airline.register;

class WrongRouteRecord {

    private String city_from;

    private String city_via;

    private String city_to;

    String createNaturalKey() {
        return (new StringBuilder()).append(city_from)
                .append(city_via)
                .append(city_to)
                .toString();
    }

    String createReverseKey() {
        return (new StringBuilder()).append(city_to)
                .append(city_via)
                .append(city_from)
                .toString();
    }

    String getCityFrom() {
        return city_from;
    }

    String getCityVia() {
        return city_via;
    }

    String getCityTo() {
        return city_to;
    }

    @Override
    public String toString() {
        return "WrongRouteRecord{" +
                "city_from='" + city_from + '\'' +
                ", city_via='" + city_via + '\'' +
                ", city_to='" + city_to + '\'' +
                '}';
    }
}
