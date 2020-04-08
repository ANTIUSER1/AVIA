package ru.integrotech.su.outputparams.grouping;

import ru.integrotech.airline.register.GroupingRecord;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.su.inputparams.grouping.InputParamsGrouping;

import java.util.ArrayList;
import java.util.List;

public class GroupingBuilder {

	public static String[] getRegisterNames() {
		return REGISTER_NAMES;
	}

	/**
	 * names of necessary registers
	 * */
	private static final String[] REGISTER_NAMES = new String[] {"localGrouping"};

	public static GroupingBuilder of(RegisterCache cache) {
	    GroupingBuilder builder = new GroupingBuilder();
	    builder.cache = cache;
	    return builder;
    }
		
	private RegisterCache cache;

	public GroupingTable buildResult(InputParamsGrouping input) {
		
		GroupingTable result = new GroupingTable();
		
		for (GroupingRecord record : this.getActualRecords(input)) {
			this.updateGroupingTable(result, record);
		}
		
		return result;
	}

	private List<GroupingRecord> getActualRecords(InputParamsGrouping input) {
		List<GroupingRecord> result = new ArrayList<>();
		if (input.getZoomLevel() == null) {
			input.setZoomLevel(0);
		}
		for (GroupingRecord record : this.cache.getGroupingRecords()) {
			if(this.isFitsByParams(record, input)) {
				result.add(record);
			}
		}
		return result;
	}
	
	private boolean isFitsByParams(GroupingRecord record, InputParamsGrouping input) {
		boolean result = false;
		
		if ((input.getZoomLevel() == 0) 
				&& this.isEmpty(input.getApp())
				&& this.isEmpty(input.getMapEngine())) {
		
			result = true;
			
		} else if ((input.getZoomLevel() == 0) && this.isEmpty(input.getApp())) {
			
			result = record.getMapEngine().equals(input.getMapEngine()); 
		
		} else if(input.getZoomLevel() == 0) {
			
			result = record.getMapEngine().equals(input.getMapEngine())
					 && record.getApp().equals(input.getApp());
		
		}
	
		return result;
	}
	
	private void updateGroupingTable(GroupingTable groupingTable, GroupingRecord record) {
	
		int engineCounter = 0;
		
		for (MapEngine mapEngine : groupingTable.getEngines()) {
			 if (mapEngine.getMapEngine().equals(record.getMapEngine())) {
				 engineCounter++;
				 
				 int appCounter = 0;
				 for (App app : mapEngine.getApps()) {
					 
					 if (app.getApp().equals(record.getApp())) {
						 appCounter++;
						 app.getLevels().add(this.createLevel(record));
					 }
				 }
				 if (appCounter == 0) {
					 mapEngine.getApps().add(this.createApp(record));
				 }
				 
			 }
			 
		 } if (engineCounter == 0) {
			 groupingTable.getEngines().add(this.createMapEngine(record));
		 }
	}
	
	private MapEngine createMapEngine(GroupingRecord record) {
			
		App app = this.createApp(record);
		List<App> apps =  new ArrayList<>();
		apps.add(app);
		return new MapEngine(record.getMapEngine(), apps);
	}
	
	private App createApp(GroupingRecord record) {
		
		Level level = this.createLevel(record);
		List<Level> levels = new ArrayList<>();
		levels.add(level);
		return new App(record.getApp(), levels);
		
	}
	
	private Level createLevel(GroupingRecord record) {
		
		return new Level(record.getGroupingLevel(), 
								record.getZoomLevelMin(),
								record.getZoomLevelMax());
		
	}
	
	private boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
}
