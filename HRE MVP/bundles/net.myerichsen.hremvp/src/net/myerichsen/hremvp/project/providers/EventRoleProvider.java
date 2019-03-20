package net.myerichsen.hremvp.project.providers;

import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.EventRoleServer;

/**
 * Provides all data for an personEvent Role
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 4. mar. 2019
 *
 */
public class EventRoleProvider implements IHREProvider {
	private int EventRolePid;
	private String Abbreviation;
	private int TableId;
	private final EventRoleServer server;

	/**
	 * Constructor
	 *
	 */
	public EventRoleProvider() {
		server = new EventRoleServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws Exception {
		server.delete(key);
	}

//	/**
//	 * Get all rows
//	 *
//	 * @return A list of lists of strings with pids and labels
//	 * @throws Exception An exception that provides information on a database
//	 *                      access error or other errors
//	 * @throws MvpException Application specific exception
//	 */
//	@Override
//	public List<List<String>> get() throws Exception {
//		return server.get();
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	public List<?> get() throws Exception {
		return server.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws Exception {
		server.get(key);
		setEventRolePid(server.getEventRolePid());
		setAbbreviation(server.getAbbreviation());
	}

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return Abbreviation;
	}

	/**
	 * @return the eventRolePid
	 */
	public int getEventRolePid() {
		return EventRolePid;
	}

	/**
	 * @return
	 */
	public int getLabelPid() {
		return server.getLabelPid();
	}

//	/**
//	 * @param key
//	 * @return
//	 * @throws MvpException
//	 * @throws Exception
//	 */
//	public List<List<String>> getNameList(int key)
//			throws Exception {
//		return server.getNameList(key);
//	}

	/**
	 * @return stringList A list of lists of event Role pids, label pids,
	 *         abbreviations and generic labels
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		return server.getStringList();
	}

	/**
	 * @param labelPid
	 * @return stringList A list of lists of pid, abbreviation and label in the
	 *         active language
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList(int labelPid) throws Exception {
		return server.getStringList(labelPid);
	}

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return TableId;
	}

	/**
	 * Insert a row
	 *
	 * @return int The persistent ID of the inserted row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws Exception {
		server.setAbbreviation(Abbreviation);
		return server.insert();
	}

	/**
	 * @param abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		Abbreviation = abbreviation;
	}

	/**
	 * @param eventRolePid the eventRolePid to set
	 */
	public void setEventRolePid(int eventRolePid) {
		EventRolePid = eventRolePid;
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(int tableId) {
		TableId = tableId;
	}

	/**
	 * Update a row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws Exception {
		server.setEventRolePid(EventRolePid);
		server.setAbbreviation(Abbreviation);
		server.update();
	}

}
