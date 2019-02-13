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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all parents for a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 10. feb. 2019
 */
@SuppressWarnings("restriction")
public class PersonParentsView {

	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private TableViewer tableViewer;

	private final PersonProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public PersonParentsView() throws SQLException {
		provider = new PersonProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(5, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openParentView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnParentsId = tableViewerColumnId.getColumn();
		tblclmnParentsId.setWidth(100);
		tblclmnParentsId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnParents = tableViewerColumnLabel.getColumn();
		tblclmnParents.setWidth(250);
		tblclmnParents.setText("Parents");
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnRole = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnParentRole = tableViewerColumnRole.getColumn();
		tblclmnParentRole.setWidth(100);
		tblclmnParentRole.setText("Role");
		tableViewerColumnRole.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnPrimary = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnParentsPrimary = tableViewerColumnPrimary
				.getColumn();
		tblclmnParentsPrimary.setWidth(73);
		tblclmnParentsPrimary.setText("Primary");
		tableViewerColumnPrimary
				.setLabelProvider(new HREColumnLabelProvider(3));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getParentList(0));
		} catch (final SQLException e1) {
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
	protected void openParentView() {
		// FIXME Doubleclick does not work any more
		int personPid = 0;

		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.openpersonview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting person pid: " + personPid);
		eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC, personPid);
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
	private void subscribePersonPidUpdateTopic(
			@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int personPid) {
		LOGGER.fine("Received person id " + personPid);
		try {
			tableViewer.setInput(provider.getParentList(personPid));
			tableViewer.refresh();
		} catch (final SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}