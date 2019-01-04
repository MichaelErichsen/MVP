package net.myerichsen.hremvp.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.serverlogic.LocationEventServer;

/**
 * Provides all events for a location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 25. nov. 2018
 *
 */
public class LocationEventProvider {

	private LocationEventServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationEventProvider() throws SQLException {
		server = new LocationEventServer();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<List<String>> getEventList(int locationPid) throws Exception {
		return server.getEventList(locationPid);
	}
}