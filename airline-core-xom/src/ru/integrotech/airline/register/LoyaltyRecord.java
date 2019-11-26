package ru.integrotech.airline.register;


import ru.integrotech.airline.core.bonus.Loyalty;

class LoyaltyRecord {

    private String code;

    private int miles;

    private int segments;

    private int business_segments;

    private int factor;

    Loyalty toLoyalty() {
        return Loyalty.of(Loyalty.LOYALTY_TYPE.valueOf(this.code),
                this.miles,
                this.segments,
                this.business_segments,
                this.factor);
    }

    String getCode() {
        return code;
    }

    int getMiles() {
        return miles;
    }

    int getSegments() {
        return segments;
    }

    int getBusinessSegments() {
        return business_segments;
    }

    int getFactor() {
        return factor;
    }

    boolean isEmptyCode() {
        return this.code == null || this.code.isEmpty();
    }

    @Override
    public String toString() {
        return "LoyaltyRecord{" +
                "code='" + code + '\'' +
                ", miles=" + miles +
                ", segments=" + segments +
                ", business_segments=" + business_segments +
                ", factor=" + factor +
                '}';
    }
}
