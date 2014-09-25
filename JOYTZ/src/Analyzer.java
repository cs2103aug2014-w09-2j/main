
public class Analyzer {
	private static String analyzedCommand;
	
	public static void analyzeCommand(String command) {
		// TODO: Analyze the command. 
		analyzedCommand = command;
		
	}
	
	public static String getAnalyzedCommand() {
		return analyzedCommand;
	}
	
}
