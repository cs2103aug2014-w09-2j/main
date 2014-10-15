//package V1;

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
	private static final String ERROR_INVALID_INDICATOR = "The update indicator is invalid.\n";
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d, \"%s\"is updated successfully.\n";

	// these are for Sort Method
	private static final String ERROR_INVALID_SORTING_INDICATOR = "The sorting indicator is invalid.\n";
	private static final String MESSAGE_SORT_SUCCESSFUL = "Category \"%s\" is sorted successfully.\n";
	private static final String ERROR_FAIL_TO_SORT = "Nothing to sort.\n";

	// these are for Save and Reload.
	private static final String ERROR_FAIL_SAVE_TO_FILE = "Fail to save the Storage to file\n";
	private static final String MESSAGE_SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";

	public static Feedback feedback;

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
			performSearchAction();
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

	private static void performAddAction(ExecutableCommand command) {
		String name = command.getTaskName();
		String description = command.getTaskDescription();
		Date date = command.getTaskDeadline();
		String location = command.getTaskLocation();
		String priority = command.getTaskPriority();
		feedback = new Feedback(false);

		// pre-condition
		assert !name.equals("") : "No task name";
		assert !description.equals("") : "No task description";
		assert !date.equals(new Date()) : " No deadline";
		assert !location.equals("") : "No task location";
		assert !priority.equals("") : "no priority";

		// create a task object with all the attributes.
		Task t = new Task(name, date, description, location, priority);

		// add the task into the storage.
		feedback.setResult(Storage.add(t));

		// post-condition
		assert feedback.getResult() : "Unsuccessful to add task";

		feedback.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL,
				name));

	}

	private static void performDeleteAction(ExecutableCommand command) {
		int taskId = command.getTaskId();
		String taskName;
		feedback = new Feedback(false);

		// pre-condition
		assert taskId != -1 : "No task index";

		taskName = Storage.get(taskId).getTaskName();
		feedback.setResult(Storage.delete(taskId));

		if (feedback.getResult()) {
			feedback.setMessageShowToUser(String.format(
					MESSAGE_DELETE_SUCCESSFUL, taskId, taskName));
		} else {
			feedback.setErrorMessage(ERROR_INVALID_TASK_INDEX);
		}

	}

	private static void performUpdateAction(ExecutableCommand command) {
		String updateIndicator = command.getUpdateIndicator();
		int taskId = command.getTaskId();
		String taskName;
		String newInfo;
		feedback = new Feedback(false);

		// pre-condition
		assert !updateIndicator.equals("") : "No update indicator";
		assert taskId != -1 : "No task index";

		// check whether the task is out of range, catch the exception, and end
		// the function.
		switch (updateIndicator) {
		case "name":
			newInfo = command.getUpdatedTaskName();
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

		feedback.setResult(Storage.update(taskId, updateIndicator, newInfo));

		if (feedback.getResult()) {
			taskName = Storage.get(taskId).getTaskName();
			feedback.setMessageShowToUser(String.format(
					MESSAGE_UPDATE_SUCCESSFUL, taskId, taskName));
		} else {
			feedback.setErrorMessage(ERROR_INVALID_TASK_INDEX);
		}
	}

	private static void performClearAction() {
		feedback = new Feedback(false);

		feedback.setResult(Storage.clean());
		feedback.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFUL);

		// post-condition
		assert feedback.getResult() : "Nothing to clear";
	}

	private static void performSortAction(ExecutableCommand command) {
		String sortKey = command.getSortIndicator();
		feedback = new Feedback(false);

		// pre-condition
		assert !sortKey.equals("") : "No sort indicator";

		// check what category sorted
		switch (sortKey) {
		case "name":
		case "deadline":
		case "priority":
		case "location":
			feedback.setResult(Storage.sort(sortKey));
			break;
		default:
			feedback.setErrorMessage(ERROR_INVALID_SORTING_INDICATOR);
			return;
		}

		if (feedback.getResult()) {
			feedback.setMessageShowToUser(MESSAGE_SORT_SUCCESSFUL);
		} else {
			feedback.setErrorMessage(ERROR_FAIL_TO_SORT);
		}
	}

	private static void performSearchAction() {
		// TODO Auto-generated method stub
	}

	private static void performUndoAction() {

	}

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

	public static Feedback getFeedback() {
		return feedback;
	}
}
