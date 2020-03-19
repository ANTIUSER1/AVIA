package ru.integrotech.su.outputparams.spend;

import java.util.Objects;

import ru.integrotech.airline.core.airline.ServiceClass;
import ru.integrotech.airline.core.bonus.Bonus;
import ru.integrotech.su.common.spend.ClassOfService;

/**
 * class use only for buildResult io results
 * 
 * <hr />
 * 
 * container for RequiredAward data
 *
 * data( private AirportWithZone airportFrom; private AirportWithZone airportTo;
 * private int distance; private List<RequiredAward> requiredAward;)
 */
class RequiredAward implements Comparable<RequiredAward> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param bonus
	 * @return
	 */
	static RequiredAward of(Bonus bonus) {
		String awardType = bonus.getType().name();
		ClassOfService classOfService1 = null;
		ServiceClass.SERVICE_CLASS_TYPE serviceClassType1 = bonus
				.getServiceClass();

		if (serviceClassType1 != null) {
			classOfService1 = ClassOfService.of(serviceClassType1);
		} else {
			classOfService1 = ClassOfService.of();
		}

		ClassOfService classOfService2 = null;
		ServiceClass.SERVICE_CLASS_TYPE serviceClassType2 = bonus
				.getUpgradeServiceClass();
		if (serviceClassType2 != null) {
			classOfService2 = ClassOfService.of(serviceClassType2);
		} else {
			classOfService2 = ClassOfService.of();
		}
		RequiredAward res = new RequiredAward();
		res.setAwardType(awardType);
		res.setClassOfService1(classOfService1);
		res.setClassOfService2(classOfService2);
		res.setValue(bonus.getValue());
		res.setIsLightAward(bonus.isLight());
		res.setFitsMilesInterval(bonus.isFitsMilesInterval());
		return res;
	}

	private String awardType;

	private ClassOfService classOfService1;

	private ClassOfService classOfService2;

	private int value;

	private boolean isLightAward;

	private boolean fitsMilesInterval;

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public ClassOfService getClassOfService1() {
		return classOfService1;
	}

	public void setClassOfService1(ClassOfService classOfService1) {
		this.classOfService1 = classOfService1;
	}

	public ClassOfService getClassOfService2() {
		return classOfService2;
	}

	public void setClassOfService2(ClassOfService classOfService2) {
		this.classOfService2 = classOfService2;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean getIsLightAward() {
		return isLightAward;
	}

	public void setIsLightAward(boolean lightAward) {
		isLightAward = lightAward;
	}

	public boolean isFitsMilesInterval() {
		return fitsMilesInterval;
	}

	public void setFitsMilesInterval(boolean fitsMilesInterval) {
		this.fitsMilesInterval = fitsMilesInterval;
	}

	String awardKey() {
		return String.format(
				"%s%s%s%s%s",
				awardType,
				'.',
				(classOfService1 == null ? "-" : classOfService1
						.getClassOfServiceName()),
				'.',
				(classOfService2 == null ? "-" : classOfService2
						.getClassOfServiceName()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RequiredAward that = (RequiredAward) o;
		return value == that.value && isLightAward == that.isLightAward
				&& fitsMilesInterval == that.fitsMilesInterval
				&& Objects.equals(awardType, that.awardType)
				&& Objects.equals(classOfService1, that.classOfService1)
				&& Objects.equals(classOfService2, that.classOfService2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(awardType, classOfService1, classOfService2, value,
				isLightAward, fitsMilesInterval);
	}

	@Override
	public int compareTo(RequiredAward o) {
		int result = 0;
		if (this.awardType.compareTo(o.awardType) != 0) {
			result = this.awardType.compareTo(o.awardType);
		} else if (this.classOfService1.compareTo(o.classOfService1) != 0) {
			result = this.classOfService1.compareTo(o.classOfService1);
		} else if (this.classOfService2.compareTo(o.classOfService2) != 0) {
			result = this.classOfService2.compareTo(o.classOfService2);
		} else if (this.value != o.value) {
			result = this.value - o.value;
		} else if (Boolean.compare(this.isLightAward, o.isLightAward) != 0) {
			result = Boolean.compare(this.isLightAward, o.isLightAward);
		} else {
			result = Boolean.compare(this.fitsMilesInterval,
					o.fitsMilesInterval);
		}
		return result;
	}

	@Override
	public String toString() {
		return "RequiredAward{" + "awardType='" + awardType + '\''
				+ ", classOfService1=" + classOfService1 + ", classOfService2="
				+ classOfService2 + ", value=" + value + ", isLightAward="
				+ isLightAward + ", fitsMilesInterval=" + fitsMilesInterval
				+ '}';
	}
}
