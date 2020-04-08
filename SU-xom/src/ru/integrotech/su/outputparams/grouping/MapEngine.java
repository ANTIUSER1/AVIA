package ru.integrotech.su.outputparams.grouping;

import java.util.List;

class MapEngine {
	
	MapEngine() {

	}
			
	MapEngine(String mapEngine, List<App> apps) {
		this.mapEngine = mapEngine;
		this.apps = apps;
	}

	private String mapEngine;

	private List<App> apps;
	
	

	public String getMapEngine() {
		return mapEngine;
	}

	public void setMapEngine(String mapEngine) {
		this.mapEngine = mapEngine;
	}

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}
	
	
}
