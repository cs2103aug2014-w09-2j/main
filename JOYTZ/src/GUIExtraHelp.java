import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

import swing2swt.layout.BoxLayout;
import swing2swt.layout.FlowLayout;
import swing2swt.layout.BorderLayout;


public class GUIExtraHelp extends Dialog {

    private static final int PAGE_LAST = 3;
    private static final int PAGE_FIRST = 1;
    private static final String PAGE_ONE_TEXT = "How to add tasks:\nYou need to follow this syntax:";
    
    protected Object result;
    protected Shell shell;
    private int currentPage; 

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
        shell.setSize(450, 300);
        shell.setText(getText());
        shell.setLayout(new GridLayout(1, false));
        
        final Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        checkPageAndSetText(currentPage, lblNewLabel);
        
        Button btnBack = new Button(shell, SWT.NONE);
        btnBack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnBack.setText("Back");
        
        Button btnNewButton = new Button(shell, SWT.NONE);
        btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnNewButton.setText("Next");


        btnNewButton.addSelectionListener(new SelectionListener() {

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
    
    private void checkPageAndSetText(int currentPage, Label label) {
        if (currentPage == 1) {
            label.setText(PAGE_ONE_TEXT);
        } else if (currentPage == 2) {
            label.setText("Page " + currentPage);
        } else if (currentPage == 3) {
            label.setText("Page " + currentPage);
        }
    }

}
