//package V1;

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
	private static final String ERROR_INVALID_TASKID = "taskId out of range. taskId : %d\n";
	private static final String ERROR_INVALID_TASK_RECORD = "Invalid task record: %s\n";
	private static final String ERROR_NULL_TASK_STRING = "Task String is null.";
	private static final String ERROR_INVALID_INPUT_TIME = "The input time is invalid.";

	// this is the two list of tasks.
	private static ArrayList<Task> list = new ArrayList<Task>();
	private static ArrayList<Task> history = new ArrayList<Task>();

	// these are for display to user.
	private static ArrayList<Task> displayTaskList= new ArrayList<Task>();
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

	public static boolean add(Task task) throws Exception {
		if (task == null) {
			throw new Exception(ERROR_NULL_OBJECT);
		}

		LOGGER.info("==============\n" + "Storage add task: \n" + "task name: "
				+ task.getTaskName() + "\n" + "task description: "
				+ task.getTaskDescription() + "\n" + "task location: "
				+ task.getTaskLocation() + "\n" + "task start timing: "
				+ task.getStartDateTime() + "\n" + "task end timing: "
				+ task.getEndDateTime() + "\n" + "task priority: " 
				+ task.getTaskPriority() + "\n"
				+ "====================\n");

		list.add(task);
		setDisplayList(list);

		return true;
	}

	/**
	 * Delete a task from taskList, and move it to history. Invalid taskId will
	 * throw NullPointerException;
	 * 
	 * @param taskId
	 * @return
	 * @throws Exception 
	 */
	public static boolean delete (int taskId) throws Exception{
		return delete(displayTaskList, taskId);
	} 

	public static boolean delete(ArrayList<Task> list, int taskId) throws Exception {
		if (taskId <= 0 || taskId > getListSize(list)) {
			throw new Exception(String.format(ERROR_INVALID_TASKID, taskId));
		}

		Task removedTask = list.remove(taskId - 1);
		list.remove(removedTask);
		
		LOGGER.info("==============\n" + "Storage delete task. \n "
				+ "taskId: " + taskId + "\n" + "task name: "
				+ removedTask.getTaskName() + "\n" + "task start timing: "
				+ removedTask.getStartDateTime() + "\n" + "task end timing: "
				+ removedTask.getEndDateTime() + "\n"
				+ "task description: " + removedTask.getTaskDescription()
				+ "\n" + "task location: " + removedTask.getTaskLocation()
				+ "\n" + "task priority: " + removedTask.getTaskPriority()
				+ "\n" + "====================\n");

		
		history.add(removedTask);
		setDisplayList(displayTaskList);

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

	public static boolean update(int taskId, String updateIndicator,
			String updateKeyValue) throws Exception {

		if (taskId <= 0 || taskId > getListSize(displayTaskList)) {
			throw new Exception(String.format(ERROR_INVALID_TASKID, taskId));
		}
		
		Task targetTask = get(displayTaskList, taskId);
		list.remove(targetTask);

		switch (updateIndicator) {
		case StringFormat.NAME:
			targetTask.setTaskName(updateKeyValue);
			break;
		case StringFormat.DESCRIPTION:
			targetTask.setTaskDescription(updateKeyValue);
			break;
		case StringFormat.START:
			Date newStartDateTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setStartDateTime(newStartDateTime);;
			break;
		case StringFormat.END:
			Date newEndDateTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndDateTime(newEndDateTime);;
			break;
		case StringFormat.START_DATE:
			Date newStartDate = new Date(Long.parseLong(updateKeyValue));	
			targetTask.setStartDate(newStartDate);
			break;
		case StringFormat.START_TIME:
			Date newStartTime = new Date (Long.parseLong(updateKeyValue));
			targetTask.setStartTime(newStartTime);
			break;
		case StringFormat.END_DATE:
			Date newEndDate = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndDate(newEndDate);;
			break;
		case StringFormat.END_TIME:
			Date newEndTime = new Date(Long.parseLong(updateKeyValue));
			targetTask.setEndTime(newEndTime);;
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

		LOGGER.info("==============\n" + "Storage update task. taskId: "
				+ taskId + "\n" + "task name: " + targetTask.getTaskName()
				+ "\n" + "task indicator: " + updateIndicator + "\n"
				+ "====================\n");

		displayTaskList.set(taskId - 1, targetTask);
		list.add(targetTask);
		
		setDisplayList(displayTaskList);
		
		return true;
	}

	/**
	 * Get a task in the taskList by taskId
	 * 
	 * @param taskId
	 * @return
	 * @throws Exception
	 */

	public static Task get(ArrayList<Task> list, int taskId) throws Exception {
		if (taskId <= 0 || taskId > getListSize(list)) {
			throw new Exception(String.format(ERROR_INVALID_TASKID, taskId));
		}
		Task task = list.get(taskId - 1);

		LOGGER.info("==============\n" + "Storage get task. \n " + "taskId: "
				+ taskId + "\n" + "task name: " + task.getTaskName() + "\n"
				+ "task start timing: "
				+ task.getStartDateTime() + "\n"
				+ "task end timing: "
				+ task.getEndDateTime() + "\n"
				+ "task description: " + task.getTaskDescription() + "\n"
				+ "task location: " + task.getTaskLocation() + "\n"
				+ "task priority: " + task.getTaskPriority() + "\n"
				+ "====================\n");

		return task;
	}

	/**
	 * Clean all the task Objects in the taskList; Put all the tasks into
	 * history
	 * 
	 * @return
	 */
	public static boolean clean() {
		if (!isEmpty()) {
			for (int itemId = 0; itemId < list.size(); itemId++) {
				history.add(list.get(itemId));
			}
			list.clear();
		}
		
		LOGGER.info("==============\n" + "Storage clean taskList. \n "
				+ "====================\n");
		setDisplayList(list);
		
		return true;
	}

	// only for test.
	public static void cleanUpEveryThing() {
		history.clear();
		list.clear();
		setDisplayList(list);
	}
	
	public static void display(){
		setDisplayList(list);
	}

	/**
	 * Sort the task in taskList corresponding to parameter key. if the key is
	 * not valid, tasks are sorted by name;
	 * 
	 * @return
	 * @throws Exception
	 */

	public static boolean sort(String key) throws Exception {

		LOGGER.info("==============\n" + "Storage sort taskList. \n "
				+ "====================\n");

		return sort(key, displayTaskList);
	}
	
	public static boolean sort(String key, ArrayList<Task> list) throws Exception{
		if (list.isEmpty()) {
			throw new Exception(MESSAGE_NO_TASK_IN_LIST);
		}
		
		Task.setSortKey(key);
		Collections.sort(list);
		
		LOGGER.info("==============\n" + "Storage sort taskList. \n "
				+ "====================\n");
		setDisplayList(list);
		
		return true;
	}

	/**
	 * Search the task in taskList corresponding to parameter key.
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public static ArrayList<Task> search(String indicator, String searchValue) throws Exception{
		return search(displayTaskList, indicator, searchValue);
	}
	
	public static ArrayList<Task> search(ArrayList<Task> list, String indicator, String searchValue)
			throws Exception {
		ArrayList<Task> resultTaskList = new ArrayList<Task>();
		
		if (getListSize(list) == 0) {
			throw new Exception(MESSAGE_NO_TASK_IN_LIST);
		}

		for (int index = 0; index < getListSize(list); index++) {
			Task task = list.get(index);
			String taskAttriString = task.get(indicator);
			
			if (taskAttriString.toLowerCase().contains(searchValue.toLowerCase())){
				resultTaskList.add(task);
			}
		}

		if (resultTaskList.size() == 0) {
			throw new Exception(MESSAGE_NO_TASK_MEET_REQUIREMENTS);
		}

		LOGGER.info("==============\n" + "Storage search. \n "
				+ "====================\n");
		
		setDisplayList(resultTaskList);
		
		return resultTaskList;
	}

	/**
	 * Check which task is passed the start and end time. Create boolean array
	 * to record these passed task.
	 */
	private static void checkTime() {
		checkTime(displayTaskList);
	}

	private static void checkTime(ArrayList<Task> list) {
		// create boolean instance based on the size of the taskList
		passStartTimeList = new boolean[list.size()];
		passEndTimeList = new boolean[list.size()];

		for (int index = 0; index < getListSize(list); index++) {
			Task currTask = list.get(index);
			Date currStartTime = currTask.getStartDateTime();
			Date currEndTime = currTask.getEndDateTime();
			Date currTime = new Date(System.currentTimeMillis());

			if (currTime.after(currStartTime)){
				passStartTimeList[index] = true;
			}
			if (currTime.after(currEndTime)){
				passEndTimeList[index] = true;
			}
		}
	}

	public static boolean[] getPassStartTimeList() {
		checkTime();
		return passStartTimeList;
	}

	public static boolean[] getPassStartTimeList(ArrayList<Task> list) {
		checkTime(list);
		return passStartTimeList;
	}

	public static boolean[] getPassEndTimeList() {
		checkTime();
		return passEndTimeList;
	}

	public static boolean[] getPassEndTimeList(ArrayList<Task> list) {
		checkTime(list);
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
	
	public static ArrayList<String> getStringFormatOfList(ArrayList<Task> list){
		ArrayList<String> resultList = new ArrayList<String>();
		
		for (int index = 0; index < getListSize(list); index++){
			Task task = list.get(index);
			resultList.add(taskListFileName.toString());
		}
		
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

		assert taskListFile.canWrite() : "taskListFile cannot write.";
		assert historyFile.canWrite() : "historyFile cannot write.";

		Date date = new Date();
		String dateString = format.format(date);
		taskListWriter.write(String.format(messageStringInFile, dateString));
		historyWriter.write(String.format(messageStringInFile, dateString));

		for (int i = 0; i < list.size(); i++) {
			String str = convertTaskToString(list.get(i));
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

		if (!isEmpty()) {
			cleanUpEveryThing();
		}

		String taskString = taskListBufferedReader.readLine();
		System.out.println(String.format(MESSAGE_RELOADING_FILE, taskString));

		taskString = taskListBufferedReader.readLine();
		while (taskString != null) {
			Task task = convertStringToTask(taskString);
			list.add(task);
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
		
		displayTaskList = list;

		return;
	}

	/**
	 * Convert a dateFormatString to Date Object.
	 * 
	 * @param d
	 * @return
	 */
	
	public static int getListSize(ArrayList list){
		return list.size();
	}

	public static boolean isEmpty() {
		return list.isEmpty();
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
				task.getLongFormatStartDateTimeString(), task.getLongFormatEndDateTimeString(),
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

	private static void setDisplayList(ArrayList list) {
		displayTaskList = list;
		checkTime();
	}

}
