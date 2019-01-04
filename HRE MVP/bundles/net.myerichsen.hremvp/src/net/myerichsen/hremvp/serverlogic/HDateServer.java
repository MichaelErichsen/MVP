package net.myerichsen.hremvp.serverlogic;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Hdates;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.HDates}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 13. nov. 2018
 *
 */
public class HDateServer {
	private int HdatePid;
	private int TableId;
	private String OriginalText;
	private LocalDate Date;
	private LocalDate SortDate;
	private String Surety;

	private final Hdates date;

	/**
	 * Constructor
	 *
	 */
	public HDateServer() {
		date = new Hdates();
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
		date.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return A list of strings of pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<String> get() throws SQLException, MvpException {
		final List<String> stringList = new ArrayList<>();

		final List<Hdates> hdateList = date.get();

		for (final Hdates hdate : hdateList) {
			stringList.add(hdate.getHdatePid() + "¤%&" + hdate.getDate().toString() + "¤%&" + hdate.getOriginalText());
		}

		return stringList;
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
		date.get(key);
		setTableId(date.getTableId());
		setOriginalText(date.getOriginalText());
		setDate(date.getDate());
		setSortDate(date.getSortDate());
		setSurety(date.getSurety());
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
		date.setTableId(751);
		date.setOriginalText(getOriginalText());
		date.setDate(getDate());
		date.setSortDate(getSortDate());
		date.setSurety(getSurety());
		return date.insert();
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
	 * @param localDate the sortDate to set
	 */
	public void setSortDate(LocalDate localDate) {
		SortDate = localDate;
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
		date.setTableId(getTableId());
		date.setOriginalText(getOriginalText());
		date.setDate(getDate());
		date.setSortDate(getSortDate());
		date.setSurety(getSurety());
		date.update();
	}
}
