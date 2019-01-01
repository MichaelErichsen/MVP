package net.myerichsen.gen.mvp.navigators;

import java.util.List;

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

import net.myerichsen.gen.mvp.dbmodels.Persons;
import net.myerichsen.gen.mvp.providers.NameProvider;
import net.myerichsen.gen.mvp.providers.PersonListProvider;

/**
 * Display a list of all persons with their primary names
 *
 * @version 2018-08-30
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 *
 */

@SuppressWarnings("restriction")
public class PersonNavigator {
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	private PersonListProvider provider;
	private Table table;

	/**
	 * Constructor
	 *
	 */
	public PersonNavigator() {
		provider = new PersonListProvider();
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

		updateGui();
	}

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
				.createCommand("net.myerichsen.gen.mvp.command.openpersonview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(net.myerichsen.gen.mvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);

	}

	@Focus
	public void setFocus() {
	}

	/**
	 *
	 */
	private void updateGui() {
		NameProvider np;
		int personPid;

		try {
			provider = new PersonListProvider();
			List<Persons> rowList = provider.getModelList();

			table.removeAll();

			for (int i = 0; i < rowList.size(); i++) {
				TableItem item = new TableItem(table, SWT.NONE);
				Persons row = rowList.get(i);
				personPid = row.getPersonPid();
				item.setText(0, Integer.toString(personPid));

				np = new NameProvider();
				item.setText(1, np.getPrimaryNameString(personPid));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
