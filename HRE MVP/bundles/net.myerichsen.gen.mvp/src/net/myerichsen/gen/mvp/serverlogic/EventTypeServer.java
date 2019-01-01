package net.myerichsen.gen.mvp.serverlogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.gen.mvp.MvpException;
import net.myerichsen.gen.mvp.dbmodels.EventNames;
import net.myerichsen.gen.mvp.dbmodels.EventTypes;
import net.myerichsen.gen.mvp.dbmodels.Languages;

/**
 * Business logic interface for {@link net.myerichsen.gen.mvp.dbmodels.Events}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 27. nov. 2018
 *
 */
public class EventTypeServer {
	// private final static Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int EventTypePid;
	private int TableId;
	private String Label;

	private EventTypes eventType;

	/**
	 * Constructor
	 *
	 */
	public EventTypeServer() {
		eventType = new EventTypes();
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
		eventType.delete(key);
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
		List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		List<EventTypes> eventTypeList = eventType.get();

		for (EventTypes type : eventTypeList) {
			stringList = new ArrayList<String>();
			stringList.add(Integer.toString(type.getEventTypePid()));
			stringList.add(type.getLabel());
			lls.add(stringList);
		}

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
		eventType.get(key);
		setEventTypePid(eventType.getEventTypePid());
		setLabel(eventType.getLabel());
		setTableId(eventType.getTableId());
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
	public int insert() throws SQLException, MvpException {
		eventType.setEventTypePid(EventTypePid);
		eventType.setLabel(Label);
		eventType.setTableId(TableId);
		return eventType.insert();
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
	public void update() throws SQLException, MvpException {
		eventType.setEventTypePid(EventTypePid);
		eventType.setLabel(Label);
		eventType.setTableId(TableId);
		eventType.update();
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 * @throws MvpException
	 */
	public List<List<String>> getNameList(int key) throws SQLException, MvpException {
		List<String> stringList;

		List<List<String>> lls = new ArrayList<>();
		EventNames names = new EventNames();
		List<EventNames> eventNameList = names.getFKEventTypePid(key);
		Languages language = new Languages();

		for (EventNames eventNames : eventNameList) {
			stringList = new ArrayList<String>();
			stringList.add(Integer.toString(eventNames.getEventTypePid()));
			stringList.add(eventNames.getLabel());

			language.get(eventNames.getLanguagePid());
			stringList.add(language.getLabel());
			stringList.add(language.getIsocode());
			lls.add(stringList);
		}

		return lls;
	}

}
