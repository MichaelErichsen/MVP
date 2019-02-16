package net.myerichsen.hremvp.project.parts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.filters.NavigatorFilter;
import net.myerichsen.hremvp.project.dialogs.ProjectNameSummaryDialog;
import net.myerichsen.hremvp.project.models.ProjectList;
import net.myerichsen.hremvp.project.models.ProjectModel;
import net.myerichsen.hremvp.project.providers.ProjectNewDatabaseProvider;
import net.myerichsen.hremvp.project.providers.ProjectProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Navigator part to display and maintain all HRE projects
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 16. feb. 2019
 *
 */
public class ProjectNavigator {
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private EPartService partService;

	private ProjectProvider provider;
	private TableViewer tableViewer;
	private NavigatorFilter navigatorFilter;
	private Text textNameFilter;

	/**
	 * Constructor
	 *
	 */
	public ProjectNavigator() {
		try {
			provider = new ProjectProvider();
			navigatorFilter = new NavigatorFilter();
		} catch (final Exception e) {
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent
	 * @param menuService
	 */
	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService) {
		parent.setLayout(new GridLayout(2, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addFilter(navigatorFilter);

		final Table table = tableViewer.getTable();
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
				postProjectPid();
			}
		});
		menuService.registerContextMenu(tableViewer.getControl(),
				"net.myerichsen.hremvp.popupmenu.projectnavigatormenu");
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1);
		gd_table.widthHint = 394;
		table.setLayoutData(gd_table);

		final TableViewerColumn tableViewerColumnProjectId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnProjectId = tableViewerColumnProjectId
				.getColumn();
		tblclmnProjectId.setWidth(100);
		tblclmnProjectId.setText("Project Id");
		tableViewerColumnProjectId
				.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnProjectName = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnProjectName = tableViewerColumnProjectName
				.getColumn();
		tblclmnProjectName.setWidth(100);
		tblclmnProjectName.setText("Project Name");
		tableViewerColumnProjectName
				.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnLocation = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLocation = tableViewerColumnLocation
				.getColumn();
		tblclmnLocation.setWidth(100);
		tblclmnLocation.setText("Location");
		tableViewerColumnLocation
				.setLabelProvider(new HREColumnLabelProvider(2));

		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmNewProject = new MenuItem(menu, SWT.NONE);
		mntmNewProject.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				newProject(parent.getShell());
			}
		});
		mntmNewProject.setText("New project...");

		MenuItem mntmOpenSelectedProject = new MenuItem(menu, SWT.NONE);
		mntmOpenSelectedProject.setText("Open selected project");

		MenuItem mntmOpenExistingProject = new MenuItem(menu, SWT.NONE);
		mntmOpenExistingProject.setText("Open existing project...");

		MenuItem mntmBackUpSelected = new MenuItem(menu, SWT.NONE);
		mntmBackUpSelected.setText("Back up selected project...");

		MenuItem mntmRestoreAProject = new MenuItem(menu, SWT.NONE);
		mntmRestoreAProject.setText("Restore a project...");

		MenuItem mntmCopyProjectAs = new MenuItem(menu, SWT.NONE);
		mntmCopyProjectAs.setText("Copy project as...");

		MenuItem mntmRenameSelectedProject = new MenuItem(menu, SWT.NONE);
		mntmRenameSelectedProject.setText("Rename selected project...");

		MenuItem mntmDeleteSelectedProject = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedProject.setText("Delete selected project...");

		final Label lblNameFilter = new Label(parent, SWT.NONE);
		lblNameFilter.setText("Name Filter");

		textNameFilter = new Text(parent, SWT.BORDER);
		textNameFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				navigatorFilter.setSearchText(textNameFilter.getText());
				tableViewer.refresh();
			}
		});

		textNameFilter.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.get());
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param shell
	 */
	protected void newProject(Shell shell) {
		// Open file dialog
		final FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setText("Create new HRE Project");
		dialog.setFilterPath("./");
		final String[] extensions = { "*.h2.db", "*.mv.db", "*.*" };
		dialog.setFilterExtensions(extensions);
		dialog.open();

		final String shortName = dialog.getFileName();
		final String[] parts = shortName.split("\\.");
		final String path = dialog.getFilterPath();
		final String dbName = parts[0];

		try {
			// Create the new database
			LOGGER.fine("New database name: " + path + "\\" + dbName);

			store.setValue("DBPATH", path);
			store.setValue("DBNAME", dbName);

			ProjectNewDatabaseProvider pndprovider = new ProjectNewDatabaseProvider();

			pndprovider.provide(dbName);

			// Disconnect from any currently connected database
			Connection conn = null;

			conn = HreH2ConnectionPool.getConnection();

			if (conn != null) {
				conn.createStatement().execute("SHUTDOWN");
				conn.close();
				try {
					HreH2ConnectionPool.dispose();
				} catch (final Exception e) {
					LOGGER.fine("No connection pool to dispose");
				}
			}

			// Connect to the new database
			conn = HreH2ConnectionPool.getConnection(dbName);

			final String h2Version = store.getString("H2VERSION");
			LOGGER.fine("Retrieved H2 version from preferences: "
					+ h2Version.substring(0, 3));
			PreparedStatement ps;

			if (h2Version.substring(0, 3).equals("1.3")) {
				ps = conn.prepareStatement(
						"SELECT TABLE_NAME, 0 FROM INFORMATION_SCHEMA.TABLES "
								+ "WHERE TABLE_TYPE = 'TABLE' ORDER BY TABLE_NAME");
			} else {
				ps = conn.prepareStatement(
						"SELECT TABLE_NAME, ROW_COUNT_ESTIMATE FROM INFORMATION_SCHEMA.TABLES "
								+ "WHERE TABLE_TYPE = 'TABLE' ORDER BY TABLE_NAME");
			}

			ps.executeQuery();
			conn.close();

			// Open a dialog for summary
			final ProjectNameSummaryDialog pnsDialog = new ProjectNameSummaryDialog(
					shell);
			pnsDialog.open();

			// Update the HRE properties
			final LocalDateTime now = LocalDateTime.now();
			final ZonedDateTime zdt = now.atZone(ZoneId.systemDefault());
			final Date timestamp = Date.from(zdt.toInstant());
			final ProjectModel model = new ProjectModel(
					pnsDialog.getProjectName(), timestamp,
					pnsDialog.getProjectSummary(), "LOCAL",
					path + "\\" + dbName);

			LOGGER.fine("New properties " + pnsDialog.getProjectName() + " "
					+ timestamp.toString() + " " + pnsDialog.getProjectSummary()
					+ " LOCAL " + dbName);

			final int index = ProjectList.add(model);

			// Set database name in title bar
			final MWindow window = (MWindow) modelService
					.find("net.myerichsen.hremvp.window.main", application);
			window.setLabel("HRE MVP v0.2 - " + dbName);

			// Open H2 Database Navigator
			final MPart h2dnPart = MBasicFactory.INSTANCE.createPart();
			h2dnPart.setLabel("Database Tables");
			h2dnPart.setContainerData("650");
			h2dnPart.setCloseable(true);
			h2dnPart.setVisible(true);
			h2dnPart.setContributionURI(
					"bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.databaseadmin.H2DatabaseNavigator");
			final List<MPartStack> stacks = modelService
					.findElements(application, null, MPartStack.class, null);
			stacks.get(stacks.size() - 2).getChildren().add(h2dnPart);
			partService.showPart(h2dnPart, PartState.ACTIVATE);

			eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, dbName);
			eventBroker.post(Constants.PROJECT_LIST_UPDATE_TOPIC, index);
			eventBroker.post(Constants.PROJECT_PROPERTIES_UPDATE_TOPIC, index);
			eventBroker.post("MESSAGE",
					"Project database " + dbName + " has been created");
		} catch (final Exception e1) {
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}

	/**
	 *
	 */
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
	private void postProjectPid() {
		final int projectPid = tableViewer.getTable().getSelectionIndex() + 1;
		eventBroker.post(Constants.PROJECT_PROPERTIES_UPDATE_TOPIC, projectPid);
		eventBroker.post(Constants.DATABASE_UPDATE_TOPIC,
				store.getString("DBNAME"));
		LOGGER.fine("Project Navigator posted selection index " + projectPid);
	}

	/**
	 *
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param dbName
	 */
	@Inject
	@Optional
	private void subscribeProjectListUpdateTopic(
			@UIEventTopic(Constants.PROJECT_LIST_UPDATE_TOPIC) int key) {
		LOGGER.fine("Received project index " + key);
		try {
			tableViewer.setInput(provider.get());
			tableViewer.refresh();
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}