package net.myerichsen.gen.mvp.providers;

import java.sql.SQLException;

import net.myerichsen.gen.mvp.MvpException;
import net.myerichsen.gen.mvp.serverlogic.LocationNameMapServer;

/**
 * Provides all data for a single map view part
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 18. sep. 2018
 *
 */
public class LocationNameMapProvider {
	// private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int locationNameMapPid;
	private String label;
	private int partNo;
	private int locationNameStylePid;
	private String styleLabel;
	private String labelPosition;

	private LocationNameMapServer server;

	/**
	 * Constructor
	 *
	 */
	public LocationNameMapProvider() {
		server = new LocationNameMapServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
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
	 *                      access error or other errors.
	 * @throws MvpException Application specific exception
	 */
	public void get(int key) throws SQLException, MvpException {
		server.get(key);

		setLabel(server.getLabel());
		setLocationNameMapPid(server.getLocationNameMapPid());
		setLocationNameStylePid(server.getLocationNameStylePid());
		setPartNo(server.getPartNo());
		setStyleLabel(server.getStyleLabel());
		setLabelPosition(server.getLabelPosition());
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
		server.setLabel(label);
		server.setLocationNameMapPid(locationNameMapPid);
		server.setLocationNameStylePid(locationNameStylePid);
		server.setPartNo(partNo);
		server.setLabelPosition(labelPosition);
		server.insert();
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
		server.setLabel(label);
		server.setLocationNameMapPid(locationNameMapPid);
		server.setLocationNameStylePid(locationNameStylePid);
		server.setPartNo(partNo);
		server.setLabelPosition(labelPosition);
		server.update();
	}

}
