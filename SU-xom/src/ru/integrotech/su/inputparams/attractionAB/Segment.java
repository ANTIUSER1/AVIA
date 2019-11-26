package ru.integrotech.su.inputparams.attractionAB;

class Segment {
	
	 private String orignIATA;
	 
	 private String destinationIATA;
	 
	 private String bookingClassCode;
	 
	 private String fareBasisCode;
	 
	 private String ticketDesignator;

	 private Segment() {
		
	 }

	public String getOrignIATA() {
		return orignIATA;
	}

	public void setOrignIATA(String orignIATA) {
		this.orignIATA = orignIATA;
	}

	public String getDestinationIATA() {
		return destinationIATA;
	}

	public void setDestinationIATA(String destinationIATA) {
		this.destinationIATA = destinationIATA;
	}

	public String getBookingClassCode() {
		return bookingClassCode;
	}

	public void setBookingClassCode(String bookingClassCode) {
		this.bookingClassCode = bookingClassCode;
	}

	public String getFareBasisCode() {
		return fareBasisCode;
	}

	public void setFareBasisCode(String fareBasisCode) {
		this.fareBasisCode = fareBasisCode;
	}

	public String getTicketDesignator() {
		return ticketDesignator;
	}

	public void setTicketDesignator(String ticketDesignator) {
		this.ticketDesignator = ticketDesignator;
	}
	
	
	 
	 

}
