package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.ProjectServer;

/**
 * Provides all registered projects
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 2. feb. 2019
 *
 */
public class ProjectProvider implements IHREProvider {
//	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	ProjectServer server;

	/**
	 * Constructor
	 *
	 */
	public ProjectProvider() {
		server = new ProjectServer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	@Override
	public List<List<String>> get() throws SQLException, MvpException {
		return server.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {

	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 * @throws MvpException
	 */
	public List<String> getElement(int key) throws SQLException, MvpException {
		return server.getElement(key);
	}

	/**
	 * @return
	 */
	public List<List<String>> getProperties(int projectId) {
		return server.getProperties(projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {

	}

}
