
public class StringFormat {
	
	/**
	 * ###################################################
	 * ##Conversion between Task Object and String. 	##
	 * ###################################################
	 */
	// this is format for taskString.
	public static String taskString = "%s-%s-%s-%s";
	public static int splitLimits = 3;
	
	
	
	/**
	 * ###################################################
	 * ##Storage : String in Exception					##
	 * ###################################################
	 */
	
	// String format for Exception in Storage.
	public static String exceptionNullTaskObject= "Task Object is null.";
	public static String exceptionTaskOutOfRange = "Task Id is out of range. TaskId : %d";
	
	
	/**
	 * ###################################################
	 * ##Method Name									##
	 * ###################################################
	 */
	
	// update indicater.
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String DEADLINE = "deadline";
	public static final String LOCATION = "location";
	public static final String PRIORITY = "priority";
	
	
	
}
