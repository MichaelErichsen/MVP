package net.myerichsen.gen.mvp.parts;

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

import net.myerichsen.gen.mvp.Constants;
import net.myerichsen.gen.mvp.MvpException;
import net.myerichsen.gen.mvp.providers.NameProvider;

/**
 * Display all data about a name
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 14. nov. 2018
 */
@SuppressWarnings("restriction")
public class NameView {
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
	private Text textPersonPid;
	private Text textNameStylePid;
	private Text textNameStyleLabel;
	private Text textDateFrom;
	private Text textDateTo;
	private Button btnPrimaryName;
	private Table tableNameParts;
	private Composite composite;
	private Button buttonSelect;
	private Button buttonInsert;
	private Button buttonUpdate;
	private Button buttonDelete;
	private Button buttonClear;
	private Button buttonClose;

	private NameProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public NameView() throws SQLException {
		provider = new NameProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("0");
		textPersonPid.setText("0");
		textNameStylePid.setText("0");
		textNameStyleLabel.setText("");
		textDateFrom.setText("");
		textDateTo.setText("");
		tableNameParts.removeAll();
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
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		Label lblPersonId = new Label(parent, SWT.NONE);
		lblPersonId.setText("Person ID");

		textPersonPid = new Text(parent, SWT.BORDER);
		textPersonPid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		Label lblNameStyle = new Label(parent, SWT.NONE);
		lblNameStyle.setText("Name Style\r\nDblClk to open");

		textNameStylePid = new Text(parent, SWT.BORDER);
		textNameStylePid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNameStyleView();
			}
		});
		textNameStylePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textNameStyleLabel = new Text(parent, SWT.BORDER);
		textNameStyleLabel.setEditable(false);
		textNameStyleLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblFromDate = new Label(parent, SWT.NONE);
		lblFromDate.setText("From Date");

		textDateFrom = new Text(parent, SWT.BORDER);
		new Label(parent, SWT.NONE);

		Label lblToDate = new Label(parent, SWT.NONE);
		lblToDate.setText("To Date");

		textDateTo = new Text(parent, SWT.BORDER);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		btnPrimaryName = new Button(parent, SWT.CHECK);
		btnPrimaryName.setText("Primary Name");
		new Label(parent, SWT.NONE);

		Label lblNameParts = new Label(parent, SWT.NONE);
		lblNameParts.setText("Name Parts\r\nDblclk to open");

		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableNameParts = tableViewer.getTable();
		tableNameParts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNamePartView();
			}
		});
		tableNameParts.setLinesVisible(true);
		tableNameParts.setHeaderVisible(true);
		tableNameParts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		TableViewerColumn tableViewerColumnMap = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnLabelFromMap = tableViewerColumnMap.getColumn();
		tblclmnLabelFromMap.setWidth(100);
		tblclmnLabelFromMap.setText("Label from Map");

		TableViewerColumn tableViewerColumnPart = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnValueFromPart = tableViewerColumnPart.getColumn();
		tblclmnValueFromPart.setWidth(100);
		tblclmnValueFromPart.setText("Value from Part");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
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
			eventBroker.post("MESSAGE", "Name " + textId.getText() + " has been deleted");
			clear();
		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
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
			textId.setText(Integer.toString(provider.getNamePid()));
			textPersonPid.setText(Integer.toString(provider.getPersonPid()));
			textNameStylePid.setText(Integer.toString(provider.getNameStylePid()));
			textNameStyleLabel.setText(provider.getNameTypeLabel());

			try {
				textDateFrom.setText(Integer.toString(provider.getFromDatePid()));
			} catch (Exception e) {
				textDateFrom.setText("");
			}

			try {
				textDateTo.setText(Integer.toString(provider.getFromDatePid()));
			} catch (Exception e) {
				textDateTo.setText("");
			}

			btnPrimaryName.setSelection(provider.isPrimaryName());

			tableNameParts.removeAll();

			List<List<String>> nameList = provider.getNameList();
			List<String> ls;

			for (int i = 0; i < nameList.size(); i++) {
				ls = nameList.get(i);
				TableItem item = new TableItem(tableNameParts, SWT.NONE);
				item.setText(0, ls.get(0));
				item.setText(1, ls.get(1));
				item.setText(2, ls.get(2));
			}

			eventBroker.post("MESSAGE", "Name " + textId.getText() + " has been fetched");

		} catch (final SQLException | MvpException e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private void insert() {
		// TODO Insert() missing
		// try {
		// provider = new NameProvider();
		// provider.setNames(new Names());
		// provider.setNamePid(Integer.parseInt(textId.getText()));
		// provider.setPersonPid(Integer.parseInt(textPersonPid.getText()));
		// provider.setNameType(Integer.parseInt(textNameType.getText()));
		//
		// provider.setFromdate(dateTimeFrom.getYear() + "-" + dateTimeFrom.getMonth() +
		// "-" + dateTimeFrom.getDay());
		// Calendar calendar = Calendar.getInstance();
		// dateTimeFrom.setDate(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DATE));
		// dateTimeTo.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DATE));
		// table.removeAll();
		// provider.insert();
		// } catch (final SQLException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 *
	 */
	protected void openNamePartView() {
		String namePartPid = "0";

		// Open an editor
		LOGGER.fine("Opening Name Part View");
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.gen.mvp.command.opennamepartview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableNameParts.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			namePartPid = selectedRow.getText(0);
		}

		LOGGER.info("Setting name part pid: " + namePartPid);
		eventBroker.post(Constants.NAME_PART_PID_UPDATE_TOPIC, Integer.parseInt(namePartPid));
	}

	/**
	 *
	 */
	protected void openNameStyleView() {
		// Open an editor
		LOGGER.fine("Opening Name Style View");
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.gen.mvp.command.opennamestyleview", null);
		handlerService.executeHandler(command);

		int nameStylePid = Integer.parseInt(textNameStylePid.getText());
		LOGGER.info("Setting name style pid: " + nameStylePid);
		eventBroker.post(Constants.NAME_STYLE_PID_UPDATE_TOPIC, nameStylePid);
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 * @throws MvpException
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(@UIEventTopic(Constants.NAME_PID_UPDATE_TOPIC) int key) throws MvpException {
		get(key);
	}

	/**
	 *
	 */
	private void update() {
		// try {
		// provider = new Names();
		// provider.setNamePid(Integer.parseInt(textId.getText()));
		// provider.setPersonPid(Integer.parseInt(textPersonPid.getText()));
		// provider.setNameType(Integer.parseInt(textNameType.getText()));
		// provider.update();
		// } catch (final SQLException e) {
		// e.printStackTrace();
		// }
	}
}
