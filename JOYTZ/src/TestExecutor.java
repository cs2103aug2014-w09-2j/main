import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class TestExecutor {

	// executableCommand: add taskName, date, description, location.
	ExecutableCommand command1;

	// executableCommand: delete taskId.
	ExecutableCommand command2;

	// executableCommand: display taskId.
	ExecutableCommand command3;

	// executableCommand: update taskId.
	ExecutableCommand command4;

	// others currently not in use.
	ExecutableCommand command5;

	// initialization the storage.
	public Executor initializeStroage() {
		Executor executor = new Executor();

		ExecutableCommand ec = new ExecutableCommand();
		ec.setAction("add");
		ec.setTaskName("testTask");
		ec.setTaskDeadline(new Date());
		ec.setTaskDescription("this is a test task.");
		ec.setTaskLocation("com1, SR1");

		executor.proceedAnalyzedCommand(ec);

		return executor;
	}

	// test add function.
	@Test
	public void testAddMethod() {

		Executor ex = initializeStroage();

		command1 = new ExecutableCommand();
		command1.setAction("add");
		command1.setTaskName("addtask");
		command1.setTaskDeadline(new Date());
		command1.setTaskDescription("testing task for add method.");
		command1.setTaskLocation("comm1");

		ex.proceedAnalyzedCommand(command1);
		Feedback feedbackObject = ex.getFeedback();

		assertEquals(true, feedbackObject.getResult());
	}

	// test delete function.
	@Test
	public void testDeleteMethod() {

		Executor ex = initializeStroage();

		command2 = new ExecutableCommand();
		command2.setAction("delete");
		command2.setTaskId(1);

		ex.proceedAnalyzedCommand(command2);
		Feedback feedbackObject = ex.getFeedback();

		assertEquals(true, feedbackObject.getResult());
	}

	// test display function.
	@Test
	public void testDisplayMethod() {

		Executor ex = initializeStroage();

		command3 = new ExecutableCommand();
		command3.setAction("display");

		ex.proceedAnalyzedCommand(command3);
		Feedback feedbackObject = ex.getFeedback();

		assertEquals(true, feedbackObject.getResult());
	}

	/*
	 * // test update function.
	 * 
	 * @Test public void testUpdateMethod() {
	 * 
	 * Executor ex = initializeStroage();
	 * 
	 * command4 = new ExecutableCommand(); command4.setAction("update");
	 * command4.setTaskId(0); command4.setUpdateIndicator("location");
	 * command4.setTaskLocation("com1,SR2");
	 * 
	 * ex.proceedAnalyzedCommand(command4); Feedback feedbackObject =
	 * ex.getFeedback();
	 * 
	 * assertEquals(true, feedbackObject.getResult()); }
	 */

}
