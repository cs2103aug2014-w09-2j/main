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
	private static final String ERROR_INVALID_SORT_KEY = "Invalid Sort Key. Key: %s\n";
	private static final String ERROR_INVALID_TASK_RECORD = "Invalid task record: %s\n";
	private static final String ERROR_NULL_TASK_STRING = "Task String is null.";

	// this is the two list of tasks.
	private static ArrayList<Task> taskList = new ArrayList<Task>();
	private static ArrayList<Task> history = new ArrayList<Task>();
 	public static int numberOfTask = 0;

	// the timer is used to track the expired date of task.
	public static Timer timer = new Timer();

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
			"yyyy-MM-dd HH:mm");
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

	public static boolean add(Task t) throws Exception {
		if (t == null) {
			throw new Exception(ERROR_NULL_OBJECT);
		}
		
		/*
		assert !t.getTaskName().equals("") : "No task name.";
		assert t.getTaskStartTime() < System.currentTimeMillis() : "Invalid taskStartTime. start time: "
				+ t.getTaskStartTime();
		assert t.getTaskEndTime() < System.currentTimeMillis() : "Invalid taskEndTime. end time: "
				+ t.getTaskEndTime();
		assert t.getTaskEndTime() < t.getTaskStartTime() : "Invalid startTime and endTime. startTime: "
				+ t.getTaskStartTime() + " endTime: " + t.getTaskEndTime();
		assert !t.getTaskDescription().equals("") : "No task description.";
		assert !t.getTaskLocation().equals("") : "No task location.";
		assert !t.getTaskPriority().equals("") : "No task priority.";
		*/
		
		LOGGER.info("==============\n" + "Storage add task: \n" + "task name: "
				+ t.getTaskName() + "\n" + "task description: "
				+ t.getTaskDescription() + "\n" + "task location: "
				+ t.getTaskLocation() + "\n" + "task start time: "
				+ convertLongToDateFormat(t.getTaskStartTime()) + "\n" + "task end time: "
				+ convertLongToDateFormat(t.getTaskEndTime()) + "\n" + "task priority: "
				+ t.getTaskPriority() + "\n" + "====================\n");

		taskList.add(t);
		// timer.schedule(t, t.getTime());
		numberOfTask++;

		return true;
	}

	/**
	 * Delete a task from taskList, and move it to history. Invalid taskId will
	 * throw NullPointerException;
	 * 
	 * @param taskId
	 * @return
	 */

	public static boolean delete(int taskId) throws Exception {
		if (taskId <= 0 || taskId > getTaskListSize()) {
			throw new Exception(String.format(ERROR_INVALID_TASKID, taskId));
		}

		Task removedTask = taskList.remove(taskId - 1);

		LOGGER.info("==============\n" + "Storage delete task. \n "
				+ "taskId: " + taskId + "\n" + "task name: "
				+ removedTask.getTaskName() + "\n" + "task start time: "
				+ convertLongToDateFormat(removedTask.getTaskStartTime()) + "\n" + "task end time: "
				+ convertLongToDateFormat(removedTask.getTaskEndTime()) + "\n" + "task description: "
				+ removedTask.getTaskDescription() + "\n" + "task location: "
				+ removedTask.getTaskLocation() + "\n" + "task priority: "
				+ removedTask.getTaskPriority() + "\n"
				+ "====================\n");

		// removedTask.cancel();
		numberOfTask--;
		history.add(removedTask);

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

		if (taskId <= 0 || taskId > getTaskListSize()) {
			throw new NullPointerException(String.format(ERROR_INVALID_TASKID,
					taskId));
		}

		Task targetTask = get(taskId);

		switch (updateIndicator) {
		case StringFormat.NAME:
			//assert updateKeyValue instanceof String : "name: " + updateKeyValue;

			targetTask.setTaskName(updateKeyValue);
			break;
		case StringFormat.DESCRIPTION:
			//assert updateKeyValue instanceof String : "description: " + updateKeyValue;

			targetTask.setTaskDescription(updateKeyValue);
			break;
		case StringFormat.START_TIMING:
			//assert updateKeyValue instanceof String : "start time: " + updateKeyValue;

			Long newStartTime = Long.parseLong(updateKeyValue);
			targetTask.setTaskStartTime(newStartTime);
			break;
		case StringFormat.END_TIMING:
			//assert updateKeyValue instanceof String : "end time: " + updateKeyValue;

			Long newEndTime = Long.parseLong(updateKeyValue);
			targetTask.setTaskEndTime(newEndTime);
			break;
		case StringFormat.START_DATE:
			Long newStartDateLong = Long.parseLong(updateKeyValue);
			Date newDatesd = new Date(newStartDateLong);
			Date oldTimesd = new Date(targetTask.getTaskStartTime());
			oldTimesd.setYear(newDatesd.getYear());
			oldTimesd.setMonth(newDatesd.getMonth());
			oldTimesd.setDate(newDatesd.getDate());
			targetTask.setTaskStartTime(oldTimesd.getTime());
			break;
		case StringFormat.START_TIME:
			Long newStartTimeLong = Long.parseLong(updateKeyValue);
			Date newTimest = new Date(newStartTimeLong);
			Date oldTimest = new Date(targetTask.getTaskStartTime());
			oldTimest.setHours(newTimest.getHours());
			oldTimest.setMinutes(newTimest.getMinutes());
			oldTimest.setSeconds(newTimest.getSeconds());
			targetTask.setTaskStartTime(oldTimest.getTime());
			break;
		case StringFormat.END_DATE:
			Long newEndDateLong = Long.parseLong(updateKeyValue);
			Date newDateed = new Date(newEndDateLong);
			Date oldTimeed = new Date(targetTask.getTaskStartTime());
			oldTimeed.setYear(newDateed.getYear());
			oldTimeed.setMonth(newDateed.getMonth());
			oldTimeed.setDate(newDateed.getDate());
			targetTask.setTaskStartTime(oldTimeed.getTime());
			break;
		case StringFormat.END_TIME:
			Long newEndTimeLong = Long.parseLong(updateKeyValue);
			Date newTimeet = new Date(newEndTimeLong);
			Date oldTimeet = new Date(targetTask.getTaskStartTime());
			oldTimeet.setHours(newTimeet.getHours());
			oldTimeet.setMinutes(newTimeet.getMinutes());
			oldTimeet.setSeconds(newTimeet.getSeconds());
			targetTask.setTaskStartTime(oldTimeet.getTime());
			break;
		case StringFormat.LOCATION:
			//assert updateKeyValue instanceof String : "location: " + updateKeyValue;

			targetTask.setTaskLocation(updateKeyValue);
			break;
		case StringFormat.PRIORITY:
			//assert updateKeyValue instanceof String : "priority: " + updateKeyValue;

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

		taskList.set(taskId - 1, targetTask);

		return true;
	}

	/**
	 * Get a task in the taskList by taskId
	 * 
	 * @param taskId
	 * @return
	 * @throws Exception
	 */

	public static Task get(int taskId) throws Exception {
		if (taskId <= 0 || taskId > getTaskListSize()) {
			throw new Exception(String.format(ERROR_INVALID_TASKID, taskId));
		}
		Task task = taskList.get(taskId - 1);
		
		LOGGER.info("==============\n" + "Storage get task. \n "
				+ "taskId: " + taskId + "\n" + "task name: "
				+ task.getTaskName() + "\n" + "task start time: "
				+ convertLongToDateFormat(task.getTaskStartTime()) + "\n" + "task end time: "
				+ convertLongToDateFormat(task.getTaskEndTime()) + "\n" + "task description: "
				+ task.getTaskDescription() + "\n" + "task location: "
				+ task.getTaskLocation() + "\n" + "task priority: "
				+ task.getTaskPriority() + "\n"
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
			for (int itemId = 0; itemId < taskList.size(); itemId++) {
				history.add(taskList.get(itemId));
			}
			taskList.clear();
		}
		assert taskList.isEmpty() : "Size of list :" + taskList.size();
		
		LOGGER.info("==============\n" + "Storage clean taskList. \n "
				+ "====================\n");
		return true;
	}

	// only for test.
	public static void cleanUpEveryThing() {
		history.clear();
		taskList.clear();
	}
	
	
	/**
	 * Sort the task in taskList corresponding to parameter key. if the key is
	 * not valid, tasks are sorted by name;
	 * 
	 * @return
	 * @throws Exception
	 */

	public static boolean sort(String key) throws Exception {
		String keyValueString = "name-description-start timing-end timing-location-priority";
		if (isEmpty()) {
			throw new Exception(MESSAGE_NO_TASK_IN_LIST);
		}
		if (!keyValueString.contains(key)) {
			throw new Exception(String.format(ERROR_INVALID_SORT_KEY, key));
		}
		Task.setSortKey(key);
		Collections.sort(taskList);
		
		LOGGER.info("==============\n" + "Storage sort taskList. \n "
				+ "====================\n");
		
		return true;
	}

	/**
	 * Search the task in taskList corresponding to parameter key.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> search(String indicator, String searchValue)
			throws Exception {
		String keyValueString = "name-description-start timing-end timing-location-priority";
		ArrayList<String> resultList = new ArrayList<String>();
		ArrayList<Task> requiredTaskList = new ArrayList<Task>();

		if (isEmpty()) {
			throw new Exception(MESSAGE_NO_TASK_IN_LIST);
		}
		if (!keyValueString.contains(indicator)) {
			throw new Exception(
					String.format(ERROR_INVALID_SORT_KEY, indicator));
		}

		for (int index = 0; index < taskList.size(); index++) {
			Task task = taskList.get(index);
			if (task.get(indicator).equals(searchValue)) {
				requiredTaskList.add(task);
			}
		}

		if (requiredTaskList.size() == 0) {
			throw new Exception(MESSAGE_NO_TASK_MEET_REQUIREMENTS);
		}

		resultList = getTaskList(requiredTaskList);
		return resultList;
	}

	/**
	 * get the string format of all tasks in taskList and store in arrayList.
	 * 
	 * @return
	 */
	public static ArrayList<String> getTaskList() {
		return getTaskList(taskList);
	}
	
	/**
	 * Return a list of tasks in String format.
	 * @param list
	 * @return
	 */

	private static ArrayList<String> getTaskList(ArrayList<Task> list) {
		ArrayList<String> displayList = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			String taskString = task.getTaskName();
			Long trivalDate = (long) 0;
			Long maxDate = Long.MAX_VALUE;

			// description
			if (!task.getTaskDescription().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskDescription());
			} else {
				taskString = taskString.concat("~ ");
			}

			// startTime
			if (!task.getTaskStartTime().equals(trivalDate) && !task.getTaskStartTime().equals(maxDate)) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(convertLongToDateFormat(
						task.getTaskStartTime()).toString());
			} else {
				taskString = taskString.concat("~ ");
			}

			// endTime
			if (!task.getTaskEndTime().equals(trivalDate) && !task.getTaskEndTime().equals(maxDate)) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(convertLongToDateFormat(
						task.getTaskEndTime()).toString());
			} else {
				taskString = taskString.concat("~ ");
			}

			// location
			if (!task.getTaskLocation().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskLocation());
			} else {
				taskString = taskString.concat("~ ");
			}

			// priority
			if (!task.getTaskPriority().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskPriority());
			} else {
				taskString = taskString.concat("~ ");
			}

			displayList.add(taskString);
		}

		return displayList;
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

		for (int i = 0; i < taskList.size(); i++) {
			String str = convertTaskToString(taskList.get(i));
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
			taskList.add(task);
			taskString = taskListBufferedReader.readLine();
		}
		
		taskString = historyBufferedReader.readLine();
		System.out.println(String.format(MESSAGE_RELOADING_FILE, taskString));
		
		taskString = historyBufferedReader.readLine();
		while(taskString != null){
			Task task = convertStringToTask(taskString);
			history.add(task);
			taskString = historyBufferedReader.readLine();
		}

		return;
	}

	/**
	 * Convert a dateFormatString to Date Object.
	 * 
	 * @param d
	 * @return
	 */

	public static int getTaskListSize() {
		return taskList.size();
	}

	public static int getHistorySize() {
		return history.size();
	}

	public static boolean isEmpty() {
		return taskList.isEmpty();
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
		String result = String.format(taskStringFormat, 
				task.getTaskName(),
				task.getTaskStartTime(), 
				task.getTaskEndTime(), 
				task.getTaskDescription(),
				task.getTaskLocation(), 
				task.getTaskPriority());
		
		return result;
	}

	private static Task convertStringToTask(String taskString) throws Exception {
		System.out.println(taskString);
		Task task = new Task();

		assert taskString.matches("(.*)-(.*)-(.*)-(.*)-(.*)") : taskString;
		
		if (taskString == null) {
			throw new Exception(ERROR_NULL_TASK_STRING);
		} else {
			String[] taskAttributes = taskString.split("-");
			if (taskAttributes.length != 6) {
				throw new Exception(String.format(ERROR_INVALID_TASK_RECORD,
						taskString));
			} else {
				task.setTaskName(taskAttributes[0]);
				task.setTaskStartTime(Long.parseLong(taskAttributes[1]));
				task.setTaskEndTime(Long.parseLong(taskAttributes[2]));
				task.setTaskDescription(taskAttributes[3]);
				task.setTaskLocation(taskAttributes[4]);
				task.setTaskPriority(taskAttributes[5]);
			}
		}
		return task;
	}
	
	/**
	 * Convert Long to a dateFormat dd-mm-yy. 
	 * @param timeLong
	 * @return
	 */
	private static String convertLongToDateFormat(Long timeLong) {
		Date date = new Date(timeLong);
		String dateString = format.format(date);
		return dateString;
	}

}
