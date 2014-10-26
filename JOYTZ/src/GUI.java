//package V1;
//@author A0094558N 
//import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

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

public class GUI { // implements HotkeyListener, IntellitypeListener {
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    private static final String HELP_TEXT_COMMANDS = "Commands: \n";
    private static final String HELP_TEXT_ADD = "add~task name~description~start time " +
    											"~end time~location~priority\n";
    private static final String HELP_TEXT_DELETE =  "delete~index number\n";
    private static final String HELP_TEXT_UPDATE = "update~index number~attribute~new data\n";
    private static final String HELP_TEXT_SORT = "sort~attribute\n";
    private static final String HELP_TEXT_UNDO = "undo\n";
    private static final String HELP_TEXT_DISPLAY ="display\n";
    private static final String HELP_TEXT_CLEAR = "clear\n";
    private static final String HELP_TEXT_HELP ="help\n";
    private static final String HELP_TEXT_EXIT = "exit\n";
    private static final String HELP_TEXT_TIME_GUIDE = "Time entry: (dd/mm/yyyy hh:mmxx, xx = am or pm)\n";
	private static final String HELP_TEXT_ATTRIBUTES_GUIDE = "Attributes: Refer to the headings on the table";
	private static final String NOTIFICATION_OVERDUE = "%s is overdue!";
	private static final int REFRESH_RATE = 600000;    // in milliseconds

    private static StyledText inputField;
    private static Table taskTable;
    private static Table feedbackTable;
    private static TableColumn tblclmnNo;
    private static TableColumn tblclmnName;
    private static TableColumn tblclmnStartedOn;
    private static TableColumn tblclmnLocation;
    private static TableColumn tblclmnPriority;
    private static TableColumn tblclmnDescription;
    private static TableColumn tblclmnDeadline;
    private static TableColumn tblclmnFeedback;
    private static boolean isSortingOrSearching;
    private static Display display;
    private static Shell shell;
    private static Timer displayTimer;
    
    private static GUI mainFrame;

    /**
     * Displays a feedback string in the GUI after each user command
     *
     * @param output	The string to be displayed
     * 
     */
    public static void displayOutput(String output) {
        TableItem item = new TableItem(feedbackTable, SWT.NONE);
        item.setText(output);
        
        // This ensures that the table is always scrolled to the bottom
        feedbackTable.setTopIndex(feedbackTable.getItemCount() - 1);
    }

    /**
     * Displays the help text in text box that is found in the middle,
     * styling it such that the background is colored. A table is used
     * to display the output to make the coloring of each individual 
     * sentence easier.
     * 
     */
    private static void displayHelp() {
    	Color grey = display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
    	
    	TableItem itemCommands = new TableItem(feedbackTable, SWT.NONE);
    	itemCommands.setText(new String[] { HELP_TEXT_COMMANDS });
    	itemCommands.setBackground(grey);
        
        TableItem itemAdd = new TableItem(feedbackTable, SWT.NONE);
        itemAdd.setText(new String[] { HELP_TEXT_ADD });
        itemAdd.setBackground(grey);
        
        TableItem itemDelete = new TableItem(feedbackTable, SWT.NONE);
        itemDelete.setText(new String[] { HELP_TEXT_DELETE });
        itemDelete.setBackground(grey);
        
        TableItem itemUpdate = new TableItem(feedbackTable, SWT.NONE);
        itemUpdate.setText(new String[] { HELP_TEXT_UPDATE });
        itemUpdate.setBackground(grey);
        
        TableItem itemSort = new TableItem(feedbackTable, SWT.NONE);
        itemSort.setText(new String[] { HELP_TEXT_SORT });
        itemSort.setBackground(grey);
        
        TableItem itemUndo = new TableItem(feedbackTable, SWT.NONE);
        itemUndo.setText(new String[] { HELP_TEXT_UNDO });
        itemUndo.setBackground(grey);
        
        TableItem itemDisplay = new TableItem(feedbackTable, SWT.NONE);
        itemDisplay.setText(new String[] { HELP_TEXT_DISPLAY });
        itemDisplay.setBackground(grey);
        
        TableItem itemHelp = new TableItem(feedbackTable, SWT.NONE);
        itemHelp.setText(new String[] { HELP_TEXT_HELP });
        itemHelp.setBackground(grey);
        
        TableItem itemClear = new TableItem(feedbackTable, SWT.NONE);
        itemClear.setText(new String[] { HELP_TEXT_CLEAR });
        itemClear.setBackground(grey);
        
        TableItem itemExit = new TableItem(feedbackTable, SWT.NONE);
        itemExit.setText(new String[] { HELP_TEXT_EXIT });
        itemExit.setBackground(grey);
        
        TableItem itemTimeGuide = new TableItem(feedbackTable, SWT.NONE);
        itemTimeGuide.setText(new String[] { HELP_TEXT_TIME_GUIDE });
        itemTimeGuide.setBackground(grey);
        
        TableItem itemAttributesGuide = new TableItem(feedbackTable, SWT.NONE);
        itemAttributesGuide.setText(new String[] { HELP_TEXT_ATTRIBUTES_GUIDE });
        itemAttributesGuide.setBackground(grey);
    }

    /**
     * Updates the table in the GUI based on the given parameters
     *
     * @param taskNumber    	Index number of the task in the table
     * @param startDate    		Starting time of the given task
     * @param endDate    		Deadline of the given task
     * @param name 				Name of the given task
     * @param location			Location of the given task
     * @param description		Description for the given task
     * @param priority			Priority level of the given task
     * @param action			Action input by the user (add, delete, etc.)
     * @param isLastRow			Is this the last item 
     * 
     */
    public static void updateTable(int taskNumber, String startDate, String endDate,
                                   String name, String location, 
                                   String description, String priority,
                                   String action, boolean isLastRow,
                                   boolean isHighlighted) {

        action = action.trim();
        
        if (action.equals("sort") || action.equals("search")) {
        	isSortingOrSearching = true;
        	stopDisplayTimer();
        } else {
        	isSortingOrSearching = false;
        	startDisplayTimer();
        }

        // To prevent multiple of the same entries, we clear the whole table first
        if (taskNumber == 0 || action.equals("null")) {
            taskTable.removeAll();
            assert taskTable.getItemCount() == 0;
        }

        if (!action.equals("clear") && !startDate.equals(Controller.EMPTY_LIST)) {
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
            TableItem item = new TableItem(taskTable, SWT.NONE);
            item.setText(new String[] { (taskNumber+1) + ".", startDate, endDate, name, location, 
                                        description, priority });
            if (isHighlighted == true) {
                colorRowRed(item);
                NotifierDialog.notify(String.format(NOTIFICATION_OVERDUE, name), "");
            }
        }

        if (isLastRow == true) {
            resizeTable();
        }
    } 
    
    /**
     * Colors the words in a row red.
     * 
     * @param item      The table row to be colored
     * 
     */
    private static void colorRowRed(TableItem item) {
        Color red = display.getSystemColor(SWT.COLOR_RED);
        item.setForeground(red);
    }

    /**
     * Resizes the columns in the table based 
     * on the width of the application.
     * 
     */
    private static void resizeTable() {
        int tableWidth = taskTable.getSize().x;
        int scrollbarWidth = taskTable.getVerticalBar().getSize().x;

        int widthLeft = tableWidth - tblclmnNo.getWidth() - 
                        tblclmnStartedOn.getWidth() - tblclmnDeadline.getWidth() - 
                        tblclmnPriority.getWidth() - scrollbarWidth;
        int widthPerColumn = widthLeft / 3;

        // Resize all the columns to fit the data
        // Note: Packing is extremely slow
        tblclmnNo.pack();
        tblclmnStartedOn.pack();
        tblclmnDeadline.pack();
        tblclmnName.pack();
        tblclmnPriority.pack();
        tblclmnDescription.pack();
        tblclmnLocation.pack();

        // Prevent it from being too big.
        // The other columns do not undergo this as they have a 
        // fixed and predictable length.
        if (tblclmnName.getWidth() >= widthPerColumn) {
            tblclmnName.setWidth(widthPerColumn);
        }
        if (tblclmnDescription.getWidth() >= widthPerColumn) {
            tblclmnDescription.setWidth(widthPerColumn);
        }
        if (tblclmnLocation.getWidth() >= widthPerColumn) {
            tblclmnLocation.setWidth(widthPerColumn);
        }  
    }
    
    /** 
     * Begin startup procedures. Things done:
     * 1. Initialize and start JIntellitype
     * 2. Initialize booleans
     * 3. Initialize the timer
     * 4. Load the contents of the database
     * 5. Display the help messages
     * 6. Adjust the size of the table columns
     * 
     */
    private static void startupProgram() {
        // initJIntellitype();
        isSortingOrSearching = false;
        initializeDisplayRefreshTimer(REFRESH_RATE);   // Timer delay in milliseconds
        
        Controller.startController("reload");
        displayHelp();
        resizeTable();
    }
    
    /** 
     * Setup the listeners necessary for the program to work
     * 1. Key listener for keyboard commands
     * 2. Listener for when the application is closed without using "exit"
     * 3. Listener for when the feedback table is resized
     * 
     */
    private static void setupListeners() {
        // We call the controller to process 
        // the user's keyboard commands
        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {    // "enter" key
                    if (inputField.getText().trim().equals("help")) {
                        displayHelp();
                        inputField.setText("");
                    } else {
                        Controller.startController(inputField.getText());

                        inputField.setText("");
                    }
                }
                if(e.stateMask == SWT.CTRL && e.keyCode == 'a') {   // Ctrl+A     
                    inputField.selectAll();
                }
            }
        });

        // We call the controller with an input "exit" so
        // that the current state of the task list can be saved.
        shell.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                Controller.startController("exit");
                System.exit(0);     // TODO: I shouldn't need to call this. "exit" is not being handled?
            }
        });
        
        // To keep the width of the single column in the feedback table
        // to be the same as the table itself.
        feedbackTable.addListener(SWT.Resize, new Listener() {
            public void handleEvent(Event event) {
                tblclmnFeedback.setWidth(feedbackTable.getClientArea().width);
            }
        });
    }
    
    private static void initializeDisplayRefreshTimer(int delay) {
        displayTimer = new Timer(delay, null);
        displayTimer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Display.getDefault().syncExec(new Runnable() {
                    public void run() {
                        Controller.startController("display");
                    }
                });
            }
        });
        displayTimer.setRepeats(true);
    }
    
    private static void startDisplayTimer() {
        displayTimer.start();
    }
    
    private static void stopDisplayTimer() {
        displayTimer.stop();
    }

    public static void main(String[] args) {
        //@author generated
        display = Display.getDefault();
        shell = new Shell();
        shell.setMinimumSize(new Point(400, 450));
        shell.setToolTipText("To-do list app of the year");
        shell.setSize(800, 512);
        shell.setLayout(new GridLayout(1, false));
        shell.setText("JOYTZ");

        taskTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
        taskTable.setLinesVisible(true);
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

        tblclmnStartedOn = new TableColumn(taskTable, SWT.CENTER);
        tblclmnStartedOn.setMoveable(true);
        tblclmnStartedOn.setWidth(92);
        tblclmnStartedOn.setText("Started On");

        tblclmnDeadline = new TableColumn(taskTable, SWT.CENTER);
        tblclmnDeadline.setMoveable(true);
        tblclmnDeadline.setWidth(100);
        tblclmnDeadline.setText("Deadline");

        tblclmnName = new TableColumn(taskTable, SWT.CENTER);
        tblclmnName.setMoveable(true);
        tblclmnName.setWidth(100);
        tblclmnName.setText("Name");

        tblclmnLocation = new TableColumn(taskTable, SWT.CENTER);
        tblclmnLocation.setMoveable(true);
        tblclmnLocation.setWidth(100);
        tblclmnLocation.setText("Location");

        tblclmnDescription = new TableColumn(taskTable, SWT.CENTER);
        tblclmnDescription.setMoveable(true);
        tblclmnDescription.setWidth(103);
        tblclmnDescription.setText("Description");

        tblclmnPriority = new TableColumn(taskTable, SWT.CENTER);
        tblclmnPriority.setMoveable(true);
        tblclmnPriority.setWidth(72);
        tblclmnPriority.setText("Priority");
        
        feedbackTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        GridData gd_feedbackTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_feedbackTable.heightHint = 140;
        feedbackTable.setLayoutData(gd_feedbackTable);
        
        tblclmnFeedback = new TableColumn(feedbackTable, SWT.NONE);
        tblclmnFeedback.setWidth(100);
        tblclmnFeedback.setText("Feedback");
        
        Label horizontalSeparator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        GridData gd_horizontalSeparator = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_horizontalSeparator.widthHint = 550;
        horizontalSeparator.setLayoutData(gd_horizontalSeparator);

        inputField = new StyledText(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
        inputField.setToolTipText("Enter your commands here");
        inputField.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
        GridData gd_inputField = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        gd_inputField.heightHint = 85;
        inputField.setLayoutData(gd_inputField);
        
        //@author A0094558N
        startupProgram();
        setupListeners();
      
        //@author generated
        shell.open();
        shell.layout();

        while(!shell.isDisposed()) {

            //@author A0094558N
            if (displayTimer.isRunning() == false && isSortingOrSearching == false) {
                startDisplayTimer();
            }
            
            //@author generated
            display.readAndDispatch();
        }
        display.dispose();
    }
    
    //@author A0094558N-reused
    /*
     * (non-Javadoc)
     * @see com.melloware.jintellitype.HotkeyListener#onHotKey(int)
     
    public void onHotKey(int aIdentifier) {
        if (aIdentifier == 1) {
           System.out.println("WINDOWS+A hotkey pressed");
           
           // JIntellitype runs in a different thread, so we need
           // to ensure that the following code runs in the 
           // same thread as the application itself
           Display.getDefault().syncExec(new Runnable() {
               public void run() {
                   shell.forceActive();     // Bring application to front
               }
           });
        }
     }
    
    /**
     * Initialize the JInitellitype library making sure the DLL is located.
     
    public static void initJIntellitype() {
        mainFrame = new GUI();
        
        try {
           // initialize JIntellitype with the frame so all windows commands can
           // be attached to this window
           JIntellitype.getInstance().addHotKeyListener(mainFrame);
           JIntellitype.getInstance().addIntellitypeListener(mainFrame);
           JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_WIN, (int)'A');    // WIN+A
           System.out.println("JIntellitype initialized");
        } catch (RuntimeException ex) {
           System.out.println("Either you are not on Windows, or there is a problem with the JIntellitype library!");
        }
     }
    
    /*
     * (non-Javadoc)
     * @see com.melloware.jintellitype.IntellitypeListener#onIntellitype(int)
     
    public void onIntellitype(int aCommand) {

       switch (aCommand) {
       case JIntellitype.APPCOMMAND_BROWSER_BACKWARD:
           System.out.println("BROWSER_BACKWARD message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_BROWSER_FAVOURITES:
           System.out.println("BROWSER_FAVOURITES message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_BROWSER_FORWARD:
           System.out.println("BROWSER_FORWARD message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_BROWSER_HOME:
           System.out.println("BROWSER_HOME message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_BROWSER_REFRESH:
           System.out.println("BROWSER_REFRESH message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_BROWSER_SEARCH:
           System.out.println("BROWSER_SEARCH message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_BROWSER_STOP:
           System.out.println("BROWSER_STOP message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_LAUNCH_APP1:
           System.out.println("LAUNCH_APP1 message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_LAUNCH_APP2:
           System.out.println("LAUNCH_APP2 message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_LAUNCH_MAIL:
           System.out.println("LAUNCH_MAIL message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_MEDIA_NEXTTRACK:
           System.out.println("MEDIA_NEXTTRACK message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_MEDIA_PLAY_PAUSE:
           System.out.println("MEDIA_PLAY_PAUSE message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_MEDIA_PREVIOUSTRACK:
           System.out.println("MEDIA_PREVIOUSTRACK message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_MEDIA_STOP:
           System.out.println("MEDIA_STOP message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_VOLUME_DOWN:
           System.out.println("VOLUME_DOWN message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_VOLUME_UP:
           System.out.println("VOLUME_UP message received " + Integer.toString(aCommand));
          break;
       case JIntellitype.APPCOMMAND_VOLUME_MUTE:
           System.out.println("VOLUME_MUTE message received " + Integer.toString(aCommand));
          break;
       default:
           System.out.println("Undefined INTELLITYPE message caught " + Integer.toString(aCommand));
          break;
       }
    }*/
}