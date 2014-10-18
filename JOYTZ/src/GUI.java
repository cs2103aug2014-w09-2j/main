//package V1;

import java.util.logging.Logger;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Display;
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
import org.eclipse.swt.graphics.Point;

public class GUI {
	private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());
	
	private static final String HELP_TEXT = "Commands: \n" +
											"	add~task name~description~start time " +
											"~end time~location~priority\n" +
											"	delete~index number\n" +
											"	update~index number~attribute~new data\n" +
											"	sort~attribute\n" +
											"	undo\n" + 
											"	display\n" +
											"	clear\n" +
											"	help\n" +
											"	exit\n" +
											"Time entry: (dd/mm/yyyy hh:mmxx, xx = am or pm)\n" +
											"Attributes: Refer to the headings on the table";

    private static StyledText inputField;
    private static StyledText outputField;
    private static String textInputData = "";
    private static Table table;
    private static TableColumn tblclmnNo;
    private static TableColumn tblclmnName;
    private static TableColumn tblclmnDate;
    private static TableColumn tblclmnLocation;
    private static TableColumn tblclmnPriority;
    private static TableColumn tblclmnDescription;
    
    /**
     * A getter method for the controller to obtain the user's input
     *
     * @return	The user's input string
     * 
     * @author Joel
	 */
    public static String getUserInput() {
        return textInputData;        
    }
    
    /**
     * Displays a feedback string in the GUI after each user command
     *
     * @param output	The string to be displayed
     * 
     * @author Joel
	 */
    public static void displayOutput(String output) {
    	outputField.setText(output);
    }
    
    /**
     * Displays the help text in the middle GUI box, styling it
     * such that the command words are bolded.
     * 
     * @author Joel
	 */
    private static void displayHelp() {
    	outputField.setText(HELP_TEXT);
    	
    	StyleRange boldAdd = new StyleRange();
    	boldAdd.start = 12;
    	boldAdd.length = 3;
    	boldAdd.fontStyle = SWT.BOLD;
    	outputField.setStyleRange(boldAdd);
    	
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
    	outputField.setStyleRange(boldExit);
    }
    
    /**
     * Updates the table in the GUI based on the given parameters
     *
     * @param taskNumber    	Index number of the task in the table
     * @param date    			Deadline of the given task
     * @param name 				Name of the given task
     * @param location			Location of the given task
     * @param description		Description for the given task
     * @param action			Action input by the user (add, delete, etc.) 
     * @param priority			Priority level of the given task
     * 
     * @author Joel
	 */
    public static void updateTable(int taskNumber, String date, 
    							   String name, String location, 
    							   String description, String action, 
    							   String priority) {

    	// To prevent multiple of the same entries, we clear the whole table first
		if (taskNumber == 0) {
			table.removeAll();
			assert table.getItemCount() == 0;
		}
		
		if (!action.equals("clear")) {
			// Debugging code
			LOGGER.info("==============\n" +
						"Writing to table (GUI):  \n" + 
						"	Action = " + action + "\n" + 
						"	Name = " + name + "\n" +
						"	Deadline = " + date + "\n" + 
						"	Description = " + description + "\n" +
						"	Location = " + location + "\n" +
						"	Priority = " + priority + "\n" +
						"====================\n");
			
			// 1 row = 1 TableItem
		    TableItem item = new TableItem(table, SWT.NONE);
	        item.setText(new String[] { (taskNumber+1) + ".", date, name, location, 
	        							description, priority });
		}
		resizeTable();
    } 
    
    /**
     * Resizes the columns in the table based 
     * on the width of the application.
     * 
     * @author Joel
	 */
    private static void resizeTable() {
    	int tableWidth = table.getSize().x;
    	int scrollbarWidth = table.getVerticalBar().getSize().x;
    	
    	int widthLeft = tableWidth - tblclmnNo.getWidth() - 
    					tblclmnDate.getWidth() - scrollbarWidth;
    	int widthPerColumn = widthLeft / 4;
    	
    	// Resize all the columns to fit the data,
		tblclmnNo.pack();
        tblclmnDate.pack();
        tblclmnName.pack();
        tblclmnPriority.pack();
        tblclmnDescription.pack();
        tblclmnLocation.pack();
        
        // prevent it from being too big
		if (tblclmnName.getWidth() >= widthPerColumn) {
			tblclmnName.setWidth(widthPerColumn);
		}
		if (tblclmnPriority.getWidth() >= widthPerColumn) {
			tblclmnPriority.setWidth(widthPerColumn);
		}
		if (tblclmnDescription.getWidth() >= widthPerColumn) {
			tblclmnDescription.setWidth(widthPerColumn);
		}
		if (tblclmnLocation.getWidth() >= widthPerColumn) {
			tblclmnLocation.setWidth(widthPerColumn);
		}  
    }
    
    public static void main(String[] args) {
        Display display = Display.getDefault();
        Shell shell = new Shell();
        shell.setMinimumSize(new Point(400, 450));
		shell.setToolTipText("To-do list app of the year");
		shell.setSize(641, 497);
		shell.setLayout(new GridLayout(1, false));
		shell.setText("JOYTZ");
        
        table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
        table.setSize(new Point(400, 400));
        table.setToolTipText("View your tasks here");
        GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_table.heightHint = 248;
        table.setLayoutData(gd_table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        tblclmnNo = new TableColumn(table, SWT.CENTER);
        tblclmnNo.setToolTipText("Index number");
        tblclmnNo.setWidth(42);
        tblclmnNo.setText("No.");
        
        tblclmnDate = new TableColumn(table, SWT.CENTER);
        tblclmnDate.setWidth(92);
        tblclmnDate.setText("Deadline");
        
        tblclmnName = new TableColumn(table, SWT.CENTER);
        tblclmnName.setWidth(154);
        tblclmnName.setText("Task Name");
        
        tblclmnLocation = new TableColumn(table, SWT.CENTER);
        tblclmnLocation.setWidth(100);
        tblclmnLocation.setText("Location");
        
        tblclmnDescription = new TableColumn(table, SWT.CENTER);
        tblclmnDescription.setWidth(118);
        tblclmnDescription.setText("Description");
        
        tblclmnPriority = new TableColumn(table, SWT.CENTER);
        tblclmnPriority.setWidth(100);
        tblclmnPriority.setText("Priority");
        
        outputField = new StyledText(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
        outputField.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
        outputField.setToolTipText("See status messages here");
        displayHelp();
        GridData gd_outputField = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);
        gd_outputField.widthHint = 370;
        gd_outputField.heightHint = 154;
        outputField.setLayoutData(gd_outputField);
        outputField.setEditable(false);
        
        inputField = new StyledText(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
        inputField.setToolTipText("Enter your commands here");
        inputField.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
        GridData gd_inputField = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        gd_inputField.heightHint = 85;
        inputField.setLayoutData(gd_inputField);
        
        // We call the controller to process the user's 
        // input once the user presses "enter"
        inputField.addKeyListener(new KeyAdapter() {
           // @Override
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {
                    textInputData = inputField.getText();
                    
                    if (textInputData.trim().equals("help")) {
                    	displayHelp();
                    	inputField.setText("");
                    } else {
	                    Controller.startController();
	                    
	                    inputField.setText("");
	
	                    NotifierDialog.notify("Hi There! I'm a notification widget!", 
	                    					  "Today we are creating a widget that allows us" +
	                    					  "to show notifications that fade in and out!");
                    }
                }
            }
        });
      
        shell.open();
		shell.layout();
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}
