//package V1;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

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
			outputCommand = handlDisplayCommand(commandArgument);
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
			if (arg[2].equals("")) {
				tempCommand.setTaskDeadline(new Date(0, 0, 0));
			} else {
				Date deadline = (Date) df.parse(arg[2]);

				tempCommand.setTaskDeadline(deadline);
			}
		}

		if (arg.length >= 4) {
			tempCommand.setTaskLocation(arg[3]);
		}

		if (arg.length >= 5) {
			tempCommand.setTaskPriority(arg[4]);
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

		String updateIndicator = arg[1];
		String updatedItem = arg[2];

		tempCommand.setIndicator(updateIndicator);

		switch (updateIndicator) {
		case "name":
			tempCommand.setTaskName(updatedItem);
			break;
		case "description":
			tempCommand.setTaskDescription(updatedItem);
			break;
		case "deadline":
			Date updatedDeadline = (Date) df.parse(updatedItem);

			tempCommand.setTaskDeadline(updatedDeadline);
			break;
		case "location":
			tempCommand.setTaskLocation(updatedItem);
			break;
		case "priority":
			tempCommand.setTaskPriority(updatedItem);
			break;
		default:
			tempCommand.setErrorMessage(ERROR_INVALID_INDICATOR);
		}

		return tempCommand;
	}

	private static ExecutableCommand handlDisplayCommand(String[] arg) {
		return new ExecutableCommand("display");
	}

	private static ExecutableCommand handleClearCommand() {
		return new ExecutableCommand("clear");
	}

	private static ExecutableCommand handleSortCommand(String[] arg) {
		ExecutableCommand tempCommand = new ExecutableCommand("search");

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_INDICATOR);

			return tempCommand;
		}

		String sortIndicator = arg[0].toLowerCase();

		tempCommand.setIndicator(sortIndicator);

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
		String searchKey = arg[1];

		tempCommand.setIndicator(searchIndicator);

		switch (searchIndicator) {
		case "name":
			tempCommand.setTaskName(searchKey);
			break;
		case "deadline":
			Date searchDeadline = (Date) df.parse(searchKey);
			tempCommand.setTaskDeadline(searchDeadline);
			break;
		case "priority":
			tempCommand.setTaskPriority(searchKey);
			break;
		case "location":
			tempCommand.setTaskLocation(searchKey);
			break;
		case "id":
			tempCommand.setTaskId(Integer.parseInt(searchKey));
			break;
		default:
			tempCommand.setErrorMessage(ERROR_INVALID_INDICATOR);
		}

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
}