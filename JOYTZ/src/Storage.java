//package V1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Storage {
	public static final String ERROR_INVALID_INDICATOR = "The update indicator is invalid.\n";

	// this is the two list of tasks.
	public static ArrayList<Task> taskList = new ArrayList<Task>();
	public static ArrayList<Task> history = new ArrayList<Task>();
	public static int numberOfTask = 0;

	// the timer is used to track the expired date of task.
	public static Timer timer = new Timer();

	// this file contains all the messages that user want to record.
	private static File file;
	public static String fileName = "RecordFile.txt";

	// this file contains all the user commands.
	private static FileWriter writer;

	// these three are for recording current information in the log file.
	private static DateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSSSSS");
	private static Date date;
	private static String dateString;

	// this is only for reload the original user file.
	private static FileReader fr;
	private static BufferedReader br;

	/**
	 * addTask() method add in task passed by Executor.
	 * 
	 * @param Task
	 * @throws Exception
	 */

	public static boolean add(Task t) {
		taskList.add(t);
		// timer.schedule(t, t.getTime());
		numberOfTask++;

		return true;
	}

	public static boolean delete(int taskId) {
		if (taskId > getTaskListSize()) {
			return false;
		}

		Task removedTask = taskList.remove(taskId - 1);
		// removedTask.cancel();
		numberOfTask--;
		history.add(removedTask);

		return true;
	}

	public static boolean update(int taskId, String updateIndicator,
			String newInfo) {
		if (taskId > getTaskListSize()) {
			return false;
		}

		Task targetTask = taskList.get(taskId - 1);

		switch (updateIndicator) {
		case "name":
			targetTask.setTaskName(newInfo);
			break;
		case "description":
			targetTask.setTaskDescription(newInfo);
			break;
		case "deadline":
			Date newDate = convertStringToDate(newInfo);
			targetTask.setTaskDeadline(newDate);
			break;
		case "location":
			targetTask.setTaskLocation(newInfo);
			break;
		case "priority":
			targetTask.setTaskPriority(newInfo);
		default:
			return false;
		}

		taskList.set(taskId - 1, targetTask);

		return true;

	}

	public static Task get(int taskId) throws Exception {
		if (taskId > getTaskListSize()) {
			throw new Exception(String.format(
					StringFormat.EXCEPTION_TASK_OUT_OF_RANGE, taskId));
		}

		return taskList.get(taskId);
	}

	public static boolean clean() {
		if (!isEmpty()) {
			for (int itemId = 0; itemId < taskList.size(); itemId++) {
				history.add(taskList.get(itemId));
			}
			taskList.clear();
		}
		return true;
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

	public static void getFileReady() throws IOException {
		file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		writer = new FileWriter(file);
		fr = new FileReader(file);
		br = new BufferedReader(fr);
	}

	public static void saveFile() throws IOException {

		getFileReady();

		date = new Date();
		dateString = format.format(date);
		writer.write(dateString);

		for (int index = 0; index < taskList.size(); index++) {
			String str = taskList.get(index).convertTaskToString();
			writer.write(str);
		}
		return;
	}

	public static void reloadFile() throws IOException {
		getFileReady();

		if (!isEmpty()) {
			clean();
		}

		String s = br.readLine();
		System.out.println("reloading file from " + s);

		s = br.readLine();
		while (!s.equals("")) {
			Task t = new Task();
			t.convertStringToTask(s);
			taskList.add(t);
		}

		return;
	}

	private static Date convertStringToDate(String d) {
		String[] temp = d.trim().split("-");
		int year = Integer.parseInt(temp[0]);
		int month = Integer.parseInt(temp[1]);
		int day = Integer.parseInt(temp[2]);

		return new Date(day, month, year);
	}

	public static int getTaskListSize() {
		return taskList.size();
	}

	public static boolean isEmpty() {
		return taskList.isEmpty();
	}

}
