package net.myerichsen.hremvp.location.parts;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.location.providers.LocationProvider;
import net.myerichsen.hremvp.location.wizards.NewLocationWizard;

/**
 * Display all locations
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 15. feb. 2019
 *
 */
public class LocationNavigator {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;

	private LocationProvider provider;
	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 */
	public LocationNavigator() {
		try {
			provider = new LocationProvider();
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
	 * @param context
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLocationView();
			}
		});

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPrimaryLocationName = tableViewerColumn_1
				.getColumn();
		tblclmnPrimaryLocationName.setWidth(400);
		tblclmnPrimaryLocationName.setText("Primary Location Name");

		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmAddLocation = new MenuItem(menu, SWT.NONE);
		mntmAddLocation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewLocationWizard(context));
				dialog.open();
			}
		});
		mntmAddLocation.setText("Add location...");

		MenuItem mntmDeleteSelectedLocation = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedLocation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteLocation(parent.getShell());
			}
		});
		mntmDeleteSelectedLocation.setText("Delete selected location...");

		// FIXME Change to Jface
		List<String> stringList;

		try {
			final List<List<String>> lls = provider.get();
			table.removeAll();
			TableItem item;

			for (int i = 0; i < lls.size(); i++) {
				stringList = lls.get(i);

				if (stringList.get(1).trim().length() > 0) {

					item = new TableItem(table, SWT.NONE);

					for (int j = 0; j < stringList.size(); j++) {
						item.setText(j, stringList.get(j).trim());
					}
				}
			}
		} catch (final Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}
	}

	/**
	 * @param shell
	 */
	protected void deleteLocation(Shell shell) {
		final TableItem[] selection = tableViewer.getTable().getSelection();

		int locationPid = 0;
		String primaryName = null;
		if (selection.length > 0) {
			final TableItem item = selection[0];
			locationPid = Integer.parseInt(item.getText(0));
			primaryName = item.getText(1);
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Delete location " + primaryName, null,
				"Are you sure that you will delete location " + locationPid
						+ ", " + primaryName + "?",
				MessageDialog.CONFIRM, 0, new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE",
					"Delete of location " + primaryName + " has been canceled");
			return;
		}

		try {
			LocationProvider provider = new LocationProvider();
			// FIXME SEVERE: Referential integrity constraint violation:
			// "LOCATIONS_LOCATION_NAMES_FK: PUBLIC.LOCATION_NAMES FOREIGN
			// KEY(LOCATION_PID) REFERENCES PUBLIC.LOCATIONS(LOCATION_PID)
			// (14)"; SQL statement:
			// DELETE FROM PUBLIC.LOCATIONS WHERE LOCATION_PID = ? [23503-197]
			provider.delete(locationPid);
			eventBroker.post("MESSAGE",
					"Location " + primaryName + " has been deleted");
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	protected void openLocationView() {
		final String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.parts.LocationView";

		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		MPart part = MBasicFactory.INSTANCE.createPart();

		boolean found = false;

		for (final MPartStack mPartStack : stacks) {
			final List<MStackElement> a = mPartStack.getChildren();

			for (int i = 0; i < a.size(); i++) {
				part = (MPart) a.get(i);
				if (part.getContributionURI().equals(contributionURI)) {
					partService.showPart(part, PartState.ACTIVATE);
					found = true;
					break;
				}
			}
		}

		if (!found) {
			part.setLabel("Location View");
			part.setCloseable(true);
			part.setVisible(true);
			part.setContributionURI(contributionURI);
			stacks.get(stacks.size() - 5).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		int locationPid = 0;

		// Post name pid
		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			locationPid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(
				net.myerichsen.hremvp.Constants.LOCATION_PID_UPDATE_TOPIC,
				locationPid);
		LOGGER.info("Location Pid: " + locationPid);
	}

	@Focus
	public void setFocus() {
	}

}
