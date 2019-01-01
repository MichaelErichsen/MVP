package net.myerichsen.gen.mvp.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.gen.mvp.MvpException;
import net.myerichsen.gen.mvp.serverlogic.NameServer;

/**
 * Provides all data for a single name for a person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 14. nov. 2018
 *
 */
public class NameProvider {
//	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int namePid;
	private int personPid;
	private int nameType;
	private String nameTypeLabel;
	private int fromDatePid;
	private int toDatePid;
	private boolean primaryName;
	private int nameStylePid;
	List<List<String>> nameList;

	private NameServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public NameProvider() throws SQLException {
		server = new NameServer();
		nameList = new ArrayList<>();
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
		server.delete(key);
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setNamePid(key);
		setPersonPid(server.getPersonPid());
		setNameType(server.getNameType());
		setNameTypeLabel(server.getNameTypeLabel());
		setFromDatePid(server.getFromDate());
		setToDatePid(server.getToDate());
		setPrimaryName(server.isPrimaryName());
		setNameStylePid(server.getNameStylePid());
		setNameList(server.getNameList());
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the nameList
	 */
	public List<List<String>> getNameList() {
		return nameList;
	}

	/**
	 * @return the namePid
	 */
	public int getNamePid() {
		return namePid;
	}

	/**
	 * Get a string of name parts for each name
	 *
	 * @return sa A list of name strings
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String[] getNameStrings() throws SQLException {
		return server.getNameStrings();
	}

	/**
	 * @return the nameStylePid
	 */
	public int getNameStylePid() {
		return nameStylePid;
	}

	/**
	 * @return the nameType
	 */
	public int getNameType() {
		return nameType;
	}

	/**
	 * @return the nameTypeLabel
	 */
	public String getNameTypeLabel() {
		return nameTypeLabel;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * Get a string of name parts for the primary name of a person
	 *
	 * @param personPid The persistent PID of the person
	 * @return s The name
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String getPrimaryNameString(int personPid) throws SQLException {
		return server.getPrimaryNameString(personPid);
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void insert() throws SQLException, MvpException {
		server.setNamePid(namePid);
		server.setPersonPid(personPid);
		server.setNameType(nameType);
		server.setFromDatePid(fromDatePid);
		server.setFromDatePid(toDatePid);
		server.setPrimaryName(primaryName);
		server.setNameStylePid(nameStylePid);
		server.insert();
	}

	/**
	 * @return the primaryName
	 */
	public boolean isPrimaryName() {
		return primaryName;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int i) {
		this.fromDatePid = i;
	}

	/**
	 * @param nameList the nameList to set
	 */
	public void setNameList(List<List<String>> nameList) {
		this.nameList = nameList;
	}

	/**
	 * @param namePid the namePid to set
	 */
	public void setNamePid(int namePid) {
		this.namePid = namePid;
	}

	/**
	 * @param nameStylePid the nameStylePid to set
	 */
	public void setNameStylePid(int nameStylePid) {
		this.nameStylePid = nameStylePid;
	}

	/**
	 * @param nameType the nameType to set
	 */
	public void setNameType(int nameType) {
		this.nameType = nameType;
	}

	/**
	 * @param nameTypeLabel the nameTypeLabel to set
	 */
	public void setNameTypeLabel(String nameTypeLabel) {
		this.nameTypeLabel = nameTypeLabel;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

	/**
	 * @param primaryName the primaryName to set
	 */
	public void setPrimaryName(boolean primaryName) {
		this.primaryName = primaryName;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDate) {
		this.toDatePid = toDate;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		server.setNamePid(namePid);
		server.setPersonPid(personPid);
		server.setNameType(nameType);
		server.setNameTypeLabel(nameTypeLabel);
		server.setFromDatePid(fromDatePid);
		server.setFromDatePid(toDatePid);
		server.setPrimaryName(primaryName);
		server.setNameStylePid(nameStylePid);
		server.update();
	}

}
