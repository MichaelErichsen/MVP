package net.myerichsen.hremvp.project.providers;

import java.util.prefs.BackingStoreException;

import net.myerichsen.hremvp.project.servers.ProjectNewDatabaseServer;

/**
 * Create and open a new HRE project database
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. mar. 2019
 *
 */
public class ProjectNewDatabaseProvider {
	ProjectNewDatabaseServer server;

	/**
	 * Constructor
	 *
	 * @throws Exception
	 */
	public ProjectNewDatabaseProvider() throws Exception {
		server = new ProjectNewDatabaseServer();
	}

	/**
	 * Provide the data
	 *
	 * @param dbName
	 *
	 * @throws Exception          When failing
	 * @throws BackingStoreException Preferences file access failure
	 * @throws                       java.util.prefs.BackingStoreException
	 * @throws                       org.osgi.service.prefs.BackingStoreException
	 */
	public void provide(String dbName)
			throws Exception, BackingStoreException {
		server.provide(dbName);
	}
}
