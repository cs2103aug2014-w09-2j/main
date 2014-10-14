import static org.junit.Assert.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class TestStorage {
	private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@Test
	public void testAddTask() {
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date(), "Exam", "NUS", "high");
		Task task2; 
		
		assertEquals(true, Storage.add(task1));
	}
	
	
	@Test
	public void testDeleteTask() {
		Storage.clean();
		Task task1 = new Task("task1", new Date(), "Exam1", "NUS", "high");
		Task task2 = new Task("task2", new Date(), "Exam2", "NUS", "high");
		
		Storage.add(task1);
		Storage.add(task2);
		
		assertEquals(true, Storage.delete(1));
		assertEquals(1, Storage.getTaskListSize());
	}
	
	@Test
	public void testUpdateTask(){
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date(), "Exam1", "NUS", "high");
		Task task2 = new Task("task2", new Date(), "Exam2", "NUS", "high");
		
		Storage.add(task1);
		Storage.add(task2);
		
		try {
			
			String expectedNameString = "task3";
			String expectedDescriptionString = "Exam3";
			String expectedLocationString = "SOC";
			String expectedPriorityString = "medium";
			Date expectedDate = (Date)dateFormat.parse("11-11-2015");
			
			assertEquals(true, Storage.update(1, "name", expectedNameString));
			assertEquals(true, Storage.update(1, "description", expectedDescriptionString));
			assertEquals(true, Storage.update(1, "location", expectedLocationString));
			assertEquals(true, Storage.update(1, "deadline", "11-11-2015"));
			assertEquals(true, Storage.update(1, "priority", expectedPriorityString));
			
			String nameString = Storage.get(1).getTaskName();
			String discriptString = Storage.get(1).getTaskDescription();
			String locationString = Storage.get(1).getTaskLocation();
			Date date = Storage.get(1).getTaskDeadline();
			String priorityString = Storage.get(1).getTaskPriority();
			
			assertEquals(expectedNameString, nameString);
			assertEquals(expectedDescriptionString, discriptString);
			assertEquals(expectedLocationString, locationString);
			assertEquals(expectedPriorityString, priorityString);
			assertEquals(expectedDate, date);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testClean() {
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date(), "Exam1", "NUS", "high");
		Task task2 = new Task("task2", new Date(), "Exam2", "NUS", "high");
		
		Storage.add(task1);
		Storage.add(task2);
		
		assertEquals(2, Storage.getTaskListSize());
		
		Storage.clean();
		
		assertEquals(0, Storage.getTaskListSize());
		assertEquals(2, Storage.getHistorySize());
	}
	
	@Test
	public void testSaveFile() {
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date(), "Exam1", "NUS", "high");
		Task task2 = new Task("task2", new Date(), "Exam2", "NUS", "high");
		
		Storage.add(task1);
		Storage.add(task2);
		
		assertEquals(2, Storage.getTaskListSize());
		try {
			Storage.saveFile();
			Storage.cleanUpEveryThing();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
