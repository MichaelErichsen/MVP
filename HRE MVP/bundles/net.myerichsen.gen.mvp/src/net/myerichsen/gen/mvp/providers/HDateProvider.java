package net.myerichsen.gen.mvp.providers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import net.myerichsen.gen.mvp.MvpException;
import net.myerichsen.gen.mvp.serverlogic.HDateServer;

/**
 * Provides all data for a single historical date
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 13. nov. 2018
 *
 */
public class HDateProvider {
	private int HdatePid;
	private int TableId;
	private String OriginalText;
	private LocalDate Date;
	private LocalDate SortDate;
	private String Surety;

	private HDateServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public HDateProvider() throws SQLException {
		server = new HDateServer();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return A list of strings with pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<String> get() throws SQLException, MvpException {
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
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setTableId(server.getTableId());
		setOriginalText(server.getOriginalText());
		setDate(server.getDate());
		setSortDate(server.getSortDate());
		setSurety(server.getSurety());
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return Date;
	}

	/**
	 * @return the hdatePid
	 */
	public int getHdatePid() {
		return HdatePid;
	}

	/**
	 * @return the originalText
	 */
	public String getOriginalText() {
		return OriginalText;
	}

	/**
	 * @return the sortDate
	 */
	public LocalDate getSortDate() {
		return SortDate;
	}

	/**
	 * @return the surety
	 */
	public String getSurety() {
		return Surety;
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
		server.setTableId(getTableId());
		server.setOriginalText(getOriginalText());
		server.setDate(getDate());
		server.setSortDate(getSortDate());
		server.setSurety(getSurety());
		return server.insert();
	}

	/**
	 * @param localDate the date to set
	 */
	public void setDate(LocalDate localDate) {
		Date = localDate;
	}

	/**
	 * @param hdatePid the hdatePid to set
	 */
	public void setHdatePid(int hdatePid) {
		HdatePid = hdatePid;
	}

	/**
	 * @param originalText the originalText to set
	 */
	public void setOriginalText(String originalText) {
		OriginalText = originalText;
	}

	/**
	 * @param sortDate the sortDate to set
	 */
	public void setSortDate(LocalDate sortDate) {
		SortDate = sortDate;
	}

	/**
	 * @param surety the surety to set
	 */
	public void setSurety(String surety) {
		Surety = surety;
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
		server.setTableId(getTableId());
		server.setOriginalText(getOriginalText());
		server.setDate(getDate());
		server.setSortDate(getSortDate());
		server.setSurety(getSurety());
		server.update();
	}

}