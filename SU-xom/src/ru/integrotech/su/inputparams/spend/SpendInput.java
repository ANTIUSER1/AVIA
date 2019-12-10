package ru.integrotech.su.inputparams.spend;


import ru.integrotech.su.common.spend.ClassOfService;

public class SpendInput {

    public static SpendInput of() {
        return new SpendInput();
    }

    public static SpendInput of(String originType,
                                String originCode,
                                String destType,
                                String destCode,
                                int milesMin,
                                int milesMax,
                                String classOfServiceName,
                                String awardType,
                                boolean isOnlyAfl,
                                boolean isRoundTrip) {
        SpendInput spendInput = new SpendInput();
        spendInput.setOrigin(LocationInput.of(originType, originCode));
        spendInput.setDestination(LocationInput.of(destType, destCode));
        spendInput.setMilesInterval(MilesInterval.of(milesMin, milesMax));
        spendInput.setClassOfService(ClassOfService.of(classOfServiceName));
        spendInput.setAwardType(awardType);
        spendInput.setIsOnlyAfl(isOnlyAfl);
        spendInput.setIsRoundTrip(isRoundTrip);
        return spendInput;
    }
    
   

    private LocationInput origin;

    private LocationInput destination;

    private MilesInterval milesInterval;

    private boolean isOnlyAfl;

    private boolean isRoundTrip;

    private ClassOfService classOfService;

    private String awardType;

    private String lang;

    private SpendInput() {
    	this.initDafaultParameters();
    }

    public boolean getIsOnlyAfl() {
        return isOnlyAfl;
    }

    public boolean getIsRoundTrip() {
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

    public void setIsOnlyAfl(boolean isOnlyAfl) {
        this.isOnlyAfl = isOnlyAfl;
    }

    public void setIsRoundTrip(boolean isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
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
    
    private void initDafaultParameters() {
    	
    	if (this.destination == null) {
    		this.destination = LocationInput.of(null, null);
     	}
    	
    	if (this.milesInterval == null) {
    		this.milesInterval = MilesInterval.of(10000, 250000);
     	}
    	
    	if (this.classOfService == null) {
    		this.classOfService = ClassOfService.of();
     	}
    	
    	if (this.awardType == null) {
    		this.awardType = "all";
     	}
    	
    }

}
