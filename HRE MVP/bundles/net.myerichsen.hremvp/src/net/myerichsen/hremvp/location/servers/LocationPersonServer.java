package net.myerichsen.hremvp.location.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.myerichsen.hremvp.dbmodels.LocationEvents;
import net.myerichsen.hremvp.dbmodels.PersonEvents;
import net.myerichsen.hremvp.serverlogic.NameServer;

/**
 * Business logic interface for persons for locationa
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. nov. 2018
 *
 */
public class LocationPersonServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final ArrayList<String> stringList;
	private final LocationEvents leLink;
	private final PersonEvents peLink;

	/**
	 * Constructor
	 *
	 */
	public LocationPersonServer() {
		stringList = new ArrayList<>();
		leLink = new LocationEvents();
		peLink = new PersonEvents();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<String> getPersonList(int locationPid) throws Exception {
		int eventPid;
		String string;
		List<PersonEvents> peList;
		int personPid;
		NameServer ns;

		final List<LocationEvents> leList = leLink.getFKLocationPid(locationPid);
		stringList.clear();

		for (final LocationEvents eventLink : leList) {
			eventPid = eventLink.getEventPid();
			peList = peLink.getFKEventPid(eventPid);

			for (final PersonEvents personLink : peList) {
				personPid = personLink.getPersonPid();
				ns = new NameServer();
				string = Integer.toString(personPid) + "," + ns.getPrimaryNameString(personPid);
				LOGGER.info(string);
				stringList.add(string);
			}
		}

		return stringList;
	}

}