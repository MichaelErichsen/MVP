package net.myerichsen.hremvp.location.parts;

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
import net.myerichsen.hremvp.dbmodels.LocationNameParts;
import net.myerichsen.hremvp.dbmodels.LocationNameStyles;
import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.location.providers.LocationNameProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Display all data, including maps and parts for a single location name
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. jan. 2019
 *
 */
@SuppressWarnings("restriction")
public class LocationNameViewOld {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
	@Inject
	private IEclipseContext context;

	private Text textId;

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

	private Text textLocationNameStylePid;
	private Text textLocationNameStyle;
	private Button btnPrimary;
	private Text textPreposition;
	private Table tableNameParts;
	private TableViewer tableViewerNameParts;

	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private final LocationNameProvider provider;
	private ScrolledComposite scrolledComposite;
	private Composite composite_1;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationNameViewOld() throws SQLException {
		provider = new LocationNameProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("0");
		textLocationNameStylePid.setText("0");
		textLocationNameStyle.setText("");
		textFromDate.setText("");
		textFromOriginal.setText("");
		textToDate.setText("");
		textToOriginal.setText("");
		btnPrimary.setSelection(false);
		tableNameParts.removeAll();
	}

	/**
	 *
	 */
	protected void close() {
		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2)
				.getSelectedElement();
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

		scrolledComposite = new ScrolledComposite(parent,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		composite_1.setLayout(new GridLayout(3, false));

		final Label lblId = new Label(composite_1, SWT.NONE);
		lblId.setText("Location Name ID");

		textId = new Text(composite_1, SWT.BORDER);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		btnPrimary = new Button(composite_1, SWT.CHECK);
		btnPrimary.setText("Primary Name");

		final Label lblStyle = new Label(composite_1, SWT.NONE);
		lblStyle.setText("Location Name Style");

		textLocationNameStylePid = new Text(composite_1, SWT.BORDER);
		textLocationNameStylePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textLocationNameStylePid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLocationNameStyleView();
			}
		});
		textLocationNameStylePid
				.setToolTipText("Double click to edit location name style");

		textLocationNameStyle = new Text(composite_1, SWT.BORDER);
		textLocationNameStyle.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textLocationNameStyle.setEditable(false);

		final Label lblPreposition = new Label(composite_1, SWT.NONE);
		lblPreposition.setText("Preposition");

		textPreposition = new Text(composite_1, SWT.BORDER);
		textPreposition.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		final Label lblLocationNameParts = new Label(composite_1, SWT.NONE);
		lblLocationNameParts.setText("Location Name Parts");

		tableViewerNameParts = new TableViewer(composite_1,
				SWT.BORDER | SWT.FULL_SELECTION);
		tableNameParts = tableViewerNameParts.getTable();
		tableNameParts.setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, false, true, 2, 1));
		tableNameParts.setToolTipText("Double click to edit name map");
		tableNameParts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLocationNamePartView();
			}
		});
		tableNameParts.setLinesVisible(true);
		tableNameParts.setHeaderVisible(true);

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewerNameParts, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumnLabelFromMaps = new TableViewerColumn(
				tableViewerNameParts, SWT.NONE);
		final TableColumn tblclmnLabelFromMaps = tableViewerColumnLabelFromMaps
				.getColumn();
		tblclmnLabelFromMaps.setWidth(200);
		tblclmnLabelFromMaps.setText("Label from Maps");

		final TableViewerColumn tableViewerColumnValueFromParts = new TableViewerColumn(
				tableViewerNameParts, SWT.NONE);
		final TableColumn tblclmnValueFromParts = tableViewerColumnValueFromParts
				.getColumn();
		tblclmnValueFromParts.setWidth(200);
		tblclmnValueFromParts.setText("Value from Parts");

		final Label lblFromDate = new Label(composite_1, SWT.NONE);
		lblFromDate.setText("From Date ID");

		textFromDatePid = new Text(composite_1, SWT.BORDER);
		textFromDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeFrom = new Composite(composite_1, SWT.NONE);
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.insert();
						textFromDate.setText(dialog.getLocalDate().toString());
						textFromOriginal.setText(dialog.getOriginal());
					} catch (final Exception e1) {
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
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textFromDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textFromDate.setText(hdp.getDate().toString());
						textFromOriginal.setText(hdp.getOriginalText());
					} catch (final Exception e1) {
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
				textFromDate.setText("");
				textFromOriginal.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		textFromDate = new Text(composite_1, SWT.BORDER);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textFromDate.setEditable(false);

		textFromOriginal = new Text(composite_1, SWT.BORDER);
		textFromOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		textFromOriginal.setEditable(false);

		final Label lblToDate = new Label(composite_1, SWT.NONE);
		lblToDate.setText("To Date ID");

		textToDatePid = new Text(composite_1, SWT.BORDER);
		textToDatePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Composite compositeTo = new Composite(composite_1, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnCopyFromTo = new Button(compositeTo, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText(textFromDate.getText());
				textToOriginal.setText(textFromOriginal.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		btnNewTo = new Button(compositeTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				final DateDialog dialog = new DateDialog(textToDate.getShell(),
						context);
				if (dialog.open() == Window.OK) {
					try {
						final HDateProvider hdp = new HDateProvider();
						hdp.setDate(dialog.getLocalDate());
						hdp.setSortDate(dialog.getSortDate());
						hdp.setOriginalText(dialog.getOriginal());
						hdp.setSurety(dialog.getSurety());
						hdp.insert();
						textToDate.setText(dialog.getLocalDate().toString());
						textToOriginal.setText(dialog.getOriginal());
					} catch (final Exception e1) {
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
				final DateNavigatorDialog dialog = new DateNavigatorDialog(
						textToDate.getShell(), context);
				if (dialog.open() == Window.OK) {
					try {
						final int hdatePid = dialog.getHdatePid();
						final HDateProvider hdp = new HDateProvider();
						hdp.get(hdatePid);
						textToDate.setText(hdp.getDate().toString());
						textToOriginal.setText(hdp.getOriginalText());
					} catch (final Exception e1) {
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
				textToDate.setText("");
				textToOriginal.setText("");
			}
		});
		btnClearTo.setText("Clear");

		textToDate = new Text(composite_1, SWT.BORDER);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textToDate.setEditable(false);

		textToOriginal = new Text(composite_1, SWT.BORDER);
		textToOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		textToOriginal.setEditable(false);

		scrolledComposite.setContent(composite_1);
		scrolledComposite
				.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		composite = new Composite(parent, SWT.NONE);
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
	 * @throws MvpException
	 * @throws NumberFormatException
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE",
					"Location Name " + textId.getText() + " has been deleted");
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
	 * @param locationNamePid
	 */
	private void get(int locationNamePid) {
		try {
			provider.get(locationNamePid);
			textId.setText(Integer.toString(locationNamePid));
			textLocationNameStylePid.setText(
					Integer.toString(provider.getLocationNameStylePid()));
			textLocationNameStyle.setText(provider.getLocationNameStyleLabel());

			final HDateProvider hdp = new HDateProvider();

			try {
				final int fromDatePid = provider.getFromDatePid();
				hdp.get(fromDatePid);
				textFromDate.setText(hdp.getDate().toString());
				textFromOriginal.setText(hdp.getOriginalText());
			} catch (final Exception e1) {
			}
			try {
				final int toDatePid = provider.getFromDatePid();
				hdp.get(toDatePid);
				textToDate.setText(hdp.getDate().toString());
				textToOriginal.setText(hdp.getOriginalText());
			} catch (final Exception e) {
			}

			textPreposition.setText(provider.getPreposition());
			btnPrimary.setEnabled(provider.isPrimaryLocationName());

			tableNameParts.removeAll();

			final List<LocationNameParts> partList = provider.getPartList();
			final List<LocationNameMaps> mapList = provider.getMapList();

			for (int i = 0; i < partList.size(); i++) {
				final TableItem item = new TableItem(tableNameParts, SWT.NONE);
				item.setText(0, Integer
						.toString(partList.get(i).getLocationNamePartPid()));
				item.setText(1, mapList.get(i).getLabel());
				item.setText(2, partList.get(i).getLabel());
			}

			eventBroker.post("MESSAGE",
					"Location Name " + textId.getText() + " has been fetched");
		} catch (final Exception e) {
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
			provider.setLocationNamePid(Integer.parseInt(textId.getText()));
			provider.setLocationNameStylePid(
					Integer.parseInt(textLocationNameStylePid.getText()));
			provider.setPrimaryLocationName(btnPrimary.getSelection());
			provider.setFromDatePid(
					Integer.parseInt(textFromDatePid.getText()));
			provider.setToDatePid(Integer.parseInt(textToDatePid.getText()));
			provider.setPreposition(textPreposition.getText());
			provider.insert();
			eventBroker.post("MESSAGE",
					"Location Name " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 *
	 */
	protected void openLocationNamePartView() {
		int namePartPid = 0;

		// Open Location Name Map View
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.openlocationnamepartview", null);
		handlerService.executeHandler(command);

		// Post name map pid
		final TableItem[] selectedRows = tableNameParts.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			namePartPid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(
				net.myerichsen.hremvp.Constants.LOCATION_NAME_PART_PID_UPDATE_TOPIC,
				namePartPid);
	}

	/**
	 * @throws SQLException
	 *
	 */
	protected void openLocationNameStyleView() {
		int locationNameStylePid = 0;

		// Open Location Name Map View
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.openlocationnamestyleview",
				null);
		handlerService.executeHandler(command);

		final String nameStyle = textLocationNameStyle.getText();

		// Get Location Name Style Pid from name style label
		List<LocationNameStyles> styleList;
		try {
			styleList = new LocationNameStyles().get();

			for (final LocationNameStyles locationNameStyles : styleList) {
				if (locationNameStyles.getLabel().equals(nameStyle)) {
					locationNameStylePid = locationNameStyles
							.getLocationNameStylePid();
					break;
				}
			}
		} catch (final SQLException e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

		eventBroker.post(
				net.myerichsen.hremvp.Constants.LOCATION_NAME_STYLE_PID_UPDATE_TOPIC,
				locationNameStylePid);
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param tableName
	 * @throws SQLException
	 * @throws MvpException
	 */
	@Inject
	@Optional
	private void subscribeNameUpdateTopic(
			@UIEventTopic(Constants.LOCATION_NAME_PID_UPDATE_TOPIC) int locationNamePid)
			throws SQLException, MvpException {
		LOGGER.info("Location name pid: " + locationNamePid);
		get(locationNamePid);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setLocationNamePid(Integer.parseInt(textId.getText()));
			provider.setLocationNameStylePid(
					Integer.parseInt(textLocationNameStylePid.getText()));
			provider.setPrimaryLocationName(btnPrimary.getSelection());
			provider.setFromDatePid(
					Integer.parseInt(textFromDatePid.getText()));
			provider.setToDatePid(Integer.parseInt(textToDatePid.getText()));
			provider.setPreposition(textPreposition.getText());
			provider.update();
			eventBroker.post("MESSAGE",
					"Location Name " + textId.getText() + " has been updated");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

}
