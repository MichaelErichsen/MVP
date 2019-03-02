package net.myerichsen.hremvp.project.dialogs;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
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

import net.myerichsen.hremvp.dbmodels.Languages;
import net.myerichsen.hremvp.dbmodels.PersonNameStyles;
import net.myerichsen.hremvp.project.providers.PersonNameStyleProvider;

/**
 * Display all person name styles
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 2. mar. 2019
 *
 */
public class PersonNameStyleNavigatorDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
//	private final IPreferenceStore store = new ScopedPreferenceStore(
//			InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	private PersonNameStyleProvider provider;

	private Table table;
	private int personNameStylePid;

	/**
	 * Create the dialog
	 *
	 * @param parentShell
	 * @param context
	 */
	public PersonNameStyleNavigatorDialog(Shell parentShell,
			IEclipseContext context) {
		super(parentShell);
		try {
			provider = new PersonNameStyleProvider();
		} catch (final SQLException e) {
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
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Select Person Name Style.");
		setTitle("Person Name Styles");
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final TableViewer tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final TableItem[] items = table.getSelection();
				final TableItem selectedItem = items[0];
				setPersonNameStylePid(
						Integer.parseInt(selectedItem.getText(0)));
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnNameStyle = tableViewerColumn_1.getColumn();
		tblclmnNameStyle.setWidth(100);
		tblclmnNameStyle.setText("Name Style");

		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLanguage = tableViewerColumn_2.getColumn();
		tblclmnLanguage.setWidth(100);
		tblclmnLanguage.setText("Language");

		final TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnIsoCode = tableViewerColumn_3.getColumn();
		tblclmnIsoCode.setWidth(100);
		tblclmnIsoCode.setText("ISO Code");

		// FIXME java.lang.NullPointerException
//		final int defaultStyle = store.getInt("DEFAULTPERSONNAMESTYLE");
//		int currentStyle;

		try {
			final List<PersonNameStyles> nameStyleList = provider.get();
			table.removeAll();
			Languages language;

			for (int i = 0; i < nameStyleList.size(); i++) {
				final PersonNameStyles style = nameStyleList.get(i);
				final TableItem item = new TableItem(table, SWT.NONE);
				personNameStylePid = style.getNameStylePid();
				item.setText(0, Integer.toString(personNameStylePid));
				item.setText(1, "style.getLabelPid()");

				language = new Languages();
//				language.get(currentStyle);
				item.setText(2, language.getLabel());
				item.setText(3, language.getIsocode());

//				if (currentStyle == defaultStyle) {
//					table.setSelection(i);
//				}
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
		}

		return area;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	/**
	 * @return the personNameStylePid
	 */
	public int getPersonNameStylePid() {
		return personNameStylePid;
	}

	/**
	 * @param personNameStylePid the personNameStylePid to set
	 */
	public void setPersonNameStylePid(int personNameStylePid) {
		this.personNameStylePid = personNameStylePid;
	}

}
