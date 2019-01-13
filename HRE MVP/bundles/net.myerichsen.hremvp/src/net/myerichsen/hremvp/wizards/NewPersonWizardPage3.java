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
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;

/**
 * Person parents wizard page
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 13. jan. 2019
 *
 */
// FIXME Add context menu to browse or delete parents
@SuppressWarnings("restriction")
public class NewPersonWizardPage3 extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private Table tableParents;

	private TableColumn tblclmnParentPrimary;
	private TableViewerColumn tableViewerColumn;

	public NewPersonWizardPage3(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Parents");
		setDescription("Add primary parents for the new person. More parents can be added later.");
		this.context = context;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableParents = tableViewer.getTable();
		tableParents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNamePartView();
			}
		});
		tableParents.setLinesVisible(true);
		tableParents.setHeaderVisible(true);
		tableParents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnParentId = tableViewerColumnId.getColumn();
		tblclmnParentId.setWidth(100);
		tblclmnParentId.setText("ID");

		final TableViewerColumn tableViewerColumnMap = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnParentName = tableViewerColumnMap.getColumn();
		tblclmnParentName.setWidth(100);
		tblclmnParentName.setText("Parent Name");

		final TableViewerColumn tableViewerColumnPart = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnParentRole = tableViewerColumnPart.getColumn();
		tblclmnParentRole.setWidth(100);
		tblclmnParentRole.setText("Role");

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnParentPrimary = tableViewerColumn.getColumn();
		tblclmnParentPrimary.setWidth(100);
		tblclmnParentPrimary.setText("Primary");
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

		final TableItem[] selectedRows = tableParents.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			namePartPid = selectedRow.getText(0);
		}

		LOGGER.info("Setting name part pid: " + namePartPid);
		eventBroker.post(Constants.NAME_PART_PID_UPDATE_TOPIC, Integer.parseInt(namePartPid));
	}
}
