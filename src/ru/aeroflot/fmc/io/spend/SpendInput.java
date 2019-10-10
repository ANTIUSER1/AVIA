package ru.aeroflot.fmc.io.spend;

public class SpendInput {

    private LocationInput origin;

    private LocationInput destination;

    private MilesInterval milesInterval;

    private boolean isOnlyAfl;

    private boolean isRoundTrip;

    private ClassOfService classOfService;

    private String awardType;

    private String lang;

    private SpendInput() {
    }

    public boolean isOnlyAfl() {
        return isOnlyAfl;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public String getAwardType() {
        return awardType;
    }

    public LocationInput getOrigin() {
        return origin;
    }

    public void setOrigin(LocationInput origin) {
        this.origin = origin;
    }

    public LocationInput getDestination() {
        return destination;
    }

    public void setDestination(LocationInput destination) {
        this.destination = destination;
    }

    public MilesInterval getMilesInterval() {
        return milesInterval;
    }

    public void setMilesInterval(MilesInterval milesInterval) {
        this.milesInterval = milesInterval;
    }

    public void setOnlyAfl(boolean onlyAfl) {
        isOnlyAfl = onlyAfl;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public ClassOfService getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(ClassOfService classOfService) {
        this.classOfService = classOfService;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    String getOriginCode() {
        return this.origin.getLocationCode();
    }

    String getDestinationCode() {
        return this.destination.getLocationCode();
    }

    String getOriginType() {
        return this.origin.getLocationType();
    }

    String getDestinationType() {
        return this.destination.getLocationType();
    }

    int getMilesMin() {
        return this.milesInterval.getMilesMin();
    }

    int getMilesMax() {
        return this.milesInterval.getMilesMax();
    }

    String getClassOfServiceName() {
        String result = null;
        if (this.classOfService != null) {
            result = this.classOfService.getClassOfServiceName();
        }
        return result;
    }
}
