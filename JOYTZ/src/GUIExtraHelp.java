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

public class GUIExtraHelp extends Dialog {

    private static final int PAGE_LAST = 3;
    private static final int PAGE_FIRST = 1;
    private static final String PAGE_ONE_TEXT = "Welcome to JOYTZ task manager!\n\nHere, you will be " +
                                                "brought through a step by step guide on the available " +
                                                "functions that are available.\n\nTo come back to this " +
                                                "tutorial next time, type \"tutorial\". If you need a " +
                                                "quick help, just type \"help\".\n\nClick \"next\" to " +
                                                "continue.";
    private static final String PAGE_TWO_TEXT = "How to add tasks:\nJOYTZ allows you to add many attributes " +
                                                "to your tasks. The available attributes are:\n" +
                                                "    - Start time\n    - End time\n    - Name\n" +
                                                "    - Description\n    - Location\n    - Priority\n\n" +
                                                "To add a task, simply follow this syntax:\n" +
                                                "add~name~description~start time~end time~location~priority\n\n" +
                                                "Not all attributes have to be filled when adding a task. " +
                                                "For example, to only add the task's name, type:\n" +
                                                "add~name\n\nTo skip an attribute in the middle of the " +
                                                "command, for example to name the name and priority, type:\n" +
                                                "add~name~~~~~priority\n\n The syntax needed for the time is:\n" +
                                                "DD/MM/YYYY HH:MMxx, xx being am or pm.\n\nThe priorities " +
                                                "allowed are:\n    - high\n    - medium\n    - low";
    private static final String PAGE_THREE_TEXT = "How to edit tasks:\nYou need to follow this syntax:";
    private static final String PAGE_FOUR_TEXT = "How to delete tasks:\nYou need to follow this syntax:";
    private static final String PAGE_FIVE_TEXT = "How to clear tasks:\nYou need to follow this syntax:";
    private static final String PAGE_SIX_TEXT = "How to undo/redo:\nYou need to follow this syntax:";
    private static final String PAGE_SEVEN_TEXT = "How to sort tasks:\nYou need to follow this syntax:";
    private static final String PAGE_EIGHT_TEXT = "How to search for tasks:\nYou need to follow this syntax:";
    
    
    protected Object result;
    protected Shell shell;
    private int currentPage; 
    private Button btnBack;
    private Button btnNext;

    /**
     * Create the dialog.
     * @param parent
     * @param style
     */
    public GUIExtraHelp(Shell parent, int style) {
        super(parent, style);
        setText("Help");
        currentPage = 1;
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
        shell.setSize(450, 330);
        shell.setText(getText());
        shell.setLayout(new GridLayout(1, false));
        
        final StyledText lblNewLabel = new StyledText(shell, SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
        lblNewLabel.setEditable(false);
        lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        checkPageAndSetText(currentPage, lblNewLabel);
        
        btnBack = new Button(shell, SWT.NONE);
        btnBack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnBack.setText("Back");
        
        btnNext = new Button(shell, SWT.NONE);
        btnNext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnNext.setText("Next");


        btnNext.addSelectionListener(new SelectionListener() {

          public void widgetSelected(SelectionEvent event) {
              if (currentPage < PAGE_LAST) {
                  currentPage += 1;
              }
              checkPageAndSetText(currentPage, lblNewLabel);
          }

          public void widgetDefaultSelected(SelectionEvent event) {
              lblNewLabel.setText("Dummy!");
          }
        });
        
        btnBack.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent event) {
                if (currentPage > PAGE_FIRST) {
                    currentPage -= 1;
                }
                checkPageAndSetText(currentPage, lblNewLabel);
            }

            public void widgetDefaultSelected(SelectionEvent event) {
                lblNewLabel.setText("Dummy!");
            }
          });
    }
    
    private void checkPageAndSetText(int currentPage, StyledText label) {
        switch(currentPage) {
        case 1:
            label.setText(PAGE_ONE_TEXT);
            break;
        case 2:
            label.setText(PAGE_TWO_TEXT);
            break;
        case 3:
            label.setText(PAGE_THREE_TEXT);
            break;
        case 4:
            label.setText(PAGE_FOUR_TEXT);
            break;
        case 5:
            label.setText(PAGE_FIVE_TEXT);
            break;
        case 6:
            label.setText(PAGE_SIX_TEXT);
            break;
        case 7:
            label.setText(PAGE_SEVEN_TEXT);
            break;
        }

    }

}
