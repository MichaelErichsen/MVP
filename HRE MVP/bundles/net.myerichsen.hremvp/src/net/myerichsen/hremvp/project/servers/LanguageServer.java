package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Languages;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Languages}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. mar. 2019
 *
 */
public class LanguageServer implements IHREServer {
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
	 * @param key
	 * @throws MvpException
	 * @throws SQLException
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		language.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return A list of lists of Pids, ISO Codes and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public List<List<String>> get() throws SQLException, MvpException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		final List<Languages> languageList = language.get();

		for (final Languages aLanguage : languageList) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(aLanguage.getLanguagePid()));
			stringList.add(aLanguage.getIsocode());
			stringList.add(aLanguage.getLabel());
			lls.add(stringList);
		}

		return lls;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		language.get();
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
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> getStringList() throws SQLException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;
		final List<Languages> list = language.get();

		for (final Languages l : list) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(l.getLanguagePid()));
			stringList.add(l.getIsocode());
			stringList.add(l.getLabel());
			lls.add(stringList);
		}

		return lls;
	}

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return TableId;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int insert() throws SQLException {
		language.setIsocode(Isocode);
		language.setLabel(Label);
		language.setTableId(7);
		return language.insert();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		language.setLanguagePid(LanguagePid);
		language.setIsocode(Isocode);
		language.setLabel(Label);
		language.update();

	}

}
