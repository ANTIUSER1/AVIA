package ru.integrotech.airline.core.bonus;

import java.util.List;

public class MilesRule {

    private int ruleOrder;

    private RULE_TYPE ruleType;

    private List<String> fareCodeMasks;

    private String bookingClassCode;

    private List<String> tickedDesignatorMasks;

    private int bonusPercent;

    private IKM_RULE ikmRule;

    private List<String> airportPairs;

    private MilesRule() {
    }

    public int getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(int ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    public RULE_TYPE getRuleType() {
        return ruleType;
    }

    public void setRuleType(RULE_TYPE ruleType) {
        this.ruleType = ruleType;
    }

    public List<String> getFareCodeMasks() {
        return fareCodeMasks;
    }

    public void setFareCodeMasks(List<String> fareCodeMasks) {
        this.fareCodeMasks = fareCodeMasks;
    }

    public String getBookingClassCode() {
        return bookingClassCode;
    }

    public void setBookingClassCode(String bookingClassCode) {
        this.bookingClassCode = bookingClassCode;
    }

    public List<String> getTickedDesignatorMasks() {
        return tickedDesignatorMasks;
    }

    public void setTickedDesignatorMasks(List<String> tickedDesignatorMasks) {
        this.tickedDesignatorMasks = tickedDesignatorMasks;
    }

    public int getBonusPercent() {
        return bonusPercent;
    }

    public void setBonusPercent(int bonusPercent) {
        this.bonusPercent = bonusPercent;
    }

    public IKM_RULE getIkmRule() {
        return ikmRule;
    }

    public void setIkmRule(IKM_RULE ikmRule) {
        this.ikmRule = ikmRule;
    }

    public List<String> getAirportPairs() {
        return airportPairs;
    }

    public void setAirportPairs(List<String> airportPairs) {
        this.airportPairs = airportPairs;
    }

    @Override
    public String toString() {
        return "MilesRule{" +
                "ruleOrder=" + ruleOrder +
                ", ruleType=" + ruleType +
                ", fareCodeMasks=" + fareCodeMasks +
                ", bookingClassCode='" + bookingClassCode + '\'' +
                ", tickedDesignatorMasks=" + tickedDesignatorMasks +
                ", bonusPercent=" + bonusPercent +
                ", ikmRule=" + ikmRule +
                ", airportPairs=" + airportPairs +
                '}';
    }

    public enum RULE_TYPE {
        TD,     //Ticket Designator,
        PF,     //Public Fare
        SF,     //Special Fare
        BC      //Booking Class
    }

    public enum IKM_RULE {
        IN,     //Exclude
        EX      //Include
    }
}
