package net.myerichsen.hremvp.person.dialogs;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.filters.NavigatorFilter;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Display all persons.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 2. feb. 2019
 *
 */
public class PersonNavigatorDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private IEventBroker eventBroker;

	private PersonProvider provider;

	private int personPid;
	private String personName;
	private String birthDate;
	private String deathDate;
	private Text textNameFilter;
	private TableViewer tableViewer;
	private NavigatorFilter navigatorFilter;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 * @param context
	 */
	public PersonNavigatorDialog(Shell parentShell, IEclipseContext context) {
		super(parentShell);
		eventBroker = context.get(IEventBroker.class);
		navigatorFilter = new NavigatorFilter();

		try {
			provider = new PersonProvider();
			new HDateProvider();
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Select a person.");
		setTitle("Persons");
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addFilter(navigatorFilter);

		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final TableItem[] items = table.getSelection();
				final TableItem selectedItem = items[0];
				setPersonPid(Integer.parseInt(selectedItem.getText(0)));
				setPersonName(selectedItem.getText(1));
				setBirthDate(selectedItem.getText(2));
				setDeathDate(selectedItem.getText(3));
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				List<String> stringList = (List<String>) element;
				return stringList.get(0);
			}
		});

		final TableViewerColumn tableViewerColumnName = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumnName.getColumn();
		tblclmnName.setWidth(100);
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
				List<String> stringList = (List<String>) element;
				return stringList.get(1);
			}
		});

		final TableViewerColumn tableViewerColumnBirthDate = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnBirthDate = tableViewerColumnBirthDate.getColumn();
		tblclmnBirthDate.setWidth(100);
		tblclmnBirthDate.setText("Birth Date");
		tableViewerColumnBirthDate.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */

			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				List<String> stringList = (List<String>) element;
				return stringList.get(2);
			}
		});

		final TableViewerColumn tableViewerColumnDeathDate = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnDeathDate = tableViewerColumnDeathDate.getColumn();
		tblclmnDeathDate.setWidth(100);
		tblclmnDeathDate.setText("Death Date");
		tableViewerColumnDeathDate.setLabelProvider(new ColumnLabelProvider() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				List<String> stringList = (List<String>) element;
				return stringList.get(3);
			}
		});

		Label lblNameFilter = new Label(container, SWT.NONE);
		lblNameFilter.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNameFilter.setText("Name Filter");

		textNameFilter = new Text(container, SWT.BORDER);
		textNameFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				navigatorFilter.setSearchText(textNameFilter.getText());
				LOGGER.fine("Filter string: " + textNameFilter.getText());
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

		return area;
	}

	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @return the deathDate
	 */
	public String getDeathDate() {
		return deathDate;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @param deathDate the deathDate to set
	 */
	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

}
