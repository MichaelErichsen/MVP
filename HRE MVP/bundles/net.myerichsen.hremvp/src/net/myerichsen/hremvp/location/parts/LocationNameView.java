package net.myerichsen.hremvp.location.parts;

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
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.location.providers.LocationProvider;

/**
 * Display all data about a location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. jan. 2019
 */
public class LocationNameView {
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

	private ScrolledComposite scrolledComposite;
	private Composite composite_1;
	private Table tableNames;
	private TableViewer tableViewerNames;

	private final LocationProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationNameView() throws SQLException {
		provider = new LocationProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		scrolledComposite = new ScrolledComposite(parent,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		final GridData gd_scrolledComposite = new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1);
		gd_scrolledComposite.widthHint = 674;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));

		tableViewerNames = new TableViewer(composite_1,
				SWT.BORDER | SWT.FULL_SELECTION);
		tableNames = tableViewerNames.getTable();
		tableNames.setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, false, true, 2, 1));
		tableNames.setToolTipText("Double click to edit name part");
		tableNames.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openLocationNameView();
			}
		});
		tableNames.setLinesVisible(true);
		tableNames.setHeaderVisible(true);

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewerNames, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumnName = new TableViewerColumn(
				tableViewerNames, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumnName.getColumn();
		tblclmnName.setWidth(200);
		tblclmnName.setText("Name");

		final TableViewerColumn tableViewerColumnPrimary = new TableViewerColumn(
				tableViewerNames, SWT.NONE);
		final TableColumn tblclmnPrimary = tableViewerColumnPrimary.getColumn();
		tblclmnPrimary.setWidth(60);
		tblclmnPrimary.setText("Primary");

		final TableViewerColumn tableViewerColumnFrom = new TableViewerColumn(
				tableViewerNames, SWT.NONE);
		final TableColumn tblclmnFrom = tableViewerColumnFrom.getColumn();
		tblclmnFrom.setWidth(100);
		tblclmnFrom.setText("From");

		final TableViewerColumn tableViewerColumnTo = new TableViewerColumn(
				tableViewerNames, SWT.NONE);
		final TableColumn tblclmnTo = tableViewerColumnTo.getColumn();
		tblclmnTo.setWidth(100);
		tblclmnTo.setText("To");

		scrolledComposite.setContent(composite_1);
		scrolledComposite
				.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);

			tableNames.removeAll();

			final List<List<String>> nameList = provider.getNameList();
			List<String> sl;

			for (int i = 0; i < nameList.size(); i++) {
				final TableItem item = new TableItem(tableNames, SWT.NONE);
				sl = nameList.get(i);
				item.setText(0, sl.get(0));
				item.setText(1, sl.get(1));
				item.setText(2, sl.get(2));
				item.setText(3, sl.get(3));
				item.setText(4, sl.get(4));
			}

			openGoogleMaps();

		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private void openGoogleMaps() {
		final String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.parts.LocationGoogleMapBrowser";

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
			part.setLabel("Google Maps View");
			part.setCloseable(true);
			part.setVisible(true);
			part.setContributionURI(contributionURI);
			stacks.get(stacks.size() - 2).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		eventBroker.post(Constants.LOCATION_GOOGLE_MAP_UPDATE_TOPIC, provider);
	}

	/**
	 *
	 */
	protected void openLocationNameView() {
		final String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.parts.LocationNameViewOld";

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
			part.setLabel("Location Name View");
			part.setCloseable(true);
			part.setVisible(true);
			part.setContributionURI(contributionURI);
			stacks.get(stacks.size() - 2).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		String locationNamePid = "0";

		final TableItem[] selectedRows = tableNames.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			locationNamePid = selectedRow.getText(0);
		}

		LOGGER.info("Setting location name pid: " + locationNamePid);
		eventBroker.post(Constants.LOCATION_NAME_PID_UPDATE_TOPIC,
				Integer.parseInt(locationNamePid));
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 */
	@Inject
	@Optional
	private void subscribeLocationPidUpdateTopic(
			@UIEventTopic(Constants.LOCATION_PID_UPDATE_TOPIC) int key) {
		get(key);
	}
}
