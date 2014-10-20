

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class TestController {
	
	private static final String INPUT_ONE = "add~meeting with friends~discuss about CS2103T project~" +
											"24/02/2015 11:30am~25/02/2015 11:45am~NUS~medium";

	@Test
	public void checkAnalyzerOutput() throws ParseException {
		Command test1 = new Command(INPUT_ONE);
		// Check if the command has been properly broken up and filled
		assertEquals("add command is not filled", "add", Controller.analyzeInput(test1).getAction());
		assertEquals("Name is not filled", "meeting with friends", Controller.analyzeInput(test1).getTaskName());	
		assertEquals("Description is not filled", "discuss about CS2103T project",
					 Controller.analyzeInput(test1).getTaskDescription());	
		//assertEquals("Deadline is not filled", "Tue Oct 14 00:00:00 SGT 2014", Controller.analyzeInput(test1).getTaskDeadline().toString());
		assertEquals("Location is not filled", "NUS", Controller.analyzeInput(test1).getTaskLocation());
		assertEquals("Priority is not filled", "medium", Controller.analyzeInput(test1).getTaskPriority());
	}
	
	public void checkFeedbackObject() {
		ExecutableCommand parsedCommand = new ExecutableCommand();
		parsedCommand.setAction("add");
		parsedCommand.setTaskName("meeting with friends");
		parsedCommand.setTaskDescription("discuss about CS2103T project");
		//parsedCommand.setTaskDeadline(new Date(2014/10/25));
		parsedCommand.setTaskLocation("NUS");
		parsedCommand.setTaskPriority("medium");
		
		// Check if the execution has been successful
		assertEquals("Executor unsuccessful", true, Controller.startExecutor(parsedCommand).getResult());
		// Check if the output feedback message is correct
		assertEquals("Output feedback message wrong", "\"meeting with friends\" is added successfully.\n", 
					 Controller.startExecutor(parsedCommand).getMessageShowToUser());
		// Check if the display string to be used in the table GUI display is correct
		assertEquals("Output string for table is wrong", "meeting with friends~discuss about CS2103T project~14/10/2014~NUS~medium", 
					 Controller.startExecutor(parsedCommand).getTaskList().get(0));
		}
	}
