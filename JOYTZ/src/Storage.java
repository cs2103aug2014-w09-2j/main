package V1;

import java.util.ArrayList;


public class Storage {
	static ArrayList<Task> listOfTask = new ArrayList<Task>();
	static ArrayList<Task> history = new ArrayList<Task>();
	static int numberOfTask;
	
	public static final String MESSAGE_NullTask = "The teskObject is null.";
	public static final String MESSAGE_ItemIdOutOfRange = "The ID '%d' is out of range.";
	public static final String MESSAGE_DisplayTask = "%d. %s\n";
	public static final String MESSAGE_DisplayTask_Empty = "There is no task currently.\n";
	/**
	 * Methods
	 */
	
	public static Feedback addTask(Task t){
		Feedback feedbackObject = new Feedback(false);
		if(t == null){
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_NullTask));
			return feedbackObject;
		}
		
		feedbackObject.setResult(true);
		listOfTask.add(t);
		numberOfTask++;
		return feedbackObject;
	}
	
	public static Feedback deleteTask(int ItemId){

		Feedback feedbackObject = new Feedback(false);
		
		if (numberOfTask < ItemId || ItemId <= 0){
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_ItemIdOutOfRange, ItemId));
			return feedbackObject;
		}
		
		feedbackObject.setResult(true);
		listOfTask.remove(ItemId);
		return feedbackObject;
	}
	
	public static Feedback displayTask(){
		Feedback feedbackObject = new Feedback(true);
		
		if (listOfTask.size() == 0){
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_DisplayTask_Empty));
		}
	}
	
	
}
