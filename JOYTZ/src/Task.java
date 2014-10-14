//package V1;

import java.util.Comparator;
import java.util.Date;

public class Task implements Comparable<Task>{

	// task attributes.
	public String taskName;
	public Date taskDeadline;
	public String taskDescription;
	public String taskLocation;
	public String taskPriority;
	
	public static String sortKey = "name";

	/**
	 * Constructor
	 */
	Task() {
		this.taskName = "";
		this.taskDeadline = new Date(0, 0, 0);
		this.taskDescription = "";
		this.taskLocation = "";
		this.taskPriority = "";
	}

	Task(String name, Date deadline, String description, String location,
			String priority) {
		this.taskName = name;
		this.taskDeadline = deadline;
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

	public void setTaskDeadline(Date d) {
		this.taskDeadline = d;
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

	public Date getTaskDeadline() {
		return this.taskDeadline;
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
		case "deadline":
			return taskDeadline.getTime() + "";
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
	
	public boolean equals(Object o){
		Task that = (Task) o;
		if (!that.getTaskName().equals(taskName)){
			return false;
		}else if(!that.getTaskDeadline().equals(taskDeadline)){
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
