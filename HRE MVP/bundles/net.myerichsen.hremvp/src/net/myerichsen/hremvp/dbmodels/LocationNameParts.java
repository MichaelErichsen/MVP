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
 * The persistent class for the LOCATION_NAME_PARTS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. nov. 2018
 *
 */

public class LocationNameParts {
	private static final String SELECT = "SELECT LOCATION_NAME_PART_PID, LOCATION_NAME_PID, LABEL, "
			+ "PART_NO, TABLE_ID FROM PUBLIC.LOCATION_NAME_PARTS WHERE LOCATION_NAME_PART_PID = ?";
	private static final String SELECT_LOCATION_NAME_PID = "SELECT LOCATION_NAME_PART_PID, "
			+ "LOCATION_NAME_PID, LABEL, PART_NO, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_PARTS WHERE LOCATION_NAME_PID = ? ORDER BY LOCATION_NAME_PART_PID";
	private static final String SELECTALL = "SELECT LOCATION_NAME_PART_PID, LOCATION_NAME_PID, LABEL, "
			+ "PART_NO, TABLE_ID FROM PUBLIC.LOCATION_NAME_PARTS ORDER BY LOCATION_NAME_PART_PID";
	private static final String SELECTMAX = "SELECT MAX(LOCATION_NAME_PART_PID) FROM PUBLIC.LOCATION_NAME_PARTS";
	private static final String INSERT = "INSERT INTO PUBLIC.LOCATION_NAME_PARTS( LOCATION_NAME_PART_PID, "
			+ "LOCATION_NAME_PID, LABEL, PART_NO, TABLE_ID) VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.LOCATION_NAME_PARTS SET LOCATION_NAME_PID = ?, "
			+ "LABEL = ?, PART_NO = ?, TABLE_ID = ? WHERE LOCATION_NAME_PART_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.LOCATION_NAME_PARTS WHERE LOCATION_NAME_PART_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.LOCATION_NAME_PARTS";

	private List<LocationNameParts> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int LocationNamePartPid;
	private int LocationNamePid;
	private String Label;
	private int PartNo;
	private int TableId;
	private LocationNameParts model;

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

	public List<LocationNameParts> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationNameParts();
			model.setLocationNamePartPid(rs.getInt("LOCATION_NAME_PART_PID"));
			model.setLocationNamePid(rs.getInt("LOCATION_NAME_PID"));
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
			setLocationNamePartPid(rs.getInt("LOCATION_NAME_PART_PID"));
			setLocationNamePid(rs.getInt("LOCATION_NAME_PID"));
			setLabel(rs.getString("LABEL"));
			setPartNo(rs.getInt("PART_NO"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<LocationNameParts> getFKLocationNamePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LOCATION_NAME_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationNameParts();
			model.setLocationNamePartPid(rs.getInt("LOCATION_NAME_PART_PID"));
			model.setLocationNamePid(rs.getInt("LOCATION_NAME_PID"));
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
	 * Get the LocationNamePartPid field.
	 *
	 * @return Contents of the LOCATION_NAME_PART_PID column
	 */
	public int getLocationNamePartPid() {
		return LocationNamePartPid;
	}

	/**
	 * Get the LocationNamePid field.
	 *
	 * @return Contents of the LOCATION_NAME_PID column
	 */
	public int getLocationNamePid() {
		return LocationNamePid;
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
		ps.setInt(2, getLocationNamePid());
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
	 * Set the LocationNamePartPid field
	 *
	 * @param LocationNamePartPid Contents of the LOCATION_NAME_PART_PID column
	 */
	public void setLocationNamePartPid(int LocationNamePartPid) {
		this.LocationNamePartPid = LocationNamePartPid;
	}

	/**
	 * Set the LocationNamePid field
	 *
	 * @param LocationNamePid Contents of the LOCATION_NAME_PID column
	 */
	public void setLocationNamePid(int LocationNamePid) {
		this.LocationNamePid = LocationNamePid;
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
		ps.setInt(1, getLocationNamePid());
		ps.setString(2, getLabel());
		ps.setInt(3, getPartNo());
		ps.setInt(4, getTableId());
		ps.setInt(5, getLocationNamePartPid());
		ps.executeUpdate();
		conn.close();
	}

}