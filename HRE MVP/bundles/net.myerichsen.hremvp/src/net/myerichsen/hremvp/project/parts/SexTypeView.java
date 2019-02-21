package net.myerichsen.hremvp.project.parts;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display a sex type with all national labels
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 21. feb. 2019
 *
 */

public class SexTypeView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Text textSexTypePid;
	private Text textAbbreviation;
	private TableViewer tableViewer;
	private final SexTypeProvider provider;
	private DictionaryProvider dp;

	/**
	 * Constructor
	 *
	 */
	public SexTypeView() {
		provider = new SexTypeProvider();
		dp = new DictionaryProvider();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		final Label lblSexTypeId = new Label(parent, SWT.NONE);
		lblSexTypeId.setText("Sex type id");

		textSexTypePid = new Text(parent, SWT.BORDER);
		textSexTypePid.setEditable(false);
		textSexTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblAbbreviation = new Label(parent, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(parent, SWT.BORDER);
		textAbbreviation.setEditable(false);
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
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
		tblclmnLabel.setText("Label");
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(1));

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			provider.get();
			tableViewer.setInput(dp.getStringList(0));
		} catch (final SQLException | MvpException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}

	}

	/**
	 * 
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * 
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param sexTypePid
	 */
	@Inject
	@Optional
	private void subscribeSexTypePidUpdateTopic(
			@UIEventTopic(Constants.SEX_TYPE_PID_UPDATE_TOPIC) int sexTypePid) {
		LOGGER.fine("Received person id " + sexTypePid);
		try {
			provider.get(sexTypePid);
			textAbbreviation.setText(provider.getAbbreviation());
			textSexTypePid.setText(Integer.toString(provider.getSexTypePid()));

			tableViewer.setInput(dp.getStringList(provider.getLabelPid()));
			tableViewer.refresh();
		} catch (final SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
