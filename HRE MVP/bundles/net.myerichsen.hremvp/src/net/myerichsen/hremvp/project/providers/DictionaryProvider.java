package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.DictionaryServer;

/**
 * Provide a dictionary entry
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 22. feb. 2019
 *
 */
public class DictionaryProvider implements IHREProvider {
	private int DictionaryPid;
	private int LabelPid;
	private String IsoCode;
	private String Label;

	private DictionaryServer server;

	/**
	 * Constructor
	 *
	 */
	public DictionaryProvider() {
		server = new DictionaryServer();
	}

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
		return server.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	public List<List<String>> getStringList(int key)
			throws SQLException, MvpException {
		return server.getStringList(key);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		server.setIsoCode(IsoCode);
		server.setLabel(Label);
		server.setLabelPid(LabelPid);
		return server.insert();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setDictionaryPid(DictionaryPid);
		server.setIsoCode(IsoCode);
		server.setLabel(Label);
		server.setLabelPid(LabelPid);
		server.update();
	}

	/**
	 * @return the dictionaryPid
	 */
	public int getDictionaryPid() {
		return DictionaryPid;
	}

	/**
	 * @param dictionaryPid the dictionaryPid to set
	 */
	public void setDictionaryPid(int dictionaryPid) {
		DictionaryPid = dictionaryPid;
	}

	/**
	 * @return the labelPid
	 */
	public int getLabelPid() {
		return LabelPid;
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
		LabelPid = labelPid;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return IsoCode;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		IsoCode = isoCode;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		Label = label;
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

}
