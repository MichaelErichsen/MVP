package net.myerichsen.hremvp.project.wizards;

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
import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.ParentRoleProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Wizard page to define a new Parent Role for HRE
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 31. mar. 2019
 *
 */
public class NewParentRoleWizardPage1 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Text textAbbreviation;
	private TableViewer tableViewer;
	private ParentRoleProvider provider;
	private DictionaryProvider dp;
	private int labelPid = 0;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewParentRoleWizardPage1(IEclipseContext context) {
		super("New Parent role wizard page 1");
		setTitle("Parent role");
		setDescription("Add an Parent role to this HRE project");
		try {
			provider = new ParentRoleProvider();
			dp = new DictionaryProvider();
			labelPid = dp.getNextLabelPid();
		} catch (final Exception e) {
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
		lblAbbreviation.setText("Abbreviation (Max 8 characters)");

		textAbbreviation = new Text(container, SWT.BORDER);
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textAbbreviation.setFocus();

		tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(80);
		tblclmnId.setText("Parent role Id");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabelId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnILabelId = tableViewerColumnLabelId
				.getColumn();
		tblclmnILabelId.setWidth(80);
		tblclmnILabelId.setText("Dictionary label Id");
		tableViewerColumnLabelId
				.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnIsoCode = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnIsoCode = tableViewerColumnIsoCode.getColumn();
		tblclmnIsoCode.setWidth(80);
		tblclmnIsoCode.setText("ISO Code");
		tableViewerColumnIsoCode
				.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(394);
		tblclmnLabel.setText("Parent label");
		tableViewerColumnLabel.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 3));
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(3));
		tableViewerColumnLabel.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 3));

		HREColumnLabelProvider.addEditingSupport(tableViewer);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getStringList(labelPid));
		} catch (final Exception e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}

	/**
	 * @return the tableViewer
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * @return the textAbbreviation
	 */
	public Text getTextAbbreviation() {
		return textAbbreviation;
	}
}