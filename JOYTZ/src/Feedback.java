import java.util.ArrayList;

//package V1;

public class Feedback {

	// attributes
	private boolean result;
	private String messageShowToUser;

	// ArrayList only for display method.
	private ArrayList<String> taskList = new ArrayList<String>();
	
	// boolean 

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
