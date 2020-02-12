package ru.integrotech.airline.core.flight;

import ru.integrotech.airline.core.airline.Airline;
import ru.integrotech.airline.core.location.Airport;

public class PassengerChargeInfo {
	
	public static PassengerChargeInfo of(Airline airline, Airport origin, Airport destination, String fareCode,
										String bookingClassCode, int distance, String tickedDesignator, Status status) {
		PassengerChargeInfo result = new PassengerChargeInfo();
		result.setAirline(airline);
		result.setOrigin(origin);
		result.setDestination(destination);
		result.setFareCode(fareCode);
		result.setBookingClassCode(bookingClassCode);
		result.setDistance(distance);
		result.setTickedDesignator(tickedDesignator);
		result.setStatus(status);
		return result;
	}
	

	private Airline airline;
	
	private Airport origin;
	
	private Airport destination;

	private String fareCode;

	private String bookingClassCode;

	private int distance;

	private String tickedDesignator;

    private double distanceCoeff;

	private int totalBonusMiles;

	private Status status;

	private PassengerChargeInfo() {
		
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public double getDistanceCoeff() {
		return distanceCoeff;
	}

	public void setDistanceCoeff(double distanceCoeff) {
		this.distanceCoeff = distanceCoeff;
	}

	public String getFlightCode() {
		return String.format("%s%s", this.origin.getCode(), this.destination.getCode());
	}

	public String getTickedDesignator() {
		return tickedDesignator;
	}

	public void setTickedDesignator(String tickedDesignator) {
		this.tickedDesignator = tickedDesignator;
	}

    public String getFareCode() {
        return fareCode;
    }

    public void setFareCode(String fareCode) {
        this.fareCode = fareCode;
    }

    public String getBookingClassCode() {
        return bookingClassCode;
    }

    public void setBookingClassCode(String bookingClassCode) {
        this.bookingClassCode = bookingClassCode;
    }

	public int getTotalBonusMiles() {
		return totalBonusMiles;
	}

	public void setTotalBonusMiles(int totalBonusMiles) {
		this.totalBonusMiles = totalBonusMiles;
	}

	public int getFlightDistance() {
		return this.origin.getOutcomeFlight(this.destination, this.airline).getDistance();
	}

	public int getMinBonusMiles() {
		int result = 0;
		int minBonus = this.airline.getMinMilesCharge();
		String milesLimitation = this.airline.getMinMilesLimit();
		boolean isInternational = this.isInternational();
		if (minBonus > 0
				&& (milesLimitation.equals("A")
				|| (milesLimitation.equals("I") && isInternational))) {

			result = minBonus;
		}
		return result;
	}

	private boolean isInternational() {
		return !this.origin.getCity().getCountry().equals(this.destination.getCity().getCountry());
	}

    public enum Status {full, partial, nodata}


}
