package net.myerichsen.hremvp.databaseadmin;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.providers.H2DatabaseProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Create a view part with all tables in the database
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 28. apr. 2019
 *
 */
@SuppressWarnings("restriction")
public class H2DatabaseNavigator {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	@Inject
	private IEventBroker eventBroker;

	private H2DatabaseProvider provider;
	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 */
	public H2DatabaseNavigator() {
		try {
			provider = new H2DatabaseProvider();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Create contents of the view part.
	 *
	 * @param parent Shell
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		LOGGER.log(Level.FINE, "Creating controls");
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		tableViewer.addDoubleClickListener(event -> {
			final TableItem[] selectedRows = table.getSelection();
			final TableItem selectedRow = selectedRows[0];
			final String tableName = selectedRow.getText(0);

			final ParameterizedCommand command = commandService.createCommand(
					"net.myerichsen.hremvp.command.tablenavigatoropen", null);
			handlerService.executeHandler(command);
			eventBroker.post(Constants.TABLENAME_UPDATE_TOPIC, tableName);
			eventBroker.post("MESSAGE", tableName + " has been opened");
		});

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumnName = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnTableName = tableViewerColumnName.getColumn();
		tblclmnTableName.setWidth(405);
		tblclmnTableName.setText("Table Name");
		tableViewerColumnName.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnCount = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnRowCount = tableViewerColumnCount.getColumn();
		tblclmnRowCount.setWidth(70);
		tblclmnRowCount.setText("Row Count");
		tableViewerColumnCount.setLabelProvider(new HREColumnLabelProvider(1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getStringList());
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.getMessage());
		}
	}

	/**
	 * @param dbName
	 */
	@Inject
	@Optional
	private void subscribeDatabaseNameUpdateTopic(
			@UIEventTopic(Constants.DATABASE_UPDATE_TOPIC) String dbName) {
		LOGGER.log(Level.INFO, "{0}", dbName);

		try {
			tableViewer.setInput(provider.getStringList());
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
}