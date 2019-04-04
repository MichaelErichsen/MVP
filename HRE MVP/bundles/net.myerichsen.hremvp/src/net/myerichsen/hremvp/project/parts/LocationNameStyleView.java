package net.myerichsen.hremvp.project.parts;

import java.util.List;
import java.util.logging.Level;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import net.myerichsen.hremvp.project.providers.LocationNameMapProvider;
import net.myerichsen.hremvp.project.providers.LocationNameStyleProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all data about a Name Style
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 31. mar. 2019
 */
public class LocationNameStyleView {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Text textId;
	private Text textLabelId;
	private Text textIsoCode;
	private Text textStyleName;
	private TableViewer tableViewer;

	private LocationNameStyleProvider provider;
	private LocationNameMapProvider lnmp;
	private int locationNameStylePid;

	private DictionaryProvider dp;

	/**
	 * Constructor
	 *
	 */
	public LocationNameStyleView() {
		try {
			provider = new LocationNameStyleProvider();
			lnmp = new LocationNameMapProvider();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 * @throws MvpException Application specific exception
	 */
	@PostConstruct
	public void createControls(Composite parent) throws MvpException {
		parent.setLayout(new GridLayout(4, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setLayoutData(
				new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblId.setText("Location name style id");

		textId = new Text(parent, SWT.BORDER);
		textId.setEditable(false);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label lblLabelId = new Label(parent, SWT.NONE);
		lblLabelId.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLabelId.setText("Label id");

		textLabelId = new Text(parent, SWT.BORDER);
		textLabelId.setEditable(false);
		textLabelId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label lblLanguage = new Label(parent, SWT.NONE);
		lblLanguage.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblLanguage.setText("ISO Code");

		textIsoCode = new Text(parent, SWT.BORDER);
		textIsoCode.setEditable(false);
		textIsoCode.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		final Label lblName = new Label(parent, SWT.NONE);
		lblName.setText("Style Name");

		textStyleName = new Text(parent, SWT.BORDER);
		textStyleName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(50);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnDictionaryId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnDictionaryId = tableViewerColumnDictionaryId
				.getColumn();
		tblclmnDictionaryId.setWidth(100);
		tblclmnDictionaryId.setText("Dictionary id");
		tableViewerColumnDictionaryId
				.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnPartNo = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPartNo = tableViewerColumnPartNo.getColumn();
		tblclmnPartNo.setWidth(82);
		tblclmnPartNo.setText("Part No.");
		tableViewerColumnPartNo.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnMapLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabel = tableViewerColumnMapLabel.getColumn();
		tblclmnLabel.setWidth(200);
		tblclmnLabel.setText("Map part label");
		tableViewerColumnMapLabel.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 3));
		tableViewerColumnMapLabel
				.setLabelProvider(new HREColumnLabelProvider(3));
		HREColumnLabelProvider.addEditingSupport(tableViewer);

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLocationNameStyle();
			}
		});
		buttonUpdate.setText("Update");

		try {
			provider.get();
			final List<List<String>> locationNameStyleList = provider
					.getStringList();
			locationNameStylePid = Integer
					.parseInt(locationNameStyleList.get(0).get(0));
			textId.setText(locationNameStyleList.get(0).get(0));
			textLabelId.setText(Integer.toString(provider.getLabelPid()));
			textIsoCode.setText(locationNameStyleList.get(0).get(1));
			textStyleName.setText(locationNameStyleList.get(0).get(2));

			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.setInput(lnmp.getStringList(locationNameStylePid));
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
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
	 * @param ls A list of style id, iso code and label
	 */
	@Inject
	@Optional
	private void subscribeLabelPidUpdateTopic(
			@UIEventTopic(Constants.LABEL_PID_UPDATE_TOPIC) List<String> ls) {
		try {
			provider.get();
			final String locationNameStylePidString = ls.get(0);
			final List<List<String>> locationNameStyleList = provider
					.getStringList();

			for (final List<String> list : locationNameStyleList) {
				if (list.get(0).equals(locationNameStylePidString)) {
					LOGGER.info("Received " + list.get(0) + ", " + list.get(1)
							+ ", " + list.get(2));

					textId.setText(locationNameStyleList.get(0).get(0));
					textLabelId
							.setText(Integer.toString(provider.getLabelPid()));
					textIsoCode.setText(list.get(1));
					textStyleName.setText(list.get(2));

					tableViewer.setInput(lnmp.getStringList(
							Integer.parseInt(locationNameStylePidString)));
					tableViewer.refresh();

					break;
				}
			}

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}

	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	protected void updateLocationNameStyle() {
		if (textStyleName.getText().length() == 0) {
			eventBroker.post("MESSAGE", "Style name must not be empty");
			textStyleName.setFocus();
			return;
		}

		try {
			provider.get(locationNameStylePid);
			final int labelPid = provider.getLabelPid();
			dp = new DictionaryProvider();
			final List<List<String>> stringListDp = dp.getStringList(labelPid);
			final String text = textStyleName.getText();
			if (text.equals(stringListDp.get(0).get(1)) == false) {
				final int dictionaryPid = Integer
						.parseInt(stringListDp.get(0).get(2));
				dp.get(dictionaryPid);
				dp.setDictionaryPid(dictionaryPid);
				dp.setLabel(text);
				dp.update();
			}
			LOGGER.info("location name style pid " + locationNameStylePid
					+ " has been updated to \"" + text + "\"");

			final List<List<String>> stringList = lnmp
					.getStringList(locationNameStylePid);
			final List<List<String>> input = (List<List<String>>) tableViewer
					.getInput();

			for (int i = 0; i < input.size(); i++) {
				for (final List<String> existingElement : stringList) {
					LOGGER.fine(input.get(i).get(1) + ", " + input.get(i).get(3)
							+ " - " + existingElement.get(1) + ", "
							+ existingElement.get(3));

					if (input.get(i).get(1).equals(existingElement.get(1))) {
						if ((input.get(i).get(3)
								.equals(existingElement.get(3)) == false)) {
							final int dictionaryPid = Integer
									.parseInt(input.get(i).get(4));
							dp.get(dictionaryPid);
							dp.setDictionaryPid(dictionaryPid);
							dp.setLabel(input.get(i).get(3));
							dp.update();

							LOGGER.info("Updated dictionary element "
									+ input.get(i).get(1) + " to \""
									+ input.get(i).get(3) + "\"");
						}
						break;
					}
				}
			}
			eventBroker.post("MESSAGE", "location name style "
					+ locationNameStylePid + " has been updated");
			eventBroker.post(Constants.LOCATION_NAME_STYLE_PID_UPDATE_TOPIC,
					locationNameStylePid);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}

}
