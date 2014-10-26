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
	private static final String ERROR_INVALID_INDICATOR = "Indicator is invalid.\n";
	private static final String[] VALID_INDICATOR = new String[] { "name",
			"description", "start date", "start time", "end date", "end time",
			"location", "priority" };

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
		case "add":
			outputCommand = handleAddCommand(commandArgument);
			break;
		case "delete":
			outputCommand = handleDeleteCommand(commandArgument);
			break;
		case "update":
			outputCommand = handleUpdateCommand(commandArgument);
			break;
		case "display":
			outputCommand = handlDisplayCommand();
			break;
		case "undo":
			outputCommand = handleUndoCommand();
			break;
		case "redo":
			outputCommand = handleRedoCommand();
			break;
		case "clear":
			outputCommand = handleClearCommand();
			break;
		case "sort":
			outputCommand = handleSortCommand(commandArgument);
			break;
		case "search":
			outputCommand = handleSearchCommand(commandArgument);
			break;
		case "exit":
			outputCommand = handleExitCommand();
			break;
		default:
			outputCommand.setErrorMessage(ERROR_INVALID_COMMAND);
		}

		return outputCommand;
	}

	private static ExecutableCommand handleAddCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand("add");

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
			tempCommand.setTaskPriority(arg[5]);
		}

		return tempCommand;
	}

	private static ExecutableCommand handleDeleteCommand(String[] arg) {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand("delete");

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

		ExecutableCommand tempCommand = new ExecutableCommand("update");

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

		if (updateIndicator.equals("start date")
				|| updateIndicator.equals("start time")
				|| updateIndicator.equals("end date")
				|| updateIndicator.equals("end time")) {
			updatedItem = inputTimingConvertor(arg[2]);
		} else {
			updatedItem = arg[2];
		}

		tempCommand.setKey(updatedItem);

		return tempCommand;
	}

	private static ExecutableCommand handlDisplayCommand() {
		return new ExecutableCommand("display");
	}

	private static ExecutableCommand handleUndoCommand() {
		return new ExecutableCommand("undo");
	}

	private static ExecutableCommand handleRedoCommand() {
		return new ExecutableCommand("redo");
	}

	private static ExecutableCommand handleClearCommand() {
		return new ExecutableCommand("clear");
	}

	private static ExecutableCommand handleSortCommand(String[] arg) {
		ExecutableCommand tempCommand = new ExecutableCommand("sort");

		// have to check the indicator here.
		if (arg.length != 0) {
			String sortIndicator = arg[0].toLowerCase();

			tempCommand.setIndicator(sortIndicator);
		}

		return tempCommand;
	}

	private static ExecutableCommand handleSearchCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand("search");

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

		if (searchIndicator.equals("start date")
				|| searchIndicator.equals("start time")
				|| searchIndicator.equals("end date")
				|| searchIndicator.equals("end time")) {
			searchKey = inputTimingConvertor(arg[1]);
		} else {
			searchKey = arg[1];
		}

		tempCommand.setKey(searchKey);

		return tempCommand;
	}

	private static ExecutableCommand handleExitCommand() {
		return new ExecutableCommand("exit");
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

		convertedDate = new Date(result[0] - 1900, result[1] - 1,
				result[2] + 1, result[3], result[4] - 1);

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