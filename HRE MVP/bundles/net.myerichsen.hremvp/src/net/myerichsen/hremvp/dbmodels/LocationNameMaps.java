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
 * The persistent class for the LOCATION_NAME_MAPS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. nov. 2018
 *
 */

public class LocationNameMaps {
	private static final String SELECT = "SELECT LOCATION_NAME_MAP_PID, LABEL, PART_NO, "
			+ "LOCATION_NAME_STYLE_PID, LABEL_POSITION, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_MAPS WHERE LOCATION_NAME_MAP_PID = ?";
	private static final String SELECT_LOCATION_NAME_STYLE_PID = "SELECT LOCATION_NAME_MAP_PID, LABEL, "
			+ "PART_NO, LOCATION_NAME_STYLE_PID, LABEL_POSITION, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_MAPS WHERE LOCATION_NAME_STYLE_PID = ? ORDER BY LOCATION_NAME_MAP_PID";
	private static final String SELECTALL = "SELECT LOCATION_NAME_MAP_PID, LABEL, PART_NO, "
			+ "LOCATION_NAME_STYLE_PID, LABEL_POSITION, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_MAPS ORDER BY LOCATION_NAME_MAP_PID";
	private static final String SELECTMAX = "SELECT MAX(LOCATION_NAME_MAP_PID) FROM PUBLIC.LOCATION_NAME_MAPS";
	private static final String INSERT = "INSERT INTO PUBLIC.LOCATION_NAME_MAPS( LOCATION_NAME_MAP_PID, "
			+ "LABEL, PART_NO, LOCATION_NAME_STYLE_PID, LABEL_POSITION, TABLE_ID) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.LOCATION_NAME_MAPS SET LABEL = ?, PART_NO = ?, "
			+ "LOCATION_NAME_STYLE_PID = ?, LABEL_POSITION = ?, TABLE_ID = ? WHERE LOCATION_NAME_MAP_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.LOCATION_NAME_MAPS WHERE LOCATION_NAME_MAP_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.LOCATION_NAME_MAPS";

	private List<LocationNameMaps> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int LocationNameMapPid;
	private String Label;
	private int PartNo;
	private int LocationNameStylePid;
	private String LabelPosition;
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
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationNameMaps();
			model.setLocationNameMapPid(rs.getInt("LOCATION_NAME_MAP_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			model.setLabelPosition(rs.getString("LABEL_POSITION"));
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
			setLabel(rs.getString("LABEL"));
			setPartNo(rs.getInt("PART_NO"));
			setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			setLabelPosition(rs.getString("LABEL_POSITION"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<LocationNameMaps> getFKLocationNameStylePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LOCATION_NAME_STYLE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationNameMaps();
			model.setLocationNameMapPid(rs.getInt("LOCATION_NAME_MAP_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			model.setLabelPosition(rs.getString("LABEL_POSITION"));
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
	 * Get the LabelPosition field.
	 *
	 * @return Contents of the LABEL_POSITION column
	 */
	public String getLabelPosition() {
		return LabelPosition;
	}

	/**
	 * Get the LocationNameMapPid field.
	 *
	 * @return Contents of the LOCATION_NAME_MAP_PID column
	 */
	public int getLocationNameMapPid() {
		return LocationNameMapPid;
	}

	/**
	 * Get the LocationNameStylePid field.
	 *
	 * @return Contents of the LOCATION_NAME_STYLE_PID column
	 */
	public int getLocationNameStylePid() {
		return LocationNameStylePid;
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
		ps.setString(2, getLabel());
		ps.setInt(3, getPartNo());
		ps.setInt(4, getLocationNameStylePid());
		ps.setString(5, getLabelPosition());
		ps.setInt(6, getTableId());
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
	 * Set the LabelPosition field
	 *
	 * @param LabelPosition Contents of the LABEL_POSITION column
	 */
	public void setLabelPosition(String LabelPosition) {
		this.LabelPosition = LabelPosition;
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
	 * @param LocationNameStylePid Contents of the LOCATION_NAME_STYLE_PID column
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
		ps.setString(1, getLabel());
		ps.setInt(2, getPartNo());
		ps.setInt(3, getLocationNameStylePid());
		ps.setString(4, getLabelPosition());
		ps.setInt(5, getTableId());
		ps.setInt(6, getLocationNameMapPid());
		ps.executeUpdate();
		conn.close();
	}

}
