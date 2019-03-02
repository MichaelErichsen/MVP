package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonNameMaps;
import net.myerichsen.hremvp.project.servers.PersonNameMapServer;

/**
 * Provides all data for a single map view part
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 2. mar. 2019
 *
 */
public class PersonNameMapProvider implements IHREProvider {
	// private static Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int nameMapPid;
	private String label;
	private int partNo;
	private int nameStylePid;
	private String styleLabel;

	private final PersonNameMapServer server;

	/**
	 * Constructor
	 *
	 */
	public PersonNameMapProvider() {
		server = new PersonNameMapServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	/**
	 * @param personNameStylePid
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void deletePersonNameStylePid(int personNameStylePid)
			throws SQLException, MvpException {
		server.deletePersonNameStylePid(personNameStylePid);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		return null;
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);

		setLabel(server.getLabel());
		setNameMapPid(server.getNameMapPid());
		setNameStylePid(server.getNameStylePid());
		setPartNo(server.getPartNo());
		setStyleLabel(server.getStyleLabel());
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public List<PersonNameMaps> getFKNameStylePid(int key) throws SQLException {
		return server.getFKNameStylePid(key);
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
	 * @param personNameStylePid
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getStringList(int personNameStylePid)
			throws SQLException, MvpException {
		return server.getStringList(personNameStylePid);
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
	@Override
	public int insert() throws SQLException {
		server.setLabel(label);
		server.setNameMapPid(nameMapPid);
		server.setNameStylePid(nameStylePid);
		server.setPartNo(partNo);
		return server.insert();
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
	@Override
	public void update() throws SQLException {
		server.setLabel(label);
		server.setNameMapPid(nameMapPid);
		server.setNameStylePid(nameStylePid);
		server.setPartNo(partNo);
		server.update();
	}

}
