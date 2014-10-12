import java.util.ArrayList;

//package V1;

public class Feedback {
	
	// attributes
	boolean result;
	String messageShowToUser;
	
	// ArrayList only for display method.
	ArrayList<String> dispalyList = new ArrayList<String>();
	
	// currently not in use.
	String actionExecuted;
	String description;

	/**
	 * Constructor
	 */
	Feedback(boolean result) {
		this.result = result;
		this.actionExecuted = "";
		this.description = "";
		this.messageShowToUser = "";
	}

	Feedback(boolean result, String actionExecuted) {
		this.result = result;
		this.actionExecuted = actionExecuted;
		this.description = "";
		this.messageShowToUser = "";
	}

	/**
	 * methods
	 */

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMessageShowToUser(String message) {
		this.messageShowToUser += message;
	}

	public boolean getResult() {
		return this.result;
	}

	public String getAction() {
		return this.actionExecuted;
	}

	public String getDescription() {
		return this.description;
	}

	public String getMessageShowToUser() {
		return this.messageShowToUser;
	}
}
