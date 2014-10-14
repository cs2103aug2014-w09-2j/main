/*
 * @author: Thang
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class TestExecutor1 {

	@Test
	public void testInvalidComand() {
		ExecutableCommand obj = new ExecutableCommand();
		Feedback fb = new Feedback(false);

		// test wrong command format
		obj.setAction("ad");
		obj.setTaskDescription("description");
		fb = Executor.proceedAnalyzedCommand(obj);

		assertFalse("wrong command type", fb.getResult());
		assertSame("no element", 0, fb.getTaskList().size());
		assertSame("message", "Invalid command.\n", fb.getErrorMessage());
		assertEquals("no task location", "", obj.getTaskLocation());
	}

	@Test
	public void testAddFunction() {
		ExecutableCommand obj = new ExecutableCommand();
		Feedback fb = new Feedback(false);

		// test invalid add task cases.
		obj.setAction("add");
		obj.setTaskLocation("Europe");
		fb = Executor.proceedAnalyzedCommand(obj);
		
		assertFalse("no user's task", fb.getResult());

		// valid input for add function
		obj.setTaskName("travelling");
		fb = Executor.proceedAnalyzedCommand(obj);
		
		assertTrue("got a user's task", fb.getResult());
		assertSame("task name", "travelling", obj.getTaskName());
	}

	@Test
	public void testDeleteFunction() {
		ExecutableCommand obj3 = new ExecutableCommand();
		Feedback fb3 = new Feedback(false);

		// test add function
		obj3.setAction("add");
		obj3.setTaskLocation("NUS");
		obj3.setTaskName("studying");
		fb3 = Executor.proceedAnalyzedCommand(obj3);

		obj3.setAction("add");
		obj3.setTaskName("meeting");
		obj3.setTaskDescription("CS2103T project");
		fb3 = Executor.proceedAnalyzedCommand(obj3);

		assertEquals("numbers of elements in table", 2, fb3.getTaskList()
				.size());

		// test delete function
		obj3.setAction("delete");
		obj3.setTaskId(1);
		fb3 = Executor.proceedAnalyzedCommand(obj3);
		
		assertEquals("there is 1 task in table", 1, fb3.getTaskList().size());
		assertEquals("a particular task in table",
				"meeting~CS2103T project~NUS", fb3.getTaskList().get(0));
	}	
	
	@Test
	public void testUpdateFunction() {
		ExecutableCommand obj3 = new ExecutableCommand();
		Feedback fb3 = new Feedback(false);

		obj3.setAction("add");
		obj3.setTaskName("meeting");
		obj3.setTaskDescription("CS2103T project");
		fb3 = Executor.proceedAnalyzedCommand(obj3);
		
		// test update function
		obj3.setAction("update");
		obj3.setTaskId(1);
		obj3.setUpdateIndicator("name");
		obj3.setUpdatedTaskName("project meeting");
		fb3 = Executor.proceedAnalyzedCommand(obj3);
		
		assertEquals("change name of 2nd task",
				"project meeting~CS2103T project~NUS", fb3.getTaskList().get(0));

	}
	
	@Test
	public void testCleanFunction(){
		// test clean function
		ExecutableCommand obj3 = new ExecutableCommand();
		Feedback fb3 = new Feedback(false);
		
		obj3.setAction("add");
		obj3.setTaskName("studying");
		fb3 = Executor.proceedAnalyzedCommand(obj3);

		obj3.setAction("add");
		obj3.setTaskName("meeting");
		fb3 = Executor.proceedAnalyzedCommand(obj3);
		
		assertNotEquals("some tasks", 0, fb3.getTaskList().size());
		
		obj3.setAction("clean");
		fb3 = Executor.proceedAnalyzedCommand(obj3);
		
		assertEquals("0 task in table", 0, fb3.getTaskList().size());
	}

	@Test
	public void testExceptionMessage() {
		Feedback fb3 = new Feedback(false);

		try {
			fb3.getTaskList().get(0);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Index: 0, Size: 0");
		}
	}
}