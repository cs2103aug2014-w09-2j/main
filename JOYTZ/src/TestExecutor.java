import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class TestExecutor {
	private static final String MESSAGE_ADD_SUCCESSFUL = "\"%s\" is added successfully.\n";
	private static final String MESSAGE_UPDATE_SUCCESSFUL = "Task %d, \"%s\"has been updated successfully.\n";
	private static final String MESSAGE_DELETE_SUCCESSFUL = "%d. \"%s\" is deleted successfully.\n";
	private static final String MESSAGE_CLEAR_SUCCESSFUL = "All tasks are cleared successfully.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";
	private static final String TEST = "testTask";

	private static void init() {
		ExecutableCommand task1 = new ExecutableCommand("add");
		task1.setTaskName("dating");

		Executor.proceedAnalyzedCommand(task1);
	}

	@Test
	public void testPerformAddAction() {
		ExecutableCommand test1 = new ExecutableCommand("add");
		test1.setTaskName(TEST);
		Feedback expected1 = new Feedback(true);

		expected1.setMessageShowToUser(String.format(MESSAGE_ADD_SUCCESSFUL,
				TEST));

		// test case 1
		assertEquals("fail to add task", expected1.getMessageShowToUser(),
				Executor.proceedAnalyzedCommand(test1).getMessageShowToUser());
	}

	@Test
	public void testPerformDeleteAction() {
		init();
		ExecutableCommand test1 = new ExecutableCommand("delete");
		ExecutableCommand test2 = new ExecutableCommand("delete");
		test1.setTaskId(100);
		test2.setTaskId(1);
		Feedback expected1 = new Feedback(false);
		Feedback expected2 = new Feedback(true);

		expected1.setErrorMessage(ERROR_INVALID_TASK_INDEX);
		expected2.setMessageShowToUser(String.format(MESSAGE_DELETE_SUCCESSFUL,
				1, "dating"));

		// test case 1
		assertEquals("invalid index case is not handled",
				expected1.getErrorMessage(),
				Executor.proceedAnalyzedCommand(test1).getErrorMessage());

		// test case 2
		assertEquals("fail to delete task", expected2.getMessageShowToUser(),
				Executor.proceedAnalyzedCommand(test2).getMessageShowToUser());
	}

	@Test
	public void testPerformUpdateAction() {
		init();
		ExecutableCommand test1 = new ExecutableCommand("update");
		ExecutableCommand test2 = new ExecutableCommand("update");
		test1.setUpdateIndicator("name");
		test1.setTaskId(100);
		test2.setTaskId(1);
		test2.setUpdateIndicator("name");
		test2.setUpdatedTaskName("meeting");
		Feedback expected1 = new Feedback(false);
		Feedback expected2 = new Feedback(true);

		expected1.setErrorMessage(ERROR_INVALID_TASK_INDEX);
		expected2.setMessageShowToUser(String.format(MESSAGE_UPDATE_SUCCESSFUL,
				1, "meeting"));

		// test case 1
		assertEquals("invalid index case is not handled",
				expected1.getErrorMessage(),
				Executor.proceedAnalyzedCommand(test1).getErrorMessage());

		// test case 2
		assertEquals("fail to update task", expected2.getMessageShowToUser(),
				Executor.proceedAnalyzedCommand(test2).getMessageShowToUser());
	}

	@Test
	public void testPerformClearAction() {
		init();
		ExecutableCommand test1 = new ExecutableCommand("clear");
		Feedback expected = new Feedback(true);

		expected.setMessageShowToUser(MESSAGE_CLEAR_SUCCESSFUL);

		// test case 1
		assertEquals("fail to clear task list",
				expected.getMessageShowToUser(), Executor
						.proceedAnalyzedCommand(test1).getMessageShowToUser());
	}
}
