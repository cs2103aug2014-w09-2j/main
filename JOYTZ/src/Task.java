package mainComponent;

import java.util.Date;

public class Task {
	
	Date date;
	String location;
	String description;
	/**
	 * Constructor with two parameters
	 */
	Task(){
		
	}
	
	Task(Date d){
		this.date = d;
		this.location = "";
	}
	
	Task(Date d, String loc){
		if (loc == null){
			loc = "";
		}
		this.date = d;
		this.location = loc;
	}
	
	/**
	 * Method supported
	 */
	public void setDate(Date d){
		this.date = d;
	}
	
	public void setLocation(String loc){
		this.location = loc;
	}
	
	public void setDescription(String des){
		this.description = des;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getDescription(){
		return this.description;
	}

}
