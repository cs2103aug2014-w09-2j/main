//package V1;

import java.util.*;

public class Executor {

	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";
	private static final String ERROR_INVALID_COMMAND_ACTION = "Invalid command action: %s.\n";

	// these are for Add Method.
	private static final String MESSAGE_ADD_SUCCESSFUL = "%s is added successfully.\n";
	// private static final String ERROR_NULL_OBJECT = "Null object.\n";

	// these are for Delete Method.
	private static final String MESSAGE_DELETE_SUCCESSFUL = "%d. \"%s\" is deleted successfully.\n";
	// private static final String ERROR_INVALID_TASK_INDEX =
	// "Task index indicated is invalid.\n";

	// these are for Clear Method.
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "All tasks are cleared successfully.\n";

	// these are for Update Method.
	// private static final String ERROR_INVALID_INDICATOR =
	// "The indicator is invalid.\n";
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d, \"%s\"is updated successfully.\n";

	// these are for Sort Method
	private static final String MESSAGE_SORT_SUCCESSFUL = "Category \"%s\" is sorted successfully.\n";
	// private static final String ERROR_FAIL_TO_SORT = "Nothing to sort.\n";

	// these are for Search Method
	private static final String MESSAGE_SEARCH_SUCCESSFUL = "\"%s\" in \"%s\" is searched successfully.\n";

	// these are for Save and Reload.
	private static final String ERROR_FAIL_SAVE_TO_FILE = "Fail to save the Storage to file\n";
	private static final String MESSAGE_SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";

	public static Feedback feedback;

	/**
	 * Called by Controller to initialize Executor.
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 * 
	 */
	public static Feedback proceedAnalyzedCommand(ExecutableCommand command) {
		feedback = new Feedback(false);

		if (command == null) {
			feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
			return feedback;
		}

		switch (command.getAction()) {
		case "add":
			feedback = performAddAction(command);
			break;
		case "delete":
			feedback = performDeleteAction(command);
			break;
		case "update":
			feedback = performUpdateAction(command);
			break;
		case "clear":
			feedback = performClearAction();
			break;
		case "sort":
			feedback = performSortAction(command);
			break;
		case "search":
			feedback = performSearchAction(command);
			break;
		case "undo":
			performUndoAction();
			break;
		case "exit":
			feedback = performExitAction();
			break;
		default:
			feedback.setMessageShowToUser(String.format(
					ERROR_INVALID_COMMAND_ACTION, command.getAction()));
			return feedback;
		}

		if (feedback.getResult()) {
			feedback.setTaskList(Storage.getTaskList());
		}

		return feedback;
	}

	/**
	 * Perform add action with command object passed from proceedAnalyzedCommand
	 * method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	private static Feedback performAddAction(ExecutableCommand command) {
		String name = command.getTaskName();
		String description = command.getTaskDescription();
		String location = command.getTaskLocation();
		String priority = command.getTaskPriority();
		String startTiming = command.getTaskStartTiming();
		String endTiming = command.getTaskEndTiming();
		Long startTime = (long) 0;
		Long endTime = (long) 0;

		Feedback fb = new Feedback(false);
		
		if (isLongType(startTiming) && isLongType(endTiming)) {
			startTime = Long.valueOf(startTiming);
			endTime = Long.valueOf(endTiming);
		}
		if(isLongType(startTiming)){
			startTime = Long.valueOf(startTiming);
		}
		if(isLongType(endTiming)){
			endTime = Long.valueOf(endTiming);
		}
		
		
		// create a task object with all the attributes.
		Task t = new Task(name, startTime, endTime, description, location,
				priority);

		// pre-condition
		//assert !name.equals("") : "No task name";
		//assert !t.equals(new Task()) : "No task created";

		// add the task into the storage.
		try {
			fb.setResult(Storage.add(t));
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		// post-condition
		//assert fb.getResult() : "Fail to add tasks";

		if (fb.getResult()) {
			fb.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL, name));
		}

		return fb;
	}

	/**
	 * Perform delete action with command object passed from
	 * proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	private static Feedback performDeleteAction(ExecutableCommand command) {
		Feedback fb = new Feedback(false);

		int taskId = command.getTaskId();
		String taskName;

		// pre-condition
		assert taskId != -1 : "Task index " + taskId;

		try {
			taskName = Storage.get(taskId).getTaskName();
			fb.setResult(Storage.delete(taskId));
			if (fb.getResult()) {
				fb.setMessageShowToUser(String.format(
						MESSAGE_DELETE_SUCCESSFUL, taskId, taskName));
			}
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		return fb;
	}

	/**
	 * Perform update action with command object passed from
	 * proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	private static Feedback performUpdateAction(ExecutableCommand command) {
		Feedback fb = new Feedback(false);

		int taskId = command.getTaskId();
		String updateIndicator = command.getIndicator();
		String updateKeyValue = command.getKey();
		String taskName;

		// pre-condition
		assert !updateIndicator.equals("") : "No update indicator";
		assert !updateKeyValue.equals("") : "No update key";

		try {
			fb.setResult(Storage
					.update(taskId, updateIndicator, updateKeyValue));
			if (fb.getResult()) {
				taskName = Storage.get(taskId).getTaskName();
				fb.setMessageShowToUser(String.format(
						MESSAGE_UPDATE_SUCCESSFUL, taskId, taskName));
			}
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		return fb;
	}

	/**
	 * Perform clear action with command object passed from
	 * proceedAnalyzedCommand method
	 * 
	 * @return
	 */
	private static Feedback performClearAction() {
		Feedback fb = new Feedback(false);

		fb.setResult(Storage.clean());
		if (fb.getResult()) {
			fb.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFUL);
		}

		return fb;
	}

	/**
	 * Perform sort action with command object passed from
	 * proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	private static Feedback performSortAction(ExecutableCommand command) {
		String sortKey = command.getIndicator();

		Feedback fb = new Feedback(false);

		// pre-condition
		// assert !sortKey.equals("") : "Sort no category";

		// check what category user want to sort
		try {
			fb.setResult(Storage.sort(sortKey));
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
			return fb;
		}

		if (fb.getResult()) {
			fb.setMessageShowToUser(String.format(MESSAGE_SORT_SUCCESSFUL,
					sortKey));
		}

		return fb;
	}

	/**
	 * Perform search action with command object passed from
	 * proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	private static Feedback performSearchAction(ExecutableCommand command) {
		String searchIndicator = command.getIndicator();
		String searchValue = command.getKey();
		ArrayList<String> resultList = new ArrayList<String>();

		Feedback fb = new Feedback(false);

		// pre-condition
		// assert !searchIndicator.equals("") : "No search indicator";
		// assert !searchValue.equals("") : "No search value";

		// check whether Storage can search the result or not
		try {
			resultList = Storage.search(searchIndicator, searchValue);
			fb.setTaskList(resultList);
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		// post-condition
		// assert !searchValue.equals("") : "No given search value";
		// assert !resultList.equals(null) : "No result is found";

		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_SEARCH_SUCCESSFUL);

		return fb;
	}

	/**
	 * Perform undo action with command object passed from
	 * proceedAnalyzedCommand method
	 * 
	 */
	private static void performUndoAction() {
		return;
	}

	/**
	 * Perform exit action with command object passed from
	 * proceedAnalyzedCommand method
	 * 
	 */
	private static Feedback performExitAction() {
		Feedback fb = new Feedback(false);

		// check whether the storage can store the information into a file.
		try {
			Storage.saveFile();
		} catch (Exception e) {
			fb.setMessageShowToUser(String.format(ERROR_FAIL_SAVE_TO_FILE));
			return fb;
		}

		fb.setResult(true);
		fb.setMessageShowToUser(String.format(MESSAGE_SAVE_SUCCESSFUL));

		return fb;
	}

	/**
	 * Return feedback to user
	 * 
	 */
	public static Feedback getFeedback() {
		return feedback;
	}

	private static boolean isLongType(String s) {
		try {
			Long.valueOf(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
