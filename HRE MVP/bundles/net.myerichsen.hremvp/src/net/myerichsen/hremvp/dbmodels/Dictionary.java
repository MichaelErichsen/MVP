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
 * The persistent class for the DICTIONARY database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 21. feb. 2019
 *
 */

public class Dictionary {
	private static final String SELECT = "SELECT DICTIONARY_PID, "
			+ "LABEL_PID, ISO_CODE, LABEL, "
			+ "TABLE_ID FROM PUBLIC.DICTIONARY WHERE DICTIONARY_PID = ?";
	private static final String SELECT_ISO_CODE = "SELECT DICTIONARY_PID, "
			+ "LABEL_PID, ISO_CODE, LABEL, "
			+ "TABLE_ID FROM PUBLIC.DICTIONARY WHERE ISO_CODE = ? ORDER BY DICTIONARY_PID";
	private static final String SELECTALL = "SELECT DICTIONARY_PID, "
			+ "LABEL_PID, ISO_CODE, LABEL, "
			+ "TABLE_ID FROM PUBLIC.DICTIONARY ORDER BY DICTIONARY_PID";
	private static final String SELECTMAX = "SELECT MAX(DICTIONARY_PID) FROM PUBLIC.DICTIONARY";
	private static final String INSERT = "INSERT INTO PUBLIC.DICTIONARY( "
			+ "DICTIONARY_PID, LABEL_PID, ISO_CODE, LABEL, "
			+ "TABLE_ID) VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.DICTIONARY SET "
			+ "LABEL_PID = ?, ISO_CODE = ?, LABEL = ?, "
			+ "TABLE_ID = ? WHERE DICTIONARY_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.DICTIONARY WHERE DICTIONARY_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.DICTIONARY";

	private static final String SELECT_LABEL_PID = "SELECT DICTIONARY_PID, LABEL_PID, ISO_CODE, LABEL, TABLE_ID "
			+ "FROM PUBLIC.DICTIONARY "
			+ "WHERE LABEL_PID = ? ORDER BY ISO_CODE";

	private static final String SELECTMAXLABELPID = "SELECT MAX(LABEL_PID) FROM PUBLIC.DICTIONARY";

	private List<Dictionary> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int DictionaryPid;
	private int LabelPid;
	private String IsoCode;
	private String Label;
	private int TableId;
	private Dictionary model;

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

	public List<Dictionary> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Dictionary();
			model.setDictionaryPid(rs.getInt("DICTIONARY_PID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setIsoCode(rs.getString("ISO_CODE"));
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
			setDictionaryPid(rs.getInt("DICTIONARY_PID"));
			setLabelPid(rs.getInt("LABEL_PID"));
			setIsoCode(rs.getString("ISO_CODE"));
			setLabel(rs.getString("LABEL"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	/**
	 * Get the DictionaryPid field.
	 *
	 * @return Contents of the DICTIONARY_PID column
	 */
	public int getDictionaryPid() {
		return DictionaryPid;
	}

	public List<Dictionary> getFKIsoCode(String key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_ISO_CODE);
		ps.setString(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Dictionary();
			model.setDictionaryPid(rs.getInt("DICTIONARY_PID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setIsoCode(rs.getString("ISO_CODE"));
			model.setLabel(rs.getString("LABEL"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<Dictionary> getFKLabelPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LABEL_PID);
		ps.setLong(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new Dictionary();
			model.setDictionaryPid(rs.getInt("DICTIONARY_PID"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setIsoCode(rs.getString("ISO_CODE"));
			model.setLabel(rs.getString("LABEL"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	/**
	 * Get the IsoCode field.
	 *
	 * @return Contents of the ISO_CODE column
	 */
	public String getIsoCode() {
		return IsoCode;
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
	 * Get the LabelPid field.
	 *
	 * @return Contents of the LABEL_PID column
	 */
	public int getLabelPid() {
		return LabelPid;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getNextLabelPid() throws SQLException {
		int maxLabelPid = 0;
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTMAXLABELPID);
		rs = ps.executeQuery();
		if (rs.next()) {
			maxLabelPid = rs.getInt(1);
		}
		maxLabelPid++;

		return maxLabelPid;
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
		ps.setInt(2, getLabelPid());
		ps.setString(3, getIsoCode());
		ps.setString(4, getLabel());
		ps.setInt(5, getTableId());
		ps.executeUpdate();
		conn.close();
		return maxPid;
	}

	/**
	 * Set the DictionaryPid field
	 *
	 * @param DictionaryPid Contents of the DICTIONARY_PID column
	 */
	public void setDictionaryPid(int DictionaryPid) {
		this.DictionaryPid = DictionaryPid;
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
	 * Set the Label field
	 *
	 * @param Label Contents of the LABEL column
	 */
	public void setLabel(String Label) {
		this.Label = Label;
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
		ps.setInt(1, getLabelPid());
		ps.setString(2, getIsoCode());
		ps.setString(3, getLabel());
		ps.setInt(4, getTableId());
		ps.setInt(5, getDictionaryPid());
		ps.executeUpdate();
		conn.close();
	}
}
