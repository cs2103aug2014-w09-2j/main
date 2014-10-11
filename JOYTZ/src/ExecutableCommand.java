//package V1;

import java.util.Date;

public class ExecutableCommand {
	private String action;
	private String taskName;
	private String taskDescription;
	private Date taskDeadline;
	private String taskLocation;
	private String taskPriority;
	private int taskId;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = null;
		this.taskName = null;
		this.taskDescription = null;
		this.taskDeadline = null;
		this.taskLocation = null;
		this.taskId = -1;
	}

	ExecutableCommand(String action) {
		this.action = action;
		this.taskName = null;
		this.taskDescription = null;
		this.taskDeadline = null;
		this.taskLocation = null;
		this.taskId = -1;
	}

	/**
	 * methods
	 */

	public void setAction(String action) {
		this.action = action;
	}
	
	public void setTaskName(String name){
		this.taskName = name;
	}
	
	public void setTaskDescription(String description) {
		this.taskDescription = description;
	}

	public void setTaskDeadline(Date date) {
		this.taskDeadline = date;
	}

	public void setTaskLocation(String location) {
		this.taskLocation = location;
	}
	
	public void setTaskPriority(String priority){
		this.taskPriority = priority;
	}
	
	public void setTaskId(int id) {
		this.taskId = id;
	}

	public String getAction() {
		return action;
	}
	
	public String getTaskName(){
		return taskName;
	}
	
	public String getTaskDescription() {
		return taskDescription;
	}

	public Date getTaskDate() {
		return taskDeadline;
	}

	public String getTaskLocation() {
		return taskLocation;
	}
	
	public String getTaskPriority(){
		return taskPriority;
	}
	
	public int getTaskId() {
		return taskId;
	}

}
