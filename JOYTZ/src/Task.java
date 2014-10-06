import java.util.Date;

public class Task {
	Date createdTime;
	Date expiredTime;
	String description;
	Status state;
	String location;
	
	/**
	 * Constructor
	 */
	
	Task(Date d){
		this.createdTime = new Date();
		this.expiredTime = d;
		this.description = "";
		//this.state = new Status();// default active;
	}
	
	Task(String des){
		this.createdTime = new Date();
		this.expiredTime = null;
		this.description = des;
		//this.state = // default active;
	}
	
	Task(Date d, String des){
		this.createdTime = new Date();
		this.expiredTime = d;
		this.description = des;
		//this.state = // default activate;
	}
	
	/**
	 * Methods
	 */
	
	public void checkState(){
		
	}
	
	public void setTime(Date d){
		this.expiredTime = d;
	}
	
	public void setDescription(String des){
		this.description = des;
	}
	
	public void setLocation(String loc){
		this.location = loc;
	}
}
