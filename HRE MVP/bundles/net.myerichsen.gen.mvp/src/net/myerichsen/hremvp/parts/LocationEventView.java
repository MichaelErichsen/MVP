package net.myerichsen.hremvp.parts;

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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.providers.LocationEventProvider;

/**
 * Display all events for a location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 25. nov. 2018
 */

public class LocationEventView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;

	private Table table;
	private TableViewer tableViewer;

	private LocationEventProvider provider;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationEventView() throws SQLException {
		provider = new LocationEventProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(4, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEventView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		TableViewerColumn tableViewerColumnEventId = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnEventId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		TableViewerColumn tableViewerColumnEventLabel = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnEvent = tableViewerColumnEventLabel.getColumn();
		tblclmnEvent.setWidth(300);
		tblclmnEvent.setText("Event");
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
		List<List<String>> lls;
		List<String> stringList;
		TableItem item;

		try {
			table.removeAll();

			lls = provider.getEventList(key);

			for (int i = 0; i < lls.size(); i++) {
				stringList = lls.get(i);

				item = new TableItem(table, SWT.NONE);

				for (int j = 0; j < stringList.size(); j++) {
					item.setText(j, stringList.get(j).trim());
				}
			}

		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void openEventView() {
		String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.parts.EventView";

		final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
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
			part.setLabel("Event View");
			part.setCloseable(true);
			part.setVisible(true);
			part.setContributionURI(contributionURI);
			stacks.get(stacks.size() - 2).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		String eventPid = "0";

		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			eventPid = selectedRow.getText(0);
		}

		LOGGER.info("Setting event pid: " + eventPid);
		eventBroker.post(Constants.EVENT_PID_UPDATE_TOPIC, Integer.parseInt(eventPid));
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 * @throws SQLException
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(@UIEventTopic(Constants.LOCATION_PID_UPDATE_TOPIC) int key)
			throws SQLException {
		get(key);
	}

}
