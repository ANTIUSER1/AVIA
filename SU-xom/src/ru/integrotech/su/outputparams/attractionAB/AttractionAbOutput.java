package ru.integrotech.su.outputparams.attractionAB;

import ru.integrotech.airline.core.flight.PassengerChargeInfo;

public class AttractionAbOutput {
	
	private PassengerChargeInfo.Status status;
	
	private int miles;

	AttractionAbOutput() {
		
	}

	public PassengerChargeInfo.Status getStatus() {
		return status;
	}

	public void setStatus(PassengerChargeInfo.Status status) {
		this.status = status;
	}

	public int getMiles() {
		return miles;
	}

	public void setMiles(int miles) {
		this.miles = miles;
	}
	

}
