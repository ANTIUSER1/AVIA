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
	 
	 private List<String> flightCode;
	 
	 private TARIFF_CONDITION tariffCondition;
	 
	 private List<String> tariffMasks;
	 
	 private CHARGE_CONDITION chargeCondition;
	 
	 private Integer chargeCoeff;
	 
	 private Integer distanceCoeff;

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

	public TARIFF_CONDITION getTariffCondition() {
		return tariffCondition;
	}

	public void setTariffCondition(TARIFF_CONDITION classCondition) {
		this.tariffCondition = classCondition;
	}

	public List<String> getTariffMasks() {
		return tariffMasks;
	}

	public void setTariffMasks(List<String> tariffMask) {
		this.tariffMasks = tariffMask;
	}

	public CHARGE_CONDITION getChargeCondition() {
		return chargeCondition;
	}

	public void setChargeCondition(CHARGE_CONDITION chargeCoeffCondition) {
		this.chargeCondition = chargeCoeffCondition;
	}

	public Integer getChargeCoeff() {
		return this.chargeCoeff == null ? 0 : this.chargeCoeff;
	}

	public void setChargeCoeff(Integer chargeCoeff) {
		this.chargeCoeff = chargeCoeff;
	}
	
	public List<String> getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(List<String> flightCode) {
		this.flightCode = flightCode;
	}

	public Integer getDistanceCoeff() {

		return this.distanceCoeff == null ? 0 : this.distanceCoeff ;
	}

	public void setDistanceCoeff(Integer distanceCoeff) {
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

	public enum TARIFF_CONDITION { A, M }

	public enum CHARGE_CONDITION { P, C }



}
