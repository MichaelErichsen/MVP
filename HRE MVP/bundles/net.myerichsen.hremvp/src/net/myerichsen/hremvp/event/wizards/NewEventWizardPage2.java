package net.myerichsen.hremvp.event.wizards;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
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

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.location.dialogs.LocationNavigatorDialog;
import net.myerichsen.hremvp.location.providers.LocationProvider;
import net.myerichsen.hremvp.location.wizards.NewLocationWizard;

/**
 * Wizard page to add a location for an event
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 23. mar. 2019
 *
 */
public class NewEventWizardPage2 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private NewEventWizard wizard;
	private Text textLocation;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewEventWizardPage2(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Event");
		setDescription(
				"Enter an optional location for the event. More can be added later");
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

		final Composite compositeLocation = new Composite(container,
				SWT.BORDER);
		compositeLocation.setLayout(new GridLayout(2, false));
		compositeLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblLocation = new Label(compositeLocation, SWT.NONE);
		lblLocation.setText("Location");

		textLocation = new Text(compositeLocation, SWT.BORDER);
		textLocation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textLocation.setEditable(false);

		final Composite compositeButtons = new Composite(compositeLocation,
				SWT.NONE);
		compositeButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNew = new Button(compositeButtons, SWT.NONE);
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
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewLocationWizard(context));
				dialog.open();
			}
		});
		btnNew.setText("New");

		final Button btnBrowse = new Button(compositeButtons, SWT.NONE);
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
				final LocationNavigatorDialog dialog = new LocationNavigatorDialog(
						getShell(), context);

				if (dialog.open() == Window.OK) {
					try {
						final int locationPid = dialog.getLocationPid();
						wizard = (NewEventWizard) getWizard();
						wizard.setLocationPid(locationPid);
						textLocation.setText(dialog.getLocationName());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse.setText("Browse");

		final Button btnClear = new Button(compositeButtons, SWT.NONE);
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
				wizard = (NewEventWizard) getWizard();
				wizard.setLocationPid(0);
				textLocation.setText("");
			}
		});
		btnClear.setText("Clear");

		final Button btnPrimaryLocation = new Button(container, SWT.CHECK);
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

		final Button btnPrimaryEvent = new Button(container, SWT.CHECK);
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

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribeLocationPidUpdateTopic(
			@UIEventTopic(Constants.LOCATION_PID_UPDATE_TOPIC) int locationPid) {
		LOGGER.fine("Received location id " + locationPid);

		if (locationPid > 0) {
			try {
				wizard = (NewEventWizard) getWizard();
				wizard.setLocationPid(locationPid);
				final LocationProvider provider = new LocationProvider();
				provider.get(locationPid);
				textLocation.setText(provider.getPrimaryName());
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}

		}
	}
}
