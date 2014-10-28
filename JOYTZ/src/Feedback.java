import java.util.ArrayList;

//package V1;

public class Feedback {

	// attributes
	private boolean result;
	private String messageShowToUser;

	// ArrayList only for display method.
	private ArrayList<String> taskList = new ArrayList<String>();
	
	// boolean arrays for task pass the date
	private boolean[] passStartTime = {};
	private boolean[] passEndTime = {};

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
	
	public void setPassStartTimeList(boolean[] list){
		this.passStartTime = list;
	}
	
	public void setPassEndTimeList (boolean[] list){
		this.passEndTime = list;
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
	
	public boolean[] getPassStartTimeList (){
		return passStartTime;
	}
	
	public boolean[] getPassEndTimeList() {
		return passEndTime;
	}

}
