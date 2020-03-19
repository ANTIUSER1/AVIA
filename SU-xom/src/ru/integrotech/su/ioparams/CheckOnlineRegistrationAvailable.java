package ru.integrotech.su.ioparams;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import ru.integrotech.airline.core.info.PassengerFlightInfo;

/**
 * container for checkOnlineRegAvailableRequest (request body for
 * CheckOnlineRegistrationAvailable project)
 *
 * data ( private PassengerFlightInfo passengerFlightInfo )
 */
public class CheckOnlineRegistrationAvailable {

	// / ru.integrotech.su.ioparams.CheckOnlineRegistrationAvailable
	private PassengerFlightInfo passengerFlightInfo = new PassengerFlightInfo();

	@XmlTransient
	public PassengerFlightInfo getPassengerFlightInfo() {
		return passengerFlightInfo;
	}

	public void setPassengerFlightInfo(PassengerFlightInfo passengerFlightInfo) {
		this.passengerFlightInfo = passengerFlightInfo;
	}

	public String getAirlineCode() {
		return passengerFlightInfo.getAirlineCode();
	}

	public void setAirlineCode(String airlineCode) {
		passengerFlightInfo.setAirlineCode(airlineCode);
	}

	public int getFlightNumber() {
		return passengerFlightInfo.getFlightNumber();
	}

	public void setFlightNumber(int flightNumber) {
		passengerFlightInfo.setFlightNumber(flightNumber);
	}

	public String getDepartureAirportCode() {
		return passengerFlightInfo.getDepartureAirportCode();
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		passengerFlightInfo.setDepartureAirportCode(departureAirportCode);
	}

	public List<String> getSsrCodes() {
		return passengerFlightInfo.getSsrCodes();
	}

	public void setSsrCodes(List<String> ssrCodes) {
		passengerFlightInfo.setSsrCodes(ssrCodes);
	}

	public boolean isOnlineRegistrationAvailable() {
		return passengerFlightInfo.isOnlineRegistrationAvailable();
	}

	public void setOnlineRegistrationAvailable(
			boolean onlineRegistrationAvailable) {
		passengerFlightInfo
				.setOnlineRegistrationAvailable(onlineRegistrationAvailable);
	}
}
