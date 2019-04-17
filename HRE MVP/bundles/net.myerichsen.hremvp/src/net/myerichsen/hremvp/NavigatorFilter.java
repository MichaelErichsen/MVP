package net.myerichsen.hremvp;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter for navigator parts
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 15. apr. 2019
 */
public class NavigatorFilter extends ViewerFilter {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private String searchString;
	private final int stringPos;

	/**
	 * Constructor
	 *
	 * @param i
	 */
	public NavigatorFilter(int i) {
		stringPos = i;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.
	 * Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if ((searchString == null) || (searchString.length() == 0)) {
			return true;
		}

		@SuppressWarnings("unchecked")
		final List<String> ls = (List<String>) element;

		LOGGER.log(Level.FINE, "Filter string: {0}, Element: {1}",
				new Object[] { searchString, ls.get(1) });

		return (ls.get(stringPos).toLowerCase().matches(searchString));
	}

	/**
	 * @param s
	 */
	public void setSearchText(String s) {
		searchString = ".*" + s.toLowerCase() + ".*";
	}
}
