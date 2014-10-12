//package V1;

import java.util.Date;

public class Task {

	// task attributes.
	public String taskName;
	public Date taskDeadline;
	public String taskDescription = "";
	public String taskLocation = "";
	
	// currently not in use.
	public String taskPriority = "";
	private int taskId;
	
	// another way to express a task.

	/**
	 * Constructor
	 */
	
	Task(){	
	}
	
	Task(String name){
		this.taskName = name;
	}

	Task(String name, Date date) {
		this.taskName = name;
		this.taskDeadline = date;
	}

	/**
	 * Methods
	 */

	/*
	 * public void setStatus(){ if (expiredTime.after(createdTime)){ this.status
	 * = TASK_STATUS.In_Process; }else { this.status = TASK_STATUS.Expired; } }
	 * 
	 * public void setStatusToBeExpired(){ this.status = TASK_STATUS.Expired; }
	 * 
	 * /*public void setStatusToBeInProcess(){ this.status =
	 * TASK_STATUS.In_Process; }
	 */

	public void setTaskDeadline(Date d) {
		this.taskDeadline = d;
	}

	public void setTaskDescription(String des) {
		this.taskDescription = des;
	}

	public void setTaskLocation(String loc) {
		this.taskLocation = loc;
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
	
	
	/**
	 * Conversion between String and Task Object.
	 * When converting back, must create a task Object, and use this object to convert.
	 * Thus, all the information will be filled into this object.
	 * @return
	 */
	public String convertTaskToString(){
		String result = String.format(StringFormat.taskString, this.taskName, this.taskDeadline.getTime(), this.taskDescription, this.taskLocation);
		return result;
	}
	
	public void convertStringToTask(String taskString){
		String[] taskAttribute = taskString.split("-", StringFormat.splitLimites);
		this.taskName = taskAttribute[0];
		this.taskDeadline = new Date(Long.parseLong(taskAttribute[1]));
		this.taskDescription = taskAttribute[2];
		this.taskLocation = taskAttribute[3];
	}

	
}
