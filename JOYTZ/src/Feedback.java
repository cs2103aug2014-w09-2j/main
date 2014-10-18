import java.util.ArrayList;

//package V1;

public class Feedback {

	// attributes
	boolean result;
	String messageShowToUser;

	// ArrayList only for display method.
	ArrayList<String> taskList = new ArrayList<String>();

	/**
	 * Constructor
	 */
	Feedback(boolean result) {
		this.result = result;
		this.messageShowToUser = "";
	}

	/**
	 * Set methods
	 */

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setMessageShowToUser(String message) {
		this.messageShowToUser = message;
	}

	public void setTaskList(ArrayList<String> taskList) {
		this.taskList = taskList;
	}

	/**
	 * Get methods
	 */

	public boolean getResult() {
		return result;
	}

	public String getMessageShowToUser() {
		return messageShowToUser;
	}

	public ArrayList<String> getTaskList() {
		return taskList;
	}
}
