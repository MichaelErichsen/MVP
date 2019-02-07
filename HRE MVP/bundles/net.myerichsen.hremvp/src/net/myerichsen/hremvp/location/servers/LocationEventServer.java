package net.myerichsen.hremvp.location.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.LocationEvents;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationEvents}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 25. nov. 2018
 *
 */
public class LocationEventServer implements IHREServer {
//	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param locationPid The persistent ID of the row
	 * @return A list of lists of strings of pids and labels
	 * @throws Exception
	 */
	public List<List<String>> getEventList(int locationPid) throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		int eventPid;
		int namePid;

		final Events event = new Events();
		final EventNames name = new EventNames();
		final LocationEvents link = new LocationEvents();
		final List<LocationEvents> a = link.getFKLocationPid(locationPid);

		for (final LocationEvents eventLink : a) {
			stringList = new ArrayList<>();

			eventPid = eventLink.getEventPid();
			event.get(eventPid);
			stringList.add(Integer.toString(eventPid));

			namePid = event.getEventNamePid();
			name.get(namePid);
			stringList.add(name.getLabel());
			lls.add(stringList);
		}

		return lls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

}
