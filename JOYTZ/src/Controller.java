public class Controller {

	private static String inputCommandString;
	private static Command inputCommandObject;
    private static String outputCommand;
    private static ExecutableCommand parsedCommand;
    
	private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void displayUserOutput(String outputCommand) {
        if (outputCommand.equals("exit")) {     
            System.exit(0);
        }
        GUI.displayOutput(outputCommand);
    }
    
    public static void startController() {
    	
    	inputCommandString = getInput();			
        inputCommandObject = convertStringToCommand(inputCommandString);
        
    	parsedCommand = analyzeInput(inputCommandObject);
       
        passToExecutor(parsedCommand);
        outputCommand = getFromExecutor();
        
        displayUserOutput(outputCommand);
        
    }
    
    private static Command convertStringToCommand(String inputCommandString) {
    	inputCommandObject = new Command(inputCommandString);
    	return inputCommandObject;
    }
    
    private static ExecutableCommand analyzeInput(Command inputCommandObject) {
        ExecutableCommand parsedCommand = Analyzer.runAnalyzer(inputCommandObject);
    	return parsedCommand;
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
