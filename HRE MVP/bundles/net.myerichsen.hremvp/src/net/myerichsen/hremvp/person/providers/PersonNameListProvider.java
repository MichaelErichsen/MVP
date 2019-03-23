package net.myerichsen.hremvp.person.providers;

import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.dbmodels.PersonNames;

/**
 * Provide a list of all names
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. feb. 2019
 *
 */
public class PersonNameListProvider implements IHREProvider {
	private List<PersonNames> modelList;
	private PersonNames model;

	/**
	 * Constructor
	 *
	 */
	public PersonNameListProvider() {
		modelList = new ArrayList<>();
		try {
			modelList = new PersonNames().get();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param namePid Persistent ID of the Name
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors.
	 */
	public void add(int namePid) throws Exception {
		model = new PersonNames();
		model.setNamePid(namePid);

		if (modelList == null) {
			modelList = new ArrayList<>();
		}
		modelList.add(model);
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
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws Exception {

	}

	/**
	 * @return modelList A list of all models
	 */
	public List<PersonNames> getModelList() {
		return modelList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
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

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<PersonNames> modelList) {
		this.modelList = modelList;
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
