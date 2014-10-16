//package V1;

import java.util.ArrayList;
import java.util.Date;

public class Executor {

	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";

	// these are for Add Method.
	private static final String MESSAGE_ADD_SUCCESSFUL = "\"%s\" is added successfully.\n";
	private static final String ERROR_NULL_OBJECT = "Null object.\n";

	// these are for Delete Method.
	private static final String MESSAGE_DELETE_SUCCESSFUL = "%d. \"%s\" is deleted successfully.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";

	// these are for Clear Method.
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "All tasks are cleared successfully.\n";

	// these are for Update Method.
	private static final String ERROR_INVALID_INDICATOR = "The indicator is invalid.\n";
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d, \"%s\"is updated successfully.\n";

	// these are for Sort Method
	private static final String MESSAGE_SORT_SUCCESSFUL = "Category \"%s\" is sorted successfully.\n";
	private static final String ERROR_FAIL_TO_SORT = "Nothing to sort.\n";

	// these are for Search Method
	private static final String MESSAGE_SEARCH_SUCCESSFUL = "\"%s\" in \"%s\" is searched successfully.\n";

	// these are for Save and Reload.
	private static final String ERROR_FAIL_SAVE_TO_FILE = "Fail to save the Storage to file\n";
	private static final String MESSAGE_SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";

	public static Feedback feedback;

	/**
	 * Called by Controller to initialize Executor.
	 *
	 * @param command: ExecutableCommand object containing the user's action
	 */
	public static Feedback proceedAnalyzedCommand(ExecutableCommand command) {
		if (command == null) {
			throw new NullPointerException(ERROR_NULL_OBJECT);
		}

		String action = command.getAction();

		switch (action) {
		case "add":
			performAddAction(command);
			break;
		case "delete":
			performDeleteAction(command);
			break;
		case "update":
			performUpdateAction(command);
			break;
		case "clear":
			performClearAction();
			break;
		case "sort":
			performSortAction(command);
			break;
		case "search":
			performSearchAction(command);
			break;
		case "undo":
			performUndoAction();
			break;
		case "exit":
			performExitAction();
			break;
		default:
			Feedback feedbackObject = new Feedback(false);
			feedbackObject.setErrorMessage(ERROR_INVALID_COMMAND);
			return feedbackObject;
		}

		if (feedback.getResult()) {
			feedback.setTaskList(Storage.getTaskList());
		}

		return feedback;
	}

	/**
	 * Perform add action with command object passed from 
	 * proceedAnalyzedCommand method
	 *
	 * @param command: ExecutableCommand object containing the user's action
	 * 
	 */
	private static void performAddAction(ExecutableCommand command) {
		String name = command.getTaskName();
		String description = command.getTaskDescription();
		Long startTime = Long.parseLong(command.getTaskStartDate());
		Long endTime = Long.parseLong(command.getTaskEndDate());
		String location = command.getTaskLocation();
		String priority = command.getTaskPriority();
		feedback = new Feedback(false);

		// pre-condition
		assert !name.equals("") : "No task name";

		// create a task object with all the attributes.
		Task t = new Task(name, startTime, endTime, description, location, priority);

		// add the task into the storage.
		try {
			feedback.setResult(Storage.add(t));
		} catch (Exception e) {
			feedback.setErrorMessage(e.getMessage());
		}
		
		if (feedback.getResult()){
		feedback.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL,
				name));
		}
		
		return;
	}
	
	/**
	 * Perform delete action with command object passed from 
	 * proceedAnalyzedCommand method
	 *
	 * @param command: ExecutableCommand object containing the user's action
	 * 
	 */
	private static void performDeleteAction(ExecutableCommand command) {
		int taskId = command.getTaskId();
		String taskName;
		feedback = new Feedback(false);

		// pre-condition
		assert taskId != -1 : "Task index " + taskId;

		try {
			taskName = Storage.get(taskId).getTaskName();
			feedback.setResult(Storage.delete(taskId));
			
			if (feedback.getResult()) {
				feedback.setMessageShowToUser(String.format(
						MESSAGE_DELETE_SUCCESSFUL, taskId, taskName));
			}
			else{
				feedback.setErrorMessage(ERROR_INVALID_TASK_INDEX);
			}
		} catch (Exception e) {
			feedback.setErrorMessage(e.getMessage());
		}
		
		return;
	}

	/**
	 * Perform update action with command object passed from 
	 * proceedAnalyzedCommand method
	 *
	 * @param command: ExecutableCommand object containing the user's action
	 * 
	 */
	private static void performUpdateAction(ExecutableCommand command) {
		int taskId = command.getTaskId();
		String updateIndicator = command.getIndicator();
		String updateKeyValue = command.getKeyValue();
		
		// currently not in use.
		String taskName;
		
		feedback = new Feedback(false);
		
		// check updateIndicator
		//##################### your code.
		
		/*
		// check whether the task is out of range, catch the exception, and end
		// the function.
		switch (updateIndicator) {
		case "name":
			newInfo = command.getTaskName();
			break;
		case "description":
			newInfo = command.getTaskDescription();
			break;
		case "deadline":
			String newDate = command.getTaskDeadline().getTime() + "";
			newInfo = newDate;
			break;
		case "location":
			newInfo = command.getTaskLocation();
			break;
		case "priority":
			newInfo = command.getTaskPriority();
			break;
		default:
			feedback.setErrorMessage(ERROR_INVALID_INDICATOR);
			return;
		}
		*/

		try {
			feedback.setResult(Storage.update(taskId, updateIndicator, updateKeyValue));
			if (feedback.getResult()) {
				taskName = Storage.get(taskId).getTaskName();
				feedback.setMessageShowToUser(String.format(
						MESSAGE_UPDATE_SUCCESSFUL, taskId, taskName));
			}
		} catch (Exception e) {
			feedback.setErrorMessage(e.getMessage());
		}
		return;
	}

	/**
	 * Perform clear action with command object passed from 
	 * proceedAnalyzedCommand method
	 * 
	 */
	private static void performClearAction() {
		feedback = new Feedback(false);

		feedback.setResult(Storage.clean());
		feedback.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFUL);

		// post-condition
		assert feedback.getResult() : "Nothing to clear";
		
		return;
	}

	/**
	 * Perform sort action wit command object passed from 
	 * proceedAnalyzedCommand method
	 *
	 * @param command: ExecutableCommand object containing the user's action
	 * 
	 */
	private static void performSortAction(ExecutableCommand command) {
		String sortKey = command.getIndicator();
		feedback = new Feedback(false);

		// pre-condition
		assert !sortKey.equals(""): "Sort no category";
		
		// check what category user want to sort
		try {
			feedback.setResult(Storage.sort(sortKey));
		} catch (Exception e) {
			feedback.setErrorMessage(e.getMessage());
		}
		
		if (feedback.getResult()) {
			feedback.setMessageShowToUser(String.format(MESSAGE_SORT_SUCCESSFUL, sortKey));
		}
		
		return;
	}

	/**
	 * Perform search action with command object passed from 
	 * proceedAnalyzedCommand method
	 *
	 * @param command: ExecutableCommand object containing the user's action
	 * 
	 */
	private static void performSearchAction(ExecutableCommand command) {
		String searchIndicator = command.getIndicator();
		String searchValue = command.getKeyValue();
		ArrayList<String> resultList = new ArrayList<String>();
		
		feedback = new Feedback(false);
		
		// check whether Storage can search the result or not
		try {
			resultList = Storage.search(searchIndicator, searchValue);
			feedback.setTaskList(resultList);
		} catch (Exception e) {
			feedback.setErrorMessage(e.getMessage());
			return;
		}
		
		// post-condition
		assert !searchValue.equals(null): "No given search value";
		assert !resultList.equals(null): "No result is found";

		feedback.setResult(true);
		feedback.setMessageShowToUser(MESSAGE_SEARCH_SUCCESSFUL);
		
		return;
	}

	/**
	 * Perform undo action with command object passed from 
	 * proceedAnalyzedCommand method
	 * 
	 */
	private static void performUndoAction() {

	}

	/**
	 * Perform exit action with command object passed from 
	 * proceedAnalyzedCommand method
	 * 
	 */
	private static void performExitAction() {
		feedback = new Feedback(false);

		// check whether the storage can store the information into a file.
		try {
			Storage.saveFile();
		} catch (Exception e) {
			feedback.setErrorMessage(String.format(ERROR_FAIL_SAVE_TO_FILE));
			e.printStackTrace();
			return;
		}

		feedback.setResult(true);
		feedback.setMessageShowToUser(String.format(MESSAGE_SAVE_SUCCESSFUL));
		System.exit(0);
	}

	/**
	 * Return feedback to user
	 * 
	 */
	public static Feedback getFeedback() {
		return feedback;
	}
}
