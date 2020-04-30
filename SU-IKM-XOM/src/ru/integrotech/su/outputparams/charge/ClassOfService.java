package ru.integrotech.su.outputparams.charge;

import java.util.Objects;

import ru.integrotech.airline.core.airline.ServiceClass;

/**
 * container for input-output <b>su.common.</b>ClassOfService
 *
 * data (String classOfServiceCode;)
 */
class ClassOfService {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param serviceClass
	 * @return
	 */
	static ClassOfService of(ServiceClass serviceClass) {
		ClassOfService res = new ClassOfService();
		res.setClassOfServiceCode(serviceClass.getType().toString());
		return res;
	}

	/**
	 * Static constructor simply refering to the corresponding private
	 * constructor with given parameters
	 *
	 * @param serviceClassType
	 * @return
	 */
	static ClassOfService of(ServiceClass.SERVICE_CLASS_TYPE serviceClassType) {
		ClassOfService res = new ClassOfService();
		res.setClassOfServiceCode(serviceClassType.toString());
		return res;
	}

	String classOfServiceCode;

	public String getClassOfServiceCode() {
		return classOfServiceCode;
	}

	public void setClassOfServiceCode(String classOfServiceCode) {
		this.classOfServiceCode = classOfServiceCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ClassOfService that = (ClassOfService) o;
		return Objects.equals(classOfServiceCode, that.classOfServiceCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(classOfServiceCode);
	}
}
