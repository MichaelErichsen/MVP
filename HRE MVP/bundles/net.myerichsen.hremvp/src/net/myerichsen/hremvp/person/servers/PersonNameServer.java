package net.myerichsen.hremvp.person.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonNameParts;
import net.myerichsen.hremvp.dbmodels.PersonNameStyles;
import net.myerichsen.hremvp.dbmodels.PersonNames;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Names}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. feb. 2019
 *
 */
//Use LocalDate
public class PersonNameServer implements IHREServer {
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

	private PersonNames name;

	/**
	 * Constructor
	 *
	 */
	public PersonNameServer() {
		name = new PersonNames();
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
	@Override
	public void delete(int key) throws SQLException, MvpException {
		final PersonNameParts part = new PersonNameParts();
		part.getFKNamePid(key);

		for (final PersonNameParts np : part.getFKNamePid(key)) {
			np.delete(np.getNamePartPid());
		}

		name.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		return null;
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
	@Override
	public void get(int key) throws SQLException, MvpException {
		name.get(key);
		setNamePid(key);
		setPersonPid(name.getPersonPid());
		setFromDatePid(name.getFromDatePid());
		setToDatePid(name.getToDatePid());
		setPrimaryName(name.isPrimaryName());
		setNameStylePid(name.getNameStylePid());

		final PersonNameStyles ns = new PersonNameStyles();
		ns.get(name.getNameStylePid());
		setNameTypeLabel("ns.getLabelPid()");

//		PersonNameMaps map;
		PersonNameParts part;
//		List<PersonNameMaps> mapList;
		List<PersonNameParts> partList;
		List<String> ls;

		nameList.clear();

//		map = new PersonNameMaps();
//		mapList = map.getFKNameStylePid(name.getNameStylePid());

		part = new PersonNameParts();
		partList = part.getFKNamePid(key);

		for (int i = 0; i < partList.size(); i++) {
			ls = new ArrayList<>();
			ls.add(Integer.toString(partList.get(i).getNamePartPid()));
			ls.add("mapList.get(i).getLabelPid()");
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
		List<PersonNames> nameList = new ArrayList<>();
		nameList = new PersonNames().getFKPersonPid(personPid);

		final String[] sa = new String[nameList.size()];

		for (int i = 0; i < nameList.size(); i++) {
			sb = new StringBuilder();
			name = nameList.get(i);
			LOGGER.fine("Name " + name.getNamePid() + ", person "
					+ name.getPersonPid());
			final List<PersonNameParts> npl = new PersonNameParts()
					.getFKNamePid(name.getNamePid());

			LOGGER.fine("List size " + npl.size());
			// Concatenate non-null name parts
			for (final PersonNameParts PersonNameParts : npl) {
				LOGGER.fine("Name part " + PersonNameParts.getNamePartPid()
						+ ", name " + PersonNameParts.getNamePid());
				if (PersonNameParts.getNamePid() == name.getNamePid()) {
					if (PersonNameParts.getLabel() != null) {
						sb.append(PersonNameParts.getLabel().trim() + " ");
						LOGGER.fine("Part " + PersonNameParts.getLabel());
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
		PersonNames name;

		List<PersonNames> nameList = new ArrayList<>();
		nameList = new PersonNames().getFKPersonPid(personPid);

		for (int i = 0; i < nameList.size(); i++) {
			name = nameList.get(i);

			if (name.isPrimaryName()) {
				final List<PersonNameParts> npl = new PersonNameParts()
						.getFKNamePid(name.getNamePid());

				// Concatenate non-null name parts
				for (final PersonNameParts PersonNameParts : npl) {
					if (PersonNameParts.getNamePid() == name.getNamePid()) {
						if (PersonNameParts.getLabel() != null) {
							sb.append(PersonNameParts.getLabel() + " ");
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
	 * @return
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		name.setNamePid(namePid);
		name.setPersonPid(personPid);
		name.setFromDatePid(fromDatePid);
		name.setToDatePid(toDatePid);
		name.setPrimaryName(primaryName);
		name.setNameStylePid(nameStylePid);
		return name.insert();
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
	@Override
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
