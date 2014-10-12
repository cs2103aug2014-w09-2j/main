//package V1;

import java.util.Date;

public class Executor {

	private static final String MESSAGE_ADD_SUCCESSFUL_WITH_DATE = "\"%s\" due on %s is added\n";
	private static final String MESSAGE_ADD_SUCCESSFUL_WITHOUT_DATE = "\"%s\" is added\n";
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "all content deleted successfully\n";
	private static final String MESSAGE_DELETE_SUCCESSFUL = "\"%s\" is deleted\n";
	private static final String ERROR_INVALID_COMMAND = "Invalid command.\nPlease try again.\n";
	private static final String ERROR_INVALID_INDEX = "Invalid item index, please try again.\n";
	private static final String ERROR_NO_TASK_TO_BE_ADDED = "No task is found to be added, please try again\n";
	private static final String MESSAGE_STORAGE_IS_EMPTY = "The storage is empty.\n";
	private static final String MESSAGE_WRONG_INDICATOR = "The indicator is wrong.\n";
	private static final String MESSAGE_TASKID_OUT_OF_RANGE = "The taskid is out of range. TaskId : %d\n";

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
			performUpdateFunction(command);
		case "exit":
			performExitAction();
			break;
		default:
			Feedback feedBackObject = new Feedback(false);
			feedBackObject.setMessageShowToUser(ERROR_INVALID_COMMAND);
		}

		return feedbackObject;
	}

	private static void performUpdateFunction(ExecutableCommand command) {
		String updateIndicator = command.getUpdateIndicator();
		int taskId = command.getTaskId();
		Task targetTask;

		feedbackObject = new Feedback(false, "update");

		// check whether the task is out of range, catch the exception, and end
		// the function.
		try {
			targetTask = Storage.get(taskId);
		} catch (Exception e) {
			feedbackObject.setMessageShowToUser(String.format(
					MESSAGE_TASKID_OUT_OF_RANGE, taskId));
			return;
		}

		switch (updateIndicator) {
		case "name":
			String newName = command.getTaskName();
			targetTask.setTaskName(newName);
			;
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
			feedbackObject.setMessageShowToUser(MESSAGE_WRONG_INDICATOR);
			return;
		}
		feedbackObject.setResult(true);
		return;
	}

	private static void performAddAction(ExecutableCommand command) {
		String name = command.getTaskName();
		Date date = command.getTaskDeadline();
		String description = command.getTaskDescription();
		String location = command.getTaskLocation();

		feedbackObject = new Feedback(false, "add");

		if (name == null) {
			feedbackObject
					.setMessageShowToUser("Task should contain a task name.");
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
			e.printStackTrace();
		}

		feedbackObject.setResult(true);
		feedbackObject.setMessageShowToUser("Message added sccessfully.");
	}

	private static void performDeleteAction(ExecutableCommand command) {
		int TaskId = command.getTaskId();
		feedbackObject = new Feedback(false, "delete");

		// check whether the taskId is out of range.
		if (TaskId <= 0 || TaskId > Storage.getSizeOfListOfTask()) {
			feedbackObject.setMessageShowToUser("Index out of range.");
			return;
		}

		try {
			feedbackObject.setResult(Storage.delete(TaskId));
		} catch (Exception e) {
			feedbackObject.setMessageShowToUser(String.format(
					MESSAGE_TASKID_OUT_OF_RANGE, TaskId));
		}

		if (feedbackObject.getResult()) {
			feedbackObject.setMessageShowToUser("task has been deleted.");
		}
		return;
	}

	private static void performDisplayAction() {

		feedbackObject = new Feedback(false, "display");

		// check whether the Storage is empty. If so, add in corresponding
		// message in feedbackObjcet.
		if (Storage.isEmpty()) {
			feedbackObject.setMessageShowToUser(String
					.format(MESSAGE_STORAGE_IS_EMPTY));
			return;
		}

		// put every Task String in the Feedback.displayList.
		for (int taskId = 0; taskId < Storage.getSizeOfListOfTask(); taskId++) {
			Task currentTask;

			// in case the TaskId is out of range.
			try {
				currentTask = Storage.get(taskId);
				feedbackObject.dispalyList.add(currentTask
						.convertTaskToString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		feedbackObject.setResult(true);
		feedbackObject.setMessageShowToUser("Here are the tasks\n");
		return;
	}

	private static void performClearAction() {
		feedbackObject = new Feedback(false, "clear");
		feedbackObject.setResult(Storage.clean());

		if (feedbackObject.getResult()) {
			feedbackObject.setMessageShowToUser("List has been cleaned.");
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
		feedbackObject = new Feedback(true, "exit");
		System.exit(0);
	}

	public static Feedback getFeedback() {
		return feedbackObject;
	}
}
