package net.myerichsen.hremvp.project.handlers;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.project.dialogs.ProjectNameSummaryDialog;
import net.myerichsen.hremvp.project.models.ProjectList;
import net.myerichsen.hremvp.project.models.ProjectModel;

/**
 * Open an existing project.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 2. feb. 2019
 *
 */
public class ProjectOpenExistingHandler {
	@Inject
	private static IEventBroker eventBroker;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
			"net.myerichsen.hremvp");

	/**
	 * Select a database and open it
	 *
	 * @param partService
	 * @param application
	 * @param modelService
	 * @param shell
	 * @throws SQLException
	 * @throws BackingStoreException
	 */
	@Execute
	public void execute(EPartService partService, MApplication application, EModelService modelService, Shell shell) {
		int index = 0;
		Connection conn = null;

		// Open file dialog
		final FileDialog dialog = new FileDialog(shell);
		final String[] extensions = { "*.h2.db", "*.mv.db", "*.*" };
		dialog.setFilterExtensions(extensions);
		dialog.open();

		final String shortName = dialog.getFileName();
		final String[] parts = shortName.split("\\.");
		final String path = dialog.getFilterPath();
		final String dbName = parts[0];

		// Disconnect from any currently connected database
		try {
			conn = HreH2ConnectionPool.getConnection();

			if (conn != null) {
				conn.createStatement().execute("SHUTDOWN");
				conn.close();
				conn = null;
				HreH2ConnectionPool.dispose();
			}
		} catch (final Exception e1) {
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
		}

		store.setValue("DBPATH", path);
		store.setValue("DBNAME", dbName);

		try {
			conn = HreH2ConnectionPool.getConnection(dbName);

			if (conn != null) {
				final String h2Version = store.getString("H2VERSION");
				LOGGER.info("Retrieved H2 version from preferences: " + h2Version.substring(0, 3));
				PreparedStatement ps;

				if (h2Version.substring(0, 3).equals("1.3")) {
					ps = conn.prepareStatement("SELECT TABLE_NAME, 0 FROM INFORMATION_SCHEMA.TABLES "
							+ "WHERE TABLE_TYPE = 'TABLE' ORDER BY TABLE_NAME");
				} else {
					ps = conn.prepareStatement("SELECT TABLE_NAME, ROW_COUNT_ESTIMATE FROM INFORMATION_SCHEMA.TABLES "
							+ "WHERE TABLE_TYPE = 'TABLE' ORDER BY TABLE_NAME");
				}

				ps.executeQuery();
				conn.close();
			}

			final int projectCount = store.getInt("projectcount");
			String key;
			boolean alreadyRegistered = false;

			for (int i = 0; i < projectCount; i++) {
				key = "project." + i + ".path";
				if (store.contains(key)) {
					if (dbName.equals(store.getString(key))) {
						alreadyRegistered = true;
						LOGGER.info("Project " + dbName + " already registered");
						break;
					}
				}
			}

			if (!alreadyRegistered) {
				// Open a dialog for summary
				final ProjectNameSummaryDialog pnsDialog = new ProjectNameSummaryDialog(shell);
				pnsDialog.open();

				// Update the HRE properties
				File file = new File(dbName + ".h2.db");
				if (file.exists() == false) {
					file = new File(dbName + ".mv.db");
				}
				final Date timestamp = new Date(file.lastModified());
				final ProjectModel model = new ProjectModel(pnsDialog.getProjectName(), timestamp,
						pnsDialog.getProjectSummary(), "LOCAL", path + "\\" + dbName);
				index = ProjectList.add(model);

				// Set database name in title bar
				final MWindow window = (MWindow) modelService.find("net.myerichsen.hremvp.window.main", application);
				window.setLabel("HRE v0.2 - " + dbName);
			}

			// Open H2 Database Navigator
			final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
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
				eventBroker.post(Constants.PROJECT_PROPERTIES_UPDATE_TOPIC, index);
			}

			eventBroker.post("MESSAGE", "Project database " + dbName + " has been opened");
		} catch (final Exception e1) {
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
	}
}
