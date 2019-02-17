package net.myerichsen.hremvp.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Languages;

/**
 * * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.Languages}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 7. nov. 2018
 *
 */
public class LanguageServer {
	private int LanguagePid;
	private String Isocode;
	private String Label;
	private int TableId;
	private final Languages language;

	/**
	 * Constructor
	 *
	 */
	public LanguageServer() {
		language = new Languages();
	}

	/**
	 * Get all rows
	 *
	 * @return A list of Pids, ISO Codes and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<String> get() throws SQLException, MvpException {
		final List<String> stringList = new ArrayList<>();

		final List<Languages> languageList = language.get();

		for (final Languages aLanguage : languageList) {
			stringList.add(aLanguage.getLanguagePid() + ","
					+ aLanguage.getIsocode() + "," + aLanguage.getLabel());
		}

		return stringList;
	}

	/**
	 * @return the isocode
	 */
	public String getIsocode() {
		return Isocode;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return LanguagePid;
	}

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return TableId;
	}

	/**
	 * @param isocode the isocode to set
	 */
	public void setIsocode(String isocode) {
		Isocode = isocode;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		Label = label;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		LanguagePid = languagePid;
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(int tableId) {
		TableId = tableId;
	}

}
