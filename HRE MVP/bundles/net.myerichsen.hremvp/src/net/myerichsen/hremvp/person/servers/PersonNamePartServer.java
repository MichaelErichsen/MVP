package net.myerichsen.hremvp.person.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.NameMaps;
import net.myerichsen.hremvp.dbmodels.NameParts;
import net.myerichsen.hremvp.dbmodels.Names;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.NameParts}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 5. feb. 2019
 */
public class PersonNamePartServer implements IHREServer {
	// private static Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int namePartPid;
	private int namePid;
	private String name;
	private String mapLabel;
	private String label;
	private int partNo;

	private final NameParts part;

	/**
	 * Constructor
	 *
	 */
	public PersonNamePartServer() {
		part = new NameParts();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		part.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		return null;
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		part.get(key);
		setLabel(part.getLabel());
		setNamePartPid(part.getNamePartPid());
		setNamePid(part.getNamePid());
		setPartNo(part.getPartNo());

		// Get name
		final StringBuilder sb = new StringBuilder();
		final List<NameParts> partList = new NameParts().getFKNamePid(namePid);

		for (final NameParts nameParts : partList) {
			if (nameParts.getNamePid() == namePid) {
				if (nameParts.getLabel() != null) {
					sb.append(nameParts.getLabel() + " ");
				}
			}
		}

		setName(sb.toString().trim());

		// Get map label
		setMapLabel("Label");
		final Names name = new Names();
		name.get(namePid);

		final NameMaps map = new NameMaps();
		final List<NameMaps> mapList = map
				.getFKNameStylePid(name.getNameStylePid());

		if (mapList.size() != partList.size()) {
			throw new MvpException("Map list size: " + mapList.size()
					+ ", part list size: " + partList.size());
		}

		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.get(i).getPartNo() == partNo) {
				setMapLabel(mapList.get(i).getLabel());
				break;
			}
		}
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public List<NameParts> getFKNamePid(int key) throws SQLException {
		return part.getFKNamePid(key);
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the mapLabel
	 */
	public String getMapLabel() {
		return mapLabel;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the namePartPid
	 */
	public int getNamePartPid() {
		return namePartPid;
	}

	/**
	 * @return the namePid
	 */
	public int getNamePid() {
		return namePid;
	}

	/**
	 * @return the partNo
	 */
	public int getPartNo() {
		return partNo;
	}

	/**
	 * Insert a row. Checks if a matching part number exists in
	 * {@link net.myerichsen.hremvp.dbmodels.NameMaps}
	 *
	 * @return
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		part.setLabel(label);
		part.setNamePartPid(namePartPid);
		part.setNamePid(namePid);
		part.setPartNo(partNo);

		// Check if matching map part no exists
		final Names name = new Names();
		name.get(namePid);

//		final NameMaps map = new NameMaps();
//		final List<NameMaps> mapList = map.getFKNameStylePid(name.getNameStylePid());
//		Boolean found = false;
//
//		for (int i = 0; i < mapList.size(); i++) {
//			if (mapList.get(i).getPartNo() == partNo) {
//				found = true;
//				break;
//			}
//		}
//
//		if (!found) {
//			throw new MvpException("Part number " + partNo + " does not exist in matching name map");
//		}

		return part.insert();
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param mapLabel the mapLabel to set
	 */
	public void setMapLabel(String mapLabel) {
		this.mapLabel = mapLabel;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param namePartPid the namePartPid to set
	 */
	public void setNamePartPid(int namePartPid) {
		this.namePartPid = namePartPid;
	}

	/**
	 * @param namePid the namePid to set
	 */
	public void setNamePid(int namePid) {
		this.namePid = namePid;
	}

	/**
	 * @param partNo the partNo to set
	 */
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}

	/**
	 * Update a row. Checks if a matching part number exists in
	 * {@link net.myerichsen.hremvp.dbmodels.NameMaps}
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws SQLException, MvpException {
		part.setLabel(label);
		part.setNamePartPid(namePartPid);
		part.setNamePid(namePid);
		part.setPartNo(partNo);

		// Check if matching map part no exists
		final Names name = new Names();
		name.get(namePid);

		final NameMaps map = new NameMaps();
		final List<NameMaps> mapList = map
				.getFKNameStylePid(name.getNameStylePid());
		Boolean found = false;

		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.get(i).getPartNo() == partNo) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new MvpException("Part number " + partNo
					+ " does not exist in matching name map");
		}
		part.update();
	}
}