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
 * Wizard page to add a person to an event
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 20. mar. 2019
 *
 */
public class NewEventWizardPage3 extends WizardPage {
	private Text textPerson;
	private Text textEventRole;

	public NewEventWizardPage3(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Event");
		setDescription(
				"Enter an optional person for the event. More can be added later");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Composite compositePerson = new Composite(container, SWT.BORDER);
		GridData gd_compositePerson = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_compositePerson.widthHint = 560;
		compositePerson.setLayoutData(gd_compositePerson);
		compositePerson.setLayout(new GridLayout(1, false));

		Label lblPerson = new Label(compositePerson, SWT.NONE);
		lblPerson.setSize(36, 15);
		lblPerson.setText("Person");
		
				textPerson = new Text(compositePerson, SWT.BORDER);
				textPerson.setSize(541, 21);
				textPerson.setEditable(false);

		Composite compositePersonButtones = new Composite(compositePerson, SWT.NONE);
		compositePersonButtones.setSize(137, 31);
		compositePersonButtones.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNewPerson = new Button(compositePersonButtones, SWT.NONE);
		btnNewPerson.setText("New");

		Button btnBrowsePerson = new Button(compositePersonButtones, SWT.NONE);
		btnBrowsePerson.setText("Browse");

		Button btnClearPerson = new Button(compositePersonButtones, SWT.NONE);
		btnClearPerson.setText("Clear");
		btnNewPerson.setText("New");
		btnBrowsePerson.setText("Browse");
		btnClearPerson.setText("Clear");

		Composite compositeRole = new Composite(container, SWT.BORDER);
		GridData gd_compositeRole = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1);
		gd_compositeRole.widthHint = 558;
		compositeRole.setLayoutData(gd_compositeRole);
		compositeRole.setLayout(new GridLayout(2, false));

		Label label = new Label(compositeRole, SWT.NONE);
		label.setText("Event Role");

		textEventRole = new Text(compositeRole, SWT.BORDER);
		textEventRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite compositeRoleButtons = new Composite(compositeRole, SWT.NONE);
		compositeRoleButtons.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeRoleButtons.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Button btnNewRole = new Button(compositeRoleButtons, SWT.NONE);

		Button btnBrowseRole = new Button(compositeRoleButtons, SWT.NONE);

		Button btnClearRole = new Button(compositeRoleButtons, SWT.NONE);

		Button btnPrimaryperson = new Button(container, SWT.CHECK);
		btnPrimaryperson.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPrimaryperson.setSelection(true);
		btnPrimaryperson.setText("Primary Person");

		Button btnCheckButton = new Button(container, SWT.CHECK);
		btnCheckButton.setSelection(true);
		btnCheckButton.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnCheckButton.setText("Primary Event");
	}
}
