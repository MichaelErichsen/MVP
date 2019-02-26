package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonNameStyles;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.NameStyles}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 26. feb. 2019
 *
 */
public class PersonNameStyleServer implements IHREServer {
	private int NameStylePid;
	private String IsoCode;
	private int LabelPid;

	private final PersonNameStyles style;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public PersonNameStyleServer() throws SQLException {
		style = new PersonNameStyles();
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
		style.delete(key);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<PersonNameStyles> get() throws SQLException {
		return style.get();
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
	 */
	@Override
	public int insert() throws SQLException {
		style.setIsoCode(IsoCode);
		style.setLabelPid(LabelPid);
		return style.insert();
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
	 */
	@Override
	public void update() throws SQLException {
		style.setNameStylePid(NameStylePid);
		style.setIsoCode(IsoCode);
		style.setLabelPid(LabelPid);
		style.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

}
