//@author A0112162Y
import java.util.ArrayList;

public class ExecutableCommand {
	
	// attributes in this object.
	private String action;

	// Task Class attributes for (add and update).
	private String taskName;
	private String taskDescription;
	private String taskStart;
	private String taskEnd;
	private String taskLocation;
	private String taskPriority;

	// this is for identification of task.
	private ArrayList<Integer> taskId;

	// errorMessage is used for Analyzer to report error to controller.
	private String errorMessage;

	// these are for display, update, sort or search method.
	private ArrayList<String> indicator;
	private ArrayList<String> key;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.taskId = new ArrayList<Integer>();
		this.indicator = new ArrayList<String>();
		this.key = new ArrayList<String>();
		
		this.action = "";

		this.taskName = "";
		this.taskDescription = "";
		this.taskStart = "";
		this.taskEnd = "";
		this.taskLocation = "";
		this.taskPriority = "";

		this.errorMessage = "";
	}

	ExecutableCommand(String action) {
		this.taskId = new ArrayList<Integer>();
		this.indicator = new ArrayList<String>();
		this.key = new ArrayList<String>();
		
		this.action = action;

		this.taskName = "";
		this.taskDescription = "";
		this.taskStart = "";
		this.taskEnd = "";
		this.taskLocation = "";
		this.taskPriority = "";

		this.errorMessage = "";
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

	public void setTaskStart(String timing) {
		this.taskStart = timing;
	}

	public void setTaskEnd(String timing) {
		this.taskEnd = timing;
	}

	public void setTaskLocation(String location) {
		this.taskLocation = location;
	}

	public void setTaskPriority(String priority) {
		this.taskPriority = priority;
	}

	public void setTaskId(int id) {
		this.taskId.add(id);
	}

	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}

	public void setIndicator(String indicator) {
		this.indicator.add(indicator);
	}

	public void setKey(String key) {
		this.key.add(key);
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

	public String getTaskStart() {
		return taskStart;
	}

	public String getTaskEnd() {
		return taskEnd;
	}

	public String getTaskLocation() {
		return taskLocation;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public ArrayList<Integer> getTaskId() {
		return taskId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public ArrayList<String> getIndicator() {
		return indicator;
	}

	public ArrayList<String> getKey() {
		return key;
	}

}
