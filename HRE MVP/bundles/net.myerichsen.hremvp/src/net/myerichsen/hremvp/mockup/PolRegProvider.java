package net.myerichsen.hremvp.mockup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.person.providers.AbstractHreProvider;

public class PolRegProvider extends AbstractHreProvider {
	ScopedPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");

	private List<PolRegModel> modelList;

	public PolRegProvider() {
		LOGGER.info("Constructor");
		try {
			final String name = store.getString("PERNAME");
			final String[] nameParts = name.split("\\+");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < (nameParts.length - 1); i++) {
				sb.append(nameParts[i] + "+");
			}
			final String mid = sb.toString();
			final String firstNames = mid.substring(0, mid.length() - 1);
			final String lastName = nameParts[(nameParts.length - 1)];
			final URL url = new URL(
					"http://www.politietsregisterblade.dk/api/1?type=person&firstnames="
							+ firstNames + "&lastname=" + lastName);
			final HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept", "application/json");

			final int responseCode = conn.getResponseCode();
			System.out.println("Response code: " + responseCode);

			final BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			sb = new StringBuilder();
			String s = "";

			while ((s = br.readLine()) != null) {
				sb.append(s);
			}

			final JSONArray resultArray = new JSONArray(sb.toString());

			modelList = new ArrayList<PolRegModel>();

			int l = resultArray.length();
			l = l > 50 ? 50 : l;

			for (int i = 0; i < l; i++) {
				final JSONObject jsonPerson = (JSONObject) resultArray.get(i);
				final PolRegModel model = new PolRegModel();
				try {
					model.setDateofbirth(jsonPerson.getString("dateofbirth"));
				} catch (final Exception localException1) {
				}
				try {
					model.setBirthplace(jsonPerson.getString("birthplace"));
				} catch (final Exception localException2) {
				}
				try {
					model.setPerson_type(jsonPerson.getString("person_type"));
				} catch (final Exception localException3) {
				}
				try {
					model.setDateofdeath(jsonPerson.getString("dateofdeath"));
				} catch (final Exception localException4) {
				}
				try {
					model.setId(jsonPerson.getString("id"));
				} catch (final Exception localException5) {
				}
				try {
					model.setRegisterblad_id(
							jsonPerson.getString("registerblad_id"));
				} catch (final Exception localException6) {
				}
				try {
					model.setFirstnames(jsonPerson.getString("firstnames"));
				} catch (final Exception localException7) {
				}
				try {
					model.setLastname(jsonPerson.getString("lastname"));
				} catch (final Exception localException8) {
				}

				modelList.add(model);
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
		}
	}

	public List<PolRegModel> getModelList() {
		LOGGER.info("Get Model List");
		return modelList;
	}

	@Override
	public void readFromH2(int i) {
	}

	public void setModelList(List<PolRegModel> modelList) {
		this.modelList = modelList;
	}
}
