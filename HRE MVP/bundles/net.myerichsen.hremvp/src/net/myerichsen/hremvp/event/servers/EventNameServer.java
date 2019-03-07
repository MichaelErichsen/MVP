package net.myerichsen.hremvp.event.servers;

import java.sql.Timestamp;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.dbmodels.EventNames;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 4. mar. 2019
 *
 */
public class EventNameServer implements IHREServer {
	private int EventNamePid;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
	private String Label;
	private int EventTypePid;

	EventNames name;

	/**
	 * Constructor
	 *
	 */
	public EventNameServer() {
		name = new EventNames();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws Exception {
		name.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	public List<EventNames> get() throws Exception {
		return name.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws Exception {
		name.get(key);
	}

	/**
	 * @return the eventNamePid
	 */
	public int getEventNamePid() {
		return EventNamePid;
	}

	/**
	 * @return the eventTypePid
	 */
	public int getEventTypePid() {
		return EventTypePid;
	}

	/**
	 * @return the insertTstmp
	 */
	public Timestamp getInsertTstmp() {
		return InsertTstmp;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return name.getLabel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
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

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return TableId;
	}

	/**
	 * @return the updateTstmp
	 */
	public Timestamp getUpdateTstmp() {
		return UpdateTstmp;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws Exception {
		name.setEventNamePid(EventNamePid);
		name.setEventTypePid(EventTypePid);
		name.setLabel(Label);
		return name.insert();
	}

	/**
	 * @param eventNamePid the eventNamePid to set
	 */
	public void setEventNamePid(int eventNamePid) {
		EventNamePid = eventNamePid;
	}

	/**
	 * @param eventTypePid the eventTypePid to set
	 */
	public void setEventTypePid(int eventTypePid) {
		EventTypePid = eventTypePid;
	}

	/**
	 * @param insertTstmp the insertTstmp to set
	 */
	public void setInsertTstmp(Timestamp insertTstmp) {
		InsertTstmp = insertTstmp;
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
	 * @param updateTstmp the updateTstmp to set
	 */
	public void setUpdateTstmp(Timestamp updateTstmp) {
		UpdateTstmp = updateTstmp;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws Exception {
		name.setEventNamePid(EventNamePid);
		name.setEventTypePid(EventTypePid);
		name.setLabel(Label);
		name.update();
	}

}
