//package V1;

import java.util.Date;

public class Task{
	
	/*enum TASK_STATUS{
		In_Process, Expired,
	}*/
	
	Date createdTime;
	Date expiredDate;
	String description;
	String location;
	//TASK_STATUS status;
	
	/**
	 * Constructor
	 */
	
	Task(String des, Date d){
		this.createdTime = new Date();
		this.expiredDate = d;
		this.description = des;
		this.location = null;
		//setStatus();
	}
	
	Task(String des, Date d, String loc){
		this.createdTime = new Date();
		this.expiredDate = d;
		this.description = des;
		this.location = loc;
		//setStatus();
	}
	
	/**
	 * Methods
	 */
	
	/*public void setStatus(){
		if (expiredTime.after(createdTime)){
			this.status = TASK_STATUS.In_Process;
		}else {
			this.status = TASK_STATUS.Expired;
		}
	}
	
	public void setStatusToBeExpired(){
		this.status = TASK_STATUS.Expired;
	}
	
	/*public void setStatusToBeInProcess(){
		this.status = TASK_STATUS.In_Process;
	}*/
	
	public void setExpiredDate(Date d){
		this.expiredDate = d;
	}
	
	public void setDescription(String des){
		this.description = des;
	}
	
	public void setLocation(String loc){
		this.location = loc;
	}
	
	public Date getExpiredDate(){
		return this.expiredDate;
	}

	public String getDescription(){
		return this.description;
	}
	
	public String getLocation(){
		return this.location;
	}

	/*@Override
	public void run() {
		setStatusToBeExpired();
		Executor.performCheckStatus();
	}*/
}
