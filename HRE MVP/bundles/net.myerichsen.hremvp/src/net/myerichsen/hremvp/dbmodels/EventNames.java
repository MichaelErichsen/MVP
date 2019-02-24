package net.myerichsen.hremvp.dbmodels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.MvpException;

/**
 * The persistent class for the EVENT_NAMES database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 24. feb. 2019
 *
 */

public class EventNames {
	private static final String SELECT = "SELECT EVENT_NAME_PID, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, LABEL, "
			+ "EVENT_TYPE_PID FROM PUBLIC.EVENT_NAMES WHERE EVENT_NAME_PID = ?";
	private static final String SELECTALL = "SELECT EVENT_NAME_PID, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, LABEL, "
			+ "EVENT_TYPE_PID FROM PUBLIC.EVENT_NAMES ORDER BY EVENT_NAME_PID";
	private static final String SELECTMAX = "SELECT MAX(EVENT_NAME_PID) FROM PUBLIC.EVENT_NAMES";
	private static final String INSERT = "INSERT INTO PUBLIC.EVENT_NAMES( "
			+ "EVENT_NAME_PID, INSERT_TSTMP, UPDATE_TSTMP, "
			+ "TABLE_ID, LABEL, EVENT_TYPE_PID) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE PUBLIC.EVENT_NAMES SET "
			+ "INSERT_TSTMP = ?, UPDATE_TSTMP = ?, TABLE_ID = ?, "
			+ "LABEL = ?, EVENT_TYPE_PID = ? WHERE EVENT_NAME_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.EVENT_NAMES WHERE EVENT_NAME_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.EVENT_NAMES";

	private List<EventNames> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int EventNamePid;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
	private String Label;
	private int EventTypePid;
	private EventNames model;

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

	public List<EventNames> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new EventNames();
			model.setEventNamePid(rs.getInt("EVENT_NAME_PID"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLabel(rs.getString("LABEL"));
			model.setEventTypePid(rs.getInt("EVENT_TYPE_PID"));
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
			setEventNamePid(rs.getInt("EVENT_NAME_PID"));
			setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			setTableId(rs.getInt("TABLE_ID"));
			setLabel(rs.getString("LABEL"));
			setEventTypePid(rs.getInt("EVENT_TYPE_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	/**
	 * Get the EventNamePid field.
	 *
	 * @return Contents of the EVENT_NAME_PID column
	 */
	public int getEventNamePid() {
		return EventNamePid;
	}

	/**
	 * Get the EventTypePid field.
	 *
	 * @return Contents of the EVENT_TYPE_PID column
	 */
	public int getEventTypePid() {
		return EventTypePid;
	}

	/**
	 * Get the InsertTstmp field.
	 *
	 * @return Contents of the INSERT_TSTMP column
	 */
	public Timestamp getInsertTstmp() {
		return InsertTstmp;
	}

	/**
	 * Get the Label field.
	 *
	 * @return Contents of the LABEL column
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * Get the TableId field.
	 *
	 * @return Contents of the TABLE_ID column
	 */
	public int getTableId() {
		return TableId;
	}

	/**
	 * Get the UpdateTstmp field.
	 *
	 * @return Contents of the UPDATE_TSTMP column
	 */
	public Timestamp getUpdateTstmp() {
		return UpdateTstmp;
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
		ps.setTimestamp(2, getInsertTstmp());
		ps.setTimestamp(3, getUpdateTstmp());
		ps.setInt(4, getTableId());
		ps.setString(5, getLabel());
		ps.setInt(6, getEventTypePid());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	/**
	 * Set the EventNamePid field
	 *
	 * @param EventNamePid Contents of the EVENT_NAME_PID column
	 */
	public void setEventNamePid(int EventNamePid) {
		this.EventNamePid = EventNamePid;
	}

	/**
	 * Set the EventTypePid field
	 *
	 * @param EventTypePid Contents of the EVENT_TYPE_PID column
	 */
	public void setEventTypePid(int EventTypePid) {
		this.EventTypePid = EventTypePid;
	}

	/**
	 * Set the InsertTstmp field
	 *
	 * @param InsertTstmp Contents of the INSERT_TSTMP column
	 */
	public void setInsertTstmp(Timestamp InsertTstmp) {
		this.InsertTstmp = InsertTstmp;
	}

	/**
	 * Set the Label field
	 *
	 * @param Label Contents of the LABEL column
	 */
	public void setLabel(String Label) {
		this.Label = Label;
	}

	/**
	 * Set the TableId field
	 *
	 * @param TableId Contents of the TABLE_ID column
	 */
	public void setTableId(int TableId) {
		this.TableId = TableId;
	}

	/**
	 * Set the UpdateTstmp field
	 *
	 * @param UpdateTstmp Contents of the UPDATE_TSTMP column
	 */
	public void setUpdateTstmp(Timestamp UpdateTstmp) {
		this.UpdateTstmp = UpdateTstmp;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setTimestamp(1, getInsertTstmp());
		ps.setTimestamp(2, getUpdateTstmp());
		ps.setInt(3, getTableId());
		ps.setString(4, getLabel());
		ps.setInt(5, getEventTypePid());
		ps.setInt(6, getEventNamePid());
		ps.executeUpdate();
		conn.close();
	}

}
