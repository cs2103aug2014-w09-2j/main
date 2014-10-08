//package V1;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class GUI extends Composite {

    private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
    private Text inputField;
    private static Text outputField;
    private static String textInputData = "";
    private static String textOutputData = "";
    private Table table;
    private TableColumn tblclmnRemarks;

    public static String getUserInput() {
        return textInputData;        
    }
    
    public static void displayOutput(String output) {
        if (textInputData.isEmpty() == false) {
            textOutputData = outputField.getText();
            textOutputData = textOutputData.concat(output + "\n");
            outputField.setText(textOutputData);
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
        setLayout(null);
        
        inputField = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        inputField.setBounds(0, 431, 443, 49);
        toolkit.adapt(inputField, true, true);
        
         outputField = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
         outputField.setEditable(false);
         outputField.setLocation(10, 320);
         outputField.setSize(433, 101);
         toolkit.adapt(outputField, true, true);
        
        table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
        table.setBounds(10, 10, 433, 304);
        toolkit.adapt(table);
        toolkit.paintBordersFor(table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        TableColumn tblclmnDate = new TableColumn(table, SWT.NONE);
        tblclmnDate.setWidth(100);
        tblclmnDate.setText("Due Date");
        
        TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
        tblclmnName.setWidth(100);
        tblclmnName.setText("Name");
        
        tblclmnRemarks = new TableColumn(table, SWT.NONE);
        tblclmnRemarks.setWidth(100);
        tblclmnRemarks.setText("Remarks");
     
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
        Shell shell = new Shell(display);
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
