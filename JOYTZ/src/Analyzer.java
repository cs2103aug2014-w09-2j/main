public class Analyzer {
	private static final String ERROR_INVALID_COMMAND = "Invalid command. Please try again.";

	private static String[] commandToBeAnalyzed;
	private static String analyzedCommand;
	
	public static void analyzeCommand(String command) {
		// TODO: Analyze the command. 
		commandToBeAnalyzed = convertStringToArray(command);
		startAnalyzer();
	}
	
	public static void startAnalyzer(){
		String action = commandToBeAnalyzed[0].toLowerCase();
		
		switch(action){
			case "add": 
				handleAddCommand();
				break;
			case "delete": 
				handleDeleteCommand();
				break;
			case "display": 
				handleDisplayCommand();
				break;
			case "clear": 
				handleClearCommand();
				break;
			case "sort":
				handleSortCommand();
				break;
			case "search":
				handleSearchCommand();
				break;
			case "exit": 
				handleExitCommand();
				break;
			default:
				analyzedCommand = "";
		}
		
	}
	
	public static void handleAddCommand(){
		return;
	}
	
	public static void handleDeleteCommand(){
		return;
	}
	
	public static void handleDisplayCommand(){
		return;
	}
	
	public static void handleClearCommand(){
		return;
	}
	
	public static void handleSortCommand(){
		return;
	}
	
	public static void handleSearchCommand(){
		return;
	}
	
	public static void handleExitCommand(){
		return;
	}
	
	public static String[] convertStringToArray(String command){
		return command.trim().split("\\s+");

	}
	
	public static String getAnalyzedCommand() {
		if(analyzedCommand == ""){
			return ERROR_INVALID_COMMAND;
		}
		return analyzedCommand;
	}
	
}
