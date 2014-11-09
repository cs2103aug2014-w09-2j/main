//@author A0112060E
//@author A011938U

import java.util.*;

public class Executor {

	private static Stack<ExecutableCommand> commandStack = new Stack<ExecutableCommand>();
	private static Stack<ExecutableCommand> redoStack = new Stack<ExecutableCommand>();

	// these are for Display method.
	private static final String MESSAGE_DISPLAY_SUCCESSFULLY = "Tasks are displayed successfully.\n";

	// these are for Update Method.
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d is updated successfully.\n";
	private static final String ERROR_INVALID_UPDATE = "Invalid update attributes.\n";

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
	 * Split into cases for processing in Executor component
	 * 
	 * @param command
	 * @return
	 */
	// @author A0112060E
	// @author A0119378U
	public static Feedback proceedAnalyzedCommand(ExecutableCommand command) {
		feedback = new Feedback(false);

		if (command.equals(null)) {
			feedback.setMessageShowToUser(StringFormat.EXE_ERROR_NULL_EXECUTABLE_COMMAND);
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
			feedback = performClearAction(command);
			break;

		case StringFormat.DISPLAY:
			feedback = performDisplayAction(command);
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

		case StringFormat.DONE:
			feedback = performDoneAction(command);
			break;

		default:
			feedback.setMessageShowToUser(String.format(
					StringFormat.EXE_ERROR_INVALID_COMMAND_ACTION,
					command.getAction()));
			return feedback;
		}

		if (feedback.getResult()) {
			saveUserCommand(command);
		}
		addInDisplayMessage(feedback);

		return feedback;
	}

	/**
	 * Adds a Task object to Storage.
	 * 
	 * Returns a Feedback object to show to a user.
	 *
	 * @param command
	 * @return
	 */
	// @author A0112060E
	// @author A0119378U
	private static Feedback performAddAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.ADD, false);

		String name = command.getTaskName();
		String description = command.getTaskDescription();
		String location = command.getTaskLocation();
		String priority = command.getTaskPriority();
		String startDateTimeString = command.getTaskStart();
		String endDateTimeString = command.getTaskEnd();

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
			fb.setMessageShowToUser(String.format(
					StringFormat.EXE_MSG_ADD_SUCCESSFUL, name));
		}

		return fb;
	}

	/**
	 * Deletes several tasks at the same time according to indices
	 *
	 * @param command
	 * @return
	 * 
	 */
	// @author A0112060E
	// @author A0119378U
	private static Feedback performDeleteAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.DELETE, false);

		ArrayList<Integer> targetTaskIndex = command.getTaskId();
		sort(targetTaskIndex); // from big to small.

		for (int i = 0; i < targetTaskIndex.size(); i++) {
			int index = targetTaskIndex.get(i);
			index--;
			try {
				fb.setResult(Storage.delete(index));
			} catch (Exception e) {
				fb.setMessageShowToUser(e.getMessage());
				break;
			}
		}
		if (fb.getResult()) {
			fb.setMessageShowToUser(StringFormat.EXE_MSG_DELETE_SUCCESSFUL);
		}

		return fb;
	}

	/**
	 * Sorts a index array from big number to small number.
	 * 
	 * @param targetTaskIndexArray
	 */
	// @author A0119378U
	private static void sort(ArrayList<Integer> targetTaskIndexArray) {
		Comparator<Integer> reverseComparator = Collections.reverseOrder();
		Collections.sort(targetTaskIndexArray, reverseComparator);
	}

	/**
	 * Performs an/multiple update action(s) with a command object passed from
	 * the proceedAnalyzedCommand method
	 *
	 * @param command
	 * @return
	 * 
	 */
	// @author A0112060E
	private static Feedback performUpdateAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.UPDATE, false);

		ArrayList<Integer> taskId = command.getTaskId();
		ArrayList<String> updateIndicator = command.getIndicator();
		ArrayList<String> updateKeyValue = command.getKey();

		assert taskId.size() == updateIndicator.size() : "Invalid size of ArrayList in update function 1";
		assert taskId.size() == updateKeyValue.size() : "Invalid size of ArrayList in update function 2";
		assert updateKeyValue.size() == updateIndicator.size() : "Invalid size of ArrayList in update function 3";

		for (int i = 0; i < taskId.size(); i++) {
			int index = taskId.get(i);
			index--;
			try {
				fb.setResult(Storage.update(index, updateIndicator.get(i),
						updateKeyValue.get(i)));
				if (fb.getResult()) {
					index--;
					Task targetTask = Storage.displayTaskList
							.getTaskByIndex(index);

					fb.setMessageShowToUser(String.format(
							MESSAGE_UPDATE_SUCCESSFUL, targetTask.getTaskName()));
				} else {
					fb.setMessageShowToUser(ERROR_INVALID_UPDATE);
				}
			} catch (Exception e) {
				fb.setMessageShowToUser(e.getMessage());
			}
		}

		return fb;
	}

	/**
	 * Performs a/multiple delete action(s) in Storage.
	 * 
	 * Deletes tasks displayed to a user.
	 * 
	 * @param command
	 * @return
	 */
	// @author A0119378U
	private static Feedback performClearAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.CLEAR, false);

		int sizeOfDisplayTaskList = Storage.getDisplayTaskListSize();
		for (int i = sizeOfDisplayTaskList; i >= 1; i--) {
			int index = i - 1;
			try {
				fb.setResult(Storage.delete(index));
			} catch (Exception e) {
				fb.setMessageShowToUser(e.getMessage());
				return fb;
			}
		}
		if (fb.getResult()) {
			fb.setMessageShowToUser(StringFormat.EXE_MSG_CLEAR_SUCCESSFUL);
		}

		return fb;
	}

	/**
	 * Display the current taskList to the user using a arrayList. Display the
	 * passed time task using two boolean array.
	 * 
	 * @return
	 */
	// @author A0119378U
	private static Feedback performDisplayAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.DISPLAY, false);
		if (command.getIndicator().size() != 1) {
			fb.setMessageShowToUser(StringFormat.EXE_ERROR_NO_TASK_LIST_INDICATOR);
			return fb;
		}

		String targetListIndicator = command.getIndicator().get(0);
		try {
			fb.setResult(Storage.display(targetListIndicator));
		} catch (Exception e) {
			fb.setResult(false);
			fb.setMessageShowToUser(e.getMessage());
		}

		if (fb.getResult()) {
			fb.setMessageShowToUser(MESSAGE_DISPLAY_SUCCESSFULLY);
		}

		return fb;
	}

	/**
	 * Performs a/multiple sort action(s) with a command object passed from the
	 * proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	// @author A0112060E
	private static Feedback performSortAction(ExecutableCommand command) {
		ArrayList<String> sortKey = command.getIndicator();

		Feedback fb = new Feedback(StringFormat.SORT, false);

		// check what category user want to sort
		for (int i = 0; i < sortKey.size(); i++) {
			try {
				fb.setResult(Storage.sort(sortKey.get(i)));
			} catch (Exception e) {
				fb.setMessageShowToUser(e.getMessage());
				return fb;
			}

			if (fb.getResult()) {
				fb.setMessageShowToUser(String.format(MESSAGE_SORT_SUCCESSFUL,
						sortKey.get(i)));
			}
		}

		return fb;
	}

	/**
	 * Performs a/multiple search action(s) with a command object passed from
	 * the proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	// @author A0112060E
	private static Feedback performSearchAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.SEARCH, false);

		ArrayList<String> searchIndicator = command.getIndicator();
		ArrayList<String> searchValue = command.getKey();

		assert searchIndicator.size() == searchValue.size() : "Invalid size of ArrayList in search function";

		// check whether Storage can search the result or not
		for (int i = 0; i < searchIndicator.size(); i++) {
			try {
				Storage.search(searchIndicator.get(i), searchValue.get(i));

			} catch (Exception e) {
				fb.setMessageShowToUser(e.getMessage());
				return fb;
			}
		}

		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_SEARCH_SUCCESSFUL);

		return fb;
	}

	/**
	 * Performs an/multiple undo action(s), which reverses previous steps
	 * 
	 * Can perform undo multiple-steps.
	 * 
	 * @return
	 */
	// @author A0112060E
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
			// clean the mainTaskList and doneTaskList.
			Storage.clean();
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
	 * Redoes the undo steps
	 * 
	 * Can redo the multiple previous undo steps
	 * 
	 * @return
	 */
	// @author A0112060E
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
	 * Obtains the result and message of the reloadFile from Storage
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
	 * Performs a exit action with a command object passed from the
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
	 * Adds a task into a history list as done.
	 * 
	 * @param command
	 * @return
	 */
	// @author A0119378U
	private static Feedback performDoneAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.DONE, false);
		ArrayList<Integer> targetIndexList = command.getTaskId();
		for (int i = 0; i < targetIndexList.size(); i++) {
			int index = targetIndexList.get(i);
			index--;
			try {
				fb.setResult(Storage.done(index));
			} catch (Exception e) {
				fb.setMessageShowToUser(e.getMessage());
				return fb;
			}
		}
		fb.setResult(true);
		fb.setMessageShowToUser(StringFormat.EXE_MSG_DONE_SUCCESSFUL);
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
	// @author A0119378U
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
			return null;
		}
		Long dateTimeLong = Long.parseLong(dateTimeString);
		Date dateTimeDate = new Date(dateTimeLong);

		return dateTimeDate;
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
	// @author A0119378U
	private static Task createNewTask(String name, String description,
			Date startDateTime, Date endDateTime, String location,
			String priority) throws Exception {

		if (name.equals(null)) {
			throw new Exception("Null task name");
		}

		Task newTask = new Task(name);
		newTask.setTaskId(Storage.obtainNewTaskId());

		if (!(description.equals(""))) {
			newTask.setTaskDescription(description);
		}
		if (!(startDateTime == null)) {
			newTask.setStartDateTime(startDateTime);
		}
		if (!(endDateTime == null)) {
			newTask.setEndDateTime(endDateTime);
		}
		if (!(location.equals(""))) {
			newTask.setTaskLocation(location);
		}
		if (!(priority.equals(""))) {
			newTask.setTaskPriority(priority);
		}

		return newTask;
	}

}
