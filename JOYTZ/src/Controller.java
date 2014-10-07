//package V1;

import java.text.ParseException;

// import java.sql.Date;

public class Controller {
	private static final String ERROR_INVALID_COMMAND = "Invalid Command";
	
	private static String inputCommandString;
	private static Command inputCommandObject;
	private static Feedback feedback;
    private static String outputString;
    private static ExecutableCommand parsedCommand;
    
	private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void displayUserOutput(String outputCommand) {   
        GUI.displayOutput(outputCommand);
    }
    
    public static void startController() {
    	inputCommandString = getInput();			
        inputCommandObject = convertStringToCommand(inputCommandString);
        
    	try {
			parsedCommand = analyzeInput(inputCommandObject);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    	feedback =startExecutor(parsedCommand);
        // getFeedbackFromExecutor();
        
        outputString = proceedFeedback(feedback, parsedCommand);
        displayUserOutput(outputString);
        
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
    	String action = "";
    	String taskName = "";
    	// Date time;
    	// String location;
    	String outputString;
    	
    	if (feedback.getResult()) {
	    	action = feedback.getMessageShowToUser();
	    	if (commandObj.getDescription() != null) {
	    		taskName = commandObj.getDescription();
	    	} else {
	    		taskName = "";
	    	}
	    	//time = command.getTime();
	    	//location = command.getLocation();
    	}
    	
    	// TODO: Proper output to be done later
    	// TODO: Put in another function
    	if (feedback.getMessageShowToUser().equals(ERROR_INVALID_COMMAND)) {
    		outputString = "Please enter a valid command!";
    	} else if (feedback.getResult()) {
    		outputString = "Success! " + taskName + action; // + 
			   				//" at " + location + " on " + time.toString();
    	} else {
    		outputString = "Action failed! " + "(" + action + " " +
    						taskName + ")";// + " at " + location + " on " + time.toString() + ")";    		
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
