package net.myerichsen.hremvp.event.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.window.Window;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.person.dialogs.PersonDialog;
import net.myerichsen.hremvp.person.dialogs.PersonNavigatorDialog;

/**
 * Wizard page to add a person to an event
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 21. mar. 2019
 *
 */
public class NewEventWizardPage3 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private NewEventWizard wizard;
	private Text textPerson;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewEventWizardPage3(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Event");
		setDescription(
				"Enter an optional person for the event. More can be added later");
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		final Composite compositePerson = new Composite(container, SWT.BORDER);
		final GridData gd_compositePerson = new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 1, 1);
		gd_compositePerson.widthHint = 559;
		compositePerson.setLayoutData(gd_compositePerson);
		compositePerson.setLayout(new GridLayout(2, false));

		final Label lblPerson = new Label(compositePerson, SWT.NONE);
		lblPerson.setText("Person");

		textPerson = new Text(compositePerson, SWT.BORDER);
		textPerson.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textPerson.setEditable(false);

		final Composite compositePersonButtons = new Composite(compositePerson,
				SWT.NONE);
		compositePersonButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositePersonButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewPerson = new Button(compositePersonButtons,
				SWT.NONE);
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
				final PersonDialog dialog = new PersonDialog(getShell(),
						context);

				if (dialog.open() == Window.OK) {
					// TODO
					setErrorMessage("Aargh ralle");
				}
			}
		});
		btnNewPerson.setText("New");

		final Button btnBrowsePerson = new Button(compositePersonButtons,
				SWT.NONE);
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
				final PersonNavigatorDialog dialog = new PersonNavigatorDialog(
						getShell(), context);

				if (dialog.open() == Window.OK) {
					final int personPid = dialog.getPersonPid();
					wizard = (NewEventWizard) getWizard();
					wizard.setPersonPid(personPid);
					textPerson.setText(dialog.getPersonName());
					setErrorMessage(null);
				}
			}
		});
		btnBrowsePerson.setText("Browse");

		final Button btnClearPerson = new Button(compositePersonButtons,
				SWT.NONE);
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
				wizard = (NewEventWizard) getWizard();
				wizard.setPersonPid(0);
				textPerson.setText("");
				setErrorMessage(null);
			}
		});
		btnClearPerson.setText("Clear");

		final Composite compositeRole = new Composite(container, SWT.BORDER);
		compositeRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		compositeRole.setLayout(new GridLayout(2, false));

		final Label label = new Label(compositeRole, SWT.NONE);
		label.setText("Event Role");

		final ComboViewer comboViewerRole = new ComboViewer(compositeRole,
				SWT.NONE);
		final Combo comboRole = comboViewerRole.getCombo();
		comboRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Button btnPrimaryperson = new Button(container, SWT.CHECK);
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
				wizard = (NewEventWizard) getWizard();
				wizard.setIsPrimaryPerson(btnPrimaryperson.getSelection());
			}
		});
		btnPrimaryperson.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPrimaryperson.setSelection(true);
		btnPrimaryperson.setText("Primary Person");

		final Button btnPrimaryPersonEvent = new Button(container, SWT.CHECK);
		btnPrimaryPersonEvent.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.
			 * events.FocusEvent)
			 */
			@Override
			public void focusLost(FocusEvent e) {
				wizard = (NewEventWizard) getWizard();
				wizard.setIsPrimaryPersonEvent(
						btnPrimaryPersonEvent.getSelection());
			}
		});
		btnPrimaryPersonEvent.setSelection(true);
		btnPrimaryPersonEvent.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPrimaryPersonEvent.setText("Primary Event");
	}
}
