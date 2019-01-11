package net.myerichsen.hremvp.parts;

import java.sql.SQLException;
import java.util.List;
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
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.providers.PersonProvider;

/**
 * Display all data about a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 8. jan. 2019
 */
@SuppressWarnings("restriction")
public class PersonSexesView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	private Table tableSex;
	private TableViewer tableViewerSex;
	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

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
	 *
	 */
	private void clear() {
		tableSex.removeAll();
	}

	/**
	 *
	 */
	private void close() {
		final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2).getSelectedElement();
		partService.hidePart(part, true);
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewerSex = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableSex = tableViewerSex.getTable();
		tableSex.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openSexView();
			}
		});
		tableSex.setLinesVisible(true);
		tableSex.setHeaderVisible(true);
		tableSex.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));

		final TableViewerColumn tableViewerColumnSexId = new TableViewerColumn(tableViewerSex, SWT.NONE);
		final TableColumn tblclmnSexId = tableViewerColumnSexId.getColumn();
		tblclmnSexId.setWidth(100);
		tblclmnSexId.setText("ID");

		final TableViewerColumn tableViewerColumnSexLabel = new TableViewerColumn(tableViewerSex, SWT.NONE);
		final TableColumn tblclmnSex = tableViewerColumnSexLabel.getColumn();
		tblclmnSex.setWidth(250);
		tblclmnSex.setText("Sex");

		final TableViewerColumn tableViewerColumnSexPrimary = new TableViewerColumn(tableViewerSex, SWT.NONE);
		final TableColumn tblclmnSexPrimary = tableViewerColumnSexPrimary.getColumn();
		tblclmnSexPrimary.setWidth(83);
		tblclmnSexPrimary.setText("Primary");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		buttonSelect = new Button(composite, SWT.NONE);
		buttonSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				get();
			}
		});
		buttonSelect.setText("Select");

		buttonInsert = new Button(composite, SWT.NONE);
		buttonInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				insert();
			}
		});
		buttonInsert.setText("Insert");

		buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		buttonUpdate.setText("Update");

		buttonDelete = new Button(composite, SWT.NONE);
		buttonDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});
		buttonDelete.setText("Delete");

		buttonClear = new Button(composite, SWT.NONE);
		buttonClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear();
			}
		});
		buttonClear.setText("Clear");

		buttonClose = new Button(composite, SWT.NONE);
		buttonClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonClose.setText("Close");

		get(1);
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			clear();
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
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
	private void get() {
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);
			tableSex.removeAll();

			List<String> ls;


			final List<List<String>> sexesList = provider.getSexesList();

			for (int i = 0; i < sexesList.size(); i++) {
				final TableItem item = new TableItem(tableSex, SWT.NONE);
				ls = sexesList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
			}

			

		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
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
	protected void openSexView() {
		int sexPid = 0;

		final ParameterizedCommand command = commandService.createCommand("net.myerichsen.hremvp.command.opensexview",
				null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableSex.getSelection();

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
		get(key);
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