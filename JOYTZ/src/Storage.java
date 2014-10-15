//package V1;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.*;
import java.util.*;
import java.util.logging.Logger;

public class Storage {
	
	private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());
	private static final boolean ASSERTION = false;
	
	// Exception messages
	private static final String ERROR_INVALID_TASK_RECORD = "Invalid task record: %s\n";
	private static final String MESSAGE_RELOADING_FILE = "reloading file from last saved point: %s\n";
	private static final String MESSAGE_HISTORY_FILE_NOT_EXIST = "HistoryFile not exist.\n";
	private static final String MESSAGE_TASK_LIST_FILE_NOT_EXIST = "TaskListFile not exist.\n";
	private static final String ERROR_INVALID_INDICATOR = "The update indicator is invalid.\n";
	private static final String ERROR_NULL_OBJECT = "Null Object.\n";
	private static final String ERROR_INVALID_TASKID = "taskId out of range. taskId : %d\n";

	// this is the two list of tasks.
	public static ArrayList<Task> taskList = new ArrayList<Task>();
	public static ArrayList<Task> history = new ArrayList<Task>();
	public static int numberOfTask = 0;

	// the timer is used to track the expired date of task.
	public static Timer timer = new Timer();

	// the file that used to save current tasks when user exit the program.
	//private static File taskListFile;
	//private static File historyFile;
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
			"yyyy-MM-dd HH:mm:ss.SSSSSS");
	private static String taskStringFormat = "%s-%s-%s-%s-%s\n";
	private static DateFormat taskDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private static String messageStringInFile = "User saved at %s.\n";

	/**
	 * addTask() method add in task passed by Executor.
	 * 
	 * @param Task
	 * @throws Exception
	 */

	public static boolean add(Task t) {
		if (t == null) {
			throw new InvalidParameterException(ERROR_NULL_OBJECT);
		}
		assert !t.getTaskName().equals("") : "No task name.";
		assert t.getTaskDeadline().before(new Date()) : "Invalid task deadline.";
		assert !t.getTaskDescription().equals("") : "No task description.";
		assert !t.getTaskLocation().equals("") : "No task location.";
		assert !t.getTaskPriority().equals("") : "No task priority.";
		
		
		LOGGER.info("==============\n" +
				"Storage add task: \n" + 
				"task name: " + t.getTaskName() + "\n" +
				"task description: " + t.getTaskDescription() + "\n" + 
				"task location: " + t.getTaskLocation() + "\n" +
				"task date: " + t.getTaskDeadline().toString() + "\n" +
				"task priority: " + t.getTaskPriority() + "\n" +
				"====================\n");
		
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

	public static boolean delete(int taskId) {
		if (taskId <= 0 || taskId > getTaskListSize()) {
			throw new NullPointerException(String.format(ERROR_INVALID_TASKID,
					taskId));
		}
		if (ASSERTION){
			assert taskId > 0 : "taskId :" + taskId;
		}
		Task removedTask = taskList.remove(taskId - 1);
		
		LOGGER.info("==============\n" +
				"Storage delete task. taskId: " + taskId + "\n" + 
				"task name: " + removedTask.getTaskName() + "\n" +
				"task description: " + removedTask.getTaskDescription() + "\n" + 
				"task location: " + removedTask.getTaskLocation() + "\n" +
				"task date: " + removedTask.getTaskDeadline().toString() + "\n" +
				"task priority: " + removedTask.getTaskPriority() + "\n" +
				"====================\n");

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
	 * @param newInfo
	 * @return
	 * @throws Exception
	 */

	public static boolean update(int taskId, String updateIndicator,
			String newInfo) throws Exception {
		if (taskId <= 0 || taskId > getTaskListSize()) {
			throw new NullPointerException(String.format(ERROR_INVALID_TASKID,
					taskId));
		}

		Task targetTask = get(taskId);

		switch (updateIndicator) {
		case "name":
			if (ASSERTION)
				assert newInfo instanceof String : "name: " + newInfo;
			targetTask.setTaskName(newInfo);
			break;
		case "description":
			if(ASSERTION)
				assert newInfo instanceof String : "description: " + newInfo;
			targetTask.setTaskDescription(newInfo);
			break;
		case "deadline":
			if(ASSERTION){
				assert newInfo instanceof String : "deadline: " + newInfo;
				assert newInfo.contains("%s-%s-%s");
			}
			Date newDate = new Date(Long.parseLong(newInfo));
			targetTask.setTaskDeadline(newDate);
			break;
		case "location":
			if(ASSERTION){
				assert newInfo instanceof String : "location: " + newInfo;
			}
			targetTask.setTaskLocation(newInfo);
			break;
		case "priority":
			if (ASSERTION){
				assert newInfo instanceof String : "priority: " + newInfo;
			}
			targetTask.setTaskPriority(newInfo);
			break;
		default:
			if(ASSERTION){
				assert false : updateIndicator;
			}
			throw new Exception(ERROR_INVALID_INDICATOR);
		}
		
		LOGGER.info("==============\n" +
				"Storage update task. taskId: " + taskId + "\n" + 
				"task name: " + targetTask.getTaskName() + "\n" +
				"task indicator: " + updateIndicator + "\n" + 
				"====================\n");

		taskList.set(taskId - 1, targetTask);

		return true;
	}

	/**
	 * Get a task in the taskList by taskId
	 * 
	 * @param taskId
	 * @return
	 */

	public static Task get(int taskId) {
		if (taskId <= 0 || taskId > getTaskListSize()) {
			throw new IndexOutOfBoundsException();
		}
		return taskList.get(taskId - 1);
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
		return true;
	}
	
	// only for test.
	public static void cleanUpEveryThing(){
		history.clear();
		taskList.clear();
	}
	
	/**
	 * Sort the task in taskList corresponding to parameter key.
	 * @return
	 * @throws Exception 
	 */
	public static void sort(String key) throws Exception{
		if (isEmpty()){
			throw new Exception("There is no task to sort.");
		}
		Task.setSortKey(key);
		Collections.sort(taskList);
	}

	public static ArrayList<String> getTaskList() {
		ArrayList<String> displayList = new ArrayList<String>();

		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			String taskString = task.getTaskName();
			Date checkDate = new Date(0, 0, 0);
			
			// description
			if (!task.getTaskDescription().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskDescription());
			}else {
				taskString = taskString.concat("~ ");
			}
			
			// deadline
			if (!task.getTaskDeadline().equals(checkDate)) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskDeadline()
						.toString());
			}else {
				taskString = taskString.concat("~ ");
			}
			
			// location
			if (!task.getTaskLocation().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskLocation());
			}else {
				taskString = taskString.concat("~ ");
			}
			
			// priority
			if (!task.getTaskPriority().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskPriority());
			}else {
				taskString = taskString.concat("~ ");
			}

			displayList.add(taskString);
		}

		return displayList;
	}

	
	/**
	 * SaveFile will save all the tasks in History and taskList into  two files.
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
		
		if(ASSERTION){
			assert taskListFile.canWrite() : "taskListFile cannot write.";
			assert historyFile.canWrite() : "historyFile cannot write.";
		}
		
		Date date = new Date();
		String dateString = format.format(date);
		taskListWriter.write(String.format(messageStringInFile, dateString));
		historyWriter.write(String.format(messageStringInFile, dateString));

		for (int i = 0; i < taskList.size(); i++) {
			String str = convertTaskToString(taskList.get(i));
			taskListWriter.write(str);
		}
		for (int i=0; i<history.size(); i++){
			String str = convertTaskToString(history.get(i));
			historyWriter.write(str);
		}
		
		taskListWriter.close();
		historyWriter.close();
		
		return;
	}
	
	/**
	 * reloadFile will reload task to the arrayList from two file.
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

		String s = taskListBufferedReader.readLine();
		System.out.println(String.format(MESSAGE_RELOADING_FILE, s));
		
		s = taskListBufferedReader.readLine();
		while (s != null) {
			Task task = convertStringToTask(s);
			taskList.add(task);
			s = taskListBufferedReader.readLine();
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
	
	public static int getHistorySize(){
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
		if (task == null){
			throw new Exception(ERROR_NULL_OBJECT);
		}
		String result = String.format(taskStringFormat, task.getTaskName(),
				task.getTaskDeadline().getTime(), task.getTaskDescription(),
				task.getTaskLocation(), task.getTaskPriority());
		return result;
	}

	private static Task convertStringToTask(String taskString) throws Exception {
		System.out.println(taskString);
		Task task = new Task();
		
		if (ASSERTION){
			assert taskString.matches("(.*)-(.*)-(.*)-(.*)-(.*)") : taskString;
		}
		if (taskString == null){
			task = null;
		}else {
			String[] taskAttribute = taskString.split("-");
			if (taskAttribute.length != 5){
				throw new Exception(String.format(ERROR_INVALID_TASK_RECORD, taskString));
			}else {
				task.setTaskName(taskAttribute[0]);
				task.setTaskDeadline(new Date(Long.parseLong(taskAttribute[1])));
				task.setTaskDescription(taskAttribute[2]);
				task.setTaskLocation(taskAttribute[3]);
				task.setTaskPriority(taskAttribute[4]);
			}
		}
		return task;
	}

}
