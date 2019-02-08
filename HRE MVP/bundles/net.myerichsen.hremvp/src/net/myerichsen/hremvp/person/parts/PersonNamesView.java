package net.myerichsen.hremvp.person.parts;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
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
import net.myerichsen.hremvp.person.providers.PersonProvider;

/**
 * View all names of a person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 7. feb. 2019
 *
 */
@SuppressWarnings("restriction")
public class PersonNamesView {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private Text textId;
	private TableViewer tableViewer;
	private PersonProvider provider;

	/**
	 * Constructor
	 *
	 */
	public PersonNamesView() {
		try {
			provider = new PersonProvider();
		} catch (final SQLException e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the view part
	 */
	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService) {
		parent.setLayout(new GridLayout(2, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblId.setText("Person Id");

		textId = new Text(parent, SWT.BORDER);
		textId.setEditable(false);
		textId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setToolTipText("Double click to edit. Right click to add or delete");
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
				openNamePartNavigator();
			}
		});
		menuService.registerContextMenu(tableViewer.getControl(), "net.myerichsen.hremvp.popupmenu.personnameviewmenu");
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("Id");
		tableViewerColumnId.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(0);
			}
		});

		final TableViewerColumn tableViewerColumnName = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumnName.getColumn();
		tblclmnName.setWidth(200);
		tblclmnName.setText("Name");
		tableViewerColumnName.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(1);
			}
		});

		final TableViewerColumn tableViewerColumnFrom = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnFromDate = tableViewerColumnFrom.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");
		tableViewerColumnFrom.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(2);
			}
		});
		final TableViewerColumn tableViewerColumnTo = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnToDate = tableViewerColumnTo.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");
		tableViewerColumnTo.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(3);
			}
		});

		final TableViewerColumn tableViewerColumnPrimary = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnPrimary = tableViewerColumnPrimary.getColumn();
		tblclmnPrimary.setWidth(50);
		tblclmnPrimary.setText("Primary");
		tableViewerColumnPrimary.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				final List<String> list = (List<String>) element;
				return list.get(4);
			}
		});

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getPersonNameList(0));
		} catch (SQLException | MvpException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}

	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	protected void openNamePartNavigator() {
		final ParameterizedCommand command = commandService
				.createCommand("net.myerichsen.hremvp.command.openpersonnamepartnavigator", null);
		handlerService.executeHandler(command);

		final TableItem[] selection = tableViewer.getTable().getSelection();
		final int namePid = Integer.parseInt(selection[0].getText(0));
		LOGGER.info("Setting name pid: " + namePid);
		eventBroker.post(Constants.NAME_PID_UPDATE_TOPIC, namePid);
	}

	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribePersonPidUpdateTopic(@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int personPid) {
		LOGGER.fine("Received person id " + personPid);
		try {
			textId.setText(Integer.toString(personPid));
			tableViewer.setInput(provider.getPersonNameList(personPid));
			tableViewer.refresh();
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
