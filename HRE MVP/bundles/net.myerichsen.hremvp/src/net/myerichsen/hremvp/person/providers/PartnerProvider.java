package net.myerichsen.hremvp.person.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.person.servers.PartnerServer;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 24. jan. 2019
 *
 */
public class PartnerProvider implements IHREProvider {
	PartnerServer server;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}
}
