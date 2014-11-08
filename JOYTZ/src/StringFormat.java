import java.text.SimpleDateFormat;

public class StringFormat {

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
	public static final String OF_INDICATOR = "of";
	public static final String AM_INDICATOR = "am";
	public static final String PM_INDICATOR = "pm";
	public static final String FROM_INDICATOR = "from";
	public static final String IMPORTANT = "important";
	public static final String UNIMPORTANT = "unimportant";

	/**
	 * invalid indicator
	 */
	public static final String INVALID = "invalid";

	
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
