package net.myerichsen.hremvp.person.parts;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
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

import net.myerichsen.hremvp.dbmodels.PersonNames;
import net.myerichsen.hremvp.person.providers.PersonNameListProvider;
import net.myerichsen.hremvp.person.providers.PersonNameProvider;

/**
 * Display all names of a person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. mar. 2019
 *
 */
@SuppressWarnings("restriction")
public class PersonNameNavigator {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	PersonNameListProvider provider;
	private TableViewer tableViewer;

	// FIXME To string now invalid in name column
	/**
	 * Constructor
	 *
	 */
	public PersonNameNavigator() {
		provider = new PersonNameListProvider();
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
				openNameView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumnName = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumnName.getColumn();
		tblclmnName.setWidth(250);
		tblclmnName.setText("Name");

		updateGui();
	}

	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	protected void openNameView() {
		int NamePid = 0;

		// Open an editor
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.opennameview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			NamePid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(
				net.myerichsen.hremvp.Constants.PERSON_NAME_PID_UPDATE_TOPIC,
				NamePid);

	}

	@Focus
	public void setFocus() {
	}

	/**
	 *
	 */
	private void updateGui() {
		try {
			provider = new PersonNameListProvider();
			final PersonNameProvider personNameProvider = new PersonNameProvider();

			final List<PersonNames> rowList = provider.getModelList();

			// FIXME Change to JFace
			tableViewer.getTable().removeAll();

			for (int i = 0; i < rowList.size(); i++) {
				final TableItem item = new TableItem(tableViewer.getTable(),
						SWT.NONE);
				final PersonNames row = rowList.get(i);
				item.setText(0, Integer.toString(row.getNamePid()));
				personNameProvider.get(row.getNamePid());
				item.setText(1, personNameProvider.toString());
			}
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

}
