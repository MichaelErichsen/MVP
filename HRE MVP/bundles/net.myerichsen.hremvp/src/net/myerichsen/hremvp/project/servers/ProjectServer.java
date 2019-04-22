package net.myerichsen.hremvp.project.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;

/**
 * Business logic interface for HRE Projects
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 22. apr. 2019
 *
 */
public class ProjectServer implements IHREServer {
	private static final String NAME = ".name";
	private static final String PROJECT = "project.";
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int projectId = 0;
	private String projectName = "";

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws Exception {
		projectId = key;
		final String propKey = PROJECT + key + NAME;
		projectName = store.getString(propKey);
	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 * @throws MvpException
	 */
	public List<String> getElement(int key) throws Exception {
		get(key);
		final List<String> element = new ArrayList<>();
		element.add(Integer.toString(projectId));
		element.add(projectName);
		return element;
	}

	/**
	 * @param projectId
	 * @return lls Project properties for a single project
	 */
	public List<List<String>> getProperties(int projectId) {
		String key;
		List<String> ls;

		final List<List<String>> lls = new ArrayList<>();
		final String[] labelArray = { "Project Name", "Last Edited", "Summary",
				"Local/Server", "Path" };

		for (int i = 0; i < labelArray.length; i++) {
			ls = new ArrayList<>();

			ls.add(labelArray[i]);

			switch (i) {
			case 0:
				key = PROJECT + projectId + NAME;
				ls.add(store.getString(key));
				break;
			case 1:
				key = PROJECT + projectId + ".lastupdated";
				ls.add(store.getString(key));
				break;
			case 2:
				key = PROJECT + projectId + ".summary";
				ls.add(store.getString(key));
				break;
			case 3:
				key = PROJECT + projectId + ".localserver";
				ls.add(store.getString(key));
				break;
			case 4:
				key = PROJECT + projectId + ".path";
				ls.add(store.getString(key));
				break;
			default:
				LOGGER.log(Level.SEVERE, "Invalid index: {0}", i);
				break;
			}

			lls.add(ls);
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
		final List<List<String>> lls = new ArrayList<>();
		List<String> ls;
		final int projectCount = store.getInt("projectcount");
		String key;

		for (int i = 1; i <= projectCount; i++) {
			ls = new ArrayList<>();
			ls.add(Integer.toString(i));
			key = PROJECT + i + NAME;
			ls.add(store.getString(key));
			key = PROJECT + i + ".localserver";
			ls.add(store.getString(key));

			LOGGER.log(Level.FINE, "Found Name: " + store.getString(key));
			lls.add(ls);
		}

		return lls;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws Exception {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws Exception {

	}
}