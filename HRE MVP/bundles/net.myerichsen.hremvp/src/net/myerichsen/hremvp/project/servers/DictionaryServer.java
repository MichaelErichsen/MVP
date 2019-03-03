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
 * @version 3. mar. 2019
 *
 */
public class DictionaryServer implements IHREServer {
	private int DictionaryPid;
	private int LabelPid;
	private String IsoCode;
	private String Label;
	private final int TableId = 6;
	private String LabelType;;

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
		dictionary.get(key);
		setIsoCode(dictionary.getIsoCode());
		setLabel(dictionary.getLabel());
		setLabelPid(dictionary.getLabelPid());
		setLabelType(dictionary.getLabelType());
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
	 * @return the labelType
	 */
	public String getLabelType() {
		return LabelType;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int getNextLabelPid() throws SQLException {
		return dictionary.getNextLabelPid();
	}

	/**
	 * @param labelPid
	 * @return List A list of lists of iso code, label text and dictionary pid
	 * @throws SQLException
	 * @throws MvpException
	 */
	public List<List<String>> getStringList(int labelPid)
			throws SQLException, MvpException {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		if (labelPid > 0) {
			final List<Dictionary> fkLabelPid = dictionary
					.getFKLabelPid(labelPid);
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
		dictionary.setLabelType(LabelType);
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

	/**
	 * @param labelType the labelType to set
	 */
	public void setLabelType(String labelType) {
		LabelType = labelType;
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
		dictionary.setLabelType(LabelType);
		dictionary.update();
	}

}
