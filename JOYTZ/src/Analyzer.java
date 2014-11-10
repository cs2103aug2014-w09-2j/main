//@author A0112162Y
import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;
import java.util.Date;
import java.text.ParseException;

public class Analyzer {

	private static final Logger LOGGER = Logger.getLogger(Analyzer.class
			.getName());

	/**
	 * Runs Analyzer
	 * 
	 * @param userInput
	 *            The user input
	 * @return ExecutableCommand containing all relevant attributes according to
	 *         the user action
	 * @throws ParseException
	 *             If there is an error during parsing operation
	 */
	public static ExecutableCommand runAnalyzer(Command userInput)
			throws ParseException {
		assertNotNull("User input is null", userInput);

		String userCommand = userInput.getUserCommand();
		ExecutableCommand outputCommand = new ExecutableCommand();

		if (userCommand == StringFormat.EMPTY) {
			outputCommand.setErrorMessage(StringFormat.ERROR_NULL_COMMAND);
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
			outputCommand = handleDisplayCommand(commandArgument);
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
		case StringFormat.DONE:
			outputCommand = handleDoneCommand(commandArgument);
			break;
		case StringFormat.EXIT:
			outputCommand = handleExitCommand();
			break;
		case StringFormat.RELOAD:
			outputCommand = handleReloadCommand();
			break;
		default:
			outputCommand.setErrorMessage(StringFormat.ERROR_INVALID_COMMAND);
		}

		// Debugging code
		LOGGER.info("==============\n" + "After analyzing (Analyzer):  \n"
				+ "	Action = "
				+ outputCommand.getAction()
				+ "\n"
				+ "	Name = "
				+ outputCommand.getTaskName()
				+ "\n"
				+ "	Start time = "
				+ outputCommand.getTaskStart()
				+ "\n"
				+ "	End time = "
				+ outputCommand.getTaskEnd()
				+ "\n"
				+ "	Description = "
				+ outputCommand.getTaskDescription()
				+ "\n"
				+ "	Location = "
				+ outputCommand.getTaskLocation()
				+ "\n"
				+ "	Priority = "
				+ outputCommand.getTaskPriority()
				+ "\n"
				+ "	Task index = "
				+ outputCommand.getTaskId()
				+ "\n"
				+ "	Indicator = "
				+ outputCommand.getIndicator()
				+ "\n"
				+ "	Key = "
				+ outputCommand.getKey()
				+ "\n"
				+ "	Error message = "
				+ outputCommand.getErrorMessage()
				+ "\n"
				+ "====================\n");

		return outputCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "add" action and sets all
	 * relevant information: task name, task description, task start time, task
	 * end time, task location, task priority. Task name must be set while the
	 * rest can be omitted.
	 * 
	 * @param arg
	 *            The user argument
	 * @return ExecutableCommand with "add" action containing all relevant
	 *         attributes stated above wherever applicable, or error message if
	 *         there is error encountered
	 * @throws ParseException
	 *             If there is an error during parsing operation
	 */
	private static ExecutableCommand handleAddCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(StringFormat.ADD);
		String startTiming = StringFormat.EMPTY;
		String endTiming = StringFormat.EMPTY;
		String temp;

		if (arg[0] == StringFormat.EMPTY) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_TASK);
			return tempCommand;
		}

		tempCommand.setTaskName(arg[0]);

		if (arg.length >= 2) {
			tempCommand.setTaskDescription(arg[1]);
		}
		if (arg.length >= 3) {
			temp = arg[2];

			if (temp.equals(StringFormat.INVALID)) {
				tempCommand.setErrorMessage(String.format(
						StringFormat.ERROR_INVALID_TIME, StringFormat.START));

				return tempCommand;
			} else if (temp.equals(StringFormat.NULL)) {
				tempCommand.setErrorMessage(StringFormat.ERROR_NULL_ARGUMENT);

				return tempCommand;
			}

			startTiming = TimeHandler.inputTimeConvertor(temp);

			if (startTiming == null) {
				tempCommand.setErrorMessage(String.format(
						StringFormat.ERROR_INVALID_TIME, StringFormat.START));

				return tempCommand;
			}
		}
		if (arg.length >= 4) {
			temp = arg[3];

			if (temp.equals(StringFormat.INVALID)) {
				tempCommand.setErrorMessage(String.format(
						StringFormat.ERROR_INVALID_TIME, StringFormat.END));

				return tempCommand;
			} else if (temp.equals(StringFormat.NULL)) {
				tempCommand.setErrorMessage(StringFormat.ERROR_NULL_ARGUMENT);

				return tempCommand;
			}

			endTiming = TimeHandler.inputTimeConvertor(temp);

			if (endTiming == null) {
				tempCommand.setErrorMessage(String.format(
						StringFormat.ERROR_INVALID_TIME, StringFormat.END));

				return tempCommand;
			}
		}

		tempCommand = TimeHandler.timeAnalyzer(startTiming, endTiming,
				tempCommand);

		if (arg.length >= 5) {
			tempCommand.setTaskLocation(arg[4]);
		}
		if (arg.length >= 6) {
			temp = arg[5].toLowerCase();

			if (!StringFormat.isValidPriority(temp)
					&& !temp.equals(StringFormat.EMPTY)) {
				tempCommand
						.setErrorMessage(StringFormat.ERROR_INVALID_PRIORITY);
			} else {
				tempCommand.setTaskPriority(temp);
			}
		}

		return tempCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "delete" action and sets all
	 * relevant information: task index. Task index must be set and it can be
	 * indicated more than once.
	 * 
	 * @param arg
	 *            The user argument
	 * @return ExecutableCommand with "delete" containing all relevant
	 *         attributes stated above wherever applicable, or error message if
	 *         there is error encountered
	 */
	private static ExecutableCommand handleDeleteCommand(String[] arg) {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(
				StringFormat.DELETE);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_TASK_INDEX);
			return tempCommand;
		}

		for (int i = 0; i < arg.length; i++) {
			if (!isInteger(arg[i]) || Integer.parseInt(arg[i]) < 1) {
				tempCommand
						.setErrorMessage(StringFormat.ERROR_INVALID_TASK_INDEX);
				return tempCommand;
			}

			tempCommand.setTaskId(Integer.parseInt(arg[i]));
		}

		return tempCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "update" action and sets all
	 * relevant information: task index, update indicator, update key. All
	 * attributes stated above must be set.
	 * 
	 * @param arg
	 *            The user Argument
	 * @return ExecutableCommand with "update" action containing all relevant
	 *         attributes stated above wherever applicable, or error message if
	 *         there is error encountered
	 * @throws ParseException
	 *             If there is an error during parsing operation
	 */
	private static ExecutableCommand handleUpdateCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(
				StringFormat.UPDATE);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_TASK_INDEX);
			return tempCommand;
		} else if (!isInteger(arg[0]) || Integer.parseInt(arg[0]) < 1) {
			tempCommand.setErrorMessage(StringFormat.ERROR_INVALID_TASK_INDEX);
			return tempCommand;
		} else if (arg.length == 1) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_INDICATOR);
			return tempCommand;
		} else if (!StringFormat.isValidIndicator(arg[1])) {
			tempCommand.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);
			return tempCommand;
		} else if (arg.length == 2) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_ARGUMENT);
			return tempCommand;
		}

		String taskToBeUpdated = arg[0];
		tempCommand.setTaskId(Integer.parseInt(taskToBeUpdated));

		String indicator = arg[1].toLowerCase();
		String updatedItem;

		tempCommand.setIndicator(indicator);

		if (indicator.equals(StringFormat.START)
				|| indicator.equals(StringFormat.END)) {
			if (arg.length < 3) {
				tempCommand.setErrorMessage(StringFormat.ERROR_NULL_ARGUMENT);

				return tempCommand;
			}
			updatedItem = TimeHandler.inputTimeConvertor(arg[2]);

			if (updatedItem == null) {
				if (indicator.equals(StringFormat.START)) {
					tempCommand.setErrorMessage(String
							.format(StringFormat.ERROR_INVALID_TIME,
									StringFormat.START));
				} else {
					tempCommand.setErrorMessage(String.format(
							StringFormat.ERROR_INVALID_TIME, StringFormat.END));
				}

				return tempCommand;
			}

			if (indicator.equals(StringFormat.START)
					|| indicator.equals(StringFormat.END)) {
				Date checkUpdatedItem = new Date(Long.valueOf(updatedItem));
				Date currentDate = new Date(System.currentTimeMillis());

				if (checkUpdatedItem.before(currentDate)) {
					if (indicator.equals(StringFormat.START)) {
						tempCommand.setErrorMessage(String.format(
								StringFormat.ERROR_INVALID_EARLIER_TIME,
								StringFormat.START));
					} else {

						tempCommand.setErrorMessage(String.format(
								StringFormat.ERROR_INVALID_EARLIER_TIME,
								StringFormat.END));
					}

					return tempCommand;
				}
			}
		} else if (indicator.equals(StringFormat.PRIORITY)) {
			String check = arg[2].toLowerCase();
			if (check.equals(StringFormat.HIGH_PRIORITY)
					|| check.equals(StringFormat.LOW_PRIORITY)
					|| check.equals(StringFormat.MEDIUM_PRIORITY)) {
				updatedItem = check;
			} else if (check.equals(StringFormat.IMPORTANT)) {
				updatedItem = StringFormat.HIGH_PRIORITY;
			} else if (check.equals(StringFormat.UNIMPORTANT)) {
				updatedItem = StringFormat.LOW_PRIORITY;
			} else {
				tempCommand
						.setErrorMessage(StringFormat.ERROR_INVALID_PRIORITY);

				return tempCommand;
			}
		} else {
			updatedItem = arg[2];
		}

		tempCommand.setKey(updatedItem);

		return tempCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "display" action and sets all
	 * relevant information: display indicator. Display indicator can be
	 * ignored. In such case, it will be recognized as default display action
	 * which will display the main task list.
	 * 
	 * @param arg
	 *            The user argument
	 * @return ExecutableCommand with "display" action containing all relevant
	 *         attributes stated above wherever applicable, or error message if
	 *         there is error encountered
	 */
	private static ExecutableCommand handleDisplayCommand(String[] arg) {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(
				StringFormat.DISPLAY);

		if (arg.length == 0) {
			tempCommand.setIndicator(StringFormat.MAIN_TASK_LIST);
			return tempCommand;
		} else {
			if (arg[0].equals(StringFormat.DONE)) {
				tempCommand.setIndicator(StringFormat.DONE_TASK_LIST);
			} else {
				tempCommand
						.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);
			}
		}
		return tempCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "search" action and sets all
	 * relevant information: search indicator and search key. Search indicator
	 * and search key must be set and they can be indicated more than once.
	 * 
	 * @param arg
	 *            The user argument
	 * @return ExecutableCommand with "search" action containing all relevant
	 *         attributes stated above wherever applicable, or error message if
	 *         there is error encountered
	 * @throws ParseException
	 *             If there is an error during parsing operation
	 */
	private static ExecutableCommand handleSearchCommand(String[] arg)
			throws ParseException {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(
				StringFormat.SEARCH);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_INDICATOR);

			return tempCommand;
		} else if (arg.length == 1 && arg[0].equals(StringFormat.INVALID)) {
			tempCommand.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);

			return tempCommand;
		} else if (arg.length == 1) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_ARGUMENT);

			return tempCommand;
		}

		boolean priorityExistence = false;

		for (int i = 0; i < arg.length; i++) {
			String temp = arg[i];
			boolean indicatorExistence = i % 2 == 0 ? true : false;
			boolean argumentExistence = i % 2 != 0 ? true : false;

			if (temp.equals(StringFormat.INVALID)) {
				tempCommand
						.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);

				return tempCommand;
			}

			if (indicatorExistence) {
				tempCommand.setIndicator(temp.toLowerCase());

				if (temp.equals(StringFormat.PRIORITY)) {
					priorityExistence = true;
				}
			} else if (argumentExistence) {
				if (StringFormat.isTimeOrDate(temp)) {
					String indicator = arg[i - 1];
					String searchKey = TimeHandler.inputTimeConvertor(temp);

					if (searchKey == null) {
						if (indicator.equals(StringFormat.START)) {
							tempCommand.setErrorMessage(String.format(
									StringFormat.ERROR_INVALID_TIME,
									StringFormat.START));
						} else {
							tempCommand.setErrorMessage(String.format(
									StringFormat.ERROR_INVALID_TIME,
									StringFormat.END));
						}

						return tempCommand;
					} else {
						tempCommand.setKey(searchKey);
					}
				} else if (priorityExistence) {
					if (!StringFormat.isValidPriority(temp)) {
						tempCommand
								.setErrorMessage(StringFormat.ERROR_INVALID_PRIORITY);

						return tempCommand;
					} else {
						switch (temp) {
						case StringFormat.IMPORTANT:
							tempCommand.setKey(StringFormat.HIGH_PRIORITY);
							break;
						case StringFormat.UNIMPORTANT:
							tempCommand.setKey(StringFormat.LOW_PRIORITY);
							break;
						default:
							tempCommand.setKey(temp.toLowerCase());
						}
					}
				} else {
					tempCommand.setKey(temp);
				}
			}
		}

		return tempCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "sort" action and sets all
	 * relevant information: sort indicator. Sort indicator must be set and it
	 * can be indicated more than once.
	 * 
	 * @param arg
	 *            The user argument
	 * @return ExecutableCommand with "sort" action containing all relevant
	 *         attributes stated above wherever applicable, or error message if
	 *         there is error encountered
	 */
	private static ExecutableCommand handleSortCommand(String[] arg) {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(StringFormat.SORT);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_INDICATOR);

			return tempCommand;
		}

		for (int i = 0; i < arg.length; i++) {
			String sortIndicator = arg[i].toLowerCase();

			if (!StringFormat.isValidIndicator(sortIndicator)) {
				tempCommand
						.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);

				return tempCommand;
			}
			tempCommand.setIndicator(sortIndicator);
		}

		return tempCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "done" action and sets all
	 * relevant information: task index. Task index must be set and it can
	 * indicated more than once.
	 * 
	 * @param arg
	 *            The user argument
	 * @return ExecutableCommand with "done" action containing all relevant
	 *         attributes stated above wherever applicable, or error message if
	 *         there is error encountered
	 */
	private static ExecutableCommand handleDoneCommand(String[] arg) {
		assertNotNull("User argument is null", arg);

		ExecutableCommand tempCommand = new ExecutableCommand(StringFormat.DONE);

		if (arg.length == 0) {
			tempCommand.setErrorMessage(StringFormat.ERROR_NULL_TASK_INDEX);
			return tempCommand;
		}

		for (int i = 0; i < arg.length; i++) {
			if (!isInteger(arg[i]) || Integer.parseInt(arg[i]) < 1) {
				tempCommand
						.setErrorMessage(StringFormat.ERROR_INVALID_TASK_INDEX);
				return tempCommand;
			}

			tempCommand.setTaskId(Integer.parseInt(arg[i]));
		}

		return tempCommand;
	}

	/**
	 * Creates an ExecutableCommand object with "undo" action.
	 * 
	 * @return ExecutableCommand with "undo" action
	 */
	private static ExecutableCommand handleUndoCommand() {
		return new ExecutableCommand(StringFormat.UNDO);
	}

	/**
	 * Creates an ExecutableCommand object with "redo" action.
	 * 
	 * @return ExecutableCommand with "redo" action
	 */
	private static ExecutableCommand handleRedoCommand() {
		return new ExecutableCommand(StringFormat.REDO);
	}

	/**
	 * Creates an ExecutableCommand object with "clear" action.
	 * 
	 * @return ExecutableCommand with "clear" action
	 */
	private static ExecutableCommand handleClearCommand() {
		return new ExecutableCommand(StringFormat.CLEAR);
	}

	/**
	 * Creates an ExecutableCommand object with "exit" action.
	 * 
	 * @return ExecutableCommand with "exit" action
	 */
	private static ExecutableCommand handleExitCommand() {
		return new ExecutableCommand(StringFormat.EXIT);
	}

	/**
	 * Creates an ExecutableCommand object with "reload" action.
	 * 
	 * @return ExecutableCommand with "reload" action
	 */
	private static ExecutableCommand handleReloadCommand() {
		return new ExecutableCommand(StringFormat.RELOAD);
	}

	/**
	 * Checks if the input string is a valid integer or not.
	 * 
	 * @param input
	 *            Input string
	 * @return True if the input string is a valid integer, otherwise return
	 *         false
	 */
	private static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Extracts action out of the parsedInput.
	 * 
	 * @param parsedInput
	 *            Parsed input in string array
	 * @return The action
	 */
	private static String getUserAction(String[] parsedInput) {
		return parsedInput[0].toLowerCase();
	}

	/**
	 * Extracts argument out of the parsedInput.
	 * 
	 * @param parsedInput
	 *            Parsed input in string array
	 * @return The argument
	 */
	private static String[] getArgument(String[] parsedInput) {
		String[] arg = new String[parsedInput.length - 1];

		for (int i = 1; i < parsedInput.length; i++) {
			arg[i - 1] = parsedInput[i].trim();
		}

		return arg;
	}

}