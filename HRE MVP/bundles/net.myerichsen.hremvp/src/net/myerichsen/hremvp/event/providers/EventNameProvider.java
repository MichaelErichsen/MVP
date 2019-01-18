package net.myerichsen.hremvp.event.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.event.servers.EventNameServer;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 18. jan. 2019
 *
 */
public class EventNameProvider {
	EventNameServer server;

	/**
	 * @return
	 * @throws SQLException
	 */
	public List<EventNames> get() throws SQLException {
		return server.get();
	}

	/**
	 * Constructor
	 *
	 */
	public EventNameProvider() {
		server = new EventNameServer();
	}

}
