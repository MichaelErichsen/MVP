package net.myerichsen.hremvp.project.wizards;

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

import net.myerichsen.hremvp.project.parts.SexTypeLabelEditingSupport;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Wizard page to define a new language for HRE
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 21. feb. 2019
 *
 */
public class NewSexTypeWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Text textAbbreviation;
	private TableViewer tableViewer;
	private final SexTypeProvider provider;
	private int labelPid;

	/**
	 * Constructor
	 *
	 * @param context
	 * @throws SQLException
	 */
	public NewSexTypeWizardPage1(IEclipseContext context) throws SQLException {
		super("New sex type wizard Page 1");
		setTitle("Sex type");
		setDescription("Add a sex type to this HRE project");
		provider = new SexTypeProvider();
		final int size = provider.get().size();
		labelPid = size + 1;
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
				.setEditingSupport(new SexTypeLabelEditingSupport(tableViewer));
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getSexTypeList(labelPid));
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