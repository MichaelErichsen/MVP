package net.myerichsen.hremvp.project.parts;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreTypeLabelEditingSupport;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.SexTypeProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display a sex type with all national labels
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 22. feb. 2019
 *
 */

public class SexTypeView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Text textSexTypePid;
	private Text textAbbreviation;
	private TableViewer tableViewer;
	private final SexTypeProvider provider;
	private DictionaryProvider dp;
	private int sexTypePid = 0;

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
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
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
		tableViewerColumnLabel
				.setEditingSupport(new HreTypeLabelEditingSupport(tableViewer));
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(1));

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		final Button btnUpdate = new Button(composite, SWT.NONE);
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				updateSexType();
			}
		});
		btnUpdate.setText("Update");

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			provider.get();
			tableViewer.setInput(dp.getStringList(sexTypePid));
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
		this.sexTypePid = sexTypePid;

		try {
			provider.get(sexTypePid);
			textAbbreviation.setText(provider.getAbbreviation());
			textSexTypePid.setText(Integer.toString(provider.getSexTypePid()));

			tableViewer.setInput(dp.getStringList(sexTypePid));
			tableViewer.refresh();
		} catch (final SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	protected void updateSexType() {
		if (textAbbreviation.getText().isEmpty()) {
			eventBroker.post("MESSAGE", "Abbreviation must not be empty");
			textAbbreviation.setFocus();
			return;
		}

		try {
			provider.get(sexTypePid);
			provider.setAbbreviation(textAbbreviation.getText());
			provider.update();

			dp = new DictionaryProvider();
			List<List<String>> stringList = dp.getStringList(sexTypePid);

			List<List<String>> input = (List<List<String>>) tableViewer
					.getInput();

			for (int i = 0; i < input.size(); i++) {
				for (List<String> existingElement : stringList) {
					LOGGER.fine(input.get(i).get(0) + " "
							+ existingElement.get(0) + " " + input.get(i).get(1)
							+ " " + existingElement.get(1));

					if (input.get(i).get(0).equals(existingElement.get(0))) {
						if ((input.get(i).get(1)
								.equals(existingElement.get(1)) == false)) {
							dp = new DictionaryProvider();
							dp.setDictionaryPid(
									Integer.parseInt(existingElement.get(2)));
							dp.setIsoCode(input.get(i).get(0));
							dp.setLabel(input.get(i).get(1));
							dp.setLabelPid(provider.getLabelPid());
							dp.update();
							LOGGER.info("Updated dictionary element "
									+ input.get(i).get(2) + ", "
									+ input.get(i).get(0) + ", "
									+ input.get(i).get(1));
						}
						break;
					}
				}
			}

		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

	}
}
