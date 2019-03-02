package net.myerichsen.hremvp.dbmodels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.MvpException;

/**
 * The persistent class for the LOCATION_NAME_MAPS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 02. mar. 2019
 *
 */

public class LocationNameMaps {
	private List<LocationNameMaps> modelList;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection conn;
	private static final String SELECT = "SELECT LOCATION_NAME_MAP_PID, "
			+ "LOCATION_NAME_STYLE_PID, PART_NO, LABEL_PID, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_MAPS WHERE LOCATION_NAME_MAP_PID = ?";

	private static final String SELECTALL = "SELECT "
			+ "LOCATION_NAME_MAP_PID, LOCATION_NAME_STYLE_PID, "
			+ "PART_NO, LABEL_PID, INSERT_TSTMP, UPDATE_TSTMP, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_MAPS ORDER BY LOCATION_NAME_MAP_PID";

	private static final String SELECTMAX = "SELECT MAX(LOCATION_NAME_MAP_PID) FROM PUBLIC.LOCATION_NAME_MAPS";

	private static final String INSERT = "INSERT INTO PUBLIC.LOCATION_NAME_MAPS( "
			+ "LOCATION_NAME_MAP_PID, LOCATION_NAME_STYLE_PID, "
			+ "PART_NO, LABEL_PID, INSERT_TSTMP, UPDATE_TSTMP, "
			+ "TABLE_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.LOCATION_NAME_MAPS SET "
			+ "LOCATION_NAME_STYLE_PID = ?, PART_NO = ?, "
			+ "LABEL_PID = ?, INSERT_TSTMP = ?, UPDATE_TSTMP = ?, "
			+ "TABLE_ID = ? WHERE LOCATION_NAME_MAP_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.LOCATION_NAME_MAPS WHERE LOCATION_NAME_MAP_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.LOCATION_NAME_MAPS";

	private int LocationNameMapPid;
	private int LocationNameStylePid;
	private int PartNo;
	private int LabelPid;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
	private LocationNameMaps model;

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

	public List<LocationNameMaps> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<LocationNameMaps>();
		while (rs.next()) {
			model = new LocationNameMaps();
			model.setLocationNameMapPid(rs.getInt("LOCATION_NAME_MAP_PID"));
			model.setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
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
			setLocationNameMapPid(rs.getInt("LOCATION_NAME_MAP_PID"));
			setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			setPartNo(rs.getInt("PART_NO"));
			setLabelPid(rs.getInt("LABEL_PID"));
			setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
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
		ps.setInt(2, getLocationNameStylePid());
		ps.setInt(3, getPartNo());
		ps.setInt(4, getLabelPid());
		ps.setTimestamp(5, getInsertTstmp());
		ps.setTimestamp(6, getUpdateTstmp());
		ps.setInt(7, getTableId());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setInt(1, getLocationNameStylePid());
		ps.setInt(2, getPartNo());
		ps.setInt(3, getLabelPid());
		ps.setTimestamp(4, getInsertTstmp());
		ps.setTimestamp(5, getUpdateTstmp());
		ps.setInt(6, getTableId());
		ps.setInt(7, getLocationNameMapPid());
		ps.executeUpdate();
		conn.close();
	}

	/**
	 * Get the LocationNameMapPid field.
	 *
	 * @return Contents of the LOCATION_NAME_MAP_PID column
	 */
	public int getLocationNameMapPid() {
		return this.LocationNameMapPid;
	}

	/**
	 * Get the LocationNameStylePid field.
	 *
	 * @return Contents of the LOCATION_NAME_STYLE_PID column
	 */
	public int getLocationNameStylePid() {
		return this.LocationNameStylePid;
	}

	/**
	 * Get the PartNo field.
	 *
	 * @return Contents of the PART_NO column
	 */
	public int getPartNo() {
		return this.PartNo;
	}

	/**
	 * Get the LabelPid field.
	 *
	 * @return Contents of the LABEL_PID column
	 */
	public int getLabelPid() {
		return this.LabelPid;
	}

	/**
	 * Get the InsertTstmp field.
	 *
	 * @return Contents of the INSERT_TSTMP column
	 */
	public Timestamp getInsertTstmp() {
		return this.InsertTstmp;
	}

	/**
	 * Get the UpdateTstmp field.
	 *
	 * @return Contents of the UPDATE_TSTMP column
	 */
	public Timestamp getUpdateTstmp() {
		return this.UpdateTstmp;
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
	 * Set the LocationNameMapPid field
	 *
	 * @param LocationNameMapPid Contents of the LOCATION_NAME_MAP_PID column
	 */
	public void setLocationNameMapPid(int LocationNameMapPid) {
		this.LocationNameMapPid = LocationNameMapPid;
	}

	/**
	 * Set the LocationNameStylePid field
	 *
	 * @param LocationNameStylePid Contents of the LOCATION_NAME_STYLE_PID
	 *                             column
	 */
	public void setLocationNameStylePid(int LocationNameStylePid) {
		this.LocationNameStylePid = LocationNameStylePid;
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
	 * Set the LabelPid field
	 *
	 * @param LabelPid Contents of the LABEL_PID column
	 */
	public void setLabelPid(int LabelPid) {
		this.LabelPid = LabelPid;
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
	 * Set the UpdateTstmp field
	 *
	 * @param UpdateTstmp Contents of the UPDATE_TSTMP column
	 */
	public void setUpdateTstmp(Timestamp UpdateTstmp) {
		this.UpdateTstmp = UpdateTstmp;
	}

	/**
	 * Set the TableId field
	 *
	 * @param TableId Contents of the TABLE_ID column
	 */
	public void setTableId(int TableId) {
		this.TableId = TableId;
	}

}
