// @author A0119378U
import java.io.*;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Description :
 * 
 * FileInOut class in charge of save taskList from memory to .txt file and
 * reload a txt file to taskList.
 * 
 *
 *
 */
public class FileInOut {
	private static final Logger LOGGER = Logger.getLogger(FileInOut.class
			.getName());

	private File file;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private FileWriter fileWriter;

	public FileInOut() {
	}

	/**
	 * Save the given taskList to local file.
	 * 
	 * @param targetList
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean saveTaskList(List targetList, String fileName)
			throws Exception {
		file = new File(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		fileWriter = new FileWriter(file);
		fileWriter.write(getFirstLineMsg());

		for (int index = 0; index < targetList.size(); index++) {
			Task currTask = targetList.getTaskByIndex(index);
			String currTaskString = convertTaskToString(currTask);
			fileWriter.write(currTaskString);
		}

		fileWriter.close();

		LOGGER.info("==============\n" + "FileInOut : Save \n"
				+ "====================\n");
		return true;
	}

	/**
	 * Read the file and create a taskList from the file.
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public List readTaskList(String fileName) throws Exception {
		file = new File(fileName);
		List resultList = new List();

		if (!file.exists()) {
			throw new Exception(
					StringFormat.IO_MESSAGE_TASK_LIST_FILE_NOT_EXIST);
		}

		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		// read the first line.
		String messageLine = bufferedReader.readLine();
		String taskString = bufferedReader.readLine();
		while (taskString != null) {
			System.out.println("task Strng : " + taskString);
			Task task = converStringToTask(taskString);
			resultList.addTask(task);
			taskString = bufferedReader.readLine();
		}

		LOGGER.info("==============\n" + "FileInOut : Read \n"
				+ "	resultList size: " + resultList.size() + "\n"
				+ "====================\n");
		return resultList;

	}

	/**
	 * Get the first line to be written in the file. Include time info.
	 * 
	 * @return String format of the message.
	 */
	public String getFirstLineMsg() {
		Date now = new Date();
		String msgString = StringFormat.IO_MESSAGE_SAVED_IN_FILE;
		String dateString = StringFormat.IO_DATE_FORMAT_SAVED_IN_FILE
				.format(now) + "\n";

		return msgString + dateString;
	}

	/**
	 * Convert a Task Object to String. Follow the given Format.
	 * 
	 * @param task
	 * @return
	 */
	public String convertTaskToString(Task task) {
		String emptySpace = " ";
		String resultString = task.getTaskName().concat("~");

		// add in description information.

		resultString = resultString.concat(task.getTaskDescription());

		resultString = resultString.concat("~");

		// add in start date time information.
		if (task.getStartDateTime() != null) {
			resultString = resultString.concat(task
					.getLongStringFormatStartDateTime());
		}
		resultString = resultString.concat("~");

		// add in end date time information.
		if (task.getEndDateTime() != null) {
			resultString = resultString.concat(task
					.getLongStringFormatEndDateTime());
		}
		resultString = resultString.concat("~");

		// add in location information.
		resultString = resultString.concat(task.getTaskLocation());
		resultString = resultString.concat("~");

		// add in priority information.
		resultString = resultString.concat(task.getTaskPriority());
		resultString = resultString.concat(emptySpace);

		resultString = resultString.concat("\n");
		return resultString;
	}

	/**
	 * Convert a String in the saved .txt file to a Task Object.
	 * 
	 * @param taskString
	 * @return
	 * @throws Exception
	 */

	public Task converStringToTask(String taskString) throws Exception {
		String[] taskAttributes = taskString.split("~");

		if (taskAttributes.length != 6) {
			throw new Exception(String.format(
					StringFormat.IO_ERROR_INVALID_TASK_RECORD, taskString));
		} else {

			Task task = new Task();
			task.setTaskName(taskAttributes[0].trim());

			task.setTaskDescription(taskAttributes[1].trim());
			if (!taskAttributes[2].trim().equals("")) {
				task.setStartDateTime(new Date(Long
						.parseLong(taskAttributes[2])));
			}
			if (!taskAttributes[3].trim().equals("")) {
				task.setEndDateTime(new Date(Long.parseLong(taskAttributes[3])));
			}
			task.setTaskLocation(taskAttributes[4].trim());
			task.setTaskPriority(taskAttributes[5].trim());
			return task;
		}
	}

}
