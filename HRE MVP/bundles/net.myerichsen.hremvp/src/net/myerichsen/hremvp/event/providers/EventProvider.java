package net.myerichsen.hremvp.event.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.event.servers.EventServer;

/**
 * Provides all data about an personEvent
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. jan. 2019
 *
 */
public class EventProvider implements IHREProvider {
	private int EventPid;
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

	private final EventServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public EventProvider() throws SQLException {
		server = new EventServer();
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
		setEventPid(server.getEventPid());
		setFromDatePid(server.getFromDatePid());
		setFromDateLabel(server.getFromDateLabel());
		setFromDateOriginal(server.getFromDateOriginal());
		setToDatePid(server.getToDatePid());
		setToDateLabel(server.getToDateLabel());
		setToDateOriginal(server.getToDateOriginal());
		setEventNamePid(server.getEventNamePid());
		setEventName(server.getEventName());
		setIsoCode(server.getIsoCode());
		setLanguagePid(server.getLanguagePid());
		setLanguage(server.getLanguage());
		setEventTypePid(server.getEventTypePid());
		setEventType(server.getEventType());
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
		return server.getLocationList(key);
	}

	/**
	 * @return the personList
	 */
	public List<List<String>> getPersonList() {
		return personList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws SQLException {
		return server.getStringList();
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
	@Override
	public int insert() throws SQLException, MvpException {
		server.setEventPid(EventPid);
		server.setFromDatePid(FromDatePid);
		server.setToDatePid(ToDatePid);
		server.setEventNamePid(EventNamePid);
		return server.insert();
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
	public void setFromDatePid(int i) {
		FromDatePid = i;
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
	 * @param list the locationList to set
	 */
	public void setLocationList(List<List<String>> list) {
		locationList = list;
	}

	/**
	 * @param list the personList to set
	 */
	public void setPersonList(List<List<String>> list) {
		personList = list;
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
	public void setToDatePid(int todate) {
		ToDatePid = todate;
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
		server.setEventPid(EventPid);
		server.setFromDatePid(FromDatePid);
		server.setToDatePid(ToDatePid);
		server.setEventNamePid(EventNamePid);
		server.update();
	}

}
