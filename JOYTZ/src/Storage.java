//@author A0119378U

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.logging.Logger;

public class Storage {

	private static final Logger LOGGER = Logger.getLogger(Storage.class
			.getName());

	// Exception messages and error
	private static final String MESSAGE_NO_TASK_IN_LIST = "There is no task in the task list.";
	private static final String MESSAGE_NO_TASK_MEET_REQUIREMENTS = "No task meet requirements.";
	private static final String MESSAGE_RELOADING_FILE = "reloading file from last saved point: %s\n";
	private static final String MESSAGE_HISTORY_FILE_NOT_EXIST = "HistoryFile not exist.\n";
	private static final String MESSAGE_TASK_LIST_FILE_NOT_EXIST = "TaskListFile not exist.\n";

	private static final String ERROR_INVALID_INDICATOR = "The update indicator '%s' is invalid.\n";
	private static final String ERROR_NULL_OBJECT = "Null Object.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "taskId out of range. taskId : %d\n";
	private static final String ERROR_NULL_TASK_STRING = "Task String is null.";
	private static final String ERROR_INVALID_INPUT_TIME = "The input time is invalid.";

	// this is the two list of tasks.
	private static List mainTaskList = new List("Main task List");
	private static List doneTaskList = new List("History task List");
	private static Integer taskId = -1; // Unique taskId start from 0.

	// these are for display to user.
	public static List displayTaskList = new List();
	public static boolean[] passStartTimeList = {};
	public static boolean[] passEndTimeList = {};

	// the file that used to save current tasks when user exit the program.
	private static String fileName = "taskList.txt";
	private static FileInOut fileProcesser = new FileInOut(fileName);

	// these three are for recording current information in the file.
	private static DateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm a");
	private static String taskStringFormat = "%s-%s-%s-%s-%s-%s\n";
	private static DateFormat taskDateFormat = new SimpleDateFormat(
			"dd-MM-yyyy");
	private static String messageStringInFile = "User saved at %s.\n";

	/**
	 * Add a task in the mainTaskList, set the display list to be mainTaskList.
	 * 
	 * @param Task
	 * @throws Exception
	 */

	public static boolean add(Task task) throws Exception {
		if (task == null) {
			throw new Exception(StringFormat.STR_ERROR_NULL_TASK_OBJECT);
		}
		if (task.getTaskName().equals("")) {
			throw new Exception(StringFormat.STR_ERROR_NO_TASK_NAME);
		}

		mainTaskList.addTask(task);
		setDisplayList(mainTaskList);

		LOGGER.info("==============\n" + "Storage : Add \n"
				+ "	Add a new task " + "\n" + "	task id : " + task.getTaskId()
				+ "\n" + "====================\n");

		return true;
	}

	/**
	 * Delete a task from taskList, and move it to history. Invalid taskId will
	 * throw NullPointerException;
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static boolean delete(int index) throws Exception {
		if (index < 0 || index >= displayTaskList.size()) {
			throw new Exception(String.format(
					StringFormat.STR_ERROR_INVALID_TASK_INDEX, index));
		}
		Task targetTask = displayTaskList.getTaskByIndex(index);
		int targetTaskId = targetTask.getTaskId();

		if (mainTaskList.containsTaskId(targetTaskId)) {
			mainTaskList.deleteTaskById(targetTaskId);
		} else if (doneTaskList.containsTaskId(targetTaskId)) {
			doneTaskList.deleteTaskById(targetTaskId);
		} else { // not supposed to reach this line;
			throw new Exception(
					"No Task with same taskId in either mainTaskList nor History.");
		}
		displayTaskList.deleteTaskByIndex(index);

		setDisplayList(displayTaskList);

		LOGGER.info("==============\n" + "Storage : Delete \n"
				+ "	Delete a task. " + "Task index : " + index + "\n"
				+ "   current Task size. " + "\n" + "	displaytasklist size : "
				+ displayTaskList.size() + "\n" + "	maintasklist size : "
				+ mainTaskList.size() + "\n" + "====================\n");

		return true;
	}

	/**
	 * Move the targetTask from mainTaskList to historyTaskList.
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */

	public static boolean done(int index) throws Exception {
		if (index < 0 || index >= displayTaskList.size()) {
			throw new Exception(String.format(
					StringFormat.STR_ERROR_INVALID_TASK_INDEX, index));
		}

		Task targetTask = displayTaskList.getTaskByIndex(index);
		int targetTaskId = targetTask.getTaskId();

		mainTaskList.deleteTaskById(targetTaskId);
		displayTaskList.deleteTaskByIndex(index);

		doneTaskList.addTask(targetTask);
		return true;
	}

	/**
	 * Update a task's certain attributes
	 * 
	 * @param taskId
	 * @param updateIndicator
	 * @param updateKeyValue
	 * @return
	 * @throws Exception
	 */

	public static boolean update(int index, String updateIndicator,
			String updateKeyValue) throws Exception {

		if (index <= 0 || index > displayTaskList.size()) {
			throw new Exception(String.format(
					StringFormat.STR_ERROR_INVALID_TASK_INDEX, index));
		}
		// remove the old task in both display list and main list.
		Task targetTask = displayTaskList.getTaskByIndex(index);
		int targetTaskId = targetTask.getTaskId();
		mainTaskList.deleteTaskById(targetTaskId);
		// update the old task.
		update(targetTask, updateIndicator, updateKeyValue);
		// add the new task back.
		displayTaskList.setTask(index, targetTask);
		mainTaskList.addTask(targetTask);

		setDisplayList(displayTaskList);
		return true;
	}

	/**
	 * Update a task Object with specified indicator and value.
	 * 
	 * @param targetTask
	 * @param updateIndicator
	 * @param updateKeyValue
	 * @throws Exception
	 */
	private static void update(Task targetTask, String updateIndicator,
			String updateKeyValue) throws Exception {
		switch (updateIndicator) {
		case StringFormat.NAME:
			targetTask.setTaskName(updateKeyValue);
			break;
		case StringFormat.DESCRIPTION:
			targetTask.setTaskDescription(updateKeyValue);
			break;
		case StringFormat.START:
			Date newStartDateTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setStartDateTime(newStartDateTime);
			break;
		case StringFormat.END:
			Date newEndDateTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndDateTime(newEndDateTime);
			break;
		case StringFormat.START_DATE:
			Date newStartDate = new Date(Long.parseLong(updateKeyValue));
			targetTask.setStartDate(newStartDate);
			break;
		case StringFormat.START_TIME:
			Date newStartTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setStartTime(newStartTime);
			break;
		case StringFormat.END_DATE:
			Date newEndDate = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndDate(newEndDate);
			break;
		case StringFormat.END_TIME:
			Date newEndTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndTime(newEndTime);
			break;
		case StringFormat.LOCATION:
			targetTask.setTaskLocation(updateKeyValue);
			break;
		case StringFormat.PRIORITY:
			targetTask.setTaskPriority(updateKeyValue);
			break;

		default:
			assert false : updateIndicator;
			throw new Exception(String.format(ERROR_INVALID_INDICATOR,
					updateIndicator));

		}
	}

	/**
	 * Clean all the tasks inside mainTaskList and doneTaskList.
	 * 
	 * @return
	 */
	public static boolean clean() {
		clean(mainTaskList);
		clean(doneTaskList);

		return true;
	}

	/**
	 * Clean tasks inside taskList with targetListName.
	 * 
	 * @param targetListName
	 * @return
	 * @throws Exception
	 */
	public static boolean clean(String targetListName) throws Exception {
		switch (targetListName) {
		case StringFormat.MAIN_TASK_LIST:
			clean(mainTaskList);
			break;
		case StringFormat.DONE_TASK_LIST:
			clean(doneTaskList);
			break;
		default:
			throw new Exception(String.format(
					StringFormat.STR_ERROR_INVALID_TASK_LIST_INDICATOR,
					targetListName));
		}

		return clean(displayTaskList);
	}

	/**
	 * Clear tasks inside target TaskList.
	 * 
	 * @param targetList
	 * @return
	 */
	private static boolean clean(List targetList) {
		targetList.clean();
		setDisplayList(displayTaskList);

		return true;
	}

	/**
	 * Set displayList to be task list indicate by targetListIndicator.
	 * 
	 * @param targetListIndicator
	 * @return
	 * @throws Exception
	 */

	public static boolean display(String targetListIndicator) throws Exception {
		switch (targetListIndicator) {
		case StringFormat.MAIN_TASK_LIST:
			setDisplayList(mainTaskList);
			break;
		case StringFormat.DONE_TASK_LIST:
			setDisplayList(doneTaskList);
			break;
		default:
			throw new Exception(String.format(
					StringFormat.STR_ERROR_INVALID_TASK_LIST_INDICATOR,
					targetListIndicator));
		}
		return true;
	}

	/**
	 * Sort the task in taskList corresponding to parameter key. if the key is
	 * not valid, tasks are sorted by name(default);
	 * 
	 * @return
	 * @throws Exception
	 */

	public static boolean sort(String key) throws Exception {
		return sort(key, displayTaskList);
	}

	private static boolean sort(String key, List targetList) throws Exception {
		Task.setSortKey(key);
		targetList.sortList();

		setDisplayList(displayTaskList);

		return true;
	}

	/**
	 * Search the task in taskList corresponding to parameter key.
	 * 
	 * @return
	 * @throws Exception
	 */

	public static boolean search(String indicator, String searchValue)
			throws Exception {
		return search(displayTaskList, indicator, searchValue);
	}

	private static boolean search(List targetList, String indicator,
			String searchValue) throws Exception {
		List newList = new List();
		for (int index = 0; index < targetList.size(); index++) {
			Task currTask = targetList.getTaskByIndex(index);
			if (currTask.get(indicator).toLowerCase()
					.contains(searchValue.toLowerCase())) {
				newList.addTask(currTask);
			}
		}

		setDisplayList(newList);

		return true;
	}

	/**
	 * Check which task is passed the start and end time. Task that pass
	 * start(or end) time will be recorded in boolean array.
	 */
	private static void checkTime() {
		checkTime(displayTaskList);
	}

	private static void checkTime(List targetList) {
		passStartTimeList = new boolean[targetList.size()];
		passEndTimeList = new boolean[targetList.size()];

		for (int index = 0; index < targetList.size(); index++) {
			Task currTask = targetList.getTaskByIndex(index);
			Date currStartTime = currTask.getStartDateTime();
			Date currEndTime = currTask.getEndDateTime();
			Date currTime = new Date(System.currentTimeMillis());

			if (currStartTime == null) {
				continue;
			} else if (currTime.after(currStartTime)) {
				passStartTimeList[index] = true;
			}
			if (currEndTime == null) {
				continue;
			} else if (currTime.after(currEndTime)) {
				passEndTimeList[index] = true;
			}
		}
	}

	/**
	 * Get the Pass Start Time indicator array of displayTaskList.
	 * 
	 * @return
	 */
	public static boolean[] getPassStartTimeList() {
		checkTime();
		return passStartTimeList;
	}

	/**
	 * Get the Pass Start Time indicator array of targetList.
	 * 
	 * @param targetList
	 * @return
	 */
	public static boolean[] getPassStartTimeList(List targetList) {
		checkTime(targetList);
		return passStartTimeList;
	}

	/**
	 * Get the Pass End Time indicator array of displayTaskList.
	 * 
	 * @return
	 */
	public static boolean[] getPassEndTimeList() {
		checkTime();
		return passEndTimeList;
	}

	/**
	 * Get the Pass End Time indicator array of targetList.
	 * 
	 * @param targetList
	 * @return
	 */
	public static boolean[] getPassEndTimeList(List targetList) {
		checkTime(targetList);
		return passEndTimeList;
	}

	/**
	 * get the string format of all tasks in taskList and store in arrayList.
	 * 
	 * @return
	 */
	public static ArrayList<String> getStringFormatOfList() {
		return getStringFormatOfList(displayTaskList);
	}

	private static ArrayList<String> getStringFormatOfList(List targetList) {
		ArrayList<String> resultList = new ArrayList<String>();

		for (int index = 0; index < targetList.size(); index++) {
			Task currTask = targetList.getTaskByIndex(index);
			resultList.add(currTask.toString());
		}
		LOGGER.info("==============\n" + "Storage : getDisplayList \n"
				+ "	resultList size " + resultList.size() + "\n"
				+ "====================\n");

		return resultList;
	}

	/**
	 * Save the mainTaskList to .txt file.
	 * 
	 * @throws IOException
	 */

	public static void saveFile() throws Exception {
		fileProcesser.saveTaskList(mainTaskList, fileName);
	}

	/**
	 * reload mainTaskList from .txt file
	 * 
	 * @throws Exception
	 */

	public static void reloadFile() throws Exception {
		mainTaskList.clean();
		mainTaskList = fileProcesser.readTaskList();
		setDisplayList(mainTaskList);
	}

	private static void setDisplayList(List targetList) {
		displayTaskList = targetList.copy();
		checkTime();
	}

	/**
	 * Return a unique taskId for new Task. And reset TaskId is necessary.
	 * 
	 * @return
	 */
	public static int obtainNewTaskId() {
		if (taskId == Integer.MAX_VALUE){
			resetTaskId();
		}
		taskId++;
		
		return taskId;
	}

	/**
	 * Get the size of displayTaskList.
	 * 
	 * @return
	 */
	public static int getDisplayTaskListSize() {
		return displayTaskList.size();
	}

	/**
	 * There will become a lot unused TaskId after a lot of delete action. If
	 * Integer TaskId is out of range when create a new Task, this method will
	 * reset the TaskId to fill the empty holes.
	 */
	private static void resetTaskId() {
		taskId = -1; // set the start taskId to be 0;
		for (int index = 0; index < doneTaskList.size(); index++) {
			int currId = obtainNewTaskId();
			Task currTask = doneTaskList.getTaskByIndex(index);
			currTask.setTaskId(currId);
		}
		for (int index = 0; index < mainTaskList.size(); index++) {
			int currId = obtainNewTaskId();
			Task currTask = mainTaskList.getTaskByIndex(index);
			currTask.setTaskId(currId);
		}
	}

}
