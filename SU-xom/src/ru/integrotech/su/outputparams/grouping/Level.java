package ru.integrotech.su.outputparams.grouping;

class Level {
			
	Level() {
	
	}
	
	Level(String groupingLevel, int zoomLevelMin, int zoomLevelMax) {
		super();
		this.groupingLevel = groupingLevel;
		this.zoomLevelMin = zoomLevelMin;
		this.zoomLevelMax = zoomLevelMax;
	}



	private String groupingLevel;
	
	int zoomLevelMin;
	
	int zoomLevelMax;

	public String getGroupingLevel() {
		return groupingLevel;
	}

	public void setGroupingLevel(String groupingLevel) {
		this.groupingLevel = groupingLevel;
	}

	public int getZoomLevelMin() {
		return zoomLevelMin;
	}

	public void setZoomLevelMin(int zoomLevelMin) {
		this.zoomLevelMin = zoomLevelMin;
	}

	public int getZoomLevelMax() {
		return zoomLevelMax;
	}

	public void setZoomLevelMax(int zoomLevelMax) {
		this.zoomLevelMax = zoomLevelMax;
	}
	
	

}
