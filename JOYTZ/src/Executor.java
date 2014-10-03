
public class Executor {
	private static String returnedMessage = "";
	
	public static void getAnalyzedCommand(ExecutableCommand command) {
		// Analyze command
		// ...
		
		editData("add meeting 3-4pm");
	}
	
	private static void editData(String actionToDo) {
		Storage.performActions(actionToDo);
	}
	
	public static void successOrFail(boolean status) {
		if (status == true) {
			returnedMessage = "Task added";
		}
	}
	
	public static String returnOutputMessage() {
		return returnedMessage;
	}
}
