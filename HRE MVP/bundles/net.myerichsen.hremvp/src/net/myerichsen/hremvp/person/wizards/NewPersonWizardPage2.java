package net.myerichsen.hremvp.person.wizards;

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
import net.myerichsen.hremvp.person.dialogs.PersonNameStyleNavigatorDialog;
import net.myerichsen.hremvp.person.providers.PersonNameStyleProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Person name wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 24. jan. 2019
 *
 */
public class NewPersonWizardPage2 extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Text textPersonNameStyle;
	private Text textFromDate;
	private Text textToDate;

	private int personNameStylePid;
	private int fromDatePid;
	private int toDatePid;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonWizardPage2(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person name style and dates");
		setDescription(
				"Set the name style for the new person and optionally dates limiting the validity of the name. More names can be added later");
		this.context = context;
	}

	/**
	 *
	 */
	private void browseFromDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(textFromDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textFromDate.setText(hdp.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	protected void browseNameStyles() {
		final PersonNameStyleNavigatorDialog dialog = new PersonNameStyleNavigatorDialog(textPersonNameStyle.getShell(),
				context);
		if (dialog.open() == Window.OK) {
			try {
				final int personNameStylePid = dialog.getPersonNameStylePid();
				final PersonNameStyleProvider pnsp = new PersonNameStyleProvider();
				pnsp.get(personNameStylePid);
				textPersonNameStyle.setText(pnsp.getLabel());
				final NewPersonWizard wizard = (NewPersonWizard) getWizard();
				wizard.setPersonNameStylePid(personNameStylePid);
				setPageComplete(true);
				wizard.addBackPages();
				wizard.getContainer().updateButtons();
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void browseToDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(textToDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textToDate.setText(hdp.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void clearFromDate() {
		textFromDate.setText("");
	}

	/**
	 *
	 */
	protected void clearNameStyles() {
		textPersonNameStyle.setText("");
		setPageComplete(false);
	}

	/**
	 *
	 */
	private void clearToDate() {
		textToDate.setText("");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));

		final Label lblPersonNameStyle = new Label(container, SWT.NONE);
		lblPersonNameStyle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPersonNameStyle.setText("Person Name Style");

		textPersonNameStyle = new Text(container, SWT.BORDER);
		textPersonNameStyle.setEditable(false);
		textPersonNameStyle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		// TODO Bad logic handling next pages
//		try {
//			int defaultStyle = store.getInt("DEFAULTPERSONNAMESTYLE");
//			PersonNameStyleProvider pnsp = new PersonNameStyleProvider();
//			pnsp.get(defaultStyle);
//			textPersonNameStyle.setText(pnsp.getLabel());
//		} catch (SQLException | MvpException e1) {
//			LOGGER.severe(e1.getMessage());
//			e1.printStackTrace();
//		}

		final Composite compositeNameStyle = new Composite(container, SWT.NONE);
		compositeNameStyle.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnBrowseNameStyle = new Button(compositeNameStyle, SWT.NONE);
		btnBrowseNameStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseNameStyles();
			}
		});
		btnBrowseNameStyle.setText("Browse");

		final Button btnClearNameStyle = new Button(compositeNameStyle, SWT.NONE);
		btnClearNameStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearNameStyles();
			}
		});
		btnClearNameStyle.setText("Clear");

		final Label lblFromDate = new Label(container, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(container, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewFromDate();
			}
		});
		btnNewFrom.setText("New");

		final Button btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseFromDates();
			}
		});
		btnBrowseFrom.setText("Browse");

		final Button btnClearFrom = new Button(compositeFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearFromDate();
			}
		});
		btnClearFrom.setText("Clear");

		final Label lblToDate = new Label(container, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(container, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeTo = new Composite(container, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewTo = new Button(compositeTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewToDate();
			}
		});
		btnNewTo.setText("New");

		final Button btnBrowseTo = new Button(compositeTo, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseToDates();
			}
		});
		btnBrowseTo.setText("Browse");

		final Button btnClearTo = new Button(compositeTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearToDate();
			}
		});
		btnClearTo.setText("Clear");

		setPageComplete(false);
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 *
	 */
	private void getNewFromDate() {
		final DateDialog dialog = new DateDialog(textFromDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				fromDatePid = hdp.insert();
				textFromDate.setText(dialog.getLocalDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void getNewToDate() {
		final DateDialog dialog = new DateDialog(textToDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				toDatePid = hdp.insert();
				textToDate.setText(dialog.getLocalDate().toString());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
			}
		}
	}

	/**
	 * @return the personNameStylePid
	 */
	public int getPersonNameStylePid() {
		return personNameStylePid;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param personNameStylePid the personNameStylePid to set
	 */
	public void setPersonNameStylePid(int personNameStylePid) {
		this.personNameStylePid = personNameStylePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}
}
