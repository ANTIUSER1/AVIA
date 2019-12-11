package ru.integrotech.airline.core.bonus;

public class TicketDesignator {

    private String mask;

    private boolean toZeroMiles;

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

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setToZeroMiles(boolean toZeroMiles) {
        this.toZeroMiles = toZeroMiles;
    }

    @Override
    public String toString() {
        return "TicketDesignator{" +
                "mask='" + mask + '\'' +
                ", toZeroMiles=" + toZeroMiles +
                '}';
    }
}
