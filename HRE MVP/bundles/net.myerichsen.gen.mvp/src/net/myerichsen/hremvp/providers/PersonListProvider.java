package net.myerichsen.hremvp.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.dbmodels.Persons;

/**
 * Provide a list of all persons.
 *
 * @version 2018-08-30
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 *
 */

public class PersonListProvider {
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
}
