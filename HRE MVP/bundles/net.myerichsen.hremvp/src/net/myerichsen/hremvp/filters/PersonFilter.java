package net.myerichsen.hremvp.filters;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 26. jan. 2019
 */
public class PersonFilter extends ViewerFilter {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private String searchString;

	/**
	 * @param s
	 */
	public void setSearchText(String s) {
		this.searchString = ".*" + s.toLowerCase() + ".*";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.
	 * Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}

		@SuppressWarnings("unchecked")
		List<String> ls = (List<String>) element;

		LOGGER.info("Filter string: " + searchString + ", Element: " + ls.get(1));

		if (ls.get(1).toLowerCase().matches(searchString)) {
			return true;
		}

		return false;
	}
}
