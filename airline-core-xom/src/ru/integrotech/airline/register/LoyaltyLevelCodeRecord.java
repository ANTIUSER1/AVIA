package ru.integrotech.airline.register;

/**
 * class for read data in JSON format from remote register
 *
 *  Can be used in ChargeLoyalty projects
 *
 */
//TODO remove after add onlineRegAvail project

public class LoyaltyLevelCodeRecord {

    private String level;

    private int percent;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "LoyaltyLevelCodeRecord{" +
                "level='" + level + '\'' +
                ", percent=" + percent +
                '}';
    }
}
