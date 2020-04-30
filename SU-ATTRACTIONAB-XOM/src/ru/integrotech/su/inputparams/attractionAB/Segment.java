package ru.integrotech.su.inputparams.attractionAB;

import javax.xml.bind.annotation.XmlType;

/**
 * container for Segment for Data class
 *
 * data ( private String originIATA; private String destinationIATA; private
 * String bookingClassCode; private String fareBasisCode; private String
 * ticketDesignator; )
 */
@XmlType(name = "InputSegment")
public class Segment {

	private String airlineIATA;

	private String originIATA;

	private String destinationIATA;

	private String bookingClassCode;

	private String fareBasisCode;

	private String ticketDesignator;

    public String getAirlineIATA() {
        return airlineIATA;
    }

    public void setAirlineIATA(String airlineIATA) {

        if (airlineIATA != null) {
            airlineIATA = airlineIATA.toUpperCase();
        }

        this.airlineIATA = airlineIATA;
    }

    public String getOriginIATA() {
		return originIATA;
	}

	public void setOriginIATA(String originIATA) {

		if (originIATA != null) {
			originIATA = originIATA.toUpperCase();
		}

		this.originIATA = originIATA;
	}

	public String getDestinationIATA() {
		return destinationIATA;
	}

	public void setDestinationIATA(String destinationIATA) {

		if (destinationIATA != null) {
			destinationIATA = destinationIATA.toUpperCase();
		}

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