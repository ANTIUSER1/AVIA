package ru.integrotech.airline.core.bonus;

import java.util.List;

public class ChargeRule {

	public static ChargeRule copy(ChargeRule other) {
		ChargeRule result = new ChargeRule();
		result.setBasic(other.isBasic);
		result.setAnyBookingClass(other.anyBookingClass);
		result.setBookingClass(other.bookingClass);
		result.setFlightCode(other.getFlightCode());
		result.setTariffCondition(other.tariffCondition);
		result.setTariffMasks(other.tariffMasks);
		result.setChargeCondition(other.chargeCondition);
		result.setChargeCoeff(other.chargeCoeff);
		result.setDistanceCoeff(other.distanceCoeff);
		return result;
	}
	
	 private boolean isBasic;
	 
	 private boolean anyBookingClass;
	 
	 private String bookingClass;
	 
	 private String flightCode;
	 
	 private String tariffCondition;
	 
	 private List<String> tariffMasks;
	 
	 private String chargeCondition;
	 
	 private int chargeCoeff;
	 
	 private int distanceCoeff;

	 private ChargeRule() {
		
	 }

	public boolean isBasic() {
		return isBasic;
	}

	public void setBasic(boolean isPublic) {
		this.isBasic = isPublic;
	}

	public boolean isAnyBookingClass() {
		return anyBookingClass;
	}

	public void setAnyBookingClass(boolean anyBookingClass) {
		this.anyBookingClass = anyBookingClass;
	}

	public String getBookingClass() {
		return bookingClass;
	}

	public void setBookingClass(String bookingClass) {
		this.bookingClass = bookingClass;
	}

	public String getTariffCondition() {
		return tariffCondition;
	}

	public void setTariffCondition(String classCondition) {
		this.tariffCondition = classCondition;
	}

	public List<String> getTariffMasks() {
		return tariffMasks;
	}

	public void setTariffMasks(List<String> tariffMask) {
		this.tariffMasks = tariffMask;
	}

	public String getChargeCondition() {
		return chargeCondition;
	}

	public void setChargeCondition(String chargeCoeffCondition) {
		this.chargeCondition = chargeCoeffCondition;
	}

	public int getChargeCoeff() {
		return chargeCoeff;
	}

	public void setChargeCoeff(int chargeCoeff) {
		this.chargeCoeff = chargeCoeff;
	}
	
	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public int getDistanceCoeff() {
		return distanceCoeff;
	}

	public void setDistanceCoeff(int distanceCoeff) {
		this.distanceCoeff = distanceCoeff;
	}

	@Override
	public String toString() {
		return "ChargeRule{" +
				"isBasic=" + isBasic +
				", anyBookingClass=" + anyBookingClass +
				", bookingClass='" + bookingClass + '\'' +
				", flightCode='" + flightCode + '\'' +
				", tariffCondition='" + tariffCondition + '\'' +
				", tariffMasks=" + tariffMasks +
				", chargeCondition='" + chargeCondition + '\'' +
				", chargeCoeff=" + chargeCoeff +
				", distanceCoeff=" + distanceCoeff +
				'}';
	}


}
