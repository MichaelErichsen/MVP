package net.myerichsen.gen.mvp.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.gen.mvp.serverlogic.LocationPersonServer;

/**
 * Provides all Persons for a location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. nov. 2018
 *
 */
public class LocationPersonProvider {
	private LocationPersonServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationPersonProvider() throws SQLException {
		server = new LocationPersonServer();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<String> getPersonList(int locationPid) throws Exception {
		return server.getPersonList(locationPid);
	}
}