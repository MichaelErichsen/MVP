package net.myerichsen.hremvp.serverlogic;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.dbmodels.LocationNameParts;
import net.myerichsen.hremvp.dbmodels.LocationNames;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationNameParts}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 30. okt. 2018
 */
public class LocationNamePartServer {
	// private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int locationNamePartPid;
	private int locationNamePid;
	private String locationName;
	private String mapLabel;
	private String label;
	private int partNo;

	private LocationNameParts part;

	/**
	 * Constructor
	 *
	 */
	public LocationNamePartServer() {
		part = new LocationNameParts();
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
		part.delete(key);
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
		part.get(key);
		setLabel(part.getLabel());
		setLocationNamePartPid(part.getLocationNamePartPid());
		setLocationNamePid(part.getLocationNamePid());
		setPartNo(part.getPartNo());

		// Get location name
		StringBuilder sb = new StringBuilder();
		List<LocationNameParts> partList = new LocationNameParts().getFKLocationNamePid(locationNamePid);

		for (LocationNameParts locationNameParts : partList) {
			if (locationNameParts.getLocationNamePid() == locationNamePid) {
				sb.append(locationNameParts.getLabel() + " ");
			}
		}

		setLocationName(sb.toString().trim());

		// Get map label
		setMapLabel("Label");
		LocationNames name = new LocationNames();
		name.get(locationNamePid);

		LocationNameMaps map = new LocationNameMaps();
		List<LocationNameMaps> mapList = map.getFKLocationNameStylePid(name.getLocationNameStylePid());

		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.get(i).getPartNo() == partNo) {
				setMapLabel(mapList.get(i).getLabel());
				break;
			}
		}
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
	 * Insert a row. Checks if a matching part number exists in
	 * {@link net.myerichsen.hremvp.dbmodels.LocationNameMaps}
	 * 
	 * @return int Persistent ID of the inserted row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public int insert() throws SQLException, MvpException {
		part.setLabel(label);
		part.setLocationNamePartPid(locationNamePartPid);
		part.setLocationNamePid(locationNamePid);
		part.setPartNo(partNo);

		// Check if matching map part no exists
		LocationNames name = new LocationNames();
		name.get(locationNamePid);

		LocationNameMaps map = new LocationNameMaps();
		List<LocationNameMaps> mapList = map.getFKLocationNameStylePid(name.getLocationNameStylePid());
		Boolean found = false;

		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.get(i).getPartNo() == partNo) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new MvpException("Part number " + partNo + " does not exist in matching location name map");
		}

		return part.insert();
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
	 * Update a row. Checks if a matching part number exists in
	 * {@link net.myerichsen.hremvp.dbmodels.LocationNameMaps}
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		part.setLabel(label);
		part.setLocationNamePartPid(locationNamePartPid);
		part.setLocationNamePid(locationNamePid);
		part.setPartNo(partNo);

		// Check if matching map part no exists
		LocationNames name = new LocationNames();
		name.get(locationNamePid);

		LocationNameMaps map = new LocationNameMaps();
		List<LocationNameMaps> mapList = map.getFKLocationNameStylePid(name.getLocationNameStylePid());
		Boolean found = false;

		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.get(i).getPartNo() == partNo) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new MvpException("Part number " + partNo + " does not exist in matching location name map");
		}
		part.update();
	}
}