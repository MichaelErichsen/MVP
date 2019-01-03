package net.myerichsen.hremvp.handlers;

import java.util.logging.Logger;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

/**
 * Handler to refresh the log
 *
 * @version 2018-09-12
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 *
 */
public class LogRefreshHandler {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * @param eventBroker The Eclipse event broker
	 */
	@Execute
	public void execute(IEventBroker eventBroker) {
		int i = 0;
		LOGGER.fine("Refreshing log");
		eventBroker.post(net.myerichsen.hremvp.Constants.LOG_REFRESH_UPDATE_TOPIC, i);

	}

}