package net.myerichsen.hremvp.wizards;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Person events wizard page
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 13. jan. 2019
 *
 */
// FIXME Add conetx menu to add, browse, edit or delete events
@SuppressWarnings("restriction")
public class NewPersonWizardPage4 extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private Text textPersonNameStyle;

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

	private Table tableEvents;

	private int fromDatePid;
	private int toDatePid;
	private TableColumn tblclmnFromDate;
	private TableViewerColumn tableViewerColumn;
	private TableColumn tblclmnToDate;
	private TableViewerColumn tableViewerColumn_1;

	public NewPersonWizardPage4(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Events");
		setDescription("Add events for the new person. More events can be added later.");
		this.context = context;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		
		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableEvents = tableViewer.getTable();
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNamePartView();
			}
		});
		tableEvents.setLinesVisible(true);
		tableEvents.setHeaderVisible(true);
		tableEvents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventId = tableViewerColumnId.getColumn();
		tblclmnEventId.setWidth(100);
		tblclmnEventId.setText("ID");

		final TableViewerColumn tableViewerColumnMap = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventLabel = tableViewerColumnMap.getColumn();
		tblclmnEventLabel.setWidth(100);
		tblclmnEventLabel.setText("Event label");

		final TableViewerColumn tableViewerColumnPart = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnEventRole = tableViewerColumnPart.getColumn();
		tblclmnEventRole.setWidth(100);
		tblclmnEventRole.setText("Role in event");
		
		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnFromDate = tableViewerColumn.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");
		
		tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnToDate = tableViewerColumn_1.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");
	}

	/**
	 *
	 */
	protected void openNamePartView() {
		String namePartPid = "0";

		// Open an editor
		LOGGER.fine("Opening Name Part View");
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.opennamepartview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableEvents.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			namePartPid = selectedRow.getText(0);
		}

		LOGGER.info("Setting name part pid: " + namePartPid);
		eventBroker.post(Constants.NAME_PART_PID_UPDATE_TOPIC, Integer.parseInt(namePartPid));
	}
}
