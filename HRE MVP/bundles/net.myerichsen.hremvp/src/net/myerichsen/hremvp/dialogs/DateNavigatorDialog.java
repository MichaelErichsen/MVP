package net.myerichsen.hremvp.dialogs;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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

import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Display all historical dates
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. jan. 2019
 *
 */
public class DateNavigatorDialog extends TitleAreaDialog {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private HDateProvider provider;
	private Table table;
	private int hdatePid;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 * @param context
	 */
	public DateNavigatorDialog(Shell parentShell, IEclipseContext context) {
		super(parentShell);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
		try {
			provider = new HDateProvider();
		} catch (final SQLException e) {
			e.printStackTrace();
			LOGGER.severe(e.getMessage());eventBroker.post("MESSAGE", e.getMessage());
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
		setMessage("Select a date. View details by double clicking");
		setTitle("Dates");
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		final GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.grabExcessHorizontalSpace = false;
		container.setLayoutData(gd_container);

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openDateDialog(table.getShell());
			}
		});
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final TableItem[] items = table.getSelection();
				final TableItem selectedItem = items[0];
				setHdatePid(Integer.parseInt(selectedItem.getText(0)));
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumn.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnHistoricalDate = tableViewerColumn_1.getColumn();
		tblclmnHistoricalDate.setWidth(100);
		tblclmnHistoricalDate.setText("Historical Date");

		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn tblclmnOriginalInputFormat = tableViewerColumn_2.getColumn();
		tblclmnOriginalInputFormat.setWidth(240);
		tblclmnOriginalInputFormat.setText("Original Input Format");

		String string;
		String[] sa;

		try {
			final List<String> stringList = provider.get();
			table.removeAll();

			for (int i = 0; i < stringList.size(); i++) {
				string = stringList.get(i);
				sa = string.split("¤%&");

				if ((sa.length > 1) && (sa[1].trim().length() > 0)) {
					final TableItem item = new TableItem(table, SWT.NONE);
					item.setText(0, sa[0]);
					item.setText(1, sa[1].trim());
					if ((sa.length > 2) && (sa[2].trim().length() > 0)) {
						item.setText(2, sa[2].trim());
					}
				}
			}
		} catch (final Exception e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}

		return area;
	}

	/**
	 * @return the hdatePid
	 */
	public int getHdatePid() {
		return hdatePid;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(477, 464);
	}

	/**
	 * @param shell
	 * @param context
	 *
	 */
	protected void openDateDialog(Shell shell) {
		final TableItem[] items = table.getSelection();
		final TableItem selectedItem = items[0];
		setHdatePid(Integer.parseInt(selectedItem.getText(0)));
		final DateDialog dateDialog = new DateDialog(shell, context, hdatePid);
		dateDialog.open();
	}

	/**
	 * @param hdatePid the hdatePid to set
	 */
	public void setHdatePid(int hdatePid) {
		this.hdatePid = hdatePid;
	}
}