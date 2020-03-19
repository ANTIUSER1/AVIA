package ru.integrotech.su.outputparams.charge;

import java.util.Objects;

import ru.integrotech.airline.core.airline.Tariff;

/**
 * container for FireGroup data( private String fareGroupName; )
 */
class FareGroup {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * At first generates the instance, then set the fareGroupName property from
	 * tariff data
	 *
	 * @param tariff
	 * @return
	 */
	static FareGroup of(Tariff tariff) {
		FareGroup res = new FareGroup();
		res.setFareGroupCode(tariff.getCode());
		return res;
	}

	private String fareGroupCode;

	public String getFareGroupCode() {
		return fareGroupCode;
	}

	public void setFareGroupCode(String fareGroupCode) {
		this.fareGroupCode = fareGroupCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FareGroup fareGroup = (FareGroup) o;
		return Objects.equals(fareGroupCode, fareGroup.fareGroupCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fareGroupCode);
	}
}
