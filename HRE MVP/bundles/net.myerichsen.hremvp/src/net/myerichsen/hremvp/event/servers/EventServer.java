package net.myerichsen.hremvp.event.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.dbmodels.EventTypes;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.Hdates;
import net.myerichsen.hremvp.dbmodels.Languages;
import net.myerichsen.hremvp.dbmodels.LocationEvents;
import net.myerichsen.hremvp.location.servers.LocationServer;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Events}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. feb. 2019
 *
 */
public class EventServer implements IHREServer {
//	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int EventPid;
	private int TableId;
	private int FromDatePid;
	private String FromDateLabel;
	private String FromDateOriginal;
	private int ToDatePid;
	private String ToDateLabel;
	private String ToDateOriginal;
	private int EventNamePid;
	private String EventName;
	private int EventTypePid;
	private String EventType;
	private int LanguagePid;
	private String Language;
	private String IsoCode;

	private List<List<String>> personList;
	private List<List<String>> locationList;

	private final Events event;

	/**
	 * Constructor
	 *
	 */
	public EventServer() {
		event = new Events();
		personList = new ArrayList<>();
		locationList = new ArrayList<>();
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
		event.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return A list of strings of pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<List<String>> get() throws SQLException, MvpException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;
		int namePid;
		int eventTypePid;
		int eventPid;

		final Events event = new Events();
		final EventNames name = new EventNames();
		final EventTypes type = new EventTypes();
		final Languages language = new Languages();

		final List<Events> eventList = event.get();

		for (final Events thisEvent : eventList) {
			stringList = new ArrayList<>();
			eventPid = thisEvent.getEventPid();
			stringList.add(Integer.toString(eventPid));

			namePid = thisEvent.getEventNamePid();
			name.get(namePid);
			stringList.add(name.getLabel());

			eventTypePid = name.getEventTypePid();
			type.get(eventTypePid);
			// FIXME Labels
			stringList.add("type.getLabel()");

			stringList.add(language.getLabel());

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
		event.get(key);
		setEventPid(event.getEventPid());

		final Hdates fromDate = new Hdates();
		setFromDatePid(event.getFromDatePid());
		try {
			fromDate.get(FromDatePid);
			setFromDateLabel(fromDate.getDate().toString());
			setFromDateOriginal(fromDate.getOriginalText());
		} catch (final Exception e) {
			setFromDateLabel("");
			setFromDateOriginal("");
		}

		final Hdates toDate = new Hdates();
		setToDatePid(event.getToDatePid());
		try {
			toDate.get(ToDatePid);
			setToDateLabel(toDate.getDate().toString());
			setToDateOriginal(toDate.getOriginalText());
		} catch (final Exception e) {
			setToDateLabel("");
			setToDateOriginal("");
		}

		final EventNames anEventName = new EventNames();
		setEventNamePid(event.getEventNamePid());
		anEventName.get(EventNamePid);
		setEventName(anEventName.getLabel());

		final Languages aLanguage = new Languages();
		aLanguage.get(LanguagePid);
		setIsoCode(aLanguage.getIsocode());
		setLanguage(aLanguage.getLabel());

		final EventTypes anEventType = new EventTypes();
		setEventTypePid(anEventName.getEventTypePid());
		anEventType.get(EventTypePid);
		// FIXME Labels
//		setEventType(anEventType.getLabel());
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return EventName;
	}

	/**
	 * @return the eventNamePid
	 */
	public int getEventNamePid() {
		return EventNamePid;
	}

	/**
	 * @return the eventPid
	 */
	public int getEventPid() {
		return EventPid;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return EventType;
	}

	/**
	 * @return the eventTypePid
	 */
	public int getEventTypePid() {
		return EventTypePid;
	}

	/**
	 * @return the fromDateLabel
	 */
	public String getFromDateLabel() {
		return FromDateLabel;
	}

	/**
	 * @return the fromDateOriginal
	 */
	public String getFromDateOriginal() {
		return FromDateOriginal;
	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return FromDatePid;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return IsoCode;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return Language;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return LanguagePid;
	}

	/**
	 * @return the locationList
	 */
	public List<List<String>> getLocationList() {
		return locationList;
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public List<String> getLocationList(int key) throws SQLException {
		LocationServer ls;

		final List<String> locationStringList = new ArrayList<>();

		final LocationEvents locationEvents = new LocationEvents();
		final List<LocationEvents> leList = locationEvents.getFKEventPid(key);

		try {
			for (final LocationEvents le : leList) {
				ls = new LocationServer();
				ls.get(le.getLocationPid());

				locationStringList.add(ls.getPrimaryName());
			}
		} catch (final MvpException e) {
			e.printStackTrace();
		}

		return locationStringList;
	}

	/**
	 * @return the personList
	 */
	public List<List<String>> getPersonList() {
		return personList;
	}

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return TableId;
	}

	/**
	 * @return the toDateLabel
	 */
	public String getToDateLabel() {
		return ToDateLabel;
	}

	/**
	 * @return the toDateOriginal
	 */
	public String getToDateOriginal() {
		return ToDateOriginal;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return ToDatePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public int insert() throws SQLException, MvpException {
		event.setEventPid(EventPid);
		event.setTableId(Constants.EVENTS_TABLE_ID);
		event.setFromDatePid(FromDatePid);
		event.setToDatePid(ToDatePid);
		event.setEventNamePid(EventNamePid);
		return event.insert();
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		EventName = eventName;
	}

	/**
	 * @param eventNamePid the eventNamePid to set
	 */
	public void setEventNamePid(int eventNamePid) {
		EventNamePid = eventNamePid;
	}

	/**
	 * @param eventPid the eventPid to set
	 */
	public void setEventPid(int eventPid) {
		EventPid = eventPid;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		EventType = eventType;
	}

	/**
	 * @param eventTypePid the eventTypePid to set
	 */
	public void setEventTypePid(int eventTypePid) {
		EventTypePid = eventTypePid;
	}

	/**
	 * @param fromDateLabel the fromDateLabel to set
	 */
	public void setFromDateLabel(String fromDateLabel) {
		FromDateLabel = fromDateLabel;
	}

	/**
	 * @param fromDateOriginal the fromDateOriginal to set
	 */
	public void setFromDateOriginal(String fromDateOriginal) {
		FromDateOriginal = fromDateOriginal;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		FromDatePid = fromDatePid;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		IsoCode = isoCode;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		Language = language;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		LanguagePid = languagePid;
	}

	/**
	 * @param locationList the locationList to set
	 */
	public void setLocationList(List<List<String>> locationList) {
		this.locationList = locationList;
	}

	/**
	 * @param personList the personList to set
	 */
	public void setPersonList(List<List<String>> personList) {
		this.personList = personList;
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(int tableId) {
		TableId = tableId;
	}

	/**
	 * @param toDateLabel the toDateLabel to set
	 */
	public void setToDateLabel(String toDateLabel) {
		ToDateLabel = toDateLabel;
	}

	/**
	 * @param toDateOriginal the toDateOriginal to set
	 */
	public void setToDateOriginal(String toDateOriginal) {
		ToDateOriginal = toDateOriginal;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		ToDatePid = toDatePid;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		event.setEventPid(EventPid);
		event.setTableId(Constants.EVENTS_TABLE_ID);
		event.setFromDatePid(FromDatePid);
		event.setToDatePid(ToDatePid);
		event.setEventNamePid(EventNamePid);
		event.update();
	}

	/**
	 * @return
	 */
	public List<List<String>> getStringList() {
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

}
