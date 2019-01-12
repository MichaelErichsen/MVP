package net.myerichsen.hremvp.person.servers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONStringer;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.Names;
import net.myerichsen.hremvp.dbmodels.Parents;
import net.myerichsen.hremvp.dbmodels.Partners;
import net.myerichsen.hremvp.dbmodels.PersonEvents;
import net.myerichsen.hremvp.dbmodels.Persons;
import net.myerichsen.hremvp.dbmodels.SexTypes;
import net.myerichsen.hremvp.dbmodels.Sexes;
import net.myerichsen.hremvp.serverlogic.NameServer;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Persons}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 9. jan. 2019
 *
 */
//Use LocalDate
public class PersonServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int personPid;
	private int birthDatePid;
	private int deathDatePid;
	private List<List<String>> nameList;
	private List<List<String>> sexesList;
	private List<List<String>> parentList;
	private List<List<String>> partnerList;
	private final List<List<String>> childrenList;
	private List<List<String>> personEventList;
	private List<List<String>> eventList;
	private List<List<String>> siblingList;

	private final Persons person;

	/**
	 * Constructor
	 *
	 */
	public PersonServer() {
		person = new Persons();
		nameList = new ArrayList<>();
		sexesList = new ArrayList<>();
		parentList = new ArrayList<>();
		partnerList = new ArrayList<>();
		childrenList = new ArrayList<>();
		eventList = new ArrayList<>();
		siblingList = new ArrayList<>();
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
		person.delete(key);
	}

	/**
	 * Delete a row
	 *
	 * @param target Target
	 */
	public void deleteRemote(String target) {
		// TODO Auto-generated method stub

	}

	/**
	 * Get all rows
	 *
	 * @return A list of lists of strings of pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<List<String>> get() throws SQLException, MvpException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		final List<Persons> lnsl = person.get();

		List<Names> ln;
		Names name;
		final NameServer ns = new NameServer();
		for (final Persons person : lnsl) {
			// Get all names of each person
			ln = new Names().getFKPersonPid(person.getPersonPid());

			nameList = new ArrayList<>();

			// For each name get pid, name string and primary flag
			for (int i = 0; i < ln.size(); i++) {
				stringList = new ArrayList<>();
				name = ln.get(i);
				if (name.isPrimaryName()) {
					stringList.add(Integer.toString(name.getNamePid()));
					ns.get(name.getNamePid());
					stringList.add(ns.getNameStrings()[i]);
					lls.add(stringList);
					break;
				}
			}

		}

		// FIXME Add events

		return lls;
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
		person.get(key);
		setBirthDatePid(person.getBirthDatePid());
		setPersonPid(key);
		setDeathDatePid(person.getDeathDatePid());

		// Get all names of the person
		final List<Names> ln = new Names().getFKPersonPid(key);
		Names name;

		final NameServer ns = new NameServer();
		nameList = new ArrayList<>();

		List<String> ls;

		// For each name get pid, name string and primary flag
		for (int i = 0; i < ln.size(); i++) {
			ls = new ArrayList<>();
			name = ln.get(i);
			ls.add(Integer.toString(name.getNamePid()));
			ns.get(name.getNamePid());
			ls.add(ns.getNameStrings()[i]);
			ls.add(Boolean.toString(name.isPrimaryName()));

			nameList.add(ls);
		}

		SexTypes st;
		sexesList.clear();

		for (final Sexes sex : new Sexes().getFKPersonPid(key)) {
			ls = new ArrayList<>();
			st = new SexTypes();
			st.get(sex.getSexTypePid());
			ls.add(Integer.toString(sex.getSexTypePid()));
			ls.add(st.getLabel());
			ls.add(Boolean.toString(sex.isPrimarySex()));

			sexesList.add(ls);
		}

		parentList.clear();

		for (final Parents parent : new Parents().getFKChild(key)) {
			ls = new ArrayList<>();
			ls.add(Integer.toString(parent.getParent()));
			ls.add(ns.getPrimaryNameString(parent.getParent()));
			ls.add(parent.getParentRole());
			ls.add(Boolean.toString(parent.isPrimaryParent()));

			parentList.add(ls);
		}

// FIXME Add siblings
		siblingList.clear();

		final List<Partners> lpa = new Partners().getFKPartner1(key);
		lpa.addAll(new Partners().getFKPartner2(key));
		partnerList.clear();

		for (final Partners partner : lpa) {
			ls = new ArrayList<>();

			if (partner.getPartner1() == key) {
				ls.add(Integer.toString(partner.getPartner2()));
				ls.add(ns.getPrimaryNameString(partner.getPartner2()));
			} else {
				ls.add(Integer.toString(partner.getPartner1()));
				ls.add(ns.getPrimaryNameString(partner.getPartner1()));
			}

			ls.add(partner.getRole());
			ls.add(Boolean.toString(partner.isPrimaryPartner()));

			partnerList.add(ls);
		}

		childrenList.clear();

		for (final Parents parent : new Parents().getFKParent(key)) {
			ls = new ArrayList<>();
			final int pid = parent.getChild();
			ls.add(Integer.toString(pid));
			ls.add(ns.getPrimaryNameString(pid));

			childrenList.add(ls);
		}

		Events event;
		EventNames eventName;
		eventList.clear();

		for (final PersonEvents personEvent : new PersonEvents().getFKPersonPid(key)) {
			event = new Events();
			event.get(personEvent.getEventPid());
			eventName = new EventNames();
			eventName.get(event.getEventNamePid());

			ls = new ArrayList<>();
			ls.add(Integer.toString(event.getEventPid()));
			ls.add(eventName.getLabel());
			ls.add(personEvent.getRole());
			ls.add(Integer.toString(event.getFromDatePid()));
			ls.add(Integer.toString(event.getToDatePid()));

			eventList.add(ls);
		}
	}

	/**
	 * @return the birthDatePid
	 */
	public int getBirthDatePid() {
		return birthDatePid;
	}

	/**
	 * @return the list of children
	 */
	public List<List<String>> getChildrenList() {
		return childrenList;
	}

	/**
	 * @return the deathDatePid
	 */
	public int getDeathDatePid() {
		return deathDatePid;
	}

	/**
	 * @return the eventList
	 */
	public List<List<String>> getEventList() {
		return eventList;
	}

	/**
	 * @return the nameList
	 */
	public List<List<String>> getNameList() {
		return nameList;
	}

	/**
	 * @return the parentList
	 */
	public List<List<String>> getParentList() {
		return parentList;
	}

	/**
	 * @return the partnerList
	 */
	public List<List<String>> getPartnerList() {
		return partnerList;
	}

	/**
	 * @return the personEventList
	 */
	public List<List<String>> getPersonEventList() {
		return personEventList;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * Get a row
	 *
	 * @param response Response
	 * @param target   Target
	 * @return List of strings
	 * @throws NumberFormatException Thrown to indicate that the application has
	 *                               attempted to converta string to one of the
	 *                               numeric types, but that the string does nothave
	 *                               the appropriate format.
	 * @throws SQLException          An exception that provides information on a
	 *                               database access error or other errors
	 * @throws MvpException          Application specific exception
	 * @throws IOException           Signals that an I/O exception of some sort has
	 *                               occurred. Thisclass is the general class of
	 *                               exceptions produced by failed orinterrupted I/O
	 *                               operations
	 * @throws JSONException         The JSONException is thrown by the JSON.org
	 *                               classes when things are amiss
	 */
	public String getRemote(HttpServletResponse response, String target) throws Exception {
		final String[] targetParts = target.split("/");
		final int targetSize = targetParts.length;

		get(Integer.parseInt(targetParts[targetSize - 1]));

		final JSONStringer js = new JSONStringer();
		js.object();
		js.key("personPid");
		js.value(personPid);
		js.key("birthDatePid");
		js.value(birthDatePid);
		js.key("deathDatePid");
		js.value(deathDatePid);

		js.key("nameList");
		js.array();

		for (final List<String> list : nameList) {
			js.object();
			js.key("namePid");
			js.value(list.get(0));
			js.key("nameString");
			js.value(list.get(1));
			js.key("primaryName");
			js.value(list.get(2));
			js.endObject();
		}

		js.endArray();

		js.key("sexTypeList");
		js.array();

		for (final List<String> list : sexesList) {
			js.object();
			js.key("sexTypePid");
			js.value(list.get(0));
			js.key("sexTypeLabel");
			js.value(list.get(1));
			js.key("primarySex");
			js.value(list.get(2));
			js.endObject();
		}

		js.endArray();

		js.key("parentList");
		js.array();

		for (final List<String> list : parentList) {
			js.object();
			js.key("namePid");
			js.value(list.get(0));
			js.key("nameString");
			js.value(list.get(1));
			js.key("role");
			js.value(list.get(2));
			js.key("primaryParent");
			js.value(list.get(3));
			js.endObject();
		}

		js.endArray();

		js.key("partnerList");
		js.array();

		for (final List<String> list : partnerList) {
			js.object();
			js.key("namePid");
			js.value(list.get(0));
			js.key("nameString");
			js.value(list.get(1));
			js.key("role");
			js.value(list.get(2));
			js.key("primaryPartner");
			js.value(list.get(3));
			js.endObject();
		}

		js.endArray();

		js.key("eventList");
		js.array();

		for (final List<String> list : eventList) {
			js.object();
			js.key("eventPid");
			js.value(list.get(0));
			js.key("label");
			js.value(list.get(1));
			js.key("role");
			js.value(list.get(2));
			js.key("fromDate");
			js.value(list.get(3));
			js.key("toDate");
			js.value(list.get(4));
			js.endObject();
		}

		js.endArray();
		js.endObject();

		LOGGER.fine(js.toString());

		return js.toString();
	}

	/**
	 * @return the sexesList
	 */
	public List<List<String>> getSexesList() {
		return sexesList;
	}

	/**
	 * @return the siblingList
	 */
	public List<List<String>> getSiblingList() {
		return siblingList;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void insert() throws SQLException, MvpException {
		person.setBirthDatePid(birthDatePid);
		person.setDeathDatePid(deathDatePid);
		person.setPersonPid(personPid);
		person.insert();
	}

	/**
	 * Insert a row
	 *
	 * @param request Request
	 */
	public void insertRemote(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param i the birthDatePid to set
	 */
	public void setBirthDatePid(int i) {
		birthDatePid = i;
	}

	/**
	 * @param childrenList the childrenList to set
	 */
	public void setChildrenList(List<List<String>> childrenList) {
	}

	/**
	 * @param i the deathDatePid to set
	 */
	public void setDeathDatePid(int i) {
		deathDatePid = i;
	}

	/**
	 * @param eventList the eventList to set
	 */
	public void setEventList(List<List<String>> eventList) {
		this.eventList = eventList;
	}

	/**
	 * @param nameList the nameList to set
	 */
	public void setNameList(List<List<String>> nameList) {
		this.nameList = nameList;
	}

	/**
	 * @param parentList the parentList to set
	 */
	public void setParentList(List<List<String>> parentList) {
		this.parentList = parentList;
	}

	/**
	 * @param partnerList the partnerList to set
	 */
	public void setPartnerList(List<List<String>> partnerList) {
		this.partnerList = partnerList;
	}

	/**
	 * @param personEventList the personEventList to set
	 */
	public void setPersonEventList(List<List<String>> personEventList) {
		this.personEventList = personEventList;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

	/**
	 * @param sexesList the sexesList to set
	 */
	public void setSexesList(List<List<String>> sexesList) {
		this.sexesList = sexesList;
	}

	/**
	 * @param siblingList the siblingList to set
	 */
	public void setSiblingList(List<List<String>> siblingList) {
		this.siblingList = siblingList;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		person.setBirthDatePid(birthDatePid);
		person.setDeathDatePid(deathDatePid);
		person.setPersonPid(personPid);
		person.update();
	}

	/**
	 * Update a row
	 *
	 * @param request Request
	 */
	public void updateRemote(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
