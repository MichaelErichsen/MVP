package net.myerichsen.hremvp.event.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.EventNames;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. jan. 2019
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

	/**
	 * @param key
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void get(int key) throws SQLException, MvpException {
		name.get(key);
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return name.getLabel();
	}

}
