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
 * The persistent class for the LOCATION_NAME_STYLES database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 24. feb. 2019
 *
 */

public class LocationNameStyles {
	private static final String SELECT = "SELECT LOCATION_NAME_STYLE_PID, "
			+ "LABEL_PID, INSERT_TSTMP, UPDATE_TSTMP, TABLE_ID, "
			+ "FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.LOCATION_NAME_STYLES WHERE LOCATION_NAME_STYLE_PID = ?";
	private static final String SELECT_FROM_DATE_PID = "SELECT "
			+ "LOCATION_NAME_STYLE_PID, LABEL_PID, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.LOCATION_NAME_STYLES WHERE FROM_DATE_PID = ? ORDER BY LOCATION_NAME_STYLE_PID";
	private static final String SELECT_TO_DATE_PID = "SELECT "
			+ "LOCATION_NAME_STYLE_PID, LABEL_PID, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.LOCATION_NAME_STYLES WHERE TO_DATE_PID = ? ORDER BY LOCATION_NAME_STYLE_PID";
	private static final String SELECTALL = "SELECT "
			+ "LOCATION_NAME_STYLE_PID, LABEL_PID, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID FROM PUBLIC.LOCATION_NAME_STYLES ORDER BY LOCATION_NAME_STYLE_PID";
	private static final String SELECTMAX = "SELECT MAX(LOCATION_NAME_STYLE_PID) FROM PUBLIC.LOCATION_NAME_STYLES";

	private static final String INSERT = "INSERT INTO PUBLIC.LOCATION_NAME_STYLES( "
			+ "LOCATION_NAME_STYLE_PID, LABEL_PID, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID, FROM_DATE_PID, "
			+ "TO_DATE_PID) VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.LOCATION_NAME_STYLES SET "
			+ "LABEL_PID = ?, INSERT_TSTMP = ?, UPDATE_TSTMP = ?, "
			+ "TABLE_ID = ?, FROM_DATE_PID = ?, "
			+ "TO_DATE_PID = ? WHERE LOCATION_NAME_STYLE_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.LOCATION_NAME_STYLES WHERE LOCATION_NAME_STYLE_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.LOCATION_NAME_STYLES";

	private List<LocationNameStyles> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int LocationNameStylePid;
	private int LabelPid;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
	private int FromDatePid;
	private int ToDatePid;
	private LocationNameStyles model;

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

	public List<LocationNameStyles> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationNameStyles();
			model.setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
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
			setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			setLabelPid(rs.getInt("LABEL_PID"));
			setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			setTableId(rs.getInt("TABLE_ID"));
			setFromDatePid(rs.getInt("FROM_DATE_PID"));
			setToDatePid(rs.getInt("TO_DATE_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<LocationNameStyles> getFKFromDatePid(int key)
			throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_FROM_DATE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationNameStyles();
			model.setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<LocationNameStyles> getFKToDatePid(int key)
			throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_TO_DATE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new LocationNameStyles();
			model.setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setInsertTstmp(rs.getTimestamp("INSERT_TSTMP"));
			model.setUpdateTstmp(rs.getTimestamp("UPDATE_TSTMP"));
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
	 * Get the InsertTstmp field.
	 *
	 * @return Contents of the INSERT_TSTMP column
	 */
	public Timestamp getInsertTstmp() {
		return InsertTstmp;
	}

	/**
	 * Get the LabelPid field.
	 *
	 * @return Contents of the LABEL_PID column
	 */
	public int getLabelPid() {
		return LabelPid;
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

	/**
	 * Get the UpdateTstmp field.
	 *
	 * @return Contents of the UPDATE_TSTMP column
	 */
	public Timestamp getUpdateTstmp() {
		return UpdateTstmp;
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
		ps.setInt(2, getLabelPid());
		ps.setTimestamp(3, getInsertTstmp());
		ps.setTimestamp(4, getUpdateTstmp());
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
		ps.executeUpdate();
		conn.close();
		return maxPid;
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
	 * Set the InsertTstmp field
	 *
	 * @param InsertTstmp Contents of the INSERT_TSTMP column
	 */
	public void setInsertTstmp(Timestamp InsertTstmp) {
		this.InsertTstmp = InsertTstmp;
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
	 * Set the LocationNameStylePid field
	 *
	 * @param LocationNameStylePid Contents of the LOCATION_NAME_STYLE_PID
	 *                             column
	 */
	public void setLocationNameStylePid(int LocationNameStylePid) {
		this.LocationNameStylePid = LocationNameStylePid;
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

	/**
	 * Set the UpdateTstmp field
	 *
	 * @param UpdateTstmp Contents of the UPDATE_TSTMP column
	 */
	public void setUpdateTstmp(Timestamp UpdateTstmp) {
		this.UpdateTstmp = UpdateTstmp;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setInt(1, getLabelPid());
		ps.setTimestamp(2, getInsertTstmp());
		ps.setTimestamp(3, getUpdateTstmp());
		ps.setInt(4, getTableId());
		if (getFromDatePid() == 0) {
			ps.setNull(5, java.sql.Types.INTEGER);
		} else {
			ps.setInt(5, getFromDatePid());
		}
		if (getToDatePid() == 0) {
			ps.setNull(6, java.sql.Types.INTEGER);
		} else {
			ps.setInt(6, getToDatePid());
		}
		ps.setInt(7, getLocationNameStylePid());
		ps.executeUpdate();
		conn.close();
	}

}
