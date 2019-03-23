package net.myerichsen.hremvp;

import java.util.List;

/**
 * Interface between visual parts and business logic
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 7. mar. 2019
 *
 */
public interface IHREProvider {

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	void delete(int key) throws Exception;

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the person
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	void get(int key) throws Exception;

	/**
	 * Get a list of list of strings
	 *
	 * @return List A list of lists of strings
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	List<List<String>> getStringList() throws Exception;

	/**
	 * Get a list of list of strings
	 *
	 * @param key
	 * @return List A list of lists of strings
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	List<List<String>> getStringList(int key) throws Exception;

	/**
	 * Insert a row
	 *
	 * @return The Pid of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	int insert() throws Exception;

	/**
	 * Update a row
	 *
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	void update() throws Exception;
}