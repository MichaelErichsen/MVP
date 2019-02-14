package net.myerichsen.hremvp.mockup;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class PolRegLocationExplorer extends AbstractHREGuiPart {
	private Table tablePolRegLocation;
	private TableViewer tableViewerPolRegLocation;
	private PolRegLocationProvider polRegLocationProvider;
	protected String registerbladId;
	private TableColumn tblclmnAddress;
	private TableViewerColumn tableViewerColumn;
	private TableColumn tblclmnDate;
	private TableViewerColumn tableViewerColumn_1;
	private TableColumn tblclmnLatitude;
	private TableViewerColumn tableViewerColumn_2;
	private TableColumn tblclmnLongitude;
	private TableViewerColumn tableViewerColumn_3;
	private Clipboard clipboard;

	@Override
	protected void callBusinessLayer(int i) {
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		LOGGER.info("Constructor");
		store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"org.historyresearchenvironment.usergui");
		parent.setLayout(new GridLayout(2, false));

		tableViewerPolRegLocation = new TableViewer(parent, 67584);
		tablePolRegLocation = tableViewerPolRegLocation.getTable();
		tablePolRegLocation.setFont(getHreFont(parent));
		tablePolRegLocation.setLinesVisible(true);
		tablePolRegLocation.setHeaderVisible(true);
		tablePolRegLocation.setLayoutData(new GridData(4, 4, true, true, 2, 1));

		tableViewerColumn = new TableViewerColumn(tableViewerPolRegLocation, 0);
		tblclmnAddress = tableViewerColumn.getColumn();
		tblclmnAddress.setWidth(250);
		tblclmnAddress.setText("Address");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegLocationModel item = (PolRegLocationModel) element;
				return item.getAddress();
			}

		});
		tableViewerColumn_1 = new TableViewerColumn(tableViewerPolRegLocation,
				0);
		tblclmnDate = tableViewerColumn_1.getColumn();
		tblclmnDate.setWidth(100);
		tblclmnDate.setText("Date");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegLocationModel item = (PolRegLocationModel) element;
				return item.getDate();
			}

		});
		tableViewerColumn_2 = new TableViewerColumn(tableViewerPolRegLocation,
				0);
		tblclmnLatitude = tableViewerColumn_2.getColumn();
		tblclmnLatitude.setWidth(150);
		tblclmnLatitude.setText("Latitude");
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegLocationModel item = (PolRegLocationModel) element;
				return item.getLatitude();
			}

		});
		tableViewerColumn_3 = new TableViewerColumn(tableViewerPolRegLocation,
				0);
		tblclmnLongitude = tableViewerColumn_3.getColumn();
		tblclmnLongitude.setWidth(150);
		tblclmnLongitude.setText("Longitude");
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegLocationModel item = (PolRegLocationModel) element;
				return item.getLongitude();
			}

		});
		final Menu menu = new Menu(tablePolRegLocation);
		tablePolRegLocation.setMenu(menu);

		final MenuItem mntmCopyToClipboard = new MenuItem(menu, 0);
		mntmCopyToClipboard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				net.myerichsen.hremvp.mockup.AbstractHREGuiPart.LOGGER
						.info("Copy to clipboard");
				final Display display = Display.getCurrent();
				clipboard = new Clipboard(display);
				final List<PolRegLocationModel> list = polRegLocationProvider
						.getModelList();

				final StringBuilder sb = new StringBuilder();

				for (final PolRegLocationModel polRegLocationModel : list) {
					sb.append(polRegLocationModel.toString() + "\n");
				}

				clipboard.setContents(new Object[] { sb.toString() },
						new Transfer[] { TextTransfer.getInstance() });
				clipboard.dispose();
			}
		});
		mntmCopyToClipboard.setText("Copy to Clipboard");

		tableViewerPolRegLocation
				.setContentProvider(ArrayContentProvider.getInstance());
		polRegLocationProvider = new PolRegLocationProvider();
		tableViewerPolRegLocation
				.setInput(polRegLocationProvider.getModelList());
	}

	@Inject
	@Optional
	private void subscribeNameUpdateTopic(
			@UIEventTopic("NAME_UPDATE_TOPIC") String perName) {
		LOGGER.fine("Subscribing to " + perName);

		if ((tablePolRegLocation != null) && (tableViewerPolRegLocation != null)
				&& (tablePolRegLocation.isVisible())) {
			updateGui();
		} else {
			LOGGER.fine("Not visible");
		}
	}

	@Override
	protected void updateGui() {
		LOGGER.fine("Updating GUI");

		polRegLocationProvider = new PolRegLocationProvider();
		LOGGER.fine("Set input: Get item list");
		tableViewerPolRegLocation
				.setInput(polRegLocationProvider.getModelList());
		LOGGER.fine("Refresh");
		tableViewerPolRegLocation.refresh();
	}
}
