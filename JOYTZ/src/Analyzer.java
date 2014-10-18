//package V1;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.eclipse.ui.internal.services.INestable;

public class Analyzer {
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not indicated.\n";
	private static final String ERROR_NULL_COMMAND = "Command is not indicated.\n";
	private static final String ERROR_NULL_TASK_TO_ADD = "Task to be added is not indicated.\n";
	private static final String ERROR_NULL_TASK_TO_SEARCH = "Task to be searched is not indicated.\n";
	private static final String ERROR_NULL_INDICATOR = "Indicator is not inserted.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";
	private static final String ERROR_INVALID_ARGUMENT = "The input argument is invalid.\n";
	private static final String ERROR_INVALID_INDICATOR = "Indicator is invalid.\n";
	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";

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
			tempCommand.setErrorMessage(ERROR_NULL_TASK_TO_ADD);
			return tempCommand;
		}

		tempCommand.setTaskName(arg[0]);

		if (arg.length >= 2) {
			tempCommand.setTaskDescription(arg[1]);
		}
		if (arg.length >= 3) {
			String timing = dateTimeSeparator(arg[2]);

			tempCommand.setTaskStartTiming(timing);
		}
		if (arg.length >= 4) {
			String timing = dateTimeSeparator(arg[2]);

			tempCommand.setTaskStartTiming(timing);
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
		}

		if (isInteger(arg[0])) {
			int index = Integer.parseInt(arg[0]);
			if (index < 1) {
				tempCommand.setErrorMessage(ERROR_INVALID_TASK_INDEX);
			} else {
				tempCommand.setTaskId(Integer.parseInt(arg[0]));
			}
		} else {
			tempCommand.setErrorMessage(ERROR_INVALID_ARGUMENT);
		}

		return tempCommand;
	}

	private static ExecutableCommand handleUpdateCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand("update");

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK_INDEX);
			return tempCommand;
		} else if (arg.length == 1) {
			tempCommand.setErrorMessage(ERROR_NULL_INDICATOR);
			return tempCommand;
		} else if (!isInteger(arg[0])) {
			tempCommand.setErrorMessage(ERROR_INVALID_ARGUMENT);
			return tempCommand;
		} else if (Integer.parseInt(arg[0]) < 1) {
			tempCommand.setErrorMessage(ERROR_INVALID_TASK_INDEX);
			return tempCommand;
		}

		String taskToBeUpdated = arg[0];

		if (isInteger(taskToBeUpdated)) {
			tempCommand.setTaskId(Integer.parseInt(taskToBeUpdated));
		} else {
			tempCommand.setErrorMessage(ERROR_INVALID_ARGUMENT);

			return tempCommand;
		}

		String updateIndicator = arg[1].toLowerCase();
		String updatedItem;

		tempCommand.setIndicator(updateIndicator);

		if (updateIndicator.equals("start date")
				|| updateIndicator.equals("start time")
				|| updateIndicator.equals("end date")
				|| updateIndicator.equals("end time")) {
			updatedItem = dateTimeSeparator(arg[2]);
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

	private static ExecutableCommand handleClearCommand() {
		return new ExecutableCommand("clear");
	}

	private static ExecutableCommand handleSortCommand(String[] arg) {
		ExecutableCommand tempCommand = new ExecutableCommand("sort");

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
			tempCommand.setErrorMessage(ERROR_NULL_TASK_TO_SEARCH);

			return tempCommand;
		}

		String searchIndicator = arg[0].toLowerCase();
		String searchKey;

		tempCommand.setIndicator(searchIndicator);

		if (searchIndicator.equals("start date")
				|| searchIndicator.equals("start time")
				|| searchIndicator.equals("end date")
				|| searchIndicator.equals("end time")) {
			searchKey = dateTimeSeparator(arg[1]);
		} else {
			searchKey = arg[1];
		}

		tempCommand.setKey(searchKey);

		/*
		 * switch (searchIndicator) { case "name":
		 * tempCommand.setTaskName(searchKey); break; case "startTiming":
		 * tempCommand.setTaskStartTiming(searchKey); break; case "endTiming":
		 * tempCommand.setTaskEndTiming(searchKey); break; case "priority":
		 * tempCommand.setTaskPriority(searchKey); break; case "location":
		 * tempCommand.setTaskLocation(searchKey); break; case "id":
		 * tempCommand.setTaskId(Integer.parseInt(searchKey)); break; default:
		 * tempCommand.setErrorMessage(ERROR_INVALID_INDICATOR); }
		 */

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

	private static String dateTimeSeparator(String timing) {
		String[] dateTime = timing.trim().split(" ");
		String[] temp;
		int day = 0;
		int month = 0;
		int year = 0;
		int hour = 0;
		int minute = 0;
		String indicator;
		Date convertedDate;

		if (dateTime.length >= 1) {
			if (dateTime[0].contains("\"")) {
				temp = dateTime[0].trim().split("\"");
				day = Integer.parseInt(temp[0]);
				month = Integer.parseInt(temp[1]);
				year = Integer.parseInt(temp[2]);
			} else if (dateTime[0].contains(":")) {
				if (dateTime[0].length() == 7) {
					hour = Integer.parseInt(dateTime[0].substring(0, 2));
					minute = Integer.parseInt(dateTime[0].substring(3, 5));
					indicator = dateTime[0].substring(5).toLowerCase();

					if (indicator.equals("pm")) {
						hour = hour + 12;
					}
				}
			}
		}

		if (dateTime.length == 2) {
			if (dateTime[1].contains("\"")) {
				temp = dateTime[1].trim().split("\"");
				day = Integer.parseInt(temp[0]);
				month = Integer.parseInt(temp[1]);
				year = Integer.parseInt(temp[2]);
			} else if (dateTime[1].contains(":")) {
				if (dateTime[1].length() == 7) {
					hour = Integer.parseInt(dateTime[1].substring(0, 2));
					minute = Integer.parseInt(dateTime[1].substring(3, 5));
					indicator = dateTime[0].substring(5).toLowerCase();

					if (indicator.equals("pm")) {
						hour = hour + 12;
					}
				}
			}
		}

		convertedDate = new Date(year, month, day, hour, minute);

		return String.valueOf(convertedDate.getTime());
	}

	/*
	 * private static Long analyzeUserInputDateString(String userInputDate) {
	 * String[] date = userInputDate.trim().split(" "); String dateString;
	 * String timeString;
	 * 
	 * // distinguish date (dd/mm/yy) and time (hh:mm:ss) if
	 * (date[0].contains("/")) { dateString = date[0]; timeString = date[1]; }
	 * else { dateString = date[1]; timeString = date[0]; }
	 * 
	 * // current date and time Date now = new Date(System.currentTimeMillis());
	 * 
	 * int day = 0; int month = 0; int year = 0; String[] dateArg =
	 * dateString.trim().split("/");
	 * 
	 * if (dateArg.length == 1) { day = Integer.parseInt(dateArg[0]); month =
	 * now.getMonth(); year = now.getYear(); } else if (dateArg.length == 2) {
	 * day = Integer.parseInt(dateArg[0]); month = Integer.parseInt(dateArg[1]);
	 * year = now.getYear(); } else if (dateArg.length == 3) { day =
	 * Integer.parseInt(dateArg[0]); month = Integer.parseInt(dateArg[1]); year
	 * = Integer.parseInt(dateArg[2]); }
	 * 
	 * int minute = 0; int hour = 0; String[] timeArg =
	 * timeString.trim().split(":");
	 * 
	 * if (timeArg.length == 1) { hour = Integer.parseInt(dateArg[0]); minute =
	 * 0; } else if (timeArg.length == 2) { hour = Integer.parseInt(dateArg[0]);
	 * minute = Integer.parseInt(dateArg[1]); }
	 * 
	 * Date convertedDate = new Date(year, month, day, hour, minute); return
	 * convertedDate.getTime(); }
	 */

}