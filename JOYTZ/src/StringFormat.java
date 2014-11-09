import java.text.SimpleDateFormat;

public class StringFormat {

	/**
	 * Format and Static String in FileInOut
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_SHOWN_TO_USER = new SimpleDateFormat(
			"yyyy.MM.dd 'at' HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_SAVED_IN_FILE = new SimpleDateFormat(
			"E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	public static final String MESSAGE_SAVED_IN_FILE = "Last saved point: ";
	// indicate name-description-start-end-location-priority.
	public static final String TASK_STRING_FORMAT_SAVED_IN_FILE = "%s-%s-%s-%s-%s-%s\n";
	public static final String MESSAGE_TASK_LIST_FILE_NOT_EXIST = "TaskListFile not exist.\n";
	public static final String ERROR_INVALID_TASK_RECORD = "Invalid task record: %s\n";

	/**
	 * Message show to the user in Executor
	 */

	public static final String EXE_ERROR_NULL_EXECUTABLE_COMMAND = "Null command.\n";
	public static final String EXE_ERROR_INVALID_COMMAND_ACTION = "Invalid command action: %s.\n";
	// Add
	public static final String EXE_MSG_ADD_SUCCESSFUL = "%s is added successfully.\n";
	// Delete
	public static final String EXE_MSG_DELETE_SUCCESSFUL = "Task is deleted successfully.\n";
	// Done
	public static final String EXE_MSG_DONE_SUCCESSFUL = "Add done task to history successfully.\n";
	// Display
	public static final String EXE_ERROR_NO_TASK_LIST_INDICATOR = "No Task List indicator.\n";
	// Clear
	public static final String EXE_MSG_CLEAR_SUCCESSFUL = "All tasks are cleared successfully.\n";

	/**
	 * Message show to the user in Storage
	 */
	// In Add Method
	public static final String STR_ERROR_NULL_TASK_OBJECT = "Cannot add a null task.\n";
	public static final String STR_ERROR_NO_TASK_NAME = "Cannot add a task without a name.\n";
	public static final String STR_ERROR_START_TIME_AFTER_END_TIME = "Start time is after End time. Start time : %s; End time : %s.\n";
	public static final String STR_ERROR_START_TIME_BEFORE_CURRENT_TIME = "Start time is before current time. Start time : %s.\n";
	public static final String STR_ERROR_END_TIME_BEFORE_CURRENT_TIME = "End time is before current time. End time : %s.\n";

	// In Delete, Update Method
	public static final String STR_ERROR_INVALID_TASK_INDEX = "Task index is out of range. Index : %d.\n";
	public static final String STR_ERROR_INVALID_INDICATOR = "The update indicator '%s' is invalid.\n";

	// In Display, Clean Method
	public static final String STR_ERROR_INVALID_TASK_LIST_INDICATOR = "No such taskList. List name : %s.\n";
	
	//@author A0112162Y
	/**
	 * error messages used in Analyzer and TimeHandler
	 */
	public static final String ERROR_NULL_COMMAND = "Command is not inserted.\n";
	public static final String ERROR_NULL_TASK_INDEX = "Task index is not inserted.\n";
	public static final String ERROR_NULL_TASK = "Task name is not inserted.\n";
	public static final String ERROR_NULL_INDICATOR = "Indicator is not inserted.\n";
	public static final String ERROR_NULL_ARGUMENT = "Argument is not inserted.\n";
	public static final String ERROR_INVALID_COMMAND = "Invalid command.\n";
	public static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";
	public static final String ERROR_INVALID_INDICATOR = "Input indicator is invalid.\n";
	public static final String ERROR_INVALID_PRIORITY = "Input priority is invalid.\n";
	public static final String ERROR_INVALID_TIME = "Format of input %s time is invalid.\n";
	public static final String ERROR_INVALID_EARLIER_TIME = "Input %s time is earlier than current time.\n";
	public static final String ERROR_INVALID_END_EARLIER_THAN_START = "End time is earlier than start time.\n";

	/**
	 * action
	 */
	public static final String ADD = "add";
	public static final String DELETE = "delete";
	public static final String DISPLAY = "display";
	public static final String UPDATE = "update";
	public static final String SEARCH = "search";
	public static final String CLEAR = "clear";
	public static final String SORT = "sort";
	public static final String UNDO = "undo";
	public static final String REDO = "redo";
	public static final String DONE = "done";
	public static final String EXIT = "exit";
	public static final String RELOAD = "reload";
	public static final String HELP = "help";
	public static final String TUTORIAL = "tutorial";
	public static final String SETTINGS = "settings";

	/**
	 * sort, search and update indicators
	 */
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String LOCATION = "location";
	public static final String PRIORITY = "priority";
	public static final String START = "start";
	public static final String END = "end";
	public static final String START_TIME = "start time";
	public static final String END_TIME = "end time";
	public static final String START_DATE = "start date";
	public static final String END_DATE = "end date";
	public static final String HIGH_PRIORITY = "high";
	public static final String MEDIUM_PRIORITY = "medium";
	public static final String LOW_PRIORITY = "low";

	/**
	 * user input indicators
	 */
	public static final String PRIORITY_INDICATOR = "#";
	public static final String LOCATION_INDICATOR = "@";
	public static final String TIME_INDICATOR = ":";
	public static final String SEPARATE_INDICATOR = ";";
	public static final String DATE_INDICATOR = "/";
	public static final String SPACE_INDICATOR = " ";
	public static final String DUE_INDICATOR = "due";
	public static final String DUE_ON_INDICATOR = "dueon";
	public static final String DUE_AT_INDICATOR = "dueat";
	public static final String TO_INDICATOR = "to";
	public static final String AT_INDICATOR = "at";
	public static final String ON_INDICATOR = "on";
	public static final String AM_INDICATOR = "am";
	public static final String PM_INDICATOR = "pm";
	public static final String FROM_INDICATOR = "from";
	public static final String IMPORTANT = "important";
	public static final String UNIMPORTANT = "unimportant";

	/**
	 * current task list and done task list indicators
	 */
	public static final String MAIN_TASK_LIST = "main task list";
	public static final String DONE_TASK_LIST = "done task list";

	/**
	 * invalid and null indicators
	 */
	public static final String INVALID = "invalid";
	public static final String NULL = "null";

	/**
	 * empty indicator
	 */
	public static final String EMPTY = "";

	/**
	 * check valid indicator
	 */
	public static boolean isValidIndicator(String indicator) {
		return indicator.equals(StringFormat.NAME)
				|| indicator.equals(StringFormat.DESCRIPTION)
				|| indicator.equals(StringFormat.LOCATION)
				|| indicator.equals(StringFormat.PRIORITY)
				|| indicator.equals(StringFormat.START)
				|| indicator.equals(StringFormat.END);
	}

	/**
	 * check valid priority
	 */
	public static boolean isValidPriority(String priority) {
		return priority.equals(HIGH_PRIORITY)
				|| priority.equals(MEDIUM_PRIORITY)
				|| priority.equals(LOW_PRIORITY) || priority.equals(IMPORTANT)
				|| priority.equals(UNIMPORTANT);
	}

	/**
	 * check input indicator
	 */
	public static boolean isInputIndicator(String indicator) {
		return indicator.equals(StringFormat.TIME_INDICATOR)
				|| indicator.equals(StringFormat.DUE_AT_INDICATOR)
				|| indicator.equals(StringFormat.DUE_ON_INDICATOR)
				|| indicator.contains(StringFormat.LOCATION_INDICATOR)
				|| indicator.contains(StringFormat.PRIORITY_INDICATOR);
	}

	public static boolean isAmbiguousInputIndicator(String indicator) {
		return indicator.equals(StringFormat.TO_INDICATOR)
				|| indicator.equals(StringFormat.FROM_INDICATOR)
				|| indicator.equals(StringFormat.AT_INDICATOR)
				|| indicator.equals(StringFormat.ON_INDICATOR);
	}

	/**
	 * check existence of date or time or both
	 */
	public static boolean isTimeOrDate(String temp) {
		return temp.contains(StringFormat.TIME_INDICATOR)
				|| temp.contains(StringFormat.DATE_INDICATOR);
	}

	public static boolean isDate(String temp) {
		return temp.contains(StringFormat.DATE_INDICATOR);
	}

	public static boolean isTime(String temp) {
		return temp.contains(StringFormat.TIME_INDICATOR);
	}
}
