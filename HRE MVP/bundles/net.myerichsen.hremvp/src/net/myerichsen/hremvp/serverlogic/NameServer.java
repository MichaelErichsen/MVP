package net.myerichsen.hremvp.serverlogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.NameMaps;
import net.myerichsen.hremvp.dbmodels.NameParts;
import net.myerichsen.hremvp.dbmodels.NameStyles;
import net.myerichsen.hremvp.dbmodels.Names;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Names}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 7. jan. 2019
 *
 */
//Use LocalDate
public class NameServer {
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int namePid;
	private int personPid;
	private int nameType;
	private String nameTypeLabel;
	private int fromDatePid;
	private int toDatePid;
	private boolean primaryName;
	private int nameStylePid;
	List<List<String>> nameList;

	private Names name;

	/**
	 * Constructor
	 *
	 */
	public NameServer() {
		name = new Names();
		nameList = new ArrayList<>();
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
		name.delete(key);
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
		name.get(key);
		setNamePid(key);
		setPersonPid(name.getPersonPid());
		setFromDatePid(name.getFromDatePid());
		setToDatePid(name.getToDatePid());
		setPrimaryName(name.isPrimaryName());
		setNameStylePid(name.getNameStylePid());

		final NameStyles ns = new NameStyles();
		ns.get(name.getNameStylePid());
		setNameTypeLabel(ns.getLabel());

		NameMaps map;
		NameParts part;
		List<NameMaps> mapList;
		List<NameParts> partList;
		List<String> ls;

		nameList.clear();

		map = new NameMaps();
		mapList = map.getFKNameStylePid(name.getNameStylePid());

		part = new NameParts();
		partList = part.getFKNamePid(key);

		for (int i = 0; i < partList.size(); i++) {
			ls = new ArrayList<>();
			ls.add(Integer.toString(partList.get(i).getNamePartPid()));
			ls.add(mapList.get(i).getLabel());
			if (partList.get(i).getLabel() != null) {
				ls.add(partList.get(i).getLabel());
			} else {
				ls.add("");
			}

			nameList.add(ls);
		}
	}

	/**
	 * @return the fromDate
	 */
	public int getFromDate() {
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
	 * @return sa An array of names
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String[] getNameStrings() throws SQLException {
		StringBuilder sb;

		final int personPid = name.getPersonPid();
		List<Names> nameList = new ArrayList<>();
		nameList = new Names().getFKPersonPid(personPid);

		final String[] sa = new String[nameList.size()];

		for (int i = 0; i < nameList.size(); i++) {
			sb = new StringBuilder();
			name = nameList.get(i);
			LOGGER.fine("Name " + name.getNamePid() + ", person " + name.getPersonPid());
			final List<NameParts> npl = new NameParts().getFKNamePid(name.getNamePid());

			LOGGER.fine("List size " + npl.size());
			// Concatenate non-null name parts
			for (final NameParts nameParts : npl) {
				LOGGER.fine("Name part " + nameParts.getNamePartPid() + ", name " + nameParts.getNamePid());
				if (nameParts.getNamePid() == name.getNamePid()) {
					if (nameParts.getLabel() != null) {
						sb.append(nameParts.getLabel().trim() + " ");
						LOGGER.fine("Part " + nameParts.getLabel());
					}
				}
			}
			sa[i] = sb.toString();
			LOGGER.fine("SB " + sb.toString());
		}

		return sa;
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
	 * Get a string of the name parts for the primary name of a person
	 *
	 * @param personPid The persistent PID of the person
	 * @return s The name
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public String getPrimaryNameString(int personPid) throws SQLException {
		final StringBuilder sb = new StringBuilder();
		Names name;

		List<Names> nameList = new ArrayList<>();
		nameList = new Names().getFKPersonPid(personPid);

		for (int i = 0; i < nameList.size(); i++) {
			name = nameList.get(i);

			if (name.isPrimaryName()) {
				final List<NameParts> npl = new NameParts().getFKNamePid(name.getNamePid());

				// Concatenate non-null name parts
				for (final NameParts nameParts : npl) {
					if (nameParts.getNamePid() == name.getNamePid()) {
						if (nameParts.getLabel() != null) {
							sb.append(nameParts.getLabel() + " ");
						}
					}
				}
				break;
			}
		}
		final String s = sb.toString().trim();
		return s;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDate() {
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
		name.setNamePid(namePid);
		name.setPersonPid(personPid);
		name.setFromDatePid(fromDatePid);
		name.setToDatePid(toDatePid);
		name.setPrimaryName(primaryName);
		name.setNameStylePid(nameStylePid);
		name.insert();
	}

	/**
	 * @return the primaryName
	 */
	public boolean isPrimaryName() {
		return primaryName;
	}

	/**
	 * @param i the fromDate to set
	 */
	public void setFromDatePid(int i) {
		fromDatePid = i;
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
		toDatePid = toDate;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		name.setNamePid(namePid);
		name.setPersonPid(personPid);
		name.setFromDatePid(fromDatePid);
		name.setToDatePid(toDatePid);
		name.setPrimaryName(primaryName);
		name.setNameStylePid(nameStylePid);
		name.update();
	}
}
