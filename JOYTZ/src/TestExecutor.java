// @author: A0112060E

import static org.junit.Assert.*;

import java.text.ParseException;
import org.junit.Test;

public class TestExecutor {
	ExecutableCommand obj;
	Feedback fb;
	ExecutableCommand clear = new ExecutableCommand("clear");

	@Test
	public void testAddMethod() throws ParseException {
		// check null object or not
		assertNull("An object is not null", obj);

		// check the wrong format or not
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction("ADdd");
		fb = Executor.proceedAnalyzedCommand(obj);
		assertFalse("Correct input format", fb.getResult());
		Executor.proceedAnalyzedCommand(clear);

		// check the input with all attributes correct or not
		obj.setAction(StringFormat.ADD);
		obj.setTaskName("buy bread");

		fb = Executor.proceedAnalyzedCommand(obj);
		assertTrue("Wrong input format", fb.getResult());

		obj.setTaskDescription("delicious bread");
		obj.setTaskLocation("KR MRT");
		obj.setTaskPriority("important");
		obj.setTaskStart("1424748600000");
		obj.setTaskEnd("1424835900000");

		fb = Executor.proceedAnalyzedCommand(obj);
		assertTrue("Fail to add", fb.getResult());
		Executor.proceedAnalyzedCommand(clear);

		// check the input with the start date < the end date
		obj.setTaskEnd("1424748000000");
		fb = Executor.proceedAnalyzedCommand(obj);
		assertFalse("Invalid start and end time", fb.getResult());
		Executor.proceedAnalyzedCommand(clear);

		// check the input without task name
		obj.setTaskName(null);
		fb = Executor.proceedAnalyzedCommand(obj);
		assertFalse("Invalid task name", fb.getResult());

		Executor.proceedAnalyzedCommand(clear);
	}

	@Test
	public void testDeleteMethod() throws ParseException {
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction("add");
		obj.setTaskName("meeting with friends");
		Executor.proceedAnalyzedCommand(obj);

		obj.setTaskName("buy some apples");
		Executor.proceedAnalyzedCommand(obj);

		obj.setTaskName("buy car");
		Executor.proceedAnalyzedCommand(obj);

		obj.setTaskName("travelling");
		Executor.proceedAnalyzedCommand(obj);

		// check delete index is valid or not
		fb = new Feedback(false);
		obj.setAction("delete");
		obj.setTaskId(10);
		fb = Executor.proceedAnalyzedCommand(obj);
		assertFalse("Valid index", fb.getResult());

		// check delete function works or not
		obj = new ExecutableCommand();
		obj.setAction("delete");
		obj.setTaskId(1);
		obj.setTaskId(3);
		fb = Executor.proceedAnalyzedCommand(obj);

		assertTrue("Cannot delete 2 tasks", fb.getResult());

		Executor.proceedAnalyzedCommand(clear);
	}

	@Test
	public void testUpdateMethod() throws ParseException {
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction("add");
		obj.setTaskName("buy car");
		fb = Executor.proceedAnalyzedCommand(obj);

		obj.setTaskName("buy bananas");
		fb = Executor.proceedAnalyzedCommand(obj);

		// check task Id
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction("update");
		obj.setTaskId(3);
		obj.setIndicator("name");
		obj.setKey("outing");
		fb = Executor.proceedAnalyzedCommand(obj);
		
		assertFalse("Valid index", fb.getResult());

		// valid input
		fb.setResult(false);
		obj = new ExecutableCommand();
		obj.setAction("update");
		obj.setTaskId(1);
		obj.setIndicator("priority");
		obj.setKey("high");

		fb = Executor.proceedAnalyzedCommand(obj);
		assertTrue("Invalid indicator", fb.getResult());

		Executor.proceedAnalyzedCommand(clear);
	}

	@Test
	public void testClearMethod() throws ParseException {
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction("add");
		obj.setTaskName("abc");
		fb = Executor.proceedAnalyzedCommand(obj);
		assertNotEquals("Zero task in the table", 0, fb.getTaskStringList()
				.size());

		fb.setResult(false);
		fb = Executor.proceedAnalyzedCommand(clear);
		assertEquals("Not empty", 0, fb.getTaskStringList().size());

		Executor.proceedAnalyzedCommand(clear);
	}

	@Test
	public void testSearchMethod() throws ParseException {
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction("add");
		obj.setTaskName("do assignments");
		Executor.proceedAnalyzedCommand(obj);

		obj.setTaskName("apply a part-time job");
		Executor.proceedAnalyzedCommand(obj);

		obj.setTaskName("apply scholarship");
		Executor.proceedAnalyzedCommand(obj);

		// search a word contained in the task list
		obj.setAction("search");
		obj.setIndicator("name");
		obj.setKey("apply");
		fb = Executor.proceedAnalyzedCommand(obj);

		assertEquals("There are not 2 tasks searched", 2, fb
				.getTaskStringList().size());

		Executor.proceedAnalyzedCommand(clear);
	}

	@Test
	public void testUndoRedoMethod() throws ParseException {
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction("add");
		obj.setTaskName("abc");
		Executor.proceedAnalyzedCommand(obj);

		obj.setTaskName("name");
		Executor.proceedAnalyzedCommand(obj);

		Executor.proceedAnalyzedCommand(clear);

		// check undo function
		obj = new ExecutableCommand();
		fb = new Feedback(false);

		obj.setAction(StringFormat.UNDO);
		fb = Executor.proceedAnalyzedCommand(obj);

		assertTrue("Cannot undo", fb.getResult());

		// check redo function
		fb.setResult(false);
		obj.setAction("redo");
		fb = Executor.proceedAnalyzedCommand(obj);

		assertTrue("Cannot redo", fb.getResult());

		Executor.proceedAnalyzedCommand(clear);
	}
}
