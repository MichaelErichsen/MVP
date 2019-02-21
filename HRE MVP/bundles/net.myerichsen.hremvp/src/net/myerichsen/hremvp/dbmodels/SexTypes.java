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
 * The persistent class for the SEX_TYPES database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 21. feb. 2019
 *
 */

public class SexTypes {
	private static final String SELECT = "SELECT SEX_TYPE_PID, "
			+ "ABBREVIATION, TABLE_ID, "
			+ "LABEL_PID FROM PUBLIC.SEX_TYPES WHERE SEX_TYPE_PID = ?";
	private static final String SELECT_LABEL_PID = "SELECT SEX_TYPE_PID, "
			+ "ABBREVIATION, TABLE_ID, "
			+ "LABEL_PID FROM PUBLIC.SEX_TYPES WHERE LABEL_PID = ? ORDER BY SEX_TYPE_PID";
	private static final String SELECTALL = "SELECT SEX_TYPE_PID, "
			+ "ABBREVIATION, TABLE_ID, "
			+ "LABEL_PID FROM PUBLIC.SEX_TYPES ORDER BY SEX_TYPE_PID";
	private static final String SELECTMAX = "SELECT MAX(SEX_TYPE_PID) FROM PUBLIC.SEX_TYPES";
	private static final String INSERT = "INSERT INTO PUBLIC.SEX_TYPES( "
			+ "SEX_TYPE_PID, ABBREVIATION, TABLE_ID, "
			+ "LABEL_PID) VALUES (?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.SEX_TYPES SET "
			+ "ABBREVIATION = ?, TABLE_ID = ?, "
			+ "LABEL_PID = ? WHERE SEX_TYPE_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.SEX_TYPES WHERE SEX_TYPE_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.SEX_TYPES";

	private List<SexTypes> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int SexTypePid;
	private String Abbreviation;
	private int TableId;
	private int LabelPid;
	private SexTypes model;

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

	public List<SexTypes> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new SexTypes();
			model.setSexTypePid(rs.getInt("SEX_TYPE_PID"));
			model.setAbbreviation(rs.getString("ABBREVIATION"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
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
			setSexTypePid(rs.getInt("SEX_TYPE_PID"));
			setAbbreviation(rs.getString("ABBREVIATION"));
			setTableId(rs.getInt("TABLE_ID"));
			setLabelPid(rs.getInt("LABEL_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	/**
	 * Get the Abbreviation field.
	 *
	 * @return Contents of the ABBREVIATION column
	 */
	public String getAbbreviation() {
		return Abbreviation;
	}

	public List<SexTypes> getFKLabelPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LABEL_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new SexTypes();
			model.setSexTypePid(rs.getInt("SEX_TYPE_PID"));
			model.setAbbreviation(rs.getString("ABBREVIATION"));
			model.setTableId(rs.getInt("TABLE_ID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
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
	 * Get the SexTypePid field.
	 *
	 * @return Contents of the SEX_TYPE_PID column
	 */
	public int getSexTypePid() {
		return SexTypePid;
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
		ps.setString(2, getAbbreviation());
		ps.setInt(3, getTableId());
		ps.setInt(4, getLabelPid());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	/**
	 * Set the Abbreviation field
	 *
	 * @param Abbreviation Contents of the ABBREVIATION column
	 */
	public void setAbbreviation(String Abbreviation) {
		this.Abbreviation = Abbreviation;
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
	 * Set the SexTypePid field
	 *
	 * @param SexTypePid Contents of the SEX_TYPE_PID column
	 */
	public void setSexTypePid(int SexTypePid) {
		this.SexTypePid = SexTypePid;
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
		ps.setString(1, getAbbreviation());
		ps.setInt(2, getTableId());
		ps.setInt(3, getLabelPid());
		ps.setInt(4, getSexTypePid());
		ps.executeUpdate();
		conn.close();
	}

}
