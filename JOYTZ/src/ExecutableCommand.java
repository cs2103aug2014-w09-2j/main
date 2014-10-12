//package V1;

import java.util.Date;

public class ExecutableCommand {
	
	// attributes in this object.
	private String action;
	private String taskName;
	private String taskDescription;
	private Date taskDeadline;
	private String taskLocation;
	
	// this is for delete method.
	private int taskId;
	
	// currently not in use.
	private String taskPriority;
	
	
	// errorMessage is used for Analyzer to report error to controller.
	private String errorMessage;
	
	// these are for update method.
	private String updateIndicator;
	private String updatedTaskName;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = "";
		this.taskName = "";
		this.taskDescription = "";
		this.taskDeadline = new Date(0, 0, 0);
		this.taskLocation = "";
		this.taskPriority = "";
		this.taskId = -1;
		this.errorMessage = "";
		this.updateIndicator = "";
		this.updatedTaskName = "";
	}

	ExecutableCommand(String action) {
		this.action = action;
		this.taskName = "";
		this.taskDescription = "";
		this.taskDeadline = new Date(0, 0, 0);
		this.taskLocation = "";
		this.taskPriority = "";
		this.taskId = -1;
		this.errorMessage = "";
		this.updateIndicator = "";
		this.updatedTaskName = "";
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

	public void setUpdateIndicator(String indicator) {
		this.updateIndicator = indicator;
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

	public Date getTaskDeadline() {
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

	public String getUpdateIndicator() {
		return updateIndicator;
	}

	public String getUpdatedTaskName() {
		return updatedTaskName;
	}
	
	public boolean contains(String data){
		boolean result = false;
		
		switch(data){
		case "taskName":
			if(getTaskName() != ""){
				result = true;
			}
		case "taskDescription":
			if(getTaskDescription() != ""){
				result = true;
			}
		case "taskDeadline":
			Date check = new Date(0, 0, 0);
			if(getTaskDeadline().equals(check)){
				result = true;
			}
		case "taskLocation":
			if(getTaskLocation() != ""){
				result = true;
			}
		case "taskPriority":
			if(getTaskPriority() != ""){
				result = true;
			}
		case "taskId":
			if(getTaskId() != -1){
				result = true;
			}
		default:
			result = false;
		}
		
		return result;
	}
}
