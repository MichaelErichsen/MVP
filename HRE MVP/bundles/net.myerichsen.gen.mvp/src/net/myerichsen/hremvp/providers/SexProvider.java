package net.myerichsen.hremvp.providers;

import java.sql.SQLException;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.serverlogic.SexServer;

/**
 * Provide all data for a sex
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 14. nov. 2018
 *
 */
public class SexProvider {
	private int sexesPid;
	private int personPid;
	private int sexTypePid;
	private int fromDatePid;
	private int toDatePid;
	private boolean primarySex;
	private String sexTypeLabel;
	private String abbreviation;
	private int languagePid;
	private String languageLabel;
	private String isocode;

	private SexServer server;

	/**
	 * Constructor
	 *
	 */
	public SexProvider() {
		server = new SexServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setSexesPid(server.getSexesPid());
		setPersonPid(server.getPersonPid());
		setSexTypePid(server.getSexTypePid());
		setFromDatePid(server.getFromDatePid());
		setToDatePid(server.getToDatePid());
		setPrimarySex(server.isPrimarySex());
		setAbbreviation(server.getAbbreviation());
		setSexTypeLabel(server.getSexTypeLabel());
		setLanguageLabel(server.getLanguageLabel());
		setIsocode(server.getIsocode());
	}

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return fromDatePid;
	}

	/**
	 * @return the isocode
	 */
	public String getIsocode() {
		return isocode;
	}

	/**
	 * @return the languageLabel
	 */
	public String getLanguageLabel() {
		return languageLabel;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * @return the sexesPid
	 */
	public int getSexesPid() {
		return sexesPid;
	}

	/**
	 * @return the sexTypeLabel
	 */
	public String getSexTypeLabel() {
		return sexTypeLabel;
	}

	/**
	 * @return the sexTypePid
	 */
	public int getSexTypePid() {
		return sexTypePid;
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
		server.setSexesPid(sexesPid);
		server.setPersonPid(personPid);
		server.setSexTypePid(sexTypePid);
		server.setFromDatePid(fromDatePid);
		server.setToDatePid(toDatePid);
		server.setPrimarySex(primarySex);
		server.insert();
	}

	/**
	 * @return the primarySex
	 */
	public boolean isPrimarySex() {
		return primarySex;
	}

	/**
	 * @param abbreviation the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int i) {
		this.fromDatePid = i;
	}

	/**
	 * @param isocode the isocode to set
	 */
	public void setIsocode(String isocode) {
		this.isocode = isocode;
	}

	/**
	 * @param languageLabel the languageLabel to set
	 */
	public void setLanguageLabel(String languageLabel) {
		this.languageLabel = languageLabel;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		this.languagePid = languagePid;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

	/**
	 * @param primarySex the primarySex to set
	 */
	public void setPrimarySex(boolean primarySex) {
		this.primarySex = primarySex;
	}

	/**
	 * @param sexesPid the sexesPid to set
	 */
	public void setSexesPid(int sexesPid) {
		this.sexesPid = sexesPid;
	}

	/**
	 * @param sexTypeLabel the sexTypeLabel to set
	 */
	public void setSexTypeLabel(String sexTypeLabel) {
		this.sexTypeLabel = sexTypeLabel;
	}

	/**
	 * @param sexTypePid the sexTypePid to set
	 */
	public void setSexTypePid(int sexTypePid) {
		this.sexTypePid = sexTypePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int todate) {
		this.toDatePid = todate;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		server.setSexesPid(sexesPid);
		server.setPersonPid(personPid);
		server.setSexTypePid(sexTypePid);
		server.setFromDatePid(fromDatePid);
		server.setToDatePid(toDatePid);
		server.setPrimarySex(primarySex);
		server.update();
	}
}
