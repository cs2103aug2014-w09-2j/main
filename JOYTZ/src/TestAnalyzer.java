import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;


public class TestAnalyzer {
	private static final String ERROR_NULL_TASK_TO_ADD = "Task to be added is not indicated.";
	private static final String ERROR_NULL_TASK_INDEX = "Task index is not indicated.";
	private static final String ERROR_INVALID_INDEX = "Task index indicated is invalid.";

	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Test
	public void testHandleAddCommand() throws ParseException {
		Command test1 = new Command("add");
		Command test2 = new Command("add,meeting with friends");
		Command test3 = new Command("add,meeting with friends,discuss about CS2103T project");
		Command test4 = new Command("add,meeting with friends,discuss about CS2103T project,14/10/2014");
		Command test5 = new Command("add,meeting with friends,discuss about CS2103T project,14/10/2014,NUS");
		Command test6 = new Command("add,meeting with friends,discuss about CS2103T project,14/10/2014,NUS,medium");
		
		ExecutableCommand expected = new ExecutableCommand("add");
		expected.setErrorMessage(ERROR_NULL_TASK_TO_ADD);
		expected.setTaskName("meeting with friends");
		expected.setTaskDescription("discuss about CS2103T project");
		expected.setTaskDeadline((java.util.Date) df.parse("14/10/2014"));
		expected.setTaskLocation("NUS");
		expected.setTaskPriority("medium");

		// test case 1
		assertEquals("null argument case is not handled", expected.getErrorMessage(), Analyzer.runAnalyzer(test1).getErrorMessage());
	
		// test case 2
		assertEquals("fail to get task name to be added", expected.getTaskName(), Analyzer.runAnalyzer(test2).getTaskName());
		
		// test case 3
		assertEquals("fail to get task description to be added", expected.getTaskDescription(), Analyzer.runAnalyzer(test3).getTaskDescription());
		
		// test case 4
		assertEquals("fail to get task deadline to be added", expected.getTaskDeadline(), Analyzer.runAnalyzer(test4).getTaskDeadline());
		
		// test case 5
		assertEquals("fail to get task location to be added", expected.getTaskLocation(), Analyzer.runAnalyzer(test5).getTaskLocation());

		// test case 6
		assertEquals("fail to get task priority to be added", expected.getTaskPriority(), Analyzer.runAnalyzer(test6).getTaskPriority());
	}
	
	@Test
	public void testHandleDeleteCommand() throws ParseException {
		Command test1 = new Command("delete");
		Command test2 = new Command("delete,0");
		Command test3 = new Command("delete,meeting with friends");
		Command test4 = new Command("delete,1");
		
		ExecutableCommand expected = new ExecutableCommand("delete");
		expected.setErrorMessage(ERROR_NULL_TASK_INDEX);
		expected.setTaskName("meeting with friends");
		expected.setTaskId(1);
		
		ExecutableCommand expected2 = new ExecutableCommand("delete");
		expected2.setErrorMessage(ERROR_INVALID_INDEX);

		// test case 1
		assertEquals("null argument case is not handled", expected.getErrorMessage(), Analyzer.runAnalyzer(test1).getErrorMessage());
	
		// test case 2
		assertEquals("invalid task index case is not handled", expected2.getErrorMessage(), Analyzer.runAnalyzer(test2).getErrorMessage());
		
		// test case 3
		assertEquals("fail to get task name to be deleted", expected.getTaskName(), Analyzer.runAnalyzer(test3).getTaskName());
		
		// test case 4
		assertEquals("fail to get task index to be deleted", expected.getTaskId(), Analyzer.runAnalyzer(test4).getTaskId());
	}

}
