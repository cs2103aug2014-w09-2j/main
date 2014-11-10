import java.util.*;

public class Executor {

	private static Stack<ExecutableCommand> commandStack = new Stack<ExecutableCommand>();
	private static Stack<ExecutableCommand> redoStack = new Stack<ExecutableCommand>();

	// @author A0112060E
	// these are for Update Method.
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d is updated successfully.\n";

	// these are for Sort Method
	private static final String MESSAGE_SORT_SUCCESSFUL = "Categories are sorted successfully.\n";

	// these are for Search Method
	private static final String MESSAGE_SEARCH_SUCCESSFUL = "Tasks are searched successfully.\n";
	private static final String MESSAGE_EMPTY_SEARCH_RESULT = "Search key was not found.\n";

	// these are for Undo Method
	private static final String MESSAGE_UNDO_SUCCESSFULLY = "Undo one step successfully.\n";
	private static final String ERROR_NOTHING_TO_UNDO = "There is nothing to undo.\n";

	// these are for Redo Method
	private static final String ERROR_NOTHING_TO_REDO = "There is nothing to redo.\n";
	private static final String MESSAGE_REDO_SUCCESSFULLY = "Redo one step successfully.\n";

	// @author A0119378U
	// these are for Display method.
	private static final String MESSAGE_DISPLAY_SUCCESSFULLY = "Tasks are displayed successfully.\n";

	// these are for Save and Reload.
	private static final String ERROR_FAIL_SAVE_TO_FILE = "Fail to save the Storage to file\n";
	private static final String MESSAGE_SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";
	private static final String MESSAGE_RELOAD_SUCCESSFULLY = "The Storage is reloaded successfully.\n";

	public static Feedback feedback;

	// @author A0119378U
	/**
	 * Called by Controller to initialize Executor. Splits into cases for
	 * processing in Executor component
	 * 
	 * @param command
	 *            ExecutableCommand from Controller.
	 * 
	 * @return a Feedback object
	 * 
	 */
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
			feedback = performClearAction();
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

	// @author A0119378U
	/**
	 * Adds a Task object to Storage. Returns a Feedback object to show to a
	 * user.
	 *
	 * @param command
	 *            ExecutableCommand from Controller. Need the Task Attributes
	 *            inside the Object.
	 * 
	 * @return a Feedback object
	 * 
	 */
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

	// @author A0112060E
	/**
	 * Deletes several tasks at the same time according to indices
	 *
	 * @param command
	 * 
	 * @return a Feedback object
	 * 
	 */
	private static Feedback performDeleteAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.DELETE, false);
		ArrayList<Integer> targetTaskIndex = command.getTaskId();
		int size = targetTaskIndex.size();

		sortFromBigToSmall(targetTaskIndex); // from big to small.

		for (int i = 0; i < size; i++) {
			int index = targetTaskIndex.get(i);
			index--;

			try {
				fb.setResult(Storage.delete(index));
			} catch (Exception e) {
				fb.setMessageShowToUser(e.getMessage());
				return fb;
			}
		}

		if (fb.getResult() && size == 1) {
			fb.setMessageShowToUser(StringFormat.EXE_MSG_DELETE_SUCCESSFUL);
		} else if (fb.getResult() && size >= 1) {
			fb.setMessageShowToUser(String.format(
					StringFormat.MSG_DELETE_MULTIPLE_TASKS_SUCCESSFUL, size));
		}

		return fb;
	}

	// @author A0119378U
	/**
	 * Sorts a index array from a big number to a small number.
	 * 
	 * @param targetTaskIndexArray
	 *            Rearrange the order to avoid the change of list size when
	 *            small index is deleted.
	 * 
	 */
	private static void sortFromBigToSmall(
			ArrayList<Integer> targetTaskIndexArray) {
		Comparator<Integer> reverseComparator = Collections.reverseOrder();
		Collections.sort(targetTaskIndexArray, reverseComparator);
	}

	// @author A0112060E
	/**
	 * Performs an/multiple update action(s) with a command object passed from
	 * the proceedAnalyzedCommand method
	 *
	 * @param command
	 * 
	 * @return a Feedback object
	 * 
	 */
	private static Feedback performUpdateAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.UPDATE, false);

		int taskId = command.getTaskId().get(0);
		String updateIndicator = command.getIndicator().get(0);
		String updateKeyValue = command.getKey().get(0);

		taskId--;
		try {
			fb.setResult(Storage
					.update(taskId, updateIndicator, updateKeyValue));
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}

		if (fb.getResult()) {
			fb.setMessageShowToUser(String.format(MESSAGE_UPDATE_SUCCESSFUL,
					(taskId + 1)));
		}

		fb.setResult(true);

		return fb;
	}

	// @author A0119378U
	/**
	 * Performs a/multiple delete action(s) in Storage. Deletes tasks displayed
	 * to a user.
	 *
	 * @return a Feedback object
	 * 
	 */
	private static Feedback performClearAction() {
		Feedback fb = new Feedback(StringFormat.CLEAR, false);

		int sizeOfDisplayTaskList = Storage.getDisplayTaskListSize();
		for (int i = sizeOfDisplayTaskList; i >= 1; i--) {
			int index = i - 1; // index in storage start from zero.
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

	// @author A0119378U
	/**
	 * Displays the current taskList to a user using an arrayList. Displays the
	 * passed time task using two boolean arrays.
	 * 
	 * @para command ExecutableCommand passed from Controller. Need the
	 *       indicator inside.
	 * 
	 * @return a Feedback object
	 * 
	 */
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

	// @author A0112060E
	/**
	 * Performs a/multiple sort action(s) with a command object passed from the
	 * proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * @return
	 * 
	 */
	private static Feedback performSortAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.SORT, false);
		ArrayList<String> sortKey = command.getIndicator();
		Collections.reverse(sortKey);

		// check what category user want to sort
		for (int i = 0; i < sortKey.size(); i++) {
			fb.setResult(Storage.sort(sortKey.get(i)));
		}
		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_SORT_SUCCESSFUL);

		return fb;
	}

	// @author A0112060E
	/**
	 * Performs a/multiple search action(s) with a command object passed from
	 * the proceedAnalyzedCommand method
	 *
	 * @param command
	 *            : ExecutableCommand object containing the user's action
	 * 
	 * @return a Feedback object
	 * 
	 */
	private static Feedback performSearchAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.SEARCH, false);

		ArrayList<String> searchIndicator = command.getIndicator();
		ArrayList<String> searchValue = command.getKey();

		assert searchIndicator.size() == searchValue.size() : "Invalid size of ArrayList in search function";

		// check whether Storage can search the result or not
		for (int i = 0; i < searchIndicator.size(); i++) {
			String searchIndicatorString = searchIndicator.get(i);
			String searchValueString = searchValue.get(i);

			if (searchIndicatorString.equals(StringFormat.START_DATE)
					|| searchValueString.equals(StringFormat.END_DATE)) {
				Date date = new Date(Long.parseLong(searchValueString));
				searchValueString = date.getYear() + "";
				searchValueString = searchValueString.concat(date.getMonth()
						+ "");
				searchValueString = searchValueString.concat(date.getDate()
						+ "");
			} else if (searchIndicatorString.equals(StringFormat.START_TIME)
					|| searchValueString.equals(StringFormat.END_TIME)) {
				Date date = new Date(Long.parseLong(searchValueString));
				searchValueString = date.getHours() + "";
				searchValueString = searchValueString.concat(date.getMinutes()
						+ "");
			}
			fb.setResult(Storage.search(searchIndicatorString,
					searchValueString));
		}

		if (Storage.displayTaskList.size() == 0) {
			fb.setMessageShowToUser(MESSAGE_EMPTY_SEARCH_RESULT);
		} else {
			fb.setMessageShowToUser(MESSAGE_SEARCH_SUCCESSFUL);
		}

		return fb;
	}

	// @author A0112060E
	/**
	 * Performs an/multiple undo action(s), which reverse(s) previous steps Can
	 * perform undo multiple-steps.
	 * 
	 * @return a Feedback object
	 * 
	 */
	private static Feedback performUndoAction() {
		Feedback fb = new Feedback(StringFormat.UNDO, false);

		// pre-condition
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

		// post-condition
		if (!commandStack.isEmpty()) {
			fb.setResult(true);
			fb.setMessageShowToUser(MESSAGE_UNDO_SUCCESSFULLY);
		} else {
			fb.setResult(false);
			fb.setMessageShowToUser(ERROR_NOTHING_TO_UNDO);
		}

		return fb;
	}

	// @author A0112060E
	/**
	 * Redo the undo steps Can redo the multiple previous undo steps
	 * 
	 * @return a Feedback object
	 * 
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

	// @author A0112060E
	/**
	 * Obtains a result and message of the reloadFile from Storage
	 * 
	 * @return a Feedback object
	 * 
	 */
	private static Feedback performReloadAction() {
		Feedback fb = new Feedback(StringFormat.RELOAD, false);

		try {
			Storage.reloadFile();
		} catch (Exception e) {
			fb.setMessageShowToUser("Exception in reload.\n");
			return fb;
		}

		fb.setResult(true);
		fb.setMessageShowToUser(MESSAGE_RELOAD_SUCCESSFULLY);

		return fb;
	}

	// @author A0119378U
	/**
	 * Performs a exit action with a command object passed from the
	 * proceedAnalyzedCommand method
	 * 
	 * @return a Feedback object
	 * 
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

	// @author A0119378U
	/**
	 * Adds a task into a history list as done.
	 * 
	 * @param command
	 *            ExecutableCommand Object passed from Controller.
	 * 
	 * @return a Feedback object
	 */
	private static Feedback performDoneAction(ExecutableCommand command) {
		Feedback fb = new Feedback(StringFormat.DONE, false);
		ArrayList<Integer> targetIndexList = command.getTaskId();

		for (int i = 0; i < targetIndexList.size(); i++) {
			int index = targetIndexList.get(i);
			index--; // index in storage start from zero.
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
	 * Return a feedback object to user
	 * 
	 * @return a Feedback object
	 * 
	 */
	public static Feedback getFeedback() {
		return feedback;
	}

	/**
	 * Saves user's commands in a Stack
	 * 
	 * @param command
	 *            ExecutableCommand Object passed in.
	 * 
	 */
	private static void saveUserCommand(ExecutableCommand command) {
		if (!command.getAction().equals("undo")
				&& !command.getAction().equals("redo")
				&& !command.getAction().equals("reload")) {
			commandStack.push(command);
		}
	}

	// @author A0119378U
	/**
	 * Set displayed messages passed from Storage.
	 * 
	 * @param fb
	 *            feedback object that will be returned to Controller.
	 * 
	 */
	private static void addInDisplayMessage(Feedback fb) {
		fb.setTaskStringList(Storage.getStringFormatOfList());
		fb.setPassStartTimeIndicator(Storage.getPassStartTimeList());
		fb.setPassEndTimeIndicator(Storage.getPassEndTimeList());
		try {
			fb.setListNameIndicator(Storage.listContainsDisplayList());
		} catch (Exception e) {
			fb.setMessageShowToUser(e.getMessage());
		}
	}

	/**
	 * Converts a String of Date to the actual date.
	 * 
	 * @param dateTimeString
	 * 
	 * @return
	 * 
	 */
	private static Date convertStringToDate(String dateTimeString) {
		if (dateTimeString.equals("")) {
			return null;
		}

		Long dateTimeLong = Long.parseLong(dateTimeString);
		Date dateTimeDate = new Date(dateTimeLong);

		return dateTimeDate;
	}

	// @author A0119378U
	/**
	 * Creates a new Task Object based on the attributes.
	 * 
	 * @param name
	 *            name string from ExecutableCommand Object
	 * @param description
	 *            description string from ExecutableCommand Object
	 * @param location
	 *            location string from ExecutableCommand Object
	 * @param priority
	 *            priority string from ExecutableCommand Object
	 * @param startDateTime
	 *            startDateTime long string from ExecutableCommand Object
	 * @param endDateTime
	 *            endDateTime long string from ExecutableCommand Object
	 * @throws Exception
	 *             throw Exception if the name is null or empty String.
	 * 
	 */
	private static Task createNewTask(String name, String description,
			Date startDateTime, Date endDateTime, String location,
			String priority) throws Exception {

		if (name.equals(null) || name.equals("")) {
			throw new Exception("Null task name");
		}

		Task newTask = new Task(name);

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