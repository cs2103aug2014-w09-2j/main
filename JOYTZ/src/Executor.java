//package V1;

import java.util.*;

public class Executor {

	private static Stack<ExecutableCommand> commandStack = new Stack<ExecutableCommand>();
	private static Stack<ExecutableCommand> redoStack = new Stack<ExecutableCommand>();

	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";
	private static final String ERROR_INVALID_COMMAND_ACTION = "Invalid command action: %s.\n";

	// these are for Add Method.
	private static final String MESSAGE_ADD_SUCCESSFUL = "%s is added successfully.\n";
	private static final String ERROR_INVALID_TIMING = "Invalid start and end time\n";

	// these are for Delete Method.
	private static final String MESSAGE_DELETE_SUCCESSFUL = "%d. \"%s\" is deleted successfully.\n";

	// these are for Clear Method.
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "All tasks are cleared successfully.\n";

	// these are for Update Method.
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d, \"%s\"is updated successfully.\n";

	// these are for Sort Method
	private static final String MESSAGE_SORT_SUCCESSFUL = "Category \"%s\" is sorted successfully.\n";

	// these are for Search Method
	private static final String MESSAGE_SEARCH_SUCCESSFUL = "\"%s\" in \"%s\" is searched successfully.\n";

	// these are for Undo Method
	private static final String MESSAGE_UNDO_SUCCESSFULLY = "Undo one step successfully.";
	private static final String ERROR_NOTHING_TO_UNDO = "There is nothing to undo.";

	// these are for Redo Method
	private static final String ERROR_NOTHING_TO_REDO = "There is nothing to redo";
	private static final String MESSAGE_REDO_SUCCESSFULLY = "Redo one strp successfully";

	// these are for Save and Reload.
	private static final String ERROR_FAIL_SAVE_TO_FILE = "Fail to save the Storage to file\n";
	private static final String MESSAGE_SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";
	private static final String MESSAGE_RELOAD_SUCCESSFULLY = "The Storage is reloaded successfully.\n";

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

		if (command.equals(new ExecutableCommand()) || command.equals(null)) {
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
			feedback = performUndoAction();
			break;
		case "redo":
			feedback = performRedoAction();
			break;
		case "reload":
			feedback = performReloadAction();
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
			if (!command.getAction().equals("undo")
					&& !command.getAction().equals("redo")) {
				storeCommand(command);
			}
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

		Long startTime;
		Long endTime;

		Feedback fb = new Feedback(false);

		if (!startTiming.equals("") && !endTiming.equals("")) {
			startTime = Long.parseLong(startTiming);
			endTime = Long.parseLong(endTiming);
		} else if (startTiming.equals("") && !endTiming.equals("")) {
			startTime = System.currentTimeMillis();
			endTime = Long.parseLong(endTiming);
		} else if (!startTiming.equals("") && endTiming.equals("")) {
			startTime = Long.parseLong(startTiming);
			endTime = Long.MAX_VALUE;
		} else {
			startTime = System.currentTimeMillis();
			endTime = Long.MAX_VALUE;
		}

		if (startTime > endTime) {
			fb.setMessageShowToUser(ERROR_INVALID_TIMING);
			return fb;
		}

		// create a task object with all the attributes.
		Task t = new Task(name, startTime, endTime, description, location,
				priority);

		// pre-condition
		assert !t.equals(new Task()) : "No task created";

		// add the task into the storage.
		try {
			fb.setResult(Storage.add(t));
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		// post-condition
		assert fb.getResult() : "Fail to add tasks";

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
		assert taskId != -1 : "Task index " + taskId;

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
		assert !searchIndicator.equals("") : "No search indicator";
		assert !searchValue.equals("") : "No search value";

		// check whether Storage can search the result or not
		try {
			resultList = Storage.search(searchIndicator, searchValue);
			fb.setTaskList(resultList);
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		// post-condition
		assert !searchValue.equals("") : "No given search value";
		assert !resultList.equals(null) : "No result is found";

		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_SEARCH_SUCCESSFUL);

		return fb;
	}

	/**
	 * Perform undo action
	 * 
	 * @param int numOfStep
	 * @return
	 */
	/*
	 * private static Feedback performUndoAction(int numberOfSteps) { Feedback
	 * fb = new Feedback(false);
	 * 
	 * int limit = Integer.min(commandStack.size(), numberOfSteps);
	 * 
	 * for (int step = 0; step <= limit; step++) { Feedback feedback =
	 * performUndoAction(); if (!feedback.getResult()) {
	 * fb.setMessageShowToUser(feedback.getMessageShowToUser()); return fb; } }
	 * 
	 * fb.setResult(true); fb.setMessageShowToUser(MESSAGE_UNDO_SUCCESSFULLY);
	 * 
	 * return fb; }
	 */

	private static Feedback performUndoAction() {
		Feedback fb = new Feedback(false);

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
			Storage.cleanUpEveryThing();
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

	private static Feedback performRedoAction() {
		Feedback fb = new Feedback(false);

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

	private static Feedback performReloadAction() {
		Feedback fb = new Feedback(false);

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

	private static void storeCommand(ExecutableCommand command) {
		commandStack.push(command);
	}
}
