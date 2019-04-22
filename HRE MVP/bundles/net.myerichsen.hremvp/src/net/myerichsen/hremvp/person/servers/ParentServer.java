package net.myerichsen.hremvp.person.servers;

import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.dbmodels.Parents;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 22. apr. 2019
 *
 */
public class ParentServer implements IHREServer {
	private int ParentPid;
	private int Child;
	private int Parent;
	private int ParentRolePid;
	private boolean PrimaryParent;
	private int LanguagePid;

	private final Parents parentRelation;

	/**
	 * Constructor
	 *
	 */
	public ParentServer() {
		parentRelation = new Parents();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#get(int)
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
	 * @return the parentRolePid
	 */
	public int getParentRolePid() {
		return ParentRolePid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#insert()
	 */
	@Override
	public int insert() throws Exception {
		parentRelation.setChild(Child);
		parentRelation.setParent(Parent);
		parentRelation.setParentRolePid(getParentRolePid());
		parentRelation.setPrimaryParent(PrimaryParent);
		parentRelation.setLanguagePid(LanguagePid);
		return parentRelation.insert();
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
	 * @param parentRolePid the parentRolePid to set
	 */
	public void setParentRolePid(int parentRolePid) {
		ParentRolePid = parentRolePid;
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
	 * @see net.myerichsen.hremvp.servers.IHREServer#update()
	 */
	@Override
	public void update() throws Exception {

	}
}
