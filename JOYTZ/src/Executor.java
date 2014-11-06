//package V1;

import java.util.*;

public class Executor {

	private static Stack<ExecutableCommand> commandStack = new Stack<ExecutableCommand>();
	private static Stack<ExecutableCommand> redoStack = new Stack<ExecutableCommand>();

	private static final String ERROR_NULL_COMMAND = "Null command.\n";
	private static final String ERROR_INVALID_COMMAND_ACTION = "Invalid command action: %s.\n";

	// these are for Add Method.
	private static final String MESSAGE_ADD_SUCCESSFUL = "%s is added successfully.\n";

	// these are for Delete Method.
	private static final String MESSAGE_DELETE_SUCCESSFUL = "%d. \"%s\" is deleted successfully.\n";

	// these are for Clear Method.
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "All tasks are cleared successfully.\n";

	// these are for Display method.
	private static final String MESSAGE_DISPLAY_SUCCESSFULLY = "Tasks are displayed successfully.\n";
	private static final String MESSAGE_EMPTY_DISPLAY = "The task list is empty.\n";

	// these are for Update Method.
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d: \"%s\" is updated successfully.\n";

	// these are for Sort Method
	private static final String MESSAGE_SORT_SUCCESSFUL = "Category \"%s\" is sorted successfully.\n";

	// these are for Search Method
	private static final String MESSAGE_SEARCH_SUCCESSFUL = "%s in %s is searched successfully.\n";

	// these are for Undo Method
	private static final String MESSAGE_UNDO_SUCCESSFULLY = "Undo one step successfully.\n";
	private static final String ERROR_NOTHING_TO_UNDO = "There is nothing to undo.\n";

	// these are for Redo Method
	private static final String ERROR_NOTHING_TO_REDO = "There is nothing to redo.\n";
	private static final String MESSAGE_REDO_SUCCESSFULLY = "Redo one step successfully.\n";

	// these are for Save and Reload.
	private static final String ERROR_FAIL_SAVE_TO_FILE = "Fail to save the Storage to file\n";
	private static final String MESSAGE_SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";
	private static final String MESSAGE_RELOAD_SUCCESSFULLY = "The Storage is reloaded successfully.\n";

	public static Feedback feedback;

	/**
	 * Called by Controller to initialize Executor.
	 *
	 * @param command
	 * @return
	 */

	public static Feedback proceedAnalyzedCommand(ExecutableCommand command) {
		feedback = new Feedback(false);

		if (command.equals(null)) {
			feedback.setMessageShowToUser(ERROR_NULL_COMMAND);
			return feedback;
		}

		switch (command.getAction()) {
		case StringFormat.ADD:
			feedback = performAddAction(command);
			break;

		case StringFormat.DELETE:
			feedback = performDeleteAction(command);
			break;

		case StringFormat.UPDATE:
			feedback = performUpdateAction(command);
			break;

		case StringFormat.CLEAR:
			feedback = performClearAction();
			break;

		case StringFormat.DISPLAY:
			feedback = performDisplayAction();
			break;

		case StringFormat.SORT:
			feedback = performSortAction(command);
			break;

		case StringFormat.SEARCH:
			feedback = performSearchAction(command);
			break;

		case StringFormat.UNDO:
			feedback = performUndoAction();
			break;

		case StringFormat.REDO:
			feedback = performRedoAction();
			break;

		case StringFormat.RELOAD:
			feedback = performReloadAction();
			break;

		case StringFormat.EXIT:
			feedback = performExitAction();
			break;

		default:
			feedback.setMessageShowToUser(String.format(
					ERROR_INVALID_COMMAND_ACTION, command.getAction()));
			return feedback;
		}

		if (feedback.getResult()) {
			saveUserCommand(command);
		}
		addInDisplayMessage(feedback);

		return feedback;
	}

	/**
	 * Perform add action with command object passed from proceedAnalyzedCommand
	 * method
	 *
	 * @param command
	 * @return
	 */
	private static Feedback performAddAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.ADD, false);

		String name = command.getTaskName();
		String description = command.getTaskDescription();
		String location = command.getTaskLocation();
		String priority = command.getTaskPriority();
		String startDateTimeString = command.getTaskStartTiming();
		String endDateTimeString = command.getTaskEndTiming();

		Date startDateTime = convertStringToDate(startDateTimeString);
		Date endDateTime = convertStringToDate(endDateTimeString);

		try {
			Task newTask = createNewTask(name, description, startDateTime,
					endDateTime, location, priority);
			fb.setResult(Storage.add(newTask));
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		if (fb.getResult()) {
			fb.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL, name));
		}

		return fb;
	}

	/**
	 * Create a new Task Object based on the attributes.
	 * 
	 * @param name
	 * @param description
	 * @param location
	 * @param priority
	 * @param startDateTime
	 * @param endDateTime
	 * @throws Exception
	 */
	private static Task createNewTask(String name, String description,
			Date startDateTime, Date endDateTime, String location,
			String priority) throws Exception {

		if (name.equals(null)) {
			throw new Exception("Null task name.");
		}

		Task newTask = new Task(name);

		if (!description.equals(null)) {
			newTask.setTaskDescription(description);
		} else if (!startDateTime.equals(null)) {
			newTask.setStartDateTime(startDateTime);
		} else if (!endDateTime.equals(null)) {
			newTask.setEndDateTime(endDateTime);
		} else if (!location.equals(null)) {
			newTask.setTaskLocation(location);
		} else if (!priority.equals(null)) {
			newTask.setTaskPriority(priority);
		}

		return newTask;
	}

	/**
	 * Perform delete action with command object passed from
	 * proceedAnalyzedCommand method
	 *
	 * @param command
	 * @return
	 * 
	 */
	private static Feedback performDeleteAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.DELETE, false);

		int taskId = command.getTaskId();

		try {
			fb.setResult(Storage.delete(taskId));
			if (fb.getResult()) {
				fb.setMessageShowToUser(String.format(
						MESSAGE_DELETE_SUCCESSFUL, taskId));
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
		Feedback fb = new Feedback(StringFormat.UPDATE, false);

		int taskId = command.getTaskId();
		String updateIndicator = command.getIndicator();
		String updateKeyValue = command.getKey();
		
		try {
			fb.setResult(Storage.update(taskId, updateIndicator, updateKeyValue));
			if (fb.getResult()) {
				fb.setMessageShowToUser(String.format(MESSAGE_UPDATE_SUCCESSFUL, taskId));
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
		Feedback fb = new Feedback(StringFormat.CLEAR, false);

		fb.setResult(Storage.clean());

		if (fb.getResult()) {
			fb.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFUL);
		}

		return fb;
	}

	/**
	 * Display the current taskList to the user using a arrayList. Display the
	 * passed time task using two boolean array.
	 * 
	 * @return
	 */
	private static Feedback performDisplayAction() {
		Feedback fb = new Feedback(StringFormat.DISPLAY, true);

		if (Storage.isEmpty()) {
			fb.setMessageShowToUser(MESSAGE_EMPTY_DISPLAY);
			return fb;
		}
		Storage.display();
		fb.setMessageShowToUser(MESSAGE_DISPLAY_SUCCESSFULLY);

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

		Feedback fb = new Feedback(StringFormat.SORT, false);

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
		Feedback fb = new Feedback(StringFormat.SEARCH, false);
		
		String searchIndicator = command.getIndicator();
		String searchValue = command.getKey();

		// check whether Storage can search the result or not
		try {
			Storage.search(searchIndicator, searchValue);
			
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
			return fb;
		}

		fb.setResult(true);
		fb.setMessageShowToUser(String.format(MESSAGE_SEARCH_SUCCESSFUL,
				searchValue, searchIndicator));

		return fb;
	}

	/**
	 * Perform undo action, which reverses previous steps Can perform undo
	 * multiple-steps
	 * 
	 * @return Feedback object
	 */
	private static Feedback performUndoAction() {
		Feedback fb = new Feedback(StringFormat.UNDO, false);

		if (commandStack.isEmpty()) {
			fb.setMessageShowToUser(ERROR_NOTHING_TO_UNDO);
			return fb;
		}

		try {
			Stack<ExecutableCommand> temp = new Stack<ExecutableCommand>();
			redoStack.add(commandStack.pop());

			while (!commandStack.isEmpty()) {
				temp.push(commandStack.pop());
			}

			// clean the tasklist and history.
			Storage.cleanUpEveryThing();
			// reload the data from saved file.
			Storage.reloadFile();

			while (!temp.isEmpty()) {
				proceedAnalyzedCommand(temp.pop());
			}
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
			return fb;
		}

		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_UNDO_SUCCESSFULLY);

		return fb;
	}

	/**
	 * Redo the undo steps Can redo the multiple previous undo steps
	 * 
	 * @return
	 */
	private static Feedback performRedoAction() {
		Feedback fb = new Feedback(StringFormat.REDO, false);

		if (redoStack.isEmpty()) {
			fb.setMessageShowToUser(ERROR_NOTHING_TO_REDO);
			return fb;
		}

		try {
			proceedAnalyzedCommand(redoStack.pop());
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
			return fb;
		}

		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_REDO_SUCCESSFULLY);

		return fb;

	}

	/**
	 * Obtain the result and message of reloadFile from Storage
	 * 
	 * @return
	 */
	private static Feedback performReloadAction() {
		Feedback fb = new Feedback(StringFormat.RELOAD, false);

		try {
			Storage.reloadFile();
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
			return fb;
		}

		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_RELOAD_SUCCESSFULLY);

		return fb;
	}

	/**
	 * Perform exit action with command object passed from
	 * proceedAnalyzedCommand method
	 * 
	 * @return
	 */
	private static Feedback performExitAction() {
		Feedback fb = new Feedback(StringFormat.EXIT, false);

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
	 * @return
	 */
	public static Feedback getFeedback() {
		return feedback;
	}

	/**
	 * Save user's commands in Stack
	 * 
	 * @param command
	 */
	private static void saveUserCommand(ExecutableCommand command) {
		if (!command.getAction().equals("undo")
				&& !command.getAction().equals("redo")
				&& !command.getAction().equals("reload")) {
			commandStack.push(command);
		}
	}

	/**
	 * Set displayed messages passed from Storage.
	 * 
	 * @param fb
	 */
	private static void addInDisplayMessage(Feedback fb) {
		fb.setTaskStringList(Storage.getStringFormatOfList());
		fb.setPassStartTimeIndicator(Storage.getPassStartTimeList());
		fb.setPassEndTimeIndicator(Storage.getPassEndTimeList());
	}

	/**
	 * Convert String format of Date to actual date.
	 * 
	 * @param dateTimeString
	 * @return
	 */
	private static Date convertStringToDate(String dateTimeString) {
		if (dateTimeString.equals("")) {
			return new Date((long) 0);
		}

		Long dateTimeLong = Long.parseLong(dateTimeString);
		Date dateTimeDate = new Date(dateTimeLong);

		return dateTimeDate;
	}
}
