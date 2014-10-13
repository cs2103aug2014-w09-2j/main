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
	Task(){
	}
	
	Task(String name, Date deadline, String description, String location, String priority) {
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
	
	public String getTaskPriority(){
		return this.taskPriority;
	}
	/**
	 * Conversion between String and Task Object. When converting back, must
	 * create a task Object, and use this object to convert. Thus, all the
	 * information will be filled into this object.
	 * 
	 * @return
	 */
	public String convertTaskToString() {
		String result = String.format(StringFormat.taskString, this.taskName,
				this.taskDeadline.getTime(), this.taskDescription,
				this.taskLocation);
		return result;
	}

	public void convertStringToTask(String taskString) {
		String[] taskAttribute = taskString
				.split("-", StringFormat.splitLimits);
		this.taskName = taskAttribute[0];
		this.taskDeadline = new Date(Long.parseLong(taskAttribute[1]));
		this.taskDescription = taskAttribute[2];
		this.taskLocation = taskAttribute[3];
	}

}
