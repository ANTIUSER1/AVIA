package ru.integrotech.su.inputparams.spend;

import ru.integrotech.airline.utils.StringMethods;
import ru.integrotech.su.common.spend.ClassOfService;

/**
 *
 * container for spendRequest (request body for Spend project)
 *
 * data (private LocationInput origin; private LocationInput destination;
 * private MilesInterval milesInterval; private boolean isOnlyAfl; private
 * boolean isRoundTrip; private ClassOfService classOfService; private String
 * awardType; private String lang;)
 */
public class SpendInput {
	/**
	 * 
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @return
	 */
	public static SpendInput of() {
		SpendInput res = new SpendInput();
		res.initDefaultParameters();
		return res;
	}

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param originType
	 * @param originCode
	 * @param destType
	 * @param destCode
	 * @param milesMin
	 * @param milesMax
	 * @param classOfServiceName
	 * @param awardType
	 * @param isOnlyAfl
	 * @param isRoundTrip
	 * @return
	 */
	public static SpendInput of(String originType, String originCode,
			String destType, String destCode, int milesMin, int milesMax,
			String classOfServiceName, String awardType, boolean isOnlyAfl,
			Boolean isRoundTrip) {
		SpendInput spendInput = new SpendInput();
		spendInput.setOrigin(LocationInput.of(originType, originCode));
		spendInput.setDestination(LocationInput.of(destType, destCode));
		spendInput.setMilesInterval(MilesInterval.of(milesMin, milesMax));
		if (classOfServiceName != null) {
			spendInput.setClassOfService(ClassOfService.of(classOfServiceName));
		}
		spendInput.setAwardType(awardType);
		spendInput.setIsOnlyAfl(isOnlyAfl);
		spendInput.setIsRoundTrip(isRoundTrip);
		return spendInput;
	}

	private LocationInput origin;

	private LocationInput destination;

	private MilesInterval milesInterval;

	private boolean isOnlyAfl;

	private Boolean isRoundTrip;

	private ClassOfService classOfService;

	private String awardType;

	private String lang;

	public boolean getIsOnlyAfl() {
		return isOnlyAfl;
	}

	public Boolean getIsRoundTrip() {
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

	public void setIsRoundTrip(Boolean isRoundTrip) {
		this.isRoundTrip = isRoundTrip;
	}

	public ClassOfService getClassOfService() {
		return classOfService;
	}

	public void setClassOfService(ClassOfService classOfService) {
		this.classOfService = classOfService;
	}

	public void setAwardType(String awardType) {

		if (awardType != null) {
			awardType = awardType.toUpperCase();
		}

		this.awardType = awardType;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Default field values Initialization <br />
	 * destination = null;<br />
	 * milesInterval = [0--250000] ;<br />
	 * classOfService = ClassOfService.of();<br />
	 * awardType = all;<br />
	 */
	public void initDefaultParameters() {

		if (this.destination == null) {
			this.destination = LocationInput.of(null, null);
		} else {
		    if (StringMethods.isEmpty(this.destination.getLocationType()) &&
                    StringMethods.isEmpty(this.destination.getLocationCode())) {
		        this.destination.setLocationType(null);
		        this.destination.setLocationCode(null);
            }
        }

        if (this.milesInterval == null) {
            this.milesInterval = MilesInterval.of(10000, 250000);
        }

		if (StringMethods.isEmpty(this.awardType)) {
			this.awardType = "ALL";
		}

		if (StringMethods.isEmpty(this.lang)) {
			this.lang = "ru";
		}

	}

}
