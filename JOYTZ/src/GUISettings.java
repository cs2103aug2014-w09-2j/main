//@author A0094558N
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
import org.eclipse.swt.widgets.Button;


public class GUISettings extends Dialog {
    private static final String ERROR_CREATE_FILE = "There was a problem " +
                                                    "creating a new file: ";
    private static final String ERROR_READING_FILE = "There was a problem " +
                                                     "reading from file: ";
    private static final String ERROR_WRITING_FILE = "There was a problem " +
                                                     "writing to file: ";
    public static final String FILENAME = "settings.txt";
    private static final int SETTINGS_TOTAL_NUMBER = 9;
    public static final int SETTINGS_NOTIF_FREQ_INDEX = 0;
    public static final int SETTINGS_DEADLINE_COLOR_R_INDEX = 1;
    public static final int SETTINGS_DEADLINE_COLOR_G_INDEX = 2;
    public static final int SETTINGS_DEADLINE_COLOR_B_INDEX = 3;
    public static final int SETTINGS_ONGOING_COLOR_R_INDEX = 4;
    public static final int SETTINGS_ONGOING_COLOR_G_INDEX = 5;
    public static final int SETTINGS_ONGOING_COLOR_B_INDEX = 6;
    public static final int SETTINGS_NOTIFICATIONS_ONGOING_INDEX = 7;
    public static final int SETTINGS_NOTIFICATIONS_OVERDUE_INDEX = 8;
    public static final int SETTINGS_DEFAULT_NOTIF_FREQ = 60;
    public static final int SETTINGS_DEFAULT_DEADLINE_COLOR_R = 255;
    public static final int SETTINGS_DEFAULT_DEADLINE_COLOR_G = 0;
    public static final int SETTINGS_DEFAULT_DEADLINE_COLOR_B = 0;
    public static final int SETTINGS_DEFAULT_ONGOING_COLOR_R = 0;
    public static final int SETTINGS_DEFAULT_ONGOING_COLOR_G = 128;
    public static final int SETTINGS_DEFAULT_ONGOING_COLOR_B = 0;
    public static final int SETTINGS_DEFAULT_NOTIF_OVERDUE = 1;
    public static final int SETTINGS_DEFAULT_NOTIF_ONGOING = 1;
    private static final int COLOR_RED_R = 255;
    private static final int COLOR_RED_G = 0;
    private static final int COLOR_RED_B = 0;
    private static final int COLOR_GREEN_R = 0;
    private static final int COLOR_GREEN_G = 128;
    private static final int COLOR_GREEN_B = 0;
    private static final int COLOR_BLUE_R = 51;
    private static final int COLOR_BLUE_G = 102;
    private static final int COLOR_BLUE_B = 255;
    private static final int COLOR_ORANGE_R = 255;
    private static final int COLOR_ORANGE_G = 120;
    private static final int COLOR_ORANGE_B = 0;
    
    private static File settingsFile;
    private static List<Integer> workingStorage;
    
    
    protected Object result;
    protected static Shell shell;
    private static Spinner spinnerNotifFreq;
    private ToolBar toolBarDeadline;
    private static ToolItem tltmOngoingRed;
    private static ToolItem tltmOngoingBlue;
    private static ToolItem tltmOngoingGreen;
    private static ToolItem tltmOngoingOrange;
    private static ToolItem tltmDeadlineRed;
    private static ToolItem tltmDeadlineBlue;
    private static ToolItem tltmDeadlineGreen;
    private static ToolItem tltmDeadlineOrange;
    private static Button btnoverdueNotification;
    private static Button btnstartedNotification;

    //@author generated
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
        loadGUISettings();
        createContents();
        setupListeners();
        displaySettingsInSettingsGUI();
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
        shell.setSize(360, 266);
        shell.setText(getText());
        shell.setLayout(new FormLayout());
        
        Label lblNotificationFrequency = new Label(shell, SWT.NONE);
        lblNotificationFrequency.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        FormData fd_lblNotificationFrequency = new FormData();
        fd_lblNotificationFrequency.top = new FormAttachment(0, 10);
        fd_lblNotificationFrequency.left = new FormAttachment(0, 10);
        lblNotificationFrequency.setLayoutData(fd_lblNotificationFrequency);
        lblNotificationFrequency.setText("Notification Frequency (minutes):");
        
        spinnerNotifFreq = new Spinner(shell, SWT.BORDER);
        spinnerNotifFreq.setMaximum(1440);
        spinnerNotifFreq.setMinimum(1);
        FormData fd_spinnerNotifFreq = new FormData();
        fd_spinnerNotifFreq.top = new FormAttachment(0, 10);
        fd_spinnerNotifFreq.left = new FormAttachment(lblNotificationFrequency, 6);
        spinnerNotifFreq.setLayoutData(fd_spinnerNotifFreq);
        
        Label horizontalSeparator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        FormData fd_horizontalSeparator1 = new FormData();
        fd_horizontalSeparator1.left = new FormAttachment(lblNotificationFrequency, 0, SWT.LEFT);
        fd_horizontalSeparator1.right = new FormAttachment(100, -10);
        horizontalSeparator1.setLayoutData(fd_horizontalSeparator1);
        
        Label lblDeadlineColor = new Label(shell, SWT.NONE);
        fd_horizontalSeparator1.bottom = new FormAttachment(100, -140);
        lblDeadlineColor.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        FormData fd_lblDeadlineColor = new FormData();
        fd_lblDeadlineColor.top = new FormAttachment(horizontalSeparator1, 6);
        fd_lblDeadlineColor.left = new FormAttachment(lblNotificationFrequency, 0, SWT.LEFT);
        lblDeadlineColor.setLayoutData(fd_lblDeadlineColor);
        lblDeadlineColor.setText("Deadline color:");
        
        Label horizontalSeparator2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        FormData fd_horizontalSeparator2 = new FormData();
        fd_horizontalSeparator2.top = new FormAttachment(lblDeadlineColor, 18);
        fd_horizontalSeparator2.left = new FormAttachment(0, 10);
        fd_horizontalSeparator2.right = new FormAttachment(100, -10);
        horizontalSeparator2.setLayoutData(fd_horizontalSeparator2);
        
        Label lblOngoingColor = new Label(shell, SWT.NONE);
        fd_horizontalSeparator2.bottom = new FormAttachment(lblOngoingColor, -6);
        FormData fd_lblOngoingColor = new FormData();
        fd_lblOngoingColor.top = new FormAttachment(0, 147);
        fd_lblOngoingColor.left = new FormAttachment(lblNotificationFrequency, 0, SWT.LEFT);
        lblOngoingColor.setLayoutData(fd_lblOngoingColor);
        lblOngoingColor.setText("Ongoing color:");
        
        toolBarDeadline = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
        FormData fd_toolBarDeadline = new FormData();
        fd_toolBarDeadline.top = new FormAttachment(horizontalSeparator1, 6);
        fd_toolBarDeadline.right = new FormAttachment(spinnerNotifFreq, 0, SWT.RIGHT);
        toolBarDeadline.setLayoutData(fd_toolBarDeadline);
        
        tltmDeadlineRed = new ToolItem(toolBarDeadline, SWT.RADIO);
        tltmDeadlineRed.setText("Red");
        
        tltmDeadlineBlue = new ToolItem(toolBarDeadline, SWT.RADIO);
        tltmDeadlineBlue.setText("Blue");
        
        tltmDeadlineGreen = new ToolItem(toolBarDeadline, SWT.RADIO);
        tltmDeadlineGreen.setText("Green");
        
        tltmDeadlineOrange = new ToolItem(toolBarDeadline, SWT.RADIO);
        tltmDeadlineOrange.setText("Orange");
        
        ToolBar toolBarOngoing = new ToolBar(shell,  SWT.RIGHT);
        FormData fd_toolBarOngoing = new FormData();
        fd_toolBarOngoing.top = new FormAttachment(horizontalSeparator2, 6);
        fd_toolBarOngoing.left = new FormAttachment(lblOngoingColor, 6);
        toolBarOngoing.setLayoutData(fd_toolBarOngoing);
        
        tltmOngoingRed = new ToolItem(toolBarOngoing, SWT.RADIO);
        tltmOngoingRed.setText("Red");
        
        tltmOngoingBlue = new ToolItem(toolBarOngoing, SWT.RADIO);
        tltmOngoingBlue.setText("Blue");
        
        tltmOngoingGreen = new ToolItem(toolBarOngoing, SWT.RADIO);
        tltmOngoingGreen.setText("Green");
        
        tltmOngoingOrange = new ToolItem(toolBarOngoing, SWT.RADIO);
        tltmOngoingOrange.setText("Orange");
        
        btnstartedNotification = new Button(shell, SWT.CHECK);
        FormData fd_btnstartedNotification = new FormData();
        fd_btnstartedNotification.top = new FormAttachment(lblNotificationFrequency, 17);
        fd_btnstartedNotification.left = new FormAttachment(0, 10);
        btnstartedNotification.setLayoutData(fd_btnstartedNotification);
        btnstartedNotification.setText("\"Started\" notification");
        
        btnoverdueNotification = new Button(shell, SWT.CHECK);
        fd_horizontalSeparator1.top = new FormAttachment(btnoverdueNotification, 6);
        FormData fd_btnoverdueNotification = new FormData();
        fd_btnoverdueNotification.top = new FormAttachment(btnstartedNotification, 6);
        fd_btnoverdueNotification.left = new FormAttachment(lblNotificationFrequency, 0, SWT.LEFT);
        btnoverdueNotification.setLayoutData(fd_btnoverdueNotification);
        btnoverdueNotification.setText("\"Overdue\" notification");
    }
    
    //@author A0094558N
    private void displaySettingsInSettingsGUI() {
        spinnerNotifFreq.setSelection(workingStorage.get(SETTINGS_NOTIF_FREQ_INDEX));
        
        int loadedNotifOverdue = workingStorage.get(SETTINGS_NOTIFICATIONS_OVERDUE_INDEX);
        int loadedNotifOngoing = workingStorage.get(SETTINGS_NOTIFICATIONS_ONGOING_INDEX);
        if (loadedNotifOverdue == 1) {
            btnoverdueNotification.setSelection(true);
        } else {
            btnoverdueNotification.setSelection(false);
        }
        
        if (loadedNotifOngoing == 1) {
            btnstartedNotification.setSelection(true);
        } else {
            btnstartedNotification.setSelection(false);
        }
        
        int loadedDeadlineColorR = workingStorage.get(SETTINGS_DEADLINE_COLOR_R_INDEX);
        int loadedDeadlineColorG = workingStorage.get(SETTINGS_DEADLINE_COLOR_G_INDEX);
        int loadedDeadlineColorB = workingStorage.get(SETTINGS_DEADLINE_COLOR_B_INDEX);
        if (loadedDeadlineColorR == COLOR_BLUE_R &&
            loadedDeadlineColorG == COLOR_BLUE_G &&
            loadedDeadlineColorB == COLOR_BLUE_B) {
            tltmDeadlineBlue.setSelection(true);
        } else if (loadedDeadlineColorR == COLOR_RED_R &&
                   loadedDeadlineColorG == COLOR_RED_G &&
                   loadedDeadlineColorB == COLOR_RED_B) {
            tltmDeadlineRed.setSelection(true);
        } else if (loadedDeadlineColorR == COLOR_ORANGE_R &&
                   loadedDeadlineColorG == COLOR_ORANGE_G &&
                   loadedDeadlineColorB == COLOR_ORANGE_B) {
            tltmDeadlineOrange.setSelection(true);
        } else if (loadedDeadlineColorR == COLOR_GREEN_R &&
                   loadedDeadlineColorG == COLOR_GREEN_G &&
                   loadedDeadlineColorB == COLOR_GREEN_B) {
            tltmDeadlineGreen.setSelection(true);
        }
        
        int loadedOngoingColorR = workingStorage.get(SETTINGS_ONGOING_COLOR_R_INDEX);
        int loadedOngoingColorG = workingStorage.get(SETTINGS_ONGOING_COLOR_G_INDEX);
        int loadedOngoingColorB = workingStorage.get(SETTINGS_ONGOING_COLOR_B_INDEX);
        if (loadedOngoingColorR == COLOR_BLUE_R &&
            loadedOngoingColorG == COLOR_BLUE_G &&
            loadedOngoingColorB == COLOR_BLUE_B) {
            tltmOngoingBlue.setSelection(true);
        } else if (loadedOngoingColorR == COLOR_RED_R &&
                   loadedOngoingColorG == COLOR_RED_G &&
                   loadedOngoingColorB == COLOR_RED_B) {
            tltmOngoingRed.setSelection(true);
        } else if (loadedOngoingColorR == COLOR_ORANGE_R &&
                   loadedOngoingColorG == COLOR_ORANGE_G &&
                   loadedOngoingColorB == COLOR_ORANGE_B) {
            tltmOngoingOrange.setSelection(true);
        } else if (loadedOngoingColorR == COLOR_GREEN_R &&
                   loadedOngoingColorG == COLOR_GREEN_G &&
                   loadedOngoingColorB == COLOR_GREEN_B) {
            tltmOngoingGreen.setSelection(true);
        }
    }

    public static void loadGUISettings() {
        initializeVariables();
        createFile(FILENAME);
        readFile(FILENAME);
    }
    
    private static void initializeVariables() {
        settingsFile = new File(FILENAME);
        workingStorage = new ArrayList<Integer>(SETTINGS_TOTAL_NUMBER);
    }
    
    // Checks whether the given file exists, and creates one if it does not
    public static void createFile (String filename) {
        // Get the file path directory
        Path filePath = Paths.get(filename);
        // Look for the file in the filepath, and create it if it does not exist
        if (!Files.exists(filePath)) {
            try {
                settingsFile.createNewFile();
                initializeWorkingStorage();
                writeFile(settingsFile);
            } catch (IOException e) {
                System.out.print(ERROR_CREATE_FILE);
            }
        }
    }
    
    private static void initializeWorkingStorage() {
        for (int i = 0; i < SETTINGS_TOTAL_NUMBER; i++) {
            workingStorage.add(-1);
        }
        workingStorage.set(SETTINGS_NOTIF_FREQ_INDEX, SETTINGS_DEFAULT_NOTIF_FREQ);
        workingStorage.set(SETTINGS_DEADLINE_COLOR_R_INDEX, SETTINGS_DEFAULT_DEADLINE_COLOR_R);
        workingStorage.set(SETTINGS_DEADLINE_COLOR_G_INDEX, SETTINGS_DEFAULT_DEADLINE_COLOR_G);
        workingStorage.set(SETTINGS_DEADLINE_COLOR_B_INDEX, SETTINGS_DEFAULT_DEADLINE_COLOR_B);
        workingStorage.set(SETTINGS_ONGOING_COLOR_R_INDEX, SETTINGS_DEFAULT_ONGOING_COLOR_R);
        workingStorage.set(SETTINGS_ONGOING_COLOR_G_INDEX, SETTINGS_DEFAULT_ONGOING_COLOR_G);
        workingStorage.set(SETTINGS_ONGOING_COLOR_B_INDEX, SETTINGS_DEFAULT_ONGOING_COLOR_B);
        workingStorage.set(SETTINGS_NOTIFICATIONS_ONGOING_INDEX, SETTINGS_DEFAULT_NOTIF_ONGOING);
        workingStorage.set(SETTINGS_NOTIFICATIONS_OVERDUE_INDEX, SETTINGS_DEFAULT_NOTIF_OVERDUE);
    }

    // Reads the contents of the file and stores it in an array list
    private static void readFile (String filename) {
        String temp;
        int i = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(settingsFile));
            // Read the file line by line and add each line
            // into an index of workingStorage
            if (workingStorage.isEmpty()) {
                while ((temp = in.readLine()) != null) {
                    int value = Integer.parseInt(temp);
                    workingStorage.add(value);
                }
            } else {
                while ((temp = in.readLine()) != null) {
                    int value = Integer.parseInt(temp);
                    workingStorage.set(i, value);
                    i++;
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.print(ERROR_READING_FILE);
        }
    }
    
    // Write the contents of the array list into the file
    private static void writeFile (File settingsFile) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(settingsFile));
            // Write all contents of workingStorage to the file
            for (int i = 0; i < workingStorage.size(); i++) {
                out.write(Integer.toString(workingStorage.get(i)));
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
                readSettingsFromDialog();
                writeFile(settingsFile);
                GUI.getSettings();
            }

            private void readSettingsFromDialog() {
                int notificationFreq = Integer.parseInt(spinnerNotifFreq.getText());
                workingStorage.set(SETTINGS_NOTIF_FREQ_INDEX, notificationFreq);
                
                if (btnoverdueNotification.getSelection() == true) {
                    workingStorage.set(SETTINGS_NOTIFICATIONS_OVERDUE_INDEX, 1);
                } else {
                    workingStorage.set(SETTINGS_NOTIFICATIONS_OVERDUE_INDEX, 0);
                }
                
                if (btnstartedNotification.getSelection() == true) {
                    workingStorage.set(SETTINGS_NOTIFICATIONS_ONGOING_INDEX, 1);
                } else {
                    workingStorage.set(SETTINGS_NOTIFICATIONS_ONGOING_INDEX, 0);
                }
                
                if (tltmOngoingRed.getSelection() == true) {
                    workingStorage.set(SETTINGS_ONGOING_COLOR_R_INDEX, COLOR_RED_R);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_G_INDEX, COLOR_RED_G);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_B_INDEX, COLOR_RED_B);
                } else if (tltmOngoingBlue.getSelection() == true) {
                    workingStorage.set(SETTINGS_ONGOING_COLOR_R_INDEX, COLOR_BLUE_R);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_G_INDEX, COLOR_BLUE_G);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_B_INDEX, COLOR_BLUE_B);
                } else if (tltmOngoingOrange.getSelection() == true) {
                    workingStorage.set(SETTINGS_ONGOING_COLOR_R_INDEX, COLOR_ORANGE_R);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_G_INDEX, COLOR_ORANGE_G);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_B_INDEX, COLOR_ORANGE_B);
                } else if (tltmOngoingGreen.getSelection() == true) {
                    workingStorage.set(SETTINGS_ONGOING_COLOR_R_INDEX, COLOR_GREEN_R);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_G_INDEX, COLOR_GREEN_G);
                    workingStorage.set(SETTINGS_ONGOING_COLOR_B_INDEX, COLOR_GREEN_B);
                } 
                
                if (tltmDeadlineRed.getSelection() == true) {
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_R_INDEX, COLOR_RED_R);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_G_INDEX, COLOR_RED_G);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_B_INDEX, COLOR_RED_B);
                } else if (tltmDeadlineBlue.getSelection() == true) {
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_R_INDEX, COLOR_BLUE_R);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_G_INDEX, COLOR_BLUE_G);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_B_INDEX, COLOR_BLUE_B);
                } else if (tltmDeadlineOrange.getSelection() == true) {
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_R_INDEX, COLOR_ORANGE_R);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_G_INDEX, COLOR_ORANGE_G);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_B_INDEX, COLOR_ORANGE_B);
                } else if (tltmDeadlineGreen.getSelection() == true) {
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_R_INDEX, COLOR_GREEN_R);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_G_INDEX, COLOR_GREEN_G);
                    workingStorage.set(SETTINGS_DEADLINE_COLOR_B_INDEX, COLOR_GREEN_B);
                } 
            }
        });
    }
}
