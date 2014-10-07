
public class Executor {
	private static Feedback feedback;
	
	public static void processAnalyzedCommand(ExecutableCommand command) {
		String action = command.getAction();
		
		switch(action){
			case "add":
				performAddAction(command);
				break;
			case "delete":
				performDeleteAction();
				break;
			case "display": 
				performDisplayAction();
				break;
			case "clear": 
				performClearAction();
				break;
			case "sort":
				performSortAction();
				break;
			case "search":
				performSearchAction();
				break;
			case "exit": 
				performExitAction();
				break;
			default:
				feedback = new Feedback();
				feedback.setMessage("Invalid Command");
		}
	}

	private static void performAddAction(ExecutableCommand command) {
		feedback = new Feedback(true, command.getAction());
		feedback.setDescription(command.getDescription());
		feedback.setMessage("add");
		
		// TODO: add it to storage
	}


	private static void performDeleteAction() {
		// TODO Auto-generated method stub
	}

	private static void performDisplayAction() {
		// TODO Auto-generated method stub
	}

	private static void performClearAction() {
		// TODO Auto-generated method stub
	}
	
	private static void performSortAction() {
		// TODO Auto-generated method stub
	}

	private static void performSearchAction() {
		// TODO Auto-generated method stub
	}

	private static void performExitAction() {
		feedback = new Feedback(true, "exit");
		
		System.exit(0);		
	}

	public static Feedback getFeedback() {
		return feedback;
	}
}
