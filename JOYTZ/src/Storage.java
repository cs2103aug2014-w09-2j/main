//package V1;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;


public class Storage {
	static ArrayList<Task> listOfTask = new ArrayList<Task>();
	static ArrayList<Task> history = new ArrayList<Task>();
	static int numberOfTask;
	static Timer timer = new Timer();
	
	public static final String MESSAGE_NullTask = "The teskObject is null.\n";
	public static final String MESSAGE_ItemIdOutOfRange = "The ID '%d' is out of range.\n";
	public static final String MESSAGE_DisplayTask = "%d. %s\n";
	public static final String MESSAGE_DisplayTask_Empty = "There is no task currently.\n";
	public static final String MESSAGE_ExpiredTask = "%s is expired\n";
	public static final String MESSAGE_TaskIsExpired = "%s is expired.\n";
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
		timer.schedule(t, t.getTime());
		numberOfTask++;
		return feedbackObject;
	}
	
	public static Feedback deleteTask(int ItemId){

		Feedback feedbackObject = new Feedback(false);
		
		if (numberOfTask < ItemId || ItemId < 0){
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_ItemIdOutOfRange, ItemId));
			return feedbackObject;
		}
		
		feedbackObject.setResult(true);
		Task removedTask = listOfTask.remove(ItemId);
		removedTask.cancel();
		history.add(removedTask);
		return feedbackObject;
	}
	
	public static Feedback displayTask(){
		Feedback feedbackObject = new Feedback(true);
		
		if (listOfTask.size() == 0){
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_DisplayTask_Empty));
		}else {
			for (int index=0; index<listOfTask.size(); index++){
				feedbackObject.setMessageShowToUser(String.format(MESSAGE_DisplayTask, index+1, listOfTask.get(index).getDescription().toString()));
			}
		}
		return feedbackObject;
	}
	
	public static Feedback clear(){
		Feedback feedbackObject = new Feedback(false);
		
		for (int ItemId=0; ItemId<listOfTask.size(); ItemId++){
			Feedback currentFeedbackObject = deleteTask(ItemId);
			if (! currentFeedbackObject.getResult()){
				feedbackObject.setMessageShowToUser("check storage error... should not be displayed.");
				return feedbackObject;
			}
		}
		feedbackObject.setResult(true);
		return feedbackObject;
	}
	
	public static Feedback checkStatus(){
		Feedback feedbackObject = new Feedback(true);
		
		Date now = new Date();
		for (int ItemId=0; ItemId<listOfTask.size(); ItemId++){
			if (listOfTask.get(ItemId).getTime().before(now)){
				Task removedTask = listOfTask.remove(ItemId);
				removedTask.setStatusToBeExpired();
				history.add(removedTask);
				feedbackObject.setMessageShowToUser(String.format(MESSAGE_TaskIsExpired, removedTask.toString()));
			}
		}
		return feedbackObject;
	}
	
	
}
