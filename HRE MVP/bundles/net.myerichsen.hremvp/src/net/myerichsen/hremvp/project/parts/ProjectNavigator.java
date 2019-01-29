package net.myerichsen.hremvp.project.parts;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.ProjectProvider;

/**
 * Navigator part to display all tables in an HRE project
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 27. jan. 2019
 *
 */
public class ProjectNavigator {
//	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Table table;
	private ProjectProvider provider;
	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 */
	public ProjectNavigator() {
		try {
			provider = new ProjectProvider();
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
	public void createControls(Composite parent, EMenuService menuService) {
		LOGGER.info("Creating controls");
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				postProjectPid();
			}
		});
		menuService.registerContextMenu(tableViewer.getControl(),
				"net.myerichsen.hremvp.popupmenu.projectnavigatormenu");
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

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
				List<String> list = (List<String>) element;
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
				List<String> list = (List<String>) element;
				return list.get(1);
			}
		});

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.get());
			LOGGER.info("Set input");
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
	 *
	 */
	private void postProjectPid() {
		final int index = table.getSelectionIndex();
		eventBroker.post(Constants.SELECTION_INDEX_TOPIC, index);
		LOGGER.info("Project Navigator posted selection index " + index);
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
	private void subscribeSelectionIndexTopic(@UIEventTopic(Constants.PROJECT_LIST_UPDATE_TOPIC) int index) {
		LOGGER.info("Added project " + index);
		// FIXME Does not refresh
		tableViewer.refresh();
	}

}
