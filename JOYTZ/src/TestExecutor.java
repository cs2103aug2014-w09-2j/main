import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class TestExecutor {
	private static final String ERROR_TASK_WITHOUT_NAME = "Task name should be mentioned.\n";
	private static final String MESSAGE_ADD_SUCCESSFUL = "\"%s\" is added successfully.\n";

	private static final String TEST = "testTask";

	@Test
	public void testPerformAddAction() {
		ExecutableCommand test1 = new ExecutableCommand("add");
		ExecutableCommand test2 = new ExecutableCommand("add");
		test2.setTaskName(TEST);
		Feedback expected1 = new Feedback(false);
		Feedback expected2 = new Feedback(true);

		expected1.setErrorMessage(ERROR_TASK_WITHOUT_NAME);
		expected2.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL,
				TEST));

		// test case 1
		assertEquals("fail to get task name to be added",
				expected1.getErrorMessage(),
				Executor.proceedAnalyzedCommand(test1).getErrorMessage());

		// test case 2
		assertEquals("fail to add task", expected2.getMessageShowToUser(),
				Executor.proceedAnalyzedCommand(test2).getMessageShowToUser());
	}

}
