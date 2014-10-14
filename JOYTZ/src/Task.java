//package V1;

import java.util.Date;

public class Task {

	// task attributes.
	public String taskName;
	public Date taskDeadline;
	public String taskDescription;
	public String taskLocation;
	public String taskPriority;

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

}
