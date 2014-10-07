package V1;

import java.util.Date;


public class Executor {
	
	
	private static final String MESSAGE_ADD = "%s is added\n";
	private static final String MESSAGE_CLEAR = "all content deleted from %s\n";
	private static final String MESSAGE_DELETE = "No. %d task is deleted\n";
	private static final String MESSAGE_DISPLAY = "Here is your time table:\n";
	private static final String MESSAGE_EMPTY = "all content is deleted from %s\n";
	private static final String MESSAGE_SORT = "all the content in %s is sorted.\n";
	private static final String MESSAGE_SEARCH_RESULT = "%d. %s\n";
	private static final String MESSAGE_NO_SEARCH_RESULT = "There is not result.";
	
	
	public static Feedback feedbackObject;
	
	public static void proceedAnalyzedCommand(ExecutableCommand command) {
		
		String action = command.getAction();
		
		switch(action){
			case "add":
				String name = command.getTaskName();
				String description = command.getDescription();
				String location = command.getLocation();
				Date date = command.getDate();
				performAddAction(name, date, description, location);
				break;
			case "delete":
				int ItemId = command.getItemId();
				performDeleteAction(ItemId);
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
	}

	private static void performAddAction(String name, Date date, String des, String loc) {
		Task t = new Task(name, date, des, loc);
		feedbackObject = Storage.addTask(t);
		if (feedbackObject.getResult()){
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_ADD, name));
		}
		
	}


	private static void performDeleteAction(int ItemId) {
		feedbackObject = Storage.deleteTask(ItemId);
		if (feedbackObject.getResult()){
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_DELETE, ItemId));
		}
	}

	private static void performDisplayAction() {
		feedbackObject = Storage.displayTask();
	}

	private static void performClearAction() {
		feedbackObject = Storage.clear();
		if (feedbackObject.getResult()){
			feedbackObject.setMessageShowToUser(MESSAGE_CLEAR);
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
	
	public static void performCheckStatus(){
		feedbackObject = Storage.checkStatus();
	}

	public static Feedback getFeedback() {
		return feedbackObject;
	}
}
