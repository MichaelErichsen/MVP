package net.myerichsen.hremvp.event.parts;

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

import net.myerichsen.hremvp.event.providers.EventTypeProvider;
import net.myerichsen.hremvp.event.wizards.NewEventTypeWizard;

/**
 * Display all personEvent types
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 15. feb. 2019
 *
 */
public class EventTypeNavigator {
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

	private EventTypeProvider provider;
	private TableViewer tableViewer;
	private int eventTypePid;

	/**
	 * Constructor
	 *
	 */
	public EventTypeNavigator() {
		try {
			provider = new EventTypeProvider();
		} catch (final Exception e) {
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent      The parent composite
	 * @param menuService The Eclipse menu service
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEventTypeView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(40);
		tblclmnId.setText("Event Type ID");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnEventType = tableViewerColumn_1.getColumn();
		tblclmnEventType.setWidth(230);
		tblclmnEventType.setText("Event Type");

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmAddEventType = new MenuItem(menu, SWT.NONE);
		mntmAddEventType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewEventTypeWizard(eventTypePid, context));
				dialog.open();
			}
		});
		mntmAddEventType.setText("Add event type...");

		MenuItem mntmDeleteSelectedEvent = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteEventType(parent.getShell());
			}
		});
		mntmDeleteSelectedEvent.setText("Delete selected event type...");

		// TODO Change to Jface
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
	protected void deleteEventType(Shell shell) {
		final TableItem[] selection = tableViewer.getTable().getSelection();

		int eventTypePid = 0;
		String eventTypeName = null;
		if (selection.length > 0) {
			final TableItem item = selection[0];
			eventTypePid = Integer.parseInt(item.getText(0));
			eventTypeName = item.getText(1);
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Delete event type " + eventTypeName, null,
				"Are you sure that you will delete " + eventTypePid + ", "
						+ eventTypeName + "?",
				MessageDialog.CONFIRM, 0, new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE", "Delete of event type " + eventTypeName
					+ " has been canceled");
			return;
		}

		try {
			EventTypeProvider provider = new EventTypeProvider();
			provider.delete(eventTypePid);
			eventBroker.post("MESSAGE",
					"Event type " + eventTypeName + " has been deleted");
		} catch (Exception e) {
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
	protected void openEventTypeView() {
		final String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.event.parts.EventTypeView";

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
			part.setLabel("Event Type View");
			part.setCloseable(true);
			part.setVisible(true);
			part.setContributionURI(contributionURI);
			stacks.get(stacks.size() - 2).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		int eventTypePid = 0;

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			eventTypePid = Integer.parseInt(selectedRow.getText(0));
		}

		eventBroker.post(
				net.myerichsen.hremvp.Constants.EVENT_TYPE_PID_UPDATE_TOPIC,
				eventTypePid);
		LOGGER.info("Event Type Pid: " + eventTypePid);
	}

	/**
	 *
	 */
	@Focus
	public void setFocus() {
	}
}
