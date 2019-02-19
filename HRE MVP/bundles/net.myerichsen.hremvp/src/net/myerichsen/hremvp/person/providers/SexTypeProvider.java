package net.myerichsen.hremvp.person.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.SexTypes;
import net.myerichsen.hremvp.person.servers.SexTypeServer;

/**
 * Provide all data for a sex type
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. feb. 2019
 *
 */
public class SexTypeProvider implements IHREProvider {
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int sexTypePid;
	private String abbreviation;
	private String label;
	private int languagePid;
	private String languageLabel;
	private String isoCode;

	private final SexTypeServer server;

	/**
	 * Constructor
	 *
	 */
	public SexTypeProvider() {
		server = new SexTypeServer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);

	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws IOException             IOException
	 * @throws ClientProtocolException ClientProtocolException
	 * @throws MvpException            Application specific exception
	 *
	 */
	public void deleteRemote(int key)
			throws ClientProtocolException, IOException, MvpException {
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpDelete request = new HttpDelete("http://"
				+ store.getString("SERVERADDRESS") + ":"
				+ store.getString("SERVERPORT") + "/mvp/v100/sextype/" + key);
		final CloseableHttpResponse response = client.execute(request);
		final StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() != 200) {
			throw new MvpException(statusLine.getReasonPhrase());
		}

		response.close();
		client.close();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<SexTypes> get() throws SQLException {
		return server.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setSexTypePid(server.getSexTypePid());
		setAbbreviation(server.getAbbreviation());
		setLabel(server.getLabel());
		setLanguagePid(server.getLanguagePid());
		setLanguageLabel(server.getLanguageLabel());
		setIsoCode(server.getIsoCode());
	}

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the languageLabel
	 */
	public String getLanguageLabel() {
		return languageLabel;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws ClientProtocolException Signals an error in the HTTP protocol
	 * @throws IOException             IOException
	 * @throws JSONException           Thrown to indicate a problem with the
	 *                                 JSON API
	 * @throws MvpException            Application specific exception
	 */
	public void getRemote(int key)
			throws ClientProtocolException, IOException, MvpException {
		final StringBuilder sb = new StringBuilder();
		String s = "";

		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpGet request = new HttpGet(
				"http://127.0.0.1:8000/mvp/v100/sextype/" + key);
		final CloseableHttpResponse response = client.execute(request);

		final StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() != 200) {
			throw new MvpException(statusLine.getReasonPhrase());
		}

		final BufferedReader br = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		while (null != (s = br.readLine())) {
			sb.append(s);
		}

		br.close();
		response.close();
		client.close();

		final JSONObject jsonObject = new JSONObject(sb.toString());

		LOGGER.info(jsonObject.toString(2));

		setSexTypePid(jsonObject.getInt("sexTypePid"));
		setAbbreviation(jsonObject.getString("abbreviation"));
		setLabel(jsonObject.getString("label"));
		setLanguagePid(jsonObject.getInt("languagePid"));
		setLanguageLabel(jsonObject.getString("languageLabel"));
		setIsoCode(jsonObject.getString("isoCode"));
	}

	/**
	 * @return stringList A list of lists of pid, abbreviation and label in the
	 *         active language
	 * @throws SQLException
	 */
	public List<List<String>> getSexTypeList() throws SQLException {
		return server.getSexTypeList();
	}

	/**
	 * @return the sexTypePid
	 */
	public int getSexTypePid() {
		return sexTypePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		server.setSexTypePid(sexTypePid);
		server.setAbbreviation(abbreviation);
		server.setLabel(label);
		server.setLanguagePid(languagePid);
		return server.insert();
	}

	/**
	 * Insert a row
	 *
	 * @throws ParseException ParseException
	 * @throws MvpException   Application specific exception
	 * @throws IOException    IOException
	 */
	public void insertRemote()
			throws ParseException, IOException, MvpException {
		final JSONStringer js = new JSONStringer();
		js.object();
		js.key("sexTypePid");
		js.value(sexTypePid);
		js.key("abbreviation");
		js.value(abbreviation);
		js.key("label");
		js.value(label);
		js.key("languagePid");
		js.value(languagePid);
		js.endObject();

		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost request = new HttpPost(
				"http://127.0.0.1:8000/mvp/v100/sextype/");
		final StringEntity params = new StringEntity(js.toString());
		request.addHeader("content-type", "application/json");
		request.setEntity(params);
		final CloseableHttpResponse result = client.execute(request);
		final StatusLine statusLine = result.getStatusLine();

		if (statusLine.getStatusCode() != 200) {
			throw new MvpException(statusLine.getReasonPhrase());
		}

		result.close();
		client.close();
	}

	/**
	 * @param abbreviation the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param languageLabel the languageLabel to set
	 */
	public void setLanguageLabel(String languageLabel) {
		this.languageLabel = languageLabel;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		this.languagePid = languagePid;
	}

	/**
	 * @param sexTypePid the sexTypePid to set
	 */
	public void setSexTypePid(int sexTypePid) {
		this.sexTypePid = sexTypePid;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setSexTypePid(sexTypePid);
		server.setAbbreviation(abbreviation);
		server.setLabel(label);
		server.setLanguagePid(languagePid);
		server.update();
	}
}
