package net.myerichsen.hremvp.navigators;

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

import net.myerichsen.hremvp.dbmodels.Names;
import net.myerichsen.hremvp.providers.NameListProvider;
import net.myerichsen.hremvp.providers.NameProvider;

/**
 * Display a list of all names
 *
 * @version 2018-08-25
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 *
 */
@SuppressWarnings("restriction")
public class NameNavigator {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	NameListProvider provider;
	private Table table;

	// To string now invalid in name column
	/**
	 * Constructor
	 *
	 */
	public NameNavigator() {
		provider = new NameListProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNameView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		TableViewerColumn tableViewerColumnName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnName = tableViewerColumnName.getColumn();
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
		final ParameterizedCommand command = commandService.createCommand("net.myerichsen.hremvp.command.opennameview",
				null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			NamePid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(net.myerichsen.hremvp.Constants.NAME_PID_UPDATE_TOPIC, NamePid);

	}

	@Focus
	public void setFocus() {
	}

	/**
	 *
	 */
	private void updateGui() {
		try {
			provider = new NameListProvider();
			NameProvider nameProvider = new NameProvider();

			List<Names> rowList = provider.getModelList();

			table.removeAll();

			for (int i = 0; i < rowList.size(); i++) {
				TableItem item = new TableItem(table, SWT.NONE);
				Names row = rowList.get(i);
				item.setText(0, Integer.toString(row.getNamePid()));
				nameProvider.get(row.getNamePid());
				item.setText(1, nameProvider.toString());
			}
		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}
