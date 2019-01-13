package net.myerichsen.hremvp.person.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.NameMaps;
import net.myerichsen.hremvp.dbmodels.NameStyles;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.NameMaps}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. jan. 2019
 *
 */
public class PersonNameMapServer {
	// private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int nameMapPid;
	private String label;
	private int partNo;
	private int nameStylePid;
	private String styleLabel;

	private final NameMaps map;
	private final NameStyles style;

	/**
	 * Constructor
	 *
	 */
	public PersonNameMapServer() {
		map = new NameMaps();
		style = new NameStyles();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void delete(int key) throws SQLException, MvpException {
		map.delete(key);
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void get(int key) throws SQLException, MvpException {
		map.get(key);
		setLabel(map.getLabel());
		setNameMapPid(map.getNameMapPid());
		setNameStylePid(map.getNameStylePid());
		setPartNo(map.getPartNo());

		style.get(getNameStylePid());
		setStyleLabel(style.getLabel());
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public List<NameMaps> getFKNameStylePid(int key) throws SQLException {
		return map.getFKNameStylePid(key);
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the nameMapPid
	 */
	public int getNameMapPid() {
		return nameMapPid;
	}

	/**
	 * @return the nameStylePid
	 */
	public int getNameStylePid() {
		return nameStylePid;
	}

	/**
	 * @return the partNo
	 */
	public int getPartNo() {
		return partNo;
	}

	/**
	 * @return the styleLabel
	 */
	public String getStyleLabel() {
		return styleLabel;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 *
	 */
	public void insert() throws SQLException {
		map.setLabel(label);
		map.setNameMapPid(nameMapPid);
		map.setNameStylePid(nameStylePid);
		map.setPartNo(partNo);
		map.insert();
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param nameMapPid the nameMapPid to set
	 */
	public void setNameMapPid(int nameMapPid) {
		this.nameMapPid = nameMapPid;
	}

	/**
	 * @param nameStylePid the nameStylePid to set
	 */
	public void setNameStylePid(int nameStylePid) {
		this.nameStylePid = nameStylePid;
	}

	/**
	 * @param partNo the partNo to set
	 */
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}

	/**
	 * @param styleLabel the styleLabel to set
	 */
	public void setStyleLabel(String styleLabel) {
		this.styleLabel = styleLabel;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 *
	 */
	public void update() throws SQLException {
		map.setLabel(label);
		map.setNameMapPid(nameMapPid);
		map.setNameStylePid(nameStylePid);
		map.setPartNo(partNo);
		map.update();
	}

}
