import static org.junit.Assert.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class TestStorage {
	
	@Test
	public void testAddTask() {
		
		Long l = Long.valueOf("32880441600000");
		System.out.print(new Date(l).getYear());
		
		
		Storage.cleanUpEveryThing();
		Task task1 = new Task("task1", new Date().getTime(), new Date().getTime(), "Exam", "NUS", "high");
		Task task2 = new Task("task2", new Date().getTime(), new Date().getTime(), "Exam2", "SOC", "high");
		
		try {
			assertEquals(true, Storage.add(task1));
			assertEquals(true, Storage.add(task2));
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
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
		
		try {
			Storage.add(task1);
			Storage.add(task2);
			
			String expectedNameString = "task3";
			String expectedDescriptionString = "Exam3";
			String expectedLocationString = "SOC";
			String expectedPriorityString = "medium";
			Date expectedStartDate = (Date)df.parse("11-11-2015");
			Date expectedStartTime = (Date)tf.parse("13:30");
			
			assertEquals(true, Storage.update(1, "name", expectedNameString));
			assertEquals(true, Storage.update(1, "description", expectedDescriptionString));
			assertEquals(true, Storage.update(1, "location", expectedLocationString));
			assertEquals(true, Storage.update(1, "start date", expectedStartDate.getTime()+""));
			assertEquals(true, Storage.update(1, "start time", expectedStartTime.getTime()+""));
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
			assertEquals(tf.format(expectedStartTime), tf.format(date));
			
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
			
			String expectedName1 = "task1";
			String expectedName2 = "task2";
			
			String name1 = Storage.get(1).getTaskName();
			String name2 = Storage.get(2).getTaskName();
			
			assertEquals(2, Storage.getTaskListSize());
			assertEquals(expectedName1, name1);
			assertEquals(expectedName2, name2);
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
