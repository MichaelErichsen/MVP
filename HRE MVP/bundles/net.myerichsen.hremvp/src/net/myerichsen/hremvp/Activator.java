package net.myerichsen.hremvp;

import java.io.File;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
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
 * @version 3. feb. 2019
 *
 */
public class Activator implements BundleActivator {
	private static BundleContext context;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static String HELPCLASSPATH = "plugins\\\\org.eclipse.help.base_4.2.400.v20181206-0815.jar";
// "plugins\\\\org.eclipse.help.base_4.2.153.v20180330-0640.jar";
	// "plugins\\\\org.eclipse.help.base_4.2.200.v20180611-0500.jar";

	/**
	 * @return The bundle context
	 */
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		HreLogger.setup();

		LOGGER.fine("HRE MVP v0.2 has been started");
		LOGGER.fine("Command line arguments:");

		final ServiceReference<EnvironmentInfo> envRef = context.getServiceReference(EnvironmentInfo.class);
		final EnvironmentInfo envInfo = context.getService(envRef);
		final String[] args = envInfo.getCommandLineArgs();
		for (int i = 0; i < args.length; i++) {
			LOGGER.fine("CLI " + i + ": " + args[i]);
		}

		// List properties to LOGGER
		LOGGER.fine("--------------------------------------");
		LOGGER.fine("Project preferences:");
		LOGGER.fine("Project count: " + store.getString("projectcount"));
		final int i = Integer.parseInt(store.getString("projectcount"));

		for (int j = 1; j < (i + 1); j++) {
			LOGGER.fine(store.getString("project." + j + ".name"));
			LOGGER.fine(store.getString("project." + j + ".lastupdated"));
			LOGGER.fine(store.getString("project." + j + ".summary"));
			LOGGER.fine(store.getString("project." + j + ".localserver"));
			LOGGER.fine(store.getString("project." + j + ".path"));
		}
		LOGGER.fine("--------------------------------------");

		final String csMode = store.getString("CSMODE");
		LOGGER.fine("Client/server mode " + csMode);
		LOGGER.fine("HRE Absolute path: " + new File(".").getAbsolutePath());
		LOGGER.fine("HRE Font: " + store.getString("HREFONT"));

		final int port = store.getInt("HELPSYSTEMPORT");
		final String command = "java -classpath " + HELPCLASSPATH
				+ " org.eclipse.help.standalone.Infocenter -command start -port " + port
				+ " -product net.myerichsen.hremvp.helpsystem -clean";

		try {
			LOGGER.fine("Help System is being started at port " + port);
			final Process helpProcess = Runtime.getRuntime().exec(command);
			if (helpProcess.isAlive()) {
				LOGGER.fine("Help system start command: " + command);
			} else {
				throw new MvpException("Could not start help system process");
			}
		} catch (final Exception e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		HreH2ConnectionPool.dispose();

		final String command = "java -classpath " + HELPCLASSPATH
				+ " org.eclipse.help.standalone.Infocenter -command shutdown";

		try {
			Runtime.getRuntime().exec(command);
			LOGGER.fine("Help System is being stopped");
		} catch (final Exception e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
		}

		Activator.context = null;
	}

}