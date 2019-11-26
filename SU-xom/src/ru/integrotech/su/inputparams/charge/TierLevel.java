package ru.integrotech.su.inputparams.charge;

public class TierLevel {

    public static TierLevel of(String tierLevelCode) {
        TierLevel tierLevel = new TierLevel();
        tierLevel.setTierLevelCode(tierLevelCode);
        return tierLevel;
    }

    private String tierLevelCode;

    private TierLevel() {
    }

    public String getTierLevelCode() {
        return tierLevelCode;
    }

    public void setTierLevelCode(String tierLevelCode) {
        this.tierLevelCode = tierLevelCode;
    }
}
