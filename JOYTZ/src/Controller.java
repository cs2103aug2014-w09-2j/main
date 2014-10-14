//package V1;

import java.text.ParseException;
import java.util.logging.Logger;
import static org.junit.Assert.*;

public class Controller {
	private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());
	
	private static final String ERROR_INVALID_COMMAND = "Invalid command\n";
	private static final String ERROR_INVALID_PARAMETER = "Invalid parameter\n";
	
	private static String inputCommandString;
	private static Command inputCommandObject;
	private static Feedback feedback;
    private static String outputString;
    private static ExecutableCommand parsedCommand;
    
	private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void displayUserOutput(String outputCommand, ExecutableCommand command) {
    	assertNotNull("Output feedback message is null", outputCommand);
    	assertNotNull("ExecutableCommand object is null during display", command);
    	if(outputCommand.length() == 0) {
    		fail("Output feedback message is empty");
    	}
    	
        GUI.displayOutput(outputCommand);
        
		// If there is no error message
        if (command.getErrorMessage().length() == 0) {
        	String action = command.getAction();
        	//String date = command.getTaskDeadline().toString();
			//String name = command.getTaskName();
			//String location = command.getTaskLocation();
			//String description = command.getTaskDescription();
			int taskId = command.getTaskId();

        	parseDisplayTasks(action, taskId);
		}
    }
    
    // Preconditions:
    // getTaskList() strings MUST HAVE: Name~ date~ location~ description~ priority
	// If something is empty: Name~date~~~priority
	// If not, I don't know what the string belongs to after I split it.
    private static void parseDisplayTasks(String action, int taskId) {
    	if (feedback.getTaskList().size() == 0) { // happens after "clear" command
    		GUI.updateTable(0, "No date", "No name", "No location", "No description", action, "No priority");
    	} else {
			for(int i = 0; i < feedback.getTaskList().size(); i++){
				System.out.println("===================\n" +
									"Display string from feedback object: \n" + 
									"	" + feedback.getTaskList().get(i) + "\n" +
									"===================\n");
				
				String[] arr = feedback.getTaskList().get(i).trim().split("~");
				int arrayLength = arr.length;
				
				// updateTable(Table index number, date, name, location, description, action, priority)
				if (arrayLength == 4) {
					GUI.updateTable(i, arr[2], arr[0], arr[3], arr[1], action, "");
				} else if (arrayLength == 5) {
					GUI.updateTable(i, arr[2], arr[0], arr[3], arr[1], action, arr[4]);
				}
			}
    	}
	}

	public static void startController() {
    	inputCommandString = getInput();
    	assertNotNull("Input user string is null", inputCommandString);
    	
    	LOGGER.info("==============\n" +
					"User Input: \n" + 
					"	" + inputCommandString + "\n" + 
					"====================\n");
    	
        inputCommandObject = convertStringToCommand(inputCommandString);
        assertNotNull("Input command object is null", inputCommandObject);
        
        LOGGER.info("==============\n" +
				"Command object: \n" + 
				"	" + inputCommandObject.getUserCommand() + "\n" + 
				"====================\n");
        
    	try {
			parsedCommand = analyzeInput(inputCommandObject);
			assertNotNull("ExecutableCommand object is null after analyzer", parsedCommand);
			
			// Debugging code
			LOGGER.info("==============\n" +
						"After analyzer: \n" + 
						"	Action = " + parsedCommand.getAction() + "\n" + 
						"	Name = " + parsedCommand.getTaskName() + "\n" +
						"	Deadline = " + parsedCommand.getTaskDeadline().toString() + "\n" + 
						"	Description = " + parsedCommand.getTaskDescription() + "\n" +
						"	Location = " + parsedCommand.getTaskLocation() + "\n" +
						"	Priority = " + parsedCommand.getTaskPriority() + "\n" +
						"	Task ID = " + parsedCommand.getTaskId() + "\n" +
						"	Error message = " + parsedCommand.getErrorMessage() + "\n" +
						"	Update indicator = " + parsedCommand.getUpdateIndicator() + "\n" +
						"	Updated task name = " + parsedCommand.getUpdatedTaskName() + "\n" + 
						"====================\n");
			
			if (parsedCommand.getErrorMessage().length() != 0) {
				// There is an error. 
				 displayUserOutput(parsedCommand.getErrorMessage(), parsedCommand);
			} else {	
				if(parsedCommand != null){
					feedback = startExecutor(parsedCommand);
					assertNotNull("Feedback object is null after executor", feedback);

				} else{
					feedback = new Feedback(false);
					feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
				}

				outputString = proceedFeedback(feedback, parsedCommand);
				assertNotNull("Feedback output string is null", outputString);
				
				displayUserOutput(outputString, parsedCommand);
	        }
		} catch (ParseException e) {
			displayUserOutput(ERROR_INVALID_PARAMETER, parsedCommand);
			e.printStackTrace();
		}   
		 
    }
    
    private static Command convertStringToCommand(String inputCommandString) {
    	inputCommandObject = new Command(inputCommandString);
    	
    	return inputCommandObject;
    }
    
    public static ExecutableCommand analyzeInput(Command inputCommandObject) throws ParseException {
        ExecutableCommand parsedCommand = Analyzer.runAnalyzer(inputCommandObject);
        
    	return parsedCommand;
    }
    
    private static String proceedFeedback(Feedback feedback, ExecutableCommand commandObj) {
    	String outputString;
    	
    	if (commandObj == null) {
    		outputString = feedback.getMessageShowToUser();
    	} else if (feedback.getResult()) {
    		outputString = feedback.getMessageShowToUser();  
    	} else {
    		outputString = feedback.getMessageShowToUser();  		
    	}
    	
    	return outputString;	
    }
    
    public static Feedback startExecutor(ExecutableCommand command) {
        return Executor.proceedAnalyzedCommand(command);
    }
    
}
