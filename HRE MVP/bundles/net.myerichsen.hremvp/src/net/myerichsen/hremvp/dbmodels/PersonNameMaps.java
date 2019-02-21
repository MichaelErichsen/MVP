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
 * The persistent class for the PERSON_NAME_MAPS database table
 *
 * @author H2ModelGenerator, &copy; History Research Environment Ltd., 2019
 * @version 19. feb. 2019
 *
 */

public class PersonNameMaps {
	private static final String SELECT = "SELECT NAME_MAP_PID, "
			+ "NAME_STYLE_PID, PART_NO, LABEL_PID, "
			+ "TABLE_ID FROM PUBLIC.PERSON_NAME_MAPS WHERE NAME_MAP_PID = ?";
	private static final String SELECT_NAME_STYLE_PID = "SELECT "
			+ "NAME_MAP_PID, NAME_STYLE_PID, PART_NO, LABEL_PID, "
			+ "TABLE_ID FROM PUBLIC.PERSON_NAME_MAPS WHERE NAME_STYLE_PID = ? ORDER BY NAME_MAP_PID";
	private static final String SELECT_LABEL_PID = "SELECT NAME_MAP_PID, "
			+ "NAME_STYLE_PID, PART_NO, LABEL_PID, "
			+ "TABLE_ID FROM PUBLIC.PERSON_NAME_MAPS WHERE LABEL_PID = ? ORDER BY NAME_MAP_PID";
	private static final String SELECTALL = "SELECT NAME_MAP_PID, "
			+ "NAME_STYLE_PID, PART_NO, LABEL_PID, "
			+ "TABLE_ID FROM PUBLIC.PERSON_NAME_MAPS ORDER BY NAME_MAP_PID";
	private static final String SELECTMAX = "SELECT MAX(NAME_MAP_PID) FROM PUBLIC.PERSON_NAME_MAPS";

	private static final String INSERT = "INSERT INTO PUBLIC.PERSON_NAME_MAPS( "
			+ "NAME_MAP_PID, NAME_STYLE_PID, PART_NO, "
			+ "LABEL_PID, TABLE_ID) VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE PUBLIC.PERSON_NAME_MAPS SET "
			+ "NAME_STYLE_PID = ?, PART_NO = ?, LABEL_PID = ?, "
			+ "TABLE_ID = ? WHERE NAME_MAP_PID = ?";

	private static final String DELETE = "DELETE FROM PUBLIC.PERSON_NAME_MAPS WHERE NAME_MAP_PID = ?";

	private static final String DELETEALL = "DELETE FROM PUBLIC.PERSON_NAME_MAPS";

	private List<PersonNameMaps> modelList;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection conn;

	private int NameMapPid;
	private int NameStylePid;
	private int PartNo;
	private int LabelPid;
	private int TableId;
	private PersonNameMaps model;

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

	public List<PersonNameMaps> get() throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECTALL);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonNameMaps();
			model.setNameMapPid(rs.getInt("NAME_MAP_PID"));
			model.setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
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
			setNameMapPid(rs.getInt("NAME_MAP_PID"));
			setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			setPartNo(rs.getInt("PART_NO"));
			setLabelPid(rs.getInt("LABEL_PID"));
			setTableId(rs.getInt("TABLE_ID"));
		} else {
			throw new MvpException("ID " + key + " not found");
		}
		conn.close();
	}

	public List<PersonNameMaps> getFKLabelPid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_LABEL_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonNameMaps();
			model.setNameMapPid(rs.getInt("NAME_MAP_PID"));
			model.setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
			modelList.add(model);
		}
		conn.close();
		return modelList;
	}

	public List<PersonNameMaps> getFKNameStylePid(int key) throws SQLException {
		conn = HreH2ConnectionPool.getConnection();
		ps = conn.prepareStatement(SELECT_NAME_STYLE_PID);
		ps.setInt(1, key);
		rs = ps.executeQuery();
		modelList = new ArrayList<>();
		while (rs.next()) {
			model = new PersonNameMaps();
			model.setNameMapPid(rs.getInt("NAME_MAP_PID"));
			model.setNameStylePid(rs.getInt("NAME_STYLE_PID"));
			model.setPartNo(rs.getInt("PART_NO"));
			model.setLabelPid(rs.getInt("LABEL_PID"));
			model.setTableId(rs.getInt("TABLE_ID"));
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
	 * Get the NameMapPid field.
	 *
	 * @return Contents of the NAME_MAP_PID column
	 */
	public int getNameMapPid() {
		return NameMapPid;
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
		ps.setInt(2, getNameStylePid());
		ps.setInt(3, getPartNo());
		ps.setInt(4, getLabelPid());
		ps.setInt(5, getTableId());
		ps.executeUpdate();
		conn.close();
		return maxPid;
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
	 * Set the NameMapPid field
	 *
	 * @param NameMapPid Contents of the NAME_MAP_PID column
	 */
	public void setNameMapPid(int NameMapPid) {
		this.NameMapPid = NameMapPid;
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
		ps.setInt(1, getNameStylePid());
		ps.setInt(2, getPartNo());
		ps.setInt(3, getLabelPid());
		ps.setInt(4, getTableId());
		ps.setInt(5, getNameMapPid());
		ps.executeUpdate();
		conn.close();
	}

}
