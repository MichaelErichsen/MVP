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
 * Person name wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 13. jan. 2019
 *
 */
// FIXME Better handling of person name style. Primary as default
@SuppressWarnings("restriction")
public class NewPersonWizardPage2 extends WizardPage {
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
	private Button btnNewFrom;
	private Button btnBrowseFrom;
	private Button btnClearFrom;

	private Text textToDate;
	private Button btnCopyFromTo;
	private Button btnNewTo;
	private Button btnBrowseTo;
	private Button btnClearTo;

	private Table tableNameParts;

	private int fromDatePid;
	private int toDatePid;
	private Text textPersonNameStylePid;

	public NewPersonWizardPage2(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person name");
		setDescription("Add a name for the new person. More names can be added later.");
		this.context = context;
	}

	/**
	 *
	 */
	private void browseFromDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(textFromDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textFromDate.setText(hdp.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void browseToDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(textToDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textToDate.setText(hdp.getDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void clearFromDate() {
		textFromDate.setText("");
	}

	/**
	 *
	 */
	private void clearToDate() {
		textToDate.setText("");
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));

		final Label lblPersonNameStyle = new Label(container, SWT.NONE);
		lblPersonNameStyle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPersonNameStyle.setText("Person Name Style");

		textPersonNameStylePid = new Text(container, SWT.BORDER);
		textPersonNameStylePid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		textPersonNameStyle = new Text(container, SWT.BORDER);
		textPersonNameStyle.setEditable(false);
		textPersonNameStyle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblFromDate = new Label(container, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(container, SWT.BORDER);
		textFromDate.setEditable(false);
		textFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeFrom = new Composite(container, SWT.NONE);
		compositeFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnNewFrom = new Button(compositeFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewFromDate();
			}
		});
		btnNewFrom.setText("New");

		btnBrowseFrom = new Button(compositeFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseFromDates();
			}
		});
		btnBrowseFrom.setText("Browse");

		btnClearFrom = new Button(compositeFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearFromDate();
			}
		});
		btnClearFrom.setText("Clear");

		final Label lblToDate = new Label(container, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(container, SWT.BORDER);
		textToDate.setEditable(false);
		textToDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Composite compositeTo = new Composite(container, SWT.NONE);
		compositeTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		btnCopyFromTo = new Button(compositeTo, SWT.NONE);
		btnCopyFromTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textToDate.setText(textFromDate.getText());
			}
		});
		btnCopyFromTo.setText("Copy From");

		btnNewTo = new Button(compositeTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewToDate();
			}
		});
		btnNewTo.setText("New");

		btnBrowseTo = new Button(compositeTo, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseToDates();
			}
		});
		btnBrowseTo.setText("Browse");

		btnClearTo = new Button(compositeTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearToDate();
			}
		});
		btnClearTo.setText("Clear");

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableNameParts = tableViewer.getTable();
		tableNameParts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNamePartView();
			}
		});
		tableNameParts.setLinesVisible(true);
		tableNameParts.setHeaderVisible(true);
		tableNameParts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumnMap = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnLabelFromMap = tableViewerColumnMap.getColumn();
		tblclmnLabelFromMap.setWidth(134);
		tblclmnLabelFromMap.setText("Label from Map");

		final TableViewerColumn tableViewerColumnPart = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnValueFromPart = tableViewerColumnPart.getColumn();
		tblclmnValueFromPart.setWidth(293);
		tblclmnValueFromPart.setText("Value from Part");
	}

	/**
	 *
	 */
	private void getNewFromDate() {
		final DateDialog dialog = new DateDialog(textFromDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				fromDatePid = hdp.insert();
				textFromDate.setText(dialog.getLocalDate().toString());
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	private void getNewToDate() {
		final DateDialog dialog = new DateDialog(textToDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getLocalDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				toDatePid = hdp.insert();
				textToDate.setText(dialog.getLocalDate().toString());
			} catch (final Exception e1) {
				LOGGER.severe(e1.getMessage());
			}
		}
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

		final TableItem[] selectedRows = tableNameParts.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			namePartPid = selectedRow.getText(0);
		}

		LOGGER.info("Setting name part pid: " + namePartPid);
		eventBroker.post(Constants.NAME_PART_PID_UPDATE_TOPIC, Integer.parseInt(namePartPid));
	}
}
