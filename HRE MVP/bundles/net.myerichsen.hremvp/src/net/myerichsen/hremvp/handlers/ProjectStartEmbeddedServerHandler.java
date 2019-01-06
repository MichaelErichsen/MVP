package net.myerichsen.hremvp.handlers;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.HreContextHandlerCollection;
import net.myerichsen.hremvp.requesthandlers.PersonHttpRequestHandler;
import net.myerichsen.hremvp.requesthandlers.RootHttpRequestHandler;
import net.myerichsen.hremvp.requesthandlers.SexTypeHttpRequestHandler;

/**
 * Starts the embedded Jetty server. Creates a ContextHandlerCollection that
 * other features can add contexts and handlers to.
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd.
 * @version 18. okt. 2018
 *
 */
public class ProjectStartEmbeddedServerHandler {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	@Inject
	private IEventBroker eventBroker;

	/**
	 * Start embedded Jetty server
	 */
	@Execute
	public void execute() {
		Runnable runnable = new Runnable() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				Server server = new Server(store.getInt("SERVERPORT"));

				try {
					server.getConnectors()[0].getConnectionFactory(HttpConnectionFactory.class);
					ContextHandlerCollection contexts = HreContextHandlerCollection.getContexts();

					ContextHandler context = new ContextHandler();
					context.setContextPath("/mvp/v100/sextype/");
					context.setHandler(new SexTypeHttpRequestHandler());
					contexts.addHandler(context);

					context = new ContextHandler();
					context.setContextPath("/mvp/v100/person/");
					context.setHandler(new PersonHttpRequestHandler());
					contexts.addHandler(context);

					context = new ContextHandler();
					context.setContextPath("/");
					context.setHandler(new RootHttpRequestHandler());
					contexts.addHandler(context);

					Handler[] handlerList = contexts.getHandlers();

					for (Handler handler : handlerList) {
						LOGGER.info("Server handler: " + ((ContextHandler) handler).getContextPath());
					}

					server.setHandler(contexts);
					server.setStopAtShutdown(true);
					server.start();

					LOGGER.info("The server is running at " + server.getURI());
					eventBroker.post("MESSAGE", "The server is running at " + server.getURI());

					// server.join();
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.severe(
							e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
				}
			}
		};
		new Thread(runnable).start();
	}
}