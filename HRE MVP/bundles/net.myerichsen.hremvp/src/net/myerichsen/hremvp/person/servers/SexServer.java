package net.myerichsen.hremvp.person.servers;

import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.SexTypes;
import net.myerichsen.hremvp.dbmodels.Sexes;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Sexes}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 4. mar. 2019
 *
 */
//Use LocalDate
public class SexServer implements IHREServer {
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

	private final Sexes sex;
	private final SexTypes sexType;
	private Dictionary dictionary;

	/**
	 * Constructor
	 *
	 */
	public SexServer() {
		sex = new Sexes();
		sexType = new SexTypes();
		dictionary = new Dictionary();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void delete(int key) throws Exception {
		sex.delete(key);
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void get(int key) throws Exception {
		sex.get(key);
		setSexesPid(sex.getSexesPid());
		setPersonPid(sex.getPersonPid());
		setSexTypePid(sex.getSexTypePid());
		setFromDatePid(sex.getFromDatePid());
		setToDatePid(sex.getToDatePid());
		setPrimarySex(sex.isPrimarySex());

		sexType.get(sex.getSexTypePid());
		setAbbreviation(sexType.getAbbreviation());
		final int labelPid = sexType.getLabelPid();
		final List<Dictionary> fkLabelPid = new Dictionary()
				.getFKLabelPid(labelPid);
		dictionary = fkLabelPid.get(0);

		setSexTypeLabel(dictionary.getLabel());
		setIsocode(dictionary.getIsoCode());
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

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		return null;
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
	 * @return
	 *
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws Exception {
		sex.setSexesPid(sexesPid);
		sex.setPersonPid(personPid);
		sex.setSexTypePid(sexTypePid);
		sex.setFromDatePid(fromDatePid);
		sex.setToDatePid(toDatePid);
		sex.setPrimarySex(primarySex);
		return sex.insert();
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
	 * @param data.fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromdate) {
		fromDatePid = fromdate;
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
	 * @param data.toDatePid the toDatePid to set
	 */
	public void setToDatePid(int todate) {
		toDatePid = todate;
	}

	/**
	 * Update a row
	 *
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws Exception {
		sex.setSexesPid(sexesPid);
		sex.setPersonPid(personPid);
		sex.setSexTypePid(sexTypePid);
		sex.setFromDatePid(fromDatePid);
		sex.setToDatePid(toDatePid);
		sex.setPrimarySex(primarySex);
		sex.update();
	}
}
