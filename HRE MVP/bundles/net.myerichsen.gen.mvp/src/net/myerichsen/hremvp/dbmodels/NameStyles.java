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
 * The persistent class for the NAME_STYLES database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018
 * @version 20. nov. 2018
 *
 */

public class NameStyles {
	private static final String SELECT = "SELECT NAME_STYLE_PID, LABEL, LANGUAGE_PID, "
			+ "TABLE_ID FROM PUBLIC.NAME_STYLES WHERE NAME_STYLE_PID = ?";
	private static final String SELECT_LANGUAGE_PID = "SELECT NAME_STYLE_PID, LABEL, LANGUAGE_PID, "
			+ "TABLE_ID FROM PUBLIC.NAME_STYLES WHERE LANGUAGE_PID = ? ORDER BY NAME_STYLE_PID";
	private static final String SELECTALL = "SELECT NAME_STYLE_PID, LABEL, LANGUAGE_PID, "
			+ "TABLE_ID FROM PUBLIC.NAME_STYLES ORDER BY NAME_STYLE_PID";
	private static final String SELECTMAX = "SELECT MAX(NAME_STYLE_PID) FROM PUBLIC.NAME_STYLES";
	private static final String INSERT = "INSERT INTO PUBLIC.NAME_STYLES( NAME_STYLE_PID, LABEL, "
			+ "LANGUAGE_PID, TABLE_ID) VALUES (?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.NAME_STYLES SET LABEL = ?, LANGUAGE_PID = ?, "
			+ "TABLE_ID = ? WHERE NAME_STYLE_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.NAME_STYLES WHERE NAME_STYLE_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.NAME_STYLES";

	private List<NameStyles> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int NameStylePid;
	private String Label;
	private int LanguagePid;
	private int TableId;
	private NameStyles model;

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

	public List<NameStyles> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameStyles();
			model.setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
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
			setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			setLabel(rs.getString("LABEL"));
			setLanguagePid(rs.getInt("LANGUAGE_PID"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<NameStyles> getFKLanguagePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LANGUAGE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameStyles();
			model.setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
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
	 * Get the LanguagePid field.
	 *
	 * @return Contents of the LANGUAGE_PID column
	 */
	public int getLanguagePid() {
		return LanguagePid;
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
		ps.setInt(3, getLanguagePid());
		ps.setInt(4, getTableId());
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
	 * Set the LanguagePid field
	 *
	 * @param LanguagePid Contents of the LANGUAGE_PID column
	 */
	public void setLanguagePid(int LanguagePid) {
		this.LanguagePid = LanguagePid;
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
		ps.setInt(2, getLanguagePid());
		ps.setInt(3, getTableId());
		ps.setInt(4, getNameStylePid());
		ps.executeUpdate();
		conn.close();
	}

}
