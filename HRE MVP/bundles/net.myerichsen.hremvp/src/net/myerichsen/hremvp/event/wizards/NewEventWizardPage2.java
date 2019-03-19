package net.myerichsen.hremvp.event.wizards;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;

/**
 * @author  Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 19. mar. 2019
 *
 */
public class NewEventWizardPage2 extends WizardPage {
	private Text textLocation;

	public NewEventWizardPage2(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Event");
		setDescription("Enter an optional location for the event. More can be added later");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblLocation = new Label(container, SWT.NONE);
		lblLocation.setText("Location");
		
		textLocation = new Text(container, SWT.BORDER);
		textLocation.setEditable(false);
		textLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		composite.setBounds(0, 0, 64, 64);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button btnNew = new Button(composite, SWT.NONE);
		btnNew.setText("New");
		
		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse");
		
		Button btnClear = new Button(composite, SWT.NONE);
		btnClear.setText("Clear");
		
		Button btnPrimaryLocation = new Button(container, SWT.CHECK);
		btnPrimaryLocation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPrimaryLocation.setSelection(true);
		btnPrimaryLocation.setText("Primary Location");
	}
}
