// import java.sql.Date;

public class Controller {

	private static String inputCommandString;
	private static Command inputCommandObject;
	private static boolean executorStatus;
    private static String outputCommandString;
    private static ExecutableCommand parsedCommand;
    
	private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void displayUserOutput(String outputCommand) {
        if (outputCommand.equals("exit")) {     // Should not be in this method
            System.exit(0);
        }
        
        GUI.displayOutput(outputCommand);
    }
    
    public static void startController() {
    	
    	inputCommandString = getInput();			
        inputCommandObject = convertStringToCommand(inputCommandString);
        
    	parsedCommand = analyzeInput(inputCommandObject);
       
        passToExecutor(parsedCommand);
        executorStatus = getFromExecutor();
        
        outputCommandString = convertCommandToString(parsedCommand, executorStatus);
        displayUserOutput(outputCommandString);
        
    }
    
    private static Command convertStringToCommand(String inputCommandString) {
    	inputCommandObject = new Command(inputCommandString);
    	return inputCommandObject;
    }
    
    private static ExecutableCommand analyzeInput(Command inputCommandObject) {
        ExecutableCommand parsedCommand = Analyzer.runAnalyzer(inputCommandObject);
    	return parsedCommand;
    }
    
    private static String convertCommandToString(ExecutableCommand command, boolean executorStatus) {
    	String action = "";
    	String description = "";
    	// Date time;
    	// String location;
    	String outputString;
    	
    	if (command != null) {
	    	action = command.getAction();
	    	description = command.getDescription();
	    	//time = command.getTime();
	    	//location = command.getLocation();
    	}
    	
    	// TODO: Proper output to be done later
    	// TODO: Put in another function
    	if (command == null) {
    		outputString = "Please enter a valid command!";
    	} else if (executorStatus == true) {
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
        Executor.getAnalyzedCommand(command);
    }
    
    // TODO
    private static boolean getFromExecutor() {
       return Executor.returnOutputMessage();
    }
}
