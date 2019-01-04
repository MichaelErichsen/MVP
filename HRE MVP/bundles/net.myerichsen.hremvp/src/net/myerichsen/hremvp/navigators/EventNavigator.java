package net.myerichsen.hremvp.navigators;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.services.EMenuService;
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

import net.myerichsen.hremvp.providers.EventProvider;

/**
 * Display all events
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. dec. 2018
 *
 */
// FIXME Reopens blank Event View
public class EventNavigator {
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
	private EventProvider provider;

	/**
	 * Constructor
	 *
	 */
	public EventNavigator() {
		try {
			provider = new EventProvider();
		} catch (Exception e) {
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
		parent.setLayout(new GridLayout(1, false));

		TableViewer tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
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
				openEventView();
			}
		});
		menuService.registerContextMenu(tableViewer.getControl(), "net.myerichsen.hremvp.popupmenu.eventmenu");
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn tableViewerColumnID = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnId = tableViewerColumnID.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("Event ID");

		TableViewerColumn tableViewerColumnEventName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPrimaryEventName = tableViewerColumnEventName.getColumn();
		tblclmnPrimaryEventName.setWidth(100);
		tblclmnPrimaryEventName.setText(" Event Name");

		TableViewerColumn tableViewerColumnEventType = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnEventType = tableViewerColumnEventType.getColumn();
		tblclmnEventType.setWidth(100);
		tblclmnEventType.setText("Event Type");

		TableViewerColumn tableViewerColumnLanguage = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnLanguage = tableViewerColumnLanguage.getColumn();
		tblclmnLanguage.setWidth(100);
		tblclmnLanguage.setText("Language");

		List<String> stringList;

		try {
			List<List<String>> lls = provider.get();
			table.removeAll();
			TableItem item;

			for (int i = 0; i < lls.size(); i++) {
				stringList = lls.get(i);

				item = new TableItem(table, SWT.NONE);

				for (int j = 0; j < stringList.size(); j++) {
					item.setText(j, stringList.get(j).trim());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}
	}

	/**
	 * 
	 */
	/**
	 * 
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	protected void openEventView() {
		String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.parts.EventView";

		final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
		MPart part = MBasicFactory.INSTANCE.createPart();

		boolean found = false;
// FIXME Opening a second window titled "Event"
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
			stacks.get(stacks.size() - 5).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		int eventPid = 0;

		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			eventPid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(net.myerichsen.hremvp.Constants.EVENT_PID_UPDATE_TOPIC, eventPid);
		LOGGER.info("Event Pid: " + eventPid);
	}

	/**
	 * 
	 */
	@Focus
	public void setFocus() {
	}
}