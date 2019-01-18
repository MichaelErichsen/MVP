package net.myerichsen.hremvp.event.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.dbmodels.EventNames;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 18. jan. 2019
 *
 */
public class EventNameServer {
	EventNames name;

	/**
	 * Constructor
	 *
	 */
	public EventNameServer() {
		name = new EventNames();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public List<EventNames> get() throws SQLException {
		return name.get();
	}

}
