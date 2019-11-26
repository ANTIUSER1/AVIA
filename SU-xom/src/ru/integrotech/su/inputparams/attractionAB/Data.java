package ru.integrotech.su.inputparams.attractionAB;

import java.util.List;

class Data {
	
	private List<Segment> segments;
	
	private String tierCode;

	private Data() {
		
	}

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
		this.tierCode = tierCode;
	}
	
	
	
	

}
