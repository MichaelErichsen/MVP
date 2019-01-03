package net.myerichsen.hremvp.navigators;

import java.sql.SQLException;
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
import org.eclipse.e4.ui.services.EMenuService;
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

import net.myerichsen.hremvp.providers.LocationNameStyleProvider;

/**
 * Display all location name styles
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 13. nov. 2018
 *
 */
// FIXME Reopens view
@SuppressWarnings("restriction")
public class LocationNameStyleNavigator {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	@Inject
	private IEventBroker eventBroker;

	private Table table;
	private LocationNameStyleProvider provider;

	public LocationNameStyleNavigator() {
		try {
			provider = new LocationNameStyleProvider();
		} catch (final SQLException e) {
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService) {
		parent.setLayout(new GridLayout(1, false));

		final TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLocationNameStyleView();
			}
		});
		menuService.registerContextMenu(tableViewer.getControl(),
				"net.myerichsen.hremvp.popupmenu.locationnamestylemenu");
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(40);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnLocationNameStyle = tableViewerColumn_1.getColumn();
		tblclmnLocationNameStyle.setWidth(230);
		tblclmnLocationNameStyle.setText("Location Name Style");

		String string;
		String[] sa;

		try {
			final List<String> stringList = provider.get();
			table.removeAll();

			for (int i = 0; i < stringList.size(); i++) {
				final TableItem item = new TableItem(table, SWT.NONE);
				string = stringList.get(i);
				sa = string.split(",");
				item.setText(0, sa[0]);
				item.setText(1, sa[1]);
			}
		} catch (final Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}
	}

	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	protected void openLocationNameStyleView() {
		int locationNameStylePid = 0;

		// Open Location Name View
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openlocationnamestyleview", null);
		handlerService.executeHandler(command);

		// Post name style pid
		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			locationNameStylePid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(net.myerichsen.hremvp.Constants.LOCATION_NAME_STYLE_PID_UPDATE_TOPIC, locationNameStylePid);
		LOGGER.info("Location Name Style Pid: " + locationNameStylePid);
	}

	@Focus
	public void setFocus() {
	}

}
