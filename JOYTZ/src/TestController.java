

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class TestController {
	
	private static final String INPUT_ALL_FIELDS = "add~meeting with friends~discuss about CS2103T project~" +
												   "24/02/2015 11:30am~25/02/2015 11:45am~NUS~medium";
	private static final String INPUT_NO_PRIORITY = "add~meeting with friends~discuss about CS2103T project~" +
													"24/02/2015 11:30am~25/02/2015 11:45am~NUS";
	private static final String INPUT_NO_LOCATION = "add~meeting with friends~discuss about CS2103T project~" +
													"24/02/2015 11:30am~25/02/2015 11:45am~~medium";
	private static final String INPUT_NO_LOCATION_AND_PRIORITY = "add~meeting with friends~discuss about CS2103T project~" +
																 "24/02/2015 11:30am~25/02/2015 11:45am~~";
	private static final String INPUT_ONLY_NAME = "add~meeting with friends~~~~~";

	@Test
	public void checkAnalyzerAdd1() throws ParseException {
		Command test = new Command(INPUT_ALL_FIELDS);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test).getTaskName());	
		assertEquals("Description is not filled", "discuss about CS2103T project",
					 Controller.analyzeInput(test).getTaskDescription());	
		//assertEquals("Start time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskStartTiming());
		//assertEquals("End time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskEndTiming());
		assertEquals("Location is not filled", "NUS", Controller.analyzeInput(test).getTaskLocation());
		assertEquals("Priority is not filled", "medium", Controller.analyzeInput(test).getTaskPriority());
	}
	
	@Test
	public void checkAnalyzerAdd() throws ParseException {	
		Command test = new Command(INPUT_NO_PRIORITY);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test).getTaskName());	
		assertEquals("Description is not filled", "discuss about CS2103T project",
					 Controller.analyzeInput(test).getTaskDescription());	
		//assertEquals("Start time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskStartTiming());
		//assertEquals("End time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskEndTiming());
		assertEquals("Location is not filled", "NUS", Controller.analyzeInput(test).getTaskLocation());
		assertEquals("Priority is not filled", "", Controller.analyzeInput(test).getTaskPriority());
	}
	
	@Test
	public void checkAnalyzerAdd3() throws ParseException {	
		Command test = new Command(INPUT_NO_LOCATION);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test).getTaskName());	
		assertEquals("Description is not filled", "discuss about CS2103T project",
					 Controller.analyzeInput(test).getTaskDescription());	
		//assertEquals("Start time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskStartTiming());
		//assertEquals("End time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskEndTiming());
		assertEquals("Location is not filled", "", Controller.analyzeInput(test).getTaskLocation());
		assertEquals("Priority is not filled", "medium", Controller.analyzeInput(test).getTaskPriority());
	}
	
	@Test
	public void checkAnalyzerAdd4() throws ParseException {	
		Command test = new Command(INPUT_NO_LOCATION_AND_PRIORITY);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test).getTaskName());	
		assertEquals("Description is not filled", "discuss about CS2103T project",
					 Controller.analyzeInput(test).getTaskDescription());	
		//assertEquals("Start time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskStartTiming());
		//assertEquals("End time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskEndTiming());
		assertEquals("Location is not filled", "", Controller.analyzeInput(test).getTaskLocation());
		assertEquals("Priority is not filled", "", Controller.analyzeInput(test).getTaskPriority());
	}
	
	@Test
	public void checkAnalyzerAdd5() throws ParseException {	
		Command test = new Command(INPUT_ONLY_NAME);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test).getTaskName());	
		assertEquals("Description is not filled", "",
					 Controller.analyzeInput(test).getTaskDescription());	
		//assertEquals("Start time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskStartTiming());
		//assertEquals("End time is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test).getTaskEndTiming());
		assertEquals("Location is not filled", "", Controller.analyzeInput(test).getTaskLocation());
		assertEquals("Priority is not filled", "", Controller.analyzeInput(test).getTaskPriority());
	}
	
	@Test
	public void checkFeedbackObject() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("add");
		parsedCommand.setTaskName("meeting with friends");
		parsedCommand.setTaskDescription("discuss about CS2103T project");
		//parsedCommand.setTaskStartTiming(2014/10/25);
		//parsedCommand.setTaskEndTiming(2014/10/25);
		parsedCommand.setTaskLocation("NUS");
		parsedCommand.setTaskPriority("medium");
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", true, Controller.startExecutor(parsedCommand).getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "meeting with friends is added successfully.\n", 
					 Controller.startExecutor(parsedCommand).getMessageShowToUser());
		// Check if the display string to be used in the table GUI display is correct
		assertEquals("Output string for table is wrong", "meeting with friends~discuss about CS2103T project~ ~ ~NUS~medium", 
					 Controller.startExecutor(parsedCommand).getTaskList().get(0));
		}
	}
