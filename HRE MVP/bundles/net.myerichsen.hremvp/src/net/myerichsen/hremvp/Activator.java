package net.myerichsen.hremvp;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.help.standalone.Infocenter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.service.environment.EnvironmentInfo;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * The activator class runs when the application starts and stops, Sets up the
 * logger. Starts and stops the Help System.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 25. jun. 2019
 *
 */
public class Activator implements BundleActivator {
	/**
	 *
	 */
	private static final String PROJECT = "project.";
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private Infocenter infocenter;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws IOException {
		HreLogger.setup();

		LOGGER.log(Level.INFO, "HRE MVP v0.2.3 has been started");
		LOGGER.log(Level.FINE, "Command line arguments:");

		final ServiceReference<EnvironmentInfo> envRef = bundleContext
				.getServiceReference(EnvironmentInfo.class);
		final EnvironmentInfo envInfo = bundleContext.getService(envRef);
		final String[] args = envInfo.getCommandLineArgs();
		for (int i = 0; i < args.length; i++) {
			LOGGER.log(Level.FINE, "CLI {0}: {1}", new Object[] { i, args[i] });
		}

		// List properties to LOGGER
		LOGGER.log(Level.FINE, "--------------------------------------");
		LOGGER.log(Level.FINE, "Project preferences:");
		LOGGER.log(Level.FINE,
				"Project count: " + store.getString("projectcount"));
		final int i = Integer.parseInt(store.getString("projectcount"));

		for (int j = 1; j < (i + 1); j++) {
			LOGGER.log(Level.FINE, store.getString(PROJECT + j + ".name"));
			LOGGER.log(Level.FINE,
					store.getString(PROJECT + j + ".lastupdated"));
			LOGGER.log(Level.FINE, store.getString(PROJECT + j + ".summary"));
			LOGGER.log(Level.FINE,
					store.getString(PROJECT + j + ".localserver"));
			LOGGER.log(Level.FINE, store.getString(PROJECT + j + ".path"));
		}
		LOGGER.log(Level.FINE, "--------------------------------------");

		final String csMode = store.getString("CSMODE");
		LOGGER.log(Level.FINE, "Client/server mode {0}", csMode);
		LOGGER.log(Level.FINE, "HRE Absolute path: {0}",
				new File(".").getAbsolutePath());
		LOGGER.log(Level.FINE, "HRE Font: {0}", store.getString("HREFONT"));

		final int port = store.getInt("HELPSYSTEMPORT");

		try {

			String[] options = new String[] { "-port", Integer.toString(port) };
			infocenter = new Infocenter(options);
			infocenter.start();

			LOGGER.log(Level.INFO, "Help System has been started at port {0}",
					port);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) {
		try {
			HreH2ConnectionPool.dispose();
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, "Dispose connection pool: {0}",
					e1.getMessage());
		}

		try {
			infocenter.shutdown();

			LOGGER.log(Level.INFO, "Help System is being stopped");
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Stop help system: {0}", e.getMessage());
		}

	}

}