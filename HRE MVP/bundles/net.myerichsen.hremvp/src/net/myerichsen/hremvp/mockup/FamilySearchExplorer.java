package net.myerichsen.hremvp.mockup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class FamilySearchExplorer extends AbstractHREGuiPart {
	private Table tableFSSearch;
	private TableViewer tableViewerFSSearch;
	private Shell shell;
	private FamilySearchProvider model;

	@Override
	protected void callBusinessLayer(int i) {
	}

	@PostConstruct
	public void createControls(Composite parent) {
		LOGGER.fine("Create Controls");
		shell = parent.getShell();
		parent.setLayout(new GridLayout(1, false));

		tableViewerFSSearch = new TableViewer(parent, 67584);

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewerFSSearch, 0);
		final TableColumn tblclmnName = tableViewerColumn.getColumn();
		tblclmnName.setWidth(200);
		tblclmnName.setText("Name");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final FamilySearchModel item = (FamilySearchModel) element;
				return item.getName();
			}

		});
		final TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
				tableViewerFSSearch, 0);
		final TableColumn tblclmnGender = tableViewerColumn_5.getColumn();
		tblclmnGender.setWidth(75);
		tblclmnGender.setText("Gender");
		tableViewerColumn_5.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final FamilySearchModel item = (FamilySearchModel) element;
				return item.getGender();
			}

		});
		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewerFSSearch, 0);
		final TableColumn tblclmnBirthDate = tableViewerColumn_1.getColumn();
		tblclmnBirthDate.setWidth(100);
		tblclmnBirthDate.setText("Birth Date");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final FamilySearchModel item = (FamilySearchModel) element;
				return item.getBirthDate();
			}

		});
		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				tableViewerFSSearch, 0);
		final TableColumn tblclmnBirthPlace = tableViewerColumn_2.getColumn();
		tblclmnBirthPlace.setWidth(200);
		tblclmnBirthPlace.setText("Birth Place");
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final FamilySearchModel item = (FamilySearchModel) element;
				return item.getBirthPlace();
			}

		});
		final TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				tableViewerFSSearch, 0);
		final TableColumn tblclmnDeathDate = tableViewerColumn_3.getColumn();
		tblclmnDeathDate.setWidth(100);
		tblclmnDeathDate.setText("Death Date");
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final FamilySearchModel item = (FamilySearchModel) element;
				return item.getDeathDate();
			}

		});
		final TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
				tableViewerFSSearch, 0);
		final TableColumn tblclmnDeathPlace = tableViewerColumn_4.getColumn();
		tblclmnDeathPlace.setWidth(200);
		tblclmnDeathPlace.setText("Death Place");
		tableViewerColumn_4.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final FamilySearchModel item = (FamilySearchModel) element;
				return item.getDeathPlace();
			}

		});
		tableFSSearch = tableViewerFSSearch.getTable();
		tableFSSearch.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		tableFSSearch.setLinesVisible(true);
		tableFSSearch.setHeaderVisible(true);
		tableFSSearch.setFont(getHreFont(parent));

		tableViewerFSSearch
				.setContentProvider(ArrayContentProvider.getInstance());

		model = new FamilySearchProvider(shell);
		tableViewerFSSearch.setInput(model.getItemList());
		tableViewerFSSearch.refresh();
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

	@Inject
	@Optional
	private void subscribeNameUpdateTopic(
			@UIEventTopic("NAME_UPDATE_TOPIC") String perName) {
		LOGGER.fine("Subscribing to " + perName);

		if ((tableFSSearch != null) && (tableViewerFSSearch != null)
				&& (tableFSSearch.isVisible())) {
			updateGui();
		} else {
			LOGGER.fine("Not visible");
		}
	}

	@Override
	protected void updateGui() {
		LOGGER.fine("Updating GUI");

		model = new FamilySearchProvider(shell);
		LOGGER.fine("Set input: Get item list");
		tableViewerFSSearch.setInput(model.getItemList());
		LOGGER.fine("Refresh");
		tableViewerFSSearch.refresh();
	}
}
