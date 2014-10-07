package V1;

import java.sql.Date;

public class ExecutableCommand {
	private String action;
	private String description;
	private String taskName;
	private Date date;
	private String location;
	private int ItemId;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = null;
		this.description = null;
		this.taskName = "";
		this.date = null;
		this.location = null;
		this.ItemId = -1;
	}
	
	ExecutableCommand(String action){
		this.action = action;
		this.description = null;
		this.taskName = "";
		this.date = null;
		this.location = null;
		this.ItemId = -1;
	}
	
	/**
	 * methods
	 */
	
	public void setAction(String action){
		this.action = action;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setTaskName(String name){
		this.taskName = name;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setItemId(int id){
		this.ItemId = id;
	}

	public String getAction() {
		return action;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getTaskName(){
		return taskName;
	}

	public Date getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}
	
	public int getItemId(){
		return ItemId;
	}

}
