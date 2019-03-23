package net.myerichsen.hremvp.project.servers;

import java.io.BufferedReader;
import java.io.IOException;
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
import net.myerichsen.hremvp.dbmodels.Languages;
import net.myerichsen.hremvp.dbmodels.SexTypes;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.SexTypes}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. mar. 2019
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
	private final int TableId = 23;

	private final SexTypes sexType;

	/**
	 * Constructor
	 *
	 */
	public SexTypeServer() {
		sexType = new SexTypes();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void delete(int key) throws Exception {
		sexType.delete(key);
	}

	/**
	 * @param target The request target
	 * @throws NumberFormatException A Number Format Exception
	 * @throws Exception             An exception that provides information on a
	 *                               database access error or other errors
	 * @throws MvpException          Application specific exception
	 */
	public void deleteRemote(String target)
			throws NumberFormatException, Exception {
		final String[] targetParts = target.split("/");
		final int targetSize = targetParts.length;
		delete(Integer.parseInt(targetParts[targetSize - 1]));
	}

	/**
	 * Get all rows
	 *
	 * @return
	 * @throws Exception
	 */
	public List<SexTypes> get() throws Exception {
		return sexType.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void get(int key) throws Exception {
		sexType.get(key);
		setSexTypePid(sexType.getSexTypePid());
		setAbbreviation(sexType.getAbbreviation());

//		final List<Dictionary> fkIsoCode = dictionary
//				.getFKIsoCode(store.getString("GUILANGUAGE"));
//		String label = "";
//
//		for (final Dictionary dict : fkIsoCode) {
//			if (key == dict.getLabelPid()) {
//				label = dict.getLabel();
//			}
//		}
//		setLabel(label);
	}

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the labelPid
	 */
	public int getLabelPid() {
		return sexType.getLabelPid();
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
	 * @throws Exception             An exception that provides information on a
	 *                               database access error or other errors
	 * @throws MvpException          Application specific exception
	 * @throws IOException           IOException
	 * @throws JSONException         JSONException
	 */
	public String getRemote(HttpServletResponse response, String target)
			throws NumberFormatException, Exception, IOException,
			JSONException {
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
//		js.value(isoCode);
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
	 * @return stringList A list of lists of pids, abbreviations and generic
	 *         labels
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		List<String> stringList;
		List<Dictionary> fkLabelPid;
		String label = "";

		final IPreferenceStore store = new ScopedPreferenceStore(
				InstanceScope.INSTANCE, "net.myerichsen.hremvp");
		final String guiLanguage = store.getString("GUILANGUAGE");
		final List<List<String>> lls = new ArrayList<>();
		final Dictionary dictionary = new Dictionary();

		final List<SexTypes> list = sexType.get();

		for (final SexTypes st : list) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(st.getSexTypePid()));
			stringList.add(Integer.toString(st.getLabelPid()));
			stringList.add(st.getAbbreviation());

			fkLabelPid = dictionary.getFKLabelPid(st.getLabelPid());

			for (final Dictionary d : fkLabelPid) {
				if (guiLanguage.equals(d.getIsoCode())) {
					label = d.getLabel();
				}
			}

			stringList.add(label);
			lls.add(stringList);
		}
		return lls;
	}

	/**
	 * @param labelPid
	 * @param abbreviation
	 * @return stringList A list of lists of sex type pids, label pids, iso
	 *         codes and generic labels
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList(int labelPid) throws Exception {
		final List<List<String>> lls = new ArrayList<>();

		if (labelPid == 0) {
			return lls;
		}

		String sexTypePidString = "1";
		final List<SexTypes> list = sexType.get();

		for (final SexTypes sexTypes : list) {
			if (sexTypes.getLabelPid() == labelPid) {
				sexTypePidString = Integer.toString(sexTypes.getSexTypePid());
			}
		}

		List<String> stringList;
		String label = "";

		final Dictionary dictionary = new Dictionary();
		final List<Dictionary> fkLabelPid = dictionary.getFKLabelPid(labelPid);

		final Languages language = new Languages();

		for (final Languages l : language.get()) {
			stringList = new ArrayList<>();
			stringList.add(sexTypePidString);
			stringList.add(Integer.toString(labelPid));
			stringList.add(l.getIsocode());

			for (final Dictionary d : fkLabelPid) {
				if (l.getIsocode().equals(d.getIsoCode())) {
					label = d.getLabel();
					break;
				}
			}

			stringList.add(label);
			lls.add(stringList);
		}
		return lls;
	}

	/**
	 * Insert a row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	@Override
	public int insert() throws Exception {
		sexType.setAbbreviation(abbreviation);
		final Dictionary dictionary = new Dictionary();
		sexType.setLabelPid(dictionary.getNextLabelPid());
		sexType.setTableId(TableId);
		return sexType.insert();
	}

	/**
	 * Insert a row remotely
	 *
	 * @param request HttpServletRequest
	 * @throws IOException   IOException
	 * @throws JSONException JSONException
	 * @throws Exception     Exception
	 */
	public void insertRemote(HttpServletRequest request)
			throws IOException, JSONException, Exception {
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
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws Exception {
		sexType.setSexTypePid(sexTypePid);
		sexType.setAbbreviation(abbreviation);
		sexType.setTableId(TableId);
		sexType.update();
	}

	/**
	 * Update a row
	 *
	 * @param request HttpServletRequest
	 * @throws IOException   IOException
	 * @throws JSONException JSONException
	 * @throws Exception     An exception that provides information on a
	 *                       database access error or other errors
	 * @throws MvpException  Application specific exception
	 */
	public void updateRemote(HttpServletRequest request)
			throws IOException, JSONException, Exception {
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
