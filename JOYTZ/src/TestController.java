

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
	private static final String INPUT_INVALID = "sdfsf";

	/*
	 * Case with all data filled (valid input partition)
	 */
	@Test
	public void checkOutputData1() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("add");
		parsedCommand.setTaskName("meeting with friends");
		parsedCommand.setTaskDescription("discuss about CS2103T project");
		parsedCommand.setTaskStartTiming("1424831340000");	// 24/02/2015 11:30am
		parsedCommand.setTaskEndTiming("1424918640000");	// 25/02/2015 11:45am
		parsedCommand.setTaskLocation("NUS");
		parsedCommand.setTaskPriority("medium");
		
		Feedback result = Controller.startExecutor(parsedCommand);
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", true, result.getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "meeting with friends is added successfully.\n", 
					 result.getMessageShowToUser());
		// Check if the display string to be used in the table GUI display is correct
		assertEquals("Output string for table is wrong", "meeting with friends~discuss about CS2103T project~25-02-2015~26-02-2015~NUS~medium", 
					 result.getTaskList().get(0));
	}
	
	/*
	 * Case with no priority (valid input partition)
	 */
	@Test
	public void checkOutputData2() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("add");
		parsedCommand.setTaskName("meeting with friends");
		parsedCommand.setTaskDescription("discuss about CS2103T project");
		parsedCommand.setTaskStartTiming("1424831340000");	// 24/02/2015 11:30am
		parsedCommand.setTaskEndTiming("1424918640000");	// 25/02/2015 11:45am
		parsedCommand.setTaskLocation("NUS");
		
		Feedback result = Controller.startExecutor(parsedCommand);
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", true, result.getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "meeting with friends is added successfully.\n", 
					 result.getMessageShowToUser());
		// Check if the display string to be used in the table GUI display is correct
		assertEquals("Output string for table is wrong", "meeting with friends~discuss about CS2103T project~25-02-2015~26-02-2015~NUS~ ", 
					 result.getTaskList().get(1));
	}

	/*
	 * Case with no priority and no location (valid input partition)
	 */
	@Test
	public void checkOutputData3() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("add");
		parsedCommand.setTaskName("meeting with friends");
		parsedCommand.setTaskDescription("discuss about CS2103T project");
		parsedCommand.setTaskStartTiming("1424831340000");	// 24/02/2015 11:30am
		parsedCommand.setTaskEndTiming("1424918640000");	// 25/02/2015 11:45am
		
		Feedback result = Controller.startExecutor(parsedCommand);
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", true, result.getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "meeting with friends is added successfully.\n", 
					 result.getMessageShowToUser());
		// Check if the display string to be used in the table GUI display is correct
		assertEquals("Output string for table is wrong", "meeting with friends~discuss about CS2103T project~25-02-2015~26-02-2015~ ~ ", 
					 result.getTaskList().get(2));
	}
	
	/*
	 * Case with only the name (valid input partition)
	 */
	@Test
	public void checkOutputData4() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("add");
		parsedCommand.setTaskName("meeting with friends");
		
		Feedback result = Controller.startExecutor(parsedCommand);
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", true, result.getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "meeting with friends is added successfully.\n", 
					 result.getMessageShowToUser());
		// Check if the display string to be used in the table GUI display is correct
		assertEquals("Output string for table is wrong", "meeting with friends~ ~ ~ ~ ~ ", 
					 result.getTaskList().get(3));
	}
	
	/*
	 * Case with only the name and priority (valid input partition)
	 * (boundary case with the first and last argument in the string)
	 */
	@Test
	public void checkOutputData5() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("add");
		parsedCommand.setTaskName("meeting with friends");
		parsedCommand.setTaskPriority("medium");
		
		Feedback result = Controller.startExecutor(parsedCommand);
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", true, result.getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "meeting with friends is added successfully.\n", 
					 result.getMessageShowToUser());
		// Check if the display string to be used in the table GUI display is correct
		assertEquals("Output string for table is wrong", "meeting with friends~ ~ ~ ~ ~medium", 
					 result.getTaskList().get(4));
	}
	
	/*
	 * Case with invalid action (invalid input partition)
	 */
	@Test
	public void checkOutputData6() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("ghfh");
		
		Feedback result = Controller.startExecutor(parsedCommand);
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", false, result.getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "Invalid command action: ghfh.\n", 
					 result.getMessageShowToUser());
	}
	
	/*
	 * The tests below test the analyzer through the controller, 
	 * so it does not test the controller itself. Since this
	 * has already been done, I am leaving it here until it breaks.
	 * 
	 * Even though the above is also testing the executor through
	 * the controller, I did not say the same about them as the output
	 * the executor returns to me is vital to the displaying of 
	 * tasks in the GUI, which I am also in charge of.
	 */
	@Test
	public void checkAnalyzerAdd1() throws ParseException {
		Command test = new Command(INPUT_ALL_FIELDS);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test).getTaskName());	
		assertEquals("Description is not filled", "discuss about CS2103T project",
					 Controller.analyzeInput(test).getTaskDescription());	
		assertEquals("Start time is not filled", "1424831340000", Controller.analyzeInput(test).getTaskStartTiming());
		assertEquals("End time is not filled", "1424918640000", Controller.analyzeInput(test).getTaskEndTiming());
		assertEquals("Location is not filled", "NUS", Controller.analyzeInput(test).getTaskLocation());
		assertEquals("Priority is not filled", "medium", Controller.analyzeInput(test).getTaskPriority());
	}
	
	@Test
	public void checkAnalyzerAdd2() throws ParseException {	
		Command test = new Command(INPUT_NO_PRIORITY);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test).getTaskName());	
		assertEquals("Description is not filled", "discuss about CS2103T project",
					 Controller.analyzeInput(test).getTaskDescription());	
		assertEquals("Start time is not filled", "1424831340000", Controller.analyzeInput(test).getTaskStartTiming());
		assertEquals("End time is not filled", "1424918640000", Controller.analyzeInput(test).getTaskEndTiming());
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
		assertEquals("Start time is not filled", "1424831340000", Controller.analyzeInput(test).getTaskStartTiming());
		assertEquals("End time is not filled", "1424918640000", Controller.analyzeInput(test).getTaskEndTiming());
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
		assertEquals("Start time is not filled", "1424831340000", Controller.analyzeInput(test).getTaskStartTiming());
		assertEquals("End time is not filled", "1424918640000", Controller.analyzeInput(test).getTaskEndTiming());
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
		assertEquals("Start time is not filled", "", Controller.analyzeInput(test).getTaskStartTiming());
		assertEquals("End time is not filled", "", Controller.analyzeInput(test).getTaskEndTiming());
		assertEquals("Location is not filled", "", Controller.analyzeInput(test).getTaskLocation());
		assertEquals("Priority is not filled", "", Controller.analyzeInput(test).getTaskPriority());
	}
	
	@Test
	public void checkAnalyzerAdd6() throws ParseException {
		Command test = new Command(INPUT_INVALID);
		// Check if the command has been properly broken up and filled
		assertEquals("Error message missing", "Invalid command.\n", Controller.analyzeInput(test).getErrorMessage());
	}
}
