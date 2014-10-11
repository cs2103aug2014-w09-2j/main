//package V1;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Analyzer {
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not indicated.";
	private static final String ERROR_NULL_COMMAND = "Command is not indicated.";
	private static final String ERROR_NULL_TASK_TO_ADD = "Task to be added is not indicated";
	private static final String ERROR_NULL_TASK_TO_SEARCH = "Task to be searched is not indicated";

	public static ExecutableCommand runAnalyzer(Command userInput)
			throws ParseException {
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
		case "display":
			outputCommand = handleDisplayCommand();
			break;
		case "clear":
			outputCommand = handleClearCommand();
			break;
		case "sort":
			outputCommand = handleSortCommand();
			break;
		case "search":
			outputCommand = handleSearchCommand(commandArgument);
			break;
		case "exit":
			outputCommand = handleExitCommand();
			break;
		default:
			outputCommand = null;
		}

		return outputCommand;
	}

	private static ExecutableCommand handleAddCommand(String[] arg)
			throws ParseException {
		ExecutableCommand tempCommand = new ExecutableCommand("add");

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK_TO_ADD);

			return tempCommand;
		}
		tempCommand.setTaskName(arg[0]);

		if (arg.length == 2) {
			tempCommand.setTaskDescription(arg[1]);
		}

		if (arg.length == 3) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date deadline = null;

			deadline = (Date) df.parse(arg[2]);
			tempCommand.setTaskDeadline(deadline);
		}

		if (arg.length == 4) {
			tempCommand.setTaskLocation(arg[3]);
		}

		return tempCommand;
	}

	private static ExecutableCommand handleDeleteCommand(String[] arg) {
		ExecutableCommand tempCommand = new ExecutableCommand("delete");

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK_INDEX);

			return tempCommand;
		}

		if (isInteger(arg[0])) {
			tempCommand.setTaskId(Integer.parseInt(arg[0]));
		} else {
			tempCommand.setTaskName(arg[0]);
		}

		return tempCommand;
	}

	private static ExecutableCommand handleDisplayCommand() {
		return new ExecutableCommand("display");
	}

	private static ExecutableCommand handleClearCommand() {
		return new ExecutableCommand("clear");
	}

	private static ExecutableCommand handleSortCommand() {
		return new ExecutableCommand("sort");
	}

	private static ExecutableCommand handleSearchCommand(String[] arg) {
		ExecutableCommand tempCommand = new ExecutableCommand("search");

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK_TO_SEARCH);

			return tempCommand;
		}

		tempCommand.setTaskName(arg[0]);

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
		String[] cmd = convertStrToArr(userCommand);
		String[] arg = new String[cmd.length - 1];

		for (int i = 1; i < cmd.length; i++) {
			arg[i - 1] = cmd[i].trim();
		}

		return arg;
	}

	private static String[] convertStrToArr(String str) {
		String[] arr = str.trim().split(",");

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