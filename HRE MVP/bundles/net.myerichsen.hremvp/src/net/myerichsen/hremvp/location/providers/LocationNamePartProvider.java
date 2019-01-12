package net.myerichsen.hremvp.location.providers;

import java.sql.SQLException;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.location.servers.LocationNamePartServer;

/**
 * Provides all data for a single part view part
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 31. okt. 2018
 *
 */
public class LocationNamePartProvider {
	// private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int locationNamePartPid;
	private int locationNamePid;
	private String locationName;
	private String mapLabel;
	private String label;
	private int partNo;

	private final LocationNamePartServer server;

	/**
	 * Constructor
	 *
	 */
	public LocationNamePartProvider() {
		server = new LocationNamePartServer();
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
		setLocationNamePartPid(server.getLocationNamePartPid());
		setLocationNamePid(server.getLocationNamePid());
		setMapLabel(server.getMapLabel());
		setLocationName(server.getLocationName());
		setPartNo(server.getPartNo());
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @return the locationNamePartPid
	 */
	public int getLocationNamePartPid() {
		return locationNamePartPid;
	}

	/**
	 * @return the locationNamePid
	 */
	public int getLocationNamePid() {
		return locationNamePid;
	}

	/**
	 * @return the mapLabel
	 */
	public String getMapLabel() {
		return mapLabel;
	}

	/**
	 * @return the partNo
	 */
	public int getPartNo() {
		return partNo;
	}

	/**
	 * Insert a row
	 *
	 * @return int The persistent ID of the inserted row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	public int insert() throws SQLException, MvpException {
		server.setLabel(label);
		server.setLocationName(locationName);
		server.setLocationNamePartPid(locationNamePartPid);
		server.setLocationNamePid(locationNamePid);
		server.setMapLabel(mapLabel);
		server.setPartNo(partNo);
		return server.insert();
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @param locationNamePartPid the locationNamePartPid to set
	 */
	public void setLocationNamePartPid(int locationNamePartPid) {
		this.locationNamePartPid = locationNamePartPid;
	}

	/**
	 * @param locationNamePid the locationNamePid to set
	 */
	public void setLocationNamePid(int locationNamePid) {
		this.locationNamePid = locationNamePid;
	}

	/**
	 * @param mapLabel the mapLabel to set
	 */
	public void setMapLabel(String mapLabel) {
		this.mapLabel = mapLabel;
	}

	/**
	 * @param partNo the partNo to set
	 */
	public void setPartNo(int partNo) {
		this.partNo = partNo;
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
		server.setLabel(label);
		server.setLocationName(locationName);
		server.setLocationNamePartPid(locationNamePartPid);
		server.setLocationNamePid(locationNamePid);
		server.setMapLabel(mapLabel);
		server.setPartNo(partNo);
		server.update();
	}
}
