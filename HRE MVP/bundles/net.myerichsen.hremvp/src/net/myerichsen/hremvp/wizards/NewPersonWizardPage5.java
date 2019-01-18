package net.myerichsen.hremvp.wizards;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dialogs.NewEventDialog;

/**
 * Person events wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 18. jan. 2019
 *
 */
public class NewPersonWizardPage5 extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Table tableEvents;

	private TableColumn tblclmnFromDate;
	private TableViewerColumn tableViewerColumn;
	private TableColumn tblclmnToDate;
	private TableViewerColumn tableViewerColumn_1;

	private List<Integer> eventPidList;

	public NewPersonWizardPage5(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Events");
		setDescription("Add events for the new person. More events can be added later.");
		this.context = context;
		setEventPidList(new ArrayList<>());
	}

	/**
	 *
	 */
	protected void addEvent() {
		final NewEventDialog dialog = new NewEventDialog(tableEvents.getShell(), context);

		if (dialog.open() == Window.OK) {
			try {
				final List<String> eventStringList = dialog.getEventStringList();

				final TableItem item = new TableItem(tableEvents, SWT.NONE);
				item.setText(0, eventStringList.get(0));
				item.setText(1, eventStringList.get(1));
				item.setText(2, eventStringList.get(2));
				item.setText(3, eventStringList.get(3));
				item.setText(4, eventStringList.get(4));

				eventPidList.add(Integer.parseInt(eventStringList.get(0)));
			} catch (final Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableEvents = tableViewer.getTable();
		tableEvents.setLinesVisible(true);
		tableEvents.setHeaderVisible(true);
		tableEvents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventId = tableViewerColumnId.getColumn();
		tblclmnEventId.setWidth(100);
		tblclmnEventId.setText("ID");

		final TableViewerColumn tableViewerColumnMap = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventLabel = tableViewerColumnMap.getColumn();
		tblclmnEventLabel.setWidth(100);
		tblclmnEventLabel.setText("Event label");

		final TableViewerColumn tableViewerColumnPart = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventRole = tableViewerColumnPart.getColumn();
		tblclmnEventRole.setWidth(100);
		tblclmnEventRole.setText("Role in event");

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnFromDate = tableViewerColumn.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");

		tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnToDate = tableViewerColumn_1.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");

		final Menu menu = new Menu(tableEvents);
		tableEvents.setMenu(menu);

		final MenuItem mntmNewEvent = new MenuItem(menu, SWT.NONE);
		mntmNewEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addEvent();
			}
		});
		mntmNewEvent.setText("New Event...");

		final MenuItem mntmDeleteSelectedEvent = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSelectedEvent();
			}
		});
		mntmDeleteSelectedEvent.setText("Delete selected Event");

	}

	/**
	 *
	 */
	protected void deleteSelectedEvent() {
		int eventPid;
		Events event;
		final TableItem[] selectedRows = tableEvents.getSelection();

		try {
			if (selectedRows.length > 0) {
				final TableItem selectedRow = selectedRows[0];
				eventPid = Integer.parseInt(selectedRow.getText(0));
				event = new Events();
				event.get(eventPid);
				event.delete();

				eventPidList.remove(eventPid);
				LOGGER.info("Deleted event " + eventPid);
			}
		} catch (NumberFormatException | SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the eventPidList
	 */
	public List<Integer> getEventPidList() {
		return eventPidList;
	}

	/**
	 * @param eventPidList the eventPidList to set
	 */
	public void setEventPidList(List<Integer> eventPidList) {
		this.eventPidList = eventPidList;
	}
}
