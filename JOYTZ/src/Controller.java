import java.sql.Date;

public class Controller {

	private static String inputCommandString;
	private static Command inputCommandObject;
	private static String executorStatus;
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
    
    private static String convertCommandToString(ExecutableCommand command, String executorStatus) {
    	String action;
    	String description;
    	Date time;
    	String location;
    	String outputString;
    	
    	action = command.getAction();
    	description = command.getDescription();
    	time = command.getTime();
    	location = command.getLocation();
    	
    	if (executorStatus == "true") {
    		outputString = "Success! " + action + " task " + description + 
    					   " at " + location + " on " + time.toString();
    	}
    	else {
    		outputString = "Action failed!" + "(" + action + " task " + 
    						description + " at " + location + " on " + time.toString() + ")";    		
    	}
    	return outputString;
    	
    }
    
    // TODO
    private static void passToExecutor(ExecutableCommand command) {
        Executor.getAnalyzedCommand(command);
    }
    
    // TODO
    private static String getFromExecutor() {
       return Executor.returnOutputMessage();
    }
}
