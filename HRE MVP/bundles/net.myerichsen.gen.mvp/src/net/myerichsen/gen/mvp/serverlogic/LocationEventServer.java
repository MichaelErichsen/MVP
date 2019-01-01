package net.myerichsen.gen.mvp.serverlogic;

import java.util.ArrayList;
import java.util.List;

import net.myerichsen.gen.mvp.dbmodels.EventNames;
import net.myerichsen.gen.mvp.dbmodels.Events;
import net.myerichsen.gen.mvp.dbmodels.LocationEvents;

/**
 * Business logic interface for
 * {@link net.myerichsen.gen.mvp.dbmodels.LocationEvents}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 25. nov. 2018
 *
 */
public class LocationEventServer {
//	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * @param locationPid The persistent ID of the row
	 * @return A list of lists of strings of pids and labels
	 * @throws Exception
	 */
	public List<List<String>> getEventList(int locationPid) throws Exception {
		List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		int eventPid;
		int namePid;

		Events event = new Events();
		EventNames name = new EventNames();
		LocationEvents link = new LocationEvents();
		List<LocationEvents> a = link.getFKLocationPid(locationPid);

		for (LocationEvents eventLink : a) {
			stringList = new ArrayList<String>();

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

}
