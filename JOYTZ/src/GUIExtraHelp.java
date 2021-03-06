//@author A0094558N
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;

public class GUIExtraHelp extends Dialog {
    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());
    
    private static final int PAGE_LAST = 10;
    private static final int PAGE_FIRST = 1;
    private static final String PAGE_ONE = "Welcome to JOYTZ task manager!\n\nHere, you will be " +
                                           "brought through a step by step guide on the available " +
                                           "functions that are available.\n\nTo come back to this " +
                                           "tutorial next time, type \"tutorial\". If you need a " +
                                           "quick help, just type \"help\".\n\n In general, the commands used " +
                                           "for input is usually first preceded by the action command, " +
                                           "followed by the necessary attributes needed for the action.\n\n" +
                                           "Click \"Next\" to continue.";
    private static final String PAGE_TWO = "How to add tasks:\nJOYTZ allows you to add many attributes " +
                                           "to your tasks. The available attributes are:\n" +
                                           "    - Start time\n    - End time\n    - Name\n" +
                                           "    - Description\n    - Location\n    - Priority\n\n" +
                                           "To add a task, simply follow this syntax (without typing " +
                                           "the \"<\" and \">\"):\n" +
                                           "add <name>; <description> from <start time> to <end time> " +
                                           "@<location> #<priority>\n\n" +
                                           "Not all attributes have to be filled when adding a task. " +
                                           "For example, to only add the task's name, type:\n" +
                                           "add <name>\n\nThe syntax needed for the time is:\n" +
                                           "DD/MM/YYYY HH:MMxx, xx being am or pm.\n\nThe priorities " +
                                           "allowed are:\n    - important\n    - medium\n    - low";
    private static final String PAGE_THREE = "How to edit tasks:\nYou can also edit tasks that have " +
                                             "been added. The syntax is (without typing " +
                                             "the \"<\" and \">\"):\n" +
                                             "update <index> <attribute> <new data>\n\n" +
                                             "    - The index can be found " +
                                             "on the first column of each task.\n    - The attribute " +
                                             "corresponds to the attribute that you want to change. " +
                                             "These attributes can be easily referenced from the table " +
                                             "headings.\n    - The new data is the what you want to update " +
                                             "the current entry to.";
    private static final String PAGE_FOUR = "How to delete tasks:\nThe syntax is (without typing " +
                                            "the \"<\" and \">\"):\ndelete <index>\n\n" +
                                            "The index can be found on the first column of each task.";
    private static final String PAGE_FIVE = "How to clear tasks:\nThe syntax is:\nclear\n\n" +
                                            "WARNING: This command deletes everything in your list.";
    private static final String PAGE_SIX = "How to undo/redo:\nTo undo, type:\nundo\n\nTo redo, type:\n" +
                                           "redo\n\nNote: You are able to perform multiple undo and redo.";
    private static final String PAGE_SEVEN = "How to sort tasks:\nThe syntax is (without typing " +
                                             "the \"<\" and \">\"):\nsort <attribute>\n\n" +
                                             "The attribute can be easily referenced from the table headings.\n\n" +
                                             "You can also sort using multiple attributes. For example, to sort " +
                                             "using priority, then the end date:\n" +
                                             "\"sort priority end\".";
    private static final String PAGE_EIGHT = "How to search for tasks:\nThe syntax is (without typing " +
                                             "the \"<\" and \">\"):\n" +
                                             "search <attribute> <search for>\n\n" +
                                             "The attribute to search for can be referenced from the table " +
                                             "headings.\n\nYou can also search for multiple items. For example:\n" +
                                             "search <attribute1> <search for1>; <attribute2> <search for 2>\n\n" +
                                             "Note: searching is case-insensitive.\nAlso, " +                                                   
                                             "to bring back all your tasks in view, type \"display\"";
    private static final String PAGE_NINE = "How to display all tasks:\nThe syntax is:\ndisplay\n\n" +
                                            "After searching, you are not shown all your tasks. Using " +
                                            "this command brings all your tasks back up.";
    private static final String PAGE_TEN = "Extra features:\n\nThis tutorial can be brought back by " +
                                           "typing \"tutorial\".\nTo view a quick list of the command " +
                                           "syntax, type \"help\".\n\nJOYTZ also features keyboard " +
                                           "shortcuts to maximize and minimize the application. " +
                                           "To maximize the application, press \"ALT+A\". To " +
                                           "minimize the application, press \"ALT+Z\".\n\nColor-coded " +
                                           "tasks are also featured. By default, tasks in red have " +
                                           "passed the deadline, and those in green are still ongoing. " +
                                           "All remaining tasks are in black.\n\nTo change these colors " +
                                           "or change the frequency of notifications, type \"settings\".\n\n" +
                                           "To begin using JOYTZ, close this window!";
    
    
    protected Object result;
    protected Shell shell;
    private int currentPage; 
    private Button btnBack;
    private Button btnNext;
    private StyledText textField;
    
    //@author generated
    /**
     * Create the dialog.
     * @param parent
     * @param style
     */
    public GUIExtraHelp(Shell parent, int style) {
        super(parent, style);
        setText("Tutorial");
        currentPage = 1;
    }
    
    /**
     * Open the dialog.
     * @return the result
     */
    public Object open() {
        createContents();
        createButtonListeners();
        checkPageAndSetText(currentPage, textField);
        
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
        shell.setMinimumSize(new Point(300, 400));
        shell.setSize(450, 330);
        shell.setText(getText());
        shell.setLayout(new GridLayout(1, false));
        
        textField = new StyledText(shell, SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
        textField.setEditable(false);
        textField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        
        btnBack = new Button(shell, SWT.NONE);
        btnBack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnBack.setText("Back");
        
        btnNext = new Button(shell, SWT.NONE);
        btnNext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnNext.setText("Next");
    }
    
    //@author A0094558N
    private void createButtonListeners() {
        btnNext.addSelectionListener(new SelectionListener() {

          public void widgetSelected(SelectionEvent event) {
              if (currentPage < PAGE_LAST) {
                  currentPage += 1;
              }
              checkPageAndSetText(currentPage, textField);
          }

          public void widgetDefaultSelected(SelectionEvent event) {
              textField.setText("Dummy!");
          }
        });
        
        btnBack.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent event) {
                if (currentPage > PAGE_FIRST) {
                    currentPage -= 1;
                }
                checkPageAndSetText(currentPage, textField);
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                textField.setText("Dummy!");
            }
          });
    }
    
    private void checkPageAndSetText(int currentPage, StyledText label) {
        assert currentPage <= PAGE_LAST;
        assert currentPage >= PAGE_FIRST;
        
        LOGGER.info("==============\n" +
                "Current tutorial page: " + currentPage + "\n" + 
                "====================\n");
        
        switch(currentPage) {
            case 1:
                label.setText(PAGE_ONE);
                shell.setText("Tutorial");
                btnBack.setEnabled(false);
                break;
                
            case 2:
                label.setText(PAGE_TWO);
                shell.setText("Tutorial: Add");
                btnBack.setEnabled(true);
                break;
                
            case 3:
                label.setText(PAGE_THREE);
                shell.setText("Tutorial: Edit");
                break;
                
            case 4:
                label.setText(PAGE_FOUR);
                shell.setText("Tutorial: Delete");
                break;
                
            case 5:
                label.setText(PAGE_FIVE);
                shell.setText("Tutorial: Clear");
                break;
                
            case 6:
                label.setText(PAGE_SIX);
                shell.setText("Tutorial: Undo/Redo");
                break;
                
            case 7:
                label.setText(PAGE_SEVEN);
                shell.setText("Tutorial: Sort");
                break;
                
            case 8:
                label.setText(PAGE_EIGHT);
                shell.setText("Tutorial: Search");
                break;
                
            case 9:
                label.setText(PAGE_NINE);
                shell.setText("Tutorial: Display");
                btnNext.setEnabled(true);
                break;
                
            case 10:
                label.setText(PAGE_TEN);
                shell.setText("Tutorial: Extra Features");
                btnNext.setEnabled(false);
                break;
                
            default:
                break;
        }
    }
}
