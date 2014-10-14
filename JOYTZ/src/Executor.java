//package V1;

import java.io.IOException;
import java.util.Date;

public class Executor {
	
	private static final String ERROR_INVALID_COMMAND = "Invalid command.\nPlease try again.\n";
	
	// these are for Add Method.
	private static final String MESSAGE_ADD_SUCCESSFUL = "Task %s is added successfully.\n";
	private static final String ERROR_TASK_WITHOUT_NAME = "Task should contains a name.\n";
	
	// these are for Delete Method.
	private static final String MESSAGE_DELETE_SUCCESSFUL = "\"%s\" is deleted successfully.\n";
	
	// these are for Clear Method.
	private static final String MESSAGE_CLEAR_SUCCESSFULLY = "all content cleared successfully\n";
	
	// these are for Display Method.
	private static final String MESSAGE_STORAGE_IS_EMPTY = "The storage is empty.\n";
	private static final String MESSAGE_DISPLAY_SUCCESSFULLY = "Here are the tasks.\n";
	
	// these are for Update Method.
	private static final String ERROR_WRONG_INDICATOR = "The indicator is wrong.\n";
	private static final String MESSAGE_UPDATE_SUCCESSFULLY = "Task has been updated successfully.\n";
	
	// these are for Save and Reload.
	private static final String MESSAGE_CONNOT_SAVE_TO_FILE = "Cannot store the Storage to file\n";
	private static final String MESSAGE_SAVED_SUCCESSFULLY = "The Storage is saved successfully.\n";

	public static Feedback feedbackObject;

	public static Feedback proceedAnalyzedCommand(ExecutableCommand command) {
		String action = command.getAction();

		switch (action) {
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
		case "update":
			performUpdateAction(command);
			break;
		case "exit":
			performExitAction();
			break;
		default:
			Feedback feedBackObject = new Feedback(false);
			feedBackObject.setMessageShowToUser(ERROR_INVALID_COMMAND);
		}

		return feedbackObject;
	}
	
	private static void performUpdateAction(ExecutableCommand command) {
		String updateIndicator = command.getUpdateIndicator();
		int taskId = command.getTaskId();
		Task targetTask;

		feedbackObject = new Feedback(false, "update");

		// check whether the task is out of range, catch the exception.
		try {
			targetTask = Storage.get(taskId);
		} catch (Exception e) {
			feedbackObject.setMessageShowToUser(e.getMessage());
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
			Date newDate = command.getTaskDeadline();
			targetTask.setTaskDeadline(newDate);
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
			feedbackObject.setMessageShowToUser(ERROR_WRONG_INDICATOR);
			return;
		}
		feedbackObject.setResult(true);
		feedbackObject.setMessageShowToUser(MESSAGE_UPDATE_SUCCESSFULLY);
	}

	private static void performAddAction(ExecutableCommand command) {
		String name = command.getTaskName();
		Date date = command.getTaskDeadline();
		String description = command.getTaskDescription();
		String location = command.getTaskLocation();

		feedbackObject = new Feedback(false, "add");

		if (name == null) {
			feedbackObject
					.setMessageShowToUser(ERROR_TASK_WITHOUT_NAME);
			return;
		}

		// create a task object with all the attributes.
		Task t = new Task(name);
		t.setTaskDeadline(date);
		t.setTaskDescription(description);
		t.setTaskLocation(location);

		// add the task into the storage.
		try {
			feedbackObject.setResult(Storage.add(t));
		} catch (Exception e) {
			feedbackObject.setMessageShowToUser(e.getMessage());
			return;
		}

		feedbackObject.setResult(true);
		feedbackObject.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL, name));
	}

	private static void performDeleteAction(ExecutableCommand command) {
		int taskId = command.getTaskId();
		String taskName;
		feedbackObject = new Feedback(false, "delete");

		try {
			taskName = Storage.get(taskId).getTaskName();
			feedbackObject.setResult(Storage.delete(taskId));
		} catch (Exception e) {
			feedbackObject.setMessageShowToUser(e.getMessage());
			return;
		}

		if (feedbackObject.getResult()) {
			feedbackObject.setMessageShowToUser(String.format(MESSAGE_DELETE_SUCCESSFUL, taskName));
		}
		return;
	}

	private static void performDisplayAction() {

		feedbackObject = new Feedback(false, "display");

		// check whether the Storage is empty. If so, add in corresponding
		// message in feedbackObjcet.
		if (Storage.isEmpty()) {
			feedbackObject.setMessageShowToUser(MESSAGE_STORAGE_IS_EMPTY);
			return;
		}

		// put every Task String in the Feedback.displayList.
		for (int taskId = 0; taskId < Storage.getSizeOfListOfTask(); taskId++) {
			Task currentTask;

			// in case the TaskId is out of range.
			try {
				currentTask = Storage.get(taskId);
				feedbackObject.displayList.add(currentTask
						.convertTaskToString());
			} catch (Exception e) {
				feedbackObject.setMessageShowToUser(e.getMessage());
				return;
			}
		}

		feedbackObject.setResult(true);
		feedbackObject.setMessageShowToUser(MESSAGE_DISPLAY_SUCCESSFULLY);
		return;
	}

	private static void performClearAction() {
		feedbackObject = new Feedback(false, "clear");
		feedbackObject.setResult(Storage.clean());

		if (feedbackObject.getResult()) {
			feedbackObject.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFULLY);
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
		feedbackObject = new Feedback(false, "exit");

		// check whether the storage can store the information into a file.
		try {
			Storage.saveFile();
		} catch (IOException e) {
			feedbackObject.setMessageShowToUser(String
					.format(MESSAGE_CONNOT_SAVE_TO_FILE));
			e.printStackTrace();
			return;
		}

		feedbackObject.setResult(true);
		feedbackObject.setMessageShowToUser(String
				.format(MESSAGE_SAVED_SUCCESSFULLY));
		System.exit(0);
	}

	public static Feedback getFeedback() {
		return feedbackObject;
	}
}
