import java.sql.Date;

public class ExecutableCommand {
	private String action;
	private String description;
	private int itemId;
	private Date time;
	private String location;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = null;
		this.description = null;
		this.itemId = -1;
		this.time = null;
		this.location = null;
	}
	
	ExecutableCommand(String action){
		this.action = action;
	}
	
	ExecutableCommand(String action, String description){
		this.action = action;
		this.description = description;
	}

	ExecutableCommand(String action, int itemId){
		this.action = action;
		this.itemId = itemId;
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
	
	public void setItemId(int id){
		this.itemId = id;
	}
	
	public void setTime() {

	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAction() {
		return action;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getItemId(){
		return itemId;
	}

	public Date getTime() {
		return time;
	}

	public String getLocation() {
		return location;
	}
}
