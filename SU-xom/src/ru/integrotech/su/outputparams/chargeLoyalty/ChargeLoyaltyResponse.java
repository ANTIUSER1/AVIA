package ru.integrotech.su.outputparams.chargeLoyalty;

import ru.integrotech.airline.core.info.PassengerLoyaltyInfo;

public class ChargeLoyaltyResponse {

    public static ChargeLoyaltyResponse of(PassengerLoyaltyInfo info) {
        ChargeLoyaltyResponse result = new ChargeLoyaltyResponse();
        result.status = info.getStatus().name();
        result.points = info.getPoints();
        return result;
    }

    private String status;

    private int points;

    ChargeLoyaltyResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
