package net.myerichsen.hremvp.mockup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FamilySearchProgressDialog implements IRunnableWithProgress {
	@Inject
	protected static IEventBroker eventBroker;
	 protected static final Logger LOGGER = Logger.getLogger("global");

	 private final String USER_AGENT = "Mozilla/5.0";
	 private final String ACCEPT = "application/json";
	 private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	 private final String SEARCH_URL = "https://integration.familysearch.org/platform/tree/search";

	private List<FamilySearchModel> itemList;

	private final String accessToken;

	private final String name;

	private final FamilySearchProvider familySearchProvider;

	public FamilySearchProgressDialog(String accessToken, String name,
			FamilySearchProvider familySearchProvider) {
		 this.accessToken = accessToken;
		 this.name = name;
		 this.familySearchProvider = familySearchProvider;
	}

	public synchronized void run(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		 int counter = 0;
		 String message = "";

		 monitor.beginTask("Fetching Family Search results", -1);

		 this.itemList = new ArrayList<FamilySearchModel>();

		 String getData = "?access_token=" + this.accessToken
				+ "&q=name:" + this.name.toLowerCase().trim();
		try {
			 URL url = new URL(
					"https://integration.familysearch.org/platform/tree/search"
							+ getData);
			 HttpsURLConnection conn = (HttpsURLConnection) url
					.openConnection();
			 conn.setRequestMethod("GET");
			 conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			 conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			 conn.setRequestProperty("Accept", "application/json");
			 conn.setRequestProperty("Authorization",
					"Bearer " + this.accessToken);

			 int responseCode = conn.getResponseCode();
			 LOGGER.fine("Sending 'GET' request to URL: " + url);
			 LOGGER.fine("Response code: " + responseCode);

			 BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			 StringBuilder sb = new StringBuilder();
			 String s = "";

			 while ((s = br.readLine()) != null) {
				 sb.append(s);
			}

			 JSONObject jsonObject = new JSONObject(sb.toString());
			 int results = jsonObject.getInt("results");
			 LOGGER.info("Results: " + results);

			 JSONArray entries = (JSONArray) jsonObject.get("entries");

			 int lim = entries.length();
			 LOGGER.info("Entries: " + lim);
			 if (lim > 10) {
				 lim = 10;
			}

			 for (int i = 0; i < lim; i++) {
				 JSONObject content = (JSONObject) entries.get(i);
				 JSONObject content2 = (JSONObject) content
						.get("content");
				 JSONObject gedcomx = (JSONObject) content2
						.get("gedcomx");
				 JSONArray persons = (JSONArray) gedcomx
						.get("persons");

				 int arrayLength = persons.length();
				 LOGGER.info("Persons: " + arrayLength);
				 if (arrayLength > 10) {
					 arrayLength = 10;
				}

				 for (int j = 0; j < arrayLength; j++) {
					 JSONObject person = (JSONObject) persons.get(j);
					 JSONObject display = (JSONObject) person
							.get("display");

					 LOGGER
							.fine("Item " + j + ": " + display.toString(2));

					 FamilySearchModel item = new FamilySearchModel();
					try {
						 item.setName(display.getString("name"));
					} catch (Exception localException1) {
					}
					try {
						 item.setGender(display.getString("gender"));
					} catch (Exception localException2) {
					}
					try {
						 item
								.setBirthDate(display.getString("birthDate"));
					} catch (Exception localException3) {
					}
					try {
						 item
								.setBirthPlace(display.getString("birthPlace"));
					} catch (Exception localException4) {
					}
					try {
						 item
								.setDeathDate(display.getString("deathDate"));
					} catch (Exception localException5) {
					}
					try {
						 item
								.setDeathPlace(display.getString("deathPlace"));
					} catch (Exception localException6) {
					}

					 if (monitor.isCanceled()) {
						 monitor.done();
						 return;
					}

					 wait(100L);
					 monitor.subTask(
							"Getting person " + counter++ + " of " + results);
					 this.itemList.add(item);
					 monitor.worked(1);

					 if (this.itemList.size() > 50) {
						 message = "Items added: "
								+ (this.itemList.size() - 1) + " (" + results
								+ " was found)";
						 LOGGER.info(message);

						 eventBroker.post("MESSAGE", message);
						 monitor.done();
						 return;
					}
				}
			}
			 message = "Items added: " + (this.itemList.size() - 1);
			 LOGGER.info(message);
			 this.familySearchProvider.setItemList(this.itemList);
			 monitor.done();
		} catch (IOException | JSONException e) {
			 LOGGER.severe(e.getClass() + ": " + e.getMessage()
					+ " at line " + e.getStackTrace()[0].getLineNumber());
		}
	}
}
