package net.myerichsen.hremvp.event.dialogs;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.event.providers.EventNameProvider;

/**
 * Display all Event Names
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 18. jan. 2019
 *
 */
public class EventNameNavigatorDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEventBroker eventBroker;

	private EventNameProvider provider;
	private Table table;
	private int eventNamePid;
	private String eventNameLabel;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 * @param context
	 */
	public EventNameNavigatorDialog(Shell parentShell,
			IEclipseContext context) {
		super(parentShell);
		eventBroker = context.get(IEventBroker.class);
		try {
			provider = new EventNameProvider();
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
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
		setMessage("Select an Event Name");
		setTitle("Event Names");
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		final GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.grabExcessHorizontalSpace = false;
		container.setLayoutData(gd_container);

		final TableViewer tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final TableItem[] items = table.getSelection();
				final TableItem selectedItem = items[0];
				setEventNamePid(Integer.parseInt(selectedItem.getText(0)));
				setEventNameLabel(selectedItem.getText(1));
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnHistoricalEvent = tableViewerColumn_1
				.getColumn();
		tblclmnHistoricalEvent.setWidth(100);
		tblclmnHistoricalEvent.setText("Event");

		try {
			final List<EventNames> EventNameList = provider.get();
			table.removeAll();

			for (int i = 0; i < EventNameList.size(); i++) {
				final EventNames eventName = EventNameList.get(i);
				final TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, Integer.toString(eventName.getEventNamePid()));
				item.setText(1, eventName.getLabel());
			}
		} catch (final Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}

		return area;
	}

	/**
	 * @return the eventNameLabel
	 */
	public String getEventNameLabel() {
		return eventNameLabel;
	}

	/**
	 * @return the EventesPid
	 */
	public int getEventNamePid() {
		return eventNamePid;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(477, 464);
	}

	/**
	 * @param eventNameLabel the eventNameLabel to set
	 */
	public void setEventNameLabel(String eventNameLabel) {
		this.eventNameLabel = eventNameLabel;
	}

	/**
	 * @param eventPid
	 */
	public void setEventNamePid(int eventPid) {
		eventNamePid = eventPid;
	}

}