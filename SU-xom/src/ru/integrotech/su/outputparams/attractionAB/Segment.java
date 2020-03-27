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
		result.setOriginIATA(info.getOrigin().getCode());
		result.setDestinationIATA(info.getDestination().getCode());
		result.setStatus(info.getStatus());
		result.setMiles(info.getTotalBonusMiles());
		return result;
	}

	private String originIATA;

	private String destinationIATA;

	private PassengerMilesInfo.Status status;

	private int miles;

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
