package net.myerichsen.hremvp;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Set up logger using the path from the preferences and the file name format
 * "mvp-log.%u.%g.txt".
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 21. okt. 2018
 *
 */
public class HreLogger {
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	static private FileHandler handler;

	/**
	 * @throws IOException When log file access fails
	 */
	static public void setup() throws IOException {
		final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		LOGGER.setLevel(Level.parse(store.getString("LOGLEVEL")));

		final String logFilePath = store.getString("LOGFILEPATH");

		handler = new FileHandler(logFilePath + "mvp-log.%u.%g.txt", 1024 * 1024, 10, true);
		handler.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(handler);

		LOGGER.info("Log file path: " + logFilePath);
	}
}