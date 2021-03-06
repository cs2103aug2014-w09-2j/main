//@author A0119378U
import java.util.Date;

public class Task implements Comparable<Task>{

	// task attributes.
	private String taskName;
	private Date startDateTime;
	private Date endDateTime;
	private String taskDescription;
	private String taskLocation;
	private String taskPriority;
	
	public boolean passStartDateTime = false;
	public boolean passEndDateTime = false;
	
	private int taskId;
	
	// the sortKey is name by default;
	private static String sortKey = "name";

	/**
	 * Constructor
	 */
	Task (){
		this.taskName = "";
		this.startDateTime = null;
		this.endDateTime = null;
		this.taskDescription = "";
		this.taskLocation = "";
		this.taskPriority = "";
	}
	Task (String nameString){
		this.taskName = nameString;
		this.startDateTime = null;
		this.endDateTime = null;
		this.taskDescription = "";
		this.taskLocation = "";
		this.taskPriority = "";
	}
	Task (String nameString, int taskId){
		this.taskName = nameString;
		this.startDateTime = null;
		this.endDateTime = null;
		this.taskDescription = "";
		this.taskLocation = "";
		this.taskPriority = "";
		this.taskId = taskId;
	}

	/**
	 * Set methods
	 */

	public void setTaskName(String name) {
		this.taskName = name;
	}
	
	/**
	 * Set startDateTime from a given Date Object.
	 * @param date
	 */
	public void setStartDateTime(Date date) {
		this.startDateTime = date;
	}
	
	/**
	 * Set endDateTime from a given Date Object.
	 * @param date
	 */
	public void setEndDateTime(Date date){
		this.endDateTime = date;
	}
	
	/**
	 * Set startDate from a given Date Object.
	 * @param date
	 */
	public void setStartDate(Date date){
		this.startDateTime.setYear(date.getYear());
		this.startDateTime.setMonth(date.getMonth());
		this.startDateTime.setDate(date.getDate());
	}
	
	/**
	 * Set endDate from a given Date Object.
	 * @param date
	 */
	public void setEndDate(Date date){
		this.endDateTime.setYear(date.getYear());
		this.endDateTime.setMonth(date.getMonth());
		this.endDateTime.setDate(date.getDate());
	}
	
	/**
	 * Set startTime from a given Date Object.
	 * @param date
	 */
	public void setStartTime(Date date){
		this.startDateTime.setHours(date.getHours());
		this.startDateTime.setMinutes(date.getMinutes());
	}
	
	/**
	 * Set endTime from a given Date Object.
	 * @param date
	 */
	public void setEndTime(Date date){
		this.endDateTime.setHours(date.getHours());
		this.endDateTime.setMinutes(date.getMinutes());
	}

	public void setTaskDescription(String des) {
		this.taskDescription = des.trim();
	}

	public void setTaskLocation(String loc) {
		this.taskLocation = loc.trim();
	}

	public void setTaskPriority(String prio) {
		this.taskPriority = prio.trim();
	}
	
	public void setTaskId(int taskId){
		this.taskId = taskId;
	}

	/**
	 * Get methods
	 */

	public String getTaskName() {
		return this.taskName;
	}
	
	/**
	 * return null is there is not startDateTime.
	 * @return
	 */
	public Date getStartDateTime() {
		return this.startDateTime;
	}
	
	/**
	 * return null is there is not endDateTime.
	 * @return
	 */
	public Date getEndDateTime(){
		return this.endDateTime;
	}
	
	/**
	 * Return String format of endDateTime.
	 * @return
	 */
	public String getFormatEndDateTime(){
		if (endDateTime == null){
			return "";
		}
		return StringFormat.DATE_FORMAT_SHOWN_TO_USER.format(endDateTime);
	}
	
	/**
	 * Return String format of startDateTime.
	 * @return
	 */
	public String getFormatStartDateTime(){
		if (startDateTime == null){
			return "";
		}
		return StringFormat.DATE_FORMAT_SHOWN_TO_USER.format(startDateTime);
	}
	
	/**
	 * Return Long String to represent startDateTime.
	 * @return
	 */
	public String getLongStringFormatStartDateTime(){
		if (startDateTime == null){
			return Long.MAX_VALUE + "";
		}
		return startDateTime.getTime() + "";
	}
	
	/**
	 * Return Long String to represent endDateTime.
	 * @return
	 */
	public String getLongStringFormatEndDateTime(){
		if (endDateTime == null){
			return Long.MAX_VALUE + "";
		}
		return endDateTime.getTime() + "";
	}
	
	public String getLongStringFormatStartDate(){
		if (startDateTime == null){
			return "";
		}
		String result = startDateTime.getYear() + "";
		result = result.concat(startDateTime.getMonth() + "");
		result = result.concat(startDateTime.getDate() + "");
		return result;
	}
	
	public String getLongStringFormatStartTime(){
		if (startDateTime == null){
			return "";
		}
		String result = startDateTime.getHours() + "";
		result = result.concat(startDateTime.getMinutes() + "");
		return result;
	}
	
	public String getLongStringFormatEndDate(){
		if (endDateTime == null){
			return "";
		}
		String result = endDateTime.getYear() + "";
		result = result.concat(endDateTime.getMonth() + "");
		result = result.concat(endDateTime.getDate() + "");
		return result;
	}
	
	public String getLongStringFormatEndTime(){
		if (endDateTime == null){
			return "";
		}
		String result = endDateTime.getHours() + "";
		result = result.concat(endDateTime.getMinutes() + "");
		return result;
	}

	public String getTaskDescription() {
		return this.taskDescription;
	}

	public String getTaskLocation() {
		return this.taskLocation;
	}

	public String getTaskPriority() {
		return this.taskPriority;
	}
	
	public int getTaskId(){
		return this.taskId;
	}
	
	/**
	 * get a certain attribute of the task.
	 * @param attribute
	 * @return
	 */
	public String get(String attribute){
		
		switch(attribute){
		case StringFormat.NAME: 
			return getTaskName();
			
		case StringFormat.DESCRIPTION:
			return getTaskDescription();
			
		case StringFormat.START:
			return getLongStringFormatStartDateTime();
			
		case StringFormat.END:
			return getLongStringFormatEndDateTime();
			
		case StringFormat.START_DATE:
			return getLongStringFormatStartDate();
			
		case StringFormat.START_TIME:
			return getLongStringFormatStartTime();
			
		case StringFormat.END_DATE:
			return getLongStringFormatEndDate();
			
		case StringFormat.END_TIME:
			return getLongStringFormatEndTime();
			
		case StringFormat.LOCATION:
			return taskLocation;
			
		case StringFormat.PRIORITY:
			return taskPriority;
			
		default:
			return taskName;
		}
	}

	/**
	 * Set a the static value sortKey.
	 * @param key
	 */
	public static void setSortKey(String key){
		sortKey = key;
	}
	
	
	@Override
	public int compareTo(Task o){
		Task that = (Task)o;
		String thisString = this.get(sortKey);
		String thatString = that.get(sortKey);
		
		if (sortKey.equals(StringFormat.PRIORITY)){			
			thisString = convertPriority(thisString);
			thatString = convertPriority(thatString);
		}
		
		return thisString.compareToIgnoreCase(thatString);
	}
	
	/**
	 * The user input priority may be ordered in High, Medium, Low.
	 * This method convert the priority into comparable string.
	 * @param priority
	 * @return
	 */
	private String convertPriority (String priority){
		if (priority.equals(StringFormat.HIGH_PRIORITY)){
			return "a";
		}else if (priority.equals(StringFormat.MEDIUM_PRIORITY)){
			return "b";
		}else if (priority.equals(StringFormat.LOW_PRIORITY)){
			return "c";
		}else {
			return "d";
		}
	}
	
	/**
	 * Task Id is unique.
	 */
	@Override
	public boolean equals(Object o){
		Task that = (Task) o;
		if (this.taskId != that.taskId){
			return false;
		}
		return true;
	}
	
	@Override
	public String toString(){
		String emptySpace = " ";
		String resultString = taskName.concat("~");
		
		// add in description information.
		if (!getTaskDescription().equals("")){
			resultString = resultString.concat(taskDescription);
		}else {
			resultString = resultString.concat(emptySpace);
		}
		resultString = resultString.concat("~");
		
		// add in start date time information.
		if (!(getStartDateTime() == null)){
			resultString = resultString.concat(getFormatStartDateTime());
		}else {
			resultString = resultString.concat(emptySpace);
		}
		resultString = resultString.concat("~");
		
		// add in end date time information.
		if (!(getEndDateTime() == null)){
			resultString = resultString.concat(getFormatEndDateTime());
		}else {
			resultString = resultString.concat(emptySpace);
		}
		resultString = resultString.concat("~");
		
		// add in location information.
		if (!getTaskLocation().equals("")){
			resultString = resultString.concat(getTaskLocation());
		}else {
			resultString = resultString.concat(emptySpace);
		}
		resultString = resultString.concat("~");
		
		// add in priority information.
		if (!getTaskPriority().equals("")){
			resultString = resultString.concat(getTaskPriority());
		}else {
			resultString = resultString.concat(emptySpace);
		}
		
		resultString = resultString.concat("\n");
		return resultString;
	}

}
