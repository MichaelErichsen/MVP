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
 * @version 02. mar. 2019
 *
 */

public class LocationNameStyles {
	private List<LocationNameStyles> modelList;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection conn;
	private static final String SELECT = "SELECT LOCATION_NAME_STYLE_PID, "
			+ "ISO_CODE, FROM_DATE_PID, TO_DATE_PID, LABEL_PID, "
			+ "INSERT_TSTMP, UPDATE_TSTMP, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_STYLES WHERE LOCATION_NAME_STYLE_PID = ?";

	private static final String SELECTALL = "SELECT "
			+ "LOCATION_NAME_STYLE_PID, ISO_CODE, FROM_DATE_PID, "
			+ "TO_DATE_PID, LABEL_PID, INSERT_TSTMP, UPDATE_TSTMP, "
			+ "TABLE_ID FROM PUBLIC.LOCATION_NAME_STYLES ORDER BY LOCATION_NAME_STYLE_PID";

	private static final String SELECTMAX = "SELECT MAX(LOCATION_NAME_STYLE_PID) FROM PUBLIC.LOCATION_NAME_STYLES";

	private static final String INSERT = "INSERT INTO PUBLIC.LOCATION_NAME_STYLES( "
			+ "LOCATION_NAME_STYLE_PID, ISO_CODE, FROM_DATE_PID, "
			+ "TO_DATE_PID, LABEL_PID, INSERT_TSTMP, "
			+ "UPDATE_TSTMP, TABLE_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.LOCATION_NAME_STYLES SET "
			+ "ISO_CODE = ?, FROM_DATE_PID = ?, TO_DATE_PID = ?, "
			+ "LABEL_PID = ?, INSERT_TSTMP = ?, UPDATE_TSTMP = ?, "
			+ "TABLE_ID = ? WHERE LOCATION_NAME_STYLE_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.LOCATION_NAME_STYLES WHERE LOCATION_NAME_STYLE_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.LOCATION_NAME_STYLES";

	private int LocationNameStylePid;
	private String IsoCode;
	private int FromDatePid;
	private int ToDatePid;
	private int LabelPid;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
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
		modelList = new ArrayList<LocationNameStyles>();
		while (rs.next()) {
			model = new LocationNameStyles();
			model.setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			model.setIsoCode(rs.getString("ISO_CODE"));
			model.setFromDatePid(rs.getInt("FROM_DATE_PID"));
			model.setToDatePid(rs.getInt("TO_DATE_PID"));
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
			setLocationNameStylePid(rs.getInt("LOCATION_NAME_STYLE_PID"));
			setIsoCode(rs.getString("ISO_CODE"));
			setFromDatePid(rs.getInt("FROM_DATE_PID"));
			setToDatePid(rs.getInt("TO_DATE_PID"));
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
		ps.setString(2, getIsoCode());
		if (getFromDatePid() == 0)
			ps.setNull(3, java.sql.Types.INTEGER);
		else
			ps.setInt(3, getFromDatePid());
		if (getToDatePid() == 0)
			ps.setNull(4, java.sql.Types.INTEGER);
		else
			ps.setInt(4, getToDatePid());
		ps.setInt(5, getLabelPid());
		ps.setTimestamp(6, getInsertTstmp());
		ps.setTimestamp(7, getUpdateTstmp());
		ps.setInt(8, getTableId());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setString(1, getIsoCode());
		if (getFromDatePid() == 0)
			ps.setNull(2, java.sql.Types.INTEGER);
		else
			ps.setInt(2, getFromDatePid());
		if (getToDatePid() == 0)
			ps.setNull(3, java.sql.Types.INTEGER);
		else
			ps.setInt(3, getToDatePid());
		ps.setInt(4, getLabelPid());
		ps.setTimestamp(5, getInsertTstmp());
		ps.setTimestamp(6, getUpdateTstmp());
		ps.setInt(7, getTableId());
		ps.setInt(8, getLocationNameStylePid());
		ps.executeUpdate();
		conn.close();
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
	 * Get the IsoCode field.
	 *
	 * @return Contents of the ISO_CODE column
	 */
	public String getIsoCode() {
		return this.IsoCode;
	}

	/**
	 * Get the FromDatePid field.
	 *
	 * @return Contents of the FROM_DATE_PID column
	 */
	public int getFromDatePid() {
		return this.FromDatePid;
	}

	/**
	 * Get the ToDatePid field.
	 *
	 * @return Contents of the TO_DATE_PID column
	 */
	public int getToDatePid() {
		return this.ToDatePid;
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
	 * Set the LocationNameStylePid field
	 *
	 * @param LocationNameStylePid Contents of the LOCATION_NAME_STYLE_PID
	 *                             column
	 */
	public void setLocationNameStylePid(int LocationNameStylePid) {
		this.LocationNameStylePid = LocationNameStylePid;
	}

	/**
	 * Set the IsoCode field
	 *
	 * @param IsoCode Contents of the ISO_CODE column
	 */
	public void setIsoCode(String IsoCode) {
		this.IsoCode = IsoCode;
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
	 * Set the ToDatePid field
	 *
	 * @param ToDatePid Contents of the TO_DATE_PID column
	 */
	public void setToDatePid(int ToDatePid) {
		this.ToDatePid = ToDatePid;
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
