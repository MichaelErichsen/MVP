package net.myerichsen.hremvp.location.servers;

import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.LocationEvents;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationEvents}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 11. mar. 2019
 *
 */
public class LocationEventServer implements IHREServer {
//	private final static Logger LOGGER = Logger
//			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final LocationEvents locationEvent;

	/**
	 * Constructor
	 *
	 */
	public LocationEventServer() {
		super();
		locationEvent = new LocationEvents();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @param locationPid
	 * @throws Exception
	 */
	public void deleteAllEventLinksForLocation(int locationPid)
			throws Exception {
		final List<LocationEvents> fkLocationPid = locationEvent
				.getFKLocationPid(locationPid);

		for (final LocationEvents event : fkLocationPid) {
			locationEvent.delete(event.getLocationEventsPid());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	public List<?> get() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws Exception {
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
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub

	}

}
