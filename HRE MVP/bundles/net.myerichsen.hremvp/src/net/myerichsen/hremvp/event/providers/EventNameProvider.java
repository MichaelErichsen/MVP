package net.myerichsen.hremvp.event.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.event.servers.EventNameServer;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. jan. 2019
 *
 */
public class EventNameProvider implements IHREProvider {
	EventNameServer server;

	/**
	 * Constructor
	 *
	 */
	public EventNameProvider() {
		server = new EventNameServer();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public List<EventNames> get() throws SQLException {
		return server.get();
	}

	/**
	 * @param parseInt
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return server.getLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

}
