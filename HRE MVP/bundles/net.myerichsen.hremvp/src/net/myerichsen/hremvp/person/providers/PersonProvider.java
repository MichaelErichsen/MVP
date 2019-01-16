package net.myerichsen.hremvp.person.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Persons;
import net.myerichsen.hremvp.person.servers.PersonServer;

/**
 * Provides all data for a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 16. jan. 2019
 *
 */
public class PersonProvider {
//	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int personPid;
	private int birthDatePid;
	private int deathDatePid;
	private List<List<String>> nameList;
	private List<List<String>> sexesList;
	private List<List<String>> parentList;
	private List<List<String>> partnerList;
	private List<List<String>> childrenList;
	private List<List<String>> personEventList;
	private List<List<String>> eventList;
	private List<List<String>> siblingList;
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
		nameList = new ArrayList<>();
		sexesList = new ArrayList<>();
		parentList = new ArrayList<>();
		partnerList = new ArrayList<>();
		eventList = new ArrayList<>();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return A list persons
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<Persons> get() throws SQLException, MvpException {
		return server.get();
	}

	/**
	 * @param key The persistent ID of the person
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setBirthDatePid(server.getBirthDatePid());
		setDeathDatePid(server.getDeathDatePid());
		setPersonPid(key);
		setEventList(server.getEventList());
		setNameList(server.getNameList());
		setParentList(server.getParentList());
		setPartnerList(server.getPartnerList());
		setChildrenList(server.getChildrenList());
		setSexesList(server.getSexesList());
		setSiblingList(server.getSiblingList());
	}

//	public void get(int key) throws ClientProtocolException, IOException, MvpException {
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
//		setBirthDate(jsonObject.getString("birthDatePid"));
//		setDeathDate(jsonObject.getString("deathDatePid"));
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
	 * @return the birthDatePid
	 */
	public int getBirthDatePid() {
		return birthDatePid;
	}

	/**
	 * @return the list of children
	 */
	public List<List<String>> getChildrenList() {
		return childrenList;
	}

	/**
	 * @return the deathDatePid
	 */
	public int getDeathDatePid() {
		return deathDatePid;
	}

	/**
	 * @return the eventList
	 */
	public List<List<String>> getEventList() {
		return eventList;
	}

	/**
	 * @return the nameList
	 */
	public List<List<String>> getNameList() {
		return nameList;
	}

	/**
	 * @return the parentList
	 */
	public List<List<String>> getParentList() {
		return parentList;
	}

	/**
	 * @return the partnerList
	 */
	public List<List<String>> getPartnerList() {
		return partnerList;
	}

	/**
	 * @return the personEventList
	 */
	public List<List<String>> getPersonEventList() {
		return personEventList;
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
	 * @return the sexesList
	 */
	public List<List<String>> getSexesList() {
		return sexesList;
	}

	/**
	 * @return the siblingList
	 */
	public List<List<String>> getSiblingList() {
		return siblingList;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public int insert() throws SQLException, MvpException {
		server.setBirthDatePid(birthDatePid);
		server.setDeathDatePid(deathDatePid);
		server.setPersonPid(personPid);
		return server.insert();
	}

	/**
	 * @param birthDatePid the birthDatePid to set
	 */
	public void setBirthDatePid(int i) {
		birthDatePid = i;
	}

	/**
	 * @param childrenList the childrenList to set
	 */
	public void setChildrenList(List<List<String>> childrenList) {
		this.childrenList = childrenList;
	}

	/**
	 * @param i the deathDatePid to set
	 */
	public void setDeathDatePid(int i) {
		deathDatePid = i;
	}

	/**
	 * @param eventList the eventList to set
	 */
	public void setEventList(List<List<String>> eventList) {
		this.eventList = eventList;
	}

	/**
	 * @param nameList the nameList to set
	 */
	public void setNameList(List<List<String>> nameList) {
		this.nameList = nameList;
	}

	/**
	 * @param parentList the parentList to set
	 */
	public void setParentList(List<List<String>> parentList) {
		this.parentList = parentList;
	}

	/**
	 * @param partnerList the partnerList to set
	 */
	public void setPartnerList(List<List<String>> partnerList) {
		this.partnerList = partnerList;
	}

	/**
	 * @param personEventList the personEventList to set
	 */
	public void setPersonEventList(List<List<String>> personEventList) {
		this.personEventList = personEventList;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

	/**
	 * @param sexesList the sexesList to set
	 */
	public void setSexesList(List<List<String>> sexesList) {
		this.sexesList = sexesList;
	}

	/**
	 * @param siblingList the siblingList to set
	 */
	public void setSiblingList(List<List<String>> siblingList) {
		this.siblingList = siblingList;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public void update() throws SQLException, MvpException {
		server.setBirthDatePid(birthDatePid);
		server.setDeathDatePid(deathDatePid);
		server.setPersonPid(personPid);
		server.update();
	}
}
