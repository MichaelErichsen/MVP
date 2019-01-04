
package net.myerichsen.hremvp.handlers;

import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.help.internal.base.BaseHelpSystem;

/**
 * Handler to open the help system in an external browser.
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. jan. 2019
 *
 */
@SuppressWarnings("restriction")
public class HelpContentsHandler {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode("net.myerichsen.hremvp");

	/**
	 * 
	 */
	@Execute
	public void execute() {
		try {
			BaseHelpSystem.ensureWebappRunning();
			final String helpURL = "http://127.0.0.1:" + preferences.getInt("HELPSYSTEMPORT", 8081) + "/help/index.jsp";

			BaseHelpSystem.getHelpBrowser(true).displayURL(helpURL);
		} catch (final Exception e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
		}
	}

}