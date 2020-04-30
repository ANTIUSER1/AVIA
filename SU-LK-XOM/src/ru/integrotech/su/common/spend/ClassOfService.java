package ru.integrotech.su.common.spend;

import java.util.Objects;

import ru.integrotech.airline.core.airline.ServiceClass;

/**
 * container for input-output <b>su.common.</b>ClassOfService <br />
 * data (private String classOfServiceName;) <br />
 * compares by classOfServiceName
 * 
 */

public class ClassOfService implements Comparable<ClassOfService> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param classOfServiceName
	 *            of String
	 * @return
	 */
	public static ClassOfService of(String classOfServiceName) {
		ClassOfService res = new ClassOfService();
		res.setClassOfServiceName(classOfServiceName);
		return res;
	}

	/**
	 * constructor, then sets ServiceClass
	 *
	 * @param serviceClass
	 *            of ServiceClass
	 * @return
	 */
	public static ClassOfService of(ServiceClass serviceClass) {
		ClassOfService result = new ClassOfService();
		result.setClassOfServiceName(serviceClass.toString());
		return result;
	}

	public static ClassOfService of(
			ServiceClass.SERVICE_CLASS_TYPE serviceClassType) {
		ClassOfService result = new ClassOfService();
		result.setClassOfServiceName(serviceClassType.toString());
		return result;
	}

	public static ClassOfService of() {
		return new ClassOfService();
	}

	private String classOfServiceName;

	public String getClassOfServiceName() {
		return classOfServiceName;
	}

	public void setClassOfServiceName(String classOfServiceName) {

		if (classOfServiceName != null) {
			classOfServiceName = classOfServiceName.toLowerCase();
		}

		this.classOfServiceName = classOfServiceName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ClassOfService that = (ClassOfService) o;
		return Objects.equals(classOfServiceName, that.classOfServiceName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(classOfServiceName);
	}

	@Override
	public int compareTo(ClassOfService o) {
		int result;
		if (this.classOfServiceName == null && o.classOfServiceName == null)
			result = 0;
		else if (this.classOfServiceName == null)
			result = -1;
		else
			result = this.classOfServiceName.compareTo(o.classOfServiceName);
		return result;
	}
}
