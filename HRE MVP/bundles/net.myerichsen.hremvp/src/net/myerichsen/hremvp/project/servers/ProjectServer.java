package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
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
 * @version 28. jan. 2019
 *
 */
public class ProjectServer implements IHREServer {
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	@Override
	public List<List<String>> get() throws SQLException, MvpException {
		List<List<String>> lls = new ArrayList<List<String>>();
		List<String> ls = new ArrayList<String>();
		final int projectCount = store.getInt("projectcount");
		String key;

		for (int i = 1; i <= projectCount; i++) {
			ls.add(Integer.toString(i));
			key = new String("project." + i + ".name");
			ls.add(store.getString(key));
		}

		lls.add(ls);

		return lls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param projectId
	 * @return lls Project properties for a single project
	 */
	public List<List<String>> getProperties(int projectId) {
		String key;
		List<String> ls;

		List<List<String>> lls = new ArrayList<>();
		String[] labelArray = { "Project Name", "Last Edited", "Summary", "Local/Server", "Path" };

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
}