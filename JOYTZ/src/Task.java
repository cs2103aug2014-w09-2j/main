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
	
	// toString return a String in this format.
	public static final String toStringFormat = "name: %s deadline: %s description: %s location: %s";

	/**
	 * Constructor
	 */
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
	
	public String toStirng(){
		String result = String.format(toStringFormat, this.taskName, this.taskDeadline, this.taskDescription, this.taskLocation);
		return result;
	}

	/*
	 * @Override public void run() { setStatusToBeExpired();
	 * Executor.performCheckStatus(); }
	 */
}
