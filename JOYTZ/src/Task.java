//package V1;

import java.util.Date;

public class Task implements Comparable<Task>{

	// task attributes.
	public String taskName;
	public Long taskStartTime;
	public Long taskEndTime;
	public String taskDescription;
	public String taskLocation;
	public String taskPriority;
	
	// the sortKey is default to be sorted by name;
	private static String sortKey = "name";

	/**
	 * Constructor
	 */
	Task() {
		this.taskName = "";
		this.taskStartTime = System.currentTimeMillis();
		this.taskEndTime = System.currentTimeMillis();
		this.taskDescription = "";
		this.taskLocation = "";
		this.taskPriority = "";
	}
	Task (String name){
		this.taskName = name;
	}
	Task(String name, Long startTimeLong, Long endTimeLong, String description, String location,
			String priority) {
		this.taskName = name;
		this.taskStartTime = startTimeLong;
		this.taskEndTime = endTimeLong;
		this.taskDescription = description;
		this.taskLocation = location;
		this.taskPriority = priority;
	}

	/**
	 * Set methods
	 */

	public void setTaskName(String name) {
		this.taskName = name;
	}

	public void setTaskStartTime(Long startTimeLong) {
		this.taskStartTime = startTimeLong;
	}
	
	public void setTaskEndTime(Long endTimeLong){
		this.taskEndTime = endTimeLong;
	}

	public void setTaskDescription(String des) {
		this.taskDescription = des;
	}

	public void setTaskLocation(String loc) {
		this.taskLocation = loc;
	}

	public void setTaskPriority(String priority) {
		this.taskPriority = priority;
	}

	/**
	 * Get methods
	 */

	public String getTaskName() {
		return this.taskName;
	}

	public Long getTaskStartTime() {
		return this.taskStartTime;
	}
	
	public Long getTaskEndTime(){
		return this.taskEndTime;
	}

	public String getTaskDescription() {
		return this.taskDescription;
	}

	public String getTaskLocation() {
		return this.taskLocation;
	}

	public String getTaskPriority() {
		return this.taskPriority;
	}
	
	public String get(String attribute){
		switch(attribute){
		case "name": 
			return taskName;
		case "descritption":
			return taskDescription;
		case "startTime":
			return taskStartTime + "";
		case "endTime":
			return taskEndTime + "";
		case "location":
			return taskLocation;
		case "priority":
			return taskPriority;
		default:
			return "name";
		}
	}
	
	public static void setSortKey(String key){
		sortKey = key;
	}

	@Override
	public int compareTo(Task o){
		Task that = (Task)o;
		String thisString = this.get(sortKey);
		String thatString = that.get(sortKey);
		
		return thisString.compareToIgnoreCase(thatString);
	}

	@Override
	public boolean equals(Object o){
		Task that = (Task) o;
		if (!that.getTaskName().equals(taskName)){
			return false;
		}else if(!that.getTaskStartTime().equals(taskStartTime)){
			return false;
		}else if(!that.getTaskEndTime().equals(taskEndTime)){
			return false;
		}else if(!that.getTaskDescription().equals(taskDescription)){
			return false;
		}else if(!that.getTaskLocation().equals(taskLocation)){
			return false;
		}else if(!that.getTaskPriority().equals(taskPriority)){
			return false;
		}
		return true;
	}

}
