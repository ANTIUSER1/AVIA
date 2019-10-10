package ru.aeroflot.fmc.model.bonus;

import java.util.Objects;

/* class represents passenger's loyalty. Loyalty is the property
 * that allows multiply value listOf bonuses */
public class Loyalty implements Comparable<Loyalty> {

    public static Loyalty of(LOYALTY_TYPE typeOf, int miles, int segments, int businessSegments, int factor) {
        return new Loyalty(typeOf, miles, segments, businessSegments, factor);
    }

    private final LOYALTY_TYPE type;

    private final int miles;

    private final int segments;

    private final int businessSegments;

    private final int factor;

    private Loyalty(LOYALTY_TYPE type, int miles, int segments, int businessSegments, int factor) {
        this.type = type;
        this.miles = miles;
        this.segments = segments;
        this.businessSegments = businessSegments;
        this.factor = factor;
    }

    public LOYALTY_TYPE getType() {
        return type;
    }

    public int getMiles() {
        return miles;
    }

    public int getSegments() {
        return segments;
    }

    public int getBusinessSegments() {
        return businessSegments;
    }

    public int getFactor() {
        return factor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loyalty loyalty = (Loyalty) o;
        return type == loyalty.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public int compareTo(Loyalty o) {
        return this.miles - o.miles;
    }

    @Override
    public String toString() {
        return String.format("Loyalty: %-8s     miles: %,7d,  segments: %,2d,  business segments: %,2d,  factor: %,2d",
                this.type,
                this.miles,
                this.segments,
                this.businessSegments,
                this.factor);
    }

    /*Loyalty must have determined type*/
    public enum LOYALTY_TYPE {basic, silver, gold, platinum}
}
