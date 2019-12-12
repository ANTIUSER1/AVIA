package ru.integrotech.airline.core.flight;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.location.Airport;

public class PassengerChargeInfo {
	
	public static PassengerChargeInfo of (boolean isPrivate, Airline airline, Airport origin, Airport destination, 
										int distance, int chargeCoeff, int distanceCoeff, Status chargeStatus) {
		PassengerChargeInfo result = new PassengerChargeInfo();
		result.setBasic(isPrivate);
		result.setAirline(airline);
		result.setOrigin(origin);
		result.setDestination(destination);
		result.setDistance(distance);
		result.setChargeCoeff(chargeCoeff);
		result.setDistanceCoeff(distanceCoeff);
		result.setChargeStatus(chargeStatus);
		return result;
	}
	
	private boolean isBasic;
	
	private Airline airline;
	
	private Airport origin;
	
	private Airport destination;
	
	private int distance;
	
	private int chargeCoeff;

	private int distanceCoeff;

	private Status chargeStatus;

	private PassengerChargeInfo() {
		
	}

	public boolean isBasic() {
		return isBasic;
	}

	public void setBasic(boolean isPrivate) {
		this.isBasic = isPrivate;
	}

	public Airport getOrigin() {
		return origin;
	}

	public void setOrigin(Airport origin) {
		this.origin = origin;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getChargeCoeff() {
		return chargeCoeff;
	}

	public void setChargeCoeff(int chargeCoeff) {
		this.chargeCoeff = chargeCoeff;
	}

	public Status getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Status chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public int getDistanceCoeff() {
		return distanceCoeff;
	}

	public void setDistanceCoeff(int distanceCoeff) {
		this.distanceCoeff = distanceCoeff;
	}

	public String getFlightCode() {
		return String.format("%s%s", this.origin.getCode(), this.destination.getCode());
	}

	public enum Status {distance, full, nodata}


}
