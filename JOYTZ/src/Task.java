import java.util.TimerTask;

//package V1;



public class Task extends TimerTask implements Comparable<Task>{

	// task attributes.
	private String taskName;
	private Long taskStartTiming;
	private Long taskEndTiming;
	private String taskDescription;
	private String taskLocation;
	private String taskPriority;
	
	private long taskDuration;
	
	public boolean passStartTiming = false;
	public boolean passEndTiming = false;
	
	// the sortKey is default to be sorted by name;
	private static String sortKey = "name";

	/**
	 * Constructor
	 */
	Task() {
		this.taskName = "";
		this.taskStartTiming = (long) 0;
		this.taskEndTiming = (long) 0;
		this.taskDescription = "";
		this.taskLocation = "";
		this.taskPriority = "";
	}
	Task (String nameString){
		this.taskName = nameString;
		this.taskStartTiming = (long) 0;
		this.taskEndTiming = (long) 0;
		this.taskDescription = "";
		this.taskLocation = "";
		this.taskPriority = "";
	}
	Task(String nameString, Long startTimeLong, Long endTimeLong, String descriptionString, String locationString,
			String priority) {
		this.taskName = nameString;
		this.taskStartTiming = startTimeLong;
		this.taskEndTiming = endTimeLong;
		this.taskDescription = descriptionString;
		this.taskLocation = locationString;
		this.taskPriority = priority;
	}

	/**
	 * Set methods
	 */

	public void setTaskName(String name) {
		this.taskName = name;
	}

	public void setTaskStartTime(Long startTimeLong) {
		this.taskStartTiming = startTimeLong;
	}
	
	public void setTaskEndTime(Long endTimeLong){
		this.taskEndTiming = endTimeLong;
	}

	public void setTaskDescription(String des) {
		this.taskDescription = des;
	}

	public void setTaskLocation(String loc) {
		this.taskLocation = loc;
	}

	public void setTaskPriority(String priority) {
		this.taskPriority = priority;
	}

	/**
	 * Get methods
	 */

	public String getTaskName() {
		return this.taskName;
	}

	public Long getTaskStartTime() {
		return this.taskStartTiming;
	}
	
	public Long getTaskEndTime(){
		return this.taskEndTiming;
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
	
	public String get(String attribute){
		switch(attribute){
		case StringFormat.NAME: 
			return taskName;
		case StringFormat.DESCRIPTION:
			return taskDescription;
		case StringFormat.START_TIMING:
			return taskStartTiming + "";
		case StringFormat.END_TIMING:
			return taskEndTiming + "";
		case StringFormat.LOCATION:
			return taskLocation;
		case StringFormat.PRIORITY:
			return taskPriority;
		default:
			return taskName;
		}
	}
	
	
	// sort method
	public static void setSortKey(String key){
		sortKey = key;
	}

	@Override
	public int compareTo(Task o){
		Task that = (Task)o;
		String thisString = this.get(sortKey);
		String thatString = that.get(sortKey);
		
		return thisString.compareToIgnoreCase(thatString);
	}

	@Override
	public boolean equals(Object o){
		Task that = (Task) o;
		if (!that.getTaskName().equals(taskName)){
			return false;
		}else if(!that.getTaskStartTime().equals(taskStartTiming)){
			return false;
		}else if(!that.getTaskEndTime().equals(taskEndTiming)){
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
	
	@Override
	public void run() {
		Storage.check();
	}

}
