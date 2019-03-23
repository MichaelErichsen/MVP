package net.myerichsen.hremvp.person.parts;

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
import org.eclipse.jface.viewers.ArrayContentProvider;
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
import net.myerichsen.hremvp.person.providers.PersonNameProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all data about a name
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 10. feb. 2019
 */
@SuppressWarnings("restriction")
public class PersonNamePartNavigator {
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
	private Text textPersonPid;
	private Text textNameStylePid;
	private Text textNameStyleLabel;
	private Text textDateFrom;
	private Text textDateTo;
	private Button btnPrimaryName;

	private final PersonNameProvider provider;

	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 *
	 */
	public PersonNamePartNavigator() throws Exception {
		provider = new PersonNameProvider();
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
		tableViewer.getTable().removeAll();
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
		parent.setLayout(new GridLayout(3, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		final Label lblPersonId = new Label(parent, SWT.NONE);
		lblPersonId.setText("Person ID");

		textPersonPid = new Text(parent, SWT.BORDER);
		textPersonPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);

		final Label lblNameStyle = new Label(parent, SWT.NONE);
		lblNameStyle.setText("Name Style\r\nDblClk to open");

		textNameStylePid = new Text(parent, SWT.BORDER);
		textNameStylePid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNameStyleView();
			}
		});
		textNameStylePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textNameStyleLabel = new Text(parent, SWT.BORDER);
		textNameStyleLabel.setEditable(false);
		textNameStyleLabel.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(parent, SWT.NONE);
		lblFromDate.setText("From Date");

		textDateFrom = new Text(parent, SWT.BORDER);
		new Label(parent, SWT.NONE);

		final Label lblToDate = new Label(parent, SWT.NONE);
		lblToDate.setText("To Date");

		textDateTo = new Text(parent, SWT.BORDER);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		btnPrimaryName = new Button(parent, SWT.CHECK);
		btnPrimaryName.setText("Primary Name");
		new Label(parent, SWT.NONE);

		final Label lblPersonNameParts = new Label(parent, SWT.NONE);
		lblPersonNameParts.setText("Name Parts\r\nDblclk to open");

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table tablePersonNameParts = tableViewer.getTable();
		tablePersonNameParts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNamePartView();
			}
		});
		tablePersonNameParts.setLinesVisible(true);
		tablePersonNameParts.setHeaderVisible(true);
		tablePersonNameParts.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnMap = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabelFromMap = tableViewerColumnMap
				.getColumn();
		tblclmnLabelFromMap.setWidth(100);
		tblclmnLabelFromMap.setText("Label from Map");
		tableViewerColumnMap.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnPart = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnValueFromPart = tableViewerColumnPart
				.getColumn();
		tblclmnValueFromPart.setWidth(100);
		tblclmnValueFromPart.setText("Value from Part");
		tableViewerColumnPart.setLabelProvider(new HREColumnLabelProvider(2));

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonInsert = new Button(composite, SWT.NONE);
		buttonInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				insert();
			}
		});
		buttonInsert.setText("Insert");

		final Button buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		buttonUpdate.setText("Update");

		final Button buttonDelete = new Button(composite, SWT.NONE);
		buttonDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});
		buttonDelete.setText("Delete");

		final Button buttonClear = new Button(composite, SWT.NONE);
		buttonClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear();
			}
		});
		buttonClear.setText("Clear");

		final Button buttonClose = new Button(composite, SWT.NONE);
		buttonClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonClose.setText("Close");

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(provider.getNameList());
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE",
					"Name " + textId.getText() + " has been deleted");
			clear();
		} catch (final Exception e) {
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
	private void insert() {
		// TODO Insert() missing
		// try {
		// provider = new PersonNameProvider();
		// provider.setNames(new Names());
		// provider.setNamePid(Integer.parseInt(textId.getText()));
		// provider.setPersonPid(Integer.parseInt(textPersonPid.getText()));
		// provider.setNameType(Integer.parseInt(textNameType.getText()));
		//
		// provider.setFromdate(dateTimeFrom.getYear() + "-" +
		// dateTimeFrom.getMonth() +
		// "-" + dateTimeFrom.getDay());
		// Calendar calendar = Calendar.getInstance();
		// dateTimeFrom.setDate(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DATE));
		// dateTimeTo.setDate(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DATE));
		// table.removeAll();
		// provider.insert();
		// } catch (final Exception e) {
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
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.opennamepartview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			namePartPid = selectedRow.getText(0);
		}

		LOGGER.info("Setting name part pid: " + namePartPid);
		eventBroker.post(Constants.PERSON_NAME_PART_PID_UPDATE_TOPIC,
				Integer.parseInt(namePartPid));
	}

	/**
	 *
	 */
	protected void openNameStyleView() {
		// Open an editor
		LOGGER.fine("Opening Name Style View");
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.opennamestyleview", null);
		handlerService.executeHandler(command);

		final int nameStylePid = Integer.parseInt(textNameStylePid.getText());
		LOGGER.info("Setting name style pid: " + nameStylePid);
		eventBroker.post(Constants.PERSON_NAME_STYLE_PID_UPDATE_TOPIC,
				nameStylePid);
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribeNamePidUpdateTopic(
			@UIEventTopic(Constants.PERSON_NAME_PID_UPDATE_TOPIC) int key) {
		LOGGER.fine("Received name id " + key);

		if (key == 0) {
			return;
		}

		try {
			provider.get(key);
			textId.setText(Integer.toString(provider.getNamePid()));
			textPersonPid.setText(Integer.toString(provider.getPersonPid()));
			textNameStylePid
					.setText(Integer.toString(provider.getNameStylePid()));
			textNameStyleLabel.setText(provider.getNameTypeLabel());

			try {
				textDateFrom
						.setText(Integer.toString(provider.getFromDatePid()));
			} catch (final Exception e) {
				textDateFrom.setText("");
			}

			try {
				textDateTo.setText(Integer.toString(provider.getFromDatePid()));
			} catch (final Exception e) {
				textDateTo.setText("");
			}

			btnPrimaryName.setSelection(provider.isPrimaryName());

			tableViewer.setInput(provider.getNameList());
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
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
		// } catch (final Exception e) {
		// e.printStackTrace();
		// }
	}
}
