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
 * The persistent class for the NAME_TYPES database table.
 *
 * @version 2018-09-17
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2018
 *
 */

public class NameTypes {
	private static final String SELECT = "SELECT NAME_TYPE_PID, LABEL, ISOCODE, NAME_PID, "
			+ "LANGUAGE_PID FROM PUBLIC.NAME_TYPES WHERE NAME_TYPE_PID = ?";
	private static final String SELECT_NAME_PID = "SELECT NAME_TYPE_PID, LABEL, ISOCODE, NAME_PID, "
			+ "LANGUAGE_PID FROM PUBLIC.NAME_TYPES WHERE NAME_PID = ? ORDER BY NAME_TYPE_PID";
	private static final String SELECT_LANGUAGE_PID = "SELECT NAME_TYPE_PID, LABEL, ISOCODE, "
			+ "NAME_PID, LANGUAGE_PID FROM PUBLIC.NAME_TYPES WHERE LANGUAGE_PID = ? ORDER BY NAME_TYPE_PID";
	private static final String SELECTALL = "SELECT NAME_TYPE_PID, LABEL, ISOCODE, NAME_PID, "
			+ "LANGUAGE_PID FROM PUBLIC.NAME_TYPES ORDER BY NAME_TYPE_PID";
	private static final String INSERT = "INSERT INTO PUBLIC.NAME_TYPES( NAME_TYPE_PID, LABEL, ISOCODE, "
			+ "NAME_PID, LANGUAGE_PID) VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.NAME_TYPES SET LABEL = ?, ISOCODE = ?, "
			+ "NAME_PID = ?, LANGUAGE_PID = ? WHERE NAME_TYPE_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.NAME_TYPES WHERE NAME_TYPE_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.NAME_TYPES";

	private List<NameTypes> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int NameTypePid;
	private String Label;
	private String Isocode;
	private int NamePid;
	private int LanguagePid;
	private NameTypes model;

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

	public List<NameTypes> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameTypes();
			model.setNameTypePid(rs.getInt("NAME_TYPE_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setIsocode(rs.getString("ISOCODE"));
			model.setNamePid(rs.getInt("NAME_PID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
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
			setNameTypePid(rs.getInt("NAME_TYPE_PID"));
			setLabel(rs.getString("LABEL"));
			setIsocode(rs.getString("ISOCODE"));
			setNamePid(rs.getInt("NAME_PID"));
			setLanguagePid(rs.getInt("LANGUAGE_PID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<NameTypes> getFKLanguagePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LANGUAGE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameTypes();
			model.setNameTypePid(rs.getInt("NAME_TYPE_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setIsocode(rs.getString("ISOCODE"));
			model.setNamePid(rs.getInt("NAME_PID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<NameTypes> getFKNamePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_NAME_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new NameTypes();
			model.setNameTypePid(rs.getInt("NAME_TYPE_PID"));
			model.setLabel(rs.getString("LABEL"));
			model.setIsocode(rs.getString("ISOCODE"));
			model.setNamePid(rs.getInt("NAME_PID"));
			model.setLanguagePid(rs.getInt("LANGUAGE_PID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	/**
	 * Get the Isocode field.
	 *
	 * @return Contents of the ISOCODE column
	 */
	public String getIsocode() {
		return Isocode;
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
	 * Get the NamePid field.
	 *
	 * @return Contents of the NAME_PID column
	 */
	public int getNamePid() {
		return NamePid;
	}

	/**
	 * Get the NameTypePid field.
	 *
	 * @return Contents of the NAME_TYPE_PID column
	 */
	public int getNameTypePid() {
		return NameTypePid;
	}

	public void insert() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(INSERT);
		ps.setInt(1, getNameTypePid());
		ps.setString(2, getLabel());
		ps.setString(3, getIsocode());
		ps.setInt(4, getNamePid());
		ps.setInt(5, getLanguagePid());
		ps.executeUpdate();
		conn.close();
	}

	/**
	 * Set the Isocode field
	 *
	 * @param Isocode Contents of the ISOCODE column
	 */
	public void setIsocode(String Isocode) {
		this.Isocode = Isocode;
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
	 * Set the NamePid field
	 *
	 * @param NamePid Contents of the NAME_PID column
	 */
	public void setNamePid(int NamePid) {
		this.NamePid = NamePid;
	}

	/**
	 * Set the NameTypePid field
	 *
	 * @param NameTypePid Contents of the NAME_TYPE_PID column
	 */
	public void setNameTypePid(int NameTypePid) {
		this.NameTypePid = NameTypePid;
	}

	public void update() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(UPDATE);
		ps.setString(1, getLabel());
		ps.setString(2, getIsocode());
		ps.setInt(3, getNamePid());
		ps.setInt(4, getLanguagePid());
		ps.setInt(5, getNameTypePid());
		ps.executeUpdate();
		conn.close();
	}

}
