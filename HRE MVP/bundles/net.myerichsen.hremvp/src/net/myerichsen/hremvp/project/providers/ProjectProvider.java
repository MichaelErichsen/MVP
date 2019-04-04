package net.myerichsen.hremvp.project.providers;

import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.ProjectServer;

/**
 * Provides all registered projects
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 11. mar. 2019
 *
 */
public class ProjectProvider implements IHREProvider {
//	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
	public void delete(int key) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	public List<List<String>> get() throws Exception {
		return server.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws Exception {

	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 * @throws MvpException
	 */
	public List<String> getElement(int key) throws Exception {
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
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		return server.getStringList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws Exception {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws Exception {

	}

}
