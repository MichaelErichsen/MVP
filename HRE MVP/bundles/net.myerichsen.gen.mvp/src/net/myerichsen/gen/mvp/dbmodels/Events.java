package net.myerichsen.gen.mvp.dbmodels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.gen.mvp.HreH2ConnectionPool;
import net.myerichsen.gen.mvp.MvpException;

/**
 * The persistent class for the EVENTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018
 * @version 24. nov. 2018
 *
 */

public class Events {
	private List<Events> modelList;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection conn;
	private static final String SELECT = "SELECT EVENT_PID, TABLE_ID, FROM_DATE_PID, TO_DATE_PID, "
			+ "EVENT_NAME_PID FROM PUBLIC.EVENTS WHERE EVENT_PID = ?";

	private static final String SELECT_FROM_DATE_PID = "SELECT EVENT_PID, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID, EVENT_NAME_PID FROM PUBLIC.EVENTS WHERE FROM_DATE_PID = ? ORDER BY EVENT_PID";

	private static final String SELECT_TO_DATE_PID = "SELECT EVENT_PID, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID, EVENT_NAME_PID FROM PUBLIC.EVENTS WHERE TO_DATE_PID = ? ORDER BY EVENT_PID";

	private static final String SELECTALL = "SELECT EVENT_PID, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID, EVENT_NAME_PID FROM PUBLIC.EVENTS ORDER BY EVENT_PID";

	private static final String SELECTMAX = "SELECT MAX(EVENT_PID) FROM PUBLIC.EVENTS";

	private static final String INSERT = "INSERT INTO PUBLIC.EVENTS( EVENT_PID, TABLE_ID, "
			+ "FROM_DATE_PID, TO_DATE_PID, EVENT_NAME_PID) VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.EVENTS SET TABLE_ID = ?, FROM_DATE_PID = ?, "
			+ "TO_DATE_PID = ?, EVENT_NAME_PID = ? WHERE EVENT_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.EVENTS WHERE EVENT_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.EVENTS";

	private int EventPid;
	private int TableId;
	private int FromDatePid;
	private int ToDatePid;
	private int EventNamePid;
	private Events model;

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

	public List<Events> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<Events>();
		while (rs.next()) {
			model = new Events();
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			model.setEventNamePid(rs.getInt("EVENT_NAME_PID"));
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
			setEventPid(rs.getInt("EVENT_PID"));
			setTableId(rs.getInt("TABLE_ID"));
			setFromDatePid(rs.getInt("FROM_DATE_PID"));
			setToDatePid(rs.getInt("TO_DATE_PID"));
			setEventNamePid(rs.getInt("EVENT_NAME_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<Events> getFKFromDatePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_FROM_DATE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<Events>();
		while (rs.next()) {
			model = new Events();
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			model.setEventNamePid(rs.getInt("EVENT_NAME_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Events> getFKToDatePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_TO_DATE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<Events>();
		while (rs.next()) {
			model = new Events();
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			model.setEventNamePid(rs.getInt("EVENT_NAME_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
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
		if (getFromDatePid() == 0)
			ps.setNull(3, java.sql.Types.INTEGER);
		else
			ps.setInt(3, getFromDatePid());
		if (getToDatePid() == 0)
			ps.setNull(4, java.sql.Types.INTEGER);
		else
			ps.setInt(4, getToDatePid());
		ps.setInt(5, getEventNamePid());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setInt(1, getTableId());
		if (getFromDatePid() == 0)
			ps.setNull(2, java.sql.Types.INTEGER);
		else
			ps.setInt(2, getFromDatePid());
		if (getToDatePid() == 0)
			ps.setNull(3, java.sql.Types.INTEGER);
		else
			ps.setInt(3, getToDatePid());
		ps.setInt(4, getEventNamePid());
		ps.setInt(5, getEventPid());
		ps.executeUpdate();
		conn.close();
	}

	/**
	 * Get the EventPid field.
	 *
	 * @return Contents of the EVENT_PID column
	 */
	public int getEventPid() {
		return this.EventPid;
	}

	/**
	 * Get the TableId field.
	 *
	 * @return Contents of the TABLE_ID column
	 */
	public int getTableId() {
		return this.TableId;
	}

	/**
	 * Get the FromDatePid field.
	 *
	 * @return Contents of the FROM_DATE_PID column
	 */
	public int getFromDatePid() {
		return this.FromDatePid;
	}

	/**
	 * Get the ToDatePid field.
	 *
	 * @return Contents of the TO_DATE_PID column
	 */
	public int getToDatePid() {
		return this.ToDatePid;
	}

	/**
	 * Get the EventNamePid field.
	 *
	 * @return Contents of the EVENT_NAME_PID column
	 */
	public int getEventNamePid() {
		return this.EventNamePid;
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
	 * Set the TableId field
	 *
	 * @param TableId Contents of the TABLE_ID column
	 */
	public void setTableId(int TableId) {
		this.TableId = TableId;
	}

	/**
	 * Set the FromDatePid field
	 *
	 * @param FromDatePid Contents of the FROM_DATE_PID column
	 */
	public void setFromDatePid(int FromDatePid) {
		this.FromDatePid = FromDatePid;
	}

	/**
	 * Set the ToDatePid field
	 *
	 * @param ToDatePid Contents of the TO_DATE_PID column
	 */
	public void setToDatePid(int ToDatePid) {
		this.ToDatePid = ToDatePid;
	}

	/**
	 * Set the EventNamePid field
	 *
	 * @param EventNamePid Contents of the EVENT_NAME_PID column
	 */
	public void setEventNamePid(int EventNamePid) {
		this.EventNamePid = EventNamePid;
	}

}
