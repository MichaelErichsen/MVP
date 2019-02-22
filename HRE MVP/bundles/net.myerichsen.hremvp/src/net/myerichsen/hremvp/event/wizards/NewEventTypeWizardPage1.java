package net.myerichsen.hremvp.event.wizards;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.HreTypeLabelEditingSupport;
import net.myerichsen.hremvp.event.providers.EventTypeProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Wizard page to define a new language for HRE
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 22. feb. 2019
 *
 */
public class NewEventTypeWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Text textAbbreviation;
	private TableViewer tableViewer;
	private EventTypeProvider provider;
	private int labelPid;

	/**
	 * Constructor
	 *
	 * @param context
	 * @throws SQLException
	 */
	public NewEventTypeWizardPage1(IEclipseContext context) {
		super("New Event type wizard Page 1");
		setTitle("Event type");
		setDescription("Add a Event type to this HRE project");
		try {
			provider = new EventTypeProvider();
			final int size = provider.get().size();
			labelPid = size + 1;
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final Label lblAbbreviation = new Label(container, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(container, SWT.BORDER);
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnIsoCode = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnIsoCode = tableViewerColumnIsoCode.getColumn();
		tblclmnIsoCode.setWidth(100);
		tblclmnIsoCode.setText("ISO Code");
		tableViewerColumnIsoCode
				.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(394);
		tblclmnLabel.setText("Label (Click to edit)");
		tableViewerColumnLabel
				.setEditingSupport(new HreTypeLabelEditingSupport(tableViewer));
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getEventTypeList(labelPid));
		} catch (final SQLException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}

	/**
	 * @return the textAbbreviation
	 */
	public Text getTextAbbreviation() {
		return textAbbreviation;
	}

	/**
	 * @return the tableViewer
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}
}