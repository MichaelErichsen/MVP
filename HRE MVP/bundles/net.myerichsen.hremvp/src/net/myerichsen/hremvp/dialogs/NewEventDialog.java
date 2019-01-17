package net.myerichsen.hremvp.dialogs;

import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 17. jan. 2019
 *
 */
public class NewEventDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Text textEventTypePid;
	private Text textEventTypeLabel;
	private Text textRole;

	private Text textFromDate;
	private Button btnNewFrom;
	private Button btnBrowseFrom;
	private Button btnClearFrom;

	private Text textToDate;
	private Button btnNewTo;
	private Button btnBrowseTo;
	private Button btnClearTo;

	private int fromDatePid;
	private int toDatePid;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public NewEventDialog(Shell parentShell, IEclipseContext context) {
		super(parentShell);
		setHelpAvailable(false);
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
	private void clearToDate() {
		textToDate.setText("");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Select an event type and create the new event.");
		setTitle("New Event");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite compositeEventType = new Composite(container, SWT.BORDER);
		compositeEventType.setLayout(new GridLayout(3, false));
		compositeEventType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblEventType = new Label(compositeEventType, SWT.NONE);
		lblEventType.setText("Event Type");

		textEventTypePid = new Text(compositeEventType, SWT.BORDER);

		textEventTypeLabel = new Text(compositeEventType, SWT.BORDER);
		textEventTypeLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textEventTypeLabel.setEditable(false);

		Composite composite = new Composite(compositeEventType, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnUpdate = new Button(composite, SWT.NONE);
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateEventType();
			}
		});
		btnUpdate.setText("Update");

		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseEventTypes();
			}
		});
		btnBrowse.setText("Browse");

		Button btnClear = new Button(composite, SWT.NONE);
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearEventType();
			}
		});
		btnClear.setText("Clear");

		Label lblRole = new Label(container, SWT.NONE);
		lblRole.setText("Role");

		textRole = new Text(container, SWT.BORDER);
		textRole.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(container, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(container, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewFromDate();
			}
		});
		btnNewFrom.setText("New");

		btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseFromDates();
			}
		});
		btnBrowseFrom.setText("Browse");

		btnClearFrom = new Button(compositeFrom, SWT.NONE);
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
				compositeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
				compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));
				
						btnNewTo = new Button(compositeTo, SWT.NONE);
						btnNewTo.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseDown(MouseEvent e) {
								getNewToDate();
							}
						});
						btnNewTo.setText("New");
						
								btnBrowseTo = new Button(compositeTo, SWT.NONE);
								btnBrowseTo.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseDown(MouseEvent e) {
										browseToDates();
									}
								});
								btnBrowseTo.setText("Browse");
								
										btnClearTo = new Button(compositeTo, SWT.NONE);
										btnClearTo.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseDown(MouseEvent e) {
												clearToDate();
											}
										});
										btnClearTo.setText("Clear");

		return area;
	}

	/**
	 * 
	 */
	protected void updateEventType() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	protected void browseEventTypes() {
	EventTypeNavigatorDialog dialog = new EventTypeNavigatorDialog(textEventTypePid.getShell(), context);
	
	if (dialog.open() == Window.OK) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
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
				setFromDatePid(hdp.insert());
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
				setToDatePid(hdp.insert());
				textToDate.setText(dialog.getLocalDate().toString());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
			}
		}
	}

	/**
	 * 
	 */
	protected void clearEventType() {
		// TODO Auto-generated method stub

	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 448);
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}
}
