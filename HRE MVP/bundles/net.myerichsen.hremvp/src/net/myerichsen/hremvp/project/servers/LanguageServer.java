package net.myerichsen.hremvp.project.servers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Languages;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Languages}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 22. apr. 2019
 *
 */
public class LanguageServer implements IHREServer {
	private int LanguagePid;
	private String Isocode;
	private String Label;
	private int TableId;
	private final Languages language;

	/**
	 * Constructor
	 *
	 */
	public LanguageServer() {
		language = new Languages();
	}

	/**
	 * @param key
	 * @throws MvpException
	 * @throws Exception
	 */
	@Override
	public void delete(int key) throws Exception {
		language.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#deleteRemote(java.lang.String)
	 */
	@Override
	public void deleteRemote(String target) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws Exception {
		language.get();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getRemote(javax.servlet.http.
	 * HttpServletResponse, java.lang.String)
	 */
	@Override
	public String getRemote(HttpServletResponse response, String target)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;
		final List<Languages> list = language.get();

		for (final Languages l : list) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(l.getLanguagePid()));
			stringList.add(l.getIsocode());
			stringList.add(l.getLabel());
			lls.add(stringList);
		}

		return lls;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return TableId;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert() throws Exception {
		language.setIsocode(Isocode);
		language.setLabel(Label);
		language.setTableId(7);
		return language.insert();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#insertRemote(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public void insertRemote(HttpServletRequest request) {
		// TODO Auto-generated method stub

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
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws Exception {
		language.setLanguagePid(LanguagePid);
		language.setIsocode(Isocode);
		language.setLabel(Label);
		language.update();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#updateRemote(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public void updateRemote(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
