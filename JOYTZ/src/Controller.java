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
    
    private static void displayUserOutput(String outputCommand, int numTasks) {   
        GUI.displayOutput(outputCommand);
        GUI.updateTable(numTasks);
    }
    
    public static void startController() {
    	inputCommandString = getInput();			
        inputCommandObject = convertStringToCommand(inputCommandString);
        
    	try {
			parsedCommand = analyzeInput(inputCommandObject);
	    	
			if(parsedCommand != null){
	    		feedback = startExecutor(parsedCommand);
	    		// getFeedbackFromExecutor();
	    	}
	    	else{
	    		feedback = new Feedback(false);
	    		feedback.setMessageShowToUser(ERROR_INVALID_COMMAND);
	    	}
	        
	        outputString = proceedFeedback(feedback, parsedCommand);
	        
	        displayUserOutput(outputString, ++numOfTasksAdded);
		} catch (ParseException e) {
			displayUserOutput(ERROR_INVALID_PARAMETER, numOfTasksAdded);
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
    	/*String action = "";
    	String taskName = "";
    	Date time;
    	String location;*/
    	String outputString;
    	
    	/*if (feedback.getResult()) {
	    	action = feedback.getMessageShowToUser();
	    	if (commandObj.getDescription() != null) {
	    		taskName = commandObj.getDescription();
	    	} else {
	    		taskName = "";
	    	}
	    	time = command.getTime();
	    	location = command.getLocation();
    	}*/
    	
    	// TODO: Proper output to be done later
    	// TODO: Put in another function
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
