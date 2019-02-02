
package net.myerichsen.hremvp.project.handlers;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
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
import org.h2.tools.Restore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.HreH2ConnectionPool;

/**
 * Opens a dialog to select a backup zip file. Closes the database if open.
 * Deletes it if it exists. Uses the H2 script tool.
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 2. feb. 2019
 *
 */
public class ProjectRestoreHandler {
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
		final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText("Restore an HRE Project");
		dialog.setFilterPath("./");
		final String[] extensions = { "*Backup.zip", "*.*" };
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
					LOGGER.info("Existing database " + dbName + " has been closed");

					try {
						HreH2ConnectionPool.dispose();
					} catch (final Exception e) {
						LOGGER.info("No connection pool to dispose");
					}
				}
			}

			File file = new File(path + "\\" + dbName);

			boolean result = Files.deleteIfExists(file.toPath());

			if (result) {
				LOGGER.info("Existing database " + dbName + " has been deleted");
			}

			Restore.execute(shortName, path, null);

			// FIXME Add to project navigator and preferences

			LOGGER.info("Project database has been restored from " + shortName);
			eventBroker.post("MESSAGE", "Project database has been restored from " + shortName);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}