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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.providers.PersonProvider;

/**
 * Display all data about a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 14. nov. 2018
 */
@SuppressWarnings("restriction")
public class PersonView {
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

	private Text textId;
	private Text textBirthDate;
	private Text textDeathDate;
	private Table tableNames;
	private TableViewer tableViewerNames;
	private Table tableSex;
	private TableViewer tableViewerSex;
	private Table tablePartners;
	private TableViewer tableViewerPartners;
	private Table tableParents;
	private TableViewer tableViewerParents;
	private Table tableChildren;
	private TableViewer tableViewerChildren;
	private Table tableEvents;
	private TableViewer tableViewerEvents;
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
	public PersonView() throws SQLException {
		provider = new PersonProvider();
	}

	/**
	 *
	 */
	private void clear() {
		textId.setText("");
		textBirthDate.setText("");
		textDeathDate.setText("");
		tableNames.removeAll();
		tableSex.removeAll();
		tableParents.removeAll();
		tablePartners.removeAll();
		tableChildren.removeAll();
		tableEvents.removeAll();
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
		parent.setLayout(new GridLayout(5, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		final Label lblBirthDate = new Label(parent, SWT.NONE);
		lblBirthDate.setText("Birth Date");

		textBirthDate = new Text(parent, SWT.BORDER);
		textBirthDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label lblDeathDate = new Label(parent, SWT.NONE);
		lblDeathDate.setText("Death Date");

		textDeathDate = new Text(parent, SWT.BORDER);
		textDeathDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		new Label(parent, SWT.NONE);

		final Label lblNames = new Label(parent, SWT.NONE);
		lblNames.setText("Names\r\nDblclk to open");

		tableViewerNames = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableNames = tableViewerNames.getTable();
		tableNames.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNameView();
			}
		});
		tableNames.setLinesVisible(true);
		tableNames.setHeaderVisible(true);
		tableNames.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnNameId = new TableViewerColumn(tableViewerNames, SWT.NONE);
		final TableColumn tblclmnNameId = tableViewerColumnNameId.getColumn();
		tblclmnNameId.setWidth(100);
		tblclmnNameId.setText("ID");

		final TableViewerColumn tableViewerColumnNameLabel = new TableViewerColumn(tableViewerNames, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumnNameLabel.getColumn();
		tblclmnName.setWidth(250);
		tblclmnName.setText("Name");

		final TableViewerColumn tableViewerColumnNamePrimary = new TableViewerColumn(tableViewerNames, SWT.NONE);
		final TableColumn tblclmnNamePrimary = tableViewerColumnNamePrimary.getColumn();
		tblclmnNamePrimary.setWidth(83);
		tblclmnNamePrimary.setText("Primary");

		final Label lblSex = new Label(parent, SWT.NONE);
		lblSex.setText("Sex\r\nDblclk to open");

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
		tableSex.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

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

		final Label lblPartners = new Label(parent, SWT.NONE);
		lblPartners.setText("Partners\r\nDblclk to open");

		tableViewerPartners = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tablePartners = tableViewerPartners.getTable();
		tablePartners.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openPartnerView();
			}
		});
		tablePartners.setLinesVisible(true);
		tablePartners.setHeaderVisible(true);
		tablePartners.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnPartnersId = new TableViewerColumn(tableViewerPartners, SWT.NONE);
		final TableColumn tblclmnPartnersId = tableViewerColumnPartnersId.getColumn();
		tblclmnPartnersId.setWidth(100);
		tblclmnPartnersId.setText("ID");

		final TableViewerColumn tableViewerColumnPartnersLabel = new TableViewerColumn(tableViewerPartners, SWT.NONE);
		final TableColumn tblclmnPartners = tableViewerColumnPartnersLabel.getColumn();
		tblclmnPartners.setWidth(250);
		tblclmnPartners.setText("Partners");

		final TableViewerColumn tableViewerColumnRole = new TableViewerColumn(tableViewerPartners, SWT.NONE);
		final TableColumn tblclmnPartnerRole = tableViewerColumnRole.getColumn();
		tblclmnPartnerRole.setWidth(100);
		tblclmnPartnerRole.setText("Role");

		final TableViewerColumn tableViewerColumnPartnersPrimary = new TableViewerColumn(tableViewerPartners, SWT.NONE);
		final TableColumn tblclmnPartnersPrimary = tableViewerColumnPartnersPrimary.getColumn();
		tblclmnPartnersPrimary.setWidth(73);
		tblclmnPartnersPrimary.setText("Primary");

		final Label lblParents = new Label(parent, SWT.NONE);
		lblParents.setText("Parents\r\nDblclk to open");

		tableViewerParents = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableParents = tableViewerParents.getTable();
		tableParents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openParentView();
			}
		});
		tableParents.setLinesVisible(true);
		tableParents.setHeaderVisible(true);
		tableParents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnParentsId = new TableViewerColumn(tableViewerParents, SWT.NONE);
		final TableColumn tblclmnParentsId = tableViewerColumnParentsId.getColumn();
		tblclmnParentsId.setWidth(100);
		tblclmnParentsId.setText("ID");

		final TableViewerColumn tableViewerColumnParentsLabel = new TableViewerColumn(tableViewerParents, SWT.NONE);
		final TableColumn tblclmnParents = tableViewerColumnParentsLabel.getColumn();
		tblclmnParents.setWidth(250);
		tblclmnParents.setText("Parents");

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewerParents, SWT.NONE);
		final TableColumn tblclmnParentRole = tableViewerColumn.getColumn();
		tblclmnParentRole.setWidth(100);
		tblclmnParentRole.setText("Role");

		final TableViewerColumn tableViewerColumnParentsPrimary = new TableViewerColumn(tableViewerParents, SWT.NONE);
		final TableColumn tblclmnParentsPrimary = tableViewerColumnParentsPrimary.getColumn();
		tblclmnParentsPrimary.setWidth(73);
		tblclmnParentsPrimary.setText("Primary");

		final Label lblChildren = new Label(parent, SWT.NONE);
		lblChildren.setText("Children\r\nDblclk to open");

		tableViewerChildren = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableChildren = tableViewerChildren.getTable();
		tableChildren.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openChildrenView();
			}
		});
		tableChildren.setLinesVisible(true);
		tableChildren.setHeaderVisible(true);
		tableChildren.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewerChildren, SWT.NONE);
		final TableColumn tblclmnChildrenId = tableViewerColumn_1.getColumn();
		tblclmnChildrenId.setWidth(100);
		tblclmnChildrenId.setText("ID");

		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewerChildren, SWT.NONE);
		final TableColumn tblclmnChildren = tableViewerColumn_2.getColumn();
		tblclmnChildren.setWidth(250);
		tblclmnChildren.setText("Children");

		final Label lblEvents = new Label(parent, SWT.NONE);
		lblEvents.setText("Events\r\nDblclk to open");

		tableViewerEvents = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableEvents = tableViewerEvents.getTable();
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEventView();
			}
		});
		tableEvents.setLinesVisible(true);
		tableEvents.setHeaderVisible(true);
		tableEvents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnEventId = new TableViewerColumn(tableViewerEvents, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnEventId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumnEventLabel = new TableViewerColumn(tableViewerEvents, SWT.NONE);
		final TableColumn tblclmnEvent = tableViewerColumnEventLabel.getColumn();
		tblclmnEvent.setWidth(100);
		tblclmnEvent.setText("Event");

		final TableViewerColumn tableViewerColumnEventRole = new TableViewerColumn(tableViewerEvents, SWT.NONE);
		final TableColumn tblclmnRole = tableViewerColumnEventRole.getColumn();
		tblclmnRole.setWidth(100);
		tblclmnRole.setText("Role");

		final TableViewerColumn tableViewerColumnFromDate = new TableViewerColumn(tableViewerEvents, SWT.NONE);
		final TableColumn tblclmnFromDate = tableViewerColumnFromDate.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");

		final TableViewerColumn tableViewerColumnToDate = new TableViewerColumn(tableViewerEvents, SWT.NONE);
		final TableColumn tblclmnToDate = tableViewerColumnToDate.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");

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
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", "Person" + textId.getText() + " has been deleted");
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
		get(Integer.parseInt(textId.getText()));
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);

			textId.setText(Integer.toString(key));
			try {
				textBirthDate.setText(Integer.toString(provider.getBirthDatePid()));
			} catch (final Exception e) {
				textBirthDate.setText("");
			}

			try {
				textDeathDate.setText(Integer.toString(provider.getDeathDatePid()));
			} catch (final Exception e) {
				textDeathDate.setText("");
			}

			tableNames.removeAll();
			tableSex.removeAll();
			tablePartners.removeAll();
			tableParents.removeAll();
			tableChildren.removeAll();
			tableEvents.removeAll();

			final List<List<String>> nameList = provider.getNameList();
			List<String> ls;

			for (int i = 0; i < nameList.size(); i++) {
				final TableItem item = new TableItem(tableNames, SWT.NONE);
				ls = nameList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
			}

			final List<List<String>> sexesList = provider.getSexesList();

			for (int i = 0; i < sexesList.size(); i++) {
				final TableItem item = new TableItem(tableSex, SWT.NONE);
				ls = sexesList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
			}

			final List<List<String>> partnersList = provider.getPartnerList();

			for (int i = 0; i < partnersList.size(); i++) {
				final TableItem item = new TableItem(tablePartners, SWT.NONE);
				ls = partnersList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
				item.setText(3, ls.get(3));
			}

			final List<List<String>> parentsList = provider.getParentList();

			for (int i = 0; i < parentsList.size(); i++) {
				final TableItem item = new TableItem(tableParents, SWT.NONE);
				ls = parentsList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
				item.setText(3, ls.get(3));
			}

			final List<List<String>> childrenList = provider.getChildrenList();

			for (int i = 0; i < childrenList.size(); i++) {
				final TableItem item = new TableItem(tableChildren, SWT.NONE);
				ls = childrenList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
			}

			final List<List<String>> eventsList = provider.getEventList();

			for (int i = 0; i < eventsList.size(); i++) {
				final TableItem item = new TableItem(tableEvents, SWT.NONE);
				ls = eventsList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
				item.setText(3, ls.get(3));
				item.setText(4, ls.get(4));
			}

			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been fetched");

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
			provider.setBirthDatePid(Integer.parseInt(textBirthDate.getText()));
			provider.setDeathDatePid(Integer.parseInt(textDeathDate.getText()));
			provider.setPersonPid(Integer.parseInt(textId.getText()));
			provider.insert();
			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
	 */
	protected void openChildrenView() {
		int personPid = 0;

		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openpersonview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableChildren.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting person pid: " + personPid);
		eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC, personPid);
	}

	/**
	 *
	 */
	protected void openEventView() {
		int eventPid = 0;

		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openeventview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableEvents.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			eventPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting event pid: " + eventPid);
		eventBroker.post(Constants.EVENT_PID_UPDATE_TOPIC, eventPid);
	}

	/**
	 *
	 */
	protected void openNameView() {
		int namePid = 0;

		final ParameterizedCommand command = commandService.createCommand("net.myerichsen.hremvp.command.opennameview",
				null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableNames.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			namePid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting name pid: " + namePid);
		eventBroker.post(Constants.NAME_PID_UPDATE_TOPIC, namePid);
	}

	/**
	 *
	 */
	protected void openParentView() {
		int personPid = 0;

		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openpersonview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableParents.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting person pid: " + personPid);
		eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC, personPid);
	}

	/**
	 *
	 */
	protected void openPartnerView() {
		int personPid = 0;

		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openpersonview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tablePartners.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting person pid: " + personPid);
		eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC, personPid);
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
			provider.setBirthDatePid(Integer.parseInt(textBirthDate.getText()));
			provider.setDeathDatePid(Integer.parseInt(textDeathDate.getText()));
			provider.setPersonPid(Integer.parseInt(textId.getText()));
			provider.update();
			eventBroker.post("MESSAGE", "Person " + textId.getText() + " has been updated");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

}
