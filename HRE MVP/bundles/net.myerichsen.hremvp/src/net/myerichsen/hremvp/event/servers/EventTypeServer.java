package net.myerichsen.hremvp.event.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.EventTypes;
import net.myerichsen.hremvp.dbmodels.Languages;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Events}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 23. feb. 2019
 *
 */
public class EventTypeServer implements IHREServer {
	// private final static Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int EventTypePid;
	private String abbreviation;
	private int TableId = 17;

	private final EventTypes eventType;

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
	@Override
	public void delete(int key) throws SQLException, MvpException {
		eventType.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<EventTypes> get() throws SQLException {
		return eventType.get();
	}

//	/**
//	 * Get all rows
//	 *
//	 * @return A list of lists of strings of pids and labels
//	 * @throws SQLException An exception that provides information on a database
//	 *                      access error or other errors
//	 * @throws MvpException Application specific exception
//	 */
//	@Override
//	public List<List<String>> get() throws SQLException, MvpException {
//		final List<List<String>> lls = new ArrayList<>();
//		List<String> stringList;
//
//		final List<EventTypes> eventTypeList = eventType.get();
//
//		for (final EventTypes type : eventTypeList) {
//			stringList = new ArrayList<>();
//			stringList.add(Integer.toString(type.getEventTypePid()));
//			// FIXME
//			stringList.add("type.getLabel()");
//			lls.add(stringList);
//		}
//
//		return lls;
//	}

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
		eventType.get(key);
		setEventTypePid(eventType.getEventTypePid());
		setAbbreviation(eventType.getAbbreviation());
	}

	/**
	 * @return
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return stringList A list of lists of event type pids, label pids,
	 *         abbreviations and generic labels
	 * @throws SQLException
	 */
	public List<List<String>> getEventTypeList() throws SQLException {
		List<String> stringList;
		List<Dictionary> fkLabelPid;
		String label = "";

		final IPreferenceStore store = new ScopedPreferenceStore(
				InstanceScope.INSTANCE, "net.myerichsen.hremvp");
		final String guiLanguage = store.getString("GUILANGUAGE");
		final List<List<String>> lls = new ArrayList<>();
		final Dictionary dictionary = new Dictionary();

		final List<EventTypes> list = eventType.get();

		for (final EventTypes st : list) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(st.getEventTypePid()));
			stringList.add(Integer.toString(st.getLabelPid()));
			stringList.add(st.getAbbreviation());

			fkLabelPid = dictionary.getFKLabelPid(st.getLabelPid());

			for (final Dictionary d : fkLabelPid) {
				if (guiLanguage.equals(d.getIsoCode())) {
					label = d.getLabel();
				}
			}

			stringList.add(label);
			lls.add(stringList);
		}
		return lls;
	}

	/**
	 * @param labelPid
	 * @param abbreviation
	 * @return stringList A list of lists of event type pids, label pids, iso
	 *         codes and generic labels
	 * @throws SQLException
	 */
	public List<List<String>> getEventTypeList(int labelPid)
			throws SQLException {
		final List<List<String>> lls = new ArrayList<>();

		if (labelPid == 0) {
			return lls;
		}

		String eventTypePidString = "";
		final List<EventTypes> list = eventType.get();

		for (final EventTypes eventTypes : list) {
			if (eventTypes.getLabelPid() == labelPid) {
				eventTypePidString = Integer
						.toString(eventTypes.getEventTypePid());
			}
		}

		List<String> stringList;
		String label = "";

		final Dictionary dictionary = new Dictionary();
		final List<Dictionary> fkLabelPid = dictionary.getFKLabelPid(labelPid);

		final Languages language = new Languages();

		for (final Languages l : language.get()) {
			stringList = new ArrayList<>();
			stringList.add(eventTypePidString);
			stringList.add(Integer.toString(labelPid));
			stringList.add(l.getIsocode());

			for (final Dictionary d : fkLabelPid) {
				if (l.getIsocode().equals(d.getIsoCode())) {
					label = d.getLabel();
					break;
				}
			}

			stringList.add(label);
			lls.add(stringList);
		}
		return lls;
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
		return eventType.getLabelPid();
	}

//	/**
//	 * @param key
//	 * @return
//	 * @throws SQLException
//	 * @throws MvpException
//	 */
//	public List<List<String>> getNameList(int key)
//			throws SQLException, MvpException {
//		List<String> stringList;
//
//		final List<List<String>> lls = new ArrayList<>();
//		final EventNames names = new EventNames();
//		final List<EventNames> eventNameList = names.getFKEventTypePid(key);
//		new Languages();
//
//		for (final EventNames eventNames : eventNameList) {
//			stringList = new ArrayList<>();
//			stringList.add(Integer.toString(eventNames.getEventTypePid()));
//			stringList.add(eventNames.getLabel());
//
////			language.get(eventNames.getLanguagePid());
////			stringList.add(language.getLabel());
////			stringList.add(language.getIsocode());
//			lls.add(stringList);
//		}
//
//		return lls;
//	}

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
		eventType.setAbbreviation(abbreviation);
		final Dictionary dictionary = new Dictionary();
		eventType.setLabelPid(dictionary.getNextLabelPid());
		eventType.setTableId(TableId);
		return eventType.insert();
	}

	/**
	 * @param abbreviation the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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
		eventType.setEventTypePid(EventTypePid);
		eventType.setTableId(TableId);
		eventType.update();
	}

}
