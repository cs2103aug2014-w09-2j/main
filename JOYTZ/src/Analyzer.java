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
	private static final String ERROR_INVALID_TIME = "Format of input %s time is invalid.\n";
	private static final String ERROR_INVALID_EARLIER_TIME = "Input %s time is earlier than current time.\n";

	private static final String[] VALID_INDICATOR = new String[] {
			StringFormat.NAME, StringFormat.DESCRIPTION,
			StringFormat.START_DATE, StringFormat.START_TIME,
			StringFormat.END_DATE, StringFormat.END_TIME, StringFormat.START,
			StringFormat.END, StringFormat.LOCATION, StringFormat.PRIORITY };

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

		String[] parsedInput = UserInputHandler.convertUserInput(userCommand);
		String userAction = getUserAction(parsedInput);
		String[] commandArgument = getArgument(parsedInput);

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
		String startTiming = "";
		String endTiming = "";

		if (arg.length == 0) {
			tempCommand.setErrorMessage(ERROR_NULL_TASK);
			return tempCommand;
		}

		tempCommand.setTaskName(arg[0]);

		if (arg.length >= 2) {
			tempCommand.setTaskDescription(arg[1]);
		}
		if (arg.length >= 3) {
			startTiming = TimeHandler.inputTimingConvertor(arg[2]);
			if (startTiming == null) {
				tempCommand.setErrorMessage(String.format(ERROR_INVALID_TIME,
						StringFormat.START));

				return tempCommand;
			}
		}
		if (arg.length >= 4) {
			endTiming = TimeHandler.inputTimingConvertor(arg[3]);
			if (endTiming == null) {
				tempCommand.setErrorMessage(String.format(ERROR_INVALID_TIME,
						StringFormat.END));

				return tempCommand;
			}
		}

		tempCommand = TimeHandler.timingAnalyzer(startTiming, endTiming,
				tempCommand);

		if (arg.length >= 5) {
			tempCommand.setTaskLocation(arg[4]);
		}
		if (arg.length >= 6) {
			String temp = arg[5].toLowerCase();

			if (!isValidPriority(temp) && !temp.equals("")) {
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

		String indicator = arg[1].toLowerCase();
		String updatedItem;

		tempCommand.setIndicator(indicator);

		if (indicator.equals(StringFormat.START)
				|| indicator.equals(StringFormat.END)
				|| indicator.equals(StringFormat.START_DATE)
				|| indicator.equals(StringFormat.START_TIME)
				|| indicator.equals(StringFormat.END_DATE)
				|| indicator.equals(StringFormat.END_TIME)) {
			updatedItem = TimeHandler.inputTimingConvertor(arg[2]);

			if (updatedItem == null) {
				if (indicator.equals(StringFormat.START)
						|| indicator.equals(StringFormat.START_DATE)
						|| indicator.equals(StringFormat.START_TIME)) {
					tempCommand.setErrorMessage(String.format(
							ERROR_INVALID_TIME, StringFormat.START));
				} else {
					tempCommand.setErrorMessage(String.format(
							ERROR_INVALID_TIME, StringFormat.END));
				}

				return tempCommand;
			}

			if (indicator.equals(StringFormat.START)
					|| indicator.equals(StringFormat.END)) {
				Date checkUpdatedItem = new Date(Long.valueOf(updatedItem));
				Date currentDate = new Date(System.currentTimeMillis());

				if (checkUpdatedItem.before(currentDate)) {
					if (indicator.equals(StringFormat.START)
							|| indicator.equals(StringFormat.START_DATE)
							|| indicator.equals(StringFormat.START_TIME)) {
						tempCommand
								.setErrorMessage(String.format(
										ERROR_INVALID_EARLIER_TIME,
										StringFormat.START));
					} else {

						tempCommand.setErrorMessage(String.format(
								ERROR_INVALID_EARLIER_TIME, StringFormat.END));
					}

					return tempCommand;
				}
			}
		} else if (indicator.equals(StringFormat.PRIORITY)) {
			String check = arg[2].toLowerCase();
			if (check.equals(StringFormat.HIGH_PRIORITY)
					|| check.equals(StringFormat.LOW_PRIORITY)) {
				updatedItem = check;
			} else if (check.equals(StringFormat.IMPORTANT)) {
				updatedItem = StringFormat.HIGH_PRIORITY;
			} else if (check.equals(StringFormat.UNIMPORTANT)) {
				updatedItem = StringFormat.LOW_PRIORITY;
			} else {
				tempCommand.setErrorMessage(ERROR_INVALID_PRIORITY);

				return tempCommand;
			}
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
		} else if (!isValidIndicator(arg[0])
				|| arg[0].equals(StringFormat.START_TIME)
				|| arg[0].equals(StringFormat.END_TIME)
				|| arg[0].equals(StringFormat.START_DATE)
				|| arg[0].equals(StringFormat.END_DATE)) {
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

		String indicator = arg[0].toLowerCase();
		String searchKey;

		tempCommand.setIndicator(indicator);

		if (indicator.equals(StringFormat.START)
				|| indicator.equals(StringFormat.END)
				|| indicator.equals(StringFormat.START_DATE)
				|| indicator.equals(StringFormat.START_TIME)
				|| indicator.equals(StringFormat.END_DATE)
				|| indicator.equals(StringFormat.END_TIME)) {
			searchKey = TimeHandler.inputTimingConvertor(arg[1]);

			if (searchKey == null) {
				if (indicator.equals(StringFormat.START)
						|| indicator.equals(StringFormat.START_DATE)
						|| indicator.equals(StringFormat.START_TIME)) {
					tempCommand.setErrorMessage(String.format(
							ERROR_INVALID_TIME, StringFormat.START));
				} else {
					tempCommand.setErrorMessage(String.format(
							ERROR_INVALID_TIME, StringFormat.END));
				}

				return tempCommand;
			}
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

	private static String getUserAction(String[] parsedInput) {
		return parsedInput[0].toLowerCase();
	}

	private static String[] getArgument(String[] parsedInput) {
		String[] arg = new String[parsedInput.length - 1];

		for (int i = 1; i < parsedInput.length; i++) {
			arg[i - 1] = parsedInput[i].trim();
		}

		return arg;
	}

}