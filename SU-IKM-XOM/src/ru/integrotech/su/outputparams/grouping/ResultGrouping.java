package ru.integrotech.su.outputparams.grouping;

public class ResultGrouping {
	
	private String groupingLevel;
	
	private GroupingTable groupingTable;
	
	public ResultGrouping() {
	};

	
	public String getGroupingLevel() {
		return groupingLevel;
	}

	
	public void setGroupingLevel(String groupingLevel) {
		this.groupingLevel = groupingLevel;
	}
	
		
	public GroupingTable getGroupingTable() {
		return groupingTable;
	}


	public void setGroupingTable(GroupingTable groupingTable) {
		this.groupingTable = groupingTable;
	}


	@Override
	public String toString() {
		return "ResultGrouping [groupingLevel=" + groupingLevel + "]";
	}

}
