package net.myerichsen.hremvp.project.parts;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.filters.NavigatorFilter;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.project.wizards.NewLanguageWizard;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;
import net.myerichsen.hremvp.providers.LanguageProvider;

/**
 * Display all data about languages used in HRE
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 20. feb. 2019
 *
 */
public class LanguageNavigator {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private TableViewer tableViewer;
	private LanguageProvider provider;
	private NavigatorFilter navigatorFilter;

	/**
	 * Constructor
	 *
	 */
	public LanguageNavigator() {
		try {
			provider = new LanguageProvider();
			navigatorFilter = new NavigatorFilter();
		} catch (final Exception e) {
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * Create contents of the view part
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDoubleClick(org.eclipse.
			 * swt.events.MouseEvent)
			 */
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLanguageView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		TableViewerColumn tableViewerColumnIsoCode = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnIsoCode = tableViewerColumnIsoCode.getColumn();
		tblclmnIsoCode.setWidth(100);
		tblclmnIsoCode.setText("ISO Code");
		tableViewerColumnIsoCode
				.setLabelProvider(new HREColumnLabelProvider(1));

		TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnName = tableViewerColumnLabel.getColumn();
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(2));

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		final MenuItem mntmAddLanguage = new MenuItem(menu, SWT.NONE);
		mntmAddLanguage.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewLanguageWizard(context));
				dialog.open();
			}
		});
		mntmAddLanguage.setText("Add Language...");

		final MenuItem mntmDeleteSelectedLanguage = new MenuItem(menu,
				SWT.NONE);
		mntmDeleteSelectedLanguage.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteLanguage(parent.getShell());
			}
		});
		mntmDeleteSelectedLanguage.setText("Delete selected Language...");

		final Label lblNameFilter = new Label(parent, SWT.NONE);
		lblNameFilter.setText("Language Filter");

		final Text textNameFilter = new Text(parent, SWT.BORDER);
		textNameFilter.addKeyListener(new KeyAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.
			 * events.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				navigatorFilter.setSearchText(textNameFilter.getText());
				tableViewer.refresh();
			}
		});

		textNameFilter.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(provider.getLanguageList());

	}

	/**
	 * @param shell
	 */
	protected void deleteLanguage(Shell shell) {
		final TableItem[] selection = tableViewer.getTable().getSelection();

		int personPid = 0;
		String primaryName = null;
		if (selection.length > 0) {
			final TableItem item = selection[0];
			personPid = Integer.parseInt(item.getText(0));
			primaryName = item.getText(1);
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Delete Person " + primaryName, null,
				"Are you sure that you will delete person " + personPid + ", "
						+ primaryName + "?",
				MessageDialog.CONFIRM, 0, new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE",
					"Deletion of person " + primaryName + " has been canceled");
			return;
		}

		try {
			final PersonProvider provider = new PersonProvider();
			provider.delete(personPid);

			LOGGER.info("Person " + primaryName + " has been deleted");
			eventBroker.post("MESSAGE",
					"Person " + primaryName + " has been deleted");
			eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC, personPid);
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	protected void openLanguageView() {
		// TODO Auto-generated method stub

	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO Set the focus to control
	}

}
