package net.myerichsen.hremvp.person.providers;

import java.util.List;

import net.myerichsen.hremvp.IHREProvider;
import net.myerichsen.hremvp.person.servers.ParentServer;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 25. jan. 2019
 */
public class ParentProvider implements IHREProvider {
	private int ParentPid;
	private int Child;
	private int Parent;
	private String ParentRole;
	private boolean PrimaryParent;
	private int LanguagePid;

	private final ParentServer server;

	/**
	 * Constructor
	 *
	 * @param server
	 */
	public ParentProvider() {
		server = new ParentServer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#delete(int)
	 */
	@Override
	public void delete(int key) throws Exception {
		server.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#get()
	 */
	public List<?> get() throws Exception {
		return server.get();
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
	 * @return the child
	 */
	public int getChild() {
		return Child;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return LanguagePid;
	}

	/**
	 * @return the parent
	 */
	public int getParent() {
		return Parent;
	}

	/**
	 * @return the parentPid
	 */
	public int getParentPid() {
		return ParentPid;
	}

	/**
	 * @return the parentRole
	 */
	public String getParentRole() {
		return ParentRole;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREProvider#insert()
	 */
	@Override
	public int insert() throws Exception {
		server.setChild(Child);
		server.setParent(Parent);
		server.setParentRole(ParentRole);
		server.setPrimaryParent(PrimaryParent);
		server.setLanguagePid(LanguagePid);
		return server.insert();
	}

	/**
	 * @return the primaryParent
	 */
	public boolean isPrimaryParent() {
		return PrimaryParent;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(int child) {
		Child = child;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		LanguagePid = languagePid;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(int parent) {
		Parent = parent;
	}

	/**
	 * @param parentPid the parentPid to set
	 */
	public void setParentPid(int parentPid) {
		ParentPid = parentPid;
	}

	/**
	 * @param parentRole the parentRole to set
	 */
	public void setParentRole(String parentRole) {
		ParentRole = parentRole;
	}

	/**
	 * @param primaryParent the primaryParent to set
	 */
	public void setPrimaryParent(boolean primaryParent) {
		PrimaryParent = primaryParent;
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
