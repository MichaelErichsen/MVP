package net.myerichsen.hremvp.person.providers;

import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.person.servers.PersonEventServer;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. jan. 2019
 *
 */
public class PersonEventProvider implements IHREProvider {
	private int PersonEventPid;
	private int EventPid;
	private int PersonPid;
	private String Role;
	private boolean PrimaryPerson;
	private boolean PrimaryEvent;

	PersonEventServer server;

	/**
	 * Constructor
	 *
	 */
	public PersonEventProvider() {
		server = new PersonEventServer();
	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 *
	 */
	public boolean areMoreEvents(int key) throws Exception {
		return server.areMoreEvents(key);
	}

	/**
	 * @param personEventPid2
	 * @throws MvpException
	 * @throws Exception
	 */
	@Override
	public void delete(int key) throws Exception {
		server.delete(key);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
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
	 * @return the role
	 */
	public String getRole() {
		return Role;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		return server.getStringList(key);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert() throws Exception {
		server.setEventPid(EventPid);
		server.setPersonPid(PersonPid);
		server.setPrimaryEvent(isPrimaryEvent());
		server.setPrimaryPerson(isPrimaryPerson());
		server.setRole(Role);

		return server.insert();
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
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws Exception {

	}
}
