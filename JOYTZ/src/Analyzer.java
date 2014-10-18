//package V1;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.eclipse.ui.internal.services.INestable;

public class Analyzer {
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not indicated.\n";
	private static final String ERROR_NULL_TASK_TO_ADD = "Task to be added is not indicated.\n";
	private static final String ERROR_NULL_TASK_TO_SEARCH = "Task to be searched is not indicated.\n";
	private static final String ERROR_NULL_INDICATOR = "Indicator is not inserted.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";
	private static final String ERROR_INVALID_INDICATOR = "Indicator is invalid.\n";
	
	private static final String ERROR_NULL_TASKNAME = "No task name";
	
	private static final String ERROR_INVALID_COMMAND_ARGUMENT = "Invalid command argument.\n";
	private static final String ERROR_INVALID_COMMAND_ACTION = "Invalid command action.\n";
	private static final String ERROR_INVALID_COMMANDSTRING = "Invalid command String.\n";
	private static final String ERROR_INVALID_COMMAND_OBJECT= "Invalid command object.\n";
	
	enum COMMAND_ACTION{ADD, DELETE, UPDATE, UNDO, REDO, CLEAR, SORT, SEARCH, EXIT, INVALID};

	public static ExecutableCommand analyzeCommand(Command commandObj){
		// assertNotNull("User input is null", userInputCommandString);
		
		ExecutableCommand executableCmd = new ExecutableCommand();
		
		if (commandObj == null){
			executableCmd.setContainError(true);
			executableCmd.setErrorMessage(ERROR_INVALID_COMMAND_OBJECT);
			return executableCmd;
		}
		
		String commandString = commandObj.getUserCommand();
		
		if (commandString == null || commandString.equals("")) {
			executableCmd.setContainError(true);
			executableCmd.setErrorMessage(ERROR_INVALID_COMMANDSTRING);
			return executableCmd;
		}
		
		COMMAND_ACTION commandAction = determineCommandAction(commandString);
		String[] command = analyzeCommandString(commandString);
		
		
		switch (commandAction) {
			case "add":
				executableCmd = handleAddCommand(command);
				break;
			case "delete":
				executableCmd = handleDeleteCommand(command);
				break;
			case "update":
				executableCmd = handleUpdateCommand(command);
				break;
			case "display":
				executableCmd = handlDisplayCommand(command);
				break;
			case "undo":
				executableCmd = handleUndoCommand(command);
				break;
			case "redo":
				executableCmd = handleRedoCommand(command);
				break;
			case "clear":
				executableCmd = handleClearCommand();
				break;
			case "sort":
				executableCmd = handleSortCommand(command);
				break;
			case "search":
				executableCmd = handleSearchCommand(command);
				break;
			case "exit":
				executableCmd = handleExitCommand();
				break;
			default:
				executableCmd.setContainError(true);
				executableCmd.setErrorMessage(ERROR_INVALID_COMMAND_ACTION);
		}

		return executableCmd;
	}
	
	/**
	 * Add 
	 * @param args
	 * @return
	 * @throws ParseException
	 */

	private static ExecutableCommand handleAddCommand(String[] args){
		// assertNotNull("User argument is null", arg);

		ExecutableCommand executableCmd = new ExecutableCommand("add");
		
		if (args.length == 0) {
			executableCmd.setContainError(true);
			executableCmd.setErrorMessage(ERROR_INVALID_COMMAND_ARGUMENT);
			return executableCmd;
		}
		
		// check task name;
		if (args[0].equals("")){
			executableCmd.setContainError(true);
			executableCmd.setErrorMessage(ERROR_NULL_TASKNAME);
			return executableCmd;
		}
		executableCmd.setTaskName(args[0]);
		
		// check
		if (args.length >= 2) {
			executableCmd.setTaskDescription(args[1]);
		}
		if (args.length >= 3) {
			executableCmd.setTaskStartTiming(args[2]);
		}
		if (args.length >= 4) {
			executableCmd.setTaskEndTiming(args[3]);
		}
		if (args.length >= 5) {
			executableCmd.setTaskLocation(args[4]);
		}
		if (args.length >= 6) {
			executableCmd.setTaskPriority(args[5]);
		}

		return executableCmd;
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
		tempCommand.setKeyValue(updatedItem);
		/*
		switch (updateIndicator) {
		case "name":
			tempCommand.setTaskName(updatedItem);
			break;
		case "description":
			tempCommand.setTaskDescription(updatedItem);
			break;
		case "startTiming":
			tempCommand.setTaskStartTiming(updatedItem);
			break;
		case "endTiming":
			tempCommand.setTaskEndTiming(updatedItem);
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
		*/

		return tempCommand;
	}

	private static ExecutableCommand handlDisplayCommand(String[] arg) {
		return new ExecutableCommand("display");
	}

	private static ExecutableCommand handleUndoCommand() {
		return new ExecutableCommand("undo");
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
		case "startTiming":
			tempCommand.setTaskStartTiming(searchKey);
			break;
		case "endTiming":
			tempCommand.setTaskEndTiming(searchKey);
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
	
	private static COMMAND_ACTION determineCommandAction(String commandString){
		String[] cmdString = commandString.trim().split(" ", 2);
		String cmdActionString = cmdString[0].trim().toLowerCase();
		COMMAND_ACTION cmdAction; 
		
		switch(cmdActionString){
			case "add":
				cmdAction = COMMAND_ACTION.ADD;
				break;
			case "delete":
				cmdAction = COMMAND_ACTION.DELETE;
				break;
			case "update":
				cmdAction = COMMAND_ACTION.UPDATE;
				break;
			case "undo":
				cmdAction = COMMAND_ACTION.UNDO;
				break;
			case "redo":
				cmdAction = COMMAND_ACTION.REDO;
				break;
			case "clear":
				cmdAction = COMMAND_ACTION.CLEAR;
				break;
			case "sort":
				cmdAction = COMMAND_ACTION.SORT;
				break;
			case "search":
				cmdAction = COMMAND_ACTION.SEARCH;
				break;
			case "exit":
				cmdAction = COMMAND_ACTION.EXIT;
			default:
				cmdAction = COMMAND_ACTION.INVALID;
		}
		
		return cmdAction;
	}
	private static String[] analyzeCommandString(String commandString){
		String[] result = {};
		String[] cmdString = commandString.trim().split(" ", 2);
		String cmdActionString = cmdString[0].trim().toLowerCase();
		
		switch(cmdActionString){
		case "add":
			result[0] = COMMAND_ACTION.ADD;
		}
	}
	
	/*
	private static String getCommandAction(String userCommand) {
		String[] cmd = convertStrToArr(userCommand);
		return cmd[0].toLowerCase();
	}

	private static String[] getCommandArgument(String userCommand) {
		assertNotNull("User command argument is null", userCommand);

		String[] cmd = convertStrToArr(userCommand);
		String[] arg = new String[cmd.length - 1];

		for (int i = 1; i < cmd.length; i++) {
			arg[i - 1] = cmd[i].trim();
		}

		return arg;
	}
	*/

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
	
	private static Long analyzeUserInputDateString(String userInputDate){
		String[] date = userInputDate.trim().split(" ");
		String dateString;
		String timeString;
		
		// distinguish date (dd/mm/yy) and time (hh:mm:ss)
		if (date[0].contains("/")){
			dateString = date[0];
			timeString = date[1];
		}else {
			dateString = date[1];
			timeString = date[0];
		}
		
		// current date and time
		Date now = new Date(System.currentTimeMillis());
		
		int day = 0;
		int month = 0;
		int year  = 0;
		String[] dateArg = dateString.trim().split("/");
		
		if (dateArg.length == 1){
			day = Integer.parseInt(dateArg[0]);
			month = now.getMonth();
			year = now.getYear();
		}else if (dateArg.length == 2){
			day = Integer.parseInt(dateArg[0]);
			month = Integer.parseInt(dateArg[1]);
			year = now.getYear();
		}else if (dateArg.length == 3){
			day = Integer.parseInt(dateArg[0]);
			month = Integer.parseInt(dateArg[1]);
			year = Integer.parseInt(dateArg[2]);
		}
		
		int minute = 0;
		int hour = 0;
		String[] timeArg = timeString.trim().split(":");
		
		if (timeArg.length == 1){
			hour = Integer.parseInt(dateArg[0]);
			minute = 0;
		}else if (timeArg.length == 2){
			hour = Integer.parseInt(dateArg[0]);
			minute = Integer.parseInt(dateArg[1]);
		}
		
		Date convertedDate = new Date(year, month, day, hour, minute);
		return convertedDate.getTime();
	}
	
	
	
	
	
}