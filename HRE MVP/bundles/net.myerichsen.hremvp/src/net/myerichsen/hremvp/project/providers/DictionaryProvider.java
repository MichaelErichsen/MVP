package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.DictionaryServer;

/**
 * Provide a dictionary row
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 3. mar. 20199
 *
 */
public class DictionaryProvider implements IHREProvider {
	private int DictionaryPid;
	private int LabelPid;
	private String IsoCode;
	private String Label;
	private String LabelType;

	private final DictionaryServer server;

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
		server.delete(key);
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
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get(key);
		setIsoCode(server.getIsoCode());
		setLabel(server.getLabel());
		setLabelPid(server.getLabelPid());
		setLabelType(server.getLabelType());
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
	 * @throws SQLException
	 *
	 */
	public int getNextLabelPid() throws SQLException {
		return server.getNextLabelPid();
	}

	/**
	 * @param labelPid
	 * @return
	 * @throws SQLException
	 * @throws MvpException
	 */
	@Override
	public List<List<String>> getStringList(int labelPid)
			throws SQLException, MvpException {
		return server.getStringList(labelPid);

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
		server.setLabelType(LabelType);
		return server.insert();
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
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setDictionaryPid(DictionaryPid);
		server.setIsoCode(IsoCode);
		server.setLabel(Label);
		server.setLabelPid(LabelPid);
		server.setLabelType(LabelType);
		server.update();
	}

}
