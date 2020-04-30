package ru.integrotech.su.inputparams.grouping;

public class InputParamsGrouping {
	
	private String mapEngine;
	
	private String app;
	
	private Integer zoomLevel;
	
	public InputParamsGrouping() {
	}


	public String getMapEngine() {
		return mapEngine;
	}

	public void setMapEngine(String mapEngine) {
		this.mapEngine = mapEngine;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public Integer getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(Integer zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	@Override
	public String toString() {
		return "InputParamsGrouping [mapEngine=" + mapEngine + ", app=" + app
				+ ", zoomLevel=" + zoomLevel + "]";
	}
	
}
