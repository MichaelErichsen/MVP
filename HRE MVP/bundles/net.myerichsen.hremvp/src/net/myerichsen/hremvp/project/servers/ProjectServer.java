package net.myerichsen.hremvp.project.servers;

import java.util.ArrayList;
import java.util.List;
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
 * @version 2. feb. 2019
 *
 */
public class ProjectServer implements IHREServer {
	private static IPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger
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
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	public List<List<String>> get() throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		List<String> ls;
		final int projectCount = store.getInt("projectcount");
		String key;

		for (int i = 1; i <= projectCount; i++) {
			ls = new ArrayList<>();
			ls.add(Integer.toString(i));
			key = new String("project." + i + ".name");
			ls.add(store.getString(key));
			key = new String("project." + i + ".localserver");
			ls.add(store.getString(key));

			LOGGER.fine("Found Name: " + store.getString(key));
			lls.add(ls);
		}

		return lls;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws Exception {
		projectId = key;
		final String propKey = new String("project." + key + ".name");
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
				key = new String("project." + projectId + ".name");
				ls.add(store.getString(key));
				break;
			case 1:
				key = new String("project." + projectId + ".lastupdated");
				ls.add(store.getString(key));
				break;
			case 2:
				key = new String("project." + projectId + ".summary");
				ls.add(store.getString(key));
				break;
			case 3:
				key = new String("project." + projectId + ".localserver");
				ls.add(store.getString(key));
				break;
			case 4:
				key = new String("project." + projectId + ".path");
				ls.add(store.getString(key));
				break;
			default:
				LOGGER.severe("Invalid index: " + i);
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
		return null;
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