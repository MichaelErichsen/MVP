package net.myerichsen.hremvp.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.NameMaps;
import net.myerichsen.hremvp.serverlogic.NameStyleServer;

/**
 * Provide a name style
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 29. sep. 2018
 *
 */
public class NameStyleProvider {
	private int nameStylePid;
	private String label;
	private int languagePid;
	private String languageLabel;
	private String isoCode;

	private List<NameMaps> mapList;
	private NameStyleServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public NameStyleProvider() throws SQLException {
		server = new NameStyleServer();
		mapList = new ArrayList<>();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void get(int key) throws SQLException, MvpException {
		server.get(key);

		setLabel(server.getLabel());
		setLanguagePid(server.getLanguagePid());
		setNameStylePid(server.getNameStylePid());
		setIsoCode(server.getIsoCode());
		setLanguageLabel(server.getLanguageLabel());
		setMapList(server.getMapList());
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
	 * @return the mapList
	 */
	public List<NameMaps> getMapList() {
		return mapList;
	}

	/**
	 * @return the nameStylePid
	 */
	public int getNameStylePid() {
		return nameStylePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	public void insert() throws SQLException, MvpException {
		server.setIsoCode(isoCode);
		server.setLabel(label);
		server.setLanguageLabel(languageLabel);
		server.setLanguagePid(languagePid);
		server.setNameStylePid(nameStylePid);
		server.setMapList(mapList);
		server.insert();
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
	 * @param mapList the mapList to set
	 */
	public void setMapList(List<NameMaps> mapList) {
		this.mapList = mapList;
	}

	/**
	 * @param nameStylePid the nameStylePid to set
	 */
	public void setNameStylePid(int nameStylePid) {
		this.nameStylePid = nameStylePid;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	public void update() throws SQLException, MvpException {
		server.setIsoCode(isoCode);
		server.setLabel(label);
		server.setLanguageLabel(languageLabel);
		server.setLanguagePid(languagePid);
		server.setNameStylePid(nameStylePid);
		server.setMapList(mapList);
		server.update();
	}
}