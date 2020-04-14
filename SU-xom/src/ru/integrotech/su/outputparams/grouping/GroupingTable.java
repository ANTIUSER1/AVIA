package ru.integrotech.su.outputparams.grouping;

import java.util.ArrayList;
import java.util.List;

public class GroupingTable {
		
	public GroupingTable() {
		this.engines = new ArrayList<MapEngine>();
	}

	private List<MapEngine> engines;

	public List<MapEngine> getEngines() {
		return engines;
	}

	public void setEngines(List<MapEngine> engines) {
		this.engines = engines;
	}
	
	

}
