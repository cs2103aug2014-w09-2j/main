package V1;

import java.util.Date;

public class Task {
	Date createdTime;
	Date expiredTime;
	String taskName;
	String description;
	String location;
	
	/**
	 * Constructor
	 */
	
	Task(String name){
		this.createdTime = new Date();
		this.expiredTime = null;
		this.taskName = name;
		this.description = "";
		this.location = null;
		//this.state = new Status();// default active;
	}
	
	Task(String name, Date d){
		this.createdTime = new Date();
		this.expiredTime = d;
		this.taskName = name;
		this.description = "";
		this.location = null;
		//this.state = // default activate;
	}
	
	Task(String name, Date d, String des, String loc){
		this.createdTime = new Date();
		this.expiredTime = d;
		this.taskName = name;
		this.description = des;
		this.location = loc;
		//this.state = // default activate;
	}
	
	/**
	 * Methods
	 */
	
	
	public void setTime(Date d){
		Date now = new Date();
		if (d.before(now)){
			
		}
		this.expiredTime = d;
	}
	
	public void setDescription(String des){
		this.description = des;
	}
	
	public void setLocation(String loc){
		this.location = loc;
	}
}
