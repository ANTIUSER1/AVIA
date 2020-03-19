package ru.integrotech.airline.core.airline;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents airline ;
 *
 * Can be used in all projects
 *
 */

public class Airline implements Comparable<Airline> {

	public static final String AFL_CODE = "SU";

	public static Airline of(String code, String name, int minMilesCharge,
			String minMilesLimit) {

		Airline result = new Airline();
		result.setCode(code);
		result.setName(name);
		result.setMinMilesCharge(minMilesCharge);
		result.setMinMilesLimit(minMilesLimit);
		result.setServiceClassMap(new HashMap<>());
		result.setDefaultServiceClasses(new HashSet<>());
		if (code.equals(AFL_CODE)) {
			result.defaultServiceClasses
					.add(ServiceClass.SERVICE_CLASS_TYPE.business);
			result.defaultServiceClasses
					.add(ServiceClass.SERVICE_CLASS_TYPE.economy);
		}
		return result;

	}

	private String code;

	private String name;

	private int minMilesCharge;

	private String minMilesLimit;

	private Map<ServiceClass.SERVICE_CLASS_TYPE, ServiceClass> serviceClassMap;

	/* service classes represents in all airline's flights */
	private Set<ServiceClass.SERVICE_CLASS_TYPE> defaultServiceClasses;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getMinMilesCharge() {
		return minMilesCharge;
	}

	public String getMinMilesLimit() {
		return minMilesLimit;
	}

	public Map<ServiceClass.SERVICE_CLASS_TYPE, ServiceClass> getServiceClassMap() {
		return serviceClassMap;
	}

	public Set<ServiceClass.SERVICE_CLASS_TYPE> getDefaultServiceClasses() {
		return defaultServiceClasses;
	}

	public void setDefaultServiceClasses(
			Set<ServiceClass.SERVICE_CLASS_TYPE> defaultServiceClasses) {
		this.defaultServiceClasses = defaultServiceClasses;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMinMilesCharge(int minMilesCharge) {
		this.minMilesCharge = minMilesCharge;
	}

	public void setMinMilesLimit(String minMilesLimit) {
		this.minMilesLimit = minMilesLimit;
	}

	public void setServiceClassMap(
			Map<ServiceClass.SERVICE_CLASS_TYPE, ServiceClass> serviceClassMap) {
		this.serviceClassMap = serviceClassMap;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Airline airline = (Airline) o;
		return Objects.equals(code, airline.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public int compareTo(Airline o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return String.format("Airline: %3s, %25.25s, %,5d mi, %3.3s",
				this.getCode(), this.getName(), this.minMilesCharge,
				this.minMilesLimit);
	}
}
