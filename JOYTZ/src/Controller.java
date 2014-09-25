public class Controller {

	private static String inputCommand;
    private static String outputCommand;
    private static String parsedCommand;
    
	private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void displayUserOutput(String outputCommand) {
        if (outputCommand.equals("exit")) {     // TODO: This probably shouldn't be here (should be in executor)
            System.exit(0);
        }
        GUI.displayOutput(outputCommand);
    }
    
    public static void startController() {
    	
    	inputCommand = getInput();			
        
        passToAnalyzer(inputCommand);
        parsedCommand = getFromAnalyzer();
       
        passToExecutor(parsedCommand);
        outputCommand = getFromExecutor();
        
        displayUserOutput(outputCommand);
        
    }
    
    private static void passToAnalyzer(String inputCommand) {
        Analyzer.analyzeCommand(inputCommand);
    }
    
    private static String getFromAnalyzer() {
        return Analyzer.getAnalyzedCommand();
    }
    
    private static void passToExecutor(String command) {
        Executor.getAnalyzedCommand(command);
    }
    
    private static String getFromExecutor() {
       return Executor.returnOutputMessage();
    }
}
