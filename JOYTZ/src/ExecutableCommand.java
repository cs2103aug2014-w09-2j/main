//package V1;

public class ExecutableCommand {

	// attributes in this object.
	private String action;

	// Task Class attributes for (add and update).
	private String taskName;
	private String taskDescription;
	private String taskStartDate;
	private String taskStartTime;
	private String taskEndDate;
	private String taskEndTime;
	private String taskLocation;
	private String taskPriority;

	// this is for identification of task.
	private int taskId;

	// errorMessage is used for Analyzer to report error to controller.
	private String errorMessage;

	// these are for update, sort or search method.
	private String indicator;
	private String key;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = "";

		this.taskName = "";
		this.taskDescription = "";
		this.taskStartDate = "";
		this.taskStartTime = "";
		this.taskEndDate = "";
		this.taskEndTime = "";
		this.taskLocation = "";
		this.taskPriority = "";

		this.taskId = -1;
		this.errorMessage = "";

		this.indicator = "";
		this.key = "";
	}

	ExecutableCommand(String action) {
		this.action = action;

		this.taskName = "";
		this.taskDescription = "";
		this.taskStartDate = "";
		this.taskStartTime = "";
		this.taskEndDate = "";
		this.taskEndTime = "";
		this.taskLocation = "";
		this.taskPriority = "";

		this.taskId = -1;
		this.errorMessage = "";

		this.indicator = "";
		this.key = "";
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

	public void setTaskStartDate(String date) {
		this.taskStartDate = date;
	}

	public void setTaskStartTime(String time) {
		this.taskStartTime = time;
	}

	public void setTaskEndDate(String date) {
		this.taskEndDate = date;
	}

	public void setTaskEndTime(String time) {
		this.taskEndTime = time;
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

	public void setKey(String key) {
		this.key = key;
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
		return taskStartDate;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public String getTaskEndDate() {
		return taskEndDate;
	}

	public String getTaskEndTime() {
		return taskEndTime;
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

	public String getKey() {
		return key;
	}

}
