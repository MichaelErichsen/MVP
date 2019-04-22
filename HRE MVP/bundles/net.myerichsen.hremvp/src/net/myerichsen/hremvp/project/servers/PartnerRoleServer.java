package net.myerichsen.hremvp.project.servers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.Languages;
import net.myerichsen.hremvp.dbmodels.PartnerRoles;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Events}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 22. apr. 2019
 *
 */
public class PartnerRoleServer implements IHREServer {
	// private static final Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int PartnerRolePid;
	private String abbreviation;

	private final PartnerRoles PartnerRole;

	/**
	 * Constructor
	 *
	 */
	public PartnerRoleServer() {
		PartnerRole = new PartnerRoles();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void delete(int key) throws Exception {
		PartnerRole.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return
	 * @throws Exception
	 */
	public List<PartnerRoles> get() throws Exception {
		return PartnerRole.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void get(int key) throws Exception {
		PartnerRole.get(key);
		setEventRolePid(PartnerRole.getPartnerRolePid());
		setAbbreviation(PartnerRole.getAbbreviation());
	}

	/**
	 * @return
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return the eventRolePid
	 */
	public int getEventRolePid() {
		return PartnerRolePid;
	}

	/**
	 * @return
	 */
	public int getLabelPid() {
		return PartnerRole.getLabelPid();
	}

	/**
	 * @return stringList A list of lists of event Role pids, label pids,
	 *         abbreviations, and generic labels
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		List<String> stringList;
		List<Dictionary> fkLabelPid;
		String label = "";

		final IPreferenceStore store = new ScopedPreferenceStore(
				InstanceScope.INSTANCE, "net.myerichsen.hremvp");
		final String guiLanguage = store.getString("GUILANGUAGE");
		final List<List<String>> lls = new ArrayList<>();
		final Dictionary dictionary = new Dictionary();

		final List<PartnerRoles> list = PartnerRole.get();

		for (final PartnerRoles st : list) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(st.getPartnerRolePid()));
			stringList.add(Integer.toString(st.getLabelPid()));
			stringList.add(st.getAbbreviation());

			fkLabelPid = dictionary.getFKLabelPid(st.getLabelPid());

			for (final Dictionary d : fkLabelPid) {
				if (guiLanguage.equals(d.getIsoCode())) {
					label = d.getLabel();
				}
			}

			stringList.add(label);
			lls.add(stringList);
		}
		return lls;
	}

	/**
	 * @param labelPid
	 * @param abbreviation
	 * @return stringList A list of lists of event Role pids, label pids, iso
	 *         codes and generic labels
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList(int labelPid) throws Exception {
		final List<List<String>> lls = new ArrayList<>();

		if (labelPid == 0) {
			return lls;
		}

		String eventRolePidString = "1";
		final List<PartnerRoles> list = PartnerRole.get();

		for (final PartnerRoles PartnerRoles : list) {
			if (PartnerRoles.getLabelPid() == labelPid) {
				eventRolePidString = Integer
						.toString(PartnerRoles.getPartnerRolePid());
			}
		}

		List<String> stringList;
		String label = "";

		final Dictionary dictionary = new Dictionary();
		final List<Dictionary> fkLabelPid = dictionary.getFKLabelPid(labelPid);

		final Languages language = new Languages();

		for (final Languages l : language.get()) {
			stringList = new ArrayList<>();
			stringList.add(eventRolePidString);
			stringList.add(Integer.toString(labelPid));
			stringList.add(l.getIsocode());

			for (final Dictionary d : fkLabelPid) {
				if (l.getIsocode().equals(d.getIsoCode())) {
					label = d.getLabel();
					break;
				}
			}

			stringList.add(label);
			lls.add(stringList);
		}
		return lls;
	}

	/**
	 * Insert a row
	 *
	 * @return int The persistent ID of the inserted row
	 *
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws Exception {
		PartnerRole.setAbbreviation(abbreviation);
		final Dictionary dictionary = new Dictionary();
		PartnerRole.setLabelPid(dictionary.getNextLabelPid());
		return PartnerRole.insert();
	}

	/**
	 * @param abbreviation the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * @param eventRolePid the eventRolePid to set
	 */
	public void setEventRolePid(int eventRolePid) {
		PartnerRolePid = eventRolePid;
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
	}

	/**
	 * Update a row
	 *
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws Exception {
		PartnerRole.setPartnerRolePid(PartnerRolePid);
		PartnerRole.update();
	}

}
