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
import net.myerichsen.hremvp.listeners.DoubleListener;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Base location data wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. nov. 2018
 *
 */
public class NewEventWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Text textFromDate;
	private Text textFromDateSort;
	private Text textFromOriginal;
	private Text textFromSurety;
	private Button btnNewFrom;
	private Button btnBrowseFrom;
	private Button btnClearFrom;

	private Text textToDate;
	private Text textToDateSort;
	private Text textToOriginal;
	private Text textToSurety;
	private Button btnCopyFromTo;
	private Button btnNewTo;
	private Button btnBrowseTo;
	private Button btnClearTo;

	private Text textXCoordinate;
	private Text textYCoordinate;
	private Text textZCoordinate;
	private Button btnCheckButtonPrimary;

	private int fromDatePid;
	private int toDatePid;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse context
	 *
	 */
	public NewEventWizardPage1(IEclipseContext context) {
		super("wizardPage");
		setTitle("New Location");
		setDescription(
				"Create a new location by entering immutable data for it.\r\nCoordinates can be set later");
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

		btnNewFrom = new Button(compositeFrom, SWT.NONE);
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

		btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
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

		btnClearFrom = new Button(compositeFrom, SWT.NONE);
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

		btnCopyFromTo = new Button(compositeTo, SWT.NONE);
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

		btnNewTo = new Button(compositeTo, SWT.NONE);
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

		btnBrowseTo = new Button(compositeTo, SWT.NONE);
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

		btnClearTo = new Button(compositeTo, SWT.NONE);
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

		final Label lblXCoordinate = new Label(container, SWT.NONE);
		lblXCoordinate.setText("X Coordinate");

		textXCoordinate = new Text(container, SWT.BORDER);
		textXCoordinate.setText("0");
		textXCoordinate.setToolTipText("Latitude (-180 to 180)");
		textXCoordinate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textXCoordinate.addListener(SWT.Verify, new DoubleListener());
		new Label(container, SWT.NONE);

		final Label lblYCoordinate = new Label(container, SWT.NONE);
		lblYCoordinate.setText("Y Coordinate");

		textYCoordinate = new Text(container, SWT.BORDER);
		textYCoordinate.setText("0");
		textYCoordinate.setToolTipText("Longitude (-90 to 90)");
		textYCoordinate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textYCoordinate.addListener(SWT.Verify, new DoubleListener());
		new Label(container, SWT.NONE);

		final Label lblZCoordinate = new Label(container, SWT.NONE);
		lblZCoordinate.setText("Z Coordinate");

		textZCoordinate = new Text(container, SWT.BORDER);
		textZCoordinate.setText("0");
		textZCoordinate.setToolTipText("Elevation above sea level in meters");
		textZCoordinate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textZCoordinate.addListener(SWT.Verify, new DoubleListener());
		new Label(container, SWT.NONE);

		new Label(container, SWT.NONE);

		btnCheckButtonPrimary = new Button(container, SWT.CHECK);
		btnCheckButtonPrimary.setToolTipText("Default");
		btnCheckButtonPrimary.setSelection(true);
		btnCheckButtonPrimary.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCheckButtonPrimary.setText("Primary Location");
		new Label(container, SWT.NONE);
	}

	/**
	 * @return the btnCheckButtonPrimary
	 */
	public Button getBtnCheckButtonPrimary() {
		return btnCheckButtonPrimary;
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the textXCoordinate
	 */
	public Text getTextXCoordinate() {
		return textXCoordinate;
	}

	/**
	 * @return the textYCoordinate
	 */
	public Text getTextYCoordinate() {
		return textYCoordinate;
	}

	/**
	 * @return the textZCoordinate
	 */
	public Text getTextZCoordinate() {
		return textZCoordinate;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * @param btnCheckButtonPrimary the btnCheckButtonPrimary to set
	 */
	public void setBtnCheckButtonPrimary(Button btnCheckButtonPrimary) {
		this.btnCheckButtonPrimary = btnCheckButtonPrimary;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param textXCoordinate the textXCoordinate to set
	 */
	public void setTextXCoordinate(Text textXCoordinate) {
		this.textXCoordinate = textXCoordinate;
	}

	/**
	 * @param textYCoordinate the textYCoordinate to set
	 */
	public void setTextYCoordinate(Text textYCoordinate) {
		this.textYCoordinate = textYCoordinate;
	}

	/**
	 * @param textZCoordinate the textZCoordinate to set
	 */
	public void setTextZCoordinate(Text textZCoordinate) {
		this.textZCoordinate = textZCoordinate;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}
}
