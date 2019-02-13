
package net.myerichsen.hremvp.project.handlers;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.h2.tools.RunScript;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.project.dialogs.ProjectNameSummaryDialog;
import net.myerichsen.hremvp.project.models.ProjectList;
import net.myerichsen.hremvp.project.models.ProjectModel;

/**
 * Opens a dialog to select a backup zip file. Closes the database if open.
 * Deletes it if it exists. Uses the H2 script tool.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 3. feb. 2019
 *
 */
public class ProjectRestoreHandler {
	@Inject
	private static IEventBroker eventBroker;

	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	/**
	 * @param workbench
	 * @param shell
	 */
	@Execute
	public void execute(IWorkbench workbench, EPartService partService,
			MApplication application, EModelService modelService, Shell shell) {
		int index = 0;

		// Open file dialog
		final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText("Restore an HRE Project");
		dialog.setFilterPath("./");
		final String[] extensions = { "*.zip", "*.*" };
		dialog.setFilterExtensions(extensions);
		dialog.open();

		final String shortName = dialog.getFileName();
		final String[] parts = shortName.split("\\.");
		final String path = dialog.getFilterPath();
		final String dbName = parts[0];

		try {
			final String activeName = store.getString("DBNAME");

			if (activeName.equals(dbName)) {
				Connection conn = null;

				conn = HreH2ConnectionPool.getConnection();

				if (conn != null) {
					conn.createStatement().execute("SHUTDOWN");
					conn.close();
					LOGGER.info(
							"Existing database " + dbName + " has been closed");

					try {
						HreH2ConnectionPool.dispose();
					} catch (final Exception e) {
						LOGGER.info("No connection pool to dispose");
					}
				}
			}

			String fullPath = path + "\\" + dbName + ".h2.db";
			File file = new File(fullPath);

			boolean result = false;

			result = Files.deleteIfExists(file.toPath());

			if (!result) {
				fullPath = path + "\\" + dbName + ".mv.db";
				file = new File(fullPath);
				result = Files.deleteIfExists(file.toPath());
			}

			if (result) {
				LOGGER.info(
						"Existing database " + dbName + " has been deleted");
			}

			final String[] bkp = { "-url", "jdbc:h2:" + path + "\\" + dbName,
					"-user", store.getString("USERID"), "-script",
					path + "\\" + dbName + ".zip", "-options", "compression",
					"zip" };
			RunScript.main(bkp);

			final int projectCount = store.getInt("projectcount");
			String key;
			boolean alreadyRegistered = false;

			for (int i = 0; i < projectCount; i++) {
				key = "project." + i + ".path";
				if (store.contains(key)) {
					if (dbName.equals(store.getString(key))) {
						alreadyRegistered = true;
						LOGGER.info(
								"Project " + dbName + " already registered");
						break;
					}
				}
			}

			if (!alreadyRegistered) {
				// Open a dialog for summary
				final ProjectNameSummaryDialog pnsDialog = new ProjectNameSummaryDialog(
						shell);
				pnsDialog.open();

				// Update the HRE properties
				if (file.exists() == false) {
					file = new File(dbName + ".mv.db");
				}
				final Date timestamp = new Date(file.lastModified());
				final ProjectModel model = new ProjectModel(
						pnsDialog.getProjectName(), timestamp,
						pnsDialog.getProjectSummary(), "LOCAL",
						path + "\\" + dbName);
				index = ProjectList.add(model);

				// Set database name in title bar
				final MWindow window = (MWindow) modelService
						.find("net.myerichsen.hremvp.window.main", application);
				window.setLabel("HRE v0.2 - " + dbName);
			}

			// Open H2 Database Navigator
			final List<MPartStack> stacks = modelService
					.findElements(application, null, MPartStack.class, null);
			final MPart h2dnPart = MBasicFactory.INSTANCE.createPart();
			h2dnPart.setLabel("Database Tables");
			h2dnPart.setContainerData("650");
			h2dnPart.setCloseable(true);
			h2dnPart.setVisible(true);
			h2dnPart.setContributionURI(
					"bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.databaseadmin.H2DatabaseNavigator");
			stacks.get(stacks.size() - 2).getChildren().add(h2dnPart);
			partService.showPart(h2dnPart, PartState.ACTIVATE);

			eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, dbName);

			if (index > 0) {
				eventBroker.post(Constants.PROJECT_LIST_UPDATE_TOPIC, index);
				eventBroker.post(Constants.PROJECT_PROPERTIES_UPDATE_TOPIC,
						index);
			}

			LOGGER.info("Project database has been restored from " + shortName);
			eventBroker.post("MESSAGE",
					"Project database has been restored from " + shortName);
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}