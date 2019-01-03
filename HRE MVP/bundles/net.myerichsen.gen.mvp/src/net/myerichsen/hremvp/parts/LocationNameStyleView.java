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
import org.eclipse.e4.core.contexts.IEclipseContext;
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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.providers.HDateProvider;
import net.myerichsen.hremvp.providers.LocationNameStyleProvider;

/**
 * Display all data about a Location Name Style
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 20. nov. 2018
 *
 */

@SuppressWarnings("restriction")
public class LocationNameStyleView {
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
	@Inject
	private IEclipseContext context;

	private ScrolledComposite scrolledComposite;
	private Composite composite_1;

	private Text textId;
	private Text textLanguageId;
	private Text textLanguage;
	private Text textIsoCode;
	private Text textLabel;
	private Table table;
	private TableViewer tableViewer;

	private Text textFromDatePid;
	private Text textFromDate;
	private Text textFromOriginal;
	private Button btnNewFrom;
	private Button btnBrowseFrom;
	private Button btnClearFrom;

	private Text textToDatePid;
	private Text textToDate;
	private Text textToOriginal;
	private Button btnCopyFromTo;
	private Button btnNewTo;
	private Button btnBrowseTo;
	private Button btnClearTo;

	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private LocationNameStyleProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationNameStyleView() throws SQLException {
		provider = new LocationNameStyleProvider();
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
		textFromDatePid.setText("");
		textFromDate.setText("");
		textFromOriginal.setText("");
		textToDatePid.setText("");
		textToDate.setText("");
		textToOriginal.setText("");

		table.removeAll();
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
		parent.setLayout(new GridLayout(1, false));

		scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(4, false));

		Label lblId = new Label(composite_1, SWT.NONE);
		lblId.setText("Location Name Style ID");

		textId = new Text(composite_1, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblLanguage = new Label(composite_1, SWT.NONE);
		lblLanguage.setText("Language");

		textLanguageId = new Text(composite_1, SWT.BORDER);
		textLanguageId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textLanguage = new Text(composite_1, SWT.BORDER);
		textLanguage.setEditable(false);
		textLanguage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textIsoCode = new Text(composite_1, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblName = new Label(composite_1, SWT.NONE);
		lblName.setText("Name");

		textLabel = new Text(composite_1, SWT.BORDER);
		textLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		Label lblLocationNameMaps = new Label(composite_1, SWT.NONE);
		lblLocationNameMaps.setText("Location Name Maps");

		tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setToolTipText("Double click to edit name map");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLocationNameMapView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("ID");

		TableViewerColumn tableViewerColumnPartNo = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPartNo = tableViewerColumnPartNo.getColumn();
		tblclmnPartNo.setWidth(50);
		tblclmnPartNo.setText("Part No.");

		TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(200);
		tblclmnLabel.setText("Label");

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnLabelPosition = tableViewerColumn.getColumn();
		tblclmnLabelPosition.setWidth(100);
		tblclmnLabelPosition.setText("Label Position");

		Label lblFromDate = new Label(composite_1, SWT.NONE);
		lblFromDate.setText("From Date ID");

		textFromDatePid = new Text(composite_1, SWT.BORDER);
		textFromDatePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Composite compositeFrom = new Composite(composite_1, SWT.NONE);
		compositeFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DateDialog dialog = new DateDialog(textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						int datePid = hdp.insert();
						textFromDatePid.setText(Integer.toString(datePid));
						textFromDate.setText(dialog.getLocalDate().toString());
						textFromOriginal.setText(dialog.getOriginal());
					} catch (Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewFrom.setText("New");

		btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DateNavigatorDialog dialog = new DateNavigatorDialog(textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						int hdatePid = dialog.getHdatePid();
						HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textFromDatePid.setText(Integer.toString(hdatePid));
						textFromDate.setText(hdp.getDate().toString());
						textFromOriginal.setText(hdp.getOriginalText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseFrom.setText("Browse");

		btnClearFrom = new Button(compositeFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textFromDatePid.setText("");
				textFromDate.setText("");
				textFromOriginal.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		textFromDate = new Text(composite_1, SWT.BORDER);
		textFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textFromDate.setEditable(false);

		textFromOriginal = new Text(composite_1, SWT.BORDER);
		textFromOriginal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		textFromOriginal.setEditable(false);

		Label lblToDate = new Label(composite_1, SWT.NONE);
		lblToDate.setText("To Date ID");

		textToDatePid = new Text(composite_1, SWT.BORDER);
		textToDatePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Composite compositeTo = new Composite(composite_1, SWT.NONE);
		compositeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnCopyFromTo = new Button(compositeTo, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDatePid.setText(textFromDatePid.getText());
				textToDate.setText(textFromDate.getText());
				textToOriginal.setText(textFromOriginal.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		btnNewTo = new Button(compositeTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DateDialog dialog = new DateDialog(textToDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						int hdatePid = hdp.insert();
						textToDatePid.setText(Integer.toString(hdatePid));
						textToDate.setText(dialog.getLocalDate().toString());
						textToOriginal.setText(dialog.getOriginal());
					} catch (Exception e1) {
						LOGGER.severe(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewTo.setText("New");

		btnBrowseTo = new Button(compositeTo, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DateNavigatorDialog dialog = new DateNavigatorDialog(textToDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						int hdatePid = dialog.getHdatePid();
						HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textToDatePid.setText(Integer.toString(hdatePid));
						textToDate.setText(hdp.getDate().toString());
						textToOriginal.setText(hdp.getOriginalText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowseTo.setText("Browse");

		btnClearTo = new Button(compositeTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDatePid.setText("");
				textToDate.setText("");
				textToOriginal.setText("");
			}
		});
		btnClearTo.setText("Clear");

		textToDate = new Text(composite_1, SWT.BORDER);
		textToDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textToDate.setEditable(false);

		textToOriginal = new Text(composite_1, SWT.BORDER);
		textToOriginal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		textToOriginal.setEditable(false);

		scrolledComposite.setContent(composite_1);
		scrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

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
			eventBroker.post("MESSAGE", "Location Name Style " + textId.getText() + " has been deleted");
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

			textId.setText(Integer.toString(provider.getLocationNameStylePid()));
			textLanguageId.setText(Integer.toString(provider.getLanguagePid()));
			textLanguage.setText(provider.getLanguageLabel());
			textIsoCode.setText(provider.getIsoCode());
			textLabel.setText(provider.getLabel());

			HDateProvider hdp = new HDateProvider();

			try {
				int fromDatePid = provider.getFromDatePid();
				hdp.get(fromDatePid);
				textFromDatePid.setText(Integer.toString(fromDatePid));
				textFromDate.setText(hdp.getDate().toString());
				textFromOriginal.setText(hdp.getOriginalText());
			} catch (Exception e1) {
			}
			try {
				int toDatePid = provider.getToDatePid();
				hdp.get(toDatePid);
				textToDatePid.setText(Integer.toString(toDatePid));
				textToDate.setText(hdp.getDate().toString());
				textToOriginal.setText(hdp.getOriginalText());
			} catch (Exception e) {
			}

			table.removeAll();

			List<LocationNameMaps> mapList = provider.getMapList();
			LocationNameMaps map;

			for (int i = 0; i < mapList.size(); i++) {
				map = mapList.get(i);

				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, Integer.toString(map.getLocationNameMapPid()));
				item.setText(1, Integer.toString(map.getPartNo()));
				item.setText(2, map.getLabel());
				item.setText(3, map.getLabelPosition());
			}
		} catch (Exception e) {
			clear();
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
	 */
	protected int insert() {
		int key = 0;

		try {
			provider.setLabel(textLabel.getText());
			provider.setLanguagePid(Integer.parseInt(textLanguageId.getText()));
			provider.setLocationNameStylePid(Integer.parseInt(textId.getText()));
			provider.setFromDatePid(Integer.parseInt(textFromDatePid.getText()));
			provider.setToDatePid(Integer.parseInt(textToDatePid.getText()));
			key = provider.insert();
			eventBroker.post("MESSAGE", "Location Name Style " + textId.getText() + " has been inserted");
		} catch (Exception e) {
			clear();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
		return key;
	}

	/**
	 *
	 */
	protected void openLocationNameMapView() {
		int nameMapPid = 0;

		// Open Location Name Map View
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openlocationnamemapview", null);
		handlerService.executeHandler(command);

		// Post name map pid
		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			nameMapPid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(net.myerichsen.hremvp.Constants.LOCATION_NAME_MAP_PID_UPDATE_TOPIC, nameMapPid);
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
	private void subscribeLocationNameStyleUpdateTopic(
			@UIEventTopic(Constants.LOCATION_NAME_STYLE_PID_UPDATE_TOPIC) int locationNameStylePid)
			throws SQLException, MvpException {
		get(locationNameStylePid);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setLabel(textLabel.getText());
			provider.setLanguagePid(Integer.parseInt(textLanguageId.getText()));
			provider.setLocationNameStylePid(Integer.parseInt(textId.getText()));
			if (textFromDatePid.getText().length() > 0)
				provider.setFromDatePid(Integer.parseInt(textFromDatePid.getText()));
			if (textToDatePid.getText().length() > 0)
				provider.setToDatePid(Integer.parseInt(textToDatePid.getText()));
			provider.update();
			eventBroker.post("MESSAGE", "Location Name Style " + textId.getText() + " has been updated");
		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
