package net.myerichsen.hremvp.location.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.dbmodels.LocationNameParts;
import net.myerichsen.hremvp.location.servers.LocationNameServer;

/**
 * Provides all data for a single name for a location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. nov. 2018
 *
 */
public class LocationNameProvider implements IHREProvider {
	private int locationNamePid;
	private int locationPid;
	private int fromDatePid;
	private int toDatePid;
	private boolean primaryLocationName;
	private int locationNameStylePid;
	private String locationNameStyleLabel;
	private String preposition;
	private List<LocationNameMaps> mapList;
	private List<LocationNameParts> partList;

	private final LocationNameServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationNameProvider() throws SQLException {
		server = new LocationNameServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);

		setFromDatePid(server.getFromDatePid());
		setLocationNamePid(key);
		setLocationNameStylePid(server.getLocationNameStylePid());
		setLocationNameStyleLabel(server.getLocationNameStyleLabel());
		setPrimaryLocationName(server.isPrimaryLocationName());
		setLocationPid(server.getLocationPid());
		setToDatePid(server.getToDatePid());
		setPreposition(server.getPreposition());
		setMapList(server.getMapList());
		setPartList(server.getPartList());
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the locationNamePid
	 */
	public int getLocationNamePid() {
		return locationNamePid;
	}

	/**
	 * @return the locationNameStyleLabel
	 */
	public String getLocationNameStyleLabel() {
		return locationNameStyleLabel;
	}

	/**
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return locationNameStylePid;
	}

	/**
	 * @return the locationPid
	 */
	public int getLocationPid() {
		return locationPid;
	}

	/**
	 * @return the mapList
	 */
	public List<LocationNameMaps> getMapList() {
		return mapList;
	}

	/**
	 * Get a string of name parts for each name
	 *
	 * @return sa An array of strings
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String[] getNameStrings() throws SQLException {
		return server.getNameStrings();
	}

	/**
	 * @return the partList
	 */
	public List<LocationNameParts> getPartList() {
		return partList;
	}

	/**
	 * @return the preposition
	 */
	public String getPreposition() {
		return preposition;
	}

	/**
	 * Get a string of name parts for the primary name of a location
	 *
	 * @param locationPid The persistent ID of the location
	 * @return s The primary name
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String getPrimaryNameString(int locationPid) throws SQLException {
		final String s = server.getPrimaryNameString(locationPid);
		return s;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * Insert a row
	 *
	 * @return int The persistent ID of the inserted row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		server.setFromDatePid(fromDatePid);
		server.setLocationNamePid(locationNamePid);
		server.setLocationNameStylePid(locationNameStylePid);
		server.setLocationPid(locationPid);
		server.setPrimaryLocationName(primaryLocationName);
		server.setToDatePid(toDatePid);
		server.setPreposition(preposition);
		return server.insert();
	}

	/**
	 * @return the primaryLocationName
	 */
	public boolean isPrimaryLocationName() {
		return primaryLocationName;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param locationNamePid the locationNamePid to set
	 */
	public void setLocationNamePid(int locationNamePid) {
		this.locationNamePid = locationNamePid;
	}

	/**
	 * @param locationNameStyleLabel the locationNameStyleLabel to set
	 */
	public void setLocationNameStyleLabel(String locationNameStyleLabel) {
		this.locationNameStyleLabel = locationNameStyleLabel;
	}

	/**
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		this.locationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param locationPid the locationPid to set
	 */
	public void setLocationPid(int locationPid) {
		this.locationPid = locationPid;
	}

	/**
	 * @param mapList the mapList to set
	 */
	public void setMapList(List<LocationNameMaps> mapList) {
		this.mapList = mapList;
	}

	/**
	 * @param partList the partList to set
	 */
	public void setPartList(List<LocationNameParts> partList) {
		this.partList = partList;
	}

	/**
	 * @param preposition the preposition to set
	 */
	public void setPreposition(String preposition) {
		this.preposition = preposition;
	}

	/**
	 * @param primaryLocationName the primaryLocationName to set
	 */
	public void setPrimaryLocationName(boolean primaryLocationName) {
		this.primaryLocationName = primaryLocationName;
	}

	/**
	 * @param toDatePid the toDatePid to set
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
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setFromDatePid(fromDatePid);
		server.setLocationNamePid(locationNamePid);
		server.setLocationNameStylePid(locationNameStylePid);
		server.setLocationPid(locationPid);
		server.setPrimaryLocationName(primaryLocationName);
		server.setToDatePid(toDatePid);
		server.setPreposition(preposition);
		server.update();
	}
}
