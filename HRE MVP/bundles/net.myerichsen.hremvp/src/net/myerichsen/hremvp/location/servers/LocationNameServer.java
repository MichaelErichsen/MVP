package net.myerichsen.hremvp.location.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.dbmodels.LocationNameParts;
import net.myerichsen.hremvp.dbmodels.LocationNameStyles;
import net.myerichsen.hremvp.dbmodels.LocationNames;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationNames}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 2. mar. 2019
 *
 */
public class LocationNameServer implements IHREServer {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
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

	private final LocationNames name;
	private final LocationNameStyles style;

	/**
	 * Constructor
	 *
	 */
	public LocationNameServer() {
		name = new LocationNames();
		style = new LocationNameStyles();
		mapList = new ArrayList<>();
		partList = new ArrayList<>();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void delete(int key) throws Exception {
		name.delete(key);
	}


	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void get(int key) throws Exception {
		name.get(key);
		setFromDatePid1(name.getFromDatePid());
		setLocationNamePid(key);
		setLocationNameStylePid(name.getLocationNameStylePid());
		setPrimaryLocationName(name.isPrimaryLocationName());
		setLocationPid(name.getLocationPid());
		setToDatePid(name.getToDatePid());
		setPreposition(name.getPreposition());

		style.get(locationNameStylePid);
		setLocationNameStyleLabel("style.getLabelPid()");

		LOGGER.fine("Location PID: " + name.getLocationPid());
		LOGGER.fine("Location name PID: " + key);
		LOGGER.fine(
				"Location name style PID: " + name.getLocationNameStylePid());
//		mapList = new LocationNameMaps()
//				.getFKLocationNameStylePid(name.getLocationNameStylePid());
//		partList = new LocationNameParts().getFKLocationNamePid(key);
//
//		if (mapList.size() != partList.size()) {
//			throw new MvpException("Map list size: " + mapList.size()
//					+ ", part list size: " + partList.size());
//		}
	}

	/**
	 * @return the fromDate
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
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String[] getNameStrings() throws Exception {
		StringBuilder sb;
		LocationNames aName;

		final int locationPid = name.getLocationPid();
		List<LocationNames> nameList = new ArrayList<>();
		nameList = new LocationNames().getFKLocationPid(locationPid);

		final String[] sa = new String[nameList.size()];

		for (int i = 0; i < nameList.size(); i++) {
			sb = new StringBuilder();
			aName = nameList.get(i);
			final List<LocationNameParts> npl = new LocationNameParts()
					.getFKLocationNamePid(aName.getLocationNamePid());

			// Concatenate non-null name parts
			for (final LocationNameParts PersonNameParts : npl) {
				if (PersonNameParts.getLocationNamePid() == aName
						.getLocationNamePid()) {
					if (PersonNameParts.getLabel() != null) {
						sb.append(PersonNameParts.getLabel() + " ");
					}
				}
			}
			sa[i] = sb.toString().trim();
		}

		return sa;
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
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String getPrimaryNameString(int locationPid) throws Exception {
		final StringBuilder sb = new StringBuilder();
		LocationNames aName;

		List<LocationNames> nameList = new ArrayList<>();
		nameList = new LocationNames().getFKLocationPid(locationPid);

		for (int i = 0; i < nameList.size(); i++) {
			aName = nameList.get(i);

			if (aName.isPrimaryLocationName()) {
				final List<LocationNameParts> lnp = new LocationNameParts()
						.getFKLocationNamePid(aName.getLocationNamePid());

				// Concatenate non-null name parts
				for (final LocationNameParts PersonNameParts : lnp) {
					if (PersonNameParts.getLocationNamePid() == aName
							.getLocationNamePid()) {
						if ((PersonNameParts.getLabel() != null)
								&& !(PersonNameParts.getLabel().equals(""))) {
							sb.append(PersonNameParts.getLabel() + " ");
						}
					}
				}
				break;
			}
		}

		final String s = sb.toString().trim();
		return s;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	 * @return int The persistent ID of the inserted row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws Exception {
		name.setFromDatePid(fromDatePid);
		name.setLocationNamePid(locationNamePid);
		name.setLocationNameStylePid(locationNameStylePid);
		name.setLocationPid(locationPid);
		name.setPrimaryLocationName(primaryLocationName);
		name.setToDatePid(toDatePid);
		name.setPreposition(preposition);
		return name.insert();
	}

	/**
	 * @return the primaryLocationName
	 */
	public boolean isPrimaryLocationName() {
		return primaryLocationName;
	}

	/**
	 * @param fromDatePid
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param fromDatePid the fromDate to set
	 */
	public void setFromDatePid1(int fromDatePid) {
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
	 * @param toDatePid the toDate to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}

	/**
	 * Update a row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws Exception {
		name.setFromDatePid(fromDatePid);
		name.setLocationNamePid(locationNamePid);
		name.setLocationNameStylePid(locationNameStylePid);
		name.setLocationPid(locationPid);
		name.setPrimaryLocationName(primaryLocationName);
		name.setToDatePid(toDatePid);
		name.setPreposition(preposition);
		name.update();
	}

	/**
	 * @return
	 */
	public List<?> get() {
		// TODO Auto-generated method stub
		return null;
	}

}
