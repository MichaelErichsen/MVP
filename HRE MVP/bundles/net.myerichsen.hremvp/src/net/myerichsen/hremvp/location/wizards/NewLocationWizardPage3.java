package net.myerichsen.hremvp.location.wizards;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.location.providers.LocationNameStyleProvider;

/**
 * Location name parts wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 2. nov. 2018
 *
 */
public class NewLocationWizardPage3 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final List<Text> textList = new ArrayList<>();
	private final List<Label> labelList = new ArrayList<>();
	private Text textGoogleMapsKey;
	private final IEclipseContext context;

	/**
	 * Constructor
	 *
	 * @param context The Eclipse Context
	 *
	 */
	public NewLocationWizardPage3(IEclipseContext context) {
		super("wizardPage");
		this.context = context;
		setTitle("Location Name Parts");
		setDescription("Enter the parts of the location name");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets. Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		try {
			final LocationNameStyleProvider provider = new LocationNameStyleProvider();
			final NewLocationWizard wizard = (NewLocationWizard) getWizard();

			if (wizard != null) {
				final int key = wizard.getLocationNameStyle();
				LOGGER.info("Key: " + key);

				if (key != 0) {
					provider.get(key);
					final List<LocationNameMaps> a = provider.getMapList();
					labelList.clear();
					textList.clear();

					for (final LocationNameMaps map : a) {
						final Label label = new Label(container, SWT.NONE);
						label.setText(map.getLabel());
						labelList.add(label);

						final Text text = new Text(container, SWT.BORDER);
						text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
								true, false, 1, 1));
						text.setText("");
						textList.add(text);

						container.getParent().layout();

						LOGGER.fine("Added " + label.getText() + " and "
								+ text.getText());
					}
				}
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
		}

		final Button btnGetCoordinatesFrom = new Button(container, SWT.NONE);
		btnGetCoordinatesFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				geocode();
			}
		});
		btnGetCoordinatesFrom.setText("Get coordinates from Google");
		new Label(container, SWT.NONE);

		final Label lblGoogleMapsKey = new Label(container, SWT.NONE);
		lblGoogleMapsKey.setText("Google Maps Key");

		textGoogleMapsKey = new Text(container, SWT.BORDER | SWT.PASSWORD);
		textGoogleMapsKey.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		setControl(container);

		setPageComplete(false);
	}

	/**
	 *
	 */
	protected void geocode() {
		final IEventBroker eventBroker = context.get(IEventBroker.class);
		boolean valid = false;
		StringBuilder sb = new StringBuilder();
		String locationPart;

		if ((textGoogleMapsKey == null)
				|| (textGoogleMapsKey.getText().length() == 0)) {
			textGoogleMapsKey.setFocus();
			eventBroker.post("MESSAGE", "Please insert Google Map Key");
			return;
		}

		for (final Text aText : textList) {
			locationPart = aText.getText();
			if (locationPart.length() > 0) {
				valid = true;
				sb.append(locationPart + " ");
			}
		}

		if (valid == false) {
			textList.get(0).setFocus();
			eventBroker.post("MESSAGE", "Please insert a location name");
			return;
		}

		locationPart = sb.toString().trim().replace(' ', '+');

		try {
			final CloseableHttpClient client = HttpClients.createDefault();
			final HttpGet request = new HttpGet(
					"https://maps.googleapis.com/maps/api/geocode/json?address="
							+ locationPart + "&key="
							+ textGoogleMapsKey.getText().trim());
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
			final Double lat = location.getDouble("lat");
			final Double lng = location.getDouble("lng");
			LOGGER.info("Lat " + lat + ", lng " + lng);

			final NewLocationWizard wizard = (NewLocationWizard) getWizard();
			wizard.getPage1().getTextXCoordinate()
					.setText(Double.toString(lat));
			wizard.getPage1().getTextYCoordinate()
					.setText(Double.toString(lng));
			wizard.addPage4();
			wizard.getContainer().updateButtons();
			wizard.setLocationName(result0.getString("formatted_address"));
			setPageComplete(true);
			eventBroker.post("MESSAGE",
					"Geocoded address " + result0.getString("formatted_address")
							+ " as lat " + lat + ", lng " + lng);
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
		}
	}

	/**
	 * @return the labelList
	 */
	public List<Label> getLabelList() {
		return labelList;
	}

	/**
	 * @return the textList
	 */
	public List<Text> getTextList() {
		return textList;
	}
}
