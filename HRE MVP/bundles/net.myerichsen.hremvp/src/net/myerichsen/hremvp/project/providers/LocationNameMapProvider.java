package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.LocationNameMapServer;

/**
 * Provides all data for a single map view part
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 2. mar. 2019
 *
 */
public class LocationNameMapProvider implements IHREProvider {
//	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int LocationNameMapPid;
	private int LocationNameStylePid;
	private int PartNo;
	private int LabelPid;

	private final LocationNameMapServer server;

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
	@Override
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 * @throws MvpException
	 */
	public void deleteLocationNameStylePid(int key)
			throws SQLException, MvpException {
		server.deleteLocationNameStylePid(key);
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
	 *                      access error or other errors.
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setLocationNameMapPid(server.getLocationNameMapPid());
		setLocationNameStylePid(server.getLocationNameStylePid());
		setPartNo(server.getPartNo());
		setLabelPid(server.getLabelPid());
	}

	/**
	 * @return the labelPid
	 */
	public int getLabelPid() {
		return LabelPid;
	}

	/**
	 * @return the locationNameMapPid
	 */
	public int getLocationNameMapPid() {
		return LocationNameMapPid;
	}

	/**
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return LocationNameStylePid;
	}

	/**
	 * @return the partNo
	 */
	public int getPartNo() {
		return PartNo;
	}

	/**
	 * @param locationNameStylePid
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<List<String>> getStringList(int locationNameStylePid)
			throws SQLException {
		return server.getStringList(locationNameStylePid);
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 *
	 */
	@Override
	public int insert() throws SQLException {
		server.setLocationNameMapPid(LocationNameMapPid);
		server.setLocationNameStylePid(LocationNameStylePid);
		server.setPartNo(PartNo);
		server.setLabelPid(LabelPid);
		return server.insert();
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
		LabelPid = labelPid;
	}

	/**
	 * @param locationNameMapPid the locationNameMapPid to set
	 */
	public void setLocationNameMapPid(int locationNameMapPid) {
		LocationNameMapPid = locationNameMapPid;
	}

	/**
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		LocationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param partNo the partNo to set
	 */
	public void setPartNo(int partNo) {
		PartNo = partNo;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 *
	 */
	@Override
	public void update() throws SQLException {
		server.setLocationNameMapPid(LocationNameMapPid);
		server.setLocationNameStylePid(LocationNameStylePid);
		server.setPartNo(PartNo);
		server.setLabelPid(LabelPid);
		server.update();
	}

}
