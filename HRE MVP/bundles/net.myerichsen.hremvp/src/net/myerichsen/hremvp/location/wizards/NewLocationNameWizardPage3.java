package net.myerichsen.hremvp.location.wizards;

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

/**
 * New location name wizard page 3
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 16. mar. 2019
 *
 */
public class NewLocationNameWizardPage3 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Text textFromDate;
	private Text textFromDateSort;
	private Text textFromOriginal;
	private Text textFromSurety;

	private Text textToDate;
	private Text textToDateSort;
	private Text textToOriginal;
	private Text textToSurety;

	private int fromDatePid;
	private int toDatePid;

	/**
	 * Constructor
	 *
	 * @param context
	 *
	 */
	public NewLocationNameWizardPage3(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Location Name");
		setDescription(
				"Enter optional time limitation for the location name.\r\n");
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
		container.setLayout(new GridLayout(3, false));

		final Label lblFromDate = new Label(container, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(container, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textFromDateSort = new Text(container, SWT.BORDER);
		textFromDateSort.setEditable(false);
		textFromDateSort.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		textFromOriginal = new Text(container, SWT.BORDER);
		textFromOriginal.setEditable(false);
		textFromOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textFromSurety = new Text(container, SWT.BORDER);
		textFromSurety.setEditable(false);
		textFromSurety.setLayoutData(
				new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						fromDatePid = hdp.insert();
						textFromDate.setText(dialog.getLocalDate().toString());
						if (textFromDateSort.getText().length() == 0) {
							textFromDateSort
									.setText(dialog.getSortDate().toString());
						}
						textFromOriginal.setText(dialog.getOriginal());
						textFromSurety.setText(dialog.getSurety());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewFrom.setText("New");

		Button btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textFromDate.setText(hdp.getDate().toString());
						textFromDateSort.setText(hdp.getSortDate().toString());
						textFromOriginal.setText(hdp.getOriginalText());
						textFromSurety.setText(hdp.getSurety());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseFrom.setText("Browse");

		Button btnClearFrom = new Button(compositeFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textFromDate.setText("");
				textFromDateSort.setText("");
				textFromOriginal.setText("");
				textFromSurety.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		final Label lblToDate = new Label(container, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(container, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textToDateSort = new Text(container, SWT.BORDER);
		textToDateSort.setEditable(false);
		textToDateSort.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		textToOriginal = new Text(container, SWT.BORDER);
		textToOriginal.setEditable(false);
		textToOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textToSurety = new Text(container, SWT.BORDER);
		textToSurety.setEditable(false);
		textToSurety.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Composite compositeTo = new Composite(container, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeTo.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		Button btnCopyFromTo = new Button(compositeTo, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText(textFromDate.getText());
				if (textToDateSort.getText().length() == 0) {
					textToDateSort.setText(textFromDateSort.getText());
				}
				textToOriginal.setText(textFromOriginal.getText());
				textToSurety.setText(textToSurety.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		Button btnNewTo = new Button(compositeTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textToDate.getShell(),
						context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						toDatePid = hdp.insert();
						textToDate.setText(dialog.getLocalDate().toString());
						textToDateSort.setText(dialog.getSortDate().toString());
						textToOriginal.setText(dialog.getOriginal());
						textToSurety.setText(dialog.getSurety());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
					}
				}
			}
		});
		btnNewTo.setText("New");

		Button btnBrowseTo = new Button(compositeTo, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textToDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textToDate.setText(hdp.getDate().toString());
						textToDateSort.setText(hdp.getSortDate().toString());
						textToOriginal.setText(hdp.getOriginalText());
						textToSurety.setText(hdp.getSurety());
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseTo.setText("Browse");

		Button btnClearTo = new Button(compositeTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText("");
				textToDateSort.setText("");
				textToOriginal.setText("");
				textToSurety.setText("");
			}
		});
		btnClearTo.setText("Clear");
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
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
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}
}
