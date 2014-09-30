package mainComponent;

import java.util.Date;

public class TaskObject {
	Date createdTime;
	Date expireTime;
	String description;
	Status state;
	
	/**
	 * Constructor
	 */
	TaskObject(Date d){
		this.createdTime = new Date();
		this.expireTime = d;
		this.description = "";
		this.state = // default active;
	}
	
	TaskObject(String des){
		this.createdTime = new Date();
		this.expireTime = null;
		this.description = des;
		this.state = // default active;
	}
	
	TaskObject(Date d, String des){
		this.createdTime = new Date();
		this.expireTime = d;
		this.description = des;
		this.state = // default activate;
	}
	
	/**
	 * Methods
	 */
	
	public void checkState(){
		
	}
	
	public void setTime(Date d){
		this.expireTime = d;
	}
	
	public void setDescription(String des){
		this.description = des;
	}
	
	public String toString(){
		
	}
}
