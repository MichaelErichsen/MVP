package net.myerichsen.hremvp.event.dialogs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.event.providers.EventNameProvider;
import net.myerichsen.hremvp.event.providers.EventTypeProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Dialog to create a new personEvent
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. jan. 2019
 *
 */
public class NewEventDialog extends TitleAreaDialog {
	@Inject
	private IEventBroker eventBroker;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Text textEventStylePid;
	private Text textEventStyleLabel;
	private Text textEventNamePid;
	private Text textEventNameLabel;
	private Text textRole;
	private Text textFromDate;
	private Text textToDate;

	private final List<String> eventStringList;
//	private String styleLabel;
	private String nameLabel;
	private int eventNamePid;
	private String role = "";
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
		eventStringList = new ArrayList<>();
	}

	/**
	 *
	 */
	protected void browseEventNames() {
		final EventNameNavigatorDialog dialog = new EventNameNavigatorDialog(textEventNamePid.getShell(), context);

		if (dialog.open() == Window.OK) {
			try {
				eventNamePid = dialog.getEventNamePid();
				textEventNamePid.setText(Integer.toString(eventNamePid));
				nameLabel = dialog.getEventNameLabel();
				textEventNameLabel.setText(nameLabel);
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	protected void browseEventStyles() {
		final EventStyleNavigatorDialog dialog = new EventStyleNavigatorDialog(textEventStylePid.getShell(), context);

		if (dialog.open() == Window.OK) {
			try {
				final int eventStylePid = dialog.getEventTypePid();
				textEventStylePid.setText(Integer.toString(eventStylePid));
				final String styleLabel = dialog.getEventTypeLabel();
				textEventStyleLabel.setText(styleLabel);
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	private void browseFromDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(textFromDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				fromDatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(fromDatePid);
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
				toDatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(toDatePid);
				textToDate.setText(hdp.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	protected void clearEventName() {
		textEventNameLabel.setText("");
		textEventNamePid.setText("");
		eventNamePid = 0;
		nameLabel = "";
	}

	/**
	 *
	 */
	protected void clearEventStyle() {
		textEventStyleLabel.setText("");
		textEventStylePid.setText("");
//		styleLabel = "";
	}

	/**
	 *
	 */
	private void clearFromDate() {
		textFromDate.setText("");
		fromDatePid = 0;
	}

	/**
	 *
	 */
	private void clearToDate() {
		textToDate.setText("");
		toDatePid = 0;
	}

	/**
	 *
	 */
	protected void copyFromDateToNewToDate() {
		textToDate.setText(textFromDate.getText());
		toDatePid = fromDatePid;

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
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Select an provider Style and create the new provider.");
		setTitle("New Event");
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite compositeEventStyle = new Composite(container, SWT.BORDER);
		compositeEventStyle.setLayout(new GridLayout(3, false));
		compositeEventStyle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblEventStyle = new Label(compositeEventStyle, SWT.NONE);
		lblEventStyle.setText("Event Style");

		textEventStylePid = new Text(compositeEventStyle, SWT.BORDER);

		textEventStyleLabel = new Text(compositeEventStyle, SWT.BORDER);
		textEventStyleLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textEventStyleLabel.setEditable(false);

		final Composite compositeEventStyleButtons = new Composite(compositeEventStyle, SWT.NONE);
		compositeEventStyleButtons.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		compositeEventStyleButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateEventStyle = new Button(compositeEventStyleButtons, SWT.NONE);
		btnUpdateEventStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateEventStyle();
			}
		});
		btnUpdateEventStyle.setText("Update");

		final Button btnBrowseEventStyle = new Button(compositeEventStyleButtons, SWT.NONE);
		btnBrowseEventStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseEventStyles();
			}
		});
		btnBrowseEventStyle.setText("Browse");

		final Button btnClearEventStyle = new Button(compositeEventStyleButtons, SWT.NONE);
		btnClearEventStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearEventStyle();
			}
		});
		btnClearEventStyle.setText("Clear");

		final Composite compositeEventName = new Composite(container, SWT.BORDER);
		compositeEventName.setLayout(new GridLayout(3, false));
		compositeEventName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Label lblEventName = new Label(compositeEventName, SWT.NONE);
		lblEventName.setText("Event Name");

		textEventNamePid = new Text(compositeEventName, SWT.BORDER);

		textEventNameLabel = new Text(compositeEventName, SWT.BORDER);
		textEventNameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textEventNameLabel.setEditable(false);

		Composite compositeEventNameButtons;
		compositeEventNameButtons = new Composite(compositeEventName, SWT.NONE);
		compositeEventNameButtons.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		compositeEventNameButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnUpdateEventName = new Button(compositeEventNameButtons, SWT.NONE);
		btnUpdateEventName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateEventName();
			}
		});
		btnUpdateEventName.setText("Update");

		final Button btnBrowseEventName = new Button(compositeEventNameButtons, SWT.NONE);
		btnBrowseEventName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseEventNames();
			}
		});
		btnBrowseEventName.setText("Browse");

		final Button btnClearEventName = new Button(compositeEventNameButtons, SWT.NONE);
		btnClearEventName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearEventName();
			}
		});
		btnClearEventName.setText("Clear");

		final Label lblRole = new Label(container, SWT.NONE);
		lblRole.setText("Role");

		textRole = new Text(container, SWT.BORDER);
		textRole.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				role = textRole.getText();
			}
		});
		textRole.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(container, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(container, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
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
		compositeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnCopyDates = new Button(compositeTo, SWT.NONE);
		btnCopyDates.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				copyFromDateToNewToDate();
			}
		});
		btnCopyDates.setText("Copy From Date");

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

		return area;
	}

	/**
	 * @return the eventNamePid
	 */
	public int getEventNamePid() {
		return eventNamePid;
	}

	/**
	 * @return the eventStringList
	 */
	public List<String> getEventStringList() {
		HDateProvider hdateProvider;

		eventStringList.clear();
		eventStringList.add(nameLabel);
		eventStringList.add(role);

		if (fromDatePid != 0) {
			try {
				hdateProvider = new HDateProvider();
				hdateProvider.get(fromDatePid);
				eventStringList.add(hdateProvider.getDate().toString());
			} catch (SQLException | MvpException e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
				eventStringList.add("");
			}
		} else {
			eventStringList.add("");
		}

		if (toDatePid != 0) {
			try {
				hdateProvider = new HDateProvider();
				hdateProvider.get(toDatePid);
				eventStringList.add(hdateProvider.getDate().toString());
			} catch (SQLException | MvpException e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
				eventStringList.add("");
			}
		} else {
			eventStringList.add("");
		}

		return eventStringList;
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(373, 455);
	}

//	/**
//	 * @return the styleLabel
//	 */
//	public String getStyleLabel() {
//		return styleLabel;
//	}

	/**
	 * @return the nameLabel
	 */
	public String getNameLabel() {
		return nameLabel;
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
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @return the textEventNamePid
	 */
	public Text getTextEventNamePid() {
		return textEventNamePid;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * @param eventNamePid the eventNamePid to set
	 */
	public void setEventNamePid(int eventNamePid) {
		this.eventNamePid = eventNamePid;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

//	/**
//	 * @param styleLabel the styleLabel to set
//	 */
//	public void setStyleLabel(String label) {
//		styleLabel = label;
//	}

	/**
	 * @param nameLabel the nameLabel to set
	 */
	public void setNameLabel(String nameLabel) {
		this.nameLabel = nameLabel;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @param textEventNamePid the textEventNamePid to set
	 */
	public void setTextEventNamePid(Text textEventNamePid) {
		this.textEventNamePid = textEventNamePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}

	/**
	 *
	 */
	protected void updateEventName() {
		try {
			final EventNameProvider provider = new EventNameProvider();
			eventNamePid = Integer.parseInt(textEventNamePid.getText());
			provider.get(eventNamePid);
			nameLabel = provider.getLabel();
			textEventNameLabel.setText(nameLabel);
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void updateEventStyle() {
		try {
			final EventTypeProvider provider = new EventTypeProvider();
			provider.get(Integer.parseInt(textEventStylePid.getText()));
			final String styleLabel = provider.getLabel();
			textEventStyleLabel.setText(styleLabel);
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}
}