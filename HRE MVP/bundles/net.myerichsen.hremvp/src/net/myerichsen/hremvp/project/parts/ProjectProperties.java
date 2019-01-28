package net.myerichsen.hremvp.project.parts;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.project.providers.ProjectProvider;

/**
 * GUI part displaying project properties.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 28. jan. 2019
 *
 */
public class ProjectProperties {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private TableViewer tableViewer;
	private ProjectProvider provider;
	private int index = 1;

	/**
	 * Constructor
	 *
	 */
	public ProjectProperties() {
		try {
			provider = new ProjectProvider();
		} catch (final Exception e) {
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumnProperty = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnProjectName = tableViewerColumnProperty.getColumn();
		tblclmnProjectName.setToolTipText("Property");
		tblclmnProjectName.setWidth(100);
		tblclmnProjectName.setText("Property");
		tableViewerColumnProperty.setLabelProvider(new ColumnLabelProvider() {

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

		final TableViewerColumn tableViewerColumnValue = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnLastEdited = tableViewerColumnValue.getColumn();
		tblclmnLastEdited.setToolTipText("Value");
		tblclmnLastEdited.setWidth(800);
		tblclmnLastEdited.setText("Value");
		tableViewerColumnValue.setLabelProvider(new ColumnLabelProvider() {

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
		tableViewer.setInput(provider.getProperties(index));
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
	@Focus
	public void setFocus() {
	}

	/**
	 * @param index
	 */
	@Inject
	@Optional
	private void subscribeSelectionIndexTopic(@UIEventTopic(Constants.SELECTION_INDEX_TOPIC) int index) {
		LOGGER.info("Received index " + index);
		this.index = index;
		tableViewer.refresh();

	}

}
