package net.myerichsen.hremvp.project.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;

/**
 * Display all data for a sex type
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. feb. 2019
 *
 */
public class SexTypeNavigator {
//	private final static Logger LOGGER = Logger
//			.getLogger(Logger.GLOBAL_LOGGER_NAME);

//	@Inject
//	private EPartService partService;
//	@Inject
//	private EModelService modelService;
//	@Inject
//	private MApplication application;
	@Inject
//	private IEventBroker eventBroker;

	private SexTypeProvider provider;
	private TableViewer tableViewer;

	/**
	 * Constructor
	 *
	 */
	public SexTypeNavigator() {
		provider = new SexTypeProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.
			 * eclipse.jface.viewers.DoubleClickEvent)
			 */
			public void doubleClick(DoubleClickEvent event) {
				openSexTypeNLSView();
			}
		});
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnSexTypeId = tableViewerColumnId.getColumn();
		tblclmnSexTypeId.setWidth(100);
		tblclmnSexTypeId.setText("Sex Type ID");

		TableViewerColumn tableViewerColumnAbbrev = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnAbbreviation = tableViewerColumnAbbrev.getColumn();
		tblclmnAbbreviation.setWidth(100);
		tblclmnAbbreviation.setText("Abbreviation");

		TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnLabelInEnglish = tableViewerColumnLabel.getColumn();
		tblclmnLabelInEnglish.setWidth(100);
		tblclmnLabelInEnglish.setText("Label in English");

		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmAddSexType = new MenuItem(menu, SWT.NONE);
		mntmAddSexType.setText("Add sex type...");

		MenuItem mntmDeleteSelectedSex = new MenuItem(menu, SWT.NONE);
		mntmDeleteSelectedSex.setText("Delete selected sex type...");
	}

	/**
	 * 
	 */
	protected void openSexTypeNLSView() {
		// TODO Auto-generated method stub

	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 * @throws MvpException
	 */
	@Inject
	@Optional
	private void subscribeSexTypeKeyUpdateTopic(
			@UIEventTopic(Constants.SEX_TYPE_PID_UPDATE_TOPIC) int key)
			throws MvpException {
	}
}
