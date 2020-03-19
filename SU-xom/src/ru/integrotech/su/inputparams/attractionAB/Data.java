package ru.integrotech.su.inputparams.attractionAB;

import java.util.List;

/**
 * container for Data for AttractionABInput
 *
 * data ( private List<Segment> segments; private String tierCode; )
 */

class Data {

	private List<Segment> segments;

	private String tierCode;

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	public String getTierCode() {
		return tierCode;
	}

	public void setTierCode(String tierCode) {

		if (tierCode != null) {
			tierCode = tierCode.toLowerCase();
		}

		this.tierCode = tierCode;
	}

}
