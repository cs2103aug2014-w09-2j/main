
public class Storage {
	
	public static void performActions(String action) {
		successOrFail(true);
	}
	
	public static void successOrFail(boolean status) {
		Executor.successOrFail(status);
	}
}
