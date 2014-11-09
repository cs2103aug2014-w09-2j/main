// @author A0119378U
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * This test for Storage contains tests for ADD, DELETE, UPDATE, SORT, SEARCH.
 * The FileInOut and checkTime() will be tested in the Integrated Test for the
 * Software.
 *
 *@author Zhang Kai
 */
public class TestStorage {

	@Test
	// Add normal Task Object.
	public void testAddNormalTask() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("task1"); // Only with name.
		Task task2 = new Task("task2");
		{// With start date time.
			task2.setStartDateTime(new Date(Long.MAX_VALUE));
		}
		Task task3 = new Task("task3");
		{// With end date time.
			task3.setEndDateTime(new Date(Long.MAX_VALUE));
		}
		Task task4 = new Task("task4");
		{// With location.
			task4.setTaskLocation("SoC");
		}
		Task task5 = new Task("task5");
		{// With priority.
			task5.setTaskPriority("high");
		}

		boolean expectedResult = true;

		try {
			assertEquals(expectedResult, Storage.add(task1));
			assertEquals(expectedResult, Storage.add(task2));
			assertEquals(expectedResult, Storage.add(task3));
			assertEquals(expectedResult, Storage.add(task4));
			assertEquals(expectedResult, Storage.add(task5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// Add null Task Object & Task without name.
	public void testAddTaskWithoutName() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = null; // Null task.
		Task task2 = new Task();
		{// Only with start date time.
			task2.setStartDateTime(new Date());
		}
		Task task3 = new Task();
		{// Only with end date time.
			task3.setEndDateTime(new Date());
		}
		Task task4 = new Task();
		{// Only with location.
			task4.setTaskLocation("SoC");
		}
		Task task5 = new Task();
		{// Only with priority.
			task5.setTaskPriority("high");
		}

		String expectedResult1 = "Cannot add a null task.\n";
		String expectedResult2 = "Cannot add a task without a name.\n";
		String expectedResult3 = "Cannot add a task without a name.\n";
		String expectedResult4 = "Cannot add a task without a name.\n";
		String expectedResult5 = "Cannot add a task without a name.\n";

		try {
			Storage.add(task1);
		} catch (Exception e) {
			assertEquals(expectedResult1, e.getMessage());
			;
		}
		try {
			Storage.add(task2);
		} catch (Exception e) {
			assertEquals(expectedResult2, e.getMessage());
			;
		}
		try {
			Storage.add(task3);
		} catch (Exception e) {
			assertEquals(expectedResult3, e.getMessage());
			;
		}
		try {
			Storage.add(task4);
		} catch (Exception e) {
			assertEquals(expectedResult4, e.getMessage());
			;
		}
		try {
			Storage.add(task5);
		} catch (Exception e) {
			assertEquals(expectedResult5, e.getMessage());
			;
		}
	}

	@Test
	// Add Task Object with invalid time.
	public void testAddTaskWithInvalidTime() {
		Storage.clean();
		Storage.resetTaskId();

		Long time = System.currentTimeMillis();
		Long timeBeforeNow = time - 1000;
		Long timeAfterNow = time + 10000;
		Long timeEvenAfterNow = time + 100000;

		Task task1 = new Task("task1");
		{ // Start time is before current time.
			task1.setStartDateTime(new Date(timeBeforeNow));
		}
		Task task2 = new Task("task2");
		{ // End time is before current time.
			task2.setEndDateTime(new Date(timeBeforeNow));
		}
		Task task3 = new Task("task3");
		{ // Start time is after End time.
			task3.setStartDateTime(new Date(timeEvenAfterNow));
			task3.setEndDateTime(new Date(timeAfterNow));
		}

		String expectedResult1 = "Start time is before current time.";
		String expectedResult2 = "End time is before current time. ";
		String expectedResult3 = "Start time is after End time.";

		try {
			Storage.add(task1);
		} catch (Exception e) {
			assertEquals(true, e.getMessage().contains(expectedResult1));
			;
		}
		try {
			Storage.add(task2);
		} catch (Exception e) {
			assertEquals(true, e.getMessage().contains(expectedResult2));
			;
		}
		try {
			Storage.add(task3);
		} catch (Exception e) {
			assertEquals(true, e.getMessage().contains(expectedResult3));
			;
		}

	}

	@Test
	// Delete Task Object.
	public void testDeleteNormalIndex() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("task1", 0);
		Task task2 = new Task("task2", 1);
		Task task3 = new Task("task3", 2);
		Task task4 = new Task("task4", 3);
		Task task5 = new Task("task5", 4);

		List expectedMainTaskList1 = new List();
		{
			expectedMainTaskList1.addTask(task2);
			expectedMainTaskList1.addTask(task3);
			expectedMainTaskList1.addTask(task4);
			expectedMainTaskList1.addTask(task5);
		}
		List expectedMainTaskList2 = new List();
		{
			expectedMainTaskList1.addTask(task3);
			expectedMainTaskList1.addTask(task4);
			expectedMainTaskList1.addTask(task5);
		}

		try {
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);
			Storage.add(task4);
			Storage.add(task5);

			Storage.delete(0);
			assertEquals(true,
					expectedMainTaskList1.equals(Storage.getMainTaskList()));

			Storage.delete(1);
			assertEquals(true,
					expectedMainTaskList2.equals(Storage.getMainTaskList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// Delete with invalid index.
	public void testDeleteInvalidIndex() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("task1");
		Task task2 = new Task("task2");
		Task task3 = new Task("task3");

		String expectedResult1 = "Task index is out of range. Index : 0.\n";
		String expectedResult2 = "Task index is out of range. Index : 4.\n";

		try {
			Storage.clean();
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			Storage.delete(-1);
		} catch (Exception e) {
			assertEquals(expectedResult1, e.getMessage());
		}
		try {
			Storage.clean();
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			Storage.delete(3);
		} catch (Exception e) {
			assertEquals(expectedResult2, e.getMessage());
		}
	}

	@Test
	// update normal task object.
	public void testUpdateNormalIndicator() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("task1", 0);
		Task task2 = new Task("task2", 1);
		Task task3 = new Task("task3", 2);

		List expectedDoneTaskList1 = new List();
		{
			expectedDoneTaskList1.addTask(task1);
			expectedDoneTaskList1.addTask(task2);
			expectedDoneTaskList1.addTask(task3);
		}
		List expectedDoneTaskList2 = new List();
		{
			expectedDoneTaskList2.addTask(task1);
			expectedDoneTaskList2.addTask(task2);
		}
		List expectedDoneTaskList3 = new List();
		{
			expectedDoneTaskList3.addTask(task1);
		}
		List expectedDoneTaskList4 = new List();

		try {
			Storage.clean();
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			assertEquals(true,
					expectedDoneTaskList4.equals(Storage.getDoneTaskList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Storage.clean();
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			Storage.done(0);
			assertEquals(true,
					expectedDoneTaskList3.equals(Storage.getDoneTaskList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Storage.clean();
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			Storage.done(0);
			Storage.done(0);
			assertEquals(true,
					expectedDoneTaskList2.equals(Storage.getDoneTaskList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Storage.clean();
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			Storage.done(0);
			Storage.done(0);
			Storage.done(0);
			assertEquals(true,
					expectedDoneTaskList1.equals(Storage.getDoneTaskList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// clean taskList and doneList.
	public void testClean() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("task1"); // Only with name.
		Task task2 = new Task("task2");
		{// With start date time.
			task2.setStartDateTime(new Date(Long.MAX_VALUE));
		}
		Task task3 = new Task("task3");
		{// With end date time.
			task3.setEndDateTime(new Date(Long.MAX_VALUE));
		}
		Task task4 = new Task("task4");
		{// With location.
			task4.setTaskLocation("SoC");
		}
		Task task5 = new Task("task5");
		{// With priority.
			task5.setTaskPriority("high");
		}

		int expectedTaskListSizeBeforeClean = 4;
		int expectedTaskListSizeAfterClean = 0;
		int expectedDoneListSizeBeforeClean = 1;
		int expectedDoneListSizeAfterClean = 0;

		try {
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);
			Storage.add(task4);
			Storage.add(task5);
			Storage.done(0);

			assertEquals(expectedTaskListSizeBeforeClean, Storage
					.getMainTaskList().size());
			assertEquals(expectedDoneListSizeBeforeClean, Storage
					.getDoneTaskList().size());

			Storage.clean();
			assertEquals(expectedTaskListSizeAfterClean, Storage
					.getMainTaskList().size());
			assertEquals(expectedDoneListSizeAfterClean, Storage
					.getDoneTaskList().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// Sort by name.
	public void testSortByName() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("e"); // Only with name.
		Task task2 = new Task("d");
		Task task3 = new Task("c");
		Task task4 = new Task("b");
		Task task5 = new Task("a");

		try {
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);
			Storage.add(task4);
			Storage.add(task5);

			assertEquals(task1, Storage.getMainTaskList().getTaskByIndex(0));
			assertEquals(task2, Storage.getDoneTaskList().getTaskByIndex(1));
			assertEquals(task3, Storage.getDoneTaskList().getTaskByIndex(2));
			assertEquals(task4, Storage.getDoneTaskList().getTaskByIndex(3));
			assertEquals(task5, Storage.getDoneTaskList().getTaskByIndex(4));

			Storage.sort("name");

			assertEquals(task5, Storage.getMainTaskList().getTaskByIndex(0));
			assertEquals(task4, Storage.getDoneTaskList().getTaskByIndex(1));
			assertEquals(task3, Storage.getDoneTaskList().getTaskByIndex(2));
			assertEquals(task2, Storage.getDoneTaskList().getTaskByIndex(3));
			assertEquals(task1, Storage.getDoneTaskList().getTaskByIndex(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// Sort by priority.
	public void testSortByPriority() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("e");
		{
			task1.setTaskPriority("low");
		}
		Task task2 = new Task("c");
		{
			task2.setTaskPriority("medium");
		}
		Task task3 = new Task("a");
		{
			task3.setTaskPriority("high");
		}

		try {
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			assertEquals(task1, Storage.getMainTaskList().getTaskByIndex(0));
			assertEquals(task2, Storage.getDoneTaskList().getTaskByIndex(1));
			assertEquals(task3, Storage.getDoneTaskList().getTaskByIndex(2));

			Storage.sort("priority");

			assertEquals(task3, Storage.getMainTaskList().getTaskByIndex(0));
			assertEquals(task2, Storage.getDoneTaskList().getTaskByIndex(1));
			assertEquals(task1, Storage.getDoneTaskList().getTaskByIndex(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// Search the name of Task.
	public void testSearchNormalCase() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("e");
		{
			task1.setTaskPriority("low");
		}
		Task task2 = new Task("c");
		{
			task2.setTaskPriority("medium");
		}
		Task task3 = new Task("a");
		{
			task3.setTaskPriority("high");
		}

		try {
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			Storage.search("name", "a");
			assertEquals(task3, Storage.getDisplayTaskList().getTaskByIndex(0));

			Storage.display("mainTaskList");

			Storage.search("priority", "low");
			assertEquals(task1, Storage.getDisplayTaskList().getTaskByIndex(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// Search for invalid indicator of Task.
	public void testSearchInvalidIndicator() {
		Storage.clean();
		Storage.resetTaskId();

		Task task1 = new Task("e");
		{
			task1.setTaskPriority("low");
		}
		Task task2 = new Task("c");
		{
			task2.setTaskPriority("medium");
		}
		Task task3 = new Task("a");
		{
			task3.setTaskPriority("high");
		}

		int expectedDisplayListSize = 0;

		try {
			Storage.add(task1);
			Storage.add(task2);
			Storage.add(task3);

			Storage.search("name", "b");
			assertEquals(expectedDisplayListSize, Storage.getDisplayTaskList()
					.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
