package net.myerichsen.hremvp.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.serverlogic.LocationNameStyleServer;

/**
 * Provide a location name style
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 20. nov. 2018
 *
 */
public class LocationNameStyleProvider {
	private int locationNameStylePid;
	private String label;
	private int languagePid;
	private String languageLabel;
	private String isoCode;
	private int fromDatePid;
	private int toDatePid;

	private List<LocationNameMaps> mapList;
	private final LocationNameStyleServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationNameStyleProvider() throws SQLException {
		server = new LocationNameStyleServer();
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
	 * Get all rows
	 *
	 * @return A list of strings with pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<String> get() throws SQLException, MvpException {
		return server.get();
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
		setLocationNameStylePid(server.getLocationNameStylePid());
		setIsoCode(server.getIsoCode());
		setLanguageLabel(server.getLanguageLabel());
		setFromDatePid(server.getFromDate());
		setToDatePid(server.getToDatePid());
		mapList = new LocationNameMaps().getFKLocationNameStylePid(key);
	}

	/**
	 * @return the fromDate
	 */
	public int getFromDatePid() {
		return fromDatePid;
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
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return locationNameStylePid;
	}

	/**
	 * @return the mapList
	 */
	public List<LocationNameMaps> getMapList() {
		return mapList;
	}

	/**
	 * @return the toDate
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	public int insert() throws SQLException, MvpException {
		server.setIsoCode(isoCode);
		server.setLabel(label);
		server.setLanguageLabel(languageLabel);
		server.setLanguagePid(languagePid);
		server.setLocationNameStylePid(locationNameStylePid);
		server.setMapList(mapList);
		server.setFromDatePid(fromDatePid);
		server.setToDatePid(toDatePid);
		return server.insert();
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDatePid(int fromDate) {
		fromDatePid = fromDate;
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
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		this.locationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param mapList the mapList to set
	 */
	public void setMapList(List<LocationNameMaps> mapList) {
		this.mapList = mapList;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
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
		server.setLocationNameStylePid(locationNameStylePid);
		server.setMapList(mapList);
		server.setFromDatePid(fromDatePid);
		server.setToDatePid(toDatePid);
		server.update();
	}
}
