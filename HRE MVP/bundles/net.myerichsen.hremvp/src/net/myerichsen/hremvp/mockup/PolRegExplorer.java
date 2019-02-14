package net.myerichsen.hremvp.mockup;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

@SuppressWarnings("restriction")
public class PolRegExplorer extends AbstractHREGuiPart {
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	private Table tablePolReg;
	private TableViewer tableViewerPolReg;
	private PolRegProvider polRegProvider;
	private TableColumn tblclmnId;
	private TableViewerColumn tableViewerColumn;
	private TableColumn tblclmnRegisterblad;
	private TableViewerColumn tableViewerColumn_1;
	private TableColumn tblclmnFirstNames;
	private TableViewerColumn tableViewerColumn_3;
	private TableColumn tblclmnSurname;
	private TableViewerColumn tableViewerColumn_4;
	private TableColumn tblclmnType_1;
	private TableViewerColumn tableViewerColumn_5;
	private TableColumn tblclmnBirthPlace;
	private TableViewerColumn tableViewerColumn_6;
	private TableColumn tblclmnBirthDate;
	private TableViewerColumn tableViewerColumn_7;
	private TableColumn tblclmnDeathDate;
	private TableViewerColumn tableViewerColumn_8;
	protected String registerbladId;
	private Menu menu;

	@Override
	protected void callBusinessLayer(int i) {
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		LOGGER.info("Constructor");
		store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"org.historyresearchenvironment.usergui");
		parent.setLayout(new GridLayout(2, false));

		tableViewerPolReg = new TableViewer(parent, 67584);
		tablePolReg = tableViewerPolReg.getTable();
		tablePolReg
				.setToolTipText("Select a person and right clik to continue");
		tablePolReg.addListener(13, new Listener() {

			@Override
			public void handleEvent(Event event) {

				final TableItem[] selection = tablePolReg.getSelection();
				final TableItem item = selection[0];
				registerbladId = item.getText(1);
				net.myerichsen.hremvp.mockup.AbstractHREGuiPart.LOGGER
						.info("Selected id: " + registerbladId);
				PolRegExplorer.this.store.putValue("POLREGID", registerbladId);
			}
		});
		tablePolReg.setFont(getHreFont(parent));
		tablePolReg.setLinesVisible(true);
		tablePolReg.setHeaderVisible(true);
		tablePolReg.setLayoutData(new GridData(4, 4, true, true, 2, 1));

		tableViewerColumn = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getId();
			}

		});
		tableViewerColumn_1 = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnRegisterblad = tableViewerColumn_1.getColumn();
		tblclmnRegisterblad.setWidth(100);
		tblclmnRegisterblad.setText("Registerblad");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getRegisterblad_id();
			}

		});
		tableViewerColumn_3 = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnFirstNames = tableViewerColumn_3.getColumn();
		tblclmnFirstNames.setWidth(200);
		tblclmnFirstNames.setText("First Names");
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getFirstnames();
			}

		});
		tableViewerColumn_4 = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnSurname = tableViewerColumn_4.getColumn();
		tblclmnSurname.setWidth(150);
		tblclmnSurname.setText("Surname");
		tableViewerColumn_4.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getLastname();
			}

		});
		tableViewerColumn_5 = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnType_1 = tableViewerColumn_5.getColumn();
		tblclmnType_1.setWidth(100);
		tblclmnType_1.setText("Type");
		tableViewerColumn_5.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getPerson_type();
			}

		});
		tableViewerColumn_6 = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnBirthPlace = tableViewerColumn_6.getColumn();
		tblclmnBirthPlace.setWidth(100);
		tblclmnBirthPlace.setText("Birth Place");
		tableViewerColumn_6.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getBirthplace();
			}

		});
		tableViewerColumn_7 = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnBirthDate = tableViewerColumn_7.getColumn();
		tblclmnBirthDate.setWidth(100);
		tblclmnBirthDate.setText("Birth date");
		tableViewerColumn_7.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getDateofbirth();
			}

		});
		tableViewerColumn_8 = new TableViewerColumn(tableViewerPolReg, 0);
		tblclmnDeathDate = tableViewerColumn_8.getColumn();
		tblclmnDeathDate.setWidth(100);
		tblclmnDeathDate.setText("Death date");
		tableViewerColumn_8.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final PolRegModel item = (PolRegModel) element;
				return item.getDateofdeath();
			}

		});
		menu = new Menu(tablePolReg);
		tablePolReg.setMenu(menu);

		final MenuItem mntmOpenOriginalIn = new MenuItem(menu, 0);
		mntmOpenOriginalIn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				net.myerichsen.hremvp.mockup.AbstractHREGuiPart.LOGGER
						.info("Browser selected on " + registerbladId);
				final ParameterizedCommand browseCommand = commandService
						.createCommand(
								"org.historyresearchenvironment.usergui.command.polregbrowser",
								null);
				handlerService.executeHandler(browseCommand);
			}
		});
		mntmOpenOriginalIn.setText("Open Original in Browser");

		final MenuItem mntmLocations = new MenuItem(menu, 0);
		mntmLocations.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				net.myerichsen.hremvp.mockup.AbstractHREGuiPart.LOGGER
						.info("Locations selected on " + registerbladId);
				final ParameterizedCommand browseCommand = commandService
						.createCommand(
								"org.historyresearchenvironment.usergui.command.polreglocation",
								null);
				handlerService.executeHandler(browseCommand);
			}
		});
		mntmLocations.setText("Locations");

		tableViewerPolReg
				.setContentProvider(ArrayContentProvider.getInstance());
		polRegProvider = new PolRegProvider();
		tableViewerPolReg.setInput(polRegProvider.getModelList());
	}

	@Inject
	@Optional
	private void subscribeNameUpdateTopic(
			@UIEventTopic("NAME_UPDATE_TOPIC") String perName) {
		LOGGER.fine("Subscribing to " + perName);

		if ((tablePolReg != null) && (tableViewerPolReg != null)
				&& (tablePolReg.isVisible())) {
			updateGui();
		} else {
			LOGGER.fine("Not visible");
		}
	}

	@Override
	protected void updateGui() {
		LOGGER.fine("Updating GUI");

		polRegProvider = new PolRegProvider();
		LOGGER.fine("Set input: Get item list");
		tableViewerPolReg.setInput(polRegProvider.getModelList());
		LOGGER.fine("Refresh");
		tableViewerPolReg.refresh();
	}
}
