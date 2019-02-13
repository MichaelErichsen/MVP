
package net.myerichsen.hremvp.project.handlers;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.h2.tools.Script;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.HreH2ConnectionPool;
import net.myerichsen.hremvp.project.models.ProjectList;
import net.myerichsen.hremvp.project.models.ProjectModel;
import net.myerichsen.hremvp.project.parts.ProjectNavigator;

/**
 * Back up the selected project. Closes the database if open. Back up to a human
 * readable, and database version independent backup.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 3. feb. 2019
 *
 */
public class ProjectBackupHandler {
	@Inject
	private static IEventBroker eventBroker;

	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.project.parts.ProjectNavigator";

	/**
	 * @param workbench
	 * @param shell
	 */
	@Execute
	public void execute(IWorkbench workbench, EPartService partService,
			MApplication application, EModelService modelService, Shell shell) {
		// Find selected database
		int index = 0;

		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		MPart part = MBasicFactory.INSTANCE.createPart();

		for (final MPartStack mPartStack : stacks) {
			final List<MStackElement> a = mPartStack.getChildren();

			for (int i = 0; i < a.size(); i++) {
				part = (MPart) a.get(i);
				if (part.getContributionURI().equals(contributionURI)) {
					final ProjectNavigator pn = (ProjectNavigator) part
							.getObject();
					index = pn.getTableViewer().getTable().getSelectionIndex();
					LOGGER.info("Selected index: " + index);
					break;
				}
			}

			if (index > 0) {
				break;
			}
		}

		// CLose if active
		final ProjectModel model = ProjectList.getModel(index);
		final String dbName = model.getName();

		try {
			final String activeName = store.getString("DBNAME");

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

			String path = model.getPath();
			File file = new File(path + ".h2.db");
			if (file.exists() == false) {
				file = new File(path + ".mv.db");
			}
			path = file.getParent();
			final String[] bkp = { "-url", "jdbc:h2:" + path + "\\" + dbName,
					"-user", store.getString("USERID"), "-password",
					store.getString("PASSWORD"), "-script",
					path + "\\" + dbName + ".zip", "-options", "compression",
					"zip" };
			Script.main(bkp);

			LOGGER.info("Project database " + dbName + " has been backed up to "
					+ path + "\\" + dbName + ".zip");
			eventBroker.post("MESSAGE",
					"Project database " + dbName + " has been backed up to "
							+ path + "\\" + dbName + ".zip");
		} catch (final SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}