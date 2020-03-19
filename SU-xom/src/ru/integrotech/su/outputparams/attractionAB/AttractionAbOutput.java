package ru.integrotech.su.outputparams.attractionAB;

import java.util.List;
import java.util.Map;

import ru.integrotech.airline.core.info.PassengerMilesInfo.Status;

public class AttractionAbOutput {
	/**
	 * 
	 * Static constructor <br />
	 * constructs, then sets up the instance's fields value
	 *
	 * @param statusMap
	 * @param totalMiles
	 * @param segments
	 * @return
	 */
	public static AttractionAbOutput of(Map<Status, Integer> statusMap,
			int totalMiles, List<Segment> segments) {
		AttractionAbOutput result = new AttractionAbOutput();
		result.setTotalStatus(getTotalStatus(statusMap));
		result.setTotalMiles(totalMiles);
		result.setSegments(segments);
		return result;
	}

	private static Status getTotalStatus(Map<Status, Integer> statusMap) {
		if (statusMap.isEmpty()) {
			return Status.nodata;
		} else if (statusMap.keySet().size() == 1) {
			return statusMap.keySet().iterator().next();
		} else {
			return Status.partial;
		}
	}

	private Status totalStatus;

	private int totalMiles;

	private List<Segment> segments;

	public Status getTotalStatus() {
		return totalStatus;
	}

	public void setTotalStatus(Status totalStatus) {
		this.totalStatus = totalStatus;
	}

	public int getTotalMiles() {
		return totalMiles;
	}

	public void setTotalMiles(int totalMiles) {
		this.totalMiles = totalMiles;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}
}
