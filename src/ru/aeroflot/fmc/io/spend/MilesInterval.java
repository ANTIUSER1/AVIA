package ru.aeroflot.fmc.io.spend;

class MilesInterval {

    private int milesMin;

    private int milesMax;

    private MilesInterval() {
    }

    public int getMilesMin() {
        return milesMin;
    }

    public void setMilesMin(int milesMin) {
        this.milesMin = milesMin;
    }

    public int getMilesMax() {
        return milesMax;
    }

    public void setMilesMax(int milesMax) {
        this.milesMax = milesMax;
    }
}

