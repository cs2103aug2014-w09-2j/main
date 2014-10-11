//package V1;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.GridLayout;

public class GUI extends Composite {

    private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
    private Text inputField;
    private static Text outputField;
    private static String textInputData = "";
    private static String textOutputData = "";
    private static Table table;
    private TableColumn tblclmnRemarks;
    private TableColumn tblclmnNo;
    private TableColumn tblclmnLocation;

    public static String getUserInput() {
        return textInputData;        
    }
    
    public static void displayOutput(String output) {
        if (textInputData.isEmpty() == false) {
            // textOutputData = outputField.getText();
            // textOutputData = textOutputData.concat(output + "\n");
            outputField.setText(output);
        }
    }
    
    // Pass object in here
    public static void updateTable(int numOfTasks, String date, String name, String location, String description) {
    	// Date
    	// Name
    	table.removeAll();
    	
    	for (int i = 0; i < numOfTasks; i++) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(new String[] { i + ".", date, name, location, description });
        }
    }
    
    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    private GUI(Composite parent, int style) {
        super(parent, SWT.NONE);
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                toolkit.dispose();
            }
        });
        toolkit.adapt(this);
        toolkit.paintBordersFor(this);
        setLayout(new GridLayout(1, false));
        
        table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
        GridData gd_table = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        gd_table.heightHint = 149;
        table.setLayoutData(gd_table);
        toolkit.adapt(table);
        toolkit.paintBordersFor(table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        tblclmnNo = new TableColumn(table, SWT.NONE);
        tblclmnNo.setWidth(34);
        tblclmnNo.setText("No.");
        
        TableColumn tblclmnDate = new TableColumn(table, SWT.NONE);
        tblclmnDate.setWidth(100);
        tblclmnDate.setText("Due Date");
        
        TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
        tblclmnName.setWidth(92);
        tblclmnName.setText("Task Name");
        
        tblclmnLocation = new TableColumn(table, SWT.NONE);
        tblclmnLocation.setWidth(100);
        tblclmnLocation.setText("Location");
        
        tblclmnRemarks = new TableColumn(table, SWT.NONE);
        tblclmnRemarks.setWidth(118);
        tblclmnRemarks.setText("Description");
        
         outputField = new Text(this, SWT.FILL | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
         GridData gd_outputField = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
         gd_outputField.widthHint = 370;
         gd_outputField.heightHint = 73;
         outputField.setLayoutData(gd_outputField);
         outputField.setEditable(false);
         toolkit.adapt(outputField, true, true);
        
        inputField = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        GridData gd_inputField = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_inputField.widthHint = 367;
        gd_inputField.heightHint = 53;
        inputField.setLayoutData(gd_inputField);
        GridData gd = new GridData();
        gd.heightHint = 53;
        gd.widthHint = 221;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalAlignment = SWT.FILL;
        inputField.setLayoutData(gd);
        toolkit.adapt(inputField, true, true);
        
        // We call the controller to process the user's 
        // input once the user presses "enter"
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {
                    textInputData = inputField.getText();
                    Controller.startController();
                    
                    inputField.setText("");
                }
            }
        });
    }
    
    public static void main(String[] args){
        Display display = new Display();
        final Shell shell = new Shell(display);
        
        shell.addListener (SWT.Resize,  new Listener () {
            public void handleEvent (Event e) {
              Rectangle rect = shell.getClientArea ();
              System.out.println(rect);
            }
          });
        
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
