//package V1;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Analyzer {
	public static ExecutableCommand runAnalyzer(Command userInput)
			throws ParseException {
		String userCommand = userInput.getUserCommand();

		if (userCommand == "") {
			return null;
		}

		String userAction = getUserAction(userCommand);
		String commandArgument = getArgument(userCommand);
		ExecutableCommand outputCommand = new ExecutableCommand();

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

	private static ExecutableCommand handleAddCommand(String arg)
			throws ParseException {
		ExecutableCommand tempCommand = new ExecutableCommand("add");
		String[] tempArr = convertStrToArr(arg);

		tempCommand.setDescription(tempArr[0]);

		if (tempArr.length > 1) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date deadline = null;

			deadline = (Date) df.parse(tempArr[1]);
			tempCommand.setDate(deadline);

			if (tempArr.length > 2) {
				String location = null;

				for (int i = 3; i < tempArr.length; i++) {
					location = location.concat(tempArr[i]);
					if (i + 1 != tempArr.length) {
						location = location.concat(" ");
					}
				}

				tempCommand.setLocation(location);
			}
		}

		return tempCommand;
	}

	private static ExecutableCommand handleDeleteCommand(String arg) {
		ExecutableCommand tempCommand = new ExecutableCommand("delete");

		if (isInteger(arg)) {
			tempCommand.setItemId(Integer.parseInt(arg));
		} else {
			tempCommand.setDescription(arg);
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

	private static ExecutableCommand handleSearchCommand(String arg) {
		ExecutableCommand tempCommand = new ExecutableCommand("search");
		tempCommand.setDescription(arg);

		return tempCommand;
	}

	private static ExecutableCommand handleExitCommand() {
		return new ExecutableCommand("exit");
	}

	private static String getUserAction(String userCommand) {
		String[] cmd = convertStrToArr(userCommand);

		return cmd[0].toLowerCase();
	}

	private static String getArgument(String userCommand) {
		String[] cmd = convertStrToArr(userCommand);
		String arg = "";

		for (int i = 1; i < cmd.length; i++) {
			arg = arg.concat(cmd[i]);
			if (i + 1 != cmd.length) {
				arg = arg.concat(" ");
			}
		}

		return arg;
	}

	private static String[] convertStrToArr(String str) {
		String[] arr = str.trim().split("\\s+");

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