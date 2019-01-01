package net.myerichsen.gen.mvp.serverlogic;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.myerichsen.gen.mvp.MvpException;
import net.myerichsen.gen.mvp.dbmodels.LocationNameParts;
import net.myerichsen.gen.mvp.dbmodels.LocationNames;
import net.myerichsen.gen.mvp.dbmodels.Locations;

/**
 * Business logic interface for
 * {@link net.myerichsen.gen.mvp.dbmodels.Locations}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 25. nov. 2018
 *
 */
public class LocationServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int locationPid;
	private int fromDatePid;
	private int toDatePid;
	private boolean primaryLocation;
	private BigDecimal xCoordinate;
	private BigDecimal yCoordinate;
	private BigDecimal zCoordinate;
	private List<List<String>> nameList;

	private Locations location;

	/**
	 * Constructor
	 *
	 */
	public LocationServer() {
		location = new Locations();
		nameList = new ArrayList<>();
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
		location.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return A list of lists of strings of pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<List<String>> get() throws SQLException, MvpException {
		List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		List<Locations> lnsl = location.get();

		StringBuilder sb;
		LocationNameParts part = new LocationNameParts();
		List<LocationNameParts> partList;
		int lp;

		for (Locations location : lnsl) {
			stringList = new ArrayList<String>();
			sb = new StringBuilder();
			lp = location.getLocationPid();
			stringList.add(Integer.toString(lp));

			List<LocationNames> lnl = new LocationNames().getFKLocationPid(lp);

			for (LocationNames name : lnl) {
				if (name.isPrimaryLocationName()) {
					partList = part.getFKLocationNamePid(name.getLocationNamePid());

					for (int i = 0; i < partList.size(); i++) {
						if ((partList.get(i) != null) && (partList.get(i).getLabel().trim().length() > 0)) {
							sb.append(partList.get(i).getLabel() + ", ");
						}
					}
					break;
				}
			}

			LOGGER.fine(sb.toString());
			stringList.add(sb.toString());
			lls.add(stringList);
		}

		return lls;
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
		location.get(key);
		setFromDatePid(location.getFromDatePid());
		setLocationPid(key);
		setPrimaryLocation(location.isPrimaryLocation());
		setToDatePid(location.getToDatePid());
		setxCoordinate(location.getXCoordinate());
		setyCoordinate(location.getYCoordinate());
		setzCoordinate(location.getZCoordinate());

		nameList = new ArrayList<>();

		List<LocationNames> lnl = new LocationNames().getFKLocationPid(key);

		LocationNameServer lns = new LocationNameServer();
		List<String> sl;
		int pid;

		for (int i = 0; i < lnl.size(); i++) {
			sl = new ArrayList<>();
			pid = lnl.get(i).getLocationNamePid();
			sl.add(Integer.toString(pid));
			lns.get(pid);
			sl.add(lns.getNameStrings()[i]);
			sl.add(Boolean.toString(lns.isPrimaryLocationName()));
			sl.add(Integer.toString(lns.getToDatePid()));
			sl.add(Integer.toString(lns.getFromDatePid()));
			nameList.add(sl);
		}
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the locationPid
	 */
	public int getLocationPid() {
		return locationPid;
	}

	/**
	 * @return the nameList
	 */
	public List<List<String>> getNameList() {
		return nameList;
	}

	/**
	 * @return the primary name of the location
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String getPrimaryName() throws SQLException {
		StringBuilder sb = new StringBuilder();
		LocationNameParts part = new LocationNameParts();
		List<LocationNameParts> partList;
		List<LocationNames> lnl = new LocationNames().getFKLocationPid(locationPid);

		for (LocationNames name : lnl) {
			if (name.isPrimaryLocationName()) {
				partList = part.getFKLocationNamePid(name.getLocationNamePid());

				for (int i = 0; i < partList.size(); i++) {
					if (partList.get(i) != null) {
						sb.append(partList.get(i).getLabel() + ", ");
					}
				}
				break;
			}
		}
		String primaryName = sb.toString();
		return primaryName;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * @return the xCoordinate
	 */
	public BigDecimal getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public BigDecimal getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * @return the zCoordinate
	 */
	public BigDecimal getzCoordinate() {
		return zCoordinate;
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
	public int insert() throws SQLException, MvpException {
		location.setFromDatePid(fromDatePid);
		location.setLocationPid(locationPid);
		location.setPrimaryLocation(primaryLocation);
		location.setToDatePid(toDatePid);
		location.setXCoordinate(xCoordinate);
		location.setYCoordinate(yCoordinate);
		location.setZCoordinate(zCoordinate);
		return location.insert();
	}

	/**
	 * @return the primaryLocation
	 */
	public boolean isPrimaryLocation() {
		return primaryLocation;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param locationPid the locationPid to set
	 */
	public void setLocationPid(int locationPid) {
		this.locationPid = locationPid;
	}

	/**
	 * @param primaryLocation the primaryLocation to set
	 */
	public void setPrimaryLocation(boolean primaryLocation) {
		this.primaryLocation = primaryLocation;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}

	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(BigDecimal xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(BigDecimal yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * @param zCoordinate the zCoordinate to set
	 */
	public void setzCoordinate(BigDecimal zCoordinate) {
		this.zCoordinate = zCoordinate;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		location.setFromDatePid(fromDatePid);
		location.setLocationPid(locationPid);
		location.setPrimaryLocation(primaryLocation);
		location.setToDatePid(toDatePid);
		location.setXCoordinate(xCoordinate);
		location.setYCoordinate(yCoordinate);
		location.setZCoordinate(zCoordinate);
		location.update();
	}
}
