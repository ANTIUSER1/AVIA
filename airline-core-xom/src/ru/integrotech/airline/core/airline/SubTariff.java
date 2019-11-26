package ru.integrotech.airline.core.airline;

import java.util.Objects;

/* class represents subtariff listOf tariff */
public class SubTariff implements Comparable<SubTariff> {

    public static SubTariff of(String code, String bookingClass, int chargeCoeff) {
        return new SubTariff(code, bookingClass, chargeCoeff);
    }

    public static SubTariff of(SubTariff subTariff, boolean isAfl) {
        String newFareCode = null;

        if (isAfl) {
            newFareCode = subTariff.getBookingClass() + subTariff.getFareCode();
        } else {
            newFareCode = subTariff.getBookingClass();
        }

        return new SubTariff(newFareCode, null, subTariff.getChargeCoeff());
    }

    private final String fareCode;

    private final String bookingClass;

    private final int chargeCoeff;

    private SubTariff(String fareCode, String bookingClass, int chargeCoeff) {
        this.fareCode = fareCode;
        this.bookingClass = bookingClass;
        this.chargeCoeff = chargeCoeff;
    }

    public String getFareCode() {
        return fareCode;
    }

    public String getBookingClass() {
        return bookingClass;
    }

    public int getChargeCoeff() {
        return chargeCoeff;
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
