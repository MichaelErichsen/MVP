package net.myerichsen.hremvp.person.servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.SexTypes;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.SexTypes}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. feb. 2019
 *
 */
public class SexTypeServer implements IHREServer {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	final IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	private int sexTypePid;
	private String abbreviation;
	private String label;
	private int languagePid;
	private String languageLabel;
	private String isoCode;

	private final SexTypes sexType;
	private final Dictionary dictionary;

	/**
	 * Constructor
	 *
	 */
	public SexTypeServer() {
		sexType = new SexTypes();
		dictionary = new Dictionary();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		sexType.delete(key);
	}

	/**
	 * @param target The request target
	 * @throws NumberFormatException A Number Format Exception
	 * @throws SQLException          An exception that provides information on a
	 *                               database access error or other errors
	 * @throws MvpException          Application specific exception
	 */
	public void deleteRemote(String target)
			throws NumberFormatException, SQLException, MvpException {
		final String[] targetParts = target.split("/");
		final int targetSize = targetParts.length;
		delete(Integer.parseInt(targetParts[targetSize - 1]));
	}

	/**
	 *
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<SexTypes> get() throws SQLException {
		return sexType.get();
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
		sexType.get(key);
		setSexTypePid(sexType.getSexTypePid());
		setAbbreviation(sexType.getAbbreviation());

		final List<Dictionary> fkIsoCode = dictionary
				.getFKIsoCode(store.getString("GUILANGUAGE"));
		String label = "";

		for (final Dictionary dict : fkIsoCode) {
			if (key == dict.getLabelPid()) {
				label = dict.getLabel();
			}
		}
		setLabel(label);
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
	 * @param response HttpServletResponse
	 * @param target   String
	 * @return JSONString a JSON String
	 * @throws NumberFormatException NumberFormatException
	 * @throws SQLException          An exception that provides information on a
	 *                               database access error or other errors
	 * @throws MvpException          Application specific exception
	 * @throws IOException           IOException
	 * @throws JSONException         JSONException
	 */
	public String getRemote(HttpServletResponse response, String target)
			throws NumberFormatException, SQLException, MvpException,
			IOException, JSONException {
		final String[] targetParts = target.split("/");
		final int targetSize = targetParts.length;

		get(Integer.parseInt(targetParts[targetSize - 1]));

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
		js.key("languageLabel");
		js.value(languageLabel);
		js.key("isoCode");
		js.value(isoCode);
		js.endObject();
		return js.toString();
	}

	/**
	 * @return stringList A list of lists of pid, abbreviation and label in the
	 *         active language
	 * @throws SQLException
	 */
	public List<List<String>> getSexTypeList() throws SQLException {
		final List<SexTypes> list = sexType.get();
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		for (final SexTypes type : list) {
			stringList = new ArrayList<>();
			final int labelPid = type.getLabelPid();
			stringList.add(Integer.toString(labelPid));
			stringList.add(type.getAbbreviation());

			LOGGER.info(store.getString("GUILANGUAGE"));

			final List<Dictionary> fkIsoCode = dictionary
					.getFKIsoCode(store.getString("GUILANGUAGE"));
			String label = "";

			for (final Dictionary dict : fkIsoCode) {
				if (labelPid == dict.getLabelPid()) {
					label = dict.getLabel();
				}
			}
			stringList.add(label);
			lls.add(stringList);
		}
		return lls;
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
	 */
	@Override
	public int insert() throws SQLException {
		sexType.setSexTypePid(sexTypePid);
		sexType.setAbbreviation(abbreviation);
		return sexType.insert();
	}

	/**
	 * Insert a row
	 *
	 * @param request HttpServletRequest
	 * @throws IOException   IOException
	 * @throws JSONException JSONException
	 * @throws SQLException  SQLException
	 */
	public void insertRemote(HttpServletRequest request)
			throws IOException, JSONException, SQLException {
		final StringBuilder sb = new StringBuilder();
		String s = "";
		final BufferedReader br = request.getReader();

		while (null != (s = br.readLine())) {
			sb.append(s);
		}

		br.close();

		final JSONObject jsonObject = new JSONObject(sb.toString());

		LOGGER.info(jsonObject.toString(2));

		setSexTypePid(jsonObject.getInt("sexTypePid"));
		setAbbreviation(jsonObject.getString("abbreviation"));
		setLabel(jsonObject.getString("label"));
		setLanguagePid(jsonObject.getInt("languagePid"));
		insert();
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
		sexType.setSexTypePid(sexTypePid);
		sexType.setAbbreviation(abbreviation);
		sexType.update();
	}

	/**
	 * Update a row
	 *
	 * @param request HttpServletRequest
	 * @throws IOException   IOException
	 * @throws JSONException JSONException
	 * @throws SQLException  An exception that provides information on a
	 *                       database access error or other errors
	 * @throws MvpException  Application specific exception
	 */
	public void updateRemote(HttpServletRequest request)
			throws IOException, JSONException, SQLException, MvpException {
		final StringBuilder sb = new StringBuilder();
		String s = "";
		final BufferedReader br = request.getReader();

		while (null != (s = br.readLine())) {
			sb.append(s);
		}

		br.close();

		final JSONObject jsonObject = new JSONObject(sb.toString());

		LOGGER.info(jsonObject.toString(2));

		setSexTypePid(jsonObject.getInt("sexTypePid"));
		setAbbreviation(jsonObject.getString("abbreviation"));
		setLabel(jsonObject.getString("label"));
		setLanguagePid(jsonObject.getInt("languagePid"));
		update();
	}
}
