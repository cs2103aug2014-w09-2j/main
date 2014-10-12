
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
	public static final String EXCPTION_NULL_TASK_OBJECT= "Task Object is null.";
	public static final String EXCEPTION_TASK_OUT_OF_RANGE = "Task Id is out of range. TaskId : %d";
	
	
	/**
	 * ###################################################
	 * ##action Name									##
	 * ###################################################
	 */
	
	public static final String ADD = "add";
	public static final String DELETE = "delete";
	public static final String DISPLAY = "display";
	public static final String UPDATE = "update";	
	
	
	/**
	 * ###################################################
	 * ##Task Attribute Name							##
	 * ###################################################
	 */
	
	// update indicater.
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String DEADLINE = "deadline";
	public static final String LOCATION = "location";
	public static final String PRIORITY = "priority";
	
	
	
}
