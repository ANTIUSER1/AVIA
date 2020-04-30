package ru.integrotech.su.outputparams.charge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.integrotech.airline.core.info.PassengerChargeInfo;
import ru.integrotech.su.common.Airline;

/**
 * container for MilesAmount
 *
 * data(private Airline airline; private ClassOfService classOfService; private
 * int distance; private List<Fare> fareGroups;)
 */
class MilesAmount implements Comparable<MilesAmount> {

	/**
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * At first generates the instance , then sets the field value from
	 * PassengerCharge
	 *
	 * @param charge
	 * @return
	 */
	public static MilesAmount of(PassengerChargeInfo charge) {
		List<Fare> fareGroups = new ArrayList<>();
		fareGroups.add(Fare.of(charge));
		MilesAmount res = new MilesAmount();
		res.setAirline(Airline.of(charge.getAirline().getCode()));
		res.setClassOfService(ClassOfService.of(charge.getServiceClass()));
		res.setDistance(charge.getDistance());
		res.setFareGroups(fareGroups);
		return res;
	}

	private Airline airline;

	private ClassOfService classOfService;

	private int distance;

	private List<Fare> fareGroups;

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public ClassOfService getClassOfService() {
		return classOfService;
	}

	public void setClassOfService(ClassOfService classOfService) {
		this.classOfService = classOfService;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<Fare> getFareGroups() {
		return fareGroups;
	}

	public void setFareGroups(List<Fare> fareGroups) {
		this.fareGroups = fareGroups;
	}

	void update(PassengerChargeInfo charge) {
		int i = 0;

		while (i < this.fareGroups.size()
				&& !this.fareGroups.get(i).getFareGroup().getFareGroupCode()
						.equals(charge.getTariff().getCode())) {
			i++;
		}

		if (i == this.fareGroups.size()) {
			this.fareGroups.add(Fare.of(charge));
		} else {
			this.fareGroups.get(i).update(charge);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MilesAmount that = (MilesAmount) o;
		return distance == that.distance
				&& Objects.equals(airline, that.airline)
				&& Objects.equals(classOfService, that.classOfService)
				&& Objects.equals(fareGroups, that.fareGroups);
	}

	@Override
	public int hashCode() {
		return Objects.hash(airline, classOfService, distance, fareGroups);
	}

	@Override
	public int compareTo(MilesAmount o) {
		return this.classOfService.getClassOfServiceCode().compareTo(
				o.getClassOfService().classOfServiceCode);
	}
}
