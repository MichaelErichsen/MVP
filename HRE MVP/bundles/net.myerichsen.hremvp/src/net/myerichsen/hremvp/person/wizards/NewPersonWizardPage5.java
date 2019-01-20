package net.myerichsen.hremvp.person.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
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

import net.myerichsen.hremvp.event.dialogs.NewEventDialog;
import net.myerichsen.hremvp.event.providers.EventProvider;
import net.myerichsen.hremvp.person.providers.PersonEventProvider;

/**
 * Person events wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. jan. 2019
 *
 */
public class NewPersonWizardPage5 extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	private Table table;

	private List<Integer> personEventPidList;
	private final IEventBroker eventBroker;

	public NewPersonWizardPage5(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Events");
		setDescription("Add events for the new person. More events can be added later.");
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
		setPersonEventPidList(new ArrayList<>());
	}

	/**
	 *
	 */
	protected void addEvent(int personPid) {
		final NewEventDialog dialog = new NewEventDialog(table.getShell(), context);

		if (dialog.open() == Window.OK) {
			try {
				final List<String> eventStringList = dialog.getEventStringList();

				// Create an personEvent
				final EventProvider ep = new EventProvider();
				ep.setEventNamePid(dialog.getEventNamePid());
				ep.setFromDatePid(dialog.getFromDatePid());
				ep.setToDatePid(dialog.getToDatePid());
				final int eventPid = ep.insert();

				// Create a person-personEvent to link them together
				final PersonEventProvider pep = new PersonEventProvider();
				pep.setEventPid(eventPid);
				pep.setPersonPid(personPid);
				pep.setPrimaryEvent(true);
				pep.setPrimaryPerson(true);
				pep.setRole(eventStringList.get(3));
				final int personEventPid = pep.insert();

				// Display in view
				final TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, Integer.toString(personEventPid));
				item.setText(1, Integer.toString(eventPid));
				item.setText(2, eventStringList.get(0));
				item.setText(3, eventStringList.get(1));
				item.setText(4, eventStringList.get(2));
				item.setText(5, eventStringList.get(3));

				personEventPidList.add(personEventPid);
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				eventBroker.post("MESSAGE", e.getMessage());
			}

		}
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnPersoneventId = tableViewerColumn.getColumn();
		tblclmnPersoneventId.setWidth(100);
		tblclmnPersoneventId.setText("PersonEvent ID");

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventId = tableViewerColumnId.getColumn();
		tblclmnEventId.setWidth(100);
		tblclmnEventId.setText("Event ID");

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventLabel = tableViewerColumnLabel.getColumn();
		tblclmnEventLabel.setWidth(100);
		tblclmnEventLabel.setText("Event label");

		final TableViewerColumn tableViewerColumnRole = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventRole = tableViewerColumnRole.getColumn();
		tblclmnEventRole.setWidth(100);
		tblclmnEventRole.setText("Role in personEvent");

		final TableViewerColumn tableViewerColumnFromDate = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnFromDate = tableViewerColumnFromDate.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");

		final TableViewerColumn tableViewerColumnToDate = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnToDate = tableViewerColumnToDate.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		final MenuItem mntmNewEvent = new MenuItem(menu, SWT.NONE);
		mntmNewEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final NewPersonWizard wizard = (NewPersonWizard) getWizard();
				addEvent(wizard.getPersonPid());
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
		int personEventPid;
		final TableItem[] selectedRows = table.getSelection();

		try {
			if (selectedRows.length > 0) {
				// Delete personEvent for person
				final TableItem selectedRow = selectedRows[0];
				personEventPid = Integer.parseInt(selectedRow.getText(0));
				final PersonEventProvider pep = new PersonEventProvider();
				final int eventPid = pep.getEventPid();
				pep.delete(personEventPid);
				personEventPidList.remove(personEventPid);
				LOGGER.info("Deleted personEvent " + personEventPid);

				// If no more person events then delete event
				if (pep.areMoreEvents(eventPid) == false) {
					final EventProvider ep = new EventProvider();
					ep.delete(eventPid);
					LOGGER.info("Deleted event " + eventPid);
				}
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the personEventPidList
	 */
	public List<Integer> getPersonEventPidList() {
		return personEventPidList;
	}

	/**
	 * @param personEventPidList the personEventPidList to set
	 */
	public void setPersonEventPidList(List<Integer> eventPidList) {
		personEventPidList = eventPidList;
	}
}
