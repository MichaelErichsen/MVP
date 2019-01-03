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
 * The persistent class for the PARENTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018
 * @version 25. nov. 2018
 *
 */

public class Parents {
	private List<Parents> modelList;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection conn;
	private static final String SELECT = "SELECT PARENT_PID, CHILD, PARENT, PARENT_ROLE, "
			+ "PRIMARY_PARENT, TABLE_ID, LANGUAGE_PID FROM PUBLIC.PARENTS WHERE PARENT_PID = ?";

	private static final String SELECT_CHILD = "SELECT PARENT_PID, CHILD, PARENT, PARENT_ROLE, "
			+ "PRIMARY_PARENT, TABLE_ID, LANGUAGE_PID FROM PUBLIC.PARENTS WHERE CHILD = ? ORDER BY PARENT_PID";

	private static final String SELECT_PARENT = "SELECT PARENT_PID, CHILD, PARENT, PARENT_ROLE, "
			+ "PRIMARY_PARENT, TABLE_ID, LANGUAGE_PID FROM PUBLIC.PARENTS WHERE PARENT = ? ORDER BY PARENT_PID";

	private static final String SELECT_LANGUAGE_PID = "SELECT PARENT_PID, CHILD, PARENT, "
			+ "PARENT_ROLE, PRIMARY_PARENT, TABLE_ID, "
			+ "LANGUAGE_PID FROM PUBLIC.PARENTS WHERE LANGUAGE_PID = ? ORDER BY PARENT_PID";

	private static final String SELECTALL = "SELECT PARENT_PID, CHILD, PARENT, PARENT_ROLE, "
			+ "PRIMARY_PARENT, TABLE_ID, LANGUAGE_PID FROM PUBLIC.PARENTS ORDER BY PARENT_PID";

	private static final String SELECTMAX = "SELECT MAX(PARENT_PID) FROM PUBLIC.PARENTS";

	private static final String INSERT = "INSERT INTO PUBLIC.PARENTS( PARENT_PID, CHILD, PARENT, "
			+ "PARENT_ROLE, PRIMARY_PARENT, TABLE_ID, LANGUAGE_PID) VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.PARENTS SET CHILD = ?, PARENT = ?, "
			+ "PARENT_ROLE = ?, PRIMARY_PARENT = ?, TABLE_ID = ?, LANGUAGE_PID = ? WHERE PARENT_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.PARENTS WHERE PARENT_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.PARENTS";

	private int ParentPid;
	private int Child;
	private int Parent;
	private String ParentRole;
	private boolean PrimaryParent;
	private int TableId;
	private int LanguagePid;
	private Parents model;

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

	public List<Parents> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<Parents>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setParentRole(rs.getString("PARENT_ROLE"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
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
			setParentPid(rs.getInt("PARENT_PID"));
			setChild(rs.getInt("CHILD"));
			setParent(rs.getInt("PARENT"));
			setParentRole(rs.getString("PARENT_ROLE"));
			setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			setTableId(rs.getInt("TABLE_ID"));
			setLanguagePid(rs.getInt("LANGUAGE_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<Parents> getFKChild(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_CHILD);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<Parents>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setParentRole(rs.getString("PARENT_ROLE"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Parents> getFKParent(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_PARENT);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<Parents>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setParentRole(rs.getString("PARENT_ROLE"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Parents> getFKLanguagePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LANGUAGE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<Parents>();
		while (rs.next()) {
			model = new Parents();
			model.setParentPid(rs.getInt("PARENT_PID"));
			model.setChild(rs.getInt("CHILD"));
			model.setParent(rs.getInt("PARENT"));
			model.setParentRole(rs.getString("PARENT_ROLE"));
			model.setPrimaryParent(rs.getBoolean("PRIMARY_PARENT"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
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
		ps.setInt(2, getChild());
		ps.setInt(3, getParent());
		ps.setString(4, getParentRole());
		ps.setBoolean(5, isPrimaryParent());
		ps.setInt(6, getTableId());
		ps.setInt(7, getLanguagePid());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setInt(1, getChild());
		ps.setInt(2, getParent());
		ps.setString(3, getParentRole());
		ps.setBoolean(4, isPrimaryParent());
		ps.setInt(5, getTableId());
		ps.setInt(6, getLanguagePid());
		ps.setInt(7, getParentPid());
		ps.executeUpdate();
		conn.close();
	}

	/**
	 * Get the ParentPid field.
	 *
	 * @return Contents of the PARENT_PID column
	 */
	public int getParentPid() {
		return this.ParentPid;
	}

	/**
	 * Get the Child field.
	 *
	 * @return Contents of the CHILD column
	 */
	public int getChild() {
		return this.Child;
	}

	/**
	 * Get the Parent field.
	 *
	 * @return Contents of the PARENT column
	 */
	public int getParent() {
		return this.Parent;
	}

	/**
	 * Get the ParentRole field.
	 *
	 * @return Contents of the PARENT_ROLE column
	 */
	public String getParentRole() {
		return this.ParentRole;
	}

	/**
	 * Get the PrimaryParent field.
	 *
	 * @return Contents of the PRIMARY_PARENT column
	 */
	public boolean isPrimaryParent() {
		return this.PrimaryParent;
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
	 * Get the LanguagePid field.
	 *
	 * @return Contents of the LANGUAGE_PID column
	 */
	public int getLanguagePid() {
		return this.LanguagePid;
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
	 * Set the Child field
	 *
	 * @param Child Contents of the CHILD column
	 */
	public void setChild(int Child) {
		this.Child = Child;
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
	 * Set the ParentRole field
	 *
	 * @param ParentRole Contents of the PARENT_ROLE column
	 */
	public void setParentRole(String ParentRole) {
		this.ParentRole = ParentRole;
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
	 * Set the LanguagePid field
	 *
	 * @param LanguagePid Contents of the LANGUAGE_PID column
	 */
	public void setLanguagePid(int LanguagePid) {
		this.LanguagePid = LanguagePid;
	}

}
