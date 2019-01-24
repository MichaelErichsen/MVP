package net.myerichsen.hremvp.person.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.PersonEvents;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. jan. 2019
 *
 */
public class PersonEventServer implements IHREServer {
	private int PersonEventPid;
	private int EventPid;
	private int PersonPid;
	private String Role;
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
	 * @throws SQLException
	 */
	public void delete(int key) throws SQLException, MvpException {
		personEvent.delete(key);
	}

	/**
	 * @return the eventPid
	 */
	public int getEventPid() {
		return EventPid;
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public boolean areMoreEvents(int key) throws SQLException {
		List<PersonEvents> peList = personEvent.getFKEventPid(key);
		if (peList.size() > 0) {
			return true;
		}
		return false;
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
	 * @return the role
	 */
	public String getRole() {
		return Role;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int insert() throws SQLException {
		personEvent.setEventPid(EventPid);
		personEvent.setPersonPid(PersonPid);
		personEvent.setPrimaryEvent(isPrimaryEvent());
		personEvent.setPrimaryPerson(isPrimaryPerson());
		personEvent.setRole(Role);
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
	 * @param role the role to set
	 */
	public void setRole(String role) {
		Role = role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

}
