import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;


public class GUISettings extends Dialog {
    private static final String ERROR_CREATE_FILE = "There was a problem " +
                                                    "creating a new file: ";
    private static final String ERROR_READING_FILE = "There was a problem " +
                                                     "reading from file: ";
    private static final String ERROR_WRITING_FILE = "There was a problem " +
                                                     "writing to file: ";
    private static final String FILENAME = "settings";
    
    private static File settingsFile;
    private static List<String> workingStorage;
    
    
    protected Object result;
    protected static Shell shell;

    /**
     * Create the dialog.
     * @param parent
     * @param style
     */
    public GUISettings(Shell parent, int style) {
        super(parent, style);
        setText("JOYTZ Settings");
    }

    /**
     * Open the dialog.
     * @return the result
     */
    public Object open() {
        createContents();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return result;
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents() {
        shell = new Shell(getParent(), SWT.CLOSE | SWT.MIN | SWT.TITLE);
        shell.setSize(360, 210);
        shell.setText(getText());
        shell.setLayout(new FormLayout());
        
        Label lblNotificationFrequency = new Label(shell, SWT.NONE);
        lblNotificationFrequency.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        FormData fd_lblNotificationFrequency = new FormData();
        fd_lblNotificationFrequency.top = new FormAttachment(0, 10);
        fd_lblNotificationFrequency.left = new FormAttachment(0, 10);
        lblNotificationFrequency.setLayoutData(fd_lblNotificationFrequency);
        lblNotificationFrequency.setText("Notification Frequency (minutes):");
        
        Spinner spinner = new Spinner(shell, SWT.BORDER);
        spinner.setMaximum(1440);
        spinner.setMinimum(1);
        FormData fd_spinner = new FormData();
        fd_spinner.top = new FormAttachment(0, 10);
        fd_spinner.left = new FormAttachment(lblNotificationFrequency, 6);
        spinner.setLayoutData(fd_spinner);
        
        Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        FormData fd_label = new FormData();
        fd_label.bottom = new FormAttachment(spinner, 18, SWT.BOTTOM);
        fd_label.top = new FormAttachment(spinner, 16);
        fd_label.left = new FormAttachment(0, 10);
        fd_label.right = new FormAttachment(100, -10);
        label.setLayoutData(fd_label);
        
        Label lblDeadlineColor = new Label(shell, SWT.NONE);
        lblDeadlineColor.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        FormData fd_lblDeadlineColor = new FormData();
        fd_lblDeadlineColor.top = new FormAttachment(label, 16);
        fd_lblDeadlineColor.left = new FormAttachment(lblNotificationFrequency, 0, SWT.LEFT);
        lblDeadlineColor.setLayoutData(fd_lblDeadlineColor);
        lblDeadlineColor.setText("Deadline color:");
        
        Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        FormData fd_label_1 = new FormData();
        fd_label_1.top = new FormAttachment(lblDeadlineColor, 17);
        fd_label_1.right = new FormAttachment(label, 0, SWT.RIGHT);
        fd_label_1.bottom = new FormAttachment(lblDeadlineColor, 19, SWT.BOTTOM);
        fd_label_1.left = new FormAttachment(lblNotificationFrequency, 0, SWT.LEFT);
        label_1.setLayoutData(fd_label_1);
        
        Label lblOngoingColor = new Label(shell, SWT.NONE);
        FormData fd_lblOngoingColor = new FormData();
        fd_lblOngoingColor.top = new FormAttachment(label_1, 21);
        fd_lblOngoingColor.left = new FormAttachment(lblNotificationFrequency, 0, SWT.LEFT);
        lblOngoingColor.setLayoutData(fd_lblOngoingColor);
        lblOngoingColor.setText("Ongoing color:");
        
        ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
        FormData fd_toolBar = new FormData();
        fd_toolBar.top = new FormAttachment(lblDeadlineColor, 0, SWT.TOP);
        fd_toolBar.left = new FormAttachment(lblDeadlineColor, 6);
        toolBar.setLayoutData(fd_toolBar);
        
        ToolItem tltmRed = new ToolItem(toolBar, SWT.RADIO);
        tltmRed.setText("Red");
        
        ToolItem tltmBlue = new ToolItem(toolBar, SWT.RADIO);
        tltmBlue.setText("Blue");
        
        ToolItem tltmGreen = new ToolItem(toolBar, SWT.RADIO);
        tltmGreen.setText("Green");
        
        ToolItem tltmOrange = new ToolItem(toolBar, SWT.RADIO);
        tltmOrange.setText("Orange");
        
        ToolBar toolBar_1 = new ToolBar(shell,  SWT.RIGHT);
        FormData fd_toolBar_1 = new FormData();
        fd_toolBar_1.top = new FormAttachment(lblOngoingColor, 0, SWT.TOP);
        fd_toolBar_1.left = new FormAttachment(lblOngoingColor, 6);
        toolBar_1.setLayoutData(fd_toolBar_1);
        
        ToolItem tltmRed_1 = new ToolItem(toolBar_1, SWT.RADIO);
        tltmRed_1.setText("Red");
        
        ToolItem tltmBlue_1 = new ToolItem(toolBar_1, SWT.RADIO);
        tltmBlue_1.setText("Blue");
        
        ToolItem tltmGreen_1 = new ToolItem(toolBar_1, SWT.RADIO);
        tltmGreen_1.setText("Green");
        
        ToolItem tltmOrange_1 = new ToolItem(toolBar_1, SWT.RADIO);
        tltmOrange_1.setText("Orange");

        setupListeners();
    }

    public static void loadGUISettings() {
        initializeVariables();
        createFile(FILENAME);
        readFile(FILENAME, workingStorage);
    }
    
    private static void initializeVariables() {
        settingsFile = new File(FILENAME);
        workingStorage = new ArrayList<String>();
    }
    
    // Checks whether the given file exists, and creates one if it does not
    private static void createFile (String filename) {
        // Get the file path directory
        Path filePath = Paths.get(filename);
        // Look for the file in the filepath, and create it if it does not exist
        if (!Files.exists(filePath)) {
            try {
                settingsFile.createNewFile();
            } catch (IOException e) {
                System.out.print(ERROR_CREATE_FILE);
            }
        }
    }
    
    // Reads the contents of the file and stores it in an array list
    private static void readFile (String filename, List<String> workingStorage) {
        String temp;
        try {
            BufferedReader in = new BufferedReader(new FileReader(settingsFile));
            // Read the file line by line and add each line
            // into an index of workingStorage
            while ((temp = in.readLine()) != null) {
                workingStorage.add(temp);
            }
            in.close();
        } catch (IOException e) {
            System.out.print(ERROR_READING_FILE);
        }
    }
    
    // Write the contents of the array list into the file
    private static void writeFile (File settingsFile, List<String> workingStorage) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(settingsFile));
            // Write all contents of workingStorage to the file
            for (int i = 0; i < workingStorage.size(); i++) {
                out.write(workingStorage.get(i));
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            System.out.print(ERROR_WRITING_FILE);
            e.printStackTrace();
        }
    }
    
    private static void setupListeners(){
        shell.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                //writeFile(settingsFile, workingStorage);
            }
        });
    }
}
