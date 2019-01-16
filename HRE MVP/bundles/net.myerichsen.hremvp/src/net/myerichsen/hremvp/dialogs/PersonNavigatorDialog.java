package net.myerichsen.hremvp.dialogs;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.dbmodels.Persons;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Display all persons.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 16. jan. 2019
 *
 */
// TODO Add filtering
public class PersonNavigatorDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private PersonProvider provider;

	private Table table;

	private int personPid;
	private String personName;
	private String birthDate;
	private String deathDate;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 */
	public PersonNavigatorDialog(Shell parentShell) {
		super(parentShell);
		try {
			provider = new PersonProvider();
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
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
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
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
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnName = tableViewerColumn_1.getColumn();
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");

		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnBirthDate = tableViewerColumn_2.getColumn();
		tblclmnBirthDate.setWidth(100);
		tblclmnBirthDate.setText("Birth Date");

		final TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnDeathDate = tableViewerColumn_3.getColumn();
		tblclmnDeathDate.setWidth(100);
		tblclmnDeathDate.setText("Death Date");

		try {
			final List<Persons> personList = provider.get();
			table.removeAll();
			final HDateProvider dateProvider = new HDateProvider();
			int loopPid;

			for (Persons person : personList) {
				final TableItem item = new TableItem(table, SWT.NONE);
				loopPid = person.getPersonPid();
				item.setText(0, Integer.toString(loopPid));
				provider.get(loopPid);
				item.setText(1, provider.getPrimaryName());
				dateProvider.get(provider.getBirthDatePid());
				item.setText(2, dateProvider.getDate().toString());
				dateProvider.get(provider.getDeathDatePid());
				item.setText(3, dateProvider.getDate().toString());
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
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
