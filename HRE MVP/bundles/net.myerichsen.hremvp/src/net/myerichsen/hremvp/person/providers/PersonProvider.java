package net.myerichsen.hremvp.person.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Persons;
import net.myerichsen.hremvp.person.servers.PersonServer;

/**
 * Provides all data for a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. mar. 2019
 *
 */
public class PersonProvider implements IHREProvider {
//	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int personPid;
	private int birthDatePid;
	private int deathDatePid;

	private final PersonServer server;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public PersonProvider() throws SQLException {
		server = new PersonServer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	@Override
	public List<Persons> get() throws SQLException, MvpException {
		return server.get();
	}

	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setBirthDatePid(server.getBirthDatePid());
		setDeathDatePid(server.getDeathDatePid());
		setPersonPid(key);
	}

//	public void getremote(int key) throws ClientProtocolException, IOException, MvpException {
//		StringBuilder sb = new StringBuilder();
//		String s = "";
//
//		CloseableHttpClient client = HttpClients.createDefault();
//		HttpGet request = new HttpGet("http://127.0.0.1:8000/mvp/v100/person/" + key);
//		CloseableHttpResponse response = client.execute(request);
//
//		StatusLine statusLine = response.getStatusLine();
//
//		if (statusLine.getStatusCode() != 200) {
//			throw new MvpException(statusLine.getReasonPhrase());
//		}
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//		while (null != (s = br.readLine())) {
//			sb.append(s);
//		}
//
//		br.close();
//		response.close();
//		client.close();
//
//		JSONObject jsonObject = new JSONObject(sb.toString());
//
//		LOGGER.info(jsonObject.toString(2));
//
//		setPersonPid(jsonObject.getInt("personPid"));
//		setBirthDatePid(jsonObject.getString("birthDatePid"));
//		setDeathDatePid(jsonObject.getString("deathDatePid"));
//
//		setEventList(jsonObject.getJSONArray("eventList"));
//		setNameList(jsonObject.getNameList());
//		setParentList(jsonObject.getParentList());
//		setPartnerList(jsonObject.getPartnerList());
//		setSexesList(jsonObject.getSexesList());
//
//	}

	/**
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getAllNames() throws SQLException, MvpException {
		return server.getAllNames();
	}

	/**
	 * @param personId
	 * @param parseInt
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getAncestorList(int personId, int generations)
			throws SQLException, MvpException {
		return server.getAncestorList(personId, 0, generations);
	}

	/**
	 * @return the birthDatePid
	 */
	public int getBirthDatePid() {
		return birthDatePid;
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> getChildrenList(int key) throws SQLException {
		return server.getChildrenList(key);
	}

	/**
	 * @return the deathDatePid
	 */
	public int getDeathDatePid() {
		return deathDatePid;
	}

	/**
	 * @param key
	 * @param generations
	 * @return
	 * @throws SQLException
	 * @throws MvpException
	 */
	public List<List<String>> getDescendantList(int key, int generations)
			throws SQLException, MvpException {
		return server.getDescendantList(key, 0, generations);
	}

	/**
	 * @return the nameList
	 */
	public List<List<String>> getNameList() {
		return server.getNameList();
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> getParentList(int key) throws SQLException {
		return server.getParentList(key);
	}

	/**
	 * @param key
	 * @return the partnerList
	 * @throws SQLException
	 */
	public List<List<String>> getPartnerList(int key) throws SQLException {
		return server.getPartnerList(key);
	}

	/**
	 * @param personPid2
	 * @return the personEventList
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getPersonEventList(int key)
			throws SQLException, MvpException {
		return server.getPersonEventList(key);
	}

	/**
	 * @return the personList
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getPersonList()
			throws SQLException, MvpException {
		return server.getPersonList();
	}

	/**
	 * @param key
	 * @return
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getPersonNameList(int key)
			throws SQLException, MvpException {
		return server.getPersonNameList(key);
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public String getPrimaryName() throws SQLException {
		return server.getPrimaryName();
	}

	/**
	 * @param key
	 * @return the sexesList
	 * @throws MvpException
	 * @throws SQLException
	 */
	public List<List<String>> getSexesList(int key)
			throws SQLException, MvpException {
		return server.getSexesList(key);
	}

	/**
	 * @return the siblingList
	 * @throws SQLException
	 */
	public List<List<String>> getSiblingList(int personPid)
			throws SQLException {
		return server.getSiblingList(personPid);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		server.setBirthDatePid(birthDatePid);
		server.setDeathDatePid(deathDatePid);
		server.setPersonPid(personPid);
		return server.insert();
	}

	/**
	 * @param parentPid
	 * @param childPid
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void removeChild(int parentPid, int childPid)
			throws SQLException, MvpException {
		server.removeChild(parentPid, childPid);
	}

	/**
	 * @param personPid
	 * @param eventPid
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void removeEvent(int eventPid) throws SQLException, MvpException {
		server.removeEvent(eventPid);
	}

	/**
	 * @param personPid
	 * @param parentPid
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void removeParent(int personPid, int parentPid)
			throws SQLException, MvpException {
		server.removeParent(personPid, parentPid);
	}

	/**
	 * @param personPid2
	 * @param partnerPid
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void removePartner(int personPid, int partnerPid)
			throws SQLException, MvpException {
		server.removePartner(personPid, partnerPid);
	}

	/**
	 * @param personPid
	 * @param sexPid
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void removeSex(int sexPid) throws SQLException, MvpException {
		server.removeSex(sexPid);
	}

	/**
	 * @param birthDatePid the birthDatePid to set
	 */
	public void setBirthDatePid(int i) {
		birthDatePid = i;
	}

	/**
	 * @param i the deathDatePid to set
	 */
	public void setDeathDatePid(int i) {
		deathDatePid = i;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setBirthDatePid(birthDatePid);
		server.setDeathDatePid(deathDatePid);
		server.setPersonPid(personPid);
		server.update();
	}
}
