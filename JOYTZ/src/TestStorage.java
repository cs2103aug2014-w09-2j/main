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
		Task task1 = new Task("task1", new Date().getTime(), new Date().getTime(), "Exam", "NUS", "high");
		Task task2; 
		
		try {
			assertEquals(true, Storage.add(task1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDeleteTask() {
		Storage.clean();
		Task task1 = new Task("task1", new Date().getTime(), new Date().getTime(), "Exam", "NUS", "high");
		Task task2 = new Task("task2", new Date().getTime(), new Date().getTime(), "Exam2", "SOC", "high");
		
		try {
			Storage.add(task1);
			Storage.add(task2);

			assertEquals(true, Storage.delete(1));
			assertEquals(1, Storage.getTaskListSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdateTask(){
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date().getTime(), new Date().getTime(), "Exam", "NUS", "high");
		Task task2 = new Task("task2", new Date().getTime(), new Date().getTime(), "Exam2", "SOC", "high");
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			Storage.add(task1);
			Storage.add(task2);
			
			String expectedNameString = "task3";
			String expectedDescriptionString = "Exam3";
			String expectedLocationString = "SOC";
			String expectedPriorityString = "medium";
			Date expectedStartDate = (Date)dateFormat.parse("11-11-2015");
			
			assertEquals(true, Storage.update(1, "name", expectedNameString));
			assertEquals(true, Storage.update(1, "description", expectedDescriptionString));
			assertEquals(true, Storage.update(1, "location", expectedLocationString));
			assertEquals(true, Storage.update(1, "start date", expectedStartDate.getTime()+""));
			assertEquals(true, Storage.update(1, "priority", expectedPriorityString));
			
			String nameString = Storage.get(1).getTaskName();
			String discriptString = Storage.get(1).getTaskDescription();
			String locationString = Storage.get(1).getTaskLocation();
			Date date = new Date(Storage.get(1).getTaskStartTime());
			String priorityString = Storage.get(1).getTaskPriority();
			
			assertEquals(expectedNameString, nameString);
			assertEquals(expectedDescriptionString, discriptString);
			assertEquals(expectedLocationString, locationString);
			assertEquals(expectedPriorityString, priorityString);
			assertEquals(df.format(expectedStartDate), df.format(date));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testClean(){
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date().getTime(), new Date().getTime(), "Exam", "NUS", "high");
		Task task2 = new Task("task2", new Date().getTime(), new Date().getTime(), "Exam2", "SOC", "high");
		
		try {
			Storage.add(task1);
			Storage.add(task2);
			
			assertEquals(2, Storage.getTaskListSize());
			
			Storage.clean();
			
			assertEquals(0, Storage.getTaskListSize());
			assertEquals(2, Storage.getHistorySize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSaveReloadFile() {
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date().getTime(), new Date().getTime(), "Exam", "NUS", "high");
		Task task2 = new Task("task2", new Date().getTime(), new Date().getTime(), "Exam2", "SOC", "high");
		
		try {
			Storage.add(task1);
			Storage.add(task2);
			
			assertEquals(2, Storage.getTaskListSize());
			
			Storage.saveFile();
			Storage.cleanUpEveryThing();
			Storage.reloadFile();
			
			assertEquals(2, Storage.getTaskListSize());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testSort(){
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date().getTime(), new Date().getTime(), "Exam", "NUS", "high");
		Task task2 = new Task("task2", new Date().getTime(), new Date().getTime(), "Exam2", "SOC", "high");
		
		try{
			Storage.add(task1);
			Storage.add(task2);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
