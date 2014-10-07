package V1;

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
       
        passToExecutor(parsedCommand);
        feedback = getFeedbackFromExecutor();
        
        outputString = proceedFeedback(feedback);
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
    
    private static String proceedFeedback(Feedback feedback) {
    	String action = "";
    	String description = "";
    	// Date time;
    	// String location;
    	String outputString;
    	
    	if (feedback.getResult()) {
	    	action = feedback.getAction();
	    	description = feedback.getDescription();
	    	//time = command.getTime();
	    	//location = command.getLocation();
    	}
    	
    	// TODO: Proper output to be done later
    	// TODO: Put in another function
    	if (feedback.getMessageShowToUser().equals(ERROR_INVALID_COMMAND)) {
    		outputString = "Please enter a valid command!";
    	} else if (feedback.getResult()) {
    		outputString = "Success! " + action + " " + description; // + 
			   				//" at " + location + " on " + time.toString();
    	} else {
    		outputString = "Action failed! " + "(" + action + " " +
    						description + ")";// + " at " + location + " on " + time.toString() + ")";    		
    	}
    	
    	return outputString;	
    }
    
    // TODO
    private static void passToExecutor(ExecutableCommand command) {
        Executor.proceedAnalyzedCommand(command);
    }
    
    // TODO
    private static Feedback getFeedbackFromExecutor() {
       return Executor.getFeedback();
    }
}
