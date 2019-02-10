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

/**
 * Display all sexes for a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 10. feb. 2019
 */
@SuppressWarnings("restriction")
public class PersonSexesView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
	public PersonSexesView() throws SQLException {
		provider = new PersonProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openSexView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));

		final TableViewerColumn tableViewerColumnSexId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnSexId = tableViewerColumnSexId.getColumn();
		tblclmnSexId.setWidth(100);
		tblclmnSexId.setText("ID");

		final TableViewerColumn tableViewerColumnSexLabel = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnSex = tableViewerColumnSexLabel.getColumn();
		tblclmnSex.setWidth(250);
		tblclmnSex.setText("Sex");

		final TableViewerColumn tableViewerColumnSexPrimary = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnSexPrimary = tableViewerColumnSexPrimary.getColumn();
		tblclmnSexPrimary.setWidth(83);
		tblclmnSexPrimary.setText("Primary");

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
	protected void openSexView() {
		int sexPid = 0;

		final ParameterizedCommand command = commandService.createCommand("net.myerichsen.hremvp.command.opensexview",
				null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			sexPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting sex pid: " + sexPid);
		eventBroker.post(Constants.SEX_PID_UPDATE_TOPIC, sexPid);
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 * @throws SQLException
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int key) throws SQLException {
	}

}
