package net.myerichsen.hremvp.serverlogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Languages;
import net.myerichsen.hremvp.dbmodels.SexTypes;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.SexTypes}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 21. okt. 2018
 *
 */
/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 7. okt. 2018
 *
 */
public class SexTypeServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int sexTypePid;
	private String abbreviation;
	private String label;
	private int languagePid;
	private String languageLabel;
	private String isoCode;

	private SexTypes sexType;
	private Languages language;

	/**
	 * Constructor
	 *
	 */
	public SexTypeServer() {
		sexType = new SexTypes();
		language = new Languages();
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
	public void deleteRemote(String target) throws NumberFormatException, SQLException, MvpException {
		String[] targetParts = target.split("/");
		int targetSize = targetParts.length;
		delete(Integer.parseInt(targetParts[targetSize - 1]));
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
	public void get(int key) throws SQLException, MvpException {
		sexType.get(key);
		setSexTypePid(sexType.getSexTypePid());
		setAbbreviation(sexType.getAbbreviation());
		setLabel(sexType.getLabel());
		setLanguagePid(sexType.getLanguagePid());

		language.get(languagePid);
		setLanguageLabel(language.getLabel());
		setIsoCode(language.getIsocode());
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
			throws NumberFormatException, SQLException, MvpException, IOException, JSONException {
		String[] targetParts = target.split("/");
		int targetSize = targetParts.length;

		get(Integer.parseInt(targetParts[targetSize - 1]));

		JSONStringer js = new JSONStringer();
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
	public void insert() throws SQLException {
		sexType.setSexTypePid(sexTypePid);
		sexType.setAbbreviation(abbreviation);
		sexType.setLabel(label);
		sexType.setLanguagePid(languagePid);
		sexType.insert();
	}

	/**
	 * Insert a row
	 *
	 * @param request HttpServletRequest
	 * @throws IOException   IOException
	 * @throws JSONException JSONException
	 * @throws SQLException  SQLException
	 */
	public void insertRemote(HttpServletRequest request) throws IOException, JSONException, SQLException {
		StringBuilder sb = new StringBuilder();
		String s = "";
		BufferedReader br = request.getReader();

		while (null != (s = br.readLine())) {
			sb.append(s);
		}

		br.close();

		JSONObject jsonObject = new JSONObject(sb.toString());

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
	public void update() throws SQLException, MvpException {
		sexType.setSexTypePid(sexTypePid);
		sexType.setAbbreviation(abbreviation);
		sexType.setLabel(label);
		sexType.setLanguagePid(languagePid);
		sexType.update();
	}

	/**
	 * Update a row
	 *
	 * @param request HttpServletRequest
	 * @throws IOException   IOException
	 * @throws JSONException JSONException
	 * @throws SQLException  An exception that provides information on a database
	 *                       access error or other errors
	 * @throws MvpException  Application specific exception
	 */
	public void updateRemote(HttpServletRequest request) throws IOException, JSONException, SQLException, MvpException {
		StringBuilder sb = new StringBuilder();
		String s = "";
		BufferedReader br = request.getReader();

		while (null != (s = br.readLine())) {
			sb.append(s);
		}

		br.close();

		JSONObject jsonObject = new JSONObject(sb.toString());

		LOGGER.info(jsonObject.toString(2));

		setSexTypePid(jsonObject.getInt("sexTypePid"));
		setAbbreviation(jsonObject.getString("abbreviation"));
		setLabel(jsonObject.getString("label"));
		setLanguagePid(jsonObject.getInt("languagePid"));
		update();
	}
}
