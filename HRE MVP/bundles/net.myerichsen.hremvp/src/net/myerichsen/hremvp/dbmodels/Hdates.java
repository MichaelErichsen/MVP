package net.myerichsen.hremvp.dbmodels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.MvpException;

/**
 * The persistent class for the HDATES database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 19. feb. 2019
 *
 */

public class Hdates {
	private static final String SELECT = "SELECT " + "HDATE_PID, "
			+ "TABLE_ID, " + "ORIGINAL_TEXT, " + "DATE, " + "SORT_DATE, "
			+ "SURETY FROM PUBLIC.HDATES WHERE HDATE_PID = ?";
	private static final String SELECTALL = "SELECT " + "HDATE_PID, "
			+ "TABLE_ID, " + "ORIGINAL_TEXT, " + "DATE, " + "SORT_DATE, "
			+ "SURETY FROM PUBLIC.HDATES ORDER BY HDATE_PID";
	private static final String SELECTMAX = "SELECT MAX(HDATE_PID) FROM PUBLIC.HDATES";
	private static final String INSERT = "INSERT INTO PUBLIC.HDATES( "
			+ "HDATE_PID, " + "TABLE_ID, " + "ORIGINAL_TEXT, " + "DATE, "
			+ "SORT_DATE, " + "SURETY) VALUES (" + "?, " + "?, " + "?, " + "?, "
			+ "?, " + "?)";
	private static final String UPDATE = "UPDATE PUBLIC.HDATES SET "
			+ "TABLE_ID = ?, " + "ORIGINAL_TEXT = ?, " + "DATE = ?, "
			+ "SORT_DATE = ?, " + "SURETY = ? WHERE HDATE_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.HDATES WHERE HDATE_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.HDATES";

	private List<Hdates> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int HdatePid;
	private int TableId;
	private String OriginalText;
	private LocalDate Date;
	private LocalDate SortDate;
	private String Surety;
	private Hdates model;

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

	public List<Hdates> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Hdates();
			model.setHdatePid(rs.getInt("HDATE_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setOriginalText(rs.getString("ORIGINAL_TEXT"));
			model.setDate(rs.getObject("DATE", LocalDate.class));
			model.setSortDate(rs.getObject("SORT_DATE", LocalDate.class));
			model.setSurety(rs.getString("SURETY"));
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
			setHdatePid(rs.getInt("HDATE_PID"));
			setTableId(rs.getInt("TABLE_ID"));
			setOriginalText(rs.getString("ORIGINAL_TEXT"));
			setDate(rs.getObject("DATE", LocalDate.class));
			setSortDate(rs.getObject("SORT_DATE", LocalDate.class));
			setSurety(rs.getString("SURETY"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	/**
	 * Get the Date field.
	 *
	 * @return Contents of the DATE column
	 */
	public LocalDate getDate() {
		return Date;
	}

	/**
	 * Get the HdatePid field.
	 *
	 * @return Contents of the HDATE_PID column
	 */
	public int getHdatePid() {
		return HdatePid;
	}

	/**
	 * Get the OriginalText field.
	 *
	 * @return Contents of the ORIGINAL_TEXT column
	 */
	public String getOriginalText() {
		return OriginalText;
	}

	/**
	 * Get the SortDate field.
	 *
	 * @return Contents of the SORT_DATE column
	 */
	public LocalDate getSortDate() {
		return SortDate;
	}

	/**
	 * Get the Surety field.
	 *
	 * @return Contents of the SURETY column
	 */
	public String getSurety() {
		return Surety;
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
		ps.setInt(2, getTableId());
		ps.setString(3, getOriginalText());
		ps.setObject(4, getDate());
		ps.setObject(5, getSortDate());
		ps.setString(6, getSurety());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	/**
	 * Set the Date field
	 *
	 * @param Date Contents of the DATE column
	 */
	public void setDate(LocalDate Date) {
		this.Date = Date;
	}

	/**
	 * Set the HdatePid field
	 *
	 * @param HdatePid Contents of the HDATE_PID column
	 */
	public void setHdatePid(int HdatePid) {
		this.HdatePid = HdatePid;
	}

	/**
	 * Set the OriginalText field
	 *
	 * @param OriginalText Contents of the ORIGINAL_TEXT column
	 */
	public void setOriginalText(String OriginalText) {
		this.OriginalText = OriginalText;
	}

	/**
	 * Set the SortDate field
	 *
	 * @param SortDate Contents of the SORT_DATE column
	 */
	public void setSortDate(LocalDate SortDate) {
		this.SortDate = SortDate;
	}

	/**
	 * Set the Surety field
	 *
	 * @param Surety Contents of the SURETY column
	 */
	public void setSurety(String Surety) {
		this.Surety = Surety;
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
		ps.setInt(1, getTableId());
		ps.setString(2, getOriginalText());
		ps.setObject(4, getDate());
		ps.setObject(5, getSortDate());
		ps.setString(3, getSurety());
		ps.setInt(6, getHdatePid());
		ps.executeUpdate();
		conn.close();
	}

}
