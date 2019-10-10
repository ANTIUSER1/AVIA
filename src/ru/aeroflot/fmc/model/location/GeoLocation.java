package ru.aeroflot.fmc.model.location;

import java.util.Objects;

/* class represents geographical position */
public class GeoLocation implements Comparable<GeoLocation> {

    public static GeoLocation of(double longitude, double latitude) {
        return new GeoLocation(longitude, latitude, 0);
    }

    private final double longitude;

    private final double latitude;

    private final double altitude;

    private GeoLocation(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
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
