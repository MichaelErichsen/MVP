package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.EventTypeServer;

/**
 * Provides all data for an personEvent type
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 4. mar. 2019
 *
 */
public class EventTypeProvider implements IHREProvider {
	private int EventTypePid;
	private String Abbreviation;
	private int TableId;
	private final EventTypeServer server;

	/**
	 * Constructor
	 *
	 */
	public EventTypeProvider() {
		server = new EventTypeServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

//	/**
//	 * Get all rows
//	 *
//	 * @return A list of lists of strings with pids and labels
//	 * @throws SQLException An exception that provides information on a database
//	 *                      access error or other errors
//	 * @throws MvpException Application specific exception
//	 */
//	@Override
//	public List<List<String>> get() throws SQLException, MvpException {
//		return server.get();
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		return server.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setEventTypePid(server.getEventTypePid());
		setAbbreviation(server.getAbbreviation());
	}

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return Abbreviation;
	}

	/**
	 * @return the eventTypePid
	 */
	public int getEventTypePid() {
		return EventTypePid;
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
//	 * @throws SQLException
//	 */
//	public List<List<String>> getNameList(int key)
//			throws SQLException, MvpException {
//		return server.getNameList(key);
//	}

	/**
	 * @return stringList A list of lists of event type pids, label pids,
	 *         abbreviations and generic labels
	 * @throws SQLException
	 */
	public List<List<String>> getStringList() throws SQLException {
		return server.getStringList();
	}

	/**
	 * @param labelPid
	 * @return stringList A list of lists of pid, abbreviation and label in the
	 *         active language
	 * @throws SQLException
	 */
	@Override
	public List<List<String>> getStringList(int labelPid) throws SQLException {
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
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws SQLException, MvpException {
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
	 * @param eventTypePid the eventTypePid to set
	 */
	public void setEventTypePid(int eventTypePid) {
		EventTypePid = eventTypePid;
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
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setEventTypePid(EventTypePid);
		server.setAbbreviation(Abbreviation);
		server.update();
	}

}
