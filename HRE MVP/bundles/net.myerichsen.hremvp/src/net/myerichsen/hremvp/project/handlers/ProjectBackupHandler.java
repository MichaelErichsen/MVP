
package net.myerichsen.hremvp.project.handlers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.h2.tools.Backup;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.HreH2ConnectionPool;

/**
 * Opens a dialog to select database and target location. Closes the database if
 * open. Uses the H2 script tool to create a compressed SQL script file. This
 * will result in a small, human readable, and database version independent
 * backup
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 2. feb. 2019
 *
 */
public class ProjectBackupHandler {
	@Inject
	private static IEventBroker eventBroker;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	/**
	 * @param workbench
	 * @param shell
	 */
	@Execute
	public void execute(IWorkbench workbench, Shell shell) {
		// Open file dialog
		final FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setText("Back up an HRE Project");
		dialog.setFilterPath("./");
		final String[] extensions = { "*.h2.db", "*.mv.db", "*.*" };
		dialog.setFilterExtensions(extensions);
		dialog.open();

		final String shortName = dialog.getFileName();
		final String[] parts = shortName.split("\\.");
		final String path = dialog.getFilterPath();
		final String dbName = parts[0];

		try {
			String activeName = store.getString("DBNAME");

			if (activeName.equals(dbName)) {
				Connection conn = null;

				conn = HreH2ConnectionPool.getConnection();

				if (conn != null) {
					conn.createStatement().execute("SHUTDOWN");
					conn.close();
					try {
						HreH2ConnectionPool.dispose();
					} catch (final Exception e) {
						LOGGER.info("No connection pool to dispose");
					}
				}
			}

			Backup.execute(shortName + "Backup.zip", path, dbName, false);

			LOGGER.info("Project database " + dbName + " has been backed up to " + shortName + "Backup.zip");
			eventBroker.post("MESSAGE",
					"Project database " + dbName + " has been backed up to " + shortName + "Backup.zip");
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}