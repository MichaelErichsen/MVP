package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.Dictionary}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 23. feb. 2019
 *
 */
public class DictionaryServer implements IHREServer {
	private int DictionaryPid;
	private int LabelPid;
	private String IsoCode;
	private String Label;
	private final int TableId = 6;

	private final Dictionary dictionary;

	/**
	 * Constructor
	 *
	 */
	public DictionaryServer() {
		dictionary = new Dictionary();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		dictionary.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		dictionary.get();
	}

	/**
	 * @return the dictionaryPid
	 */
	public int getDictionaryPid() {
		return DictionaryPid;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return IsoCode;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * @return the labelPid
	 */
	public int getLabelPid() {
		return LabelPid;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getNextLabelPid() throws SQLException {
		return dictionary.getNextLabelPid();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	public List<List<String>> getStringList(int key)
			throws SQLException, MvpException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		if (key > 0) {
			final List<Dictionary> fkLabelPid = dictionary.getFKLabelPid(key);
			for (final Dictionary d : fkLabelPid) {
				stringList = new ArrayList<>();
				stringList.add(d.getIsoCode());
				stringList.add(d.getLabel());
				stringList.add(Integer.toString(d.getDictionaryPid()));
				lls.add(stringList);
			}
		}
		return lls;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		dictionary.setIsoCode(IsoCode);
		dictionary.setLabel(Label);
		dictionary.setLabelPid(LabelPid);
		dictionary.setTableId(TableId);
		return dictionary.insert();
	}

	/**
	 * @param dictionaryPid the dictionaryPid to set
	 */
	public void setDictionaryPid(int dictionaryPid) {
		DictionaryPid = dictionaryPid;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		IsoCode = isoCode;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		Label = label;
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
		LabelPid = labelPid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		dictionary.setDictionaryPid(DictionaryPid);
		dictionary.setIsoCode(IsoCode);
		dictionary.setLabel(Label);
		dictionary.setLabelPid(LabelPid);
		dictionary.update();
	}

}
