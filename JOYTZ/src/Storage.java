//package V1;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.*;
import java.util.*;

public class Storage {
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
	private static File taskListFile;
	private static File historyFile;
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
		//assert !t.getTaskName().equals("") : "No task name.";
		//assert t.getTaskDeadline().before(new Date()) : "Invalid task deadline.";
		//assert !t.getTaskDescription().equals("") : "No task description.";
		//assert !t.getTaskLocation().equals("") : "No task location.";
		//assert !t.getTaskPriority().equals("") : "No task priority.";

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

		//assert taskId > 0 : "taskId :" + taskId;
		Task removedTask = taskList.remove(taskId - 1);

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
			//assert newInfo instanceof String : "name: " + newInfo;
			targetTask.setTaskName(newInfo);
			break;
		case "description":
			//assert newInfo instanceof String : "description: " + newInfo;
			targetTask.setTaskDescription(newInfo);
			break;
		case "deadline":
			//assert newInfo instanceof String : "deadline: " + newInfo;
			//assert newInfo.contains("%s-%s-%s");
			Date newDate = convertStringToDate(newInfo);
			targetTask.setTaskDeadline(newDate);
			break;
		case "location":
			//assert newInfo instanceof String : "location: " + newInfo;
			targetTask.setTaskLocation(newInfo);
			break;
		case "priority":
			//assert newInfo instanceof String : "priority: " + newInfo;
			targetTask.setTaskPriority(newInfo);
			break;
		default:
			//assert false : updateIndicator;
			throw new Exception(ERROR_INVALID_INDICATOR);
		}

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

	public static ArrayList<String> getTaskList() {
		ArrayList<String> displayList = new ArrayList<String>();

		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			String taskString = task.getTaskName();
			Date checkDate = new Date(0, 0, 0);

			if (!task.getTaskDescription().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskDescription());
			}
			if (!task.getTaskDeadline().equals(checkDate)) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskDeadline()
						.toString());
			}

			if (!task.getTaskLocation().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskLocation());
			}

			if (!task.getTaskPriority().equals("")) {
				taskString = taskString.concat("~");
				taskString = taskString.concat(task.getTaskDescription());
			}

			displayList.add(taskString);
		}

		return displayList;
	}

	/**
	 * openFile() will set up the File and FileWriter, also create a file with
	 * name {@fileName} a new line will be added to the file to
	 * indicate a new save.
	 * 
	 * @throws IOException
	 */

	private static void openFile() throws IOException {
		taskListFile = new File(taskListFileName);
		historyFile = new File(historyFileName);

		if (!taskListFile.exists()) {
			taskListFile.createNewFile();
		}
		if (!historyFile.exists()) {
			historyFile.createNewFile();
		}

		taskListWriter = new FileWriter(taskListFile);
		taskListFileReader = new FileReader(taskListFile);
		taskListBufferedReader = new BufferedReader(taskListFileReader);

		historyWriter = new FileWriter(historyFile);
		historyFileReader = new FileReader(historyFile);
		historyBufferedReader = new BufferedReader(historyFileReader);
	}

	private static void closeFile() throws IOException {
		taskListBufferedReader.close();
		taskListFileReader.close();
		taskListWriter.close();

		historyBufferedReader.close();
		historyFileReader.close();
		historyWriter.close();
	}

	public static void saveFile() throws IOException {

		openFile();
		//assert taskListFile.canWrite() : "taskListFile cannot write.";
		//assert historyFile.canWrite() : "historyFile cannot write.";

		Date date = new Date();
		String dateString = format.format(date);
		taskListWriter.write(String.format(messageStringInFile, dateString));
		historyWriter.write(String.format(messageStringInFile, dateString));

		for (int i = 0; i < taskList.size(); i++) {
			String str = convertTaskToString(taskList.get(i));
			taskListWriter.write(str);
		}

		closeFile();
		return;
	}

	public static void reloadFile() throws IOException {
		openFile();

		if (!isEmpty()) {
			clean();
		}

		String s = taskListBufferedReader.readLine();
		System.out.println("reloading file from " + s);

		s = taskListBufferedReader.readLine();
		while (!s.equals("")) {
			Task task = convertStringToTask(taskListBufferedReader.readLine());
			taskList.add(task);
		}

		closeFile();
		return;
	}

	/**
	 * Convert a dateFormatString to Date Object.
	 * 
	 * @param d
	 * @return
	 */

	private static Date convertStringToDate(String d) {
		/*
		String[] temp = d.trim().split("-");
		int year = Integer.parseInt(temp[0]);
		int month = Integer.parseInt(temp[1]);
		int day = Integer.parseInt(temp[2]);
		*/
		Date date = new Date();
		try {
			date = (Date) taskDateFormat.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

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
	 */
	private static String convertTaskToString(Task task) {
		String result = String.format(taskStringFormat, task.getTaskName(),
				task.getTaskDeadline().getTime(), task.getTaskDescription(),
				task.getTaskLocation(), task.getTaskPriority());
		return result;
	}

	private static Task convertStringToTask(String taskString) {
		String[] taskAttribute = taskString.split("-");
		Task task = new Task();

		//assert taskAttribute.length == 5 : taskAttribute.toString();
		//assert taskString.matches("(.*)-(.*)-(.*)-(.*)-(.*)") : taskString;

		task.setTaskName(taskAttribute[0]);
		task.setTaskDeadline(new Date(Long.parseLong(taskAttribute[1])));
		task.setTaskDescription(taskAttribute[2]);
		task.setTaskLocation(taskAttribute[3]);
		task.setTaskPriority(taskAttribute[4]);

		return task;
	}

}
