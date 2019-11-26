package ru.integrotech.airline.core.bonus;

public class TicketDesignator {

    private final String mask;

    private final boolean toZeroMiles;

    public TicketDesignator(String mask, boolean toZeroMiles) {
        this.mask = mask;
        this.toZeroMiles = toZeroMiles;
    }

    public String getMask() {
        return mask;
    }

    public boolean isToZeroMiles() {
        return toZeroMiles;
    }

    @Override
    public String toString() {
        return "TicketDesignator{" +
                "mask='" + mask + '\'' +
                ", toZeroMiles=" + toZeroMiles +
                '}';
    }
}
