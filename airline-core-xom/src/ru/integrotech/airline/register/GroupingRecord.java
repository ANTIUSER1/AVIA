package ru.integrotech.airline.register;

public class GroupingRecord {

    private String mapEngine;

    private String app;

    private String groupingLevel;

    private int zoomLevelMin;

    private int zoomLevelMax;

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

    @Override
    public String toString() {
        return "GroupingRecord [mapEngine=" + mapEngine + ", app=" + app
                + ", groupingLevel=" + groupingLevel + ", zoomLevelMin="
                + zoomLevelMin + ", zoomLevelMax=" + zoomLevelMax + "]";
    }
}
