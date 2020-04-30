package ru.integrotech.su.inputparams.spend;

/**
 * container for MilesInterval data ( milesMin; milesMax; * )
 *
 * data ( private int milesMin; private int milesMax;)
 */
public class MilesInterval {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param milesMin
	 * @param milesMax
	 * @return
	 */
	public static MilesInterval of(int milesMin, int milesMax) {
		MilesInterval milesInterval = new MilesInterval();
		milesInterval.milesMin = milesMin;
		milesInterval.milesMax = milesMax;
		return milesInterval;
	}

	private int milesMin;

	private int milesMax;

	public int getMilesMin() {
		return milesMin;
	}

	public void setMilesMin(int milesMin) {
		this.milesMin = milesMin;
	}

	public int getMilesMax() {
		return milesMax;
	}

	public void setMilesMax(int milesMax) {
		this.milesMax = milesMax;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MilesInterval [milesMin=");
		builder.append(milesMin);
		builder.append(",    milesMax=");
		builder.append(milesMax);
		builder.append("]");
		return builder.toString();
	}
}
