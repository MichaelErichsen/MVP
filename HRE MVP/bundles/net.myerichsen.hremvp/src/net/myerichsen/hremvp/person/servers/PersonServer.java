package net.myerichsen.hremvp.person.servers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONStringer;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.Hdates;
import net.myerichsen.hremvp.dbmodels.NameParts;
import net.myerichsen.hremvp.dbmodels.Names;
import net.myerichsen.hremvp.dbmodels.Parents;
import net.myerichsen.hremvp.dbmodels.Partners;
import net.myerichsen.hremvp.dbmodels.PersonEvents;
import net.myerichsen.hremvp.dbmodels.Persons;
import net.myerichsen.hremvp.dbmodels.SexTypes;
import net.myerichsen.hremvp.dbmodels.Sexes;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Persons}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 9. feb. 2019
 *
 */
public class PersonServer implements IHREServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int personPid;
	private int birthDatePid;
	private int deathDatePid;
	private List<List<String>> nameList;
	private List<List<String>> sexesList;
	private List<List<String>> personList;
	private List<List<String>> parentList;
	private List<List<String>> partnerList;
	private final List<List<String>> childrenList;
	private List<List<String>> personEventList;
	private List<List<String>> eventList;

	private final Persons person;

	/**
	 * Constructor
	 *
	 */
	public PersonServer() {
		person = new Persons();
		nameList = new ArrayList<>();
		sexesList = new ArrayList<>();
		personList = new ArrayList<>();
		parentList = new ArrayList<>();
		partnerList = new ArrayList<>();
		childrenList = new ArrayList<>();
		eventList = new ArrayList<>();
//		siblingList = new ArrayList<>();
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
		// Delete all person_sexes
		final Sexes sex = new Sexes();

		for (final Sexes sexes : sex.getFKPersonPid(key)) {
			sex.delete(sexes.getSexesPid());
		}

		// Delete all person names
		final Names name = new Names();
		int namePid = 0;

		for (final Names names : name.getFKPersonPid(key)) {
			namePid = names.getNamePid();

			// Delete all name parts
			final NameParts part = new NameParts();

			for (final NameParts namePart : part.getFKNamePid(namePid)) {
				part.delete(namePart.getNamePartPid());
			}

			name.delete(namePid);
		}

		// Delete all person events
		final PersonEvents event = new PersonEvents();

		for (final PersonEvents events : event.getFKPersonPid(key)) {
			event.delete(events.getPersonEventPid());
		}

		// Delete all partner links
		Partners partner = new Partners();

		for (final Partners p : partner.getFKPartner1(key)) {
			partner.delete(p.getPartnerPid());
		}

		partner = new Partners();

		for (final Partners p : partner.getFKPartner2(key)) {
			partner.delete(p.getPartnerPid());
		}

		// Delete all parent links
		Parents parent = new Parents();

		for (final Parents p : parent.getFKChild(key)) {
			parent.delete(p.getParentPid());
		}

		parent = new Parents();

		for (final Parents p : parent.getFKParent(key)) {
			parent.delete(p.getParentPid());
		}

		// Delete person
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
	@Override
	public List<Persons> get() throws SQLException, MvpException {
		return person.get();
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
		person.get(key);
		setBirthDatePid(person.getBirthDatePid());
		setPersonPid(key);
		setDeathDatePid(person.getDeathDatePid());

		// Get all names of the person
		final List<Names> ln = new Names().getFKPersonPid(key);
		Names name;

		final PersonNameServer pns = new PersonNameServer();
		nameList = new ArrayList<>();

		List<String> ls;

		// For each name get pid, name string and primary flag
		for (int i = 0; i < ln.size(); i++) {
			ls = new ArrayList<>();
			name = ln.get(i);
			ls.add(Integer.toString(name.getNamePid()));
			pns.get(name.getNamePid());
			ls.add(pns.getNameStrings()[i]);
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
			ls.add(pns.getPrimaryNameString(parent.getParent()));
			ls.add(parent.getParentRole());
			ls.add(Boolean.toString(parent.isPrimaryParent()));

			parentList.add(ls);
		}

//		siblingList.clear();
//
//		final List<Parents> fkParent = new Parents().getFKChild(key);
//
//		for (final Parents parents : fkParent) {
//			LOGGER.info("Parent id: " + parents.getParentPid());
//			final List<Parents> fkChild = new Parents().getFKParent(parents.getParentPid());
//			final TreeSet<Integer> childList = new TreeSet<>();
//
//			for (final Parents parents4 : fkChild) {
//				LOGGER.info("Child id: " + parents4.getChild());
//				childList.add(parents4.getChild());
//				childList.remove(key);
//			}
//
//			for (final int child : childList) {
//				ls = new ArrayList<>();
//				ls.add(Integer.toString(child));
//				ls.add(pns.getPrimaryNameString(child));
//
//				siblingList.add(ls);
//			}
//		}

		final List<Partners> lpa = new Partners().getFKPartner1(key);
		lpa.addAll(new Partners().getFKPartner2(key));
		partnerList.clear();

		for (final Partners partner : lpa) {
			ls = new ArrayList<>();

			if (partner.getPartner1() == key) {
				ls.add(Integer.toString(partner.getPartner2()));
				ls.add(pns.getPrimaryNameString(partner.getPartner2()));
			} else {
				ls.add(Integer.toString(partner.getPartner1()));
				ls.add(pns.getPrimaryNameString(partner.getPartner1()));
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
			ls.add(pns.getPrimaryNameString(pid));

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
	 * Get all names for the person
	 *
	 * @return A list of lists of strings of pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<List<String>> getAllNames() throws SQLException, MvpException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		final List<Persons> lnsl = person.get();

		List<Names> ln;
		Names name;
		final PersonNameServer ns = new PersonNameServer();
		for (final Persons person : lnsl) {
			// Get all names of each person
			ln = new Names().getFKPersonPid(person.getPersonPid());

			nameList = new ArrayList<>();

			// For each name get pid, name string, and primary flag
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

		return lls;
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
	 * List all persons.
	 *
	 * @return the personList
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getPersonList() throws SQLException, MvpException {
		List<String> ls;
		final PersonNameServer pns = new PersonNameServer();

		personList.clear();
		final Hdates hdates = new Hdates();
		String s;

		for (final Persons person : get()) {
			ls = new ArrayList<>();
			ls.add(Integer.toString(person.getPersonPid()));
			ls.add(pns.getPrimaryNameString(person.getPersonPid()));
			s = "";
			if (person.getBirthDatePid() > 0) {
				hdates.get(person.getBirthDatePid());
				s = hdates.getDate().toString();
			}
			ls.add(s);
			s = "";
			if (person.getDeathDatePid() > 0) {
				hdates.get(person.getDeathDatePid());
				s = hdates.getDate().toString();
			}
			ls.add(s);
			personList.add(ls);
		}

		return personList;
	}

	/**
	 * Get a list of all names for the person
	 *
	 * @param key
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getPersonNameList(int key) throws SQLException, MvpException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		if (key == 0) {
			stringList = new ArrayList<>();
			stringList.add("0");
			stringList.add("");
			stringList.add("");
			stringList.add("");
			stringList.add("false");
			lls.add(stringList);
			return lls;
		}

		final Names name = new Names();
		int namePid = 0;
		StringBuilder sb;

		for (final Names names : name.getFKPersonPid(key)) {
			stringList = new ArrayList<>();

			namePid = names.getNamePid();
			stringList.add(Integer.toString(namePid));

			sb = new StringBuilder();
			final NameParts part = new NameParts();

			for (final NameParts namePart : part.getFKNamePid(namePid)) {
				sb.append(namePart.getLabel() + " ");
			}
			stringList.add(sb.toString().trim());

			final Hdates date = new Hdates();

			int datePid = names.getFromDatePid();

			if (datePid == 0) {
				stringList.add("");
			} else {
				date.get(datePid);
				stringList.add(date.getDate().toString());
			}

			datePid = names.getToDatePid();

			if (datePid == 0) {
				stringList.add("");
			} else {
				date.get(datePid);
				stringList.add(date.getDate().toString());
			}

			stringList.add(Boolean.toString(name.isPrimaryName()));

			lls.add(stringList);
		}

		return lls;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * @return The primary name
	 * @throws SQLException
	 */
	public String getPrimaryName() throws SQLException {
		final PersonNameServer pns = new PersonNameServer();
		return pns.getPrimaryNameString(personPid);
	}

	/**
	 * Get a row remotely
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
	 * @throws SQLException
	 */
	public List<List<String>> getSiblingList(int personPid) throws SQLException {
		List<List<String>> siblingList = new ArrayList<>();
		List<String> ls;

		if (personPid == 0) {
			return siblingList;
		}

		PersonNameServer pns = new PersonNameServer();
		final List<Parents> fkParent = new Parents().getFKChild(personPid);

		for (final Parents parents : fkParent) {
			LOGGER.info("Parent id: " + parents.getParentPid());
			final List<Parents> fkChild = new Parents().getFKParent(parents.getParentPid());
			final TreeSet<Integer> childList = new TreeSet<>();

			for (final Parents parents4 : fkChild) {
				LOGGER.info("Child id: " + parents4.getChild());
				childList.add(parents4.getChild());
//				childList.remove(key);
			}

			for (final int child : childList) {
				ls = new ArrayList<>();
				ls.add(Integer.toString(child));
				ls.add(pns.getPrimaryNameString(child));

				siblingList.add(ls);
			}
		}
		return siblingList;
	}

	/**
	 * Insert a row
	 *
	 * @return The pid of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		person.setBirthDatePid(birthDatePid);
		person.setDeathDatePid(deathDatePid);
		person.setPersonPid(personPid);
		return person.insert();
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
	 * @param personList the personList to set
	 */
	public void setPersonList(List<List<String>> personList) {
		this.personList = personList;
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
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws SQLException, MvpException {
		person.setBirthDatePid(birthDatePid);
		person.setDeathDatePid(deathDatePid);
		person.setPersonPid(personPid);
		person.update();
	}

	/**
	 * Update a row remotely
	 *
	 * @param request Request
	 */
	public void updateRemote(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param key
	 * @param generations
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getDescendantList(int key, int generations) throws SQLException, MvpException {
		List<List<String>> lls = new ArrayList<>();
		int parentPid;

		Persons person = new Persons();
		person.get(key);

		Parents parentRelation = new Parents();

		for (Parents parent : parentRelation.getFKParent(key)) {
			if (generations-- > 0) {
				return getDescendantList(generations, parent.getChild());
			} else {
				List<String> ls = new ArrayList<String>();
				parentPid = parent.getParent();
				ls.add(Integer.toString(parentPid));
				ls.add(Integer.toString(parent.getChild()));
				ls.add(new PersonNameServer().getPrimaryNameString(parentPid));
				lls.add(ls);
				return lls;
			}
		}

		return lls;
	}
}
