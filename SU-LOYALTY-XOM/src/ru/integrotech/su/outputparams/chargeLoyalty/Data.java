package ru.integrotech.su.outputparams.chargeLoyalty;

//
import ru.integrotech.airline.core.info.PassengerLoyaltyInfo;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "OutputData")
public class Data {

	public static Data of(PassengerLoyaltyInfo info) {
		Data result = new Data();
		result.status = info.getStatus().name();
		result.points = info.getPoints();
		return result;
	}

	private String status;

	private int points;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
