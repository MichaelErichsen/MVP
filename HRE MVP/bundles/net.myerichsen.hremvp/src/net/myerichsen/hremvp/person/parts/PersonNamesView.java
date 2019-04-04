package net.myerichsen.hremvp.person.parts;

import java.util.logging.Level;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.person.providers.PersonNameProvider;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.person.wizards.NewPersonNameWizard;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * View and maintain all names of a person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 15. feb. 2019
 *
 */
@SuppressWarnings("restriction")
// TODO Add name type with a language specific label
public class PersonNamesView {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private Text textId;
	private TableViewer tableViewer;
	private PersonProvider provider;
	private int personPid = 0;

	/**
	 * Constructor
	 *
	 */
	public PersonNamesView() {
		try {
			provider = new PersonProvider();
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent
	 * @param context
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblId.setText("Person Id");

		textId = new Text(parent, SWT.BORDER);
		textId.setEditable(false);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setToolTipText(
				"Double click to edit. Right click to add or delete");
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDoubleClick(org.eclipse.
			 * swt.events. MouseEvent)
			 */
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openNamePartNavigator();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("Id");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnName = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumnName.getColumn();
		tblclmnName.setWidth(200);
		tblclmnName.setText("Name");
		tableViewerColumnName.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnFrom = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnFromDate = tableViewerColumnFrom.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");
		tableViewerColumnFrom.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnTo = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnToDate = tableViewerColumnTo.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");
		tableViewerColumnTo.setLabelProvider(new HREColumnLabelProvider(3));

		final TableViewerColumn tableViewerColumnPrimary = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPrimary = tableViewerColumnPrimary.getColumn();
		tblclmnPrimary.setWidth(50);
		tblclmnPrimary.setText("Primary");
		tableViewerColumnPrimary
				.setLabelProvider(new HREColumnLabelProvider(4));

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		final MenuItem mntmNewNameFor = new MenuItem(menu, SWT.NONE);
		mntmNewNameFor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewPersonNameWizard(personPid, context));
				dialog.open();
			}
		});
		mntmNewNameFor.setText("New name for person...");

		final MenuItem mntmDeleteSelectedName = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteName(parent.getShell());
			}
		});
		mntmDeleteSelectedName.setText("Delete selected name...");

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getStringList(personPid));
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
			e1.printStackTrace();
		}
	}

	/**
	 * @param shell
	 */
	protected void deleteName(Shell shell) {
		final TableItem[] selection = tableViewer.getTable().getSelection();

		int namePid = 0;
		String primaryName = null;
		if (selection.length > 0) {
			final TableItem item = selection[0];
			namePid = Integer.parseInt(item.getText(0));
			primaryName = item.getText(1);
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Delete Name " + primaryName, null,
				"Are you sure that you will delete name " + namePid + ", "
						+ primaryName + "?",
				MessageDialog.CONFIRM, 0, new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE",
					"Deletion of name " + primaryName + " has been canceled");
			return;
		}

		try {
			final PersonNameProvider provider = new PersonNameProvider();
			provider.delete(namePid);

			LOGGER.info("Name " + primaryName + " has been deleted");
			eventBroker.post("MESSAGE",
					"Name " + primaryName + " has been deleted");
			eventBroker.post(Constants.PERSON_NAME_PID_UPDATE_TOPIC, 0);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void dispose() {
	}

	/**
	 * @return the tableViewer
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 *
	 */
	protected void openNamePartNavigator() {
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.openpersonnamepartnavigator",
				null);
		handlerService.executeHandler(command);

		final TableItem[] selection = tableViewer.getTable().getSelection();
		final int namePid = Integer.parseInt(selection[0].getText(0));
		LOGGER.info("Setting name pid: " + namePid);
		eventBroker.post(Constants.PERSON_NAME_PID_UPDATE_TOPIC, namePid);
	}

	@Focus
	public void setFocus() {
	}

	/**
	 * @param namePid
	 */
	@Inject
	@Optional
	private void subscribeNamePidUpdateTopic(
			@UIEventTopic(Constants.PERSON_NAME_PID_UPDATE_TOPIC) int namePid) {
		LOGGER.fine("Received name id " + namePid);
		try {
			tableViewer.setInput(provider.getStringList(personPid));
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribePersonPidUpdateTopic(
			@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int personPid) {
		LOGGER.fine("Received person id " + personPid);
		this.personPid = personPid;
		try {
			textId.setText(Integer.toString(personPid));
			tableViewer.setInput(provider.getStringList(personPid));
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}
}
