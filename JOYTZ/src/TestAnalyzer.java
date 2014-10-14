import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class TestAnalyzer {
	private static final String ERROR_NULL_TASK_TO_ADD = "Task to be added is not indicated.\n";
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not indicated.\n";
	private static final String ERROR_INVALID_INDEX = "Task index indicated is invalid.\n";
	private static final String ERROR_NULL_UPDATED_TASK = "Task to be updated is not indicated.\n";
	private static final String ERROR_NULL_UPDATE_INDICATOR = "Item in task to be updated is not indicated.\n";
	private static final String ERROR_INVALID_ARGUMENT = "The input argument is invalid.\n";

	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Test
	public void testHandleAddCommand() throws ParseException {
		Command test1 = new Command("add");
		Command test2 = new Command("add~meeting with friends");
		Command test3 = new Command(
				"add~meeting with friends~discuss about CS2103T project");
		Command test4 = new Command(
				"add~meeting with friends~discuss about CS2103T project~14/10/2014");
		Command test5 = new Command(
				"add~meeting with friends~discuss about CS2103T project~14/10/2014~NUS");
		Command test6 = new Command(
				"add~meeting with friends~discuss about CS2103T project~14/10/2014~NUS~medium");

		ExecutableCommand expected = new ExecutableCommand("add");
		expected.setErrorMessage(ERROR_NULL_TASK_TO_ADD);
		expected.setTaskName("meeting with friends");
		expected.setTaskDescription("discuss about CS2103T project");
		expected.setTaskDeadline((java.util.Date) df.parse("14/10/2014"));
		expected.setTaskLocation("NUS");
		expected.setTaskPriority("medium");

		// test case 1
		assertEquals("null argument case is not handled",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2
		assertEquals("fail to get task name to be added",
				expected.getTaskName(), Analyzer.runAnalyzer(test2)
						.getTaskName());

		// test case 3
		assertEquals("fail to get task description to be added",
				expected.getTaskDescription(), Analyzer.runAnalyzer(test3)
						.getTaskDescription());

		// test case 4
		assertEquals("fail to get task deadline to be added",
				expected.getTaskDeadline(), Analyzer.runAnalyzer(test4)
						.getTaskDeadline());

		// test case 5
		assertEquals("fail to get task location to be added",
				expected.getTaskLocation(), Analyzer.runAnalyzer(test5)
						.getTaskLocation());

		// test case 6
		assertEquals("fail to get task priority to be added",
				expected.getTaskPriority(), Analyzer.runAnalyzer(test6)
						.getTaskPriority());
	}

	@Test
	public void testHandleDeleteCommand() throws ParseException {
		Command test1 = new Command("delete");
		Command test2 = new Command("delete~0");
		Command test3 = new Command("delete~meeting with friends");

		ExecutableCommand expected = new ExecutableCommand("delete");
		expected.setErrorMessage(ERROR_NULL_TASK_INDEX);
		expected.setTaskName("meeting with friends");

		ExecutableCommand expected2 = new ExecutableCommand("delete");
		expected2.setErrorMessage(ERROR_INVALID_INDEX);

		ExecutableCommand expected3 = new ExecutableCommand("delete");
		expected3.setErrorMessage(ERROR_INVALID_ARGUMENT);

		// test case 1
		assertEquals("null argument case is not handled",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3
		assertEquals("invalid input argument case is not handled",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test3)
						.getErrorMessage());
	}

	@Test
	public void testHandleUpdateCommand() throws ParseException {
		Command test1 = new Command("update");
		Command test2 = new Command("update~2");
		Command test3 = new Command("update~meeting");
		Command test4 = new Command("update~2~name~dating");
		Command test5 = new Command(
				"update~2~description~discussion about CS2103T");
		Command test6 = new Command("update~2~deadline~27/10/2014");
		Command test7 = new Command("update~2~location~NTU");
		Command test8 = new Command("update~2~priority~high");

		ExecutableCommand expected = new ExecutableCommand("update");
		expected.setErrorMessage(ERROR_NULL_UPDATED_TASK);
		expected.setTaskName("meeting");
		expected.setTaskDescription("discussion about CS2103T");
		expected.setTaskDeadline((java.util.Date) df.parse("27/10/2014"));
		expected.setTaskLocation("NTU");
		expected.setTaskPriority("high");
		expected.setTaskId(2);

		ExecutableCommand expected2 = new ExecutableCommand("update");
		expected2.setErrorMessage(ERROR_NULL_UPDATE_INDICATOR);

		ExecutableCommand expected3 = new ExecutableCommand("update");
		expected3.setErrorMessage(ERROR_NULL_UPDATE_INDICATOR);

		ExecutableCommand expected4 = new ExecutableCommand("update");
		expected4.setUpdatedTaskName("dating");

		// test case 1
		assertEquals("null argument case is not handled",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2
		assertEquals("null update indicator case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3
		assertEquals("invalid input argument case is not handled",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test3)
						.getErrorMessage());

		// test case 4
		assertEquals("fail to get task name to be updated",
				expected4.getUpdatedTaskName(), Analyzer.runAnalyzer(test4)
						.getUpdatedTaskName());

		// test case 5
		assertEquals("fail to get task description to be updated",
				expected.getTaskId(), Analyzer.runAnalyzer(test5).getTaskId());

		// test case 6
		assertEquals("fail to get task deadline to be updated",
				expected.getTaskDeadline(), Analyzer.runAnalyzer(test6)
						.getTaskDeadline());

		// test case 7
		assertEquals("fail to get task location to be updated",
				expected.getTaskLocation(), Analyzer.runAnalyzer(test7)
						.getTaskLocation());

		// test case 8
		assertEquals("fail to get task priority to be updated",
				expected.getTaskPriority(), Analyzer.runAnalyzer(test8)
						.getTaskPriority());

	}

}
