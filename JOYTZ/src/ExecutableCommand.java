//package V1;

public class ExecutableCommand {

	// attributes in this object.
	private String action;
	
	// Task Class attributes for (add and update).
	private String taskName;
	private String taskDescription;
	private String taskStartTiming;
	private String taskEndTiming;
	private String taskLocation;
	private String taskPriority;
	
	// this is for identification of task.
	private int taskId;

	// errorMessage is used for Analyzer to report error to controller.
	private String errorMessage;

	// these are for update, sort or search method.
	private String indicator;
	private String KeyValue;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = "";
		
		this.taskName = "";
		this.taskDescription = "";
		this.taskStartTiming = "";
		this.taskEndTiming = "";
		this.taskLocation = "";
		this.taskPriority = "";
		
		this.taskId = -1;
		this.errorMessage = "";
		
		this.indicator = "";
		this.KeyValue = "";
	}

	ExecutableCommand(String action) {
		this.action = action;
		
		this.taskName = "";
		this.taskDescription = "";
		this.taskStartTiming = "";
		this.taskEndTiming = "";
		this.taskLocation = "";
		this.taskPriority = "";
		
		this.taskId = -1;
		this.errorMessage = "";
		
		this.indicator = "";
		this.KeyValue = "";
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

	public void setTaskStartTiming(String date) {
		this.taskStartTiming = date;
	}

	public void setTaskEndTiming(String date) {
		this.taskEndTiming = date;
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

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	
	public void setKeyValue(String key){
		this.KeyValue = key;
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

	public String getTaskStartDate() {
		return taskStartTiming;
	}

	public String getTaskEndDate() {
		return taskEndTiming;
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

	public String getIndicator() {
		return indicator;
	}
	
	public String getKeyValue(){
		return KeyValue;
	}
	

}
