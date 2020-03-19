package ru.integrotech.airline.core.bonus;

import java.util.Objects;

/**
 * class represents passenger's Loyalty.
 * Loyalty is the property bonuses
 *
 * Can be used in all projects
 *
 */

public class Loyalty implements Comparable<Loyalty> {

    public static Loyalty of(LOYALTY_TYPE typeOf, int miles, int segments, int businessSegments, int factor) {
        Loyalty result = new Loyalty();
        result.setType(typeOf);
        result.setMiles(miles);
        result.setSegments(segments);
        result.setBusinessSegments(businessSegments);
        result.setFactor(factor);
        return result;
    }

    private LOYALTY_TYPE type;

    private int miles;

    private int segments;

    private int businessSegments;

    private int factor;

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

    public void setType(LOYALTY_TYPE type) {
        this.type = type;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public void setBusinessSegments(int businessSegments) {
        this.businessSegments = businessSegments;
    }

    public void setFactor(int factor) {
        this.factor = factor;
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
