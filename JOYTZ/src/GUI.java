//package V1;

import java.util.logging.Logger;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wb.swt.SWTResourceManager;

public class GUI extends Composite {
	private final static Logger LOGGER = Logger.getLogger(GUI.class.getName());

    private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
    private StyledText inputField;
    private static Text outputField;
    private static String textInputData = "";
    private static Table table;
    private TableColumn tblclmnRemarks;
    private TableColumn tblclmnNo;
    private TableColumn tblclmnLocation;
    private TableColumn tblclmnPriority;
    private StyledText styledText;
    
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
    } 
    
    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    private GUI(Composite parent, int style) {
        super(parent, SWT.BORDER);
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                toolkit.dispose();
            }
        });
        toolkit.adapt(this);
        toolkit.paintBordersFor(this);
        setLayout(new GridLayout(1, false));
        
        table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
        table.setToolTipText("View your tasks here");
        GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_table.heightHint = 248;
        table.setLayoutData(gd_table);
        toolkit.adapt(table);
        toolkit.paintBordersFor(table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        tblclmnNo = new TableColumn(table, SWT.NONE);
        tblclmnNo.setWidth(42);
        tblclmnNo.setText("No.");
        
        TableColumn tblclmnDate = new TableColumn(table, SWT.NONE);
        tblclmnDate.setWidth(92);
        tblclmnDate.setText("Deadline");
        
        TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
        tblclmnName.setWidth(154);
        tblclmnName.setText("Task Name");
        
        tblclmnLocation = new TableColumn(table, SWT.NONE);
        tblclmnLocation.setWidth(100);
        tblclmnLocation.setText("Location");
        
        tblclmnRemarks = new TableColumn(table, SWT.NONE);
        tblclmnRemarks.setWidth(118);
        tblclmnRemarks.setText("Description");
        
        tblclmnPriority = new TableColumn(table, SWT.NONE);
        tblclmnPriority.setWidth(100);
        tblclmnPriority.setText("Priority");
        
        outputField = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
        outputField.setToolTipText("See status messages here");
        outputField.setText("Commands: \nadd~task name~description~dd/mm/yyyy~location~priority\r\n" +
        					"delete~index number \n" + "update~index number~attribute~new data\n" + 
        					"clear\n" + "exit");
        GridData gd_outputField = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        gd_outputField.widthHint = 370;
        gd_outputField.heightHint = 73;
        outputField.setLayoutData(gd_outputField);
        outputField.setEditable(false);
        toolkit.adapt(outputField, true, true);
        
        inputField = new StyledText(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
        inputField.setToolTipText("Enter your commands here");
        inputField.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
        GridData gd_inputField = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_inputField.heightHint = 50;
        inputField.setLayoutData(gd_inputField);
        toolkit.adapt(inputField);
        toolkit.paintBordersFor(inputField);
        
        // We call the controller to process the user's 
        // input once the user presses "enter"
        inputField.addKeyListener(new KeyAdapter() {
           // @Override
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {
                    textInputData = inputField.getText();
                    Controller.startController();
                    
                    inputField.setText("");
                }
            }
        });
    }
    
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);//, SWT.CLOSE | SWT.TITLE | SWT.MIN );
           
        GUI gui = new GUI(shell, SWT.NONE);
        shell.setText("JOYTZ");
        gui.pack();
        shell.pack();
        shell.open();
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}
