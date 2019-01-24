package net.myerichsen.hremvp.location.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.LocationEvents;
import net.myerichsen.hremvp.dbmodels.PersonEvents;
import net.myerichsen.hremvp.person.servers.PersonNameServer;

/**
 * Business logic interface for persons for locationa
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. nov. 2018
 *
 */
public class LocationPersonServer implements IHREServer {
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
		PersonNameServer ns;

		final List<LocationEvents> leList = leLink.getFKLocationPid(locationPid);
		stringList.clear();

		for (final LocationEvents eventLink : leList) {
			eventPid = eventLink.getEventPid();
			peList = peLink.getFKEventPid(eventPid);

			for (final PersonEvents personLink : peList) {
				personPid = personLink.getPersonPid();
				ns = new PersonNameServer();
				string = Integer.toString(personPid) + "," + ns.getPrimaryNameString(personPid);
				LOGGER.info(string);
				stringList.add(string);
			}
		}

		return stringList;
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		
	}

}
