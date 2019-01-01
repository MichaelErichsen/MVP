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
 * The persistent class for the NAME_PARTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018
 * @version 20. nov. 2018
 *
 */

public class NameParts {
	private static final String SELECT = "SELECT NAME_PART_PID, NAME_PID, LABEL, PART_NO, "
			+ "TABLE_ID FROM PUBLIC.NAME_PARTS WHERE NAME_PART_PID = ?";
	private static final String SELECT_NAME_PID = "SELECT NAME_PART_PID, NAME_PID, LABEL, PART_NO, "
			+ "TABLE_ID FROM PUBLIC.NAME_PARTS WHERE NAME_PID = ? ORDER BY NAME_PART_PID";
	private static final String SELECTALL = "SELECT NAME_PART_PID, NAME_PID, LABEL, PART_NO, "
			+ "TABLE_ID FROM PUBLIC.NAME_PARTS ORDER BY NAME_PART_PID";
	private static final String SELECTMAX = "SELECT MAX(NAME_PART_PID) FROM PUBLIC.NAME_PARTS";
	private static final String INSERT = "INSERT INTO PUBLIC.NAME_PARTS( NAME_PART_PID, NAME_PID, "
			+ "LABEL, PART_NO, TABLE_ID) VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.NAME_PARTS SET NAME_PID = ?, LABEL = ?, "
			+ "PART_NO = ?, TABLE_ID = ? WHERE NAME_PART_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.NAME_PARTS WHERE NAME_PART_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.NAME_PARTS";

	private List<NameParts> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int NamePartPid;
	private int NamePid;
	private String Label;
	private int PartNo;
	private int TableId;
	private NameParts model;

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

	public List<NameParts> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameParts();
			model.setNamePartPid(rs.getInt("NAME_PART_PID"));
			model.setNamePid(rs.getInt("NAME_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setPartNo(rs.getInt("PART_NO"));
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
			setNamePartPid(rs.getInt("NAME_PART_PID"));
			setNamePid(rs.getInt("NAME_PID"));
			setLabel(rs.getString("LABEL"));
			setPartNo(rs.getInt("PART_NO"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<NameParts> getFKNamePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_NAME_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameParts();
			model.setNamePartPid(rs.getInt("NAME_PART_PID"));
			model.setNamePid(rs.getInt("NAME_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
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
	 * Get the NamePartPid field.
	 *
	 * @return Contents of the NAME_PART_PID column
	 */
	public int getNamePartPid() {
		return NamePartPid;
	}

	/**
	 * Get the NamePid field.
	 *
	 * @return Contents of the NAME_PID column
	 */
	public int getNamePid() {
		return NamePid;
	}

	/**
	 * Get the PartNo field.
	 *
	 * @return Contents of the PART_NO column
	 */
	public int getPartNo() {
		return PartNo;
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
		ps.setInt(2, getNamePid());
		ps.setString(3, getLabel());
		ps.setInt(4, getPartNo());
		ps.setInt(5, getTableId());
		ps.executeUpdate();
		conn.close();
		return maxPid;
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
	 * Set the NamePartPid field
	 *
	 * @param NamePartPid Contents of the NAME_PART_PID column
	 */
	public void setNamePartPid(int NamePartPid) {
		this.NamePartPid = NamePartPid;
	}

	/**
	 * Set the NamePid field
	 *
	 * @param NamePid Contents of the NAME_PID column
	 */
	public void setNamePid(int NamePid) {
		this.NamePid = NamePid;
	}

	/**
	 * Set the PartNo field
	 *
	 * @param PartNo Contents of the PART_NO column
	 */
	public void setPartNo(int PartNo) {
		this.PartNo = PartNo;
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
		ps.setInt(1, getNamePid());
		ps.setString(2, getLabel());
		ps.setInt(3, getPartNo());
		ps.setInt(4, getTableId());
		ps.setInt(5, getNamePartPid());
		ps.executeUpdate();
		conn.close();
	}

}
