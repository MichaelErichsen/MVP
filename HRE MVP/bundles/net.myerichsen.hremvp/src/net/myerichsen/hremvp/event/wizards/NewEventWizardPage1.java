package net.myerichsen.hremvp.event.wizards;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.providers.HDateProvider;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;

/**
 * Base location data wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. mar. 2019
 *
 */
public class NewEventWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private NewEventWizard wizard;
	private Text textEventName;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse context
	 *
	 */
	public NewEventWizardPage1(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Event");
		setDescription(
				"Create a new event by entering immutable data for it\r\n");
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets. Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		GridLayout gl_container = new GridLayout(2, false);
		container.setLayout(gl_container);
		
		Label lblEventType = new Label(container, SWT.NONE);
		lblEventType.setText("Event Type");
		
		ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
		Combo comboEventType = comboViewer.getCombo();
		comboEventType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite compositeRole = new Composite(container, SWT.BORDER);
		compositeRole.setLayout(new GridLayout(1, false));
		compositeRole.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Label lblEventName = new Label(container, SWT.NONE);
		lblEventName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEventName.setText("Event Name");
		
		textEventName = new Text(container, SWT.BORDER);
		textEventName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblIsoCode = new Label(container, SWT.NONE);
		lblIsoCode.setText("ISO Code");
		
		ComboViewer comboViewer_1 = new ComboViewer(container, SWT.NONE);
		Combo comboIsoCode = comboViewer_1.getCombo();
		comboIsoCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.BORDER);
		compositeFrom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		compositeFrom.setLayout(new GridLayout(2, false));
		final Label lblFromDate = new Label(compositeFrom, SWT.NONE);
		lblFromDate.setText("From Date");

		Text textFromDate = new Text(compositeFrom, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFromButtons = new Composite(compositeFrom,
				SWT.NONE);
		compositeFromButtons.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeFromButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		Button btnNewFrom = new Button(compositeFromButtons, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						wizard = (NewEventWizard) getWizard();
						wizard.setFromDatePid(hdp.insert());
						textFromDate.setText(dialog.getDate().toString());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewFrom.setText("New");

		Button btnBrowseFrom = new Button(compositeFromButtons, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						wizard = (NewEventWizard) getWizard();
						wizard.setFromDatePid(hdatePid);
						textFromDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseFrom.setText("Browse");

		Button btnClearFrom = new Button(compositeFromButtons, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
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
				wizard.setFromDatePid(0);
				textFromDate.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		final Composite compositeTo = new Composite(container, SWT.BORDER);
		compositeTo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		compositeTo.setLayout(new GridLayout(2, false));

		final Label lblToDate = new Label(compositeTo, SWT.NONE);
		lblToDate.setText("To Date");

		Text textToDate = new Text(compositeTo, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeToButtons = new Composite(compositeTo,
				SWT.NONE);
		compositeToButtons.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeToButtons.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		Button btnCopyFromTo = new Button(compositeToButtons, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
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
				wizard.setToDatePid(wizard.getFromDatePid());
				textToDate.setText(textFromDate.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		Button btnNewTo = new Button(compositeToButtons, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textToDate.getShell(),
						context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						wizard = (NewEventWizard) getWizard();
						wizard.setToDatePid(hdp.insert());
						textToDate.setText(dialog.getDate().toString());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
					}
				}
			}
		});
		btnNewTo.setText("New");

		Button btnBrowseTo = new Button(compositeToButtons, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textToDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						wizard = (NewEventWizard) getWizard();
						wizard.setToDatePid(hdatePid);
						textToDate.setText(hdp.getDate().toString());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseTo.setText("Browse");

		Button btnClearTo = new Button(compositeToButtons, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
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
				wizard.setToDatePid(0);
				textToDate.setText("");
			}
		});
		btnClearTo.setText("Clear");
	}

}
