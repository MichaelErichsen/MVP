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

/**
 * Wizard page to add a location for an event
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 20. mar. 2019
 *
 */
public class NewEventWizardPage2 extends WizardPage {
	private Text textLocation;

	public NewEventWizardPage2(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Event");
		setDescription(
				"Enter an optional location for the event. More can be added later");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Composite compositeLocation = new Composite(container, SWT.BORDER);
		compositeLocation.setLayout(new GridLayout(2, false));
		compositeLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblLocation = new Label(compositeLocation, SWT.NONE);
		lblLocation.setText("Location");

		textLocation = new Text(compositeLocation, SWT.BORDER);
		textLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textLocation.setEditable(false);

		Composite compositeButtons = new Composite(compositeLocation, SWT.NONE);
		compositeButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNew = new Button(compositeButtons, SWT.NONE);
		btnNew.addMouseListener(new MouseAdapter() {
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
		btnNew.setText("New");

		Button btnBrowse = new Button(compositeButtons, SWT.NONE);
		btnBrowse.addMouseListener(new MouseAdapter() {
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
		btnBrowse.setText("Browse");

		Button btnClear = new Button(compositeButtons, SWT.NONE);
		btnClear.addMouseListener(new MouseAdapter() {
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
		btnClear.setText("Clear");

		Button btnPrimaryLocation = new Button(container, SWT.CHECK);
		btnPrimaryLocation.addFocusListener(new FocusAdapter() {
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
		btnPrimaryLocation.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPrimaryLocation.setSelection(true);
		btnPrimaryLocation.setText("Primary Location");

		Button btnPrimaryEvent = new Button(container, SWT.CHECK);
		btnPrimaryEvent.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPrimaryEvent.addFocusListener(new FocusAdapter() {
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
		btnPrimaryEvent.setSelection(true);
		btnPrimaryEvent.setText("Primary Event");
	}
}
