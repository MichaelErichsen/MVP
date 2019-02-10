package net.myerichsen.hremvp.person.parts;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.filters.NavigatorFilter;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all persons with their primary names
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 10. feb. 2019
 *
 */
public class PersonNavigator {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private PersonProvider provider;
	private TableViewer tableViewer;
	private NavigatorFilter navigatorFilter;

	/**
	 * Constructor
	 *
	 */
	public PersonNavigator() {
		try {
			provider = new PersonProvider();
			navigatorFilter = new NavigatorFilter();
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
	 * @param menuService
	 */
	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService) {
		parent.setLayout(new GridLayout(2, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addFilter(navigatorFilter);

		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
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
				postPersonPid();
			}
		});
		menuService.registerContextMenu(tableViewer.getControl(),
				"net.myerichsen.hremvp.popupmenu.personnavigatormenu");
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnName = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnPrimaryName = tableViewerColumnName.getColumn();
		tblclmnPrimaryName.setWidth(300);
		tblclmnPrimaryName.setText("Primary Name");
		tableViewerColumnName.setLabelProvider(new HREColumnLabelProvider(1));

		final Label lblNameFilter = new Label(parent, SWT.NONE);
		lblNameFilter.setText("Name Filter");

		final Text textNameFilter = new Text(parent, SWT.BORDER);
		textNameFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				navigatorFilter.setSearchText(textNameFilter.getText());
				LOGGER.info("Filter string: " + textNameFilter.getText());
				tableViewer.refresh();
			}
		});

		textNameFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getPersonList());
		} catch (SQLException | MvpException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}

	/**
	 *
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * @return
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 *
	 */
	private void postPersonPid() {
		final TableItem[] selection = tableViewer.getTable().getSelection();
		final int personPid = Integer.parseInt(selection[0].getText(0));
		LOGGER.info("Posting person pid " + personPid);
		eventBroker.post(net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);
	}

	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribePersonListUpdateTopic(@UIEventTopic(Constants.PERSON_LIST_UPDATE_TOPIC) int personPid) {
		LOGGER.fine("Received person id " + personPid);
		try {
			tableViewer.setInput(provider.getPersonList());
			tableViewer.refresh();
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
