
package net.myerichsen.hremvp.project.handlers;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
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
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.project.models.ProjectList;
import net.myerichsen.hremvp.project.models.ProjectModel;
import net.myerichsen.hremvp.project.parts.ProjectNavigator;

/**
 * Deletes an existing and registered projects. Closes the database if open.
 * Deletes it if it exists. Removes it from preferences.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 3. feb. 2019
 *
 */
public class ProjectDeleteHandler {
	@Inject
	private static IEventBroker eventBroker;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.project.parts.ProjectNavigator";

	/**
	 * Select a project and delete it
	 *
	 * @param workbench
	 * @param shell
	 */
	@Execute
	public void execute(IWorkbench workbench, EPartService partService, MApplication application,
			EModelService modelService, Shell shell) {
		// Find selected database
		int index = 0;

		final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
		MPart part = MBasicFactory.INSTANCE.createPart();

		for (final MPartStack mPartStack : stacks) {
			final List<MStackElement> a = mPartStack.getChildren();

			for (int i = 0; i < a.size(); i++) {
				part = (MPart) a.get(i);
				if (part.getContributionURI().equals(contributionURI)) {
					final ProjectNavigator pn = (ProjectNavigator) part.getObject();
					index = pn.getTableViewer().getTable().getSelectionIndex();
					LOGGER.info("Selected index: " + index);
					break;
				}
			}

			if (index > 0) {
				break;
			}
		}

		final ProjectModel model = ProjectList.getModel(index);
		final String dbName = model.getName();

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell, "Delete Project " + dbName, null,
				"Are you sure that you will delete project " + dbName + "?", MessageDialog.CONFIRM, 0,
				new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post("MESSAGE", "Deletion of project " + dbName + " has been canceled");
			return;
		}

		// Close if active

		try {
			final String activeName = store.getString("DBNAME");

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

			final String path = model.getPath().trim();
			String fullPath = path + ".h2.db";
			File file = new File(fullPath);
			boolean result = false;

			try {
				result = Files.deleteIfExists(file.toPath());
			} catch (final Exception e) {
				fullPath = path + ".mv.db";
				file = new File(fullPath);
				try {
					result = Files.deleteIfExists(file.toPath());
				} catch (final Exception e1) {
					LOGGER.severe(e1.getMessage());
				}
			}

			if (result) {
				LOGGER.info("Existing database " + fullPath + " has been deleted");
			}

			final int projectCount = store.getInt("projectcount");

			String key;

			for (int i = 1; i <= projectCount; i++) {
				key = "project." + i + ".path";
				if (store.contains(key)) {
					LOGGER.info("Path: " + path + ", key : " + key + ", value: " + store.getString(key));

					if (path.equals(store.getString(key).trim())) {
						// Delete from preferences
						index = i;
						ProjectList.remove(index, dbName);
						LOGGER.info("Project " + dbName + " has been deleted");
						eventBroker.post("MESSAGE", "Project " + dbName + " has been deleted");
						eventBroker.post(Constants.PROJECT_LIST_UPDATE_TOPIC, index);
						break;
					}
				}
			}

		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}