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
 * @version 22. feb. 2019
 *
 */
public class EventTypeProvider implements IHREProvider {
	private int EventTypePid;
	private int TableId;
	private int LabelPid;
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
		setLabelPid(server.getLabelPid());
		setTableId(server.getTableId());
	}

	/**
	 * @param labelPid
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> getEventTypeList(int labelPid)
			throws SQLException {
		return server.getEventTypeList(labelPid);
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
		server.setLabelPid(LabelPid);
		return server.insert();
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
		server.setLabelPid(LabelPid);
		server.update();
	}

}
