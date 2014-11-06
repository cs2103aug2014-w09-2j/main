//@author A0094558N
import static org.junit.Assert.*;

import java.text.ParseException;
import org.junit.Test;

public class TestController {
    private static final String INPUT_INVALID = "sdfsf";
    
    private static final String INPUT_ADD_ALL = "add meeting with friends, discuss about CS2103T project " +
                                                "from 24/02/2015 11:30am to 25/02/2015 11:45am @NUS #medium";
    private static final String INPUT_ADD_NO_PRIORITY = "add meeting with friends, discuss about CS2103T project " +
                                                        "from 24/02/2015 11:30am to 25/02/2015 11:45am @NUS";
    private static final String INPUT_ADD_NO_LOCATION = "add meeting with friends, discuss about CS2103T project " +
                                                        "from 24/02/2015 11:30am to 25/02/2015 11:45am #medium";
    private static final String INPUT_ADD_NO_LOCATION_AND_PRIORITY = "add meeting with friends, " +
                                                                     "discuss about CS2103T project " +
                                                                     "from 24/02/2015 11:30am to 25/02/2015 11:45am";
    private static final String INPUT_ADD_ONLY_NAME = "add meeting with friends";
    
    private static final String EXECUTABLECOMMAND_ACTION_ADD = "add";
    private static final String EXECUTABLECOMMAND_NAME = "meeting with friends";
    private static final String EXECUTABLECOMMAND_DESCRIPTION = "discuss about CS2103T project";
    private static final String EXECUTABLECOMMAND_START_TIME = "1424748600000";     // 24/02/2015 11:30am
    private static final String EXECUTABLECOMMAND_END_TIME = "1424835900000";       // 25/02/2015 11:45am
    private static final String EXECUTABLECOMMAND_LOCATION = "NUS";
    private static final String EXECUTABLECOMMAND_PRIORITY = "medium";
    
    private static final String ERROR_INVALID_ACTION = "Action is invalid.";
    private static final String ERROR_INVALID_NAME = "Name is invalid.";
    private static final String ERROR_INVALID_DESCRIPTION = "Description is invalid.";
    private static final String ERROR_INVALID_START_TIME = "Start time is invalid.";
    private static final String ERROR_INVALID_END_TIME = "End time is invalid";
    private static final String ERROR_INVALID_LOCATION = "Location is invalid";
    private static final String ERROR_INVALID_PRIORITY = "Priority is invalid";
    private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";
    private static final String ERROR_INVALID_COMMAND_ACTION = "Invalid command action: sdfsf.\n";
    
    private static final String ERROR_MESSAGE_MISSING = "Error message missing";
    private static final String ERROR_MESSAGE_WRONG = "Output feedback message wrong";
    private static final String ERROR_MESSAGE_STRING_WRONG = "Output string for table is wrong";
    
    private static final String ERROR_EXECUTOR_UNSUCCESSFUL = "Executor unsuccessful";
    
    private static final String SUCCESS_OUTPUT_ADD = "meeting with friends is added successfully.\n";
    private static final String SUCCESS_OUTPUT_ALL = "meeting with friends~discuss about CS2103T project ~" +
                                                     "2015-02-24 11:30 AM~2015-02-25 11:45 AM~NUS ~medium ";
    private static final String SUCCESS_OUTPUT_NO_PRIORITY = "meeting with friends~discuss about CS2103T project ~" + 
                                                             "2015-02-24 11:30 AM~2015-02-25 11:45 AM~NUS ~ ";
    private static final String SUCCESS_OUTPUT_NO_LOCATION_AND_PRIORITY = "meeting with friends~" +
                                                                          "discuss about CS2103T project ~" +
                                                                          "2015-02-24 11:30 AM~2015-02-25 11:45 AM~ ~ ";
    private static final String SUCCESS_OUTPUT_ONLY_NAME = "meeting with friends~ ~2015-02-24 11:30 AM~ ~ ~medium ";
    
    private static final String EMPTY_STRING = "";

    /*
     * Case with all data filled (valid input partition)
     */
    @Test
    public void unitTestExecutorAdd1() {
        ExecutableCommand parsedCommand = new ExecutableCommand();
        parsedCommand.setAction(EXECUTABLECOMMAND_ACTION_ADD);
        parsedCommand.setTaskName(EXECUTABLECOMMAND_NAME);
        parsedCommand.setTaskDescription(EXECUTABLECOMMAND_DESCRIPTION);
        parsedCommand.setTaskStart(EXECUTABLECOMMAND_START_TIME);
        parsedCommand.setTaskEnd(EXECUTABLECOMMAND_END_TIME);
        parsedCommand.setTaskLocation(EXECUTABLECOMMAND_LOCATION);
        parsedCommand.setTaskPriority(EXECUTABLECOMMAND_PRIORITY);

        Feedback result = Controller.startExecutor(parsedCommand);
        int listSize = result.getTaskStringList().size();

        // Check if the execution has been successful
        assertEquals(ERROR_EXECUTOR_UNSUCCESSFUL, true, result.getResult());
        // Check if the output feedback message is correct
        assertEquals(ERROR_MESSAGE_WRONG, SUCCESS_OUTPUT_ADD, result.getMessageShowToUser());
        // Check if the display string to be used in the table GUI display is correct
        assertEquals(ERROR_MESSAGE_STRING_WRONG, SUCCESS_OUTPUT_ALL, 
                     result.getTaskStringList().get(listSize - 1));
    }

    /*
     * Case with no priority (valid input partition)
     */
    @Test
    public void unitTestExecutorAdd2() {
        ExecutableCommand parsedCommand = new ExecutableCommand();
        parsedCommand.setAction(EXECUTABLECOMMAND_ACTION_ADD);
        parsedCommand.setTaskName(EXECUTABLECOMMAND_NAME);
        parsedCommand.setTaskDescription(EXECUTABLECOMMAND_DESCRIPTION);
        parsedCommand.setTaskStart(EXECUTABLECOMMAND_START_TIME);
        parsedCommand.setTaskEnd(EXECUTABLECOMMAND_END_TIME);
        parsedCommand.setTaskLocation(EXECUTABLECOMMAND_LOCATION);

        Feedback result = Controller.startExecutor(parsedCommand);
        int listSize = result.getTaskStringList().size();

        // Check if the execution has been successful
        assertEquals(ERROR_EXECUTOR_UNSUCCESSFUL, true, result.getResult());
        // Check if the output feedback message is correct
        assertEquals(ERROR_MESSAGE_WRONG, SUCCESS_OUTPUT_ADD, result.getMessageShowToUser());
        // Check if the display string to be used in the table GUI display is correct
        assertEquals(ERROR_MESSAGE_STRING_WRONG, SUCCESS_OUTPUT_NO_PRIORITY, 
                     result.getTaskStringList().get(listSize - 1));
    }

    /*
     * Case with no priority and no location (valid input partition)
     */
    @Test
    public void unitTestExecutorAdd3() {
        ExecutableCommand parsedCommand = new ExecutableCommand();
        parsedCommand.setAction(EXECUTABLECOMMAND_ACTION_ADD);
        parsedCommand.setTaskName(EXECUTABLECOMMAND_NAME);
        parsedCommand.setTaskDescription(EXECUTABLECOMMAND_DESCRIPTION);
        parsedCommand.setTaskStart(EXECUTABLECOMMAND_START_TIME);
        parsedCommand.setTaskEnd(EXECUTABLECOMMAND_END_TIME);

        Feedback result = Controller.startExecutor(parsedCommand);
        int listSize = result.getTaskStringList().size();

        // Check if the execution has been successful
        assertEquals(ERROR_EXECUTOR_UNSUCCESSFUL, true, result.getResult());
        // Check if the output feedback message is correct
        assertEquals(ERROR_MESSAGE_WRONG, SUCCESS_OUTPUT_ADD, result.getMessageShowToUser());
        // Check if the display string to be used in the table GUI display is correct
        assertEquals(ERROR_MESSAGE_STRING_WRONG, SUCCESS_OUTPUT_NO_LOCATION_AND_PRIORITY, 
                     result.getTaskStringList().get(listSize - 1));
    }

    /*
     * Case with only the name, start time, and priority (valid input partition)
     * (boundary case with the first and last argument in the string)
     * (Start time is added so as to prevent dynamic output)
     */
    @Test
    public void unitTestExecutorAdd4() {
        ExecutableCommand parsedCommand = new ExecutableCommand();
        parsedCommand.setAction(EXECUTABLECOMMAND_ACTION_ADD);
        parsedCommand.setTaskName(EXECUTABLECOMMAND_NAME);
        parsedCommand.setTaskStart(EXECUTABLECOMMAND_START_TIME);
        parsedCommand.setTaskPriority(EXECUTABLECOMMAND_PRIORITY);

        Feedback result = Controller.startExecutor(parsedCommand);
        int listSize = result.getTaskStringList().size();

        // Check if the execution has been successful
        assertEquals(ERROR_EXECUTOR_UNSUCCESSFUL, true, result.getResult());
        // Check if the output feedback message is correct
        assertEquals(ERROR_MESSAGE_WRONG, SUCCESS_OUTPUT_ADD, result.getMessageShowToUser());
        // Check if the display string to be used in the table GUI display is correct
        assertEquals(ERROR_MESSAGE_STRING_WRONG, SUCCESS_OUTPUT_ONLY_NAME, 
                     result.getTaskStringList().get(listSize - 1));
    }

    /*
     * Case with invalid action (invalid input partition)
     */
    @Test
    public void unitTestExecutorAdd5() {
        ExecutableCommand parsedCommand = new ExecutableCommand();
        parsedCommand.setAction(INPUT_INVALID);

        Feedback result = Controller.startExecutor(parsedCommand);

        // Check if the execution has been successful
        assertEquals(ERROR_EXECUTOR_UNSUCCESSFUL, false, result.getResult());
        // Check if the output feedback message is correct
        assertEquals(ERROR_MESSAGE_WRONG, ERROR_INVALID_COMMAND_ACTION, 
                     result.getMessageShowToUser());
    }

    // The tests below test the analyzer through the controller
    /*
     * Case with all data filled (valid input partition)
     */
    @Test
    public void unitTestAnalyzerAdd1() throws ParseException {
        Command test = new Command(INPUT_ADD_ALL);
        // Check if the command has been properly broken up and filled
        assertEquals(ERROR_INVALID_ACTION, EXECUTABLECOMMAND_ACTION_ADD, 
                     Controller.analyzeInput(test).getAction());
        assertEquals(ERROR_INVALID_NAME, EXECUTABLECOMMAND_NAME, 
                     Controller.analyzeInput(test).getTaskName());	
        assertEquals(ERROR_INVALID_DESCRIPTION, EXECUTABLECOMMAND_DESCRIPTION,
                     Controller.analyzeInput(test).getTaskDescription());	
        assertEquals(ERROR_INVALID_START_TIME, EXECUTABLECOMMAND_START_TIME, 
                     Controller.analyzeInput(test).getTaskStart());
        assertEquals(ERROR_INVALID_END_TIME, EXECUTABLECOMMAND_END_TIME, 
                     Controller.analyzeInput(test).getTaskEnd());
        assertEquals(ERROR_INVALID_LOCATION, EXECUTABLECOMMAND_LOCATION, 
                     Controller.analyzeInput(test).getTaskLocation());
        assertEquals(ERROR_INVALID_PRIORITY, EXECUTABLECOMMAND_PRIORITY, 
                     Controller.analyzeInput(test).getTaskPriority());
    }

    /*
     * Case with no priority (valid input partition)
     */
    @Test
    public void unitTestAnalyzerAdd2() throws ParseException {	
        Command test = new Command(INPUT_ADD_NO_PRIORITY);
        // Check if the command has been properly broken up and filled
        assertEquals(ERROR_INVALID_ACTION, EXECUTABLECOMMAND_ACTION_ADD, 
                     Controller.analyzeInput(test).getAction());
        assertEquals(ERROR_INVALID_NAME, EXECUTABLECOMMAND_NAME, 
                     Controller.analyzeInput(test).getTaskName());	
        assertEquals(ERROR_INVALID_DESCRIPTION, EXECUTABLECOMMAND_DESCRIPTION,
                     Controller.analyzeInput(test).getTaskDescription());	
        assertEquals(ERROR_INVALID_START_TIME, EXECUTABLECOMMAND_START_TIME, 
                     Controller.analyzeInput(test).getTaskStart());
        assertEquals(ERROR_INVALID_END_TIME, EXECUTABLECOMMAND_END_TIME, 
                     Controller.analyzeInput(test).getTaskEnd());
        assertEquals(ERROR_INVALID_LOCATION, EXECUTABLECOMMAND_LOCATION, 
                     Controller.analyzeInput(test).getTaskLocation());
        assertEquals(ERROR_INVALID_PRIORITY, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskPriority());
    }

    /*
     * Case with no location (valid input partition)
     * This tests the skipping of fields found in the middle of the input string
     */
    @Test
    public void unitTestAnalyzerAdd3() throws ParseException {	
        Command test = new Command(INPUT_ADD_NO_LOCATION);
        // Check if the command has been properly broken up and filled
        assertEquals(ERROR_INVALID_ACTION, EXECUTABLECOMMAND_ACTION_ADD, 
                     Controller.analyzeInput(test).getAction());
        assertEquals(ERROR_INVALID_NAME, EXECUTABLECOMMAND_NAME, 
                     Controller.analyzeInput(test).getTaskName());	
        assertEquals(ERROR_INVALID_DESCRIPTION, EXECUTABLECOMMAND_DESCRIPTION,
                     Controller.analyzeInput(test).getTaskDescription());	
        assertEquals(ERROR_INVALID_START_TIME, EXECUTABLECOMMAND_START_TIME, 
                     Controller.analyzeInput(test).getTaskStart());
        assertEquals(ERROR_INVALID_END_TIME, EXECUTABLECOMMAND_END_TIME, 
                     Controller.analyzeInput(test).getTaskEnd());
        assertEquals(ERROR_INVALID_LOCATION, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskLocation());
        assertEquals(ERROR_INVALID_PRIORITY, EXECUTABLECOMMAND_PRIORITY, 
                     Controller.analyzeInput(test).getTaskPriority());
    }

    /*
     * Case with no priority and no location (valid input partition)
     * This tests the skipping of multiple fields
     */
    @Test
    public void unitTestAnalyzerAdd4() throws ParseException {	
        Command test = new Command(INPUT_ADD_NO_LOCATION_AND_PRIORITY);
        // Check if the command has been properly broken up and filled
        assertEquals(ERROR_INVALID_ACTION, EXECUTABLECOMMAND_ACTION_ADD, 
                     Controller.analyzeInput(test).getAction());
        assertEquals(ERROR_INVALID_NAME, EXECUTABLECOMMAND_NAME, 
                     Controller.analyzeInput(test).getTaskName());	
        assertEquals(ERROR_INVALID_DESCRIPTION, EXECUTABLECOMMAND_DESCRIPTION,
                     Controller.analyzeInput(test).getTaskDescription());	
        assertEquals(ERROR_INVALID_START_TIME, EXECUTABLECOMMAND_START_TIME, 
                     Controller.analyzeInput(test).getTaskStart());
        assertEquals(ERROR_INVALID_END_TIME, EXECUTABLECOMMAND_END_TIME, 
                     Controller.analyzeInput(test).getTaskEnd());
        assertEquals(ERROR_INVALID_LOCATION, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskLocation());
        assertEquals(ERROR_INVALID_PRIORITY, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskPriority());
    }

    /*
     * Case with only the name, start time, and priority (valid input partition)
     * (boundary case with the first and last argument in the string)
     * 
     */
    @Test
    public void unitTestAnalyzerAdd5() throws ParseException {	
        Command test = new Command(INPUT_ADD_ONLY_NAME);
        // Check if the command has been properly broken up and filled
        assertEquals(ERROR_INVALID_ACTION, EXECUTABLECOMMAND_ACTION_ADD, 
                     Controller.analyzeInput(test).getAction());
        assertEquals(ERROR_INVALID_NAME, EXECUTABLECOMMAND_NAME, 
                     Controller.analyzeInput(test).getTaskName());	
        assertEquals(ERROR_INVALID_DESCRIPTION, EMPTY_STRING,
                     Controller.analyzeInput(test).getTaskDescription());	
        assertEquals(ERROR_INVALID_START_TIME, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskStart());
        assertEquals(ERROR_INVALID_END_TIME, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskEnd());
        assertEquals(ERROR_INVALID_LOCATION, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskLocation());
        assertEquals(ERROR_INVALID_PRIORITY, EMPTY_STRING, 
                     Controller.analyzeInput(test).getTaskPriority());
    }

    /*
     * Case with invalid action (invalid input partition)
     */
    @Test
    public void unitTestAnalyzerAdd6() throws ParseException {
        Command test = new Command(INPUT_INVALID);
        // Check if the command has been properly broken up and filled
        assertEquals(ERROR_MESSAGE_MISSING, ERROR_INVALID_COMMAND, 
                     Controller.analyzeInput(test).getErrorMessage());
    }
}
