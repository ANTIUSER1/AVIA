package ru.integrotech.airline.core.bonus;


import ru.integrotech.airline.core.airline.ServiceClass;

import java.util.Date;
import java.util.Objects;

/* class represents bonus is given to passenger for flight */
public class Bonus implements Comparable<Bonus> {

    public static Bonus of(String typeName,
                           ServiceClass.SERVICE_CLASS_TYPE serviceClass,
                           ServiceClass.SERVICE_CLASS_TYPE serviceUpgradeClass,
                           int value,
                           boolean isLight,
                           Date validFrom,
                           Date validTo) {

        return new Bonus(BONUS_TYPE.valueOf(typeName),
                         serviceClass,
                         serviceUpgradeClass,
                         value,
                         isLight,
                         validFrom ,
                         validTo);
    }

    private BONUS_TYPE type;

    private final ServiceClass.SERVICE_CLASS_TYPE serviceClass;

    private final ServiceClass.SERVICE_CLASS_TYPE upgradeServiceClass;

    private final int value;

    private final boolean isLight;

    private final Date validFrom;

    private final Date validTo;

    private boolean fitsMilesInterval;

    private Bonus(BONUS_TYPE bonusType, ServiceClass.SERVICE_CLASS_TYPE serviceClass, ServiceClass.SERVICE_CLASS_TYPE upgradeServiceClass, int value, boolean isLight, Date validFrom, Date validTo) {
        this.type = bonusType;
        this.serviceClass = serviceClass;
        this.upgradeServiceClass = upgradeServiceClass;
        this.value = value;
        this.isLight = isLight;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public BONUS_TYPE getType() {
        return type;
    }

    public void setType(BONUS_TYPE type) {
        this.type = type;
    }

    public ServiceClass.SERVICE_CLASS_TYPE getServiceClass() {
        return serviceClass;
    }

    public ServiceClass.SERVICE_CLASS_TYPE getUpgradeServiceClass() {
        return upgradeServiceClass;
    }

    public int getValue() {
        return value;
    }

    public boolean isLight() {
        return isLight;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public boolean isFitsMilesInterval() {
        return fitsMilesInterval;
    }

    public void setFitsMilesInterval(boolean fitsMilesInterval) {
        this.fitsMilesInterval = fitsMilesInterval;
    }

    public void setFitsMilesInterval(int milesMin, int milesMax) {
        if (milesMin == -1) milesMin = 0;
        if (milesMax == -1) milesMax = Integer.MAX_VALUE;
        this.fitsMilesInterval = this.value >= milesMin
                && this.value <= milesMax;
    }

    //TODO update. Remove value
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bonus bonus = (Bonus) o;
        return value == bonus.value &&
                type == bonus.type &&
                serviceClass == bonus.serviceClass &&
                upgradeServiceClass == bonus.upgradeServiceClass;
    }

    //TODO remove after equals and hashcode update
    public boolean equalsIgnoreValue(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bonus bonus = (Bonus) o;
        return type == bonus.type &&
                serviceClass == bonus.serviceClass &&
                upgradeServiceClass == bonus.upgradeServiceClass;
    }

    //TODO remove after equals and hashcode update
    public String getDescription() {
        return String.format("%s%s%s", this.type, this.serviceClass, this.upgradeServiceClass);
    }

    //TODO update. Remove value
    @Override
    public int hashCode() {
        return Objects.hash(type, serviceClass, upgradeServiceClass, value);
    }

    @Override
    public int compareTo(Bonus o) {

        int typeDiff = this.type.compareTo(o.type);
        if (typeDiff != 0) return typeDiff;

        int serviceClassDiff = this.serviceClass.compareTo(o.serviceClass);
        if (serviceClassDiff != 0) return serviceClassDiff;

        return this.upgradeServiceClass.compareTo(o.upgradeServiceClass);
    }

    @Override
    public String toString() {
        String upgrade = this.upgradeServiceClass != null ? String.format("upgrade to %s", this.upgradeServiceClass) : "";
        String isLight = this.isLight ? ", is light" : "";
        String validTo = this.validTo != null ? String.format("%tc", this.validTo) : "unlimited";
        return String.format("Bonus: %-2s  %-10s  %-20s   %,-10d %tc -> %-10s %-10s",
                this.type,
                this.serviceClass,
                upgrade,
                this.value,
                this.validFrom,
                validTo,
                isLight);
    }

    /*bonus must have determined type*/
    public enum BONUS_TYPE {OW, RT, U, UC, UO}

    /*use for filter collections listOf <bonus> by type*/
    public enum AWARD_TYPE {TICKET, UPGRADE, ALL}

}
