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

		String[] parsedInput = convertUserInput(userCommand);
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

	private static String[] convertUserInput(String input) {
		int index;
		String temp1, temp2;

		if (input.contains("due")) {
			index = input.indexOf("due");
			temp1 = input.substring(index + 4, index + 6);
			temp2 = input.substring(index + 6);
			input = input.substring(0, index + 3);
			if (temp1.equals("on")) {
				input = input.concat("on");
			} else {
				input = input.concat("at");
			}
			input = input.concat(temp2);
		}

		String[] arg = input.trim().split(" ");
		String[] parsedInput;

		System.out.println(input);

		switch (arg[0]) {
		case StringFormat.ADD:
			parsedInput = handleAddInput(arg);
			break;
		case StringFormat.DELETE:
			parsedInput = handleDeleteInput(arg);
			break;
		case StringFormat.UPDATE:
			parsedInput = handleUpdateInput(arg);
			break;
		case StringFormat.SORT:
			parsedInput = handleSortInput(arg);
			break;
		case StringFormat.SEARCH:
			parsedInput = handleSearchInput(arg);
			break;
		default:
			parsedInput = new String[1];
			parsedInput[0] = arg[0];
		}

		return parsedInput;
	}

	private static String[] handleSearchInput(String[] str) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String[] handleSortInput(String[] str) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String[] handleUpdateInput(String[] str) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String[] handleDeleteInput(String[] str) {
		String[] output = { StringFormat.DELETE, "" };

		if (str.length > 1) {
			output[1] = str[1];
		}

		return output;
	}

	private static String[] handleAddInput(String[] str) {
		String[] output = { StringFormat.ADD, "", "", "", "", "", "" };
		boolean nameExistence = false;
		boolean descriptionExistence = false;
		boolean startTimeExistence = false;
		boolean startDateExistence = false;
		boolean endTimeExistence = false;
		boolean endDateExistence = false;
		boolean locationExistence = false;

		for (int i = 1; i < str.length; i++) {
			String temp = str[i];

			if (nameExistence) {
				output[1] = output[1].concat(temp);
				output[1] = output[1].concat(" ");
				if (temp.contains(StringFormat.DESCRIPTION_INDICATOR)) {
					output[1] = output[1].substring(0, output[1].length() - 2);
					nameExistence = false;
					descriptionExistence = true;
				} else if (str.length > i + 1) {
					if (isInputIndicator(str[i + 1])
							|| (isAmbiguousInputIndicator(str[i + 1]) && isTimeOrDate(str[i + 2]))) {
						nameExistence = false;
					}
				}
			} else if (descriptionExistence) {
				output[2] = output[2].concat(temp);
				output[2] = output[2].concat(" ");

				if (str.length > i + 1) {
					if (isInputIndicator(str[i + 1])
							|| (isAmbiguousInputIndicator(str[i + 1]) && isTimeOrDate(str[i + 2]))) {
						descriptionExistence = false;
					}
				}
			} else if (startDateExistence) {
				output[3] = temp;
				startDateExistence = false;

				if (str.length > i + 1
						&& str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
					startTimeExistence = true;

				}
			} else if (startTimeExistence) {
				if (output[3].contains(StringFormat.DATE_INDICATOR)) {
					output[3] = output[3].concat(" ");
					output[3] = output[3].concat(temp);
				} else {
					output[3] = temp;
				}
				startTimeExistence = false;
			} else if (endDateExistence) {
				output[4] = temp;
				endDateExistence = false;
				if (str.length > i + 1
						&& str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
					endTimeExistence = true;
				}

			} else if (endTimeExistence) {
				if (output[4].contains(StringFormat.DATE_INDICATOR)) {
					output[4] = output[4].concat(" ");
					output[4] = output[4].concat(temp);
				} else {
					output[4] = temp;
				}

				endTimeExistence = false;
			} else if (locationExistence) {
				if (!output[5].equals("")) {
					output[5] = output[5].concat(" ");
					output[5] = output[5].concat(temp);
				} else {
					output[5] = temp.substring(1);
				}

				if (str.length > i + 1 && isInputIndicator(str[i + 1])) {
					locationExistence = false;
				}
			} else if (temp.equals(StringFormat.TO_INDICATOR)
					|| temp.equals(StringFormat.DUE_ON_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						endTimeExistence = true;
					} else {
						endDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.ON_INDICATOR)
					|| temp.equals(StringFormat.FROM_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						startTimeExistence = true;
					} else {
						startDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.DUE_AT_INDICATOR)) {
				endTimeExistence = true;
			} else if (temp.equals(StringFormat.AT_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					startTimeExistence = true;
				}
			} else if (temp.contains(StringFormat.LOCATION_INDICATOR)) {
				output[5] = temp.substring(1);

				if (str.length > i + 1) {
					if (isAmbiguousInputIndicator(str[i + 1])) {
						if (str.length > i + 2 && !isTimeOrDate(str[i + 2])) {
							locationExistence = true;
						}
					} else if (!isInputIndicator(str[i + 1])) {
						locationExistence = true;
					}
				}
			} else if (temp.contains(StringFormat.PRIORITY_INDICATOR)) {
				String priority = temp.substring(1).toLowerCase();

				if (priority.equals(StringFormat.IMPORTANT)) {
					output[6] = StringFormat.HIGH_PRIORITY;
				} else if (priority.equals(StringFormat.UNIMPORTANT)) {
					output[6] = StringFormat.LOW_PRIORITY;
				} else {
					output[6] = StringFormat.INVALID_PRIORITY;
				}
			} else {
				output[1] = output[1].concat(temp);
				output[1] = output[1].concat(" ");

				if (str.length > i + 1) {
					if (temp.contains(StringFormat.DESCRIPTION_INDICATOR)) {
						output[1] = output[1].substring(0,
								output[1].length() - 2);
						descriptionExistence = true;
					} else if (isAmbiguousInputIndicator(str[i + 1])) {
						if (isTimeOrDate(str[i + 2])) {
							nameExistence = false;
						} else {
							nameExistence = true;
						}
					} else if (!isInputIndicator(str[i + 1])) {
						nameExistence = true;
					}
				}
			}
		}

		return output;
	}

	private static boolean isInputIndicator(String indicator) {
		return indicator.equals(StringFormat.TIME_INDICATOR)
				|| indicator.equals(StringFormat.DUE_AT_INDICATOR)
				|| indicator.equals(StringFormat.DUE_ON_INDICATOR)
				|| indicator.contains(StringFormat.LOCATION_INDICATOR)
				|| indicator.contains(StringFormat.PRIORITY_INDICATOR);
	}

	private static boolean isTimeOrDate(String str) {
		return str.contains(StringFormat.TIME_INDICATOR)
				|| str.contains(StringFormat.DATE_INDICATOR);
	}

	private static boolean isAmbiguousInputIndicator(String indicator) {
		return indicator.equals(StringFormat.TO_INDICATOR)
				|| indicator.equals(StringFormat.FROM_INDICATOR)
				|| indicator.equals(StringFormat.AT_INDICATOR)
				|| indicator.equals(StringFormat.ON_INDICATOR);
	}
}