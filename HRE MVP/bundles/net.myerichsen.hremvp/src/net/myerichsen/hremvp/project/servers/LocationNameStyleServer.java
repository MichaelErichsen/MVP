package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.LocationNameStyles;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationNameStyles}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. mar. 2019
 *
 */
public class LocationNameStyleServer implements IHREServer {
	private int LocationNameStylePid;
	private String IsoCode;
	private int FromDatePid;
	private int ToDatePid;
	private int LabelPid;

	private final LocationNameStyles style;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationNameStyleServer() throws SQLException {
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
	@Override
	public void delete(int key) throws SQLException, MvpException {
		style.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	@Override
	public List<LocationNameStyles> get() throws SQLException {
		return style.get();
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
		style.get(key);
		setLocationNameStylePid(key);
		setIsoCode(style.getIsoCode());
		setFromDatePid(style.getFromDatePid());
		setToDatePid(style.getToDatePid());

	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return FromDatePid;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return IsoCode;
	}

	/**
	 * @return the labelPid
	 */
	public int getLabelPid() {
		return LabelPid;
	}

	/**
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return LocationNameStylePid;
	}

	/**
	 * @return lls A list of lists of pid, iso code,label pid and label
	 * @throws SQLException
	 */
	public List<List<String>> getStringList() throws SQLException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;
		final Dictionary dictionary = new Dictionary();

		final List<LocationNameStyles> list = get();

		for (final LocationNameStyles style : list) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(style.getLocationNameStylePid()));
			stringList.add(style.getIsoCode());
			LabelPid = style.getLabelPid();
			final List<Dictionary> fkLabelPid = dictionary
					.getFKLabelPid(LabelPid);
			stringList.add(fkLabelPid.get(0).getLabel());
			lls.add(stringList);
		}
		return lls;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return ToDatePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	@Override
	public int insert() throws SQLException {
		style.setLocationNameStylePid(LocationNameStylePid);
		style.setIsoCode(IsoCode);
		style.setLabelPid(LabelPid);
		style.setFromDatePid(FromDatePid);
		style.setToDatePid(ToDatePid);
		return style.insert();
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		FromDatePid = fromDatePid;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		IsoCode = isoCode;
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
		LabelPid = labelPid;
	}

	/**
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		LocationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		ToDatePid = toDatePid;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	@Override
	public void update() throws SQLException {
		style.setLocationNameStylePid(LocationNameStylePid);
		style.setIsoCode(IsoCode);
		style.setLabelPid(LabelPid);
		style.setFromDatePid(FromDatePid);
		style.setToDatePid(ToDatePid);
		style.update();
	}
}
