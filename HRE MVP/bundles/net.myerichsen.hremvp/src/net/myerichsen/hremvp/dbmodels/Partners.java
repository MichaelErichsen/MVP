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
 * The persistent class for the PARTNERS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. nov. 2018
 *
 */

public class Partners {
	private static final String SELECT = "SELECT PARTNER_PID, PARTNER1, PARTNER2, PRIMARY_PARTNER, "
			+ "ROLE, TABLE_ID, FROM_DATE_PID, TO_DATE_PID FROM PUBLIC.PARTNERS WHERE PARTNER_PID = ?";
	private static final String SELECT_PARTNER1 = "SELECT PARTNER_PID, PARTNER1, PARTNER2, "
			+ "PRIMARY_PARTNER, ROLE, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.PARTNERS WHERE PARTNER1 = ? ORDER BY PARTNER_PID";
	private static final String SELECT_PARTNER2 = "SELECT PARTNER_PID, PARTNER1, PARTNER2, "
			+ "PRIMARY_PARTNER, ROLE, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.PARTNERS WHERE PARTNER2 = ? ORDER BY PARTNER_PID";
	private static final String SELECT_FROM_DATE_PID = "SELECT PARTNER_PID, PARTNER1, PARTNER2, "
			+ "PRIMARY_PARTNER, ROLE, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.PARTNERS WHERE FROM_DATE_PID = ? ORDER BY PARTNER_PID";
	private static final String SELECT_TO_DATE_PID = "SELECT PARTNER_PID, PARTNER1, PARTNER2, "
			+ "PRIMARY_PARTNER, ROLE, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.PARTNERS WHERE TO_DATE_PID = ? ORDER BY PARTNER_PID";

	private static final String SELECTALL = "SELECT PARTNER_PID, PARTNER1, PARTNER2, "
			+ "PRIMARY_PARTNER, ROLE, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.PARTNERS ORDER BY PARTNER_PID";

	private static final String SELECTMAX = "SELECT MAX(PARTNER_PID) FROM PUBLIC.PARTNERS";

	private static final String INSERT = "INSERT INTO PUBLIC.PARTNERS( PARTNER_PID, PARTNER1, PARTNER2, "
			+ "PRIMARY_PARTNER, ROLE, TABLE_ID, FROM_DATE_PID, TO_DATE_PID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.PARTNERS SET PARTNER1 = ?, PARTNER2 = ?, "
			+ "PRIMARY_PARTNER = ?, ROLE = ?, TABLE_ID = ?, FROM_DATE_PID = ?, "
			+ "TO_DATE_PID = ? WHERE PARTNER_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.PARTNERS WHERE PARTNER_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.PARTNERS";

	private List<Partners> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int PartnerPid;
	private int Partner1;
	private int Partner2;
	private boolean PrimaryPartner;
	private String Role;
	private int TableId;
	private int FromDatePid;
	private int ToDatePid;
	private Partners model;

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

	public List<Partners> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Partners();
			model.setPartnerPid(rs.getInt("PARTNER_PID"));
			model.setPartner1(rs.getInt("PARTNER1"));
			model.setPartner2(rs.getInt("PARTNER2"));
			model.setPrimaryPartner(rs.getBoolean("PRIMARY_PARTNER"));
			model.setRole(rs.getString("ROLE"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
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
			setPartnerPid(rs.getInt("PARTNER_PID"));
			setPartner1(rs.getInt("PARTNER1"));
			setPartner2(rs.getInt("PARTNER2"));
			setPrimaryPartner(rs.getBoolean("PRIMARY_PARTNER"));
			setRole(rs.getString("ROLE"));
			setTableId(rs.getInt("TABLE_ID"));
			setFromDatePid(rs.getInt("FROM_DATE_PID"));
			setToDatePid(rs.getInt("TO_DATE_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<Partners> getFKFromDatePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_FROM_DATE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Partners();
			model.setPartnerPid(rs.getInt("PARTNER_PID"));
			model.setPartner1(rs.getInt("PARTNER1"));
			model.setPartner2(rs.getInt("PARTNER2"));
			model.setPrimaryPartner(rs.getBoolean("PRIMARY_PARTNER"));
			model.setRole(rs.getString("ROLE"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Partners> getFKPartner1(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_PARTNER1);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Partners();
			model.setPartnerPid(rs.getInt("PARTNER_PID"));
			model.setPartner1(rs.getInt("PARTNER1"));
			model.setPartner2(rs.getInt("PARTNER2"));
			model.setPrimaryPartner(rs.getBoolean("PRIMARY_PARTNER"));
			model.setRole(rs.getString("ROLE"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Partners> getFKPartner2(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_PARTNER2);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Partners();
			model.setPartnerPid(rs.getInt("PARTNER_PID"));
			model.setPartner1(rs.getInt("PARTNER1"));
			model.setPartner2(rs.getInt("PARTNER2"));
			model.setPrimaryPartner(rs.getBoolean("PRIMARY_PARTNER"));
			model.setRole(rs.getString("ROLE"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Partners> getFKToDatePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_TO_DATE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Partners();
			model.setPartnerPid(rs.getInt("PARTNER_PID"));
			model.setPartner1(rs.getInt("PARTNER1"));
			model.setPartner2(rs.getInt("PARTNER2"));
			model.setPrimaryPartner(rs.getBoolean("PRIMARY_PARTNER"));
			model.setRole(rs.getString("ROLE"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	/**
	 * Get the FromDatePid field.
	 *
	 * @return Contents of the FROM_DATE_PID column
	 */
	public int getFromDatePid() {
		return FromDatePid;
	}

	/**
	 * Get the Partner1 field.
	 *
	 * @return Contents of the PARTNER1 column
	 */
	public int getPartner1() {
		return Partner1;
	}

	/**
	 * Get the Partner2 field.
	 *
	 * @return Contents of the PARTNER2 column
	 */
	public int getPartner2() {
		return Partner2;
	}

	/**
	 * Get the PartnerPid field.
	 *
	 * @return Contents of the PARTNER_PID column
	 */
	public int getPartnerPid() {
		return PartnerPid;
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

	/**
	 * Get the ToDatePid field.
	 *
	 * @return Contents of the TO_DATE_PID column
	 */
	public int getToDatePid() {
		return ToDatePid;
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
		ps.setInt(2, getPartner1());
		ps.setInt(3, getPartner2());
		ps.setBoolean(4, isPrimaryPartner());
		ps.setString(5, getRole());
		ps.setInt(6, getTableId());
		if (getFromDatePid() == 0) {
			ps.setNull(7, java.sql.Types.INTEGER);
		} else {
			ps.setInt(7, getFromDatePid());
		}
		if (getToDatePid() == 0) {
			ps.setNull(8, java.sql.Types.INTEGER);
		} else {
			ps.setInt(8, getToDatePid());
		}
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	/**
	 * Get the PrimaryPartner field.
	 *
	 * @return Contents of the PRIMARY_PARTNER column
	 */
	public boolean isPrimaryPartner() {
		return PrimaryPartner;
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
	 * Set the Partner1 field
	 *
	 * @param Partner1 Contents of the PARTNER1 column
	 */
	public void setPartner1(int Partner1) {
		this.Partner1 = Partner1;
	}

	/**
	 * Set the Partner2 field
	 *
	 * @param Partner2 Contents of the PARTNER2 column
	 */
	public void setPartner2(int Partner2) {
		this.Partner2 = Partner2;
	}

	/**
	 * Set the PartnerPid field
	 *
	 * @param PartnerPid Contents of the PARTNER_PID column
	 */
	public void setPartnerPid(int PartnerPid) {
		this.PartnerPid = PartnerPid;
	}

	/**
	 * Set the PrimaryPartner field
	 *
	 * @param PrimaryPartner Contents of the PRIMARY_PARTNER column
	 */
	public void setPrimaryPartner(boolean PrimaryPartner) {
		this.PrimaryPartner = PrimaryPartner;
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

	/**
	 * Set the ToDatePid field
	 *
	 * @param ToDatePid Contents of the TO_DATE_PID column
	 */
	public void setToDatePid(int ToDatePid) {
		this.ToDatePid = ToDatePid;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setInt(1, getPartner1());
		ps.setInt(2, getPartner2());
		ps.setBoolean(3, isPrimaryPartner());
		ps.setString(4, getRole());
		ps.setInt(5, getTableId());
		if (getFromDatePid() == 0) {
			ps.setNull(6, java.sql.Types.INTEGER);
		} else {
			ps.setInt(6, getFromDatePid());
		}
		if (getToDatePid() == 0) {
			ps.setNull(7, java.sql.Types.INTEGER);
		} else {
			ps.setInt(7, getToDatePid());
		}
		ps.setInt(8, getPartnerPid());
		ps.executeUpdate();
		conn.close();
	}

}