package net.myerichsen.hremvp.project.parts;

import java.sql.SQLException;
import java.util.ArrayList;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;
import net.myerichsen.hremvp.project.wizards.NewSexTypeWizard;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all sex types
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 4. mar. 2019
 *
 */
@SuppressWarnings("restriction")
public class SexTypeNavigator {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	@Inject
	private IEventBroker eventBroker;

	private final SexTypeProvider provider;
	private TableViewer tableViewer;
	private int sexTypePid = 0;
	private final int labelPid = 0;

	/**
	 * Constructor
	 *
	 */
	public SexTypeNavigator() {
		provider = new SexTypeProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent  The parent composite
	 * @param context
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.
			 * eclipse.jface.viewers.DoubleClickEvent)
			 */
			@Override
			public void doubleClick(DoubleClickEvent event) {
				openSexTypeView();
			}
		});

		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnSexTypeId = tableViewerColumnId.getColumn();
		tblclmnSexTypeId.setWidth(40);
		tblclmnSexTypeId.setText("Sex Type ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabelId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabelId = tableViewerColumnLabelId.getColumn();
		tblclmnLabelId.setWidth(80);
		tblclmnLabelId.setText("Dictionary Label ID");
		tableViewerColumnLabelId
				.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnAbbrev = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnAbbreviation = tableViewerColumnAbbrev
				.getColumn();
		tblclmnAbbreviation.setWidth(100);
		tblclmnAbbreviation.setText("Abbreviation");
		tableViewerColumnAbbrev.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabelInEnglish = tableViewerColumnLabel
				.getColumn();
		tblclmnLabelInEnglish.setWidth(100);
		tblclmnLabelInEnglish.setText("Sex Type");
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(3));

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		final MenuItem mntmAddSexType = new MenuItem(menu, SWT.NONE);
		mntmAddSexType.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewSexTypeWizard(context));
				dialog.open();
			}
		});
		mntmAddSexType.setText("Add sex type...");

		final MenuItem mntmDeleteSelectedSex = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedSex.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSexType(parent.getShell());

			}
		});
		mntmDeleteSelectedSex.setText("Delete selected sex type...");

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getStringList());
		} catch (final SQLException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}

	/**
	 * @param shell
	 *
	 */
	protected void deleteSexType(Shell shell) {
		final TableItem[] selection = tableViewer.getTable().getSelection();

		String primaryName = null;
		if (selection.length > 0) {
			final TableItem item = selection[0];
			sexTypePid = Integer.parseInt(item.getText(0));
			primaryName = item.getText(3);
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Delete sex type " + primaryName, null,
				"Are you sure that you will delete sex type " + sexTypePid
						+ ", " + primaryName + "?",
				MessageDialog.CONFIRM, 0, new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE", "Deletion of sex type " + primaryName
					+ " has been canceled");
			return;
		}

		try {
			final SexTypeProvider provider = new SexTypeProvider();
			provider.delete(sexTypePid);
			LOGGER.info("Sex type " + primaryName + " has been deleted");
			eventBroker.post("MESSAGE",
					"Sex type " + primaryName + " has been deleted");
			eventBroker.post(Constants.SEX_TYPE_PID_UPDATE_TOPIC, sexTypePid);
		} catch (SQLException | MvpException e) {
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
	protected void openSexTypeView() {
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.opensextypeview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			sexTypePid = Integer.parseInt(selectedRow.getText(0));
			final List<String> ls = new ArrayList<>();
			ls.add(selectedRow.getText(0));
			ls.add(selectedRow.getText(1));
			ls.add(selectedRow.getText(2));

			eventBroker.post(Constants.LABEL_PID_UPDATE_TOPIC, ls);
		}
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
	private void subscribeSexTypePidUpdateTopic(
			@UIEventTopic(Constants.SEX_TYPE_PID_UPDATE_TOPIC) int sexTypePid) {
		LOGGER.fine("Received sex type id " + sexTypePid);
		this.sexTypePid = sexTypePid;

		if (sexTypePid > 0) {
			try {
				tableViewer.setInput(provider.getStringList());
				tableViewer.refresh();

				final TableItem[] items = tableViewer.getTable().getItems();
				final String item0 = Integer.toString(sexTypePid);

				for (int i = 0; i < items.length; i++) {
					if (item0.equals(items[i].getText(0))) {
						tableViewer.getTable().setSelection(i);
						break;
					}
				}
			} catch (final SQLException e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}