
public class Executor {
	public static void getAnalyzedCommand(String command) {
		// Analyze command
		// ...
		
		editData("add meeting 3-4pm");
	}
	
	private static void editData(String actionToDo) {
		Storage.performActions(actionToDo);
	}
	
	public static void successOrFail(boolean status) {
		if (status == true) {
			returnOutputMessage("Task added");
		}
	}
	
	public static void returnOutputMessage(String message) {
		Controller.getFromExecutor(message);
	}
}
