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
    
	private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void displayUserOutput(String outputCommand, ExecutableCommand command) {
    	assert outputCommand != null;
    	assert command != null;
    	assert outputCommand.length() != 0;
    	
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
    		GUI.updateTable(0, "", "", "", "", action, "");
    	} else {
			for(int i = 0; i < feedback.getTaskList().size(); i++){
				System.out.println("===================\n" +
									"Display string from feedback object: \n" + 
									"	" + feedback.getTaskList().get(i) + "\n" +
									"===================\n");
				
				String[] arr = feedback.getTaskList().get(i).trim().split("~");
				int arrayLength = arr.length;
				
				String[] dateArr = arr[2].trim().split(" ");
				
				// updateTable(Table index number, date, name, location, description, action, priority)
				if (arrayLength == 4) {
					GUI.updateTable(i, dateArr[0] + " " + dateArr[1] + " " + dateArr[2], arr[0], arr[3], arr[1], action, "");
				} else if (arrayLength == 5) {
					GUI.updateTable(i, dateArr[0] + " " + dateArr[1] + " " + dateArr[2], arr[0], arr[3], arr[1], action, arr[4]);
				}
			}
    	}
	}

	public static void startController() {
    	inputCommandString = getInput();
    	assert inputCommandString != null;
    	
    	LOGGER.info("==============\n" +
					"User Input: \n" + 
					"	" + inputCommandString + "\n" + 
					"====================\n");
    	
        inputCommandObject = convertStringToCommand(inputCommandString);
        assert inputCommandObject != null;
        
        LOGGER.info("==============\n" +
				"Command object: \n" + 
				"	" + inputCommandObject.getUserCommand() + "\n" + 
				"====================\n");
        
    	try {
			parsedCommand = analyzeInput(inputCommandObject);
			assert parsedCommand != null;
			
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
					assert feedback != null;
					
				} else{
					feedback = new Feedback(false);
					feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
				}

				outputString = proceedFeedback(feedback, parsedCommand);
				assert outputString != null;
				
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
