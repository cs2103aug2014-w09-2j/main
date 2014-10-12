//package V1;

import java.text.ParseException;
import java.util.logging.Logger;

// import java.sql.Date;

public class Controller {
	private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());
	
	private static final String ERROR_INVALID_COMMAND = "Invalid command\n";
	private static final String ERROR_INVALID_PARAMETER = "Invalid parameter\n";
	
	private static String inputCommandString;
	private static Command inputCommandObject;
	private static Feedback feedback;
    private static String outputString;
    private static ExecutableCommand parsedCommand;
    private static int numOfTasksAdded;
    
	private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void displayUserOutput(String outputCommand, ExecutableCommand command) {   
        GUI.displayOutput(outputCommand);
        
		// If there is no error message
        if (command.getErrorMessage().length() == 0) {
        	parseDisplayTasks();
			String date = command.getTaskDeadline().toString();
			String name = command.getTaskName();
			String location = command.getTaskLocation();
			String description = command.getTaskDescription();
			int taskId = command.getTaskId();
			String action = command.getAction();
			
			// Debugging code
			LOGGER.info("Controller, displaying output: " + 
						parsedCommand.getAction() + " " + 
						parsedCommand.getTaskName() + " " +
						parsedCommand.getTaskDeadline().toString() + " " + 
						parsedCommand.getTaskDescription() + " " +
						parsedCommand.getTaskLocation());
			
			if (action.equals("add")) {
				numOfTasksAdded++;	
			} else if (action.equals("delete")) {
				numOfTasksAdded--;
			}
			
			GUI.updateTable(numOfTasksAdded, date, name, location, description, action, taskId);
		}
    }
    
    // Call updateTable() in each iteration
    private static void parseDisplayTasks() {
		// TODO Auto-generated method stub
    	if (parsedCommand.getAction().equals("display")){
			for(int i = 0; i < feedback.dispalyList.size(); i++){
				System.out.println(feedback.dispalyList.get(i));
			}
		}
    	//String[] arr = str.trim().split(",");

		//return arr;
	}

	public static void startController() {
    	LOGGER.info("Controller started");
    	
    	inputCommandString = getInput();			
        inputCommandObject = convertStringToCommand(inputCommandString);
        
    	try {
			parsedCommand = analyzeInput(inputCommandObject);
			
			if (parsedCommand.getErrorMessage().length() != 0) {
				// There is an error. 
				 displayUserOutput(parsedCommand.getErrorMessage(), parsedCommand);
			} else {
			
				// Debugging code
				LOGGER.info("Controller, after analyzer: " + 
							parsedCommand.getAction() + " " + 
							parsedCommand.getTaskName() + " " +
							parsedCommand.getTaskDeadline().toString() + " " + 
							parsedCommand.getTaskDescription() + " " +
							parsedCommand.getTaskLocation());
				//displayUserOutput("success", parsedCommand);
				
				
				if(parsedCommand != null){
					feedback = startExecutor(parsedCommand);
					// getFeedbackFromExecutor();
				} else{
					feedback = new Feedback(false);
					feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
				}

				outputString = proceedFeedback(feedback, parsedCommand);
				
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
    
    private static ExecutableCommand analyzeInput(Command inputCommandObject) throws ParseException {
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
    
    // TODO
    private static Feedback startExecutor(ExecutableCommand command) {
        return Executor.proceedAnalyzedCommand(command);
    }
    
    // TODO
    //private static Feedback getFeedbackFromExecutor() {
    //   return Executor.getFeedback();
    //}
}
