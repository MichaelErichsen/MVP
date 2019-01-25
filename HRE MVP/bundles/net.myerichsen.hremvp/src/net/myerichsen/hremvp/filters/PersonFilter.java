package net.myerichsen.hremvp.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 25. jan. 2019
 *
 */
public class PersonFilter extends ViewerFilter {

	private String searchString;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.
	 * Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if ((searchString == null) || (searchString.length() == 0)) {
			return true;
		}
//	    Person p = (Person) element;
//	    if (p.getFirstName().matches(searchString)) {
//	        return true;
//	    }
//	    if (p.getLastName().matches(searchString)) {
//	        return true;
//	    }

		return false;
	}

	/**
	 * @param s
	 */
	public void setSearchText(String s) {
		// ensure that the value can be used for matching
		searchString = ".*" + s + ".*";
	}
}
