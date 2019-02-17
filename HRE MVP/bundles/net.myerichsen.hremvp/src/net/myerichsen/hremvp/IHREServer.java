package net.myerichsen.hremvp;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface defining the HRE business interface
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 24. jan. 2019
 *
 */
public interface IHREServer {
	/**
	 * Delete a row
	 *
	 * @param key
	 * @throws SQLException
	 * @throws MvpException
	 */
	void delete(int key) throws SQLException, MvpException;

	/**
	 * Get all rows
	 *
	 * @return
	 * @throws SQLException
	 * @throws MvpException
	 */
	List<?> get() throws SQLException, MvpException;

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	void get(int key) throws SQLException, MvpException;

	/**
	 * Insert a row
	 *
	 * @return The Pid of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	int insert() throws SQLException, MvpException;

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	void update() throws SQLException, MvpException;
}
