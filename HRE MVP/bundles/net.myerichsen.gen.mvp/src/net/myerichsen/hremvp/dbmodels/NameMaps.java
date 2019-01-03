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
 * The persistent class for the NAME_MAPS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. nov. 2018
 *
 */

public class NameMaps {
	private static final String SELECT = "SELECT NAME_MAP_PID, NAME_STYLE_PID, PART_NO, LABEL, "
			+ "TABLE_ID FROM PUBLIC.NAME_MAPS WHERE NAME_MAP_PID = ?";
	private static final String SELECT_NAME_STYLE_PID = "SELECT NAME_MAP_PID, NAME_STYLE_PID, PART_NO, "
			+ "LABEL, TABLE_ID FROM PUBLIC.NAME_MAPS WHERE NAME_STYLE_PID = ? ORDER BY NAME_MAP_PID";
	private static final String SELECTALL = "SELECT NAME_MAP_PID, NAME_STYLE_PID, PART_NO, LABEL, "
			+ "TABLE_ID FROM PUBLIC.NAME_MAPS ORDER BY NAME_MAP_PID";
	private static final String SELECTMAX = "SELECT MAX(NAME_MAP_PID) FROM PUBLIC.NAME_MAPS";
	private static final String INSERT = "INSERT INTO PUBLIC.NAME_MAPS( NAME_MAP_PID, NAME_STYLE_PID, "
			+ "PART_NO, LABEL, TABLE_ID) VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.NAME_MAPS SET NAME_STYLE_PID = ?, PART_NO = ?, "
			+ "LABEL = ?, TABLE_ID = ? WHERE NAME_MAP_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.NAME_MAPS WHERE NAME_MAP_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.NAME_MAPS";

	private List<NameMaps> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int NameMapPid;
	private int NameStylePid;
	private int PartNo;
	private String Label;
	private int TableId;
	private NameMaps model;

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

	public List<NameMaps> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameMaps();
			model.setNameMapPid(rs.getInt("NAME_MAP_PID"));
			model.setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLabel(rs.getString("LABEL"));
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
			setNameMapPid(rs.getInt("NAME_MAP_PID"));
			setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			setPartNo(rs.getInt("PART_NO"));
			setLabel(rs.getString("LABEL"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<NameMaps> getFKNameStylePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_NAME_STYLE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameMaps();
			model.setNameMapPid(rs.getInt("NAME_MAP_PID"));
			model.setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLabel(rs.getString("LABEL"));
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
	 * Get the NameMapPid field.
	 *
	 * @return Contents of the NAME_MAP_PID column
	 */
	public int getNameMapPid() {
		return NameMapPid;
	}

	/**
	 * Get the NameStylePid field.
	 *
	 * @return Contents of the NAME_STYLE_PID column
	 */
	public int getNameStylePid() {
		return NameStylePid;
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
		ps.setInt(2, getNameStylePid());
		ps.setInt(3, getPartNo());
		ps.setString(4, getLabel());
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
	 * Set the NameMapPid field
	 *
	 * @param NameMapPid Contents of the NAME_MAP_PID column
	 */
	public void setNameMapPid(int NameMapPid) {
		this.NameMapPid = NameMapPid;
	}

	/**
	 * Set the NameStylePid field
	 *
	 * @param NameStylePid Contents of the NAME_STYLE_PID column
	 */
	public void setNameStylePid(int NameStylePid) {
		this.NameStylePid = NameStylePid;
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
		ps.setInt(1, getNameStylePid());
		ps.setInt(2, getPartNo());
		ps.setString(3, getLabel());
		ps.setInt(4, getTableId());
		ps.setInt(5, getNameMapPid());
		ps.executeUpdate();
		conn.close();
	}

}
