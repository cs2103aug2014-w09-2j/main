//package V1;

public class Command {
	private String userCommand;

	/**
	 * Constructor
	 */

	Command(String userInput) {
		this.userCommand = userInput;
	}

	/**
	 * methods
	 */

	public String getUserCommand() {
		return userCommand;
	}
}
