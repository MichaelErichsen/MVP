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

import net.myerichsen.hremvp.project.providers.PersonNameStyleProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Add a person name style wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 25. feb. 2019
 *
 */
public class NewPersonNameStyleWizardPage2 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Text textLabelPid;
	private Text textIsoCode;
	private Text textStyleName;
	private TableViewer tableViewer;
	private PersonNameStyleProvider provider;
	private Text textNamePartCount;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonNameStyleWizardPage2(IEclipseContext context) {
		super("Person name style wizard Page 1");
		setTitle("Person name style");
		setDescription("Add parts of a new person name style");
		try {
			provider = new PersonNameStyleProvider();
		} catch (final SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		final Label lblNewPersonName = new Label(container, SWT.NONE);
		lblNewPersonName.setText("New person name style pid");

		textLabelPid = new Text(container, SWT.BORDER);
		textLabelPid.setEditable(false);
		textLabelPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblIsoCode = new Label(container, SWT.NONE);
		lblIsoCode.setText("ISO Code");

		textIsoCode = new Text(container, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblStyleName = new Label(container, SWT.NONE);
		lblStyleName.setText("Style name");

		textStyleName = new Text(container, SWT.BORDER);
		textStyleName.setEditable(false);
		textStyleName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNumberOfName = new Label(container, SWT.NONE);
		lblNumberOfName.setText("Number of name parts");
		
		textNamePartCount = new Text(container, SWT.BORDER);
		textNamePartCount.setEditable(false);
		textNamePartCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnLabelId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnDictionaryLabelId = tableViewerColumnLabelId
				.getColumn();
		tblclmnDictionaryLabelId.setWidth(111);
		tblclmnDictionaryLabelId.setText("Dictionary label id");
		tableViewerColumnLabelId
				.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnPartNo = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPartNo = tableViewerColumnPartNo.getColumn();
		tblclmnPartNo.setWidth(100);
		tblclmnPartNo.setText("Part no.");
		tableViewerColumnPartNo.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnPartName = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPartName = tableViewerColumnPartName
				.getColumn();
		tblclmnPartName.setWidth(334);
		tblclmnPartName.setText("Part name");
		tableViewerColumnPartName
				.setLabelProvider(new HREColumnLabelProvider(2));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(provider.getPersonNameStyleList(0));

	}

	/**
	 * @return the tableViewer
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

//	/**
//	 * @return the textIsoCode
//	 */
//	public Combo getTextIsoCode() {
//		return textIsoCode;
//	}

	/**
	 * @return the textLabelPid
	 */
	public Text getTextLabelPid() {
		return textLabelPid;
	}

	/**
	 * @return the textStyleName
	 */
	public Text getTextStyleName() {
		return textStyleName;
	}
}
