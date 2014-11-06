import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class TestAnalyzer {
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not inserted.\n";
	private static final String ERROR_NULL_TASK = "Task name is not inserted.\n";
	private static final String ERROR_NULL_INDICATOR = "Indicator is not inserted.\n";
	private static final String ERROR_INVALID_TASK_INDEX = "Task index indicated is invalid.\n";
	private static final String ERROR_INVALID_TIME = "Format of input %s time is invalid.\n";
	private static final String ERROR_INVALID_EARLIER_TIME = "Input %s time is earlier than current time.\n";
	private static final String ERROR_INVALID_END_EARLIER_THAN_START_TIME = "End time is earlier than start time.\n";

	private static Date currentDate = new Date(System.currentTimeMillis());
	private static Date d1 = new Date(115, 9, 14);
	private static Date d2 = new Date(115, 9, 20);
	private static Date d3 = new Date(115, 9, 14, 0, 0);
	private static Date d4 = new Date(115, 9, 20, 23, 59);
	private static Date d5 = new Date(115, 9, 20, 2, 38);

	@Test
	public void testHandleAddCommand() throws ParseException {
		Command test1 = new Command("add");
		Command test2 = new Command("add meeting with friends");
		Command test3 = new Command(
				"add meeting with friends, discuss about CS2103T project");
		Command test4 = new Command(
				"add meeting with friends, discuss about CS2103T project on 14/10/2015");
		Command test5 = new Command(
				"add meeting with friends, discuss about CS2103T project on 14/10/2015 @NUS");
		Command test6 = new Command(
				"add meeting with friends, discuss about CS2103T project from 14/10/2015 to 20/10/2015 @NUS #medium");
		Command test7 = new Command(
				"add meeting with friends, discuss about CS2103T project from 14/10/2015 to 20/10/2015 @NUS #medium");
		Command test8 = new Command("add assignment on 14/10/2015 @NUS #medium");
		Command test9 = new Command(
				"add jogging due on 20/10/2015 @NUS #medium");
		Command test10 = new Command(
				"add project from 14/10/2015 to 20/10/2015 @NUS #medium");
		Command test11 = new Command(
				"add project from 14/10/2015 to 20/10/2015 2:38AM @NUS #medium");
		Command test12 = new Command("add task1 on 32/2/2015");
		Command test13 = new Command("add task1 on 25/13/2015");
		Command test14 = new Command("add task1 on 29/2/2017");
		Command test15 = new Command("add task1 on 31/4/2015");
		Command test16 = new Command("add task1 on 22/2/2017 13:24PM");
		Command test17 = new Command("add task1 on 22/4/2015 12:60AM");

		ExecutableCommand expected = new ExecutableCommand("add");
		expected.setErrorMessage(ERROR_NULL_TASK);
		expected.setTaskName("meeting with friends");
		expected.setTaskDescription("discuss about CS2103T project");
		expected.setTaskStartTiming(String.valueOf(d1.getTime()));
		expected.setTaskEndTiming(String.valueOf(d2.getTime()));
		expected.setTaskLocation("NUS");
		expected.setTaskPriority("medium");

		ExecutableCommand expected2 = new ExecutableCommand("add");
		expected2.setTaskStartTiming(String.valueOf(d3.getTime()));
		expected2.setTaskEndTiming(String.valueOf(d4.getTime()));
		expected2.setErrorMessage(String.format(ERROR_INVALID_TIME,
				StringFormat.START));

		ExecutableCommand expected3 = new ExecutableCommand("add");
		expected3.setTaskStartTiming(String.valueOf(d3.getTime()));
		expected3.setTaskEndTiming(String.valueOf(d5.getTime()));

		// test case 1: test if the error catcher is working
		assertEquals("null argument case is not handled",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2: test if the task name is able to be added
		assertEquals("fail to get task name to be added",
				expected.getTaskName(), Analyzer.runAnalyzer(test2)
						.getTaskName());

		// test case 3: test if the task description is able to be added
		assertEquals("fail to get task description to be added",
				expected.getTaskDescription(), Analyzer.runAnalyzer(test3)
						.getTaskDescription());

		// test case 4: test if the task start timing is able to be added
		assertEquals("fail to get task start timing to be added",
				expected.getTaskStartTiming(), Analyzer.runAnalyzer(test4)
						.getTaskStartTiming());

		// test case 5: test if the task location is able to be added
		assertEquals("fail to get task location to be added",
				expected.getTaskLocation(), Analyzer.runAnalyzer(test5)
						.getTaskLocation());

		// test case 6: test if the task priority is able to be added
		assertEquals("fail to get task priority to be added",
				expected.getTaskPriority(), Analyzer.runAnalyzer(test6)
						.getTaskPriority());

		// test case 7: test if the task end timing is able to be added
		assertEquals("fail to get task end timing to be added",
				expected.getTaskEndTiming(), Analyzer.runAnalyzer(test7)
						.getTaskEndTiming());

		// test case 8: test if the task start timing is able to be added
		// correctly without the presence of task end timing
		assertEquals("fail to get only task start timing to be added",
				expected.getTaskStartTiming(), Analyzer.runAnalyzer(test8)
						.getTaskStartTiming());

		// test case 9: test if the task end timing is able to be added
		// correctly without the presence of task start timing
		assertEquals("fail to get only task end timing to be added",
				expected.getTaskEndTiming(), Analyzer.runAnalyzer(test9)
						.getTaskEndTiming());

		// test case 10: test if the task start time will be recorded into task
		// start timing correctly
		assertEquals("fail to get task start timing (with time) to be added",
				expected2.getTaskStartTiming(), Analyzer.runAnalyzer(test10)
						.getTaskStartTiming());

		// test case 11: test if the task end time will be recorded into task
		// end timing correctly
		assertEquals("fail to get task end timing (with time) to be added",
				expected3.getTaskEndTiming(), Analyzer.runAnalyzer(test11)
						.getTaskEndTiming());

		// test case 12: test if the input timing is valid
		assertEquals("fail to get task start timing added with invalid format",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test12)
						.getErrorMessage());

		// test case 13: test if the input timing is valid
		assertEquals("fail to get task start timing added with invalid format",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test13)
						.getErrorMessage());

		// test case 14: test if the input timing is valid
		assertEquals("fail to get task start timing added with invalid format",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test14)
						.getErrorMessage());

		// test case 15: test if the input timing is valid
		assertEquals("fail to get task start timing added with invalid format",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test15)
						.getErrorMessage());

		// test case 16: test if the input timing is valid
		assertEquals("fail to get task start timing added with invalid format",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test16)
						.getErrorMessage());

		// test case 17: test if the input timing is valid
		assertEquals("fail to get task start timing added with invalid format",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test17)
						.getErrorMessage());
	}

	@Test
	public void testHandleDeleteCommand() throws ParseException {
		Command test1 = new Command("delete");
		Command test2 = new Command("delete 0");
		Command test3 = new Command("delete meeting with friends");

		ExecutableCommand expected = new ExecutableCommand("delete");
		expected.setErrorMessage(ERROR_NULL_TASK_INDEX);
		expected.setTaskName("meeting with friends");

		ExecutableCommand expected2 = new ExecutableCommand("delete");
		expected2.setErrorMessage(ERROR_INVALID_TASK_INDEX);

		ExecutableCommand expected3 = new ExecutableCommand("delete");
		expected3.setErrorMessage(ERROR_INVALID_TASK_INDEX);

		// test case 1: test if the error catcher for null argument is working
		assertEquals("null argument case is not handled",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the error catcher for invalid input argument is
		// working
		assertEquals("invalid input argument case is not handled",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test3)
						.getErrorMessage());
	}

	@Test
	public void testHandleUpdateCommand() throws ParseException {
		Command test1 = new Command("update");
		Command test2 = new Command("update 2");
		Command test3 = new Command("update meeting");
		Command test4 = new Command("update 2 name dating");

		ExecutableCommand expected = new ExecutableCommand("update");
		expected.setErrorMessage(ERROR_NULL_TASK_INDEX);

		ExecutableCommand expected2 = new ExecutableCommand("update");
		expected2.setErrorMessage(ERROR_NULL_INDICATOR);

		ExecutableCommand expected3 = new ExecutableCommand("update");
		expected3.setErrorMessage(ERROR_INVALID_TASK_INDEX);

		ExecutableCommand expected4 = new ExecutableCommand("update");
		expected4.setKey("dating");

		// test case 1: test if the error catcher for null argument is working
		assertEquals("null argument case is not handled",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2: test if the error catcher for null update indicator is
		// working
		assertEquals("null update indicator case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the error catcher for invalid input argument is
		// working
		assertEquals("invalid input argument case is not handled",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test3)
						.getErrorMessage());

		// test case 4: test if the task name to be updated is stored correctly
		assertEquals("fail to get task name to be updated", expected4.getKey(),
				Analyzer.runAnalyzer(test4).getKey());

	}

}
