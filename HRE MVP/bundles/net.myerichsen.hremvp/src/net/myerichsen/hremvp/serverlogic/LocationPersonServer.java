package net.myerichsen.hremvp.serverlogic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.myerichsen.hremvp.dbmodels.LocationEvents;
import net.myerichsen.hremvp.dbmodels.PersonEvents;

/**
 * Business logic interface for persons for locationa
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. nov. 2018
 *
 */
public class LocationPersonServer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private ArrayList<String> stringList;
	private LocationEvents leLink;
	private PersonEvents peLink;

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

		List<LocationEvents> leList = leLink.getFKLocationPid(locationPid);
		stringList.clear();

		for (LocationEvents eventLink : leList) {
			eventPid = eventLink.getEventPid();
			peList = peLink.getFKEventPid(eventPid);

			for (PersonEvents personLink : peList) {
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
