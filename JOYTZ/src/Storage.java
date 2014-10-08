//package V1;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Storage {
	static ArrayList<Task> listOfTask = new ArrayList<Task>();
	static ArrayList<Task> history = new ArrayList<Task>();
	static int numberOfTask = 0;
	static Timer timer = new Timer();

	private static final String ERROR_INDEX_OUT_OF_RANGE = "The index '%d' is out of range.\n";
	private static final String MESSAGE_DISPLAY_WITH_DATE = "%d. \"%s\" due on %s\n";
	private static final String MESSAGE_DISPLAY_WITHOUT_DATE = "%d. \"%s\" without due date\n";
	private static final String MESSAGE_EMPTY_TASK = "There is no task currently.\n";

	/**
	 * Methods
	 */

	public static Feedback addTask(Task t) {
		Feedback feedbackObject = new Feedback(false);

		feedbackObject.setResult(true);
		listOfTask.add(t);
		// timer.schedule(t, t.getTime());
		numberOfTask++;

		return feedbackObject;
	}

	public static Feedback deleteTask(int itemId) {
		Feedback feedbackObject = new Feedback(false);

		if (numberOfTask < itemId) {
			feedbackObject.setMessageShowToUser(String.format(
												ERROR_INDEX_OUT_OF_RANGE, 
												itemId));
			return feedbackObject;
		}

		feedbackObject.setResult(true);
		Task removedTask = listOfTask.remove(itemId - 1);
		// removedTask.cancel();
		numberOfTask--;
		history.add(removedTask);
		feedbackObject.setDescription(removedTask.getDescription());

		return feedbackObject;
	}

	public static Feedback displayTask() {
		Feedback feedbackObject = new Feedback(true);

		if (listOfTask.size() == 0) {
			feedbackObject.setMessageShowToUser(String
												.format(MESSAGE_EMPTY_TASK));
		} else {
			for (int index = 0; index < listOfTask.size(); index++) {
				Date date = listOfTask.get(index).getExpiredDate();
				String description = listOfTask.get(index).getDescription();

				if (date == null) {
					feedbackObject.setMessageShowToUser(String.format(
							MESSAGE_DISPLAY_WITHOUT_DATE, index + 1,
							description));
				} else {
					feedbackObject.setMessageShowToUser(String.format(
							MESSAGE_DISPLAY_WITH_DATE, index + 1, description,
							date));
				}
			}
		}
		return feedbackObject;
	}

	public static Feedback clear() {
		Feedback feedbackObject = new Feedback(false);

		if (listOfTask.isEmpty()) {
			feedbackObject.setMessageShowToUser(MESSAGE_EMPTY_TASK);
			return feedbackObject;
		}

		for (int itemId = 0; itemId < listOfTask.size(); itemId++) {
			history.add(listOfTask.get(itemId));
		}

		listOfTask.clear();
		feedbackObject.setResult(true);

		return feedbackObject;
	}

	/*
	 * public static Feedback checkStatus(){ Feedback feedbackObject = new
	 * Feedback(true);
	 * 
	 * Date now = new Date(); for (int itemId = 0; itemId < listOfTask.size();
	 * itemId++){ if (listOfTask.get(itemId).getTime().before(now)){ Task
	 * removedTask = listOfTask.remove(itemId);
	 * removedTask.setStatusToBeExpired(); history.add(removedTask);
	 * feedbackObject.setMessageShowToUser(String.format(MESSAGE_EXPIRED,
	 * removedTask.toString())); } } return feedbackObject; }
	 */

}
