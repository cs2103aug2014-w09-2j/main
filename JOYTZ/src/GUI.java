//package V1;
//@author A0094558N 
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

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
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

import com.ibm.icu.util.Calendar;
import org.eclipse.swt.widgets.Label;

public class GUI {
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

    private static StyledText inputField;
    private static Table taskTable;
    private static TableColumn tblclmnNo;
    private static TableColumn tblclmnName;
    private static TableColumn tblclmnStartedOn;
    private static TableColumn tblclmnLocation;
    private static TableColumn tblclmnPriority;
    private static TableColumn tblclmnDescription;
    private static TableColumn tblclmnDeadline;
    private static boolean hasNotified = false;
    private static Display display;
    private static Table feedbackTable;
    private static TableColumn tblclmnFeedback;

    /**
     * Displays a feedback string in the GUI after each user command
     *
     * @param output	The string to be displayed
     * 
     */
    public static void displayOutput(String output) {
        TableItem item = new TableItem(feedbackTable, SWT.NONE);
        item.setText(new String[] { output });
        
        // This ensures that the table is always scrolled to the bottom
        feedbackTable.setTopIndex(feedbackTable.getItemCount() - 1);
    }

    /**
     * Displays the help text in GUI box that is found in the middle,
     * styling it such that the command words are in bold.
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
        
        
        
        /*
        StyleRange boldAdd = new StyleRange();
        boldAdd.start = 12;
        boldAdd.length = 3;
        boldAdd.fontStyle = SWT.BOLD;
        itemAdd.setStyleRange(boldAdd);

        StyleRange boldDelete = new StyleRange();
        boldDelete.start = 78;
        boldDelete.length = 6;
        boldDelete.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldDelete);

        StyleRange boldUpdate = new StyleRange();
        boldUpdate.start = 99;
        boldUpdate.length = 6;
        boldUpdate.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldUpdate);

        StyleRange boldSort = new StyleRange();
        boldSort.start = 139;
        boldSort.length = 4;
        boldSort.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldSort);

        StyleRange boldUndo = new StyleRange();
        boldUndo.start = 155;
        boldUndo.length = 4;
        boldUndo.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldUndo);

        StyleRange boldDisplay = new StyleRange();
        boldDisplay.start = 161;
        boldDisplay.length = 7;
        boldDisplay.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldDisplay);

        StyleRange boldClear = new StyleRange();
        boldClear.start = 170;
        boldClear.length = 5;
        boldClear.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldClear);

        StyleRange boldHelp = new StyleRange();
        boldHelp.start = 177;
        boldHelp.length = 4;
        boldHelp.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldHelp);

        StyleRange boldExit = new StyleRange();
        boldExit.start = 183;
        boldExit.length = 4;
        boldExit.fontStyle = SWT.BOLD;
        outputField.setStyleRange(boldExit);*/
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

        // To prevent multiple of the same entries, we clear the whole table first
        if (taskNumber == 0 || action.equals("null")) {
            taskTable.removeAll();
            assert taskTable.getItemCount() == 0;
        }

        // TODO: Don't hardcode !startDate.equals("null")
        if (!action.equals("clear") && !startDate.equals("null")) {
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
                colorRow(item);
                NotifierDialog.notify(name + " is overdue!", "");
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
    private static void colorRow(TableItem item) {
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
        // fixed format and are unlikely to become too big.
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

    public static void main(String[] args) {
        //@author generated
        display = Display.getDefault();
        Shell shell = new Shell();
        shell.setMinimumSize(new Point(400, 450));
        shell.setToolTipText("To-do list app of the year");
        shell.setSize(647, 512);
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

        /*
        outputField = new StyledText(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
        outputField.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
        outputField.setToolTipText("See status messages here");
        //@author A0094558N
        
        //@author generated
        GridData gd_outputField = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        gd_outputField.widthHint = 370;
        gd_outputField.heightHint = 154;
        outputField.setLayoutData(gd_outputField);
        outputField.setEditable(false);
        */
        
        feedbackTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        GridData gd_feedbackTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_feedbackTable.heightHint = 140;
        feedbackTable.setLayoutData(gd_feedbackTable);
        
        tblclmnFeedback = new TableColumn(feedbackTable, SWT.NONE);
        tblclmnFeedback.setWidth(100);
        tblclmnFeedback.setText("Feedback");
        
        displayHelp();
        
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
        // We call the controller to process the user's 
        // input once the user presses "enter"
        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {
                    if (inputField.getText().trim().equals("help")) {
                        displayHelp();
                        inputField.setText("");
                    } else {
                        Controller.startController(inputField.getText());

                        inputField.setText("");
                    }
                }
            }
        });

        // We call the controller with an input "exit" so
        // that the current state of the task list can be saved.
        shell.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                Controller.startController("exit");
                System.exit(0);		// TODO: I shouldn't need to call this. "exit" is not being handled?
            }
        });
        
        // To keep the width of the single column in the feedback table
        // to be the same as the table itself.
        feedbackTable.addListener(SWT.Resize, new Listener()
        {
            public void handleEvent(Event event)
            {
            	tblclmnFeedback.setWidth(feedbackTable.getClientArea().width);
            }
        });
        
        //@author generated
        shell.open();
        shell.layout();

        while(!shell.isDisposed()) {

            //@author A0094558N
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            //System.out.println(timeStamp );

            if (timeStamp.trim().equals("20141018_200700") && hasNotified == false) {
                hasNotified = true;
                NotifierDialog.notify("Hi There! I'm a notification widget!", 
                                      "Today we are creating a widget that allows us" +
                                      "to show notifications that fade in and out!");

                // We only toggle the boolean after a delay, 
                // or multiple notifications will popup.
                Timer setHasNotifiedToFalse = new Timer();
                setHasNotifiedToFalse.schedule(new TimerTask() {
                    public void run() {
                        hasNotified = false;
                    }
                }, 
                1000);
            }

            display.readAndDispatch();

            // Uncomment this and comment the line above if you
            // want the program to sleep if not in focus
            // if(!display.readAndDispatch()) {
            //    display.sleep();
            // }
        }
        display.dispose();
    }
}