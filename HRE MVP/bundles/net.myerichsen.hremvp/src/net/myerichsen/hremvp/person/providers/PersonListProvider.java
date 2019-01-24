package net.myerichsen.hremvp.person.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Persons;

/**
 * Provide a list of all persons.
 *
 * @version 2018-08-30
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 *
 */

public class PersonListProvider implements IHREProvider {
	private List<Persons> modelList;
	private Persons model;

	/**
	 * Constructor
	 *
	 */
	public PersonListProvider() {
		modelList = new ArrayList<>();
		try {
			modelList = new Persons().get();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a person
	 *
	 * @param personPid The persistent ID of the person
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	public void add(int personPid) throws SQLException {
		model = new Persons();
		model.setPersonPid(personPid);

		if (modelList == null) {
			modelList = new ArrayList<>();
		}
		modelList.add(model);
	}

	/**
	 * @return modelList A list of persons
	 */
	public List<Persons> getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<Persons> modelList) {
		this.modelList = modelList;
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		
	}
}
