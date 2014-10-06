public class Feedback {
	boolean result;
	String actionExecuted;
	String description;
	String message;
	
	/**
	 * Constructor
	 */

	Feedback(){
		result = false;
		actionExecuted = "";
	}
	
	Feedback(boolean result, String actionExecuted){
		this.result = result;
		this.actionExecuted = actionExecuted;
	}
	
	/**
	 * methods
	 */
	
	public void setResult(boolean result){
		this.result = result;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public boolean getResult(){
		return this.result;
	}
	
	public String getAction(){
		return this.actionExecuted;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getMessage(){
		return this.message;
	}
}
