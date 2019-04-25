package net.myerichsen.hremvp.project.handlers;

import java.util.logging.Level;
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
import net.myerichsen.hremvp.event.servers.EventServer;
import net.myerichsen.hremvp.location.servers.LocationServer;
import net.myerichsen.hremvp.person.servers.PersonServer;
import net.myerichsen.hremvp.project.servers.ProjectServer;
import net.myerichsen.hremvp.project.servers.SexTypeServer;
import net.myerichsen.hremvp.requesthandlers.HREHttpRequestHandler;
import net.myerichsen.hremvp.requesthandlers.RootHttpRequestHandler;

/**
 * Starts the embedded Jetty server. Creates a ContextHandlerCollection that
 * other features can add contexts and handlers to.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd.
 * @version 25. apr. 2019
 *
 */
public class ProjectStartEmbeddedServerHandler {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
			"net.myerichsen.hremvp");

	@Inject
	private IEventBroker eventBroker;

	/**
	 * Start embedded Jetty server
	 */
	@Execute
	public void execute() {
		final Runnable runnable = () -> {
			final Server server = new Server(store.getInt("SERVERPORT"));
			ContextHandler context;

			try {
				server.getConnectors()[0]
						.getConnectionFactory(HttpConnectionFactory.class);
				final ContextHandlerCollection contexts = HreContextHandlerCollection
						.getContexts();

				context = new ContextHandler();
				context.setContextPath("/mvp/v100/events/");
				context.setHandler(
						new HREHttpRequestHandler(new EventServer()));
				contexts.addHandler(context);

				context = new ContextHandler();
				context.setContextPath("/mvp/v100/locations/");
				context.setHandler(
						new HREHttpRequestHandler(new LocationServer()));
				contexts.addHandler(context);

				context = new ContextHandler();
				context.setContextPath("/mvp/v100/persons/");
				context.setHandler(
						new HREHttpRequestHandler(new PersonServer()));
				contexts.addHandler(context);

				context = new ContextHandler();
				context.setContextPath("/mvp/v100/projects/");
				context.setHandler(
						new HREHttpRequestHandler(new ProjectServer()));
				contexts.addHandler(context);

				context = new ContextHandler();
				context.setContextPath("/mvp/v100/sextypes/");
				context.setHandler(
						new HREHttpRequestHandler(new SexTypeServer()));
				contexts.addHandler(context);

				context = new ContextHandler();
				context.setContextPath("/");
				context.setHandler(new RootHttpRequestHandler());
				contexts.addHandler(context);

				final Handler[] handlerList = contexts.getHandlers();

				for (final Handler handler : handlerList) {
					LOGGER.log(Level.INFO, "Server handler: {0}",
							((ContextHandler) handler).getContextPath());
				}

				server.setHandler(contexts);
				server.setStopAtShutdown(true);
				server.start();

				LOGGER.log(Level.INFO, "The server is running at {0}",
						server.getURI());
				eventBroker.post("MESSAGE",
						"The server is running at " + server.getURI());

				// server.join();
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		};
		new Thread(runnable).start();
	}
}