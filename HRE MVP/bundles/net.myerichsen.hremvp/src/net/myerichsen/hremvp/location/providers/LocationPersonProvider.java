package net.myerichsen.hremvp.location.providers;

import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.location.servers.LocationPersonServer;

/**
 * Provides all Persons for a location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 4. mar. 2019
 *
 */
public class LocationPersonProvider implements IHREProvider {
	private final LocationPersonServer server;

	/**
	 * Constructor
	 *
	 * @throws Exception An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationPersonProvider() throws Exception {
		server = new LocationPersonServer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws Exception {
		server.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	public List<?> get() throws Exception {
		return server.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws Exception {
		server.get(key);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<String> getPersonList(int locationPid) throws Exception {
		return server.getPersonList(locationPid);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		return server.getStringList(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub

	}
}