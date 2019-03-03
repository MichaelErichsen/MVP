package net.myerichsen.hremvp.project.providers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.project.servers.LanguageServer;

/**
 * Provide a language
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. mar. 2019
 *
 */
public class LanguageProvider implements IHREProvider {
	private int LanguagePid;
	private String Isocode;
	private String Label;
	private int TableId;
	private final LanguageServer server;

	/**
	 * Constructor
	 *
	 */
	public LanguageProvider() {
		server = new LanguageServer();
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

	/**
	 * @return A list of lists of Pids, ISO Codes and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public List<List<String>> get() throws SQLException, MvpException {
		return server.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		server.get();
	}

	/**
	 * @return the isocode
	 */
	public String getIsocode() {
		return Isocode;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return LanguagePid;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> getStringList() throws SQLException {
		return server.getStringList();
	}

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return TableId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		server.setIsocode(Isocode);
		server.setLabel(Label);
		return server.insert();
	}

	/**
	 * @param isocode the isocode to set
	 */
	public void setIsocode(String isocode) {
		Isocode = isocode;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		Label = label;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		LanguagePid = languagePid;
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(int tableId) {
		TableId = tableId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		server.setLanguagePid(LanguagePid);
		server.setIsocode(Isocode);
		server.setLabel(Label);
		server.update();
	}

}
