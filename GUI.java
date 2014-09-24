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
import org.eclipse.swt.widgets.Label;

public class GUI extends Composite {

    private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
    private Text inputField;
    private static Label outputField;
    private static String textInputData = "";
    private static String textOutputData = "";

    public static String getUserInput() {
        return textInputData;        
    }
    
    public static void displayOutput(String output) {
        if (textInputData.isEmpty() == false) {
            textOutputData = outputField.getText();
            textOutputData = textOutputData.concat(textInputData + "\n");
            outputField.setText(textOutputData);
        }
    }
    
    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public GUI(Composite parent, int style) {
        super(parent, style);
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                toolkit.dispose();
            }
        });
        toolkit.adapt(this);
        toolkit.paintBordersFor(this);
        setLayout(null);
        
        inputField = new Text(this, SWT.BORDER);
        inputField.setBounds(0, 224, 443, 49);
        toolkit.adapt(inputField, true, true);
       
        outputField = new Label(this, SWT.NONE);
        outputField.setBounds(10, 10, 433, 188);
        toolkit.adapt(outputField, true, true);
     
        // We call the controller to process the user's 
        // input once the user presses "enter"
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {
                    textInputData = inputField.getText();
                    Controller.main(null);
                    
                    inputField.setText("");
                }
            }
        });
    }
    
    public static void main(String[] args){
        Display display = new Display();
        Shell shell = new Shell(display);
        GUI gui = new GUI(shell, SWT.NONE);
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
