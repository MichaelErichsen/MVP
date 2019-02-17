package net.myerichsen.hremvp.person.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.NameParts;
import net.myerichsen.hremvp.person.servers.PersonNamePartServer;

/**
 * Provides all data for a single name part
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 5. feb. 2019
 *
 */
public class PersonNamePartProvider implements IHREProvider {
	// private static Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int namePartPid;
	private int namePid;
	private String name;
	private String mapLabel;
	private String label;
	private int partNo;

	private final PersonNamePartServer server;

	/**
	 * Constructor
	 *
	 */
	public PersonNamePartProvider() {
		server = new PersonNamePartServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
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
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setLabel(server.getLabel());
		setNamePartPid(server.getNamePartPid());
		setNamePid(server.getNamePid());
		setMapLabel(server.getMapLabel());
		setName(server.getName());
		setPartNo(server.getPartNo());
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public List<NameParts> getFKNamePid(int key) throws SQLException {
		return server.getFKNamePid(key);
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the mapLabel
	 */
	public String getMapLabel() {
		return mapLabel;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the namePartPid
	 */
	public int getNamePartPid() {
		return namePartPid;
	}

	/**
	 * @return the namePid
	 */
	public int getNamePid() {
		return namePid;
	}

	/**
	 * @return the partNo
	 */
	public int getPartNo() {
		return partNo;
	}

	/**
	 * Insert a row
	 *
	 * @return
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		server.setLabel(label);
		server.setName(name);
		server.setNamePartPid(namePartPid);
		server.setNamePid(namePid);
		server.setMapLabel(mapLabel);
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
	 * @param mapLabel the mapLabel to set
	 */
	public void setMapLabel(String mapLabel) {
		this.mapLabel = mapLabel;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param namePartPid the namePartPid to set
	 */
	public void setNamePartPid(int namePartPid) {
		this.namePartPid = namePartPid;
	}

	/**
	 * @param namePid the namePid to set
	 */
	public void setNamePid(int namePid) {
		this.namePid = namePid;
	}

	/**
	 * @param partNo the partNo to set
	 */
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setLabel(label);
		server.setName(name);
		server.setNamePartPid(namePartPid);
		server.setNamePid(namePid);
		server.setMapLabel(mapLabel);
		server.setPartNo(partNo);
		server.update();
	}
}
