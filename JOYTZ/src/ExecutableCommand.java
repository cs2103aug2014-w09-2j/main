import java.sql.Date;

public class ExecutableCommand {
	private String action;
	private String description;
	private int itemId;
	private Date date;
	private String location;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = null;
		this.description = null;
		this.itemId = -1;
		this.date = null;
		this.location = null;
	}
	
	ExecutableCommand(String action){
		this.action = action;
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
	
	public void setDate(Date date) {
		this.date = date;
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

	public Date getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

}
