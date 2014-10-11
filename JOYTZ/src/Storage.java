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
	
	// this is the two list of tasks.
	static ArrayList<Task> listOfTask = new ArrayList<Task>();
	static ArrayList<Task> history = new ArrayList<Task>();
	static int numberOfTask = 0;
	
	// the timer is used to track the expired date of task.
	static Timer timer = new Timer();
	
	// this file contains all the messages that user want to record.
	private static File file;
	public static String fileName = "RecordFile.txt";
		
	// this file contains all the user commands.
	private static FileWriter writer;
	
	// these three are for recording current information in the log file.
	private static DateFormat format = new SimpleDateFormat("New Log: yyyy-MM-dd HH:mm:ss.SSSSSS");
	private static Date date;
	private static String dateString;
		
	// this is only for reload the original user file.
	private static FileReader fr;
	private static BufferedReader br;
	
	/**
	 * addTask() method add in task passed by Executor.
	 * @param Task t
	 * @throws nullTaskException 
	 */

	public static boolean add(Task t) throws Exception {
		if (t.equals(null)){
			throw new Exception("the task object is null.");
		}
		
		listOfTask.add(t);
		// timer.schedule(t, t.getTime());
		numberOfTask++;

		return true;
	}

	public static boolean delete(int taskId) throws Exception {
		if (taskId<0 || taskId>getSizeOfListOfTask()){
			throw new Exception("TaskId out of range. TaskId : " + taskId);
		}
		
		Task removedTask = listOfTask.remove(taskId - 1);
		// removedTask.cancel();
		numberOfTask--;
		history.add(removedTask);
		
		return true;
	}
	
	public static Task get(int taskId) throws Exception{
		if (taskId<0 || taskId>getSizeOfListOfTask()){
			throw new Exception("TaskId out of range. TaskId : " + taskId);
		}
		
		Task task = listOfTask.get(taskId);
		
		return task;
	}


	public static boolean clean() {
		
		for (int itemId = 0; itemId < listOfTask.size(); itemId++) {
			history.add(listOfTask.get(itemId));
		}
		listOfTask.clear();
		
		return true;
	}
	
	/**
	 * openFile() will set up the File and FileWriter, also create a file with name {@fileName}
	 * a new line will be added to the file to indicate a new save.
	 * @throws IOException
	 */
	
	public static void openFile() throws IOException{
		file = new File(fileName);
		if (!file.exists()){
			file.createNewFile();
		}
		writer = new FileWriter (file);
		date = new Date();
		dateString= format.format(date);
		writer.write(dateString);
	}
	
	public static boolean saveToFile() throws IOException{
		
		openFile();
		
		for (int index=0; index<listOfTask.size(); index++){
			String str = listOfTask.get(index).toString();
			writer.write(str);
		}
		
		return true;
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
	
	public static int getSizeOfListOfTask() {
		int size = listOfTask.size();
		return size;
	}


}
