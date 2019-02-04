package net.myerichsen.hremvp.project.parts;

import java.sql.SQLException;
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
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.filters.NavigatorFilter;
import net.myerichsen.hremvp.project.providers.ProjectProvider;

/**
 * Navigator part to display all tables in an HRE project
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 4. feb. 2019
 *
 */
public class ProjectNavigator {
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

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
			 * org.eclipse.swt.events.MouseAdapter#mouseDoubleClick(org.eclipse.swt.events.
			 * MouseEvent)
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
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table.widthHint = 394;
		table.setLayoutData(gd_table);

		final TableViewerColumn tableViewerColumnProjectId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnProjectId = tableViewerColumnProjectId.getColumn();
		tblclmnProjectId.setWidth(100);
		tblclmnProjectId.setText("Project Id");
		tableViewerColumnProjectId.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(0);
			}
		});

		final TableViewerColumn tableViewerColumnProjectName = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnProjectName = tableViewerColumnProjectName.getColumn();
		tblclmnProjectName.setWidth(100);
		tblclmnProjectName.setText("Project Name");
		tableViewerColumnProjectName.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(1);
			}
		});

		final TableViewerColumn tableViewerColumnLocation = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnLocation = tableViewerColumnLocation.getColumn();
		tblclmnLocation.setWidth(100);
		tblclmnLocation.setText("Location");
		tableViewerColumnLocation.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(2);
			}
		});

		final Label lblNameFilter = new Label(parent, SWT.NONE);
		lblNameFilter.setText("Name Filter");

		textNameFilter = new Text(parent, SWT.BORDER);
		textNameFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				navigatorFilter.setSearchText(textNameFilter.getText());
				LOGGER.info("Filter string: " + textNameFilter.getText());
				tableViewer.refresh();
			}
		});

		textNameFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.get());
			LOGGER.fine("Set input");
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
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
		eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, store.getString("DBNAME"));
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
	private void subscribeProjectListUpdateTopic(@UIEventTopic(Constants.PROJECT_LIST_UPDATE_TOPIC) int key) {
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