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
import net.myerichsen.hremvp.project.providers.EventTypeProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display a Event type with all national labels
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 24. feb. 2019
 *
 */

public class EventTypeView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Text textEventTypePid;
	private Text textLabelPid;
	private Text textAbbreviation;
	private TableViewer tableViewer;
	private final EventTypeProvider provider;
	private DictionaryProvider dp;
	private int eventTypePid = 0;
	private int labelPid = 0;

	/**
	 * Constructor
	 *
	 */
	public EventTypeView() {
		provider = new EventTypeProvider();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(4, false));

		final Label lblEventTypeId = new Label(parent, SWT.NONE);
		lblEventTypeId.setText("Event type id");

		textEventTypePid = new Text(parent, SWT.BORDER);
		textEventTypePid.setEditable(false);
		textEventTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblLabelPid = new Label(parent, SWT.NONE);
		lblLabelPid.setText("Label id");

		textLabelPid = new Text(parent, SWT.BORDER);
		textLabelPid.setEditable(false);
		textLabelPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblAbbreviation = new Label(parent, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(parent, SWT.BORDER);
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(80);
		tblclmnId.setText("Event Type Id");
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
		tblclmnLabel.setText("Event label");
		tableViewerColumnLabel.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 3));
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(3));

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));

		final Button btnUpdate = new Button(composite, SWT.NONE);
		btnUpdate.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				updateEventType();
			}
		});
		btnUpdate.setText("Update");

		try {
			provider.get();
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.setInput(provider.getEventTypeList(labelPid));
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
	 * @param labelPid
	 */
	@Inject
	@Optional
	private void subscribeLabelPidUpdateTopic(
			@UIEventTopic(Constants.LABEL_PID_UPDATE_TOPIC) List<String> ls) {
		eventTypePid = Integer.parseInt(ls.get(0));
		textEventTypePid.setText(ls.get(0));
		labelPid = Integer.parseInt(ls.get(1));
		textLabelPid.setText(ls.get(1));
		textAbbreviation.setText(ls.get(2));
		LOGGER.info("Received label id " + labelPid);

		try {
			tableViewer.setInput(provider.getEventTypeList(labelPid));
		} catch (final SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	protected void updateEventType() {
		if (textAbbreviation.getText().isEmpty()) {
			eventBroker.post("MESSAGE", "Abbreviation must not be empty");
			textAbbreviation.setFocus();
			return;
		}

		try {
			final List<List<String>> eventTypeList = provider
					.getEventTypeList(labelPid);

			eventTypePid = Integer.parseInt(eventTypeList.get(0).get(0));
			labelPid = Integer.parseInt(eventTypeList.get(0).get(1));

			provider.get(eventTypePid);
			provider.setAbbreviation(textAbbreviation.getText());
			provider.update();
			LOGGER.info("Event pid " + eventTypePid + " has been updated");

			dp = new DictionaryProvider();
			final List<List<String>> stringList = dp.getStringList(labelPid);

			final List<List<String>> input = (List<List<String>>) tableViewer
					.getInput();

			for (int i = 0; i < input.size(); i++) {
				for (final List<String> existingElement : stringList) {
					LOGGER.info(input.get(i).get(2) + ", " + input.get(i).get(3)
							+ " - " + existingElement.get(0) + ", "
							+ existingElement.get(1) + ", "
							+ existingElement.get(2));

					if (input.get(i).get(2).equals(existingElement.get(0))) {
						if ((input.get(i).get(3)
								.equals(existingElement.get(1)) == false)) {
							dp = new DictionaryProvider();
							dp.setDictionaryPid(
									Integer.parseInt(existingElement.get(2)));
							dp.setIsoCode(input.get(i).get(2));
							dp.setLabel(input.get(i).get(3));
							dp.setLabelPid(provider.getLabelPid());
							dp.setLabelType("EVENT");
							dp.update();
							LOGGER.info("Updated dictionary element "
									+ input.get(i).get(0) + ", "
									+ input.get(i).get(1) + ", "
									+ input.get(i).get(2) + ", "
									+ input.get(i).get(3));
						}
						break;
					}
				}
			}
			eventBroker.post("MESSAGE",
					"Event type " + eventTypePid + " has been updated");
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}