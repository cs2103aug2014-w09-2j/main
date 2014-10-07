//package V1;

import java.util.Date;


public class Executor {
	
	
	private static final String MESSAGE_ADD_SUCCESSFUL_WITH_DATE = "\"%s\" due on %s is added\n";
	private static final String MESSAGE_ADD_SUCCESSFUL_WITHOUT_DATE = "\"%s\" is added\n";
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "all content deleted successfully\n";
	private static final String MESSAGE_DELETE_SUCCESSFUL = "\"%s\" is deleted\n";
	/*private static final String MESSAGE_EMPTY = "all content is deleted from %s\n";
	private static final String MESSAGE_SORT = "all the content in %s is sorted.\n";
	private static final String MESSAGE_SEARCH_RESULT = "%d. %s\n";
	private static final String MESSAGE_NO_SEARCH_RESULT = "Searched task not found.\n";*/
	private static final String ERROR_INVALID_INDEX = "Invalid item index, please try again.\n";
	private static final String ERROR_NO_TASK_TO_BE_ADDED = "No task is found to be added, please try again\n";

	public static Feedback feedbackObject;
	
	public static Feedback proceedAnalyzedCommand(ExecutableCommand command) {
		String action = command.getAction();
		
		switch(action){
			case "add":
				performAddAction(command);
				break;
			case "delete":
				performDeleteAction(command);
				break;
			case "display": 
				performDisplayAction();
				break;
			case "clear": 
				performClearAction();
				break;
			case "sort":
				performSortAction();
				break;
			case "search":
				performSearchAction();
				break;
			case "exit": 
				performExitAction();
				break;
			default:
				Feedback feedBackObject= new Feedback(false);
				feedBackObject.setMessageShowToUser("Invalid Command");
		}
		
		return feedbackObject;
	}

	private static void performAddAction(ExecutableCommand command) {
		String description = command.getDescription();
		String location = command.getLocation();
		Date date = command.getDate();
		feedbackObject = new Feedback(false);
		
		if(description == null){
			feedbackObject.setMessageShowToUser(ERROR_NO_TASK_TO_BE_ADDED);
		}
		else{
			Task t = new Task(description, date, location);
		
			feedbackObject = Storage.addTask(t);
			if (feedbackObject.getResult()){
				if(date == null){
					feedbackObject.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL_WITHOUT_DATE, description));
				}
				else{
					feedbackObject.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL_WITH_DATE, description, date));
				}
			}
		}
	}

	private static void performDeleteAction(ExecutableCommand command) {
		int itemId = command.getItemId();
		feedbackObject = new Feedback(false);
		
		if(itemId <= 0){
			feedbackObject.setMessageShowToUser(ERROR_INVALID_INDEX);
		}
		else{
			feedbackObject = Storage.deleteTask(itemId);
			
			if(feedbackObject.getResult()){
				feedbackObject.setMessageShowToUser(String.format(MESSAGE_DELETE_SUCCESSFUL, feedbackObject.getDescription()));
			}
		}
	}

	private static void performDisplayAction() {
		feedbackObject = Storage.displayTask();
	}

	private static void performClearAction() {
		feedbackObject = Storage.clear();
		if (feedbackObject.getResult()){
			feedbackObject.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFUL);
		}
	}
	
	private static void performSortAction() {
		// TODO Auto-generated method stubs
	}

	private static void performSearchAction() {
		// TODO Auto-generated method stub
	}

	private static void performExitAction() {
		feedbackObject = new Feedback(true, "exit");
		
		System.exit(0);		
	}
	
/*	public static void performCheckStatus(){
		feedbackObject = Storage.checkStatus();
	}*/

	public static Feedback getFeedback() {
		return feedbackObject;
	}
}
