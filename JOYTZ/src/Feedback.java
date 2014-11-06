//@author A0119378U
import java.util.ArrayList;



public class Feedback {
	
	// action that trigger the feedback
	private String action;

	// attributes
	private boolean result;
	private String messageShowToUser;

	// ArrayList only for display method.
	private ArrayList<String> taskStringListShowToUser = new ArrayList<String>();
	
	// boolean arrays for task pass the date
	private boolean[] passStartTimeTaskIndexIndicator = {};
	private boolean[] passEndTimeTaskIndexIndicator = {};

	/**
	 * Constructor
	 */
	
	Feedback(boolean result) {
		this.action = "";
		this.result = result;
		this.messageShowToUser = "";
	}
	Feedback(String action, boolean result){
		this.action = action;
		this.result = result;
		this.messageShowToUser = "";
	}

	/**
	 * Set methods
	 */
	
	public void setAction(String action){
		this.action = action;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setMessageShowToUser(String message) {
		this.messageShowToUser = message;
	}

	public void setTaskStringList(ArrayList<String> taskList) {
		this.taskStringListShowToUser = taskList;
	}
	
	public void setPassStartTimeIndicator(boolean[] indicatorArray){
		this.passStartTimeTaskIndexIndicator = indicatorArray;
	}
	
	public void setPassEndTimeIndicator (boolean[] indicatorArray){
		this.passEndTimeTaskIndexIndicator = indicatorArray;
	}

	/**
	 * Get methods
	 */
	
	public String getAction(){
		return action;
	}

	public boolean getResult() {
		return result;
	}

	public String getMessageShowToUser() {
		return messageShowToUser;
	}

	public ArrayList<String> getTaskStringList() {
		return taskStringListShowToUser;
	}
	
	public boolean[] getPassStartTimeIndicator (){
		return passStartTimeTaskIndexIndicator;
	}
	
	public boolean[] getPassEndTimeListIndicator() {
		return passEndTimeTaskIndexIndicator;
	}

}
