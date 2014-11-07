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
	private static final String ERROR_INVALID_TASK_RECORD = "Invalid task record: %s\n";
	private static final String ERROR_NULL_TASK_STRING = "Task String is null.";
	private static final String ERROR_INVALID_INPUT_TIME = "The input time is invalid.";

	// this is the two list of tasks.
	private static List mainTaskList = new List();
	private static ArrayList<Task> history = new ArrayList<Task>();

	// these are for display to user.
	public static List displayTaskList = new List();
	public static boolean[] passStartTimeList = {};
	public static boolean[] passEndTimeList = {};

	// the file that used to save current tasks when user exit the program.
	private static String taskListFileName = "TaskList.txt";
	private static String historyFileName = "Histroy.txt";

	// these are for reading the user saved file.
	private static FileReader taskListFileReader;
	private static BufferedReader taskListBufferedReader;
	private static FileReader historyFileReader;
	private static BufferedReader historyBufferedReader;

	// these are for writing file.
	private static FileWriter taskListWriter;
	private static FileWriter historyWriter;

	// these three are for recording current information in the file.
	private static DateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm a");
	private static String taskStringFormat = "%s-%s-%s-%s-%s-%s\n";
	private static DateFormat taskDateFormat = new SimpleDateFormat(
			"dd-MM-yyyy");
	private static String messageStringInFile = "User saved at %s.\n";

	/**
	 * add() method add in task passed by Executor.
	 * 
	 * @param Task
	 * @throws Exception
	 */

	public static boolean add(Task task){
		
		mainTaskList.addTask(task);
		
		setDisplayList(mainTaskList);
		LOGGER.info("==============\n" +
				"Storage : Add \n" + 
				"	Add a new task " + "\n" +
				"	task id : " + task.getTaskId() + "\n" + 
				"====================\n");
		
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
		if (index<0 || index >= displayTaskList.size()){
			throw new Exception(String.format(ERROR_INVALID_TASK_INDEX, index));
		}
		Task targetTask = displayTaskList.getTaskByIndex(index);
		int targetTaskId = targetTask.getTaskId();
		
		mainTaskList.deleteTaskById(targetTaskId);
		displayTaskList.deleteTaskByIndex(index);
		
		setDisplayList(displayTaskList);
		
		LOGGER.info("==============\n" +
				"Storage : Delete \n" + 
				"	Delete a task. " + "Task index : " + index + "\n" +
				"   current Task size. " + "\n" + 
				"	displaytasklist size : " + displayTaskList.size() + "\n" + 
				"	maintasklist size : " + mainTaskList.size() + "\n" +
 				"====================\n");
		
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

	public static boolean update(int index, String updateIndicator, String updateKeyValue) throws Exception {
		
		if (index <= 0 || index > displayTaskList.size()) {
			throw new Exception(String.format(ERROR_INVALID_TASK_INDEX, index));
		}
		
		index--;
		Task targetTask = displayTaskList.getTaskByIndex(index);
		int targetTaskId = targetTask.getTaskId();
		mainTaskList.deleteTaskById(targetTaskId);
		
		update(targetTask, updateIndicator, updateKeyValue);

		displayTaskList.setTask(index - 1, targetTask);
		mainTaskList.addTask(targetTask);
		
		setDisplayList(displayTaskList);
		return true;
	}

	private static void update(Task targetTask, String updateIndicator, String updateKeyValue) throws Exception {
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
			;
			break;
		case StringFormat.END:
			Date newEndDateTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndDateTime(newEndDateTime);
			;
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
			;
			break;
		case StringFormat.END_TIME:
			Date newEndTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndTime(newEndTime);
			;
			break;
		case StringFormat.LOCATION:
			System.out.println("FIND");

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
	 * Clean all the task Objects in the taskList; Put all the tasks into
	 * history
	 * 
	 * @return
	 */
	public static boolean clean() {
		return clean(mainTaskList);
	}
	
	public static boolean clean(List targetList){
		targetList.clean();
		setDisplayList(mainTaskList);
		return true;
	}
	
	public static void display() {
		setDisplayList(mainTaskList);
	}

	/**
	 * Sort the task in taskList corresponding to parameter key. if the key is
	 * not valid, tasks are sorted by name;
	 * 
	 * @return
	 * @throws Exception
	 */

	public static boolean sort(String key) throws Exception {
		return sort(key, displayTaskList);
	}

	public static boolean sort(String key, List targetList) throws Exception {

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

	public static boolean search(String indicator, String searchValue) throws Exception {
		return search(displayTaskList, indicator, searchValue);
	}

	public static boolean search(List targetList, String indicator, String searchValue) throws Exception {
		List newList = new List();
		for (int index=0; index<targetList.size(); index++){
			Task currTask = targetList.getTaskByIndex(index);
			if (currTask.get(indicator).toLowerCase().contains(searchValue.toLowerCase())){
				newList.addTask(currTask);
			}
		}
		
		setDisplayList(newList);

		return true;
	}

	/**
	 * Check which task is passed the start and end time. Create boolean array
	 * to record these passed task.
	 */
	private static void checkTime() {
		checkTime(displayTaskList);
	}

	private static void checkTime(List targetList) {
		// create boolean instance based on the size of the taskList
		passStartTimeList = new boolean[targetList.size()];
		passEndTimeList = new boolean[targetList.size()];

		for (int index = 0; index < targetList.size(); index++) {
			Task currTask = targetList.getTaskByIndex(index);
			Date currStartTime = currTask.getStartDateTime();
			Date currEndTime = currTask.getEndDateTime();
			Date currTime = new Date(System.currentTimeMillis());

			if (currStartTime == null || currEndTime == null) {
				continue;
			}
			if (currTime.after(currStartTime)) {
				passStartTimeList[index] = true;
			}
			if (currTime.after(currEndTime)) {
				passEndTimeList[index] = true;
			}
		}
	}

	public static boolean[] getPassStartTimeList() {
		checkTime();
		return passStartTimeList;
	}

	public static boolean[] getPassStartTimeList(List targetList) {
		checkTime(targetList);
		return passStartTimeList;
	}

	public static boolean[] getPassEndTimeList() {
		checkTime();
		return passEndTimeList;
	}

	public static boolean[] getPassEndTimeList(List targetList) {
		checkTime(targetList);
		return passEndTimeList;
	}

	/**
	 * get the string format of all tasks in taskList and store in arrayList.
	 * 
	 * @return
	 */
	public static ArrayList<String> getStringFormatOfList(){
		return getStringFormatOfList(displayTaskList);
	}

	public static ArrayList<String> getStringFormatOfList(List targetList) {
		ArrayList<String> resultList = new ArrayList<String>();
		
		for (int index = 0; index < targetList.size(); index++) {
			Task currTask = targetList.getTaskByIndex(index);
			resultList.add(currTask.toString());
		}
		LOGGER.info("==============\n" +
				"Storage : getDisplayList \n" + 
				"	resultList size " + resultList.size() + "\n" +
				"====================\n");
		
		return resultList;
	}

	/**
	 * SaveFile will save all the tasks in History and taskList into two files.
	 * 
	 * @throws IOException
	 */

	public static void saveFile() throws Exception {

		File taskListFile = new File(taskListFileName);
		File historyFile = new File(historyFileName);
		
		if (!taskListFile.exists()) {
			taskListFile.createNewFile();
		}
		if (!historyFile.exists()) {
			historyFile.createNewFile();
		}

		taskListWriter = new FileWriter(taskListFile);
		historyWriter = new FileWriter(historyFile);

		Date date = new Date();
		String dateString = format.format(date);
		taskListWriter.write(String.format(messageStringInFile, dateString));
		historyWriter.write(String.format(messageStringInFile, dateString));

		for (int i = 0; i < mainTaskList.size(); i++) {
			String str = convertTaskToString(mainTaskList.getTaskByIndex(i));
			taskListWriter.write(str);
		}
		for (int i = 0; i < history.size(); i++) {
			String str = convertTaskToString(history.get(i));
			historyWriter.write(str);
		}

		taskListWriter.close();
		historyWriter.close();

		return;
	}

	/**
	 * reloadFile will reload task to the arrayList from two file.
	 * 
	 * @throws Exception
	 */

	public static void reloadFile() throws Exception {
		File taskListFile = new File(taskListFileName);
		File historyFile = new File(historyFileName);

		if (!taskListFile.exists()) {
			throw new Exception(MESSAGE_TASK_LIST_FILE_NOT_EXIST);
		}
		if (!historyFile.exists()) {
			throw new Exception(MESSAGE_HISTORY_FILE_NOT_EXIST);
		}

		taskListFileReader = new FileReader(taskListFile);
		taskListBufferedReader = new BufferedReader(taskListFileReader);

		historyFileReader = new FileReader(historyFile);
		historyBufferedReader = new BufferedReader(historyFileReader);

		if (!(mainTaskList.size() == 0)) {
			clean();
		}

		String taskString = taskListBufferedReader.readLine();
		System.out.println(String.format(MESSAGE_RELOADING_FILE, taskString));

		taskString = taskListBufferedReader.readLine();
		while (taskString != null) {
			Task task = convertStringToTask(taskString);
			mainTaskList.addTask(task);
			taskString = taskListBufferedReader.readLine();
		}

		taskString = historyBufferedReader.readLine();
		System.out.println(String.format(MESSAGE_RELOADING_FILE, taskString));

		taskString = historyBufferedReader.readLine();
		while (taskString != null) {
			Task task = convertStringToTask(taskString);
			history.add(task);
			taskString = historyBufferedReader.readLine();
		}

		displayTaskList = mainTaskList;

		return;
	}

	/**
	 * Conversion between String and Task Object. When converting back, must
	 * create a task Object, and use this object to convert. Thus, all the
	 * information will be filled into this object.
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String convertTaskToString(Task task) throws Exception {
		if (task == null) {
			throw new Exception(ERROR_NULL_OBJECT);
		}
		String result = String.format(taskStringFormat, task.getTaskName(),
				task.getLongFormatStartDateTimeString(),
				task.getLongFormatEndDateTimeString(),
				task.getTaskDescription() + " ", task.getTaskLocation() + " ",
				task.getTaskPriority() + " ");

		return result;
	}

	private static Task convertStringToTask(String taskString) throws Exception {
		Task task = new Task();

		if (taskString == null) {
			throw new Exception(ERROR_NULL_TASK_STRING);
		} else {
			String[] taskAttributes = taskString.split("-");
			if (taskAttributes.length != 6) {
				System.out.println(taskString);
				throw new Exception(String.format(ERROR_INVALID_TASK_RECORD,
						taskString));
			} else {
				task.setTaskName(taskAttributes[0]);
				task.setStartDate(new Date(Long.parseLong(taskAttributes[1])));
				task.setEndDateTime(new Date(Long.parseLong(taskAttributes[2])));
				task.setTaskDescription(taskAttributes[3]);
				task.setTaskLocation(taskAttributes[4]);
				task.setTaskPriority(taskAttributes[5]);
			}
		}
		return task;
	}

	public static void setDisplayList(List targetList) {
		displayTaskList = targetList.copy();
		checkTime();
	}
	
	public static int obtainNewTaskId(){
		return mainTaskList.size();
	}

}
