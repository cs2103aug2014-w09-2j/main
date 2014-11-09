//@author A0094558N
import java.text.ParseException;
import java.util.logging.Logger;

public class Controller {
    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private static final String ERROR_INVALID_COMMAND = "Invalid command. Need help? " +
                                                        "Type \"help\" or \"tutorial\".\n";
    private static final String ERROR_INVALID_PARAMETER = "Invalid parameter. Need help? " +
                                                          "Type \"help\" or \"tutorial\".\n";
    private static final String SAVE_SUCCESSFUL = "The Storage is saved to file successfully.\n";
    public static final String EMPTY_LIST = "null";

    private static Command inputCommandObject;
    private static Feedback feedback;
    private static String outputString;
    private static ExecutableCommand parsedCommand;

    /**
     * Called at the end of controller runtime to process 
     * feedback data for display.
     *
     * @param outputFeedbackString	    Feedback string that is shown to the user
     * 					                after each command
     * @param command			        ExecutableCommand object containing the
     * 					                user's action and taskId
     * 
     */
    private static void displayInGUI(String outputFeedbackString, ExecutableCommand command,
                                     boolean isSuccessful) {
        assert outputFeedbackString != null;
        assert command != null;
        assert outputFeedbackString.length() != 0;
        int taskId = 0;
        
        GUI.displayOutput(outputFeedbackString, isSuccessful);

        // If there is no error message
        if (isSuccessful == true) {
            String action = command.getAction().trim();
            if (command.getTaskId().isEmpty() == false) {
                taskId = command.getTaskId().get(0);
            }

            parseDisplayTasks(action, taskId);
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
     * @param taskId    The id of the task given by the user
     * 
     */
    private static void parseDisplayTasks(String action, int taskId) {
        boolean isLastItem = false;
        boolean isHighlightedPassStart = false;
        boolean isHighlightedPassEnd = false;

        if (feedback.getTaskStringList().size() == 0) {
            isLastItem = true;
            GUI.updateTable(0, EMPTY_LIST, "", "", "", "", "", action, taskId,
                            isLastItem, isHighlightedPassStart, isHighlightedPassEnd);

        } else {
            for (int i = 0; i < feedback.getTaskStringList().size(); i++) {
                System.out.println("===================\n" +
                                   "Display string from feedback object: \n" + 
                                   "    " + feedback.getTaskStringList().get(i) + "\n" +
                                   "===================\n");

                String[] parameterArr = processDisplayString(i);
                isHighlightedPassStart = feedback.getPassStartTimeIndicator()[i];
                isHighlightedPassEnd = feedback.getPassEndTimeListIndicator()[i];

                if (i == feedback.getTaskStringList().size() - 1) {
                    isLastItem = true;
                }

                /* Commented because there's too much stuff in the console*/
				LOGGER.info("==============\n" +
    						"After splitting: \n" + 
    						"    Action = " + action + "\n" + 
    						"    Name = " + parameterArr[0] + "\n" +
    						"    Start time = " + parameterArr[2] + "\n" + 
    						"    End time = " + parameterArr[3] + "\n" + 
    						"    Description = " + parameterArr[1] + "\n" +
    						"    Location = " + parameterArr[4] + "\n" +
    						"    Priority = " + parameterArr[5] + "\n" +
    						"====================\n");
                 

                /*
                 * Parameters: updateTable(Table index number, start time, end time, name, 
                 *                         location, description, priority, action);
                 */ 
                assert parameterArr.length == 6;
                GUI.updateTable(i, parameterArr[2], parameterArr[3], parameterArr[0], 
                                parameterArr[4], parameterArr[1], parameterArr[5], action, taskId,
                                isLastItem, isHighlightedPassStart, isHighlightedPassEnd);
            }
        }
    }

    /**
     * Processes the string obtained from the task list that is stored
     * in the feedback object. This method will split the string
     * via the '~' token, and then trim it.
     * 
     * @param i     The index of the task in the task list
     * 
     * @return      A string array containing the parameters to be displayed
     * 
     */
    private static String[] processDisplayString(int i) {
        String[] parameterArr = feedback.getTaskStringList().get(i).split("~");
        for(int k = 0; k < parameterArr.length; k++) {
            parameterArr[k] = parameterArr[k].trim();
        }
        return parameterArr;
    }

    /**
     * Starts the controller, and consequently the execution of 
     * the whole data flow. It is called when the user presses "enter"
     * after input that requires processing and data manipulation in the GUI.
     * 
     * @param inputCommandString    The user's input
     * 
     * @return                      Feedback object used exclusively for testing
     * 
     */
    public static Feedback startController(String inputCommandString) {
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
                        "    Action = " + parsedCommand.getAction() + "\n" + 
                        "    Name = " + parsedCommand.getTaskName() + "\n" +
                        "    Start time = " + parsedCommand.getTaskStart() + "\n" + 
                        "    End time = " + parsedCommand.getTaskEnd() + "\n" + 
                        "    Description = " + parsedCommand.getTaskDescription() + "\n" +
                        "    Location = " + parsedCommand.getTaskLocation() + "\n" +
                        "    Priority = " + parsedCommand.getTaskPriority() + "\n" +
                        "    Task ID = " + parsedCommand.getTaskId() + "\n" +
                        "    Search key = " + parsedCommand.getKey() + "\n" +
                        "    Error message = " + parsedCommand.getErrorMessage() + "\n" +
                        "    Update indicator = " + parsedCommand.getIndicator() + "\n" +
                        "====================\n");

            if (parsedCommand.getErrorMessage().length() != 0) {	// There is an error
                outputString = parsedCommand.getErrorMessage();
                displayInGUI(outputString, parsedCommand, false);
            } else {	

                if(parsedCommand != null){
                    feedback = startExecutor(parsedCommand);
                    assert feedback != null;

                } else {
                    feedback = new Feedback(false);
                    feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
                }

                outputString = getFeedbackMessage(feedback);
                boolean isFeedbackSuccess = feedback.getResult();
                assert outputString != null;
                
                if (outputString.equals(StringFormat.IO_MESSAGE_TASK_LIST_FILE_NOT_EXIST)) {
                    GUI.openTutorial();
                }
                
                if (outputString.equals(SAVE_SUCCESSFUL)) {
                    System.exit(0);
                } else if (GUI.getShell() != null){      // Only display in GUI if the window is running
                    displayInGUI(outputString, parsedCommand, isFeedbackSuccess);
                }
            }
        } catch (ParseException e) {
            displayInGUI(ERROR_INVALID_PARAMETER, parsedCommand, true);
        }   
        return feedback;    // used exclusively for JUnit testing
    }

    /**
     * Converts the user's input string into a Command object.
     * 
     * @param inputCommandString	The user's input
     * 
     * @return						Command object containing the user's input 
     * 
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
     */
    private static String getFeedbackMessage(Feedback feedback) {
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
     */
    public static Feedback startExecutor(ExecutableCommand command) {
        return Executor.proceedAnalyzedCommand(command);
    }
}
