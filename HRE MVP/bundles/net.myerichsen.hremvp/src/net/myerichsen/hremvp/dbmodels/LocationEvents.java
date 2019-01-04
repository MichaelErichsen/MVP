package net.myerichsen.hremvp.dbmodels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.MvpException;

/**
 * The persistent class for the LOCATION_EVENTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. nov. 2018
 *
 */

public class LocationEvents {
	private static final String SELECT = "SELECT LOCATION_EVENTS_PID, EVENT_PID, LOCATION_PID, "
			+ "PRIMARY_EVENT, PRIMARY_LOCATION, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_EVENTS WHERE LOCATION_EVENTS_PID = ?";
	private static final String SELECT_EVENT_PID = "SELECT LOCATION_EVENTS_PID, EVENT_PID, "
			+ "LOCATION_PID, PRIMARY_EVENT, PRIMARY_LOCATION, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_EVENTS WHERE EVENT_PID = ? ORDER BY LOCATION_EVENTS_PID";
	private static final String SELECT_LOCATION_PID = "SELECT LOCATION_EVENTS_PID, EVENT_PID, "
			+ "LOCATION_PID, PRIMARY_EVENT, PRIMARY_LOCATION, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_EVENTS WHERE LOCATION_PID = ? ORDER BY LOCATION_EVENTS_PID";
	private static final String SELECTALL = "SELECT LOCATION_EVENTS_PID, EVENT_PID, LOCATION_PID, "
			+ "PRIMARY_EVENT, PRIMARY_LOCATION, TABLE_ID FROM PUBLIC.LOCATION_EVENTS ORDER BY LOCATION_EVENTS_PID";
	private static final String SELECTMAX = "SELECT MAX(LOCATION_EVENTS_PID) FROM PUBLIC.LOCATION_EVENTS";

	private static final String INSERT = "INSERT INTO PUBLIC.LOCATION_EVENTS( LOCATION_EVENTS_PID, "
			+ "EVENT_PID, LOCATION_PID, PRIMARY_EVENT, PRIMARY_LOCATION, TABLE_ID) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.LOCATION_EVENTS SET EVENT_PID = ?, LOCATION_PID = ?, "
			+ "PRIMARY_EVENT = ?, PRIMARY_LOCATION = ?, TABLE_ID = ? WHERE LOCATION_EVENTS_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.LOCATION_EVENTS WHERE LOCATION_EVENTS_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.LOCATION_EVENTS";

	private List<LocationEvents> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int LocationEventsPid;
	private int EventPid;
	private int LocationPid;
	private boolean PrimaryEvent;
	private boolean PrimaryLocation;
	private int TableId;
	private LocationEvents model;

	public void delete() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(DELETEALL);
		ps.executeUpdate();
		conn.close();
	}

	public void delete(int key) throws SQLException, MvpException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(DELETE);
		ps.setInt(1, key);
		if (ps.executeUpdate() == 0) {
			conn.close();
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<LocationEvents> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationEvents();
			model.setLocationEventsPid(rs.getInt("LOCATION_EVENTS_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setLocationPid(rs.getInt("LOCATION_PID"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setPrimaryLocation(rs.getBoolean("PRIMARY_LOCATION"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public void get(int key) throws SQLException, MvpException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		if (rs.next()) {
			setLocationEventsPid(rs.getInt("LOCATION_EVENTS_PID"));
			setEventPid(rs.getInt("EVENT_PID"));
			setLocationPid(rs.getInt("LOCATION_PID"));
			setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			setPrimaryLocation(rs.getBoolean("PRIMARY_LOCATION"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	/**
	 * Get the EventPid field.
	 *
	 * @return Contents of the EVENT_PID column
	 */
	public int getEventPid() {
		return EventPid;
	}

	public List<LocationEvents> getFKEventPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_EVENT_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationEvents();
			model.setLocationEventsPid(rs.getInt("LOCATION_EVENTS_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setLocationPid(rs.getInt("LOCATION_PID"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setPrimaryLocation(rs.getBoolean("PRIMARY_LOCATION"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<LocationEvents> getFKLocationPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LOCATION_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationEvents();
			model.setLocationEventsPid(rs.getInt("LOCATION_EVENTS_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setLocationPid(rs.getInt("LOCATION_PID"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setPrimaryLocation(rs.getBoolean("PRIMARY_LOCATION"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	/**
	 * Get the LocationEventsPid field.
	 *
	 * @return Contents of the LOCATION_EVENTS_PID column
	 */
	public int getLocationEventsPid() {
		return LocationEventsPid;
	}

	/**
	 * Get the LocationPid field.
	 *
	 * @return Contents of the LOCATION_PID column
	 */
	public int getLocationPid() {
		return LocationPid;
	}

	/**
	 * Get the TableId field.
	 *
	 * @return Contents of the TABLE_ID column
	 */
	public int getTableId() {
		return TableId;
	}

	public int insert() throws SQLException {
		int maxPid = 0;
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTMAX);
		rs = ps.executeQuery();
		if (rs.next()) {
			maxPid = rs.getInt(1);
		}
		maxPid++;

		ps = conn.prepareStatement(INSERT);
		ps.setInt(1, maxPid);
		ps.setInt(2, getEventPid());
		ps.setInt(3, getLocationPid());
		ps.setBoolean(4, isPrimaryEvent());
		ps.setBoolean(5, isPrimaryLocation());
		ps.setInt(6, getTableId());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	/**
	 * Get the PrimaryEvent field.
	 *
	 * @return Contents of the PRIMARY_EVENT column
	 */
	public boolean isPrimaryEvent() {
		return PrimaryEvent;
	}

	/**
	 * Get the PrimaryLocation field.
	 *
	 * @return Contents of the PRIMARY_LOCATION column
	 */
	public boolean isPrimaryLocation() {
		return PrimaryLocation;
	}

	/**
	 * Set the EventPid field
	 *
	 * @param EventPid Contents of the EVENT_PID column
	 */
	public void setEventPid(int EventPid) {
		this.EventPid = EventPid;
	}

	/**
	 * Set the LocationEventsPid field
	 *
	 * @param LocationEventsPid Contents of the LOCATION_EVENTS_PID column
	 */
	public void setLocationEventsPid(int LocationEventsPid) {
		this.LocationEventsPid = LocationEventsPid;
	}

	/**
	 * Set the LocationPid field
	 *
	 * @param LocationPid Contents of the LOCATION_PID column
	 */
	public void setLocationPid(int LocationPid) {
		this.LocationPid = LocationPid;
	}

	/**
	 * Set the PrimaryEvent field
	 *
	 * @param PrimaryEvent Contents of the PRIMARY_EVENT column
	 */
	public void setPrimaryEvent(boolean PrimaryEvent) {
		this.PrimaryEvent = PrimaryEvent;
	}

	/**
	 * Set the PrimaryLocation field
	 *
	 * @param PrimaryLocation Contents of the PRIMARY_LOCATION column
	 */
	public void setPrimaryLocation(boolean PrimaryLocation) {
		this.PrimaryLocation = PrimaryLocation;
	}

	/**
	 * Set the TableId field
	 *
	 * @param TableId Contents of the TABLE_ID column
	 */
	public void setTableId(int TableId) {
		this.TableId = TableId;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setInt(1, getEventPid());
		ps.setInt(2, getLocationPid());
		ps.setBoolean(3, isPrimaryEvent());
		ps.setBoolean(4, isPrimaryLocation());
		ps.setInt(5, getTableId());
		ps.setInt(6, getLocationEventsPid());
		ps.executeUpdate();
		conn.close();
	}

}