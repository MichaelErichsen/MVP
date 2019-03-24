package net.myerichsen.hremvp.dbmodels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.MvpException;

/**
 * The persistent class for the PERSON_EVENTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 24. mar. 2019
 *
 */

public class PersonEvents {
	private static final String SELECT = "SELECT PERSON_EVENT_PID, "
			+ "EVENT_PID, EVENT_ROLE_PID, PERSON_PID, "
			+ "PRIMARY_PERSON, PRIMARY_EVENT, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, "
			+ "LANGUAGE_PID FROM PUBLIC.PERSON_EVENTS WHERE PERSON_EVENT_PID = ?";
	private static final String SELECT_EVENT_PID = "SELECT "
			+ "PERSON_EVENT_PID, EVENT_PID, EVENT_ROLE_PID, "
			+ "PERSON_PID, PRIMARY_PERSON, PRIMARY_EVENT, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, "
			+ "LANGUAGE_PID FROM PUBLIC.PERSON_EVENTS WHERE EVENT_PID = ? ORDER BY PERSON_EVENT_PID";
	private static final String SELECT_EVENT_ROLE_PID = "SELECT "
			+ "PERSON_EVENT_PID, EVENT_PID, EVENT_ROLE_PID, "
			+ "PERSON_PID, PRIMARY_PERSON, PRIMARY_EVENT, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, "
			+ "LANGUAGE_PID FROM PUBLIC.PERSON_EVENTS WHERE EVENT_ROLE_PID = ? ORDER BY PERSON_EVENT_PID";
	private static final String SELECT_PERSON_PID = "SELECT "
			+ "PERSON_EVENT_PID, EVENT_PID, EVENT_ROLE_PID, "
			+ "PERSON_PID, PRIMARY_PERSON, PRIMARY_EVENT, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, "
			+ "LANGUAGE_PID FROM PUBLIC.PERSON_EVENTS WHERE PERSON_PID = ? ORDER BY PERSON_EVENT_PID";
	private static final String SELECTALL = "SELECT PERSON_EVENT_PID, "
			+ "EVENT_PID, EVENT_ROLE_PID, PERSON_PID, "
			+ "PRIMARY_PERSON, PRIMARY_EVENT, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, "
			+ "LANGUAGE_PID FROM PUBLIC.PERSON_EVENTS ORDER BY PERSON_EVENT_PID";

	private static final String SELECTMAX = "SELECT MAX(PERSON_EVENT_PID) FROM PUBLIC.PERSON_EVENTS";

	private static final String INSERT = "INSERT INTO PUBLIC.PERSON_EVENTS( "
			+ "PERSON_EVENT_PID, EVENT_PID, EVENT_ROLE_PID, "
			+ "PERSON_PID, PRIMARY_PERSON, PRIMARY_EVENT, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, "
			+ "LANGUAGE_PID) VALUES (?, ?, ?, ?, ?, "
			+ "?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.PERSON_EVENTS SET "
			+ "EVENT_PID = ?, EVENT_ROLE_PID = ?, PERSON_PID = ?"
			+ ", PRIMARY_PERSON = ?, PRIMARY_EVENT = ?"
			+ ", UPDATE_TSTMP = CURRENT_TIMESTAMP"
			+ " WHERE PERSON_EVENT_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.PERSON_EVENTS WHERE PERSON_EVENT_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.PERSON_EVENTS";

	private List<PersonEvents> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int PersonEventPid;
	private int EventPid;
	private int EventRolePid;
	private int PersonPid;
	private boolean PrimaryPerson;
	private boolean PrimaryEvent;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
	private int LanguagePid;
	private PersonEvents model;

	public void delete() throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(DELETEALL);
		ps.executeUpdate();
		conn.close();
	}

	public void delete(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(DELETE);
		ps.setInt(1, key);
		if (ps.executeUpdate() == 0) {
			conn.close();
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<PersonEvents> get() throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonEvents();
			model.setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setEventRolePid(rs.getInt("EVENT_ROLE_PID"));
			model.setPersonPid(rs.getInt("PERSON_PID"));
			model.setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public void get(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		if (rs.next()) {
			setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			setEventPid(rs.getInt("EVENT_PID"));
			setEventRolePid(rs.getInt("EVENT_ROLE_PID"));
			setPersonPid(rs.getInt("PERSON_PID"));
			setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			setTableId(rs.getInt("TABLE_ID"));
			setLanguagePid(rs.getInt("LANGUAGE_PID"));
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

	/**
	 * Get the EventRolePid field.
	 *
	 * @return Contents of the EVENT_ROLE_PID column
	 */
	public int getEventRolePid() {
		return EventRolePid;
	}

	public List<PersonEvents> getFKEventPid(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_EVENT_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonEvents();
			model.setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setEventRolePid(rs.getInt("EVENT_ROLE_PID"));
			model.setPersonPid(rs.getInt("PERSON_PID"));
			model.setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<PersonEvents> getFKEventRolePid(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_EVENT_ROLE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonEvents();
			model.setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setEventRolePid(rs.getInt("EVENT_ROLE_PID"));
			model.setPersonPid(rs.getInt("PERSON_PID"));
			model.setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<PersonEvents> getFKPersonPid(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_PERSON_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonEvents();
			model.setPersonEventPid(rs.getInt("PERSON_EVENT_PID"));
			model.setEventPid(rs.getInt("EVENT_PID"));
			model.setEventRolePid(rs.getInt("EVENT_ROLE_PID"));
			model.setPersonPid(rs.getInt("PERSON_PID"));
			model.setPrimaryPerson(rs.getBoolean("PRIMARY_PERSON"));
			model.setPrimaryEvent(rs.getBoolean("PRIMARY_EVENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
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
	 * Get the LanguagePid field.
	 *
	 * @return Contents of the LANGUAGE_PID column
	 */
	public int getLanguagePid() {
		return LanguagePid;
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

	public int insert() throws Exception {
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
		ps.setInt(3, getEventRolePid());
		ps.setInt(4, getPersonPid());
		ps.setBoolean(5, isPrimaryPerson());
		ps.setBoolean(6, isPrimaryEvent());
		ps.setInt(7, getLanguagePid());
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
	 * Set the EventRolePid field
	 *
	 * @param EventRolePid Contents of the EVENT_ROLE_PID column
	 */
	public void setEventRolePid(int EventRolePid) {
		this.EventRolePid = EventRolePid;
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
	 * Set the LanguagePid field
	 *
	 * @param LanguagePid Contents of the LANGUAGE_PID column
	 */
	public void setLanguagePid(int LanguagePid) {
		this.LanguagePid = LanguagePid;
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

	public void update() throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setInt(1, getEventPid());
		ps.setInt(2, getEventRolePid());
		ps.setInt(3, getPersonPid());
		ps.setBoolean(4, isPrimaryPerson());
		ps.setBoolean(5, isPrimaryEvent());
		ps.setInt(6, getLanguagePid());
		ps.setInt(7, getPersonEventPid());
		ps.executeUpdate();
		conn.close();
	}

}
