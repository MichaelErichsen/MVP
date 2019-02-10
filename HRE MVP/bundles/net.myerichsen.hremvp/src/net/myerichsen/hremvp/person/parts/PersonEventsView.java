package net.myerichsen.hremvp.person.parts;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.filters.NavigatorFilter;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all events for a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 10. feb. 2019
 */
@SuppressWarnings("restriction")
public class PersonEventsView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private TableViewer tableViewer;
	private PersonProvider provider;
	private NavigatorFilter navigatorFilter;

	/**
	 * Constructor
	 *
	 */
	public PersonEventsView() {
		try {
			provider = new PersonProvider();
			navigatorFilter = new NavigatorFilter();
		} catch (Exception e) {
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addFilter(navigatorFilter);

		Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEventView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEvent = tableViewerColumnLabel.getColumn();
		tblclmnEvent.setWidth(100);
		tblclmnEvent.setText("Event");
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnRole = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnRole = tableViewerColumnRole.getColumn();
		tblclmnRole.setWidth(100);
		tblclmnRole.setText("Role");
		tableViewerColumnRole.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnFromDate = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnFromDate = tableViewerColumnFromDate.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");
		tableViewerColumnFromDate.setLabelProvider(new HREColumnLabelProvider(3));

		final TableViewerColumn tableViewerColumnToDate = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnToDate = tableViewerColumnToDate.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");
		tableViewerColumnToDate.setLabelProvider(new HREColumnLabelProvider(4));

		final Label lblFilter = new Label(parent, SWT.NONE);
		lblFilter.setText("Event Filter");

		final Text textFilter = new Text(parent, SWT.BORDER);
		textFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				navigatorFilter.setSearchText(textFilter.getText());
				LOGGER.fine("Filter string: " + textFilter.getText());
				tableViewer.refresh();
			}
		});

		textFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getPersonEventList(0));
		} catch (SQLException | MvpException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	protected void insert() {
		try {
			provider.insert();
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
	 */
	protected void openEventView() {
		int eventPid = 0;

		final ParameterizedCommand command = commandService.createCommand("net.myerichsen.hremvp.command.openeventview",
				null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			eventPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting personEvent pid: " + eventPid);
		eventBroker.post(Constants.EVENT_PID_UPDATE_TOPIC, eventPid);
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribePersonPidUpdateTopic(@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int personPid) {
		LOGGER.fine("Received person id " + personPid);
		try {
			tableViewer.setInput(provider.getPersonEventList(personPid));
			tableViewer.refresh();
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.update();
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

}
