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
 * The persistent class for the PERSON_EVENTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. nov. 2018
 *
 */

public class PersonEvents {
	private static final String SELECT = "SELECT PERSON_EVENT_PID, EVENT_PID, PERSON_PID, ROLE, "
			+ "PRIMARY_PERSON, PRIMARY_EVENT, TABLE_ID FROM PUBLIC.PERSON_EVENTS WHERE PERSON_EVENT_PID = ?";
	private static final String SELECT_EVENT_PID = "SELECT PERSON_EVENT_PID, EVENT_PID, PERSON_PID, "
			+ "ROLE, PRIMARY_PERSON, PRIMARY_EVENT, "
			+ "TABLE_ID FROM PUBLIC.PERSON_EVENTS WHERE EVENT_PID = ? ORDER BY PERSON_EVENT_PID";
	private static final String SELECT_PERSON_PID = "SELECT PERSON_EVENT_PID, EVENT_PID, PERSON_PID, "
			+ "ROLE, PRIMARY_PERSON, PRIMARY_EVENT, "
			+ "TABLE_ID FROM PUBLIC.PERSON_EVENTS WHERE PERSON_PID = ? ORDER BY PERSON_EVENT_PID";
	private static final String SELECTALL = "SELECT PERSON_EVENT_PID, EVENT_PID, PERSON_PID, ROLE, "
			+ "PRIMARY_PERSON, PRIMARY_EVENT, TABLE_ID FROM PUBLIC.PERSON_EVENTS ORDER BY PERSON_EVENT_PID";
	private static final String SELECTMAX = "SELECT MAX(PERSON_EVENT_PID) FROM PUBLIC.PERSON_EVENTS";

	private static final String INSERT = "INSERT INTO PUBLIC.PERSON_EVENTS( PERSON_EVENT_PID, EVENT_PID, "
			+ "PERSON_PID, ROLE, PRIMARY_PERSON, PRIMARY_EVENT, TABLE_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.PERSON_EVENTS SET EVENT_PID = ?, PERSON_PID = ?, "
			+ "ROLE = ?, PRIMARY_PERSON = ?, PRIMARY_EVENT = ?, TABLE_ID = ? WHERE PERSON_EVENT_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.PERSON_EVENTS WHERE PERSON_EVENT_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.PERSON_EVENTS";

	private List<PersonEvents> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int PersonEventPid;
	private int EventPid;
	private int PersonPid;
	private String Role;
	private boolean PrimaryPerson;
	private boolean PrimaryEvent;
	private int TableId;
	private PersonEvents model;

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

	public List<PersonEvents> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonEvents();
			model.setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setPersonPid(rs.getInt("PERSON_PID"));
			model.setRole(rs.getString("ROLE"));
			model.setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
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
			setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			setEventPid(rs.getInt("EVENT_PID"));
			setPersonPid(rs.getInt("PERSON_PID"));
			setRole(rs.getString("ROLE"));
			setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
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

	public List<PersonEvents> getFKEventPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_EVENT_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonEvents();
			model.setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setPersonPid(rs.getInt("PERSON_PID"));
			model.setRole(rs.getString("ROLE"));
			model.setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<PersonEvents> getFKPersonPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_PERSON_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonEvents();
			model.setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setPersonPid(rs.getInt("PERSON_PID"));
			model.setRole(rs.getString("ROLE"));
			model.setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	/**
	 * Get the PersonEventPid field.
	 *
	 * @return Contents of the PERSON_EVENT_PID column
	 */
	public int getPersonEventPid() {
		return PersonEventPid;
	}

	/**
	 * Get the PersonPid field.
	 *
	 * @return Contents of the PERSON_PID column
	 */
	public int getPersonPid() {
		return PersonPid;
	}

	/**
	 * Get the Role field.
	 *
	 * @return Contents of the ROLE column
	 */
	public String getRole() {
		return Role;
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
		ps.setInt(3, getPersonPid());
		ps.setString(4, getRole());
		ps.setBoolean(5, isPrimaryPerson());
		ps.setBoolean(6, isPrimaryEvent());
		ps.setInt(7, getTableId());
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
	 * Get the PrimaryPerson field.
	 *
	 * @return Contents of the PRIMARY_PERSON column
	 */
	public boolean isPrimaryPerson() {
		return PrimaryPerson;
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
	 * Set the PersonEventPid field
	 *
	 * @param PersonEventPid Contents of the PERSON_EVENT_PID column
	 */
	public void setPersonEventPid(int PersonEventPid) {
		this.PersonEventPid = PersonEventPid;
	}

	/**
	 * Set the PersonPid field
	 *
	 * @param PersonPid Contents of the PERSON_PID column
	 */
	public void setPersonPid(int PersonPid) {
		this.PersonPid = PersonPid;
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
	 * Set the PrimaryPerson field
	 *
	 * @param PrimaryPerson Contents of the PRIMARY_PERSON column
	 */
	public void setPrimaryPerson(boolean PrimaryPerson) {
		this.PrimaryPerson = PrimaryPerson;
	}

	/**
	 * Set the Role field
	 *
	 * @param Role Contents of the ROLE column
	 */
	public void setRole(String Role) {
		this.Role = Role;
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
		ps.setInt(2, getPersonPid());
		ps.setString(3, getRole());
		ps.setBoolean(4, isPrimaryPerson());
		ps.setBoolean(5, isPrimaryEvent());
		ps.setInt(6, getTableId());
		ps.setInt(7, getPersonEventPid());
		ps.executeUpdate();
		conn.close();
	}

}
