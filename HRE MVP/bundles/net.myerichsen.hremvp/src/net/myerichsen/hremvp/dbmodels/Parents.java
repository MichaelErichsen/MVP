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
 * The persistent class for the PARENTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 28. mar. 2019
 *
 */

public class Parents {
	private static final String SELECT = "SELECT PARENT_PID, CHILD, "
			+ "PARENT, PRIMARY_PARENT, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, LANGUAGE_PID, "
			+ "PARENT_ROLE_PID FROM PUBLIC.PARENTS WHERE PARENT_PID = ?";
	private static final String SELECT_CHILD = "SELECT PARENT_PID, "
			+ "CHILD, PARENT, PRIMARY_PARENT, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, LANGUAGE_PID, "
			+ "PARENT_ROLE_PID FROM PUBLIC.PARENTS WHERE CHILD = ? ORDER BY PARENT_PID";
	private static final String SELECT_PARENT = "SELECT PARENT_PID, "
			+ "CHILD, PARENT, PRIMARY_PARENT, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, LANGUAGE_PID, "
			+ "PARENT_ROLE_PID FROM PUBLIC.PARENTS WHERE PARENT = ? ORDER BY PARENT_PID";
	private static final String SELECT_PARENT_ROLE_PID = "SELECT "
			+ "PARENT_PID, CHILD, PARENT, PRIMARY_PARENT, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, LANGUAGE_PID, "
			+ "PARENT_ROLE_PID FROM PUBLIC.PARENTS WHERE PARENT_ROLE_PID = ? ORDER BY PARENT_PID";
	private static final String SELECTALL = "SELECT PARENT_PID, "
			+ "CHILD, PARENT, PRIMARY_PARENT, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, LANGUAGE_PID, "
			+ "PARENT_ROLE_PID FROM PUBLIC.PARENTS ORDER BY PARENT_PID";

	private static final String SELECTMAX = "SELECT MAX(PARENT_PID) FROM PUBLIC.PARENTS";

	private static final String INSERT = "INSERT INTO PUBLIC.PARENTS( "
			+ "PARENT_PID, CHILD, PARENT, PRIMARY_PARENT, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, "
			+ "LANGUAGE_PID, PARENT_ROLE_PID) VALUES (?, ?, "
			+ "?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 22, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.PARENTS SET "
			+ "CHILD = ?, PARENT = ?, PRIMARY_PARENT = ?"
			+ ", UPDATE_TSTMP = CURRENT_TIMESTAMP, LANGUAGE_PID = ?"
			+ ", PARENT_ROLE_PID = ? WHERE PARENT_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.PARENTS WHERE PARENT_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.PARENTS";

	private List<Parents> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int ParentPid;
	private int Child;
	private int Parent;
	private boolean PrimaryParent;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
	private int LanguagePid;
	private int ParentRolePid;
	private Parents model;

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

	public List<Parents> get() throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			model.setParentRolePid(rs.getInt("PARENT_ROLE_PID"));
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
			setParentPid(rs.getInt("PARENT_PID"));
			setChild(rs.getInt("CHILD"));
			setParent(rs.getInt("PARENT"));
			setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			setTableId(rs.getInt("TABLE_ID"));
			setLanguagePid(rs.getInt("LANGUAGE_PID"));
			setParentRolePid(rs.getInt("PARENT_ROLE_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	/**
	 * Get the Child field.
	 *
	 * @return Contents of the CHILD column
	 */
	public int getChild() {
		return Child;
	}

	public List<Parents> getFKChild(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_CHILD);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			model.setParentRolePid(rs.getInt("PARENT_ROLE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Parents> getFKParent(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_PARENT);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			model.setParentRolePid(rs.getInt("PARENT_ROLE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Parents> getFKParentRolePid(int key) throws Exception {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_PARENT_ROLE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			model.setParentRolePid(rs.getInt("PARENT_ROLE_PID"));
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
	 * Get the Parent field.
	 *
	 * @return Contents of the PARENT column
	 */
	public int getParent() {
		return Parent;
	}

	/**
	 * Get the ParentPid field.
	 *
	 * @return Contents of the PARENT_PID column
	 */
	public int getParentPid() {
		return ParentPid;
	}

	/**
	 * Get the ParentRolePid field.
	 *
	 * @return Contents of the PARENT_ROLE_PID column
	 */
	public int getParentRolePid() {
		return ParentRolePid;
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
		ps.setInt(2, getChild());
		ps.setInt(3, getParent());
		ps.setBoolean(4, isPrimaryParent());
		ps.setInt(5, getLanguagePid());
		ps.setInt(6, getParentRolePid());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	/**
	 * Get the PrimaryParent field.
	 *
	 * @return Contents of the PRIMARY_PARENT column
	 */
	public boolean isPrimaryParent() {
		return PrimaryParent;
	}

	/**
	 * Set the Child field
	 *
	 * @param Child Contents of the CHILD column
	 */
	public void setChild(int Child) {
		this.Child = Child;
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
	 * Set the Parent field
	 *
	 * @param Parent Contents of the PARENT column
	 */
	public void setParent(int Parent) {
		this.Parent = Parent;
	}

	/**
	 * Set the ParentPid field
	 *
	 * @param ParentPid Contents of the PARENT_PID column
	 */
	public void setParentPid(int ParentPid) {
		this.ParentPid = ParentPid;
	}

	/**
	 * Set the ParentRolePid field
	 *
	 * @param ParentRolePid Contents of the PARENT_ROLE_PID column
	 */
	public void setParentRolePid(int ParentRolePid) {
		this.ParentRolePid = ParentRolePid;
	}

	/**
	 * Set the PrimaryParent field
	 *
	 * @param PrimaryParent Contents of the PRIMARY_PARENT column
	 */
	public void setPrimaryParent(boolean PrimaryParent) {
		this.PrimaryParent = PrimaryParent;
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
		ps.setInt(1, getChild());
		ps.setInt(2, getParent());
		ps.setBoolean(3, isPrimaryParent());
		ps.setInt(4, getLanguagePid());
		ps.setInt(5, getParentRolePid());
		ps.setInt(6, getParentPid());
		ps.executeUpdate();
		conn.close();
	}

}
