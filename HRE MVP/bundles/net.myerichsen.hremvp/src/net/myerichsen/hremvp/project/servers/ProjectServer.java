package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;

/**
 * Business logic interface for HRE Projects
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 27. jan. 2019
 *
 */
public class ProjectServer implements IHREServer {
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
//	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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

		for (int i = 0; i < projectCount; i++) {
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

}
