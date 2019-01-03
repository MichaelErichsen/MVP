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
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.NameMaps;
import net.myerichsen.hremvp.providers.NameStyleProvider;

/**
 * Display all data about a Name Style
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 29. sep. 2018
 *
 */
@SuppressWarnings("restriction")
public class NameStyleView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	@Inject
	private IEventBroker eventBroker;

	private Text textId;
	private Text textLanguageId;
	private Text textLanguage;
	private Text textIsoCode;
	private Text textLabel;
	private Table tableMaps;
	private TableViewer tableViewerMaps;
	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private NameStyleProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public NameStyleView() throws SQLException {
		provider = new NameStyleProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("0");
		textLanguageId.setText("0");
		textLanguage.setText("");
		textIsoCode.setText("");
		textLabel.setText("");

		tableMaps.removeAll();
	}

	/**
	 *
	 */
	protected void close() {
		final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2).getSelectedElement();
		partService.hidePart(part, true);
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 * @throws MvpException Application specific exception
	 */
	@PostConstruct
	public void createControls(Composite parent) throws MvpException {
		parent.setLayout(new GridLayout(4, false));

		Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		Label lblLanguage = new Label(parent, SWT.NONE);
		lblLanguage.setText("Language");

		textLanguageId = new Text(parent, SWT.BORDER);
		textLanguageId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textLanguage = new Text(parent, SWT.BORDER);
		textLanguage.setEditable(false);
		textLanguage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textIsoCode = new Text(parent, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblName = new Label(parent, SWT.NONE);
		lblName.setText("Name");

		textLabel = new Text(parent, SWT.BORDER);
		textLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label lblNameMaps = new Label(parent, SWT.NONE);
		lblNameMaps.setText(" Name Maps\r\nDblclk to open");

		tableViewerMaps = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableMaps = tableViewerMaps.getTable();
		tableMaps.setToolTipText("Double click to edit name map");
		tableMaps.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNameMapView();
			}
		});
		tableMaps.setLinesVisible(true);
		tableMaps.setHeaderVisible(true);
		tableMaps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewerMaps, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("ID");

		TableViewerColumn tableViewerColumnPartNo = new TableViewerColumn(tableViewerMaps, SWT.NONE);
		TableColumn tblclmnPartNo = tableViewerColumnPartNo.getColumn();
		tblclmnPartNo.setWidth(50);
		tblclmnPartNo.setText("Part No.");

		TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(tableViewerMaps, SWT.NONE);
		TableColumn tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(200);
		tblclmnLabel.setText("Label");

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewerMaps, SWT.NONE);
		TableColumn tblclmnLabelPosition = tableViewerColumn.getColumn();
		tblclmnLabelPosition.setWidth(100);
		tblclmnLabelPosition.setText("Label Position");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
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
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE", " Name Part " + textId.getText() + " has been deleted");
			clear();
		} catch (SQLException | NumberFormatException | MvpException e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

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
	protected void get(int key) {
		try {
			provider.get(key);

			textId.setText(Integer.toString(provider.getNameStylePid()));
			textLanguageId.setText(Integer.toString(provider.getLanguagePid()));
			textLanguage.setText(provider.getLanguageLabel());
			textIsoCode.setText(provider.getIsoCode());
			textLabel.setText(provider.getLabel());

			tableMaps.removeAll();

			List<NameMaps> mapList = provider.getMapList();
			NameMaps map;

			for (int i = 0; i < mapList.size(); i++) {
				map = mapList.get(i);

				TableItem item = new TableItem(tableMaps, SWT.NONE);
				item.setText(0, Integer.toString(map.getNameMapPid()));
				item.setText(1, Integer.toString(map.getPartNo()));
				item.setText(2, map.getLabel());
			}
		} catch (Exception e) {
			clear();
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
			provider.setLabel(textLabel.getText());
			provider.setLanguagePid(Integer.parseInt(textLanguageId.getText()));
			provider.setNameStylePid(Integer.parseInt(textId.getText()));
			provider.insert();
		} catch (Exception e) {
			clear();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
	 */
	protected void openNameMapView() {
		int nameMapPid = 0;

		// Open Name Map View
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.opennamemapview", null);
		handlerService.executeHandler(command);

		// Post name map pid
		final TableItem[] selectedRows = tableMaps.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			nameMapPid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(net.myerichsen.hremvp.Constants.NAME_MAP_PID_UPDATE_TOPIC, nameMapPid);
	}

	@Focus
	public void setFocus() {
	}

	/**
	 * @param recordNumString
	 * @throws SQLException When failing
	 * @throws MvpException Application specific exception
	 */
	@Inject
	@Optional
	private void subscribeNameStyleUpdateTopic(@UIEventTopic(Constants.NAME_STYLE_PID_UPDATE_TOPIC) int nameStylePid)
			throws SQLException, MvpException {
		LOGGER.info("Got pid " + nameStylePid);
		get(nameStylePid);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setLabel(textLabel.getText());
			provider.setLanguagePid(Integer.parseInt(textLanguageId.getText()));
			provider.setNameStylePid(Integer.parseInt(textId.getText()));
			provider.update();
		} catch (Exception e) {
			clear();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}
}
