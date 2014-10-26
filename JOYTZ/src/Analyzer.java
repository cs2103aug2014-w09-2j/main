//package V1;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.text.ParseException;

public class Analyzer {
	private static final String ERROR_NULL_COMMAND = "Command is not inserted.\n";
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not inserted.\n";
	private static final String ERROR_NULL_TASK = "Task name is not inserted.\n";
	private static final String ERROR_NULL_INDICATOR = "Indicator is not inserted.\n";
	private static final String ERROR_NULL_ARGUMENT = "Argument is not inserted.\n";
	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";
	private static final String ERROR_INVALID_INDICATOR = "Input indicator is invalid.\n";
	private static final String ERROR_INVALID_PRIORITY = "Input priority is invalid.\n";

	private static final String[] VALID_INDICATOR = new String[] {
			StringFormat.NAME, StringFormat.DESCRIPTION,
			StringFormat.START_DATE, StringFormat.START_TIME,
			StringFormat.END_DATE, StringFormat.END_TIME,
			StringFormat.LOCATION, StringFormat.PRIORITY };
	private static final String[] VALID_PRIORITY = new String[] {
			StringFormat.HIGH_PRIORITY, StringFormat.LOW_PRIORITY,
			StringFormat.MEDIUM_PRIORITY };

	public static ExecutableCommand runAnalyzer(Command userInput)
			throws ParseException {
		assertNotNull("User input is null", userInput);

		String userCommand = userInput.getUserCommand();
		ExecutableCommand outputCommand = new ExecutableCommand();

		if (userCommand == "") {
			outputCommand.setErrorMessage(ERROR_NULL_COMMAND);
			return outputCommand;
		}

		String userAction = getUserAction(userCommand);
		String[] commandArgument = getArgument(userCommand);

		switch (userAction) {
		case StringFormat.ADD:
			outputCommand = handleAddCommand(commandArgument);
			break;
		case StringFormat.DELETE:
			outputCommand = handleDeleteCommand(commandArgument);
			break;
		case StringFormat.UPDATE:
			outputCommand = handleUpdateCommand(commandArgument);
			break;
		case StringFormat.DISPLAY:
			outputCommand = handlDisplayCommand();
			break;
		case StringFormat.UNDO:
			outputCommand = handleUndoCommand();
			break;
		case StringFormat.REDO:
			outputCommand = handleRedoCommand();
			break;
		case StringFormat.CLEAR:
			outputCommand = handleClearCommand();
			break;
		case StringFormat.SORT:
			outputCommand = handleSortCommand(commandArgument);
			break;
		case StringFormat.SEARCH:
			outputCommand = handleSearchCommand(commandArgument);
			break;
		case StringFormat.EXIT:
			outputCommand = handleExitCommand();
			break;
		case StringFormat.RELOAD:
			outputCommand = handleReloadCommand();
			break;
		default:
			outputCommand.setErrorMessage(ERROR_INVALID_COMMAND);
		}

		return outputCommand;
	}

	private static ExecutableCommand handleAddCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(StringFormat.ADD);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK);
			return tempCommand;
		}

		tempCommand.setTaskName(arg[0]);

		if (arg.length >= 2) {
			tempCommand.setTaskDescription(arg[1]);
		}
		if (arg.length >= 3) {
			String timing = inputTimingConvertor(arg[2]);

			tempCommand.setTaskStartTiming(timing);
		}
		if (arg.length >= 4) {
			String timing = inputTimingConvertor(arg[3]);

			tempCommand.setTaskEndTiming(timing);
		}
		if (arg.length >= 5) {
			tempCommand.setTaskLocation(arg[4]);
		}
		if (arg.length >= 6) {
			String temp = arg[5].toLowerCase();

			if (!isValidPriority(temp)) {
				tempCommand.setErrorMessage(ERROR_INVALID_PRIORITY);
			} else {
				tempCommand.setTaskPriority(temp);
			}
		}

		return tempCommand;
	}

	private static ExecutableCommand handleDeleteCommand(String[] arg) {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(
				StringFormat.DELETE);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK_INDEX);
			return tempCommand;
		} else if (!isInteger(arg[0]) || Integer.parseInt(arg[0]) < 1) {
			tempCommand.setErrorMessage(ERROR_INVALID_TASK_INDEX);
			return tempCommand;
		}

		tempCommand.setTaskId(Integer.parseInt(arg[0]));

		return tempCommand;
	}

	private static ExecutableCommand handleUpdateCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(
				StringFormat.UPDATE);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK_INDEX);
			return tempCommand;
		} else if (!isInteger(arg[0]) || Integer.parseInt(arg[0]) < 1) {
			tempCommand.setErrorMessage(ERROR_INVALID_TASK_INDEX);
			return tempCommand;
		} else if (arg.length == 1) {
			tempCommand.setErrorMessage(ERROR_NULL_INDICATOR);
			return tempCommand;
		} else if (!isValidIndicator(arg[1])) {
			tempCommand.setErrorMessage(ERROR_INVALID_INDICATOR);
			return tempCommand;
		}

		String taskToBeUpdated = arg[0];
		tempCommand.setTaskId(Integer.parseInt(taskToBeUpdated));

		String updateIndicator = arg[1].toLowerCase();
		String updatedItem;

		tempCommand.setIndicator(updateIndicator);

		if (updateIndicator.equals(StringFormat.START_DATE)
				|| updateIndicator.equals(StringFormat.START_TIME)
				|| updateIndicator.equals(StringFormat.END_DATE)
				|| updateIndicator.equals(StringFormat.END_TIME)) {
			updatedItem = inputTimingConvertor(arg[2]);
		} else {
			updatedItem = arg[2];
		}

		tempCommand.setKey(updatedItem);

		return tempCommand;
	}

	private static ExecutableCommand handlDisplayCommand() {
		return new ExecutableCommand(StringFormat.DISPLAY);
	}

	private static ExecutableCommand handleUndoCommand() {
		return new ExecutableCommand(StringFormat.UNDO);
	}

	private static ExecutableCommand handleRedoCommand() {
		return new ExecutableCommand(StringFormat.REDO);
	}

	private static ExecutableCommand handleClearCommand() {
		return new ExecutableCommand(StringFormat.CLEAR);
	}

	private static ExecutableCommand handleSortCommand(String[] arg) {
		ExecutableCommand tempCommand = new ExecutableCommand(StringFormat.SORT);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_INDICATOR);
			return tempCommand;
		} else if (!isValidIndicator(arg[0])) {
			tempCommand.setErrorMessage(ERROR_INVALID_INDICATOR);
			return tempCommand;
		}

		String sortIndicator = arg[0].toLowerCase();

		tempCommand.setIndicator(sortIndicator);

		return tempCommand;
	}

	private static ExecutableCommand handleSearchCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(
				StringFormat.SEARCH);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_INDICATOR);
			return tempCommand;
		} else if (arg.length == 1) {
			tempCommand.setErrorMessage(ERROR_NULL_ARGUMENT);
			return tempCommand;
		}

		String searchIndicator = arg[0].toLowerCase();
		String searchKey;

		tempCommand.setIndicator(searchIndicator);

		if (searchIndicator.equals(StringFormat.START_DATE)
				|| searchIndicator.equals(StringFormat.START_TIME)
				|| searchIndicator.equals(StringFormat.END_DATE)
				|| searchIndicator.equals(StringFormat.END_TIME)) {
			searchKey = inputTimingConvertor(arg[1]);
		} else {
			searchKey = arg[1];
		}

		tempCommand.setKey(searchKey);

		return tempCommand;
	}

	private static ExecutableCommand handleExitCommand() {
		return new ExecutableCommand(StringFormat.EXIT);
	}

	private static ExecutableCommand handleReloadCommand() {
		return new ExecutableCommand(StringFormat.RELOAD);
	}

	private static String getUserAction(String userCommand) {
		String[] cmd = convertStrToArr(userCommand);
		return cmd[0].toLowerCase();
	}

	private static String[] getArgument(String userCommand) {
		assertNotNull("User command argument is null", userCommand);

		String[] cmd = convertStrToArr(userCommand);
		String[] arg = new String[cmd.length - 1];

		for (int i = 1; i < cmd.length; i++) {
			arg[i - 1] = cmd[i].trim();
		}

		return arg;
	}

	private static String[] convertStrToArr(String str) {
		String[] arr = str.trim().split("~");
		return arr;
	}

	private static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isValidIndicator(String indicator) {
		for (int i = 0; i < VALID_INDICATOR.length; i++) {
			if (VALID_INDICATOR[i].equals(indicator)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isValidPriority(String priority) {
		for (int i = 0; i < VALID_PRIORITY.length; i++) {
			if (VALID_PRIORITY[i].equals(priority)) {
				return true;
			}
		}
		return false;
	}

	private static String inputTimingConvertor(String timing) {
		if (timing.equals("")) {
			return "";
		}

		String[] dateTime = timing.trim().split(" ");
		int[] result = { 0, 0, 0, 0, 0 };

		Date convertedDate;

		if (dateTime.length >= 1) {
			result = dateTimeSeparator(dateTime[0], result);
		}

		if (dateTime.length == 2) {
			result = dateTimeSeparator(dateTime[1], result);
		}

		convertedDate = new Date(result[0] - 1900, result[1] - 1, result[2],
				result[3], result[4]);

		return String.valueOf(convertedDate.getTime());
	}

	private static int[] dateTimeSeparator(String dateTime, int[] result) {
		String[] temp;
		int year = result[0];
		int month = result[1];
		int day = result[2];
		int hour = result[3];
		int minute = result[4];
		String indicator = "";

		if (dateTime.contains("/")) {
			temp = dateTime.trim().split("/");
			day = Integer.parseInt(temp[0]);
			month = Integer.parseInt(temp[1]);
			year = Integer.parseInt(temp[2]);
		} else if (dateTime.contains(":")) {
			if (dateTime.length() == 7) {
				hour = Integer.parseInt(dateTime.substring(0, 2));
				minute = Integer.parseInt(dateTime.substring(3, 5));
				indicator = dateTime.substring(5).toLowerCase();

				if (indicator.equals("pm") && hour != 12) {
					hour = hour + 12;
				} else if (indicator.equals("am") && hour == 12) {
					hour = 0;
				}
			} else {
				hour = Integer.parseInt(dateTime.substring(0, 1));
				minute = Integer.parseInt(dateTime.substring(2, 4));
				indicator = dateTime.substring(4).toLowerCase();

				if (indicator.equals("pm")) {
					hour = hour + 12;
				}
			}
		}

		result[0] = year;
		result[1] = month;
		result[2] = day;
		result[3] = hour;
		result[4] = minute;

		return result;
	}
}