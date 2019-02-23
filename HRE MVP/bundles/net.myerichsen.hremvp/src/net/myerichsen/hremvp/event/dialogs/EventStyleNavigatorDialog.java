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

import net.myerichsen.hremvp.event.providers.EventTypeProvider;

/**
 * Display all event types
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 23. feb. 2019
 *
 */
public class EventStyleNavigatorDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEventBroker eventBroker;

	private EventTypeProvider provider;
	private Table table;
	private int eventTypePid;
	private String eventTypeLabel;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 * @param context
	 */
	public EventStyleNavigatorDialog(Shell parentShell,
			IEclipseContext context) {
		super(parentShell);
		eventBroker = context.get(IEventBroker.class);
		try {
			provider = new EventTypeProvider();
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
		setMessage("Select an Event Type");
		setTitle("Event Types");
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
				setEventTypePid(Integer.parseInt(selectedItem.getText(0)));
				setEventTypeLabel(selectedItem.getText(1));
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
			final List<List<String>> EventTypeList = provider
					.getEventTypeList();
			table.removeAll();

			for (int i = 0; i < EventTypeList.size(); i++) {
				final List<String> type = EventTypeList.get(i);
				final TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, type.get(0));
				item.setText(1, type.get(1));
			}
		} catch (final Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}

		return area;
	}

	/**
	 * @return the eventTypeLabel
	 */
	public String getEventTypeLabel() {
		return eventTypeLabel;
	}

	/**
	 * @return the EventesPid
	 */
	public int getEventTypePid() {
		return eventTypePid;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(477, 464);
	}

	/**
	 * @param eventTypeLabel the eventTypeLabel to set
	 */
	public void setEventTypeLabel(String eventTypeLabel) {
		this.eventTypeLabel = eventTypeLabel;
	}

	/**
	 * @param eventPid
	 */
	public void setEventTypePid(int eventPid) {
		eventTypePid = eventPid;
	}

}