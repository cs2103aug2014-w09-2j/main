import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;


public class TestExecutor {
	
	// executableCommand: add taskName, date, description, location.
	ExecutableCommand command1;
	
	// executableCommand: delete taskId.
	ExecutableCommand command2;
	
	// executableCommand: update taskId.
	ExecutableCommand command3;
	
	// others currently not in use.
	ExecutableCommand command4;
	ExecutableCommand command5;
	
	// test add function.
	@Test 
	public void testAddMethod() {
		command1 = new ExecutableCommand();
		command1.setAction("add");
		command1.setTaskName("addtask");
		command1.setTaskDeadline(new Date());
		command1.setTaskDescription("testing task for add method.");
		command1.setTaskLocation("comm1");
		
		Executor.proceedAnalyzedCommand(command1);
		Feedback feedbackObject = Executor.getFeedback();
		
		assertEquals(true, feedbackObject.getResult());
	}

}
