package net.myerichsen.hremvp.location.wizards;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.HreTypeLabelEditingSupport;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.providers.LocationNameMapProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Location name parts wizard page 2
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. mar. 2019
 *
 */
public class NewLocationWizardPage2 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private Text textGoogleApiKey;
	private final IEclipseContext context;
	private final LocationNameMapProvider provider;
	private TableViewer tableViewer;
	private Text textCoordinates;
	private double lat;
	private double lng;
	private List<List<String>> stringList;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewLocationWizardPage2(IEclipseContext context) {
		super("wizardPage");
		LOGGER.info("Wizard page 2");
		this.context = context;
		setTitle("Location Name Parts");
		setDescription(
				"Enter the parts of the location name. Get coordinates from Google.");
		provider = new LocationNameMapProvider();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets. Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		LOGGER.info("Create controls");
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		final TableViewerColumn tableViewerColumnMapPid = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnMapPid = tableViewerColumnMapPid.getColumn();
		tblclmnMapPid.setWidth(70);
		tblclmnMapPid.setText("Map Pid");
		tableViewerColumnMapPid.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabelPid = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabelPid = tableViewerColumnLabelPid
				.getColumn();
		tblclmnLabelPid.setWidth(70);
		tblclmnLabelPid.setText("Label Pid");
		tableViewerColumnLabelPid
				.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnPartNo = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPartNo = tableViewerColumnPartNo.getColumn();
		tblclmnPartNo.setWidth(70);
		tblclmnPartNo.setText("Part no.");
		tableViewerColumnPartNo.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnMapLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnMapLabel = tableViewerColumnMapLabel
				.getColumn();
		tblclmnMapLabel.setWidth(200);
		tblclmnMapLabel.setText("Map Label");
		tableViewerColumnMapLabel
				.setLabelProvider(new HREColumnLabelProvider(3));

		final TableViewerColumn tableViewerColumnPartLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPartLabel = tableViewerColumnPartLabel
				.getColumn();
		tblclmnPartLabel.setWidth(200);
		tblclmnPartLabel.setText("Part Label");
		tableViewerColumnPartLabel.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 4));
		tableViewerColumnPartLabel
				.setLabelProvider(new HREColumnLabelProvider(4));

		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
				tableViewer, new FocusCellOwnerDrawHighlighter(tableViewer));
		final ColumnViewerEditorActivationStrategy editorActivationStrategy = new ColumnViewerEditorActivationStrategy(
				tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return (event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL)
						|| (event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION)
						|| ((event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED)
								&& (event.keyCode == SWT.CR))
						|| (event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC);
			}
		};

		TableViewerEditor.create(tableViewer, focusCellManager,
				editorActivationStrategy,
				ColumnViewerEditor.TABBING_HORIZONTAL
						| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL
						| ColumnViewerEditor.KEYBOARD_ACTIVATION);

		tableViewer.getTable().addTraverseListener(new TraverseListener() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse.
			 * swt.events.TraverseEvent)
			 */
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.keyCode == SWT.TAB) {
					LOGGER.fine("Traversed " + e.keyCode);

					final int itemCount = tableViewer.getTable().getItemCount();
					final int selectionIndex = tableViewer.getTable()
							.getSelectionIndex();
					if (selectionIndex < (itemCount - 1)) {
						e.doit = false;

					} else {
						e.doit = true;
					}

				}
			}

		});

		final Label lblGoogleMapsKey = new Label(container, SWT.NONE);
		lblGoogleMapsKey.setText("Google API Key");

		textGoogleApiKey = new Text(container, SWT.BORDER | SWT.PASSWORD);
		textGoogleApiKey.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textGoogleApiKey.setText(store.getString("GOOGLEAPIKEY"));

		final Button btnGetCoordinatesFrom = new Button(container, SWT.NONE);
		btnGetCoordinatesFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				for (final TableItem item : tableViewer.getTable().getItems()) {
					if (item.getText(4).length() > 0) {
						setPageComplete(true);
					}
				}
				geocode();
			}
		});
		btnGetCoordinatesFrom.setText("Get coordinates from Google");

		textCoordinates = new Text(container, SWT.BORDER);
		textCoordinates.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textCoordinates.setEditable(false);

		setControl(container);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			final NewLocationWizard wizard = (NewLocationWizard) getWizard();
			final int locationNameStylePid = wizard.getPage1()
					.getLocationNameStylePid();
			stringList = provider.getStringList(locationNameStylePid);
			tableViewer.setInput(stringList);
		} catch (final Exception e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}

		setPageComplete(true);
	}

	/**
	 *
	 */
	protected void geocode() {
		final IEventBroker eventBroker = context.get(IEventBroker.class);
		boolean valid = false;
		StringBuilder sb = new StringBuilder();
		String locationPart;

		for (final TableItem item : tableViewer.getTable().getItems()) {
			locationPart = item.getText(4);
			if (locationPart.length() > 0) {
				valid = true;
				sb.append(locationPart + " ");
			}
		}

		if (valid) {
			setPageComplete(true);
			final NewLocationWizard wizard = (NewLocationWizard) getWizard();
			wizard.addBackPages();
			wizard.getContainer().updateButtons();
		}
		if ((textGoogleApiKey == null)
				|| (textGoogleApiKey.getText().length() == 0)) {
			textGoogleApiKey.setFocus();
			eventBroker.post("MESSAGE", "Please insert Google Map Key");
			return;
		}

		if (valid == false) {
			tableViewer.getTable().select(0);
			eventBroker.post("MESSAGE", "Please enter a location name");
			return;
		}

		locationPart = sb.toString().trim().replace(' ', '+');

		try {
			final CloseableHttpClient client = HttpClients.createDefault();
			String requestString = "https://maps.googleapis.com/maps/api/geocode/json?address="
					+ locationPart + "&key="
					+ textGoogleApiKey.getText().trim();
			final HttpGet request = new HttpGet(requestString);

			final CloseableHttpResponse response = client.execute(request);

			final StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() != 200) {
				throw new MvpException(statusLine.getReasonPhrase());
			}

			final BufferedReader br = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			String s;
			sb = new StringBuilder();

			while (null != (s = br.readLine())) {
				sb.append(s);
			}

			br.close();
			response.close();
			client.close();

			final JSONObject jsonObject = new JSONObject(sb.toString());

			LOGGER.fine(jsonObject.toString(2));

			final String status = jsonObject.getString("status");

			if (!status.equals("OK")) {
				throw new MvpException(
						status + ", " + jsonObject.getString("error_message"));
			}

			final JSONArray results = jsonObject.getJSONArray("results");
			final JSONObject result0 = results.getJSONObject(0);
			final JSONObject geometry = result0.getJSONObject("geometry");
			final JSONObject location = geometry.getJSONObject("location");
			lat = location.getDouble("lat");
			lng = location.getDouble("lng");
			LOGGER.fine("Lat " + lat + ", lng " + lng);
			textCoordinates.setText("Lat " + lat + ", lng " + lng);

			final NewLocationWizard wizard = (NewLocationWizard) getWizard();
			wizard.addBackPages();
			wizard.getContainer().updateButtons();
			wizard.setLocationName(result0.getString("formatted_address"));
			eventBroker.post("MESSAGE",
					"Geocoded address " + result0.getString("formatted_address")
							+ " as lat " + lat + ", lng " + lng);
		} catch (final Exception e) {
			LOGGER.severe(e.getCause() + ": " + e.getMessage());
			eventBroker.post("MESSAGE", e.getCause() + ": " + e.getMessage());
		}
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @return the stringList
	 */
	public List<List<String>> getStringList() {
		return stringList;
	}

}
