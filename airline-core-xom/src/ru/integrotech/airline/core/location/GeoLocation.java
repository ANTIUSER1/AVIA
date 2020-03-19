package ru.integrotech.airline.core.location;

import java.util.Objects;

/**
 * class represents geographical position
 *
 *  Сan be used in all projects
 *
 */

public class GeoLocation implements Comparable<GeoLocation> {

    public static GeoLocation of(double longitude, double latitude) {
        GeoLocation result = new GeoLocation();
        result.setLongitude(longitude);
        result.setLatitude(latitude);
        result.setAltitude(0);
        return result;
    }

    private double longitude;

    private double latitude;

    private double altitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocation that = (GeoLocation) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.altitude, altitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, altitude);
    }

    @Override
    public int compareTo(GeoLocation o) {
        int latitudeDiff = Double.compare(this.latitude, o.latitude);
        if (latitudeDiff > 0) return latitudeDiff;

        int longitudeDiff = Double.compare(this.longitude, o.longitude);
        if (longitudeDiff > 0) return longitudeDiff;

        return Double.compare(this.altitude, o.altitude);
    }

    @Override
    public String toString() {
        String latitudePref = this.latitude > 0 ? "N" : "S";
        String longitudePref = this.longitude > 0 ? "E" : "W";
        return String.format("%s %s,  %s %s",
                                            latitudePref,
                                            toDegrees(this.latitude),
                                            longitudePref,
                                            toDegrees(this.longitude));
    }

    private String toDegrees(Double tangle) {
        if (tangle < 0) tangle = tangle * -1;
        int degrees = tangle.intValue();
        Double remain = (tangle - degrees) * 60;
        int minutes = remain.intValue();
        double seconds = (remain - minutes) * 60;
        return String.format("%2d° %2d' %5.2f\"", degrees, minutes, seconds);
    }
}
