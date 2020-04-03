package ru.integrotech.su.outputparams.attractionAB;

import javax.xml.bind.annotation.XmlType;

import ru.integrotech.airline.core.info.PassengerMilesInfo;

import java.util.Objects;

/**
 * container for Segment data
 *
 * data( private String originIATA; private String destinationIATA; private
 * PassengerChargeInfo.Status status; private int miles; )
 */
@XmlType(name = "OutputSegment")
public class Segment {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param info
	 * @return
	 */
	public static Segment of(PassengerMilesInfo info) {
		Segment result = new Segment();
		result.setAirlineIATA(info.getAirline().getCode());
		result.setOriginIATA(info.getOrigin().getCode());
		result.setDestinationIATA(info.getDestination().getCode());
		result.setBookingClassCode(info.getBookingClassCode());
		result.setFareBasisCode(info.getFareCode());
		result.setTicketDesignator(info.getTickedDesignator());
		result.setStatus(info.getStatus());
		result.setMiles(info.getTotalBonusMiles());
		result.setPercent(info.getPercent());
		return result;
	}

    private String airlineIATA;

    private String originIATA;

	private String destinationIATA;

	private String bookingClassCode;

	private String fareBasisCode;

	private String ticketDesignator;

	private PassengerMilesInfo.Status status;

	private int miles;

	private Integer percent;

	public String getOriginIATA() {
		return originIATA;
	}

	public void setOriginIATA(String originIATA) {
		this.originIATA = originIATA;
	}

	public String getDestinationIATA() {
		return destinationIATA;
	}

	public void setDestinationIATA(String destinationIATA) {
		this.destinationIATA = destinationIATA;
	}

	public PassengerMilesInfo.Status getStatus() {
		return status;
	}

	public void setStatus(PassengerMilesInfo.Status status) {
		this.status = status;
	}

	public int getMiles() {
		return miles;
	}

	public void setMiles(int miles) {
		this.miles = miles;
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

    public String getAirlineIATA() {
        return airlineIATA;
    }

    public void setAirlineIATA(String airlineIATA) {
        this.airlineIATA = airlineIATA;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Segment segment = (Segment) o;
		return miles == segment.miles &&
				Objects.equals(originIATA, segment.originIATA) &&
				Objects.equals(destinationIATA, segment.destinationIATA) &&
				status == segment.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(originIATA, destinationIATA, status, miles);
	}
}
