import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class TestAnalyzer {
	private static Date currentDate = new Date(System.currentTimeMillis());
	private static Date d1 = new Date(115, 9, 14);
	private static Date d2 = new Date(115, 9, 20);
	private static Date d3 = new Date(115, 9, 14, 0, 0);
	private static Date d4 = new Date(115, 9, 20, 23, 59);
	private static Date d5 = new Date(115, 9, 20, 2, 38);
	private static int currentYear = currentDate.getYear();
	private static int currentMonth = currentDate.getMonth();
	private static int currentDay = currentDate.getDate();
	private static Date d6 = new Date(currentYear, currentMonth, currentDay,
			23, 59);

	@Test
	public void testHandleAddCommand() throws ParseException {
		Command test1 = new Command("add");
		Command test2 = new Command("add meeting with friends");
		Command test3 = new Command(
				"add meeting with friends; discuss about CS2103T project");
		Command test4 = new Command(
				"add meeting with friends; discuss about CS2103T project on 14/10/2015");
		Command test5 = new Command(
				"add meeting with friends; discuss about CS2103T project on 14/10/2015 @NUS");
		Command test6 = new Command(
				"add meeting with friends; discuss about CS2103T project from 14/10/2015 to 20/10/2015 @NUS #medium");
		Command test7 = new Command(
				"add meeting with friends; discuss about CS2103T project from 14/10/2015 to 20/10/2015 @NUS #medium");
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
		Command test24 = new Command("add trial #high");

		ExecutableCommand expected = new ExecutableCommand(StringFormat.ADD);
		expected.setErrorMessage(StringFormat.ERROR_NULL_TASK);
		expected.setTaskName("meeting with friends");
		expected.setTaskDescription("discuss about CS2103T project");
		expected.setTaskStart(String.valueOf(d1.getTime()));
		expected.setTaskEnd(String.valueOf(d2.getTime()));
		expected.setTaskLocation("NUS");
		expected.setTaskPriority(StringFormat.MEDIUM_PRIORITY);

		ExecutableCommand expected2 = new ExecutableCommand(StringFormat.ADD);
		expected2.setTaskStart(String.valueOf(d3.getTime()));
		expected2.setTaskEnd(String.valueOf(d4.getTime()));
		expected2.setErrorMessage(String.format(
				StringFormat.ERROR_INVALID_TIME, StringFormat.START));
		expected2.setTaskLocation("nus soc");
		expected2.setTaskPriority(StringFormat.HIGH_PRIORITY);

		ExecutableCommand expected3 = new ExecutableCommand(StringFormat.ADD);
		expected3.setTaskStart(String.valueOf(d3.getTime()));
		expected3.setTaskEnd(String.valueOf(d5.getTime()));
		expected3.setErrorMessage(String
				.format(StringFormat.ERROR_INVALID_PRIORITY));

		ExecutableCommand expected4 = new ExecutableCommand(StringFormat.ADD);
		expected4.setErrorMessage(String.format(
				StringFormat.ERROR_INVALID_TIME, StringFormat.END));

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
		
		// test case 24: test if the task priority will be recorded correctly
		assertEquals("fail to get task priority to be added",
				expected2.getTaskPriority(), Analyzer.runAnalyzer(test24)
						.getTaskPriority());
	}

	@Test
	public void testHandleDeleteCommand() throws ParseException {
		Command test1 = new Command("delete");
		Command test2 = new Command("delete 0");
		Command test3 = new Command("delete meeting with friends");
		Command test4 = new Command("delete 1; 3");
		Command test5 = new Command("delete 1; 0; 3");
		Command test6 = new Command("delete 1; meeting with friends");
		Command test7 = new Command("delete 1; 3; -3");
		Command test8 = new Command("delete 1 3 ");
		Command test9 = new Command("delete 1; 3 4");

		ExecutableCommand expected = new ExecutableCommand(StringFormat.DELETE);
		expected.setErrorMessage(StringFormat.ERROR_NULL_TASK_INDEX);
		expected.setTaskId(1);
		expected.setTaskId(3);

		ExecutableCommand expected2 = new ExecutableCommand(StringFormat.DELETE);
		expected2.setErrorMessage(StringFormat.ERROR_INVALID_TASK_INDEX);
		expected2.setTaskId(1);
		expected2.setTaskId(3);
		expected2.setTaskId(4);

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

		// test case 7: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test6)
						.getErrorMessage());

		// test case 6: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test7)
						.getErrorMessage());

		// test case 7: test if multiple delete can be analyzed correctly
		// without the indicator
		assertEquals("multiple delete action is not analyzed correctly",
				expected.getTaskId(), Analyzer.runAnalyzer(test8).getTaskId());

		// test case 8: test if multiple delete can be analyzed correctly with
		// or without the indicator
		assertEquals("multiple delete action is not analyzed correctly",
				expected2.getTaskId(), Analyzer.runAnalyzer(test9).getTaskId());

	}

	@Test
	public void testHandleUpdateCommand() throws ParseException {
		Command test1 = new Command("update");
		Command test2 = new Command("update 2");
		Command test3 = new Command("update meeting");
		Command test4 = new Command("update 2 name dating");
		Command test5 = new Command("update 0 location nus");
		Command test6 = new Command("update 1 importance high");
		Command test7 = new Command("update 1 start");
		Command test8 = new Command("update 1 start 20/10/2015 2:38AM");
		Command test9 = new Command("update 1 end 14/10/2015");
		Command test10 = new Command("update 1 start time 11:59pm");
		Command test11 = new Command("update 1 task to be updated");
		Command test12 = new Command("update 1 name new task");
		Command test13 = new Command("update 1 description new task");
		Command test14 = new Command("update 1 location nus mrt");
		Command test15 = new Command("update 1 priority yes");
		Command test16 = new Command("update 1 priority important");
		Command test17 = new Command("update 1 priority high");

		ExecutableCommand expected = new ExecutableCommand(StringFormat.UPDATE);
		expected.setErrorMessage(StringFormat.ERROR_NULL_TASK_INDEX);
		expected.setKey(String.valueOf(d5.getTime()));

		ExecutableCommand expected2 = new ExecutableCommand(StringFormat.UPDATE);
		expected2.setErrorMessage(StringFormat.ERROR_NULL_INDICATOR);
		expected2.setKey(String.valueOf(d1.getTime()));

		ExecutableCommand expected3 = new ExecutableCommand(StringFormat.UPDATE);
		expected3.setErrorMessage(StringFormat.ERROR_INVALID_TASK_INDEX);
		expected3.setKey(String.valueOf(d6.getTime()));

		ExecutableCommand expected4 = new ExecutableCommand(StringFormat.UPDATE);
		expected4.setKey("dating");
		expected4.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);

		ExecutableCommand expected5 = new ExecutableCommand(StringFormat.UPDATE);
		expected5.setErrorMessage(StringFormat.ERROR_NULL_ARGUMENT);
		expected5.setKey("new task");

		ExecutableCommand expected6 = new ExecutableCommand(StringFormat.UPDATE);
		expected6.setErrorMessage(StringFormat.ERROR_INVALID_PRIORITY);
		expected6.setKey("nus mrt");

		ExecutableCommand expected7 = new ExecutableCommand(StringFormat.UPDATE);
		expected7.setKey(StringFormat.HIGH_PRIORITY);

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

		// test case 5: test if the error catcher for invalid task index is
		// working
		assertEquals("invalid task index case is not handled",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test5)
						.getErrorMessage());

		// test case 6: test if the error catcher for invalid update indicator
		// is working
		assertEquals("invalid update indicator is not detected",
				expected4.getErrorMessage(), Analyzer.runAnalyzer(test6)
						.getErrorMessage());

		// test case 7: test if the error catcher for null argument case is
		// working
		assertEquals("null argument error is not detected",
				expected5.getErrorMessage(), Analyzer.runAnalyzer(test7)
						.getErrorMessage());

		// test case 8: test if the new time to be updated is stored correctly
		assertEquals("new time to be updated is unable to be stored correctly",
				expected.getKey(), Analyzer.runAnalyzer(test8).getKey());

		// test case 9: test if the new time to be updated is stored correctly
		assertEquals("new time to be updated is unable to be stored correctly",
				expected2.getKey(), Analyzer.runAnalyzer(test9).getKey());

		// test case 10: test when the user input something unexpected, but
		// required key words are all existed in valid format, it still work
		assertEquals("new time to be updated is unable to stored correctly",
				expected3.getKey(), Analyzer.runAnalyzer(test10).getKey());

		// test case 11: test if the error catcher for invalid update indicator
		// is working
		assertEquals("invalid update indicator is not detected",
				expected4.getErrorMessage(), Analyzer.runAnalyzer(test11)
						.getErrorMessage());

		// test case 12: test if the new task name to be updated is stored
		// correctly
		assertEquals(
				"new task name to be updated is unable to be stored correctly",
				expected5.getKey(), Analyzer.runAnalyzer(test12).getKey());

		// test case 13: test if the new task description to be updated is
		// stored correctly
		assertEquals(
				"new task description to be updated is unable to be stored correctly",
				expected5.getKey(), Analyzer.runAnalyzer(test13).getKey());

		// test case 14: test if the new task location to be updated is stored
		// correctly
		assertEquals(
				"new task location to be updated is unable to be stored correctly",
				expected6.getKey(), Analyzer.runAnalyzer(test14).getKey());

		// test case 15: test if the error catcher for invalid priority
		// is working
		assertEquals(
				"new task name to be updated is unable to be stored correctly",
				expected6.getErrorMessage(), Analyzer.runAnalyzer(test15)
						.getErrorMessage());

		// test case 16: test if the new task priority to be updated is
		// stored correctly
		assertEquals(
				"new task priority to be updated is unable to be stored correctly",
				expected7.getKey(), Analyzer.runAnalyzer(test16).getKey());

		// test case 16: test if the new task priority to be updated is
		// stored correctly
		assertEquals(
				"new task priority to be updated is unable to be stored correctly",
				expected7.getKey(), Analyzer.runAnalyzer(test17).getKey());

	}

	@Test
	public void testHandleDisplayCommand() throws ParseException {
		Command test1 = new Command("display");
		Command test2 = new Command("displayy");
		Command test3 = new Command("DISPLAY");
		Command test4 = new Command("display asdasdaooxcj");

		ExecutableCommand expected = new ExecutableCommand(StringFormat.DISPLAY);
		expected.setErrorMessage(StringFormat.ERROR_INVALID_COMMAND);

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
	public void testHandleSearchCommand() throws ParseException {
		Command test1 = new Command("search");
		Command test2 = new Command("search name");
		Command test3 = new Command("search task meeting");
		Command test4 = new Command("search name meeting");
		Command test5 = new Command("search name meeting with friends");
		Command test6 = new Command("search start 14/10/2015");
		Command test7 = new Command("search start 11:59pm");
		Command test8 = new Command("search end 20/10/2015 2:38AM");
		Command test9 = new Command("search end time 11:59pm");
		Command test10 = new Command("search description new");
		Command test11 = new Command("search description new task");
		Command test12 = new Command("search location nus");
		Command test13 = new Command("search location kr mrt");
		Command test14 = new Command("search priority yes");
		Command test15 = new Command("search priority unimportant");
		Command test16 = new Command("search priority low");
		Command test17 = new Command("search name meeting; desc new");
		Command test18 = new Command("search name meeting; description new");
		Command test19 = new Command(
				"search name meeting with friends; description new task");
		Command test20 = new Command(
				"search name meeting with friends; description new task; location kr mrt");

		ExecutableCommand expected = new ExecutableCommand(StringFormat.SEARCH);
		expected.setErrorMessage(StringFormat.ERROR_NULL_INDICATOR);
		expected.setKey("meeting");

		ExecutableCommand expected2 = new ExecutableCommand(StringFormat.SEARCH);
		expected2.setErrorMessage(StringFormat.ERROR_NULL_ARGUMENT);
		expected2.setKey("meeting with friends");

		ExecutableCommand expected3 = new ExecutableCommand(StringFormat.SEARCH);
		expected3.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);
		expected3.setKey(String.valueOf(d1.getTime()));

		ExecutableCommand expected4 = new ExecutableCommand(StringFormat.SEARCH);
		expected4.setErrorMessage(StringFormat.ERROR_INVALID_PRIORITY);
		expected4.setKey(String.valueOf(d6.getTime()));

		ExecutableCommand expected5 = new ExecutableCommand(StringFormat.SEARCH);
		expected5.setKey(String.valueOf(d5.getTime()));

		ExecutableCommand expected6 = new ExecutableCommand(StringFormat.SEARCH);
		expected6.setKey("new");

		ExecutableCommand expected7 = new ExecutableCommand(StringFormat.SEARCH);
		expected7.setKey("new task");

		ExecutableCommand expected8 = new ExecutableCommand(StringFormat.SEARCH);
		expected8.setKey("nus");

		ExecutableCommand expected9 = new ExecutableCommand(StringFormat.SEARCH);
		expected9.setKey("kr mrt");

		ExecutableCommand expected10 = new ExecutableCommand(
				StringFormat.SEARCH);
		expected10.setKey(StringFormat.LOW_PRIORITY);

		ExecutableCommand expected11 = new ExecutableCommand(
				StringFormat.SEARCH);
		expected11.setKey("meeting");
		expected11.setKey("new");

		ExecutableCommand expected12 = new ExecutableCommand(
				StringFormat.SEARCH);
		expected12.setKey("meeting with friends");
		expected12.setKey("new task");

		ExecutableCommand expected13 = new ExecutableCommand(
				StringFormat.SEARCH);
		expected13.setKey("meeting with friends");
		expected13.setKey("new task");
		expected13.setKey("kr mrt");

		// test case 1: test if the error catcher for null indicator is working
		// correctly
		assertEquals("unable to detect null indicator error",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2: test if the error catcher for null argument is working
		// correctly
		assertEquals("unable to detect null argument error",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test2)
						.getErrorMessage());

		// test case 3: test if the error catcher for invalid search indicator
		// is able to work correctly
		assertEquals("unable to detect invalid search indicator",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test3)
						.getErrorMessage());

		// test case 4: test if the task name to be searched is stored correctly
		assertEquals("task name to be searched is not stored correctly",
				expected.getKey(), Analyzer.runAnalyzer(test4).getKey());

		// test case 5: test if the task name to be searched is stored correctly
		assertEquals("task name to be searched is not stored correctly",
				expected2.getKey(), Analyzer.runAnalyzer(test5).getKey());

		// test case 6: test if the time to be searched is stored correctly
		assertEquals("time to be searched is not stored correctly",
				expected3.getKey(), Analyzer.runAnalyzer(test6).getKey());

		// test case 7: test if the time to be searched is stored correctly
		assertEquals("time to be searched is not stored correctly",
				expected4.getKey(), Analyzer.runAnalyzer(test7).getKey());

		// test case 8: test if the time to be searched is stored correctly
		assertEquals("time to be searched is not stored correctly",
				expected5.getKey(), Analyzer.runAnalyzer(test8).getKey());

		// test case 9: test if the time to be searched is stored correctly
		assertEquals("time to be searched is not stored correctly",
				expected4.getKey(), Analyzer.runAnalyzer(test9).getKey());

		// test case 10: test if the task description to be searched is stored
		// correctly
		assertEquals("task description to be searched is not stored correctly",
				expected6.getKey(), Analyzer.runAnalyzer(test10).getKey());

		// test case 11: test if the task description to be searched is stored
		// correctly
		assertEquals("task description to be searched is not stored correctly",
				expected7.getKey(), Analyzer.runAnalyzer(test11).getKey());

		// test case 12: test if the task location to be searched is stored
		// correctly
		assertEquals("task location to be searched is not stored correctly",
				expected8.getKey(), Analyzer.runAnalyzer(test12).getKey());

		// test case 13: test if the task location to be searched is stored
		// correctly
		assertEquals("task location to be searched is not stored correctly",
				expected9.getKey(), Analyzer.runAnalyzer(test13).getKey());

		// test case 14: test if the error catcher for invalid priority is able
		// to work correctly
		assertEquals("unable to detect invalid priority",
				expected4.getErrorMessage(), Analyzer.runAnalyzer(test14)
						.getErrorMessage());

		// test case 15: test if the task priority to be searched is stored
		// correctly
		assertEquals("task priority to be searched is not stored correctly",
				expected10.getKey(), Analyzer.runAnalyzer(test15).getKey());

		// test case 16: test if the task priority to be searched is stored
		// correctly
		assertEquals("task priority to be searched is not stored correctly",
				expected10.getKey(), Analyzer.runAnalyzer(test16).getKey());

		// test case 17: test if the error catcher for invalid search indicator
		// is able to work correctly
		assertEquals("unable to detect invalid search indicator",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test17)
						.getErrorMessage());

		// test case 18: test if the multiple search indicators and keys are
		// able to stored correctly correctly
		assertEquals(
				"unable to get multiple search indicators and keys to be stored correctly",
				expected11.getKey(), Analyzer.runAnalyzer(test18).getKey());

		// test case 19: test if the multiple search indicators and keys are
		// able to stored correctly
		assertEquals(
				"unable to get multiple search indicators and keys to be stored correctly",
				expected12.getKey(), Analyzer.runAnalyzer(test19).getKey());

		// test case 20: test if the multiple search indicators and keys are
		// able to stored correctly
		assertEquals(
				"unable to get multiple search indicators and keys to be stored correctly",
				expected13.getKey(), Analyzer.runAnalyzer(test20).getKey());

	}

	@Test
	public void testHandleSortCommand() throws ParseException {
		Command test1 = new Command("sort");
		Command test2 = new Command("sort name");
		Command test3 = new Command("sort priority; location");
		Command test4 = new Command("sort place");
		Command test5 = new Command("sort priority; place");
		Command test6 = new Command("sortS name");
		Command test7 = new Command("SORT start");
		Command test8 = new Command("sort asdasdaooxcj");
		Command test9 = new Command("sort end time");
		Command test10 = new Command("sort priority location");
		Command test11 = new Command("sort priority location; name");

		ExecutableCommand expected = new ExecutableCommand(StringFormat.SORT);
		expected.setErrorMessage(StringFormat.ERROR_NULL_INDICATOR);
		expected.setIndicator(StringFormat.NAME);

		ExecutableCommand expected2 = new ExecutableCommand(StringFormat.SORT);
		expected2.setIndicator(StringFormat.PRIORITY);
		expected2.setIndicator(StringFormat.LOCATION);
		expected2.setErrorMessage(StringFormat.ERROR_INVALID_INDICATOR);

		ExecutableCommand expected3 = new ExecutableCommand(StringFormat.SORT);
		expected3.setErrorMessage(StringFormat.ERROR_INVALID_COMMAND);
		expected3.setIndicator(StringFormat.START);

		ExecutableCommand expected4 = new ExecutableCommand(StringFormat.SORT);
		expected4.setIndicator(StringFormat.PRIORITY);
		expected4.setIndicator(StringFormat.LOCATION);
		expected4.setIndicator(StringFormat.NAME);

		// test case 1: test if the error catcher for null indicator is working
		assertEquals("null indicator is not detected",
				expected.getErrorMessage(), Analyzer.runAnalyzer(test1)
						.getErrorMessage());

		// test case 2: test if the user is able to sort by single indicator
		assertEquals("unable to get the sort indicator correctly",
				expected.getIndicator(), Analyzer.runAnalyzer(test2)
						.getIndicator());

		// test case 3: test if the user is able to sort by multiple indicators
		assertEquals("unable to get multiple sort indicators correctly",
				expected2.getIndicator(), Analyzer.runAnalyzer(test3)
						.getIndicator());

		// test case 4: test if the error catcher for invalid sort indicator is
		// working
		assertEquals("invalid sort indicator is not detected",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test4)
						.getErrorMessage());

		// test case 5: test if the error catcher for invalid sort indicator in
		// multiple indicators is working
		assertEquals("invalid sort indicator is not detected",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test5)
						.getErrorMessage());

		// test case 6: test if the error catcher for invalid sort command is
		// working
		assertEquals("unable to detect invalid sort command",
				expected3.getErrorMessage(), Analyzer.runAnalyzer(test6)
						.getErrorMessage());

		// test case 7: test if the user is able to type in command with
		// capitalized letter
		assertEquals(
				"user is unable to type in command with capitalized letter",
				expected3.getIndicator(), Analyzer.runAnalyzer(test7)
						.getIndicator());

		// test case 8: test if the error catcher for invalid sort indicator is
		// working
		assertEquals("invalid sort indicator is not detected",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test8)
						.getErrorMessage());

		// test case 9: test if the error catcher for invalid sort indicator is
		// working
		assertEquals("invalid sort indicator is not detected",
				expected2.getErrorMessage(), Analyzer.runAnalyzer(test9)
						.getErrorMessage());

		// test case 10: test if multiple sort can be analyzed correctly without
		// the indicator
		assertEquals("multiple sort action is not analyzed correctly",
				expected2.getIndicator(), Analyzer.runAnalyzer(test10)
						.getIndicator());

		// test case 11: test if multiple sort can be analyzed correctly with or
		// without the indicator
		assertEquals("multiple sort action is not analyzed correctly",
				expected4.getIndicator(), Analyzer.runAnalyzer(test11)
						.getIndicator());
	}

	@Test
	public void testHandleUndoCommand() throws ParseException {
		Command test1 = new Command("undo");
		Command test2 = new Command("undoa");
		Command test3 = new Command("UNdo");
		Command test4 = new Command("undo asdasdaooxcj");

		ExecutableCommand expected = new ExecutableCommand(StringFormat.UNDO);
		expected.setErrorMessage(StringFormat.ERROR_INVALID_COMMAND);

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

		ExecutableCommand expected = new ExecutableCommand(StringFormat.REDO);
		expected.setErrorMessage(StringFormat.ERROR_INVALID_COMMAND);

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

		ExecutableCommand expected = new ExecutableCommand(StringFormat.CLEAR);
		expected.setErrorMessage(StringFormat.ERROR_INVALID_COMMAND);

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

		ExecutableCommand expected = new ExecutableCommand(StringFormat.EXIT);
		expected.setErrorMessage(StringFormat.ERROR_INVALID_COMMAND);

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
