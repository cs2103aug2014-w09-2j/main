public class Feedback {
	boolean result;
	String message;
	
	Feedback(){
		result = false;
		message = "";
	}
	
	Feedback(boolean result, String show){
		this.result = result;
		this.message = show;
	}
	
	public void setResult(boolean result){
		this.result = result;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
}
