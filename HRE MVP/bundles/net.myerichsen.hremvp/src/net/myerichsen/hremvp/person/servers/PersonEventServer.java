package net.myerichsen.hremvp.person.servers;

import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonEvents;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 24. mar. 2019
 *
 */
public class PersonEventServer implements IHREServer {
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
	 * @return
	 * @throws Exception
	 */
	public boolean areMoreEvents(int key) throws Exception {
		final List<PersonEvents> peList = personEvent.getFKEventPid(key);
		if (peList.size() > 0) {
			return true;
		}
		return false;
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

	/**
	 * @return the rolePid
	 */
	public int getRolePid() {
		return EventRolePid;
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
		// TODO Auto-generated method stub
		return null;
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

	/**
	 * @param rolePid the rolePid to set
	 */
	public void setRolePid(int rolePid) {
		EventRolePid = rolePid;
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
