package net.myerichsen.gen.mvp.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.gen.mvp.dbmodels.Names;

/**
 * Provide a list of all names
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 28. sep. 2018
 *
 */
public class NameListProvider {
	private List<Names> modelList;
	private Names model;

	/**
	 * Constructor
	 *
	 */
	public NameListProvider() {
		modelList = new ArrayList<>();
		try {
			modelList = new Names().get();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param namePid Persistent ID of the Name
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors.
	 */
	public void add(int namePid) throws SQLException {
		model = new Names();
		model.setNamePid(namePid);

		if (modelList == null) {
			modelList = new ArrayList<>();
		}
		modelList.add(model);
	}

	/**
	 * @return modelList A list of all models
	 */
	public List<Names> getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<Names> modelList) {
		this.modelList = modelList;
	}

}
