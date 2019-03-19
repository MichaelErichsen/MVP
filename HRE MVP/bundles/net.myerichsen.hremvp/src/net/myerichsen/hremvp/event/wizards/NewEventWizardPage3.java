package net.myerichsen.hremvp.event.wizards;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author  Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 19. mar. 2019
 *
 */
public class NewEventWizardPage3 extends WizardPage {
	private Text textPerson;
	private Text textRole;

	public NewEventWizardPage3(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Event");
		setDescription("Enter an optional person for the event. More can be added later");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblPerson = new Label(container, SWT.NONE);
		lblPerson.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPerson.setText("Person");
		
		textPerson = new Text(container, SWT.BORDER);
		textPerson.setEditable(false);
		textPerson.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		composite.setBounds(0, 0, 85, 54);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button btnNew = new Button(composite, SWT.NONE);
		btnNew.setText("New");
		
		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse");
		
		Button btnClear = new Button(composite, SWT.NONE);
		btnClear.setText("Clear");
		
		Label lblRole = new Label(container, SWT.NONE);
		lblRole.setText("Role");
		
		textRole = new Text(container, SWT.BORDER);
		textRole.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		
		Button btnPrimaryperson = new Button(container, SWT.CHECK);
		btnPrimaryperson.setSelection(true);
		btnPrimaryperson.setText("PrimaryPerson");
	}
}
