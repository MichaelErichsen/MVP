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
 * The persistent class for the EVENT_TYPES database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 19. feb. 2019
 *
 */

public class EventTypes {
	private static final String SELECT = "SELECT EVENT_TYPE_PID, "
			+ "TABLE_ID, "
			+ "LABEL_PID FROM PUBLIC.EVENT_TYPES WHERE EVENT_TYPE_PID = ?";
	private static final String SELECT_LABEL_PID = "SELECT "
			+ "EVENT_TYPE_PID, TABLE_ID, "
			+ "LABEL_PID FROM PUBLIC.EVENT_TYPES WHERE LABEL_PID = ? ORDER BY EVENT_TYPE_PID";
	private static final String SELECTALL = "SELECT EVENT_TYPE_PID, "
			+ "TABLE_ID, "
			+ "LABEL_PID FROM PUBLIC.EVENT_TYPES ORDER BY EVENT_TYPE_PID";
	private static final String SELECTMAX = "SELECT MAX(EVENT_TYPE_PID) FROM PUBLIC.EVENT_TYPES";
	private static final String INSERT = "INSERT INTO PUBLIC.EVENT_TYPES( "
			+ "EVENT_TYPE_PID, TABLE_ID, LABEL_PID) VALUES (?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.EVENT_TYPES SET "
			+ "TABLE_ID = ?, LABEL_PID = ? WHERE EVENT_TYPE_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.EVENT_TYPES WHERE EVENT_TYPE_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.EVENT_TYPES";

	private List<EventTypes> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int EventTypePid;
	private int TableId;
	private int LabelPid;
	private EventTypes model;

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

	public List<EventTypes> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new EventTypes();
			model.setEventTypePid(rs.getInt("EVENT_TYPE_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
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
			setEventTypePid(rs.getInt("EVENT_TYPE_PID"));
			setTableId(rs.getInt("TABLE_ID"));
			setLabelPid(rs.getInt("LABEL_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	/**
	 * Get the EventTypePid field.
	 *
	 * @return Contents of the EVENT_TYPE_PID column
	 */
	public int getEventTypePid() {
		return EventTypePid;
	}

	public List<EventTypes> getFKLabelPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LABEL_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new EventTypes();
			model.setEventTypePid(rs.getInt("EVENT_TYPE_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	/**
	 * Get the LabelPid field.
	 *
	 * @return Contents of the LABEL_PID column
	 */
	public int getLabelPid() {
		return LabelPid;
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
		ps.setInt(2, getTableId());
		ps.setInt(3, getLabelPid());
		ps.executeUpdate();
		conn.close();
		return maxPid;
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
	 * Set the LabelPid field
	 *
	 * @param LabelPid Contents of the LABEL_PID column
	 */
	public void setLabelPid(int LabelPid) {
		this.LabelPid = LabelPid;
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
		ps.setInt(1, getTableId());
		ps.setInt(2, getLabelPid());
		ps.setInt(3, getEventTypePid());
		ps.executeUpdate();
		conn.close();
	}

}
