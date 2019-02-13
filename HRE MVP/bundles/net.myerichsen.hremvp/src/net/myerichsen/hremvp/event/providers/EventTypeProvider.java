package net.myerichsen.hremvp.event.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.event.servers.EventTypeServer;

/**
 * Provides all data for an personEvent type
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. dec. 2018
 *
 */
public class EventTypeProvider implements IHREProvider {
	private int EventTypePid;
	private int TableId;
	private String Label;
	private final EventTypeServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public EventTypeProvider() throws Exception {
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

	/**
	 * Get all rows
	 *
	 * @return A list of lists of strings with pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public List<List<String>> get() throws SQLException, MvpException {
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
		setLabel(server.getLabel());
		setTableId(server.getTableId());
	}

	/**
	 * @return the eventTypePid
	 */
	public int getEventTypePid() {
		return EventTypePid;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * @param key
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getNameList(int key)
			throws SQLException, MvpException {
		return server.getNameList(key);
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
		server.setEventTypePid(EventTypePid);
		server.setLabel(Label);
		return server.insert();
	}

	/**
	 * @param eventTypePid the eventTypePid to set
	 */
	public void setEventTypePid(int eventTypePid) {
		EventTypePid = eventTypePid;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		Label = label;
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
		server.setLabel(Label);
		server.update();
	}

}
