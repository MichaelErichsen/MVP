package net.myerichsen.hremvp.person.providers;

/**
 * A person displayed in a TreeViewer
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 13. feb. 2019
 *
 */
public class TreePerson {
	private int personPid;
	private int childPid;
	private int parentPid;
	private String Name;

	/**
	 * Constructor for ancestor tree
	 *
	 * @param sPersonPid
	 * @param parentpid
	 * @param sChildPid
	 * @param name
	 */
	public TreePerson(String sPersonPid, int parentpid, String sChildPid, String name) {
		super();
		this.personPid = Integer.parseInt(sPersonPid);
		this.parentPid = 0;
		this.childPid = Integer.parseInt(sChildPid);
		Name = name;
	}

	/**
	 * Constructor for descendant tree
	 *
	 * @param sPersonPid
	 * @param sParentpid
	 * @param childPid
	 * @param name
	 */
	public TreePerson(String sPersonPid, String sParentpid, int childPid, String name) {
		super();
		this.personPid = Integer.parseInt(sPersonPid);
		this.parentPid = Integer.parseInt(sParentpid);
		this.childPid = 0;
		Name = name;
	}

	/**
	 * @return the childPid
	 */
	public int getChildPid() {
		return childPid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @return the parentPid
	 */
	public int getParentPid() {
		return parentPid;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * @param childPid the childPid to set
	 */
	public void setChildPid(int childPid) {
		this.childPid = childPid;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @param parentPid the parentPid to set
	 */
	public void setParentPid(int parentPid) {
		this.parentPid = parentPid;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}
}
