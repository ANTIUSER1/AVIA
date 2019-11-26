package ru.integrotech.su.inputparams.spend;

public class MilesInterval {

    static MilesInterval of(int milesMin, int milesMax) {
        MilesInterval milesInterval = new MilesInterval();
        milesInterval.milesMin = milesMin;
        milesInterval.milesMax = milesMax;
        return milesInterval;
    }

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

