package net.myerichsen.hremvp.event.dialogs;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.event.providers.EventProvider;
import net.myerichsen.hremvp.project.providers.EventRoleProvider;
import net.myerichsen.hremvp.project.providers.EventTypeProvider;
import net.myerichsen.hremvp.providers.HREComboLabelProvider;

/**
 * Dialog to add a person event role
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 12. maj 2019
 *
 */
public class EventRoleDialog extends TitleAreaDialog {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Combo comboEventRole;
	private ComboViewer comboViewerEventRole;

	private int eventNamePid = 0;
	private String role = "";
	private int eventTypePid = 0;
	private int eventRolePid = 0;
	private int eventPid = 0;

	private List<List<String>> eventRoleStringList;
	private Text textEventType;
	private EventTypeProvider etp;

	/**
	 * Create the dialog.
	 * 
	 * @param eventtypePid
	 *
	 * @param parentShell
	 */
	public EventRoleDialog(int eventTypePid, Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		this.eventTypePid = eventTypePid;
		LOGGER.log(Level.INFO, "Event type pid set to {0}", eventTypePid);
		etp = new EventTypeProvider();
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		final Button button = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				createEvent();
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Add an event role for this person event");
		setTitle("Event role");
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label lblEventType = new Label(container, SWT.NONE);
		lblEventType.setText("Event Type");

		textEventType = new Text(container, SWT.BORDER);
		textEventType.setEditable(false);
		textEventType.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		try {
			LOGGER.log(Level.INFO, "Event type pid {0}", eventTypePid);
			etp.get(eventTypePid);
			textEventType.setText(etp.getAbbreviation());
		} catch (Exception e2) {
			LOGGER.log(Level.SEVERE, e2.toString(), e2);
		}

		final Label lblRole = new Label(container, SWT.NONE);
		lblRole.setText("Role");

		comboViewerEventRole = new ComboViewer(container, SWT.NONE);
		comboEventRole = comboViewerEventRole.getCombo();
		comboEventRole.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		comboEventRole.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int selectionIndex = comboEventRole.getSelectionIndex();
				eventRolePid = Integer.parseInt(
						eventRoleStringList.get(selectionIndex).get(0));
			}
		});
		comboViewerEventRole
				.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerEventRole.setLabelProvider(new HREComboLabelProvider(1));

		try {
			eventRoleStringList = new EventRoleProvider()
					.getStringListByType(eventTypePid);
			comboViewerEventRole.setInput(eventRoleStringList);
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
			eventBroker.post("MESSAGE", e1.getMessage());
		}

		return area;
	}

	/**
	 *
	 */
	protected void createEvent() {
		try {
			final EventProvider provider = new EventProvider();
			provider.setEventTypePid(eventTypePid);
			setEventPid(provider.insert());

			LOGGER.log(Level.INFO, "Created event {0}", eventPid);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * @return the eventNamePid
	 */
	public int getEventNamePid() {
		return eventNamePid;
	}

	/**
	 * @return the eventPid
	 */
	public int getEventPid() {
		return eventPid;
	}

	/**
	 * @return the eventRolePid
	 */
	public int getEventRolePid() {
		return eventRolePid;
	}

	/**
	 * @return the eventTypePid
	 */
	public int getEventTypePid() {
		return eventTypePid;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(373, 455);
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param eventNamePid the eventNamePid to set
	 */
	public void setEventNamePid(int eventNamePid) {
		this.eventNamePid = eventNamePid;
	}

	/**
	 * @param eventPid the eventPid to set
	 */
	public void setEventPid(int eventPid) {
		this.eventPid = eventPid;
	}

	/**
	 * @param eventRolePid the eventRolePid to set
	 */
	public void setEventRolePid(int eventRolePid) {
		this.eventRolePid = eventRolePid;
	}

	/**
	 * @param eventTypePid the eventTypePid to set
	 */
	public void setEventTypePid(int eventTypePid) {
		this.eventTypePid = eventTypePid;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	@Inject
	@Optional
	private void subscribeEventTypePidUpdateTopic(
			@UIEventTopic(Constants.LOCATION_PID_UPDATE_TOPIC) int eventTypePid) {
		LOGGER.log(Level.INFO, "Received event type pid {0}", eventTypePid);
		this.eventTypePid = eventTypePid;

		try {
			etp.get(eventTypePid);
			textEventType.setText(etp.getAbbreviation());

			eventRoleStringList = new EventRoleProvider()
					.getStringListByType(eventTypePid);
			comboViewerEventRole.setInput(eventRoleStringList);
			comboViewerEventRole.refresh();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}

	}
}
