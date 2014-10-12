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
	public static final String taskString = "%s-%s-%s-%s";
	public static final int splitLimites = 3;

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
	
	public String convertToString(){
		String result = String.format(taskString, this.taskName, this.taskDeadline.getTime(), this.taskDescription, this.taskLocation);
		return result;
	}
	
	public void convertFromString(String taskString){
		String[] taskAttribute = taskString.split("-", splitLimites);
		this.taskName = taskAttribute[0];
		this.taskDeadline = new Date(Long.parseLong(taskAttribute[1]));
		this.taskDescription = taskAttribute[2];
		this.taskLocation = taskAttribute[3];
	}

	/*
	 * @Override public void run() { setStatusToBeExpired();
	 * Executor.performCheckStatus(); }
	 */
}
