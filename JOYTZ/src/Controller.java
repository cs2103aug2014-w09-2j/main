//package V1;

import java.text.ParseException;
import java.util.logging.Logger;

public class Controller {
    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private static final String ERROR_INVALID_COMMAND = "Invalid command\n";
    private static final String ERROR_INVALID_PARAMETER = "Invalid parameter\n";

    private static String inputCommandString;
    private static Command inputCommandObject;
    private static Feedback feedback;
    private static String outputString;
    private static ExecutableCommand parsedCommand;
    
    /**
     * A getter method to obtain the user's input from the GUI
     *
     * @return	The user's input string
     * 
     * @author Joel
     */
    private static String getInput() {
        return GUI.getUserInput();
    }
    
    /**
     * Called at the end of controller runtime to process 
     * feedback data for display.
     *
     * @param outputFeedbackString	    Feedback string that is shown to the user
     * 					                after each command
     * @param command			        ExecutableCommand object containing the
     * 					                user's action and taskId
     * 
     * @author Joel
     */
    private static void displayInGUI(String outputFeedbackString, ExecutableCommand command) {
    	assert outputFeedbackString != null;
    	assert command != null;
    	assert outputFeedbackString.length() != 0;
    	
        GUI.displayOutput(outputFeedbackString);
        
        // If there is no error message
        if (command.getErrorMessage().length() == 0) {
        	String action = command.getAction();

        	parseDisplayTasks(action);
		}
    }
    
    /**
     * Parses the feedback data string from the Executor
     * 
     * Precondition: getTaskList() returns a string in the form
     * of "name~ description~ start date~ end date~ location~ priority". If there are
     * empty values, leave it blank. For example: "name~~~end date~~priority"
     * to leave the description and location empty.
     * 
     * @param action	The user's input action (add, delete, etc.)
     * 
     * @author Joel
	 */
    public static void parseDisplayTasks(String action) {
    	boolean isLastItem = false;
    	
    	if (feedback.getTaskList().size() == 0) { 	// happens after "clear" command
    		isLastItem = true;
    		GUI.updateTable(0, "", "", "", "", "", "", action, isLastItem);
    		
    	} else {									// all other commands
			for (int i = 0; i < feedback.getTaskList().size(); i++) {
				System.out.println("===================\n" +
								   "Display string from feedback object: \n" + 
								   "	" + feedback.getTaskList().get(i) + "\n" +
								   "===================\n");
				
				String[] parameterArr = feedback.getTaskList().get(i).trim().split("~");
				
				if (i == feedback.getTaskList().size() - 1) {
					isLastItem = true;
				}
				
				/* Commented because there's too much stuff in the console
				LOGGER.info("==============\n" +
						"After splitting: \n" + 
						"	Action = " + action + "\n" + 
						"	Name = " + parameterArr[0] + "\n" +
						"	Start time = " + parameterArr[2] + "\n" + 
						"	End time = " + parameterArr[3] + "\n" + 
						"	Description = " + parameterArr[1] + "\n" +
						"	Location = " + parameterArr[4] + "\n" +
						"	Priority = " + parameterArr[5] + "\n" +
						"====================\n");
				*/
				
				/*
                 * Parameters: updateTable(Table index number, start time, end time, name, 
                 *                          location, description, priority, action);
                 */ 
				if (parameterArr.length == 5) {
					GUI.updateTable(i, parameterArr[2], parameterArr[3], parameterArr[0], 
									parameterArr[4], parameterArr[1], "", action, isLastItem);
				} else if (parameterArr.length == 6) {
					GUI.updateTable(i, parameterArr[2], parameterArr[3], parameterArr[0], 
									parameterArr[4], parameterArr[1], parameterArr[5], action, isLastItem);
				}
			}
    	}
	}

    /**
     * Starts the controller, and consequently the execution of 
     * the whole data flow. It is called when the user presses "enter"
     * after input in the GUI.
     * 
     * @author Joel
	 */
	public static void startController() {
    	inputCommandString = getInput();
    	assert inputCommandString != null;
    	
    	LOGGER.info("==============\n" +
					"User Input: \n" + 
					"	" + inputCommandString + "\n" + 
					"====================\n");
    	
        inputCommandObject = convertStringToCommand(inputCommandString);
        assert inputCommandObject != null;
        
        /* Commented because there's too much stuff in the console.
        LOGGER.info("==============\n" +
				"Command object: \n" + 
				"	" + inputCommandObject.getUserCommand() + "\n" + 
				"====================\n");
		*/
        
    	try {
			parsedCommand = analyzeInput(inputCommandObject);
			assert parsedCommand != null;
			
			// Debugging code
			LOGGER.info("==============\n" +
						"After analyzer: \n" + 
						"	Action = " + parsedCommand.getAction() + "\n" + 
						"	Name = " + parsedCommand.getTaskName() + "\n" +
						"	Start time = " + parsedCommand.getTaskStartTiming() + "\n" + 
						"	End time = " + parsedCommand.getTaskEndTiming() + "\n" + 
						"	Description = " + parsedCommand.getTaskDescription() + "\n" +
						"	Location = " + parsedCommand.getTaskLocation() + "\n" +
						"	Priority = " + parsedCommand.getTaskPriority() + "\n" +
						"	Task ID = " + parsedCommand.getTaskId() + "\n" +
						"	Error message = " + parsedCommand.getErrorMessage() + "\n" +
						"	Update indicator = " + parsedCommand.getIndicator() + "\n" +
						"====================\n");
			
			if (parsedCommand.getErrorMessage().length() != 0) {	// There is an error
				displayInGUI(parsedCommand.getErrorMessage(), parsedCommand);
			} else {	
				
				if(parsedCommand != null){
					feedback = startExecutor(parsedCommand);
					assert feedback != null;
					
				} else{
					feedback = new Feedback(false);
					feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
				}

				outputString = proceedFeedback(feedback);
				assert outputString != null;
				
				displayInGUI(outputString, parsedCommand);
	        }
		} catch (ParseException e) {
			displayInGUI(ERROR_INVALID_PARAMETER, parsedCommand);
			e.printStackTrace();
		}   
		 
    }
    
	/**
     * Converts the user's input string into a Command object.
     * 
     * @param inputCommandString	The user's input
     * 
     * @return						Command object containing the user's input 
     * 
     * @author Joel
	 */
    private static Command convertStringToCommand(String inputCommandString) {
    	inputCommandObject = new Command(inputCommandString);
    	
    	return inputCommandObject;
    }
    
    /**
     * Starts the analyzer, and passes the Command object to it
     * 
     * @param inputCommandObject	Command object containing the user's input
     * 
     * @return						ExecutableCommand object with parsed data
     * 
     * @author Joel
	 */
    public static ExecutableCommand analyzeInput(Command inputCommandObject) throws ParseException {
        ExecutableCommand parsedCommand = Analyzer.runAnalyzer(inputCommandObject);
        
    	return parsedCommand;
    }
    
    /**
     * Gets the feedback message to show to the user from the feedback object
     * 
     * @param feedback		Feedback object containing the feedback message
     * 
     * @return				String with feedback to show to user after each command
     * 
     * @author Joel
	 */
    private static String proceedFeedback(Feedback feedback) {
    	String outputString = feedback.getMessageShowToUser();  		
    	
    	return outputString;	
    }
    
    /**
     * Starts the executor, and passes the ExecutableCommand object to it
     * 
     * @param command	ExecutableCommand object containing the parsed data
     * 
     * @return			Feedback object with data to display in GUI
     * 
     * @author Joel
	 */
    public static Feedback startExecutor(ExecutableCommand command) {
        return Executor.proceedAnalyzedCommand(command);
    }
}
