import java.io.*;
import java.util.Date;
import java.util.logging.Logger;

public class FileInOut {
	private static final Logger LOGGER = Logger.getLogger(FileInOut.class.getName());
	
	String fileName;
	private File file;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private FileWriter fileWriter;

	public FileInOut(String name) {
		this.fileName = name;
		this.file = new File(fileName);
	}

	/**
	 * Save the given taskList to local file.
	 * 
	 * @param targetList
	 * @param fileName
	 * @return
	 */
	public boolean saveTaskList(List targetList, String fileName) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			fileWriter = new FileWriter(file);
			fileWriter.write(getFirstLineMsg());
			
			for (int index = 0; index < targetList.size(); index++) {
				Task currTask = targetList.getTaskByIndex(index);
				String currTaskString = convertTaskToString(currTask);
				System.out.println("shwo nee");
				fileWriter.write(currTaskString);
			}

			fileWriter.close();
		} catch (Exception e) {
			return false;
		}
		
		LOGGER.info("==============\n" +
				"FileInOut : Save \n" + 
				"====================\n");
		return true;
	}

	public List readTaskList(String fileName) throws Exception {
		List resultList = new List();
		
		if (!file.exists()) {
			throw new Exception(StringFormat.MESSAGE_TASK_LIST_FILE_NOT_EXIST);
		}

		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		// read the first line.
		String messageLine = bufferedReader.readLine();
		
		String taskString = bufferedReader.readLine();
		while (taskString != null) {
			Task task = converStringToTask(taskString);
			resultList.addTask(task);
			taskString = bufferedReader.readLine();
		}
		
		LOGGER.info("==============\n" +
				"FileInOut : Read \n" + 
				"	resultList size: " + resultList.size() + "\n" +
				"====================\n");
		return resultList;

	}

	/**
	 * Get the first line to be written in the file. Include time info.
	 * 
	 * @return String format of the message.
	 */
	public String getFirstLineMsg() {
		Date now = new Date();
		String msgString = StringFormat.MESSAGE_SAVED_IN_FILE;
		String dateString = StringFormat.DATE_FORMAT_SAVED_IN_FILE.format(now) + "\n";

		return msgString + dateString;
	}

	/**
	 * Convert a Task Object to String. Follow the given Format.
	 * 
	 * @param task
	 * @return
	 */
	public String convertTaskToString(Task task) {
		String result = task.toString();
		return result;
	}
	
	/**
	 * Convert a String in the saved .txt file to a Task Object.
	 * @param taskString
	 * @return
	 * @throws Exception
	 */
	
	public Task converStringToTask(String taskString) throws Exception{
		String[] taskAttributes = taskString.split("~");
		
		if (taskAttributes.length != 6) {
			throw new Exception(String.format(StringFormat.ERROR_INVALID_TASK_RECORD, taskString));
		} else {
			Task task = new Task();
			task.setTaskName(taskAttributes[0]);
			task.setStartDate(new Date(Long.parseLong(taskAttributes[1])));
			task.setEndDateTime(new Date(Long.parseLong(taskAttributes[2])));
			task.setTaskDescription(taskAttributes[3]);
			task.setTaskLocation(taskAttributes[4]);
			task.setTaskPriority(taskAttributes[5]);
			return task;
		}
	}
	
}
