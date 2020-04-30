package ru.integrotech.su.outputparams.charge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.integrotech.airline.core.info.PassengerChargeInfo;

/**
 * container for FareGroup
 *
 * data(private List<String> farePrefixes; private int milesCharged; private int
 * milesQualifying; private int milesBonus;)
 */
class AirlineFareGroup implements Comparable<AirlineFareGroup> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * At first generates the list of prefixes (from Charge->subtariff) then
	 * creates the instance
	 *
	 * @param charge
	 * @return
	 */
	static AirlineFareGroup of(PassengerChargeInfo charge) {
		List<String> prefixes = new ArrayList<>();
		prefixes.add(charge.getSubTariff().getFareCode());
		AirlineFareGroup res = new AirlineFareGroup();
		res.setFarePrefixes(prefixes);
		res.setMilesBonus(charge.getMilesBonus());
		res.setMilesCharged(charge.getMilesCharged());
		res.setMilesQualifying(charge.getMilesQualifying());
		return res;
	}

	private List<String> farePrefixes;

	private int milesCharged;

	private int milesQualifying;

	private int milesBonus;

	public List<String> getFarePrefixes() {
		return farePrefixes;
	}

	public void setFarePrefixes(List<String> farePrefixes) {
		this.farePrefixes = farePrefixes;
	}

	public int getMilesCharged() {
		return milesCharged;
	}

	public void setMilesCharged(int milesCharged) {
		this.milesCharged = milesCharged;
	}

	public int getMilesQualifying() {
		return milesQualifying;
	}

	public void setMilesQualifying(int milesQualifying) {
		this.milesQualifying = milesQualifying;
	}

	public int getMilesBonus() {
		return milesBonus;
	}

	public void setMilesBonus(int milesBonus) {
		this.milesBonus = milesBonus;
	}

	void update(PassengerChargeInfo charge) {
		this.farePrefixes.add(charge.getSubTariff().getFareCode());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AirlineFareGroup that = (AirlineFareGroup) o;
		return milesCharged == that.milesCharged
				&& milesQualifying == that.milesQualifying
				&& milesBonus == that.milesBonus
				&& Objects.equals(farePrefixes, that.farePrefixes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(farePrefixes, milesCharged, milesQualifying,
				milesBonus);
	}

	@Override
	public int compareTo(AirlineFareGroup o) {
		return this.milesCharged - o.milesCharged;
	}
}