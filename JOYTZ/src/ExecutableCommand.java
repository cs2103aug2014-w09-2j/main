//package V1;

import java.util.Date;

public class ExecutableCommand {
	private String action;
	private String description;
	private Date date;
	private String location;
	private int itemId;

	/**
	 * Constructor
	 */

	ExecutableCommand() {
		this.action = null;
		this.description = null;
		this.date = null;
		this.location = null;
		this.itemId = -1;
	}

	ExecutableCommand(String action) {
		this.action = action;
		this.description = null;
		this.date = null;
		this.location = null;
		this.itemId = -1;
	}

	/**
	 * methods
	 */

	public void setAction(String action) {
		this.action = action;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setItemId(int id) {
		this.itemId = id;
	}

	public String getAction() {
		return action;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public int getItemId() {
		return itemId;
	}

}
