package net.myerichsen.hremvp.person.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonEvents;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 22. apr. 2019
 *
 */
public class PersonEventServer implements IHREServer {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int PersonEventPid;
	private int EventPid;
	private int PersonPid;
	private int EventRolePid;
	private boolean PrimaryPerson;
	private boolean PrimaryEvent;

	PersonEvents personEvent;

	/**
	 * Constructor
	 *
	 */
	public PersonEventServer() {
		personEvent = new PersonEvents();
	}

	/**
	 * @param key
	 * @throws MvpException
	 * @throws Exception
	 */
	@Override
	public void delete(int key) throws Exception {
		personEvent.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws Exception {

	}

	/**
	 * @return the eventPid
	 */
	public int getEventPid() {
		return EventPid;
	}

	/**
	 * @return the eventRolePid
	 */
	public int getEventRolePid() {
		return EventRolePid;
	}

	/**
	 * @return the personEventPid
	 */
	public int getPersonEventPid() {
		return PersonEventPid;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return PersonPid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		PersonServer ls;

		if (key == 0) {
			return lls;
		}

		final List<String> personStringList = new ArrayList<>();

		final PersonEvents personEvents = new PersonEvents();
		final List<PersonEvents> peList = personEvents.getFKEventPid(key);

		try {
			for (final PersonEvents pe : peList) {
				ls = new PersonServer();
				PersonPid = pe.getPersonPid();
				ls.get(PersonPid);
				personStringList.add(Integer.toString(PersonPid));
				personStringList.add(ls.getPrimaryName());
			}
		} catch (final MvpException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}

		lls.add(personStringList);
		return lls;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert() throws Exception {
		personEvent.setEventPid(EventPid);
		personEvent.setPersonPid(PersonPid);
		personEvent.setPrimaryEvent(isPrimaryEvent());
		personEvent.setPrimaryPerson(isPrimaryPerson());
		personEvent.setEventRolePid(EventRolePid);
		return personEvent.insert();
	}

	/**
	 * @return the primaryEvent
	 */
	public boolean isPrimaryEvent() {
		return PrimaryEvent;
	}

	/**
	 * @return the primaryPerson
	 */
	public boolean isPrimaryPerson() {
		return PrimaryPerson;
	}

	/**
	 * @param eventPid the eventPid to set
	 */
	public void setEventPid(int eventPid) {
		EventPid = eventPid;
	}

	/**
	 * @param eventRolePid the eventRolePid to set
	 */
	public void setEventRolePid(int eventRolePid) {
		EventRolePid = eventRolePid;
	}

	/**
	 * @param personEventPid the personEventPid to set
	 */
	public void setPersonEventPid(int personEventPid) {
		PersonEventPid = personEventPid;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		PersonPid = personPid;
	}

	/**
	 * @param primaryEvent the primaryEvent to set
	 */
	public void setPrimaryEvent(boolean primaryEvent) {
		PrimaryEvent = primaryEvent;
	}

	/**
	 * @param primaryPerson the primaryPerson to set
	 */
	public void setPrimaryPerson(boolean primaryPerson) {
		PrimaryPerson = primaryPerson;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#update()
	 */
	@Override
	public void update() throws Exception {

	}

}
