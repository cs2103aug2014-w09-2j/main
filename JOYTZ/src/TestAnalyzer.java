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
	private static final String ERROR_INVALID_PRIORITY = "Input priority is invalid.\n";
	private static final String ERROR_INVALID_COMMAND = "Invalid command.\n";

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
		Command test12 = new Command("add trial on 32/2/2015");
		Command test13 = new Command("add trial on 25/13/2015");
		Command test14 = new Command("add trial on 29/2/2017");
		Command test15 = new Command("add trial on 31/4/2015");
		Command test16 = new Command("add trial on 22/2/2017 13:24PM");
		Command test17 = new Command("add trial on 22/4/2015 12:60AM");
		Command test18 = new Command("add trial @nus soc #important");
		Command test19 = new Command("add trial #kr mrt");
		Command test20 = new Command("add trial on 3:45pm");
		Command test21 = new Command("add trial at 11/11/2014");
		Command test22 = new Command("add trial due at 3/11/2015");
		Command test23 = new Command("add trial due on 3:45pm");

		ExecutableCommand expected = new ExecutableCommand("add");
		expected.setErrorMessage(ERROR_NULL_TASK);
		expected.setTaskName("meeting with friends");
		expected.setTaskDescription("discuss about CS2103T project");
		expected.setTaskStart(String.valueOf(d1.getTime()));
		expected.setTaskEnd(String.valueOf(d2.getTime()));
		expected.setTaskLocation("NUS");
		expected.setTaskPriority("medium");

		ExecutableCommand expected2 = new ExecutableCommand("add");
		expected2.setTaskStart(String.valueOf(d3.getTime()));
		expected2.setTaskEnd(String.valueOf(d4.getTime()));
		expected2.setErrorMessage(String.format(ERROR_INVALID_TIME,
				StringFormat.START));
		expected2.setTaskLocation("nus soc");

		ExecutableCommand expected3 = new ExecutableCommand("add");
		expected3.setTaskStart(String.valueOf(d3.getTime()));
		expected3.setTaskEnd(String.valueOf(d5.getTime()));
		expected3.setErrorMessage(String.format(ERROR_INVALID_PRIORITY));

		ExecutableCommand expected4 = new ExecutableCommand("add");
		expected4.setErrorMessage(String.format(ERROR_INVALID_TIME,
				StringFormat.END));

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
				expected.getTaskStart(), Analyzer.runAnalyzer(test4)
						.getTaskStart());

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
				expected.getTaskEnd(), Analyzer.runAnalyzer(test7).getTaskEnd());

		// test case 8: test if the task start timing is able to be added
		// correctly without the presence of task end timing
		assertEquals("fail to get only task start timing to be added",
				expected.getTaskStart(), Analyzer.runAnalyzer(test8)
						.getTaskStart());

		// test case 9: test if the task end timing is able to be added
		// correctly without the presence of task start timing
		assertEquals("fail to get only task end timing to be added",
				expected.getTaskEnd(), Analyzer.runAnalyzer(test9).getTaskEnd());

		// test case 10: test if the task start time will be recorded into task
		// start timing correctly
		assertEquals("fail to get task start timing (with time) to be added",
				expected2.getTaskStart(), Analyzer.runAnalyzer(test10)
						.getTaskStart());

		// test case 11: test if the task end time will be recorded into task
		// end timing correctly
		assertEquals("fail to get task end timing (with time) to be added",
				expected3.getTaskEnd(), Analyzer.runAnalyzer(test11)
						.getTaskEnd());

		// test case 12: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start timing",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test12)
						.getErrorMessage());

		// test case 13: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start timing",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test13)
						.getErrorMessage());

		// test case 14: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start timing",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test14)
						.getErrorMessage());

		// test case 15: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start timing",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test15)
						.getErrorMessage());

		// test case 16: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start timing",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test16)
						.getErrorMessage());

		// test case 17: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start timing",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test17)
						.getErrorMessage());

		// test case 18: test if the task location is able to be added correctly
		// with space within
		assertEquals("fail to get task location to be added",
				expected2.getTaskLocation(), Analyzer.runAnalyzer(test18)
						.getTaskLocation());

		// test case 19: test if the invalid priority is able to be detected
		assertEquals("fail to detect invalid task priority",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test19)
						.getErrorMessage());

		// test case 20: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start time",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test20)
						.getErrorMessage());

		// test case 21: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task start time",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test21)
						.getErrorMessage());

		// test case 22: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task end time",
				expected4.getErrorMessage(), Analyzer.runAnalyzer(test22)
						.getErrorMessage());

		// test case 23: test if the invalid time is able to be detected
		assertEquals("fail to detect invalid task end time",
				expected4.getErrorMessage(), Analyzer.runAnalyzer(test23)
						.getErrorMessage());
	}

	@Test
	public void testHandleDeleteCommand() throws ParseException {
		Command test1 = new Command("delete");
		Command test2 = new Command("delete 0");
		Command test3 = new Command("delete meeting with friends");
		Command test4 = new Command("delete 1 3");
		Command test5 = new Command("delete 1 0 3");
		Command test6 = new Command("delete 1 meeting with friends");
		Command test7 = new Command("delete 1 3 -3");

		ExecutableCommand expected = new ExecutableCommand("delete");
		expected.setErrorMessage(ERROR_NULL_TASK_INDEX);
		expected.setTaskId(1);
		expected.setTaskId(3);

		ExecutableCommand expected2 = new ExecutableCommand("delete");
		expected2.setErrorMessage(ERROR_INVALID_TASK_INDEX);

		// test case 1: test if the error catcher for null argument is working
		assertEquals("null argument case is not handled",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test3)
						.getErrorMessage());

		// test case 4: test if multiple delete can be analyzed correctly
		assertEquals("multiple delete action is not analyzed correctly",
				expected.getTaskId(), Analyzer.runAnalyzer(test4).getTaskId());

		// test case 5: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test5)
						.getErrorMessage());

		// test case 6: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test6)
						.getErrorMessage());

		// test case 6: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test7)
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

	@Test
	public void testHandleDisplayCommand() throws ParseException {
		Command test1 = new Command("display");
		Command test2 = new Command("displayy");
		Command test3 = new Command("DISPLAY");
		Command test4 = new Command("display asdasdaooxcj");

		ExecutableCommand expected = new ExecutableCommand("display");
		expected.setErrorMessage(ERROR_INVALID_COMMAND);

		// test case 1: test if the display action is processed correctly
		assertEquals("display action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test1).getAction());

		// test case 2: test if the error catcher for invalid display command is
		// working
		assertEquals("unable to detect invalid display command",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the user is able to type in command with
		// capitalized letter
		assertEquals(
				"user is unable to type in command with capitalized letter",
				expected.getAction(), Analyzer.runAnalyzer(test3).getAction());

		// test case 4: test if the display action is processed correctly
		assertEquals("display action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test4).getAction());

	}

	@Test
	public void testHandleUndoCommand() throws ParseException {
		Command test1 = new Command("undo");
		Command test2 = new Command("undoa");
		Command test3 = new Command("UNdo");
		Command test4 = new Command("undo asdasdaooxcj");

		ExecutableCommand expected = new ExecutableCommand("undo");
		expected.setErrorMessage(ERROR_INVALID_COMMAND);

		// test case 1: test if the undo action is processed correctly
		assertEquals("undo action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test1).getAction());

		// test case 2: test if the error catcher for invalid undo command is
		// working
		assertEquals("unable to detect invalid undo command",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the user is able to type in command with
		// capitalized letter
		assertEquals(
				"user is unable to type in command with capitalized letter",
				expected.getAction(), Analyzer.runAnalyzer(test3).getAction());

		// test case 4: test if the undo action is processed correctly
		assertEquals("undo action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test4).getAction());

	}

	@Test
	public void testHandleRedoCommand() throws ParseException {
		Command test1 = new Command("redo");
		Command test2 = new Command("redopls");
		Command test3 = new Command("ReDo");
		Command test4 = new Command("ReDO asdasdaooxcj");

		ExecutableCommand expected = new ExecutableCommand("redo");
		expected.setErrorMessage(ERROR_INVALID_COMMAND);

		// test case 1: test if the redo action is processed correctly
		assertEquals("redo action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test1).getAction());

		// test case 2: test if the error catcher for invalid redo command is
		// working
		assertEquals("unable to detect invalid redo command",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the user is able to type in command with
		// capitalized letter
		assertEquals(
				"user is unable to type in command with capitalized letter",
				expected.getAction(), Analyzer.runAnalyzer(test3).getAction());

		// test case 4: test if the redo action is processed correctly
		assertEquals("redo action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test4).getAction());

	}

	@Test
	public void testHandleClearCommand() throws ParseException {
		Command test1 = new Command("clear");
		Command test2 = new Command("clearS");
		Command test3 = new Command("CLEar");
		Command test4 = new Command("CLEAr asdasdaooxcj");

		ExecutableCommand expected = new ExecutableCommand("clear");
		expected.setErrorMessage(ERROR_INVALID_COMMAND);

		// test case 1: test if the clear action is processed correctly
		assertEquals("clear action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test1).getAction());

		// test case 2: test if the error catcher for invalid clear command is
		// working
		assertEquals("unable to detect invalid clear command",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the user is able to type in command with
		// capitalized letter
		assertEquals(
				"user is unable to type in command with capitalized letter",
				expected.getAction(), Analyzer.runAnalyzer(test3).getAction());

		// test case 4: test if the clear action is processed correctly
		assertEquals("clear action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test4).getAction());

	}
	
	@Test
	public void testHandleExitCommand() throws ParseException {
		Command test1 = new Command("exit");
		Command test2 = new Command("exitS");
		Command test3 = new Command("ExIT");
		Command test4 = new Command("EXIT asdasdaooxcj");

		ExecutableCommand expected = new ExecutableCommand("exit");
		expected.setErrorMessage(ERROR_INVALID_COMMAND);

		// test case 1: test if the exit action is processed correctly
		assertEquals("exit action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test1).getAction());

		// test case 2: test if the error catcher for invalid exit command is
		// working
		assertEquals("unable to detect invalid redo command",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the user is able to type in command with
		// capitalized letter
		assertEquals(
				"user is unable to type in command with capitalized letter",
				expected.getAction(), Analyzer.runAnalyzer(test3).getAction());

		// test case 4: test if the exit action is processed correctly
		assertEquals("exit action is not processed correctly",
				expected.getAction(), Analyzer.runAnalyzer(test4).getAction());

	}
}
