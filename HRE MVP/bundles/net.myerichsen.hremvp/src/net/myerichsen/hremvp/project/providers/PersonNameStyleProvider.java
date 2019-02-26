package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonNameStyles;
import net.myerichsen.hremvp.project.servers.PersonNameStyleServer;

/**
 * Provide a person name style
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 26. feb. 2019
 *
 */
public class PersonNameStyleProvider implements IHREProvider {
	private int NameStylePid;
	private String IsoCode;
	private int LabelPid;

	private final PersonNameStyleServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public PersonNameStyleProvider() throws SQLException {
		server = new PersonNameStyleServer();
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

	/**
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<PersonNameStyles> get() throws SQLException {
		return server.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return IsoCode;
	}

	/**
	 * @return the labelPid
	 */
	public int getLabelPid() {
		return LabelPid;
	}

	/**
	 * @return the nameStylePid
	 */
	public int getNameStylePid() {
		return NameStylePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		server.setIsoCode(IsoCode);
		server.setLabelPid(LabelPid);
		return server.insert();
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		IsoCode = isoCode;
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
		LabelPid = labelPid;
	}

	/**
	 * @param nameStylePid the nameStylePid to set
	 */
	public void setNameStylePid(int nameStylePid) {
		NameStylePid = nameStylePid;
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
		server.setNameStylePid(NameStylePid);
		server.setIsoCode(IsoCode);
		server.setLabelPid(LabelPid);
		server.update();
	}

}