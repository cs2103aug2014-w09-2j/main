import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class TestAnalyzer {
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not inserted.\n";
	private static final String ERROR_NULL_COMMAND = "Command is not inserted.\n";
	private static final String ERROR_NULL_TASK = "Task name is not inserted.\n";
	private static final String ERROR_NULL_INDICATOR = "Indicator is not inserted.\n";
	private static final String ERROR_NULL_ARGUMENT = "Argument is not inserted.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";
	private static final String ERROR_INVALID_INDICATOR = "Indicator is invalid.\n";
	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";

	private static Date d1 = new Date(114, 9, 15, -1, -1);
	private static Date d2 = new Date(114, 9, 21, -1, -1);

	@Test
	public void testHandleAddCommand() throws ParseException {
		Command test1 = new Command("add");
		Command test2 = new Command("add~meeting with friends");
		Command test3 = new Command(
				"add~meeting with friends~discuss about CS2103T project");
		Command test4 = new Command(
				"add~meeting with friends~discuss about CS2103T project~14/10/2014");
		Command test5 = new Command(
				"add~meeting with friends~discuss about CS2103T project~14/10/2014~~NUS");
		Command test6 = new Command(
				"add~meeting with friends~discuss about CS2103T project~14/10/2014~20/10/2014~NUS~medium");
		Command test7 = new Command(
				"add~meeting with friends~discuss about CS2103T project~14/10/2014~20/10/2014~NUS~medium");

		ExecutableCommand expected = new ExecutableCommand("add");
		expected.setErrorMessage(ERROR_NULL_TASK);
		expected.setTaskName("meeting with friends");
		expected.setTaskDescription("discuss about CS2103T project");
		expected.setTaskStartTiming(String.valueOf(d1.getTime()));
		expected.setTaskEndTiming(String.valueOf(d2.getTime()));
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
		assertEquals("fail to get task start timing to be added",
				expected.getTaskStartTiming(), Analyzer.runAnalyzer(test4)
						.getTaskStartTiming());

		// test case 5
		assertEquals("fail to get task location to be added",
				expected.getTaskLocation(), Analyzer.runAnalyzer(test5)
						.getTaskLocation());

		// test case 6
		assertEquals("fail to get task priority to be added",
				expected.getTaskPriority(), Analyzer.runAnalyzer(test6)
						.getTaskPriority());

		// test case 7
		assertEquals("fail to get task end timing to be added",
				expected.getTaskEndTiming(), Analyzer.runAnalyzer(test7)
						.getTaskEndTiming());
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
		expected2.setErrorMessage(ERROR_INVALID_TASK_INDEX);

		ExecutableCommand expected3 = new ExecutableCommand("delete");
		expected3.setErrorMessage(ERROR_INVALID_TASK_INDEX);

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

		ExecutableCommand expected = new ExecutableCommand("update");
		expected.setErrorMessage(ERROR_NULL_TASK_INDEX);

		ExecutableCommand expected2 = new ExecutableCommand("update");
		expected2.setErrorMessage(ERROR_NULL_INDICATOR);

		ExecutableCommand expected3 = new ExecutableCommand("update");
		expected3.setErrorMessage(ERROR_INVALID_TASK_INDEX);

		ExecutableCommand expected4 = new ExecutableCommand("update");
		expected4.setKey("dating");

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
		assertEquals("fail to get task name to be updated", expected4.getKey(),
				Analyzer.runAnalyzer(test4).getKey());

	}

}
