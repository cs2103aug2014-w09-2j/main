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
    	
        outputCommand = getInput();			// TODO: Change outputText to inputCommand
        inputCommand = outputCommand;		// TODO: remove this when the above is changed
        
        passToAnalyzer(inputCommand);
        parsedCommand = getFromAnalyzer();
       
        passToExecutor(parsedCommand);
        //outputCommand = getFromExecutor();
        
        displayUserOutput(outputCommand);
        
    }
    
    public static void passToAnalyzer(String inputCommand) {
        Analyzer.analyzeCommand(inputCommand);
    }
    
    public static String getFromAnalyzer() {
        return Analyzer.getAnalyzedCommand();
    }
    
    public static void passToExecutor(String command) {
        Executor.getAnalyzedCommand(command);
    }
    
    public static void getFromExecutor(String message) {
       outputCommand = message;
       System.out.println(message);
    }
}
