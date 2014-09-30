package mainComponent;

import java.util.Date;

public class Task {
	
	Date time;
	String Location;
	
	/**
	 * Constructor with two parameters
	 */
	Task(){
		
	}
	
	Task(Date d){
		this.time = d;
		this.Location = "";
	}
	
	Task(Date d, String loc){
		if (loc == null){
			loc = "";
		}
		this.time = d;
		this.Location = loc;
	}
	
	/**
	 * Method supported
	 */
	public void setTime(Date d){
		
	}
	
	public void setLocation(){
		
	}
	
	public Date getTime(){
		
	}
	
	public String getLocation(){
		
	}
	
	public String toString(){
		
	}

}
