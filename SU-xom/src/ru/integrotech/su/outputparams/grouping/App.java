package ru.integrotech.su.outputparams.grouping;

import java.util.List;

class App {
	
	App() {

	}
			
	App(String app, List<Level> levels) {
		this.app = app;
		this.levels = levels;
	}

	private String app;

	private List<Level> levels;
	
	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
	
	
}
