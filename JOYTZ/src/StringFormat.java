import java.util.HashSet;


public class StringFormat {
	
	/**
	 * ###################################################
	 * ##Conversion between Task Object and String. 	##
	 * ###################################################
	 */
	// this is format for taskString.
	public static String taskString = "%s-%s-%s-%s-%s";
	public static int splitLimits = 3;
	
	
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
	 * ##task attribute   		   						##
	 * ##sort and search indicator						##
	 * ###################################################
	 */
	public static final String VALID_INDICATOR = "";
	
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String START_TIMING = "start timing";
	public static final String END_TIMING = "end timing";
	public static final String LOCATION = "location";
	public static final String PRIORITY = "priority";
	
	/**
	 * ###################################################
	 * ##update indicator  		   						##
	 * ###################################################
	 */
	
	public static final String START_TIME = "start time";
	public static final String END_TIME = "end time";
	public static final String START_DATE = "start date";
	public static final String END_DATE = "end date";
	
	/**
	 * ###################################################
	 * ##priority tag   		   						##
	 * ###################################################
	 */
	
	public static final String HIGH_PRIORITY = "high";
	public static final String MEDIUM_PRIORITY = "medium";
	public static final String LOW_PRIORITY = "low";
	
}
