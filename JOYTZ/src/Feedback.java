import java.util.ArrayList;

//package V1;

public class Feedback {
	
	// attributes
	boolean result;
	String messageShowToUser;
	String errorMessage;
	
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
	
	public void setErrorMessage(String message){
		this.errorMessage = message;
	}
	
	public void setTaskList(ArrayList<String> taskList){
		this.taskList = taskList;
	}
	
	/**
	 * Get methods
	 */
	
	public boolean getResult() {
		return this.result;
	}

	public String getMessageShowToUser() {
		return this.messageShowToUser;
	}
	
	public String getErrorMessage(){
		return this.getErrorMessage();
	}
	
	public ArrayList<String> getTaskList(){
		return this.taskList;
	}
}
