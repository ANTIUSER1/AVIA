package ru.integrotech.su.inputparams.charge;

/**
 * container for tier level
 *
 * data (private String tierLevelCode;)
 */
public class TierLevel {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param tierLevelCode
	 * @return
	 */
	public static TierLevel of(String tierLevelCode) {
		TierLevel tierLevel = new TierLevel();
		tierLevel.setTierLevelCode(tierLevelCode);
		return tierLevel;
	}

	private String tierLevelCode;

	public String getTierLevelCode() {
		return tierLevelCode;
	}

	public void setTierLevelCode(String tierLevelCode) {

		if (tierLevelCode != null) {
			tierLevelCode = tierLevelCode.toLowerCase();
		}

		this.tierLevelCode = tierLevelCode;
	}
}
