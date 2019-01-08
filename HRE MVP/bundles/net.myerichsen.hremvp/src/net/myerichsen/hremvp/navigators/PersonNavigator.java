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

import net.myerichsen.hremvp.providers.PersonProvider;

/**
 * Display a list of all persons with their primary names
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 7. jan. 2019
 *
 */

@SuppressWarnings("restriction")
public class PersonNavigator {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private Table table;
	private PersonProvider provider;

	/**
	 * Constructor
	 *
	 */
	public PersonNavigator() {
		try {
			provider = new PersonProvider();
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
		parent.setLayout(new GridLayout(1, false));

		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		// FIXME Double click should not open person view
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openPersonView();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		TableViewerColumn tableViewerColumnName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPrimaryName = tableViewerColumnName.getColumn();
		tblclmnPrimaryName.setWidth(300);
		tblclmnPrimaryName.setText("Primary Name");

		List<String> stringList;

		try {
			List<List<String>> lls = provider.get();
			table.removeAll();
			TableItem item;

			for (int i = 0; i < lls.size(); i++) {
				stringList = lls.get(i);

				if (stringList.get(1).trim().length() > 0) {

					item = new TableItem(table, SWT.NONE);

					for (int j = 0; j < stringList.size(); j++) {
						item.setText(j, stringList.get(j).trim());
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}
	}

	/**
	 * 
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	private void openPersonView() {
		int personPid = 0;

		// Open an editor
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openpersonview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);

	}

	@Focus
	public void setFocus() {
	}
}
