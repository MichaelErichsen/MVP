package net.myerichsen.hremvp.serverlogic;

import java.sql.SQLException;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.dbmodels.LocationNameStyles;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationNameMaps}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 18. sep. 2018
 *
 */
public class LocationNameMapServer {
	// private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int locationNameMapPid;
	private String label;
	private int partNo;
	private int locationNameStylePid;
	private String styleLabel;
	private String labelPosition;

	private final LocationNameMaps map;
	private final LocationNameStyles style;

	/**
	 * Constructor
	 *
	 */
	public LocationNameMapServer() {
		map = new LocationNameMaps();
		style = new LocationNameStyles();
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
		map.delete(key);
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void get(int key) throws SQLException, MvpException {
		map.get(key);
		setLabel(map.getLabel());
		setLocationNameMapPid(map.getLocationNameMapPid());
		setLocationNameStylePid(map.getLocationNameStylePid());
		setPartNo(map.getPartNo());
		setLabelPosition(map.getLabelPosition());

		style.get(getLocationNameStylePid());
		setStyleLabel(style.getLabel());
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the labelPosition
	 */
	public String getLabelPosition() {
		return labelPosition;
	}

	/**
	 * @return the locationNameMapPid
	 */
	public int getLocationNameMapPid() {
		return locationNameMapPid;
	}

	/**
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return locationNameStylePid;
	}

	/**
	 * @return the partNo
	 */
	public int getPartNo() {
		return partNo;
	}

	/**
	 * @return the styleLabel
	 */
	public String getStyleLabel() {
		return styleLabel;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 *
	 */
	public void insert() throws SQLException {
		map.setLabel(label);
		map.setLocationNameMapPid(locationNameMapPid);
		map.setLocationNameStylePid(locationNameStylePid);
		map.setPartNo(partNo);
		map.setLabelPosition(labelPosition);
		map.insert();
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param labelPosition the labelPosition to set
	 */
	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}

	/**
	 * @param locationNameMapPid the locationNameMapPid to set
	 */
	public void setLocationNameMapPid(int locationNameMapPid) {
		this.locationNameMapPid = locationNameMapPid;
	}

	/**
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		this.locationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param partNo the partNo to set
	 */
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}

	/**
	 * @param styleLabel the styleLabel to set
	 */
	public void setStyleLabel(String styleLabel) {
		this.styleLabel = styleLabel;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 *
	 */
	public void update() throws SQLException {
		map.setLabel(label);
		map.setLocationNameMapPid(locationNameMapPid);
		map.setLocationNameStylePid(locationNameStylePid);
		map.setPartNo(partNo);
		map.setLabelPosition(labelPosition);
		map.update();
	}

}
