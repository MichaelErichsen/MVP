package net.myerichsen.hremvp.event.wizards;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;

/**
 * Wizard page to add a person to an event
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 20. mar. 2019
 *
 */
public class NewEventWizardPage3 extends WizardPage {
	private Text textPerson;

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
		container.setLayout(new GridLayout(1, false));

		Composite compositePerson = new Composite(container, SWT.BORDER);
		GridData gd_compositePerson = new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1);
		gd_compositePerson.widthHint = 560;
		compositePerson.setLayoutData(gd_compositePerson);
		compositePerson.setLayout(new GridLayout(2, false));

		Label lblPerson = new Label(compositePerson, SWT.NONE);
		lblPerson.setText("Person");

		textPerson = new Text(compositePerson, SWT.BORDER);
		textPerson.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textPerson.setEditable(false);

		Composite compositePersonButtons = new Composite(compositePerson,
				SWT.NONE);
		compositePersonButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositePersonButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNewPerson = new Button(compositePersonButtons, SWT.NONE);
		btnNewPerson.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnNewPerson.setText("New");

		Button btnBrowsePerson = new Button(compositePersonButtons, SWT.NONE);
		btnBrowsePerson.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnBrowsePerson.setText("Browse");

		Button btnClearPerson = new Button(compositePersonButtons, SWT.NONE);
		btnClearPerson.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		btnClearPerson.setText("Clear");

		Composite compositeRole = new Composite(container, SWT.BORDER);
		GridData gd_compositeRole = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1);
		gd_compositeRole.widthHint = 558;
		compositeRole.setLayoutData(gd_compositeRole);
		compositeRole.setLayout(new GridLayout(2, false));

		Label label = new Label(compositeRole, SWT.NONE);
		label.setText("Event Role");
		
		ComboViewer comboViewerRole = new ComboViewer(compositeRole, SWT.NONE);
		Combo comboRole = comboViewerRole.getCombo();
		comboRole.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnPrimaryperson = new Button(container, SWT.CHECK);
		btnPrimaryperson.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.
			 * events.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		btnPrimaryperson.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPrimaryperson.setSelection(true);
		btnPrimaryperson.setText("Primary Person");

		Button btnCheckButton = new Button(container, SWT.CHECK);
		btnCheckButton.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.
			 * events.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		btnCheckButton.setSelection(true);
		btnCheckButton.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnCheckButton.setText("Primary Event");
	}
}
