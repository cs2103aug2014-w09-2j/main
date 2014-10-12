//package V1;

import java.text.ParseException;

// import java.sql.Date;

public class Controller {
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
        
        String date = command.getTaskDeadline().toString();
        String name = command.getTaskName();
        String location = command.getTaskLocation();
        String description = command.getTaskDescription();
        int taskId = command.getTaskId();
        String action = command.getAction();
        
        // Debugging code
        System.out.println("Controller, displaying output: " + 
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
    
    public static void startController() {
    	inputCommandString = getInput();			
        inputCommandObject = convertStringToCommand(inputCommandString);
        
    	try {
			parsedCommand = analyzeInput(inputCommandObject);
			
			// Debugging code
	    	System.out.println("Controller, after analyzer: " + 
	    						parsedCommand.getAction() + " " + 
	    						parsedCommand.getTaskName() + " " +
	    						parsedCommand.getTaskDeadline().toString() + " " + 
	    						parsedCommand.getTaskDescription() + " " +
	    						parsedCommand.getTaskLocation());
			//displayUserOutput("success", parsedCommand);
	    	
	    	
			if(parsedCommand != null){
	    		feedback = startExecutor(parsedCommand);
	    		// getFeedbackFromExecutor();
	    	}
	    	else{
	    		feedback = new Feedback(false);
	    		feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
	    	}
	        
	        outputString = proceedFeedback(feedback, parsedCommand);
	        
	        System.out.println("Controller, before display: " + parsedCommand.getAction() + " " + parsedCommand.getTaskName());
	        displayUserOutput(outputString, parsedCommand);
	        
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
