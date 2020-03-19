package ru.integrotech.airline.core.info;

import java.util.List;

/**
 * Describes the flight of exact passenger in exact flight
 *
 * Used in CheckOnlineRegistrationAvailable project
 *
 */

public class PassengerFlightInfo {
	
	private String airlineCode;
	
	private int flightNumber;
	
	private String departureAirportCode;
		
	private List<String> ssrCodes;
	
	private boolean onlineRegistrationAvailable ;

	public String getAirlineCode() {
		return airlineCode == null ? null : airlineCode.toUpperCase();
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public List<String> getSsrCodes() {
		return ssrCodes;
	}

	public void setSsrCodes(List<String> ssrCodes) {
		this.ssrCodes = ssrCodes;
	}

	public boolean isOnlineRegistrationAvailable() {
		return onlineRegistrationAvailable;
	}

	public void setOnlineRegistrationAvailable(boolean onlineRegistrationAvailable) {
		this.onlineRegistrationAvailable = onlineRegistrationAvailable;
	}
	
	


}
