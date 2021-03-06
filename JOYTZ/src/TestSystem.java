//@author A0094558N
import static org.junit.Assert.*;

import org.junit.Test;


public class TestSystem {
    private static final String INPUT_ADD_ALL = "add meeting with friends; discuss about " +
                                                "CS2103T project from 24/02/2015 11:30am to " +
                                                "25/02/2015 11:45pm @NUS #medium";
    private static final String INPUT_ADD_ALL_HIGH_PRIORITY = "add meeting with friends; " +
                                                              "discuss about CS2103T project " +
                                                              "from 24/02/2015 11:30am to " +
                                                              "25/02/2015 11:45pm @NUS #important";
    
    private static final String INPUT_UPDATE_NAME = "update 1 name chat with friends";
    private static final String INPUT_UPDATE_PRIORITY = "update 1 priority low";
    private static final String INPUT_UPDATE_LOCATION = "update 1 location UTown";
    private static final String INPUT_UPDATE_DESCRIPTION = "update 1 description talk about movies";
    
    private static final String INPUT_SORT_PRIORITY = "sort priority";
    private static final String INPUT_SEARCH_PRIORITY_HIGH = "search priority important";
    private static final String INPUT_DELETE_1 = "delete 1";
    
    private static final String FEEDBACK_RESULT_MESSAGE_ADD = "\"meeting with friends\" is added successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_UPDATE = "Task 1 is updated successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_DISPLAY = "Tasks are displayed successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_DELETE = "A task is deleted successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_CLEAR = "All tasks are cleared successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_SORT = "A category is sorted successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_SEARCH = "Tasks are searched successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_UNDO = "Undo one step successfully.\n";
    private static final String FEEDBACK_RESULT_MESSAGE_REDO = "Redo one step successfully.\n";
    private static final String FEEDBACK_RESULT_STRING_1 = "meeting with friends~discuss about " +
                                                           "CS2103T project~24/02/2015 at 11:30 AM~" +
                                                           "25/02/2015 at 11:45 PM~NUS~medium\n";
    private static final String FEEDBACK_RESULT_STRING_2 = "chat with friends~discuss about " +
                                                           "CS2103T project~24/02/2015 at 11:30 AM~" + 
                                                           "25/02/2015 at 11:45 PM~NUS~medium\n";
    private static final String FEEDBACK_RESULT_STRING_3 = "chat with friends~discuss about " +
                                                           "CS2103T project~24/02/2015 at 11:30 AM~" +
                                                           "25/02/2015 at 11:45 PM~NUS~low\n";
    private static final String FEEDBACK_RESULT_STRING_4 = "chat with friends~discuss about " +
                                                           "CS2103T project~24/02/2015 at 11:30 AM~" +
                                                           "25/02/2015 at 11:45 PM~UTown~low\n";
    private static final String FEEDBACK_RESULT_STRING_5 = "chat with friends~talk about movies~"+
                                                           "24/02/2015 at 11:30 AM~25/02/2015 at 11:45 PM~" +
                                                           "UTown~low\n";
    private static final String FEEDBACK_RESULT_STRING_6 = "meeting with friends~discuss about " +
                                                           "CS2103T project~24/02/2015 at 11:30 AM~" +
                                                           "25/02/2015 at 11:45 PM~NUS~high\n";
    
    @Test
    public void systemTestAdd() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
    }
    
    @Test
    public void systemTestUpdate() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
        
        // Test updating the added task
        // Update name
        systemTestResult = Controller.startController(INPUT_UPDATE_NAME);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_STRING_2, 
                     systemTestResult.getTaskStringList().get(listSize - 2));
        System.out.println(systemTestResult.getTaskStringList().get(listSize - 2));
        
        // Update priority
        systemTestResult = Controller.startController(INPUT_UPDATE_PRIORITY);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_UPDATE, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_3, 
                     systemTestResult.getTaskStringList().get(listSize - 2));
        
        // Update location
        systemTestResult = Controller.startController(INPUT_UPDATE_LOCATION);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_UPDATE, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_4, 
                     systemTestResult.getTaskStringList().get(listSize - 2));
        
        // Update description
        systemTestResult = Controller.startController(INPUT_UPDATE_DESCRIPTION);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_UPDATE, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_5, 
                     systemTestResult.getTaskStringList().get(listSize - 2));
    }
    
    @Test
    public void systemTestClear() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
        
        // Test clear
        systemTestResult = Controller.startController(StringFormat.CLEAR);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_CLEAR, systemTestResult.getMessageShowToUser());
        assertEquals(true, systemTestResult.getTaskStringList().isEmpty());
    }
    
    @Test
    public void systemTestDisplay() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));

        // Test display
        systemTestResult = Controller.startController(StringFormat.DISPLAY);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_DISPLAY, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
    }
    
    @Test
    public void systemTestDelete() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));

        // Test delete
        systemTestResult = Controller.startController(INPUT_DELETE_1);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_DELETE, systemTestResult.getMessageShowToUser());
        assertEquals(true, systemTestResult.getTaskStringList().isEmpty());
    }
    
    @Test
    public void systemTestSort() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
        
        // Test add
        systemTestResult = Controller.startController(INPUT_ADD_ALL_HIGH_PRIORITY);
        listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_6, 
                     systemTestResult.getTaskStringList().get(listSize - 1));

        // Test sort
        systemTestResult = Controller.startController(INPUT_SORT_PRIORITY);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_SORT, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_6, 
                     systemTestResult.getTaskStringList().get(listSize - 2));
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
    }
    
    @Test
    public void systemTestSearch() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
        
        // Test add
        systemTestResult = Controller.startController(INPUT_ADD_ALL_HIGH_PRIORITY);
        listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_6, 
                     systemTestResult.getTaskStringList().get(listSize - 1));

        // Test search
        systemTestResult = Controller.startController(INPUT_SEARCH_PRIORITY_HIGH);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_SEARCH, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_6, systemTestResult.getTaskStringList().get(0));
    }
    
    @Test
    public void systemTestUndoRedo() {
        Controller.startController(StringFormat.CLEAR);
        
        // Test add
        Feedback systemTestResult = Controller.startController(INPUT_ADD_ALL);
        int listSize = systemTestResult.getTaskStringList().size();

        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_ADD, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));

        // Test undo
        systemTestResult = Controller.startController(StringFormat.UNDO);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_UNDO, systemTestResult.getMessageShowToUser());
        assertEquals(true, systemTestResult.getTaskStringList().isEmpty());
        
        // Test redo
        systemTestResult = Controller.startController(StringFormat.REDO);
        assertEquals(true, systemTestResult.getResult());
        assertEquals(FEEDBACK_RESULT_MESSAGE_REDO, systemTestResult.getMessageShowToUser());
        assertEquals(FEEDBACK_RESULT_STRING_1, 
                     systemTestResult.getTaskStringList().get(listSize - 1));
    }

}
