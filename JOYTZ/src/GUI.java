//@author A0094558N 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.Timer;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;

//import com.melloware.jintellitype.HotkeyListener;
//import com.melloware.jintellitype.IntellitypeListener;
//import com.melloware.jintellitype.JIntellitype;

public class GUI { //implements HotkeyListener, IntellitypeListener {
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    private static final String HELP_TEXT_COMMANDS = "List of Commands (\"<\" and \">\" do not have to be typed): \n";
    private static final String HELP_TEXT_ADD = "\t    add <task name>; <description> from <start date> " +
    											"to <end time> @<location> #<priority>\n";
    private static final String HELP_TEXT_DELETE =  "\t    delete <index number>\n";
    private static final String HELP_TEXT_UPDATE = "\t    update <index number> <attribute> <new data>\n";
    private static final String HELP_TEXT_SEARCH = "\t    search <attribute> <search for>\n";
    private static final String HELP_TEXT_SORT = "\t    sort <attribute>\n";
    private static final String HELP_TEXT_UNDO = "\t    undo\n";
    private static final String HELP_TEXT_REDO = "\t    redo\n";
    private static final String HELP_TEXT_DISPLAY ="\t    display\n";
    private static final String HELP_TEXT_CLEAR = "\t    clear\n";
    private static final String HELP_TEXT_HELP ="\t    help\n";
    private static final String HELP_TEXT_TUTORIAL ="\t    tutorial\n";
    private static final String HELP_TEXT_SETTINGS ="\t    settings\n";
    private static final String HELP_TEXT_EXIT = "\t    exit\n";
    private static final String HELP_TEXT_TIME_GUIDE = "\t    Time entry: (dd/mm/yyyy hh:mmxx, xx = am or pm)\n";
	private static final String HELP_TEXT_ATTRIBUTES_GUIDE = "\t    Attributes: Refer to the headings on the table";
    private static final String HELP_TEXT_SHORTCUT_MAXIMIZE = "\t    ALT+A to maximize application";
    private static final String HELP_TEXT_SHORTCUT_MINIMIZE = "\t    ALT+Z to minimize application";
    private static final String HELP_TEXT_PREVIOUS_COMMAND = "\t    ALT+up to get your previous command";
    private static final String HELP_TEXT_NEXT_COMMAND = "\t    ALT+down to get your next command";
	private static final String NOTIFICATION_START = "%s has started!";
	private static final String NOTIFICATION_OVERDUE = "%s is overdue!";
	private static final String ERROR_NO_SETTINGS = "No settings set up. Using defaults.";
	private static final String LABEL_TASKS_INCOMPLETE = "Task List: Incomplete Tasks";
	private static final String LABEL_TASKS_DONE = "Task List: Done Tasks";
	private static final int DEFAULT_DEADLINE_COLOR_R = 255;
	private static final int DEFAULT_DEADLINE_COLOR_G = 0;
	private static final int DEFAULT_DEADLINE_COLOR_B = 0;
	private static final int DEFAULT_ONGOING_COLOR_R = 0;
	private static final int DEFAULT_ONGOING_COLOR_G = 128;
	private static final int DEFAULT_ONGOING_COLOR_B = 0;
	private static final int IN_MILLISECONDS_ONE_MINUTE = 60000;
	private static final int IN_MILLISECONDS_ONE_DAY = 86400000;
	private static final int NULL_NUMBER = -1;
	private static final String EMPTY_STRING = "";

	// Variables for settings
	private static int refreshRate;
	private static int deadlineRowColorR;
	private static int deadlineRowColorG;
	private static int deadlineRowColorB;
	private static int ongoingRowColorR;
	private static int ongoingRowColorG;
	private static int ongoingRowColorB;
	private static int isNotifcationsOverdueEnabled;
    private static int isNotifcationsOngoingEnabled;
	private static List<Integer> settingsStorage;
	
    private static StyledText inputField;
    private static Table taskTable;
    private static Table feedbackTable;
    private static TableColumn tblclmnNo;
    private static TableColumn tblclmnName;
    private static TableColumn tblclmnStart;
    private static TableColumn tblclmnEnd;
    private static TableColumn tblclmnFeedback;
    private static TableColumn tblclmnPriority;
    private static boolean isSortingOrSearching;
    private static Deque<String> previousUserInputStack;
    private static Deque<String> nextUserInputStack;
    private static Display display;
    private static Shell shell;
    private static Timer displayTimer;
    private static String beingDisplayed;
    private static GUI mainFrame;
    private static Label lblIncompleteTasks;
    private static Label lblInputBox;
    
    
    /**
     * Creates and returns a new TableItem containing
     * the given string
     * 
     * @param thisTable     The table the TableItem belongs to
     * @param text          The text to be placed in the TableItem
     *
     * @return              The table item
     * 
     */
    private static TableItem newTableItem(Table thisTable, String text){
        TableItem item = new TableItem(thisTable, SWT.NONE);
        item.setText(text);
        
        return item;
    }
    
    /**
     * Creates and returns a new TableItem
     * 
     * @param thisTable     The table the TableItem belongs to
     * @param textArr       An array of strings to be placed in the TableItem
     *
     * @return              The table item
     * 
     */
    private static TableItem newTableItem(Table thisTable, String[] textArr){
        TableItem item = new TableItem(thisTable, SWT.NONE);
        item.setText(textArr);
        
        return item;
    }

    /**
     * Returns the shell of the program
     *
     * @return    The shell of the program
     * 
     */
    public static Shell getShell(){
        return shell;
    }
    
    /**
     * Displays a feedback string in the GUI after each user command
     *
     * @param output    The string to be displayed
     * 
     */
    public static void displayOutput(String output, boolean isSuccessful) {
        TableItem item = newTableItem(feedbackTable, output);
        if (isSuccessful == false) {
            colorRowBackgroundRed(item);
        }
        
        // This ensures that the table is always scrolled to the bottom
        feedbackTable.setTopIndex(feedbackTable.getItemCount() - 1);
    }

    /**
     * Opens the tutorial
     * 
     */
    public static void openTutorial() {
        GUIExtraHelp helpDialog = new GUIExtraHelp(shell, SWT.NONE);
        helpDialog.open();
    }

    /**
     * Displays the help text in text box that is found in the middle,
     * styling it such that the background is colored.
     * 
     */
    private static void displayHelp() {
    	Color grey = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
    	TableItem item;
    	
    	item = newTableItem(feedbackTable, HELP_TEXT_COMMANDS);
    	item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_ADD);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_DELETE);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_UPDATE);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_SEARCH);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_SORT);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_UNDO);
        item.setBackground(grey);

        item = newTableItem(feedbackTable, HELP_TEXT_REDO);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_DISPLAY);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_CLEAR);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_HELP);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_TUTORIAL);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_SETTINGS);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_EXIT);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_TIME_GUIDE);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_ATTRIBUTES_GUIDE);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_SHORTCUT_MAXIMIZE);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_SHORTCUT_MINIMIZE);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_PREVIOUS_COMMAND);
        item.setBackground(grey);
        
        item = newTableItem(feedbackTable, HELP_TEXT_NEXT_COMMAND);
        item.setBackground(grey);

        feedbackTable.setTopIndex(feedbackTable.getItemCount() - 1);
    }

    /**
     * Updates the table in the GUI based on the given parameters
     *
     * @param taskNumber    	         Index number of the task in the table
     * @param startDate    		         Starting time of the given task
     * @param endDate    		         Deadline of the given task
     * @param name 				         Name of the given task
     * @param location			         Location of the given task
     * @param description		         Description for the given task
     * @param priority			         Priority level of the given task
     * @param action			         Action input by the user (add, delete, etc.)
     * @param taskId                     The taskId of the task given by the user
     * @param isLastRow			         Is this the last item 
     * @param isHighlightedPassStart     Has the task passed the start time
     * @param isHighlightedPassEnd       Has the task passed the end time
     * 
     */
    public static void updateTable(int taskNumber, String startDate, String endDate,
                                   String name, String location, 
                                   String description, String priority,
                                   String action, int taskId, boolean isLastRow,
                                   boolean isHighlightedPassStart,
                                   boolean isHighlightedPassEnd) {
        
        stopTimerIfSortingOrSearching(action);

        // To prevent multiple of the same entries, we clear the whole table first
        if (taskNumber == 0 || action.equals("null")) {
            taskTable.removeAll();
            assert taskTable.getItemCount() == 0;
        }

        if (!action.equals(StringFormat.CLEAR) && !startDate.equals(Controller.EMPTY_LIST)) {
            // Debugging code
            LOGGER.info("==============\n" +
                        "Writing to table (GUI):  \n" + 
                        "	Action = " + action + "\n" + 
                        "	Name = " + name + "\n" +
                        "	Start time = " + startDate + "\n" + 
                        "	End time = " + endDate + "\n" + 
                        "	Description = " + description + "\n" +
                        "	Location = " + location + "\n" +
                        "	Priority = " + priority + "\n" +
                        "====================\n");

            // 1 row = 1 TableItem
            TableItem item = newTableItem(taskTable, new String[] { (taskNumber+1) + ".", name, 
                                                                     startDate, endDate, priority});
            
            TableItem item2;
            if (location.equals(EMPTY_STRING)) {
                item2 = newTableItem(taskTable, new String[] {EMPTY_STRING, location });
            } else {
                item2 = newTableItem(taskTable, new String[] {EMPTY_STRING, "at " + location });
            }
            taskTable.setTopIndex(taskTable.getItemCount() - 1);
            
            displayGUIFeedback(name, action, taskId, isLastRow, isHighlightedPassStart,
                               isHighlightedPassEnd, item, item2, taskNumber, priority);
            
        }

        if (isLastRow == true) {
            resizeTable();
        }
    }

    /**
     * Stops the display timer if the user's action is a sort or search
     *
     * @param action       The action executed by the user
     * 
     */
    private static void stopTimerIfSortingOrSearching(String action) {
        if (action.equals(StringFormat.SORT) || action.equals(StringFormat.SEARCH)) {
        	isSortingOrSearching = true;
        	stopDisplayTimer();
        } else {
        	isSortingOrSearching = false;
        	startDisplayTimer();
        }
    }
    
    /**
     * Processes the rows to color, when to display notifications,
     * scrolling the table to the newly edited row, and
     * making tasks with high priority bold.
     * 
     * @param name                      The name of the task
     * @param action                    The action executed by the user
     * @param taskId                    The index of the task in the table
     * @param isLastRow                 Is this the last row?
     * @param isHighlightedPassStart    Has the start timing passed?
     * @param isHighlitedPassEnd        Has the end timing passed?
     * @param item                      The upper table row of the task
     * @param item2                     The lower table row of the task
     * @param taskNumber                The index number of the task in the table
     * @param priority                  The priority of the task
     * 
     */
    private static void displayGUIFeedback(String name, String action, int taskId,
                                           boolean isLastRow, boolean isHighlightedPassStart,
                                           boolean isHighlightedPassEnd, TableItem item, 
                                           TableItem item2, int taskNumber, String priority) {
        // Alternate task colors
        if (taskNumber % 2 == 1) {
            colorRowBackgroundYellow(item);
            colorRowBackgroundYellow(item2);
        } else {
            colorRowBackgroundLightGrey(item);
            colorRowBackgroundLightGrey(item2);
        }
        
        System.out.println(isHighlightedPassStart);
        System.out.println(isHighlightedPassEnd);
        
        // Coloring green
        if (isHighlightedPassStart == true) {
            System.out.println("Ongoing = " + isNotifcationsOngoingEnabled);
            System.out.println("Overdue = " + isNotifcationsOverdueEnabled);
            colorOngoingRow(item);
            colorOngoingRow(item2);
            if (action.equals(StringFormat.DISPLAY) && isNotifcationsOngoingEnabled == 1) {
                NotifierDialog.notify(String.format(NOTIFICATION_START, name), EMPTY_STRING);
            }
        }
        
        // Coloring red
        if (isHighlightedPassEnd == true) {
            colorDeadlineRow(item);
            colorDeadlineRow(item2);
            if (action.equals(StringFormat.DISPLAY) && isNotifcationsOverdueEnabled == 1) {
                NotifierDialog.notify(String.format(NOTIFICATION_OVERDUE, name), EMPTY_STRING);
            }
        }
        
        // Coloring newly edited row
        if (isLastRow == true && action.equals(StringFormat.ADD)) {
            colorRowBackgroundGreen(item);
            colorRowBackgroundGreen(item2);
        } else if (action.equals(StringFormat.UPDATE) && taskNumber+1 == taskId) {
            colorRowBackgroundGreen(item);
            colorRowBackgroundGreen(item2);
        } 
        
        // Scrolling of table to the newly edited row
        if (isLastRow == true && action.equals(StringFormat.UPDATE)) {
            if ((taskId - 1) <= taskTable.getItemCount()) {
                taskTable.showItem(taskTable.getItem(taskId-1));
            }
        }
        
        // Making tasks with high priority bold
        if (priority.trim().equals("high")) {
            item.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 11, SWT.BOLD));
            item2.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 9, SWT.BOLD));
        } else {
            item.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 11, SWT.NORMAL));
        }
    } 
    
    /**
     * Colors the row based on the colors used for tasks that
     * have passed the deadline (colors are based on user settings)
     * 
     * @param item      The table row to be colored
     * 
     */
    private static void colorDeadlineRow(TableItem item) {
        Color color;
        if (deadlineRowColorR == NULL_NUMBER) {
            color = SWTResourceManager.getColor(DEFAULT_DEADLINE_COLOR_R, 
                                                DEFAULT_DEADLINE_COLOR_G, 
                                                DEFAULT_DEADLINE_COLOR_B);
        } else {
            color = SWTResourceManager.getColor(deadlineRowColorR, 
                                                deadlineRowColorG, 
                                                deadlineRowColorB);
        }
        item.setForeground(color);
    }
    
    /**
     * Colors the background in a row red.
     * 
     * @param item      The table row to be colored
     * 
     */
    private static void colorRowBackgroundRed(TableItem item) {
        Color red = display.getSystemColor(SWT.COLOR_RED);
        item.setBackground(red);
    }
    
    /**
     * Colors the row based on the colors used for tasks that
     * are still ongoing  (colors are based on user settings)
     * 
     * @param item      The table row to be colored
     * 
     */
    private static void colorOngoingRow(TableItem item) {
        Color color;
        if (ongoingRowColorR == NULL_NUMBER) {
            color = SWTResourceManager.getColor(DEFAULT_ONGOING_COLOR_R, 
                                                DEFAULT_ONGOING_COLOR_G, 
                                                DEFAULT_ONGOING_COLOR_B);
        } else {
            color = SWTResourceManager.getColor(ongoingRowColorR, 
                                                ongoingRowColorG, 
                                                ongoingRowColorB);
        }
        item.setForeground(color);
    }
    
    /**
     * Colors the background of a row green
     * 
     * @param item      The table row to be colored
     * 
     */
    private static void colorRowBackgroundGreen(TableItem item) {
        Color color = SWTResourceManager.getColor(32,178,170);
        item.setBackground(color);
    }
    
    /**
     * Colors the background of a row light grey
     * 
     * @param item      The table row to be colored
     * 
     */
    private static void colorRowBackgroundLightGrey(TableItem item) {
        Color grey = display.getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);
        item.setBackground(grey);
    }
    
    /**
     * Colors the background of a row yellow
     * 
     * @param item      The table row to be colored
     * 
     */
    private static void colorRowBackgroundYellow(TableItem item) {
        Color color = SWTResourceManager.getColor(255,255,240);
        item.setBackground(color);
    }

    /**
     * Resizes the columns in the table based 
     * on the width of the application, as well as
     * the length of the content in the cells.
     * 
     */
    private static void resizeTable() {
        int scrollbarWidth = 0;
        int padding = 5;
        int tableWidth = taskTable.getSize().x;
        
        if (taskTable.getVerticalBar().isVisible()) {
            scrollbarWidth = taskTable.getVerticalBar().getSize().x;
        } else {
            scrollbarWidth = 0;
        }
        
        // Resize all the columns to fit the data
        tblclmnStart.pack();
        tblclmnEnd.pack();
        
        int widthLeft = tableWidth - scrollbarWidth - tblclmnNo.getWidth() - 
                        tblclmnStart.getWidth() - tblclmnEnd.getWidth() - 
                        tblclmnPriority.getWidth();

        tblclmnName.setWidth(widthLeft - padding);
    }
    
    /** 
     * Begin startup procedures. Things done:
     * 1. Initialize and start JIntellitype
     * 2. Initialize variables
     * 3. Load and apply settings
     * 4. Initialize the timer
     * 5. Display the help messages
     * 6. Load the contents of the database
     * 7. Display loaded tasks
     * 8. Place typing cursor into the input text field
     * 
     */
    private static void startupProgram() {
//        initJIntellitype();
        initializeVariables();
        getSettings();
        applySettings();
        initializeDisplayRefreshTimer(refreshRate);   // Timer delay in milliseconds
        
        displayHelp();
        Controller.startController(StringFormat.RELOAD);
        Controller.startController(StringFormat.DISPLAY);
        inputField.setFocus();
    }

    /** 
     * Initialize some variables used by the program
     * 
     */
    private static void initializeVariables() {
        isSortingOrSearching = false;
        settingsStorage = new ArrayList<Integer>();
        previousUserInputStack = new ArrayDeque<String>();
        nextUserInputStack = new ArrayDeque<String>();
        beingDisplayed = EMPTY_STRING;
        deadlineRowColorR = NULL_NUMBER;
        deadlineRowColorG = NULL_NUMBER;
        deadlineRowColorB = NULL_NUMBER;
        ongoingRowColorR = NULL_NUMBER;
        ongoingRowColorG = NULL_NUMBER;
        ongoingRowColorB = NULL_NUMBER;
        isNotifcationsOverdueEnabled = NULL_NUMBER;
        isNotifcationsOngoingEnabled = NULL_NUMBER;
    }
    
    /** 
     * Setup the listeners necessary for the program to work.
     * Listeners used:
     * 1. Key listener for keyboard commands
     * 2. Listener for when the application is closed without using "exit"
     * 3. Listener for when the feedback table is resized
     * 
     */
    private static void setupListeners() {
        
        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {                        // "enter" key
                    restorePreviousInputStack();
                    handleUserInput();
                }
                if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {  // Ctrl+A     
                    inputField.selectAll();
                }
                if (e.stateMask == SWT.ALT && e.keyCode == SWT.ARROW_UP) {    // Alt+Up arrow
                    getNextInput();
                }
                if (e.stateMask == SWT.ALT && e.keyCode == SWT.ARROW_DOWN) {  // Alt+Down arrow
                    getPreviousInput();
                }
            }

            private void restorePreviousInputStack() {
                if (beingDisplayed.equals(EMPTY_STRING) == false) {
                    previousUserInputStack.push(beingDisplayed);
                    beingDisplayed = EMPTY_STRING;
                }
                
                while (nextUserInputStack.isEmpty() == false) {
                    previousUserInputStack.push(nextUserInputStack.pop());
                }
            }

            private void getNextInput() {
                if (previousUserInputStack.isEmpty() == false) {
                    if (beingDisplayed.equals(EMPTY_STRING) == false) {
                        nextUserInputStack.push(beingDisplayed);
                    }
                    beingDisplayed = previousUserInputStack.pop();
                    inputField.setText(beingDisplayed);
                    inputField.setSelection(inputField.getCharCount());
                }
            }

            private void getPreviousInput() {
                if (nextUserInputStack.isEmpty() == false) {
                    previousUserInputStack.push(beingDisplayed);
                    beingDisplayed = nextUserInputStack.pop();
                    inputField.setText(beingDisplayed);
                    inputField.setSelection(inputField.getCharCount());
                }
            }

            private void handleUserInput() {
                String userInput = inputField.getText().trim();
                
                if (userInput.equals(StringFormat.HELP)) {
                    displayHelp();
                    inputField.setText(EMPTY_STRING);
                } else if (userInput.equals(StringFormat.TUTORIAL)) {    
                    GUIExtraHelp helpDialog = new GUIExtraHelp(shell, SWT.NONE);
                    inputField.setText(EMPTY_STRING);
                    helpDialog.open();
                } else if (userInput.equals(StringFormat.SETTINGS)) {    
                    GUISettings settingsDialog = new GUISettings(shell, SWT.NONE);
                    inputField.setText(EMPTY_STRING);
                    settingsDialog.open();
                    applySettings();
                    initializeDisplayRefreshTimer(refreshRate);
                    Controller.startController(StringFormat.DISPLAY);
                } else if (userInput.equals("display done")) {
                    previousUserInputStack.push(userInput);
                    lblIncompleteTasks.setText(LABEL_TASKS_DONE);
                    Controller.startController(userInput);

                    inputField.setText(EMPTY_STRING);
                } else {
                    userInput = userInput.replaceAll("[\n\r]", EMPTY_STRING);
                    previousUserInputStack.push(userInput);
                    lblIncompleteTasks.setText(LABEL_TASKS_INCOMPLETE);
                    Controller.startController(userInput);

                    inputField.setText(EMPTY_STRING);
                }
            }
        });
                
        // This catches the close event generated when the user closes 
        // the application in any way other than typing "exit"
        shell.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                Controller.startController(StringFormat.EXIT);
            }
        });
        
        // To scale the width of the columns in the tables with
        // respect to the window size
        feedbackTable.addListener(SWT.Resize, new Listener() {
            public void handleEvent(Event event) {
                tblclmnFeedback.setWidth(feedbackTable.getClientArea().width);
                resizeTable();
            }
        });
    }
    
    /** 
     * Gets the settings from the settings text file.
     * The settings are stored in an array list once loaded
     * 
     */
    public static void getSettings() {
        readFile(GUISettings.FILENAME);
    }
    
    /** 
     * Saves the settings into variables that are 
     * being used by the GUI
     * 
     */
    private static void applySettings() {
        if (settingsStorage.size() != 0) {
            LOGGER.info("==============\n" +
                        "Loading settings from file.\n" +
                        "====================\n");
            refreshRate = settingsStorage.get(GUISettings.SETTINGS_NOTIF_FREQ_INDEX);
            refreshRate *= IN_MILLISECONDS_ONE_MINUTE;
            deadlineRowColorR = settingsStorage.get(GUISettings.SETTINGS_DEADLINE_COLOR_R_INDEX);
            deadlineRowColorG = settingsStorage.get(GUISettings.SETTINGS_DEADLINE_COLOR_G_INDEX);
            deadlineRowColorB = settingsStorage.get(GUISettings.SETTINGS_DEADLINE_COLOR_B_INDEX);
            ongoingRowColorR = settingsStorage.get(GUISettings.SETTINGS_ONGOING_COLOR_R_INDEX);
            ongoingRowColorG = settingsStorage.get(GUISettings.SETTINGS_ONGOING_COLOR_G_INDEX);
            ongoingRowColorB = settingsStorage.get(GUISettings.SETTINGS_ONGOING_COLOR_B_INDEX);
            isNotifcationsOverdueEnabled = settingsStorage.get(GUISettings.SETTINGS_NOTIFICATIONS_OVERDUE_INDEX);
            isNotifcationsOngoingEnabled = settingsStorage.get(GUISettings.SETTINGS_NOTIFICATIONS_ONGOING_INDEX);
        } else {
            LOGGER.info("==============\n" +
                        "No settings found. Using default settings.\n" +
                        "====================\n");
            refreshRate = GUISettings.SETTINGS_DEFAULT_NOTIF_FREQ * IN_MILLISECONDS_ONE_MINUTE;
            deadlineRowColorR = GUISettings.SETTINGS_DEFAULT_DEADLINE_COLOR_R;
            deadlineRowColorG = GUISettings.SETTINGS_DEFAULT_DEADLINE_COLOR_G;
            deadlineRowColorB = GUISettings.SETTINGS_DEFAULT_DEADLINE_COLOR_B;
            
            ongoingRowColorR = GUISettings.SETTINGS_DEFAULT_ONGOING_COLOR_R;
            ongoingRowColorG = GUISettings.SETTINGS_DEFAULT_ONGOING_COLOR_G;
            ongoingRowColorB = GUISettings.SETTINGS_DEFAULT_ONGOING_COLOR_B;
            
            isNotifcationsOverdueEnabled = GUISettings.SETTINGS_DEFAULT_NOTIF_OVERDUE;
            isNotifcationsOngoingEnabled = GUISettings.SETTINGS_DEFAULT_NOTIF_ONGOING;
        }
    }
    
    /** 
     * Read the contents of a file and stores it in an array list
     * 
     * @param filename     The name of the file
     * 
     */
    private static void readFile (String filename) {
        try {
            String temp;
            File settingsFile = new File(filename);
            BufferedReader in = new BufferedReader(new FileReader(settingsFile));
            // Read the file line by line and add each line
            // into an index of settingsStorage
            if (settingsStorage.isEmpty()) {
                while ((temp = in.readLine()) != null) {
                    int value = Integer.parseInt(temp);
                    settingsStorage.add(value);
                }
            } else {
                int i = 0;
                while ((temp = in.readLine()) != null) {
                    int value = Integer.parseInt(temp);
                    settingsStorage.set(i, value);
                    i++;
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.print(GUI.ERROR_NO_SETTINGS);
        }
    }
    
    /** 
     * Initializes the timer used for the periodic display function
     * 
     * @param delay     The delay used for the timer
     * 
     */
    private static void initializeDisplayRefreshTimer(int delay) {
        assert delay >= IN_MILLISECONDS_ONE_MINUTE; 
        assert delay <= IN_MILLISECONDS_ONE_DAY;
        displayTimer = new Timer(delay, null);
        displayTimer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Ensure that the following code runs in the 
                // same thread as the application itself
                Display.getDefault().syncExec(new Runnable() {
                    public void run() {
                        Controller.startController(StringFormat.DISPLAY);
                    }
                });
            }
        });
        // The timer will continuously repeat with the given delay
        displayTimer.setRepeats(true);
    }
    
    /** 
     * Starts the timer used for the periodic display function
     * 
     */
    private static void startDisplayTimer() {
        displayTimer.start();
    }
    
    /** 
     * Stops the timer used for the periodic display function
     * 
     */
    private static void stopDisplayTimer() {
        displayTimer.stop();
    }

    public static void main(String[] args) {
        createUI();
        
        startupProgram();
        setupListeners();
      
        openUI();

        readingAndDispatching();    
        cleanupUI();
    }

    /** 
     * Frees the resources used for the GUI
     * 
     */
    private static void cleanupUI() {
        display.dispose();
    }

    /** 
     * Opens the window of the application. The application design
     * and setup must be done before this.
     * 
     */
    private static void openUI() {
        shell.open();
        shell.layout();
    }

    /** 
     * Main loop of the GUI. The GUI will be constantly listening
     * for events and actions performed on the GUI here. 
     * 
     */
    private static void readingAndDispatching() {
        while(!shell.isDisposed()) {

            if (displayTimer.isRunning() == false && isSortingOrSearching == false) {
                startDisplayTimer();
            }

            display.readAndDispatch();
        }
    }
    
    //@author generated
    /** 
     * Configuration of the GUI. This includes the layout, 
     * the sizes, the color, the fonts, etc.
     * 
     */
    private static void createUI() {
        
        display = Display.getDefault();
        shell = new Shell();
        shell.setMinimumSize(new Point(400, 500));
        shell.setToolTipText("To-do list app of the year");
        shell.setSize(800, 550);
        shell.setLayout(new GridLayout(1, false));
        shell.setText("JOYTZ");
        
        lblIncompleteTasks = new Label(shell, SWT.NONE);
        lblIncompleteTasks.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 9, SWT.NORMAL));
        lblIncompleteTasks.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        lblIncompleteTasks.setAlignment(SWT.CENTER);
        lblIncompleteTasks.setText("Task List: Incomplete Tasks");

        taskTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
        taskTable.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 9, SWT.NORMAL));
        taskTable.setHeaderVisible(true);
        taskTable.setSize(new Point(400, 400));
        taskTable.setToolTipText("View your tasks here");
        GridData gd_taskTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_taskTable.heightHint = 248;
        taskTable.setLayoutData(gd_taskTable);

        tblclmnNo = new TableColumn(taskTable, SWT.CENTER);
        tblclmnNo.setMoveable(true);
        tblclmnNo.setToolTipText("Index number");
        tblclmnNo.setWidth(42);
        tblclmnNo.setText("No.");

        tblclmnName = new TableColumn(taskTable, SWT.CENTER);
        tblclmnName.setMoveable(true);
        tblclmnName.setWidth(100);
        tblclmnName.setText("Name");

        tblclmnStart = new TableColumn(taskTable, SWT.CENTER);
        tblclmnStart.setMoveable(true);
        tblclmnStart.setWidth(180);
        tblclmnStart.setText("Start");

        tblclmnEnd = new TableColumn(taskTable, SWT.CENTER);
        tblclmnEnd.setMoveable(true);
        tblclmnEnd.setWidth(180);
        tblclmnEnd.setText("End");
        
        tblclmnPriority = new TableColumn(taskTable, SWT.CENTER);
        tblclmnPriority.setMoveable(true);
        tblclmnPriority.setWidth(90);
        tblclmnPriority.setText("Priority");
        
        feedbackTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        feedbackTable.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 11, SWT.NORMAL));
        GridData gd_feedbackTable = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        gd_feedbackTable.heightHint = 121;
        feedbackTable.setLayoutData(gd_feedbackTable);
        
        tblclmnFeedback = new TableColumn(feedbackTable, SWT.NONE);
        tblclmnFeedback.setWidth(100);
        tblclmnFeedback.setText("Feedback");
        
        Label horizontalSeparator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        GridData gd_horizontalSeparator = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_horizontalSeparator.widthHint = 550;
        horizontalSeparator.setLayoutData(gd_horizontalSeparator);
        
        lblInputBox = new Label(shell, SWT.NONE);
        lblInputBox.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 9, SWT.NORMAL));
        lblInputBox.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        lblInputBox.setAlignment(SWT.CENTER);
        lblInputBox.setText("Input Box");

        inputField = new StyledText(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
        inputField.setFont(SWTResourceManager.getFont("HelveticaNeueLT Pro 55 Roman", 14, SWT.NORMAL));
        inputField.setToolTipText("Enter your commands here");
        inputField.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
        GridData gd_inputField = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        gd_inputField.heightHint = 85;
        inputField.setLayoutData(gd_inputField);
    }
    
//    //@author A0094558N-reused
//    /*
//     * (non-Javadoc)
//     * @see com.melloware.jintellitype.HotkeyListener#onHotKey(int)
//     */
//    public void onHotKey(int aIdentifier) {
//        if (aIdentifier == 1) {
//           System.out.println("WINDOWS+A hotkey pressed");
//           
//           // Ensure that the following code runs in the 
//           // same thread as the application itself
//           Display.getDefault().syncExec(new Runnable() {
//               public void run() {
//                   if (shell.getMaximized() == true) { 
//                       shell.forceActive();     // Bring application to front
//                       shell.setMaximized(true);
//                   } else if (shell.getMaximized() == false) { 
//                       shell.forceActive();     // Bring application to front
//                       shell.setMinimized(false);
//                   }
//               }
//           });
//        }
//        if (aIdentifier == 2) {
//            System.out.println("WINDOWS+Z hotkey pressed");
//            
//            // Ensure that the following code runs in the 
//            // same thread as the application itself
//            Display.getDefault().syncExec(new Runnable() {
//                public void run() {
//                    shell.setMinimized(true);
//                }
//            });
//         }
//     }
//    
//    /**
//     * Initialize the JInitellitype library making sure the DLL is located.
//     */
//    public static void initJIntellitype() {
//        mainFrame = new GUI();
//        
//        try {
//           // initialize JIntellitype with the frame so all windows commands can
//           // be attached to this window
//           JIntellitype.getInstance().addHotKeyListener(mainFrame);
//           JIntellitype.getInstance().addIntellitypeListener(mainFrame);
//           JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, (int)'A');    // WIN+A
//           JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_ALT, (int)'Z');    // WIN+Z
//           System.out.println("JIntellitype initialized");
//        } catch (RuntimeException ex) {
//           System.out.println("Either you are not on Windows, or there is a problem with the JIntellitype library!");
//        }
//     }
//    
//    /*
//     * (non-Javadoc)
//     * @see com.melloware.jintellitype.IntellitypeListener#onIntellitype(int)
//     */
//    public void onIntellitype(int aCommand) {
//
//       switch (aCommand) {
//       case JIntellitype.APPCOMMAND_BROWSER_BACKWARD:
//           System.out.println("BROWSER_BACKWARD message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_BROWSER_FAVOURITES:
//           System.out.println("BROWSER_FAVOURITES message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_BROWSER_FORWARD:
//           System.out.println("BROWSER_FORWARD message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_BROWSER_HOME:
//           System.out.println("BROWSER_HOME message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_BROWSER_REFRESH:
//           System.out.println("BROWSER_REFRESH message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_BROWSER_SEARCH:
//           System.out.println("BROWSER_SEARCH message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_BROWSER_STOP:
//           System.out.println("BROWSER_STOP message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_LAUNCH_APP1:
//           System.out.println("LAUNCH_APP1 message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_LAUNCH_APP2:
//           System.out.println("LAUNCH_APP2 message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_LAUNCH_MAIL:
//           System.out.println("LAUNCH_MAIL message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_MEDIA_NEXTTRACK:
//           System.out.println("MEDIA_NEXTTRACK message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_MEDIA_PLAY_PAUSE:
//           System.out.println("MEDIA_PLAY_PAUSE message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_MEDIA_PREVIOUSTRACK:
//           System.out.println("MEDIA_PREVIOUSTRACK message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_MEDIA_STOP:
//           System.out.println("MEDIA_STOP message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_VOLUME_DOWN:
//           System.out.println("VOLUME_DOWN message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_VOLUME_UP:
//           System.out.println("VOLUME_UP message received " + Integer.toString(aCommand));
//          break;
//       case JIntellitype.APPCOMMAND_VOLUME_MUTE:
//           System.out.println("VOLUME_MUTE message received " + Integer.toString(aCommand));
//          break;
//       default:
//           System.out.println("Undefined INTELLITYPE message caught " + Integer.toString(aCommand));
//          break;
//       }
//    }
}