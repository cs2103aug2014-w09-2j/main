public class Controller {

    private static String getInput() {
        return GUI.getUserInput();
    }
    
    private static void executeUserOutput(String outputCommand) {
        if (outputCommand.equals("exit")) {     // TODO: This probably shouldn't be here (should be in executor)
            System.exit(0);
        }
        GUI.displayOutput(outputCommand);
    }
    
    public static void main(String[] args) {
        String inputCommand;
        String outputCommand;
        String parsedCommand;
        
        outputCommand = getInput();              // TODO: Change outputText to inputCommand
        // passToAnalyzer(inputCommand);
        // parsedCommand = getFromAnalyzer();
        // passToExecutor(parsedCommand);
        // outputText = getFromExecutor();
        executeUserOutput(outputCommand);
    }
    
    public static void passToAnalyzer(String inputCommand) {
        
    }
    
    public static String getFromAnalyzer() {
        return null;
    }
    
    public static void passToExecutor(String command) {
        
    }
    
    public static String getFromExecutor() {
        return null; 
    }
}
