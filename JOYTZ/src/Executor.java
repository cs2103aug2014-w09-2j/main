//package V1;

import java.io.IOException;
import java.util.Date;

public class Executor {

	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";

	// these are for Add Method.
	private static final String MESSAGE_ADD_SUCCESSFUL = "\"%s\" is added successfully.\n";
	private static final String ERROR_ADD_UNSUCCESSFUL = "Fail to add \"%s\".\n";
	private static final String ERROR_TASK_WITHOUT_NAME = "Task name should be mentioned.\n";

	// these are for Delete Method.
	private static final String MESSAGE_DELETE_SUCCESSFUL = "\"%s\" is deleted successfully.\n";
	private static final String ERROR_DELETE_UNSUCCESSFUL = "Fail to delete task %d: \"%s\".\n";
	private static final String ERROR_INVALID_INDEX = "Task index indicated is invalid.\n";

	// these are for Clear Method.
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "All tasks are cleared successfully.\n";

	// these are for Display Method.
	private static final String MESSAGE_STORAGE_IS_EMPTY = "The task storage is empty.\n";
	private static final String MESSAGE_DISPLAY_SUCCESSFUL = "Here are the exist tasks.\n";

	// these are for Update Method.
	private static final String ERROR_INVALID_INDICATOR = "The update indicator is invalid.\n";
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task has been updated successfully.\n";

	// these are for Save and Reload.
	private static final String ERROR_FAIL_SAVE_TO_FILE = "Fail to save the Storage to file\n";
	private static final String MESSAGE_SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";

	public static Feedback feedback;

	public static Feedback proceedAnalyzedCommand(ExecutableCommand command) {
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
			Feedback feedBackObject = new Feedback(false);
			feedBackObject.setMessageShowToUser(ERROR_INVALID_COMMAND);
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

		if (name == "") {
			feedback.setMessageShowToUser(ERROR_TASK_WITHOUT_NAME);
			return;
		}

		// create a task object with all the attributes.
		Task t = new Task(name, date, description, location, priority);

		// add the task into the storage.
		feedback.setResult(Storage.add(t));

		if (feedback.getResult()) {
			feedback.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL,
					name));
		} else {
			feedback.setErrorMessage(String
					.format(ERROR_ADD_UNSUCCESSFUL, name));
		}
	}

	private static void performDeleteAction(ExecutableCommand command) {
		int taskId = command.getTaskId();
		String taskName;
		feedback = new Feedback(false);

		if (taskId < 0) {
			feedback.setErrorMessage(ERROR_INVALID_INDEX);
			return;
		}

		try {
			taskName = Storage.get(taskId).getTaskName();
			feedback.setResult(Storage.delete(taskId));
		} catch (Exception e) {
			feedback.setMessageShowToUser(e.getMessage());
			return;
		}

		if (feedback.getResult()) {
			feedback.setMessageShowToUser(String.format(
					MESSAGE_DELETE_SUCCESSFUL, taskId, taskName));
		} else {
			feedback.setErrorMessage(ERROR_DELETE_UNSUCCESSFUL);
		}
	}

	private static void performUpdateAction(ExecutableCommand command) {
		String updateIndicator = command.getUpdateIndicator();
		int taskId = command.getTaskId();
		Task targetTask;

		feedback = new Feedback(false);

		// check whether the task is out of range, catch the exception, and end
		// the function.
		if (taskId < 0) {
			feedback.setErrorMessage(ERROR_INVALID_INDEX);
			return;
		}

		try {
			targetTask = Storage.get(taskId);
		} catch (Exception e) {
			feedback.setMessageShowToUser(e.getMessage());
			return;
		}

		switch (updateIndicator) {
		case "name":
			String newName = command.getTaskName();
			targetTask.setTaskName(newName);
			break;
		case "description":
			String newDescription = command.getTaskDescription();
			targetTask.setTaskDescription(newDescription);
			break;
		case "deadline":
			Date newDeadline = command.getTaskDeadline();
			targetTask.setTaskDeadline(newDeadline);
			break;
		case "location":
			String newLocation = command.getTaskLocation();
			targetTask.setTaskLocation(newLocation);
			break;
		case "priority":
			String newPriority = command.getTaskPriority();
			targetTask.setTaskPriority(newPriority);
			break;
		default:
			feedback.setMessageShowToUser(ERROR_INVALID_INDICATOR);
			return;
		}
		feedback.setResult(true);
		feedback.setMessageShowToUser(MESSAGE_UPDATE_SUCCESSFUL);
	}

	private static void performDisplayAction() {

		feedback = new Feedback(false);

		// check whether the Storage is empty. If so, add in corresponding
		// message in feedbackObjcet.
		if (Storage.isEmpty()) {
			feedback.setMessageShowToUser(MESSAGE_STORAGE_IS_EMPTY);
			return;
		}

		// put every Task String in the Feedback.displayList.
		for (int taskId = 0; taskId < Storage.getTaskListSize(); taskId++) {
			Task currentTask;

			// in case the TaskId is out of range.
			try {
				currentTask = Storage.get(taskId);
				feedback.getTaskList().add(currentTask.convertTaskToString());
			} catch (Exception e) {
				feedback.setMessageShowToUser(e.getMessage());
				return;
			}
		}

		feedback.setResult(true);
		feedback.setMessageShowToUser(MESSAGE_DISPLAY_SUCCESSFUL);
		return;
	}

	private static void performClearAction() {
		feedback = new Feedback(false);
		feedback.setResult(Storage.clean());

		if (feedback.getResult()) {
			feedback.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFUL);
		}
		return;
	}

	private static void performSortAction() {
		// TODO Auto-generated method stubs
	}

	private static void performSearchAction() {
		// TODO Auto-generated method stub
	}

	private static void performExitAction() {
		feedback = new Feedback(false);

		// check whether the storage can store the information into a file.
		try {
			Storage.saveFile();
		} catch (IOException e) {
			feedback.setMessageShowToUser(String
					.format(ERROR_FAIL_SAVE_TO_FILE));
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
