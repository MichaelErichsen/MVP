package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationNameMaps}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 2. mar. 2019
 *
 */
public class LocationNameMapServer implements IHREServer {
//	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int LocationNameMapPid;
	private int LocationNameStylePid;
	private int PartNo;
	private int LabelPid;
	private String styleLabel;

	private final LocationNameMaps map;

	/**
	 * Constructor
	 *
	 */
	public LocationNameMapServer() {
		map = new LocationNameMaps();
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
		map.delete(key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 * @throws MvpException
	 */
	public void deleteLocationNameStylePid(int key)
			throws SQLException, MvpException {
		final List<LocationNameMaps> fkLocationNameStylePid = map
				.getFKLocationNameStylePid(key);

		for (final LocationNameMaps locationNameMaps : fkLocationNameStylePid) {
			delete(locationNameMaps.getLocationNameMapPid());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		map.get(key);
		setLocationNameMapPid(map.getLocationNameMapPid());
		setLocationNameStylePid(map.getLocationNameStylePid());
		setPartNo(map.getPartNo());
		setLabelPid(map.getLabelPid());
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
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		if (locationNameStylePid > 0) {
			final List<LocationNameMaps> fkNameStylePid = map
					.getFKLocationNameStylePid(locationNameStylePid);

			for (final LocationNameMaps personNameMaps : fkNameStylePid) {
				stringList = new ArrayList<>();
				stringList.add(Integer
						.toString(personNameMaps.getLocationNameMapPid()));
				final int labelPid = personNameMaps.getLabelPid();
				stringList.add(Integer.toString(labelPid));
				stringList.add(Integer.toString(personNameMaps.getPartNo()));

				final Dictionary dictionary = new Dictionary();
				final List<Dictionary> fkLabelPid = dictionary
						.getFKLabelPid(labelPid);
				stringList.add(fkLabelPid.get(0).getLabel());
				stringList.add(
						Integer.toString(fkLabelPid.get(0).getDictionaryPid()));
				lls.add(stringList);
			}
		}

		return lls;
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
	@Override
	public int insert() throws SQLException {
		map.setLocationNameMapPid(LocationNameMapPid);
		map.setLocationNameStylePid(LocationNameStylePid);
		map.setPartNo(PartNo);
		map.setLabelPid(LabelPid);
		return map.insert();
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
	@Override
	public void update() throws SQLException {
		map.setLocationNameMapPid(LocationNameMapPid);
		map.setLocationNameStylePid(LocationNameStylePid);
		map.setPartNo(PartNo);
		map.setLabelPid(LabelPid);
		map.update();
	}
}
