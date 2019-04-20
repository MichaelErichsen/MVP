package net.myerichsen.hremvp.project.providers;

import java.util.prefs.BackingStoreException;

import org.eclipse.swt.widgets.Shell;

import net.myerichsen.hremvp.project.servers.ProjectNewDatabaseServer;

/**
 * Create and open a new HRE project database
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. apr. 2019
 *
 */
public class ProjectNewDatabaseProvider {
	ProjectNewDatabaseServer server;

	/**
	 * Constructor
	 * 
	 * @param shell
	 *
	 * @throws Exception
	 */
	public ProjectNewDatabaseProvider(Shell shell) throws Exception {
		server = new ProjectNewDatabaseServer(shell);
	}

	/**
	 * Provide the data
	 *
	 * @param dbName
	 *
	 * @throws Exception             When failing
	 * @throws BackingStoreException Preferences file access failure
	 * @throws                       java.util.prefs.BackingStoreException
	 * @throws                       org.osgi.service.prefs.BackingStoreException
	 */
	public void provide(String dbName) throws Exception, BackingStoreException {
		server.provide(dbName);
	}
}
