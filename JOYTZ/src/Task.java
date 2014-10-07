//package V1;

import java.util.Date;
import java.util.TimerTask;

public class Task<StatusOfTask> extends TimerTask{
	
	enum TASK_STATUS{
		In_Process, Expired,
		}
	
	Date createdTime;
	Date expiredTime;
	String taskName;
	String description;
	String location;
	TASK_STATUS status;
	
	/**
	 * Constructor
	 */
	
	Task(String name, Date d){
		this.createdTime = new Date();
		this.expiredTime = d;
		this.taskName = name;
		this.description = "";
		this.location = null;
		setStatus();
	}
	
	Task(String name, Date d, String des, String loc){
		this.createdTime = new Date();
		this.expiredTime = d;
		this.taskName = name;
		this.description = des;
		this.location = loc;
		setStatus();
	}
	
	/**
	 * Methods
	 */
	
	public void setStatus(){
		if (expiredTime.after(createdTime)){
			this.status = TASK_STATUS.In_Process;
		}else {
			this.status = TASK_STATUS.Expired;
		}
	}
	
	public void setStatusToBeExpired(){
		this.status = TASK_STATUS.Expired;
	}
	
	public void setStatusToBeInProcess(){
		this.status = TASK_STATUS.In_Process;
	}
	
	public void setTaskName(String name){
		this.taskName = name;
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
	
	public Date getTime(){
		return this.expiredTime;
	}
	
	public String getTaskName(){
		return this.taskName;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getLocation(){
		return this.location;
	}

	@Override
	public void run() {
		setStatusToBeExpired();
		Executor.performCheckStatus();
	}
}
