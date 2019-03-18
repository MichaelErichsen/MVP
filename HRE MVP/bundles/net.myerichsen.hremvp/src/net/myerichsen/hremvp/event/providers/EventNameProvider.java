package net.myerichsen.hremvp.event.providers;

import java.sql.Timestamp;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.event.servers.EventNameServer;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 4. mar. 2019
 *
 */
public class EventNameProvider implements IHREProvider {
	private int EventNamePid;
	private Timestamp InsertTstmp;
	private Timestamp UpdateTstmp;
	private int TableId;
	private String Label;
	private int EventTypePid;

	EventNameServer server;

	/**
	 * Constructor
	 *
	 */
	public EventNameProvider() {
		server = new EventNameServer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws Exception {
		server.delete(key);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<EventNames> get() throws Exception {
		return server.get();
	}

	/**
	 * @param parseInt
	 * @throws MvpException
	 * @throws Exception
	 */
	@Override
	public void get(int key) throws Exception {
		server.get(key);
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
		return server.getLabel();
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
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws Exception {
		server.setEventNamePid(EventNamePid);
		server.setEventTypePid(EventTypePid);
		server.setLabel(Label);
		return server.insert();
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
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws Exception {
		server.setEventNamePid(EventNamePid);
		server.setEventTypePid(EventTypePid);
		server.setLabel(Label);
		server.update();
	}

}
