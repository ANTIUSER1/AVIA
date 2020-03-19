package ru.integrotech.airline.core.airline;

import java.util.Objects;

/**
 * class represents bonus has been charged for passenger
 * according of booking class of tariff of service class of airline
 *
 * Used in Charge project
 *
 */

public class SubTariff implements Comparable<SubTariff> {

    public static SubTariff of(String code, String bookingClass, int chargeCoeff) {
        SubTariff result = new SubTariff();
        result.setFareCode(code);
        result.setBookingClass(bookingClass);
        result.setChargeCoeff(chargeCoeff);
        return result;
    }

    public static SubTariff of(SubTariff subTariff, boolean isAfl) {
        String newFareCode = null;

        if (isAfl) {
            newFareCode = subTariff.getBookingClass() + subTariff.getFareCode();
        } else {
            newFareCode = subTariff.getBookingClass();
        }

        SubTariff result = new SubTariff();
        result.setFareCode(newFareCode);
        result.setBookingClass(null);
        result.setChargeCoeff(subTariff.getChargeCoeff());
        return result;
    }

    private String fareCode;

    private String bookingClass;

    private int chargeCoeff;

    public String getFareCode() {
        return fareCode;
    }

    public String getBookingClass() {
        return bookingClass;
    }

    public int getChargeCoeff() {
        return chargeCoeff;
    }

    public void setFareCode(String fareCode) {
        this.fareCode = fareCode;
    }

    public void setBookingClass(String bookingClass) {
        this.bookingClass = bookingClass;
    }

    public void setChargeCoeff(int chargeCoeff) {
        this.chargeCoeff = chargeCoeff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTariff subTariff = (SubTariff) o;
        return chargeCoeff == subTariff.chargeCoeff &&
                Objects.equals(fareCode, subTariff.fareCode) &&
                Objects.equals(bookingClass, subTariff.bookingClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fareCode, bookingClass, chargeCoeff);
    }

    @Override
    public int compareTo(SubTariff o) {
        int chargeDiff = o.chargeCoeff - this.chargeCoeff;
        if (chargeDiff != 0) return chargeDiff;
        int bookingDiff = o.bookingClass.compareTo(this.bookingClass);
        if (bookingDiff != 0) return bookingDiff;
        return o.fareCode.compareTo(this.fareCode);
    }

    @Override
    public String toString() {
        return String.format("%2s%s %5d", this.bookingClass, this.fareCode, this.chargeCoeff);
    }
}
