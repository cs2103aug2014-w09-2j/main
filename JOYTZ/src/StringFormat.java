import java.text.SimpleDateFormat;


public class StringFormat {

	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_SHOWN_TO_USER = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm");
	
	/**
	 * action
	 */
	public static final String ADD = "add";
	public static final String DELETE = "delete";
	public static final String DISPLAY = "display";
	public static final String UPDATE = "update";
	public static final String SEARCH = "search";
	public static final String CLEAR = "clear";
	public static final String SORT = "sort";
	public static final String UNDO = "undo";
	public static final String REDO = "redo";
	public static final String EXIT = "exit";
	public static final String RELOAD = "reload";
	public static final String HELP = "help";
	public static final String TUTORIAL = "tutorial";
	public static final String SETTINGS = "settings";
	
	/**
	 * sort, search and update indicators
	 */

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String LOCATION = "location";
	public static final String PRIORITY = "priority";
	public static final String START = "start";
	public static final String END = "end";
	public static final String START_TIME = "start time";
	public static final String END_TIME = "end time";
	public static final String START_DATE = "start date";
	public static final String END_DATE = "end date";
	public static final String HIGH_PRIORITY = "high";
	public static final String MEDIUM_PRIORITY = "medium";
	public static final String LOW_PRIORITY = "low";
	
	public static final String INVALID_PRIORITY = "invalid";

	/**
	 * user input indicators
	 */
	public static final String PRIORITY_INDICATOR = "#";
	public static final String LOCATION_INDICATOR = "@";
	public static final String TIME_INDICATOR = ":";
	public static final String DESC_OR_ITEM_INDICATOR = ",";
	public static final String DATE_INDICATOR = "/";
	public static final String DUE_ON_INDICATOR = "dueon";
	public static final String DUE_AT_INDICATOR = "dueat";
	public static final String TO_INDICATOR = "to";
	public static final String AT_INDICATOR = "at";
	public static final String ON_INDICATOR = "on";
	public static final String OF_INDICATOR = "of";
	public static final String FROM_INDICATOR = "from";
	public static final String IMPORTANT = "important";
	public static final String UNIMPORTANT = "unimportant";
	
}
