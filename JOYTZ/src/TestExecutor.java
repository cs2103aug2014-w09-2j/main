/*
 * @author: Thang
 */

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class TestExecutor {

	ExecutableCommand obj;
	ExecutableCommand clear = new ExecutableCommand("clear");

	@Test
	public void checkAddMethod() throws ParseException {
		obj = new ExecutableCommand();

		obj.setAction("add");
		obj.setTaskName("Meeting");
		obj.setTaskLocation("NUS");
		obj.setTaskPriority("high");
		obj.setTaskDescription("with teammates");

		assertTrue("Fail to add", Executor.proceedAnalyzedCommand(obj)
				.getResult());
		Executor.proceedAnalyzedCommand(clear);

		/*
		 * This is a boundary case for the non-empty ArrayList partition
		 */
		assertNotNull("No element in list", Executor
				.proceedAnalyzedCommand(obj).getTaskList().size());
		Executor.proceedAnalyzedCommand(clear);

		assertEquals("There's more than 1 element", 1, Executor
				.proceedAnalyzedCommand(obj).getTaskList().size());
		Executor.proceedAnalyzedCommand(clear);

		assertNotEquals("There are 2 elements", 2, Executor
				.proceedAnalyzedCommand(obj).getTaskList().size());
		Executor.proceedAnalyzedCommand(clear);
	}

	@Test
	public void checkDeleteMethod() throws ParseException {
		obj = new ExecutableCommand();

		obj.setAction("add");
		obj.setTaskName("meeting");
		Executor.proceedAnalyzedCommand(obj);

		obj.setAction("add");
		obj.setTaskName("eating");
		Executor.proceedAnalyzedCommand(obj);

		obj.setAction("delete");
		obj.setTaskId(1);

		/*
		 * This is a boundary case for the non-empty ArrayList partition
		 */

		int size = Executor.proceedAnalyzedCommand(obj).getTaskList().size();

		assertEquals("Fail to check size", 1, size);
		assertNotEquals("Fail to check size", 2, size);

		assertNotEquals("Fail to check List", 0, size);

		/*
		 * Delete the last item
		 */
		assertEquals("Fail to check List", 0, Executor.proceedAnalyzedCommand(obj)
				.getTaskList().size());

		Executor.proceedAnalyzedCommand(clear);
	}
	

}
