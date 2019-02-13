package net.myerichsen.hremvp.event.parts;

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
import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.event.providers.EventProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Display all data about an personEvent
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. dec. 2018
 */

@SuppressWarnings("restriction")
public class EventViewOld {
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

	private Text textId;
	private Text textLabel;
	private Text textFromDate;
	private Text textFromDateSort;
	private Text textFromOriginal;
	private Text textFromSurety;
	private Button btnNewFrom;
	private Button btnBrowseFrom;
	private Button btnClearFrom;

	private Text textToDate;
	private Text textToDateSort;
	private Text textToOriginal;
	private Text textToSurety;
	private Button btnCopyFromTo;
	private Button btnNewTo;
	private Button btnBrowseTo;
	private Button btnClearTo;
	private Table tablePersons;
	private TableViewer tableViewerPersons;
	private Table tableLocations;
	private TableViewer tableViewerLocations;
	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private final EventProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public EventViewOld() throws SQLException {
		provider = new EventProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("0");
		textLabel.setText("");
		textFromDate.setText("");
		textFromDateSort.setText("");
		textFromOriginal.setText("");
		textFromSurety.setText("");
		textToDate.setText("");
		textToDateSort.setText("");
		textToOriginal.setText("");
		textToSurety.setText("");
		tablePersons.removeAll();
		tableLocations.removeAll();
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
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblLabel = new Label(parent, SWT.NONE);
		lblLabel.setText("Label");

		textLabel = new Text(parent, SWT.BORDER);
		textLabel.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(parent, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(parent, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textFromDateSort = new Text(parent, SWT.BORDER);
		textFromDateSort.setEditable(false);
		textFromDateSort.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		textFromOriginal = new Text(parent, SWT.BORDER);
		textFromOriginal.setEditable(false);
		textFromOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textFromSurety = new Text(parent, SWT.BORDER);
		textFromSurety.setEditable(false);
		textFromSurety.setLayoutData(
				new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		final Composite compositeFrom = new Composite(parent, SWT.NONE);
		compositeFrom.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
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
						if (textFromDateSort.getText().length() == 0) {
							textFromDateSort
									.setText(dialog.getSortDate().toString());
						}
						textFromOriginal.setText(dialog.getOriginal());
						textFromSurety.setText(dialog.getSurety());
					} catch (final Exception e1) {
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
						textFromDateSort.setText(hdp.getSortDate().toString());
						textFromOriginal.setText(hdp.getOriginalText());
						textFromSurety.setText(hdp.getSurety());
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
				textFromDateSort.setText("");
				textFromOriginal.setText("");
				textFromSurety.setText("");
			}
		});
		btnClearFrom.setText("Clear");

		final Label lblToDate = new Label(parent, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(parent, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textToDateSort = new Text(parent, SWT.BORDER);
		textToDateSort.setEditable(false);
		textToDateSort.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		textToOriginal = new Text(parent, SWT.BORDER);
		textToOriginal.setEditable(false);
		textToOriginal.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textToSurety = new Text(parent, SWT.BORDER);
		textToSurety.setEditable(false);
		textToSurety.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		final Composite compositeTo = new Composite(parent, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeTo.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		btnCopyFromTo = new Button(compositeTo, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText(textFromDate.getText());
				if (textToDateSort.getText().length() == 0) {
					textToDateSort.setText(textFromDateSort.getText());
				}
				textToOriginal.setText(textFromOriginal.getText());
				textToSurety.setText(textToSurety.getText());
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
						textToDateSort.setText(dialog.getSortDate().toString());
						textToOriginal.setText(dialog.getOriginal());
						textToSurety.setText(dialog.getSurety());
					} catch (final Exception e1) {
						LOGGER.severe(e1.getMessage());
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
						textToDateSort.setText(hdp.getSortDate().toString());
						textToOriginal.setText(hdp.getOriginalText());
						textToSurety.setText(hdp.getSurety());
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
				textToDateSort.setText("");
				textToOriginal.setText("");
				textToSurety.setText("");
			}
		});
		btnClearTo.setText("Clear");

		final Label lblPersons = new Label(parent, SWT.NONE);
		lblPersons.setText("Persons\r\nDblclk to open");

		tableViewerPersons = new TableViewer(parent,
				SWT.BORDER | SWT.FULL_SELECTION);
		tablePersons = tableViewerPersons.getTable();
		tablePersons.setToolTipText("Double click to edit persons");
		tablePersons.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openPersonView();
			}
		});
		tablePersons.setLinesVisible(true);
		tablePersons.setHeaderVisible(true);
		tablePersons.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumnPersonId = new TableViewerColumn(
				tableViewerPersons, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnPersonId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumnPersonName = new TableViewerColumn(
				tableViewerPersons, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumnPersonName.getColumn();
		tblclmnName.setWidth(200);
		tblclmnName.setText("Name");

		final TableViewerColumn tableViewerColumnPersonRole = new TableViewerColumn(
				tableViewerPersons, SWT.NONE);
		final TableColumn tblclmnRole = tableViewerColumnPersonRole.getColumn();
		tblclmnRole.setWidth(100);
		tblclmnRole.setText("Role");

		final Label lblLocations = new Label(parent, SWT.NONE);
		lblLocations.setText("Locations\r\nDblclk to open");

		tableViewerLocations = new TableViewer(parent,
				SWT.BORDER | SWT.FULL_SELECTION);
		tableLocations = tableViewerLocations.getTable();
		tableLocations.setToolTipText("Double click to edit locatios");
		tableLocations.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLocationView();
			}
		});
		tableLocations.setLinesVisible(true);
		tableLocations.setHeaderVisible(true);
		tableLocations.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumnLocationId = new TableViewerColumn(
				tableViewerLocations, SWT.NONE);
		final TableColumn tblclmnLocationId = tableViewerColumnLocationId
				.getColumn();
		tblclmnLocationId.setWidth(50);
		tblclmnLocationId.setText("ID");

		final TableViewerColumn tableViewerColumnLocationName = new TableViewerColumn(
				tableViewerLocations, SWT.NONE);
		final TableColumn tblclmnLocation = tableViewerColumnLocationName
				.getColumn();
		tblclmnLocation.setWidth(300);
		tblclmnLocation.setText("Location");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
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
			eventBroker.post("MESSAGE",
					"Event " + textId.getText() + " has been deleted");
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
		final int key = Integer.parseInt(textId.getText());
		get(key);
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);

			textId.setText(Integer.toString(provider.getEventPid()));
//			textLabel.setText(provider.getLabel());
			textFromDate.setText(Integer.toString(provider.getFromDatePid()));
			textToDate.setText(Integer.toString(provider.getToDatePid()));

			tablePersons.removeAll();
			tableLocations.removeAll();

			final List<List<String>> personList = provider.getPersonList();
			List<String> ls;

			for (int i = 0; i < personList.size(); i++) {
				final TableItem item = new TableItem(tablePersons, SWT.NONE);
				ls = personList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
			}

			final List<List<String>> locationList = provider.getLocationList();

			for (int i = 0; i < locationList.size(); i++) {
				final TableItem item = new TableItem(tableLocations, SWT.NONE);
				ls = locationList.get(i);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
			}
			eventBroker.post("MESSAGE",
					"Event " + textId.getText() + " has been fetched");

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
			provider.setFromDatePid(Integer.parseInt(textFromDate.getText()));
			provider.setEventPid(Integer.parseInt(textId.getText()));
			provider.setToDatePid(Integer.parseInt(textToDate.getText()));
//			provider.setLabel(textLabel.getText());
			provider.insert();
			eventBroker.post("MESSAGE",
					"Event " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}

	}

	/**
	 *
	 */
	protected void openLocationView() {
		int locationPid = 0;

		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.openlocationview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableLocations.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			locationPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting location pid: " + locationPid);
		eventBroker.post(Constants.LOCATION_PID_UPDATE_TOPIC, locationPid);
	}

	/**
	 *
	 */
	protected void openPersonView() {
		int personPid = 0;

		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.openpersonview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tablePersons.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.info("Setting person pid: " + personPid);
		eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC, personPid);
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 */
	@Inject
	@Optional
	private void subscribeLocationPidUpdateTopic(
			@UIEventTopic(Constants.EVENT_PID_UPDATE_TOPIC) int key) {
		get(key);
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setFromDatePid(Integer.parseInt(textFromDate.getText()));
			provider.setEventPid(Integer.parseInt(textId.getText()));
			provider.setToDatePid(Integer.parseInt(textToDate.getText()));
//			provider.setLabel(textLabel.getText());
			provider.update();
			eventBroker.post("MESSAGE",
					"Location Name " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}

	}

}
