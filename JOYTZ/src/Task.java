import java.util.Date;
import java.util.jar.Attributes.Name;

// @author Zhang Kai (A0119378U)
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
	
	// the sortKey is default to be sorted by name;
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

	/**
	 * Set methods
	 */

	public void setTaskName(String name) {
		this.taskName = name.trim();
	}

	public void setStartDateTime(Date date) {
		this.startDateTime = date;
	}
	
	public void setEndDateTime(Date date){
		this.endDateTime = date;
	}
	
	public void setStartDate(Date date){
		this.startDateTime.setYear(date.getYear());
		this.startDateTime.setMonth(date.getMonth());
		this.startDateTime.setDate(date.getDate());
	}
	
	public void setEndDate(Date date){
		this.endDateTime.setYear(date.getYear());
		this.endDateTime.setMonth(date.getMonth());
		this.endDateTime.setDate(date.getDate());
	}
	
	public void setStartTime(Date date){
		this.startDateTime.setHours(date.getHours());
		this.startDateTime.setMinutes(date.getMinutes());
	}
	
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

	/**
	 * Get methods
	 */

	public String getTaskName() {
		return this.taskName;
	}

	public Date getStartDateTime() {
		return this.startDateTime;
	}
	
	public Date getEndDateTime(){
		return this.endDateTime;
	}
	
	public String getFormatEndDateTime(){
		return StringFormat.DATE_FORMAT_SHOWN_TO_USER.format(endDateTime);
	}
	
	public String getFormatStartDateTime(){
		return StringFormat.DATE_FORMAT_SHOWN_TO_USER.format(startDateTime);
	}
	public String getLongFormatStartDateTimeString(){
		return startDateTime.getTime() + "";
	}
	
	public String getLongFormatEndDateTimeString(){
		return endDateTime.getTime() + "";
	}
	
	public String getLongFormatStartDateString(){
		Date currDate = new Date();
		currDate.setYear(startDateTime.getYear());
		currDate.setMonth(startDateTime.getMonth());
		currDate.setDate(startDateTime.getDate());
		
		currDate.setHours(0);
		currDate.setMinutes(0);
		return currDate.getTime() + "";
	}
	
	public String getLongFormatStartTimeString(){
		Date currDate = new Date();
		currDate.setYear(0);
		currDate.setMonth(0);
		currDate.setDate(0);
		
		currDate.setHours(startDateTime.getHours());
		currDate.setMinutes(startDateTime.getMinutes());
		return currDate.getTime() + "";
	}
	
	public String getLongFormatEndDateString(){
		Date currDate = new Date();
		currDate.setYear(endDateTime.getYear());
		currDate.setMonth(endDateTime.getMonth());
		currDate.setDate(endDateTime.getDate());
		
		currDate.setHours(0);
		currDate.setMinutes(0);
		return currDate.getTime() + "";
	}
	
	public String getLongFormatEndTimeString(){
		Date currDate = new Date();
		currDate.setYear(0);
		currDate.setMonth(0);
		currDate.setDate(0);
		
		currDate.setHours(endDateTime.getHours());
		currDate.setMinutes(endDateTime.getMinutes());
		return currDate.getTime() + "";
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
	
	// get date will return a long number represent the date.
	public String get(String attribute){						// need to insert check here?
		
		switch(attribute){
		case StringFormat.NAME: 
			return getTaskName();
			
		case StringFormat.DESCRIPTION:
			return getTaskDescription();
			
		case StringFormat.START:
			return getLongFormatStartDateTimeString();
			
		case StringFormat.END:
			return getLongFormatEndDateTimeString();
			
		case StringFormat.START_DATE:
			return getLongFormatStartDateString();
			
		case StringFormat.START_TIME:
			return getLongFormatStartTimeString();
			
		case StringFormat.END_DATE:
			return getLongFormatEndDateString();
			
		case StringFormat.END_TIME:
			return getLongFormatEndTimeString();
			
		case StringFormat.LOCATION:
			return taskLocation;
			
		case StringFormat.PRIORITY:
			return taskPriority;
			
		default:
			return taskName;
		}
	}

	public static void setSortKey(String key){
		sortKey = key;
	}

	@Override
	public int compareTo(Task o){
		Task that = (Task)o;
		String thisString = this.get(sortKey);
		String thatString = that.get(sortKey);
		
		if (sortKey.equals(StringFormat.PRIORITY)){			// need to refactor this? enum priority?
			thisString = convertPriority(thisString);
			thatString = convertPriority(thatString);
		}
		
		return thisString.compareToIgnoreCase(thatString);
	}
	
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

	@Override
	public boolean equals(Object o){
		Task that = (Task) o;
		if (!that.getTaskName().equals(taskName)){
			return false;
		}else if(!that.getStartDateTime().equals(getStartDateTime())){
			return false;
		}else if(!that.getEndDateTime().equals(getEndDateTime())){
			return false;
		}else if(!that.getTaskDescription().equals(taskDescription)){
			return false;
		}else if(!that.getTaskLocation().equals(taskLocation)){
			return false;
		}else if(!that.getTaskPriority().equals(taskPriority)){
			return false;
		}
		return true;
	}
	
	public String toString(){
		String resultString = taskName.concat("~");
		
		if (!getTaskDescription().equals(null)){
			resultString.concat(taskDescription);
		}
		resultString.concat("~");
		if (!getStartDateTime().equals(null)){
			resultString.concat(getFormatStartDateTime());
		}
		resultString.concat("~");
		if (!getEndDateTime().equals(null)){
			resultString.concat(getFormatEndDateTime());
		}
		resultString.concat("~");
		if (!getTaskLocation().equals(null)){
			resultString.concat(getTaskLocation());
		}
		resultString.concat("~");
		if (!getTaskPriority().equals(null)){
			resultString.concat(getTaskPriority());
		}
		
		return resultString;
	}

}
