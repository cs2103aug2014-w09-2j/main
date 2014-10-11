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
	private String errorMessage;
	private String updatedTaskName;
	private String updatedTaskDescription;
	private Date updatedTaskDeadline;
	private String updatedTaskLocation;
	private String updatedTaskPriority;
	private int updatedTaskId;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = "";
		this.taskName = "";
		this.taskDescription = "";
		this.taskDeadline = new Date();
		this.taskLocation = "";
		this.taskPriority = "";
		this.taskId = -1;
		this.errorMessage = "";
		this.updatedTaskName = "";
		this.updatedTaskDescription = "";
		this.updatedTaskDeadline = new Date();
		this.updatedTaskLocation = "";
		this.updatedTaskPriority = "";
		this.updatedTaskId = -1;
	}

	ExecutableCommand(String action) {
		this.action = action;
		this.taskName = "";
		this.taskDescription = "";
		this.taskDeadline = new Date();
		this.taskLocation = "";
		this.taskPriority = "";
		this.taskId = -1;
		this.errorMessage = "";
		this.updatedTaskName = "";
		this.updatedTaskDescription = "";
		this.updatedTaskDeadline = new Date();
		this.updatedTaskLocation = "";
		this.updatedTaskPriority = "";
		this.updatedTaskId = -1;
	}

	/**
	 * set methods
	 */

	public void setAction(String action) {
		this.action = action;
	}

	public void setTaskName(String name) {
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

	public void setTaskPriority(String priority) {
		this.taskPriority = priority;
	}

	public void setTaskId(int id) {
		this.taskId = id;
	}

	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}

	public void setUpdatedTaskId(int id) {
		this.updatedTaskId = id;
	}

	public void setUpdatedTaskDescription(String description) {
		this.updatedTaskDescription = description;
	}

	public void setUpdatedTaskDeadline(Date date) {
		this.updatedTaskDeadline = date;
	}

	public void setUpdatedTaskLocation(String location) {
		this.updatedTaskLocation = location;
	}

	public void setUpdatedTaskPriority(String priority) {
		this.updatedTaskPriority = priority;
	}

	public void setUpdatedTaskName(String name) {
		this.updatedTaskName = name;
	}

	/**
	 * get methods
	 */

	public String getAction() {
		return action;
	}

	public String getTaskName() {
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

	public String getTaskPriority() {
		return taskPriority;
	}

	public int getTaskId() {
		return taskId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getUpdatedTaskName() {
		return updatedTaskName;
	}

	public String getUpdatedTaskDescription() {
		return updatedTaskDescription;
	}

	public Date getUpdatedTaskDeadline() {
		return updatedTaskDeadline;
	}

	public String getUpdatedTaskLocation() {
		return updatedTaskLocation;
	}

	public String getUpdatedTaskPriority() {
		return updatedTaskPriority;
	}

	public int getUpdatedTaskId() {
		return updatedTaskId;
	}
}
