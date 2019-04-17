package net.myerichsen.hremvp.event.parts;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
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

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.person.providers.PersonEventProvider;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.person.wizards.NewPersonWizard;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all persons for a single event
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 17. apr. 2019
 */
public class EventPersonView {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;

	private final PersonEventProvider provider;

	// FIXME Populate event pid
	private final static int eventPid = 0;

	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 */
	public EventPersonView() {
		provider = new PersonEventProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDoubleClick(org.eclipse.
			 * swt.events.MouseEvent)
			 */
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openPersonView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnEventId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnEventId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnEventId
				.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnEventLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPerson = tableViewerColumnEventLabel
				.getColumn();
		tblclmnPerson.setWidth(300);
		tblclmnPerson.setText("Person");
		tableViewerColumnEventLabel
				.setLabelProvider(new HREColumnLabelProvider(1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getStringList(eventPid));
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
		}

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		final MenuItem mntmAddNewPerson = new MenuItem(menu, SWT.NONE);
		mntmAddNewPerson.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewPersonWizard(context));
				dialog.open();
				// FIXME Add personevent
			}
		});
		mntmAddNewPerson.setText("Add new person...");

		final MenuItem mntmAddExistingPerson = new MenuItem(menu, SWT.NONE);
		mntmAddExistingPerson.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				openPersonNavigator();

			}
		});
		mntmAddExistingPerson.setText("Add existing person...");

		final MenuItem mntmDeleteSelectedPerson = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedPerson.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				deletePerson(parent.getShell());
			}
		});
		mntmDeleteSelectedPerson.setText("Delete selected person...");
	}

	/**
	 * @param shell
	 */
	protected void deletePerson(Shell shell) {
		final TableItem[] selection = tableViewer.getTable().getSelection();

		int personPid = 0;
		String primaryName = null;
		if (selection.length > 0) {
			final TableItem item = selection[0];
			personPid = Integer.parseInt(item.getText(0));
			primaryName = item.getText(1);
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Delete Person " + primaryName, null,
				"Are you sure that you will delete person " + personPid + ", "
						+ primaryName + "?",
				MessageDialog.CONFIRM, 0, "OK", "Cancel");

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE",
					"Deletion of person " + primaryName + " has been canceled");
			return;
		}

		// FIXME Delete personevent

		try {
			final PersonProvider pp = new PersonProvider();
			pp.delete(personPid);

			LOGGER.log(Level.INFO, "Person {0} has been deleted", primaryName);
			eventBroker.post("MESSAGE",
					"Person " + primaryName + " has been deleted");
			eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC, personPid);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}

	}

	/**
	 *
	 */
	protected void openPersonNavigator() {
		// FIXME Populate openPersonNavigator()
		// FIXME Add personevent

//		final PersonNavigatorDialog dialog = new PersonNavigatorDialog(
//				parentShell, context);
//		if (dialog.open() == Window.OK) {
//			try {
//				final int fatherPid = dialog.getPersonPid();
//				textFatherPersonPid.setText(Integer.toString(fatherPid));
//				textFatherName.setText(dialog.getPersonName());
//				textFatherBirthDate.setText(dialog.getBirthDate());
//				textFatherDeathDate.setText(dialog.getDeathDate());
//				wizard.setFatherPid(fatherPid);
//			} catch (final Exception e) {
//				LOGGER.log(Level.SEVERE, e.toString(), e);
//			}
//		}

	}

	/**
	 *
	 */
	protected void openPersonView() {
		final String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.person.parts.PersonView";

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
			part.setLabel("Person View");
			part.setCloseable(true);
			part.setVisible(true);
			part.setContributionURI(contributionURI);
			stacks.get(stacks.size() - 2).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		String personPid = "0";

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = selectedRow.getText(0);
		}

		LOGGER.log(Level.INFO, "Setting person pid: {0}", personPid);
		eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC,
				Integer.parseInt(personPid));

	}

	/**
	 * @param key
	 * @throws Exception
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(
			@UIEventTopic(Constants.EVENT_PID_UPDATE_TOPIC) int eventPid) {
		try {
			tableViewer.setInput(provider.getStringList(eventPid));
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
}
