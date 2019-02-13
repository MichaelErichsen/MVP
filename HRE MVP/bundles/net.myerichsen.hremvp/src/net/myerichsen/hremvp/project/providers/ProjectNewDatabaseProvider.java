package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.prefs.BackingStoreException;

import net.myerichsen.hremvp.project.servers.ProjectNewDatabaseServer;

/**
 * Create and open a new HRE project database
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. jan. 2019
 *
 */
public class ProjectNewDatabaseProvider {
	ProjectNewDatabaseServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException
	 */
	public ProjectNewDatabaseProvider() throws SQLException {
		server = new ProjectNewDatabaseServer();
	}

	/**
	 * Provide the data
	 *
	 * @param dbName
	 *
	 * @throws SQLException          When failing
	 * @throws BackingStoreException Preferences file access failure
	 * @throws                       java.util.prefs.BackingStoreException
	 * @throws                       org.osgi.service.prefs.BackingStoreException
	 */
	public void provide(String dbName)
			throws SQLException, BackingStoreException {
		server.provide(dbName);
	}
}
