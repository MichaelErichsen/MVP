package net.myerichsen.hremvp.providers;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;

/**
 * Default JFace table label provider
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 10. feb. 2019
 *
 */
public class HREColumnLabelProvider extends ColumnLabelProvider {
	private int column;

	/**
	 * Constructor
	 *
	 * @param column
	 */
	public HREColumnLabelProvider(int column) {
		super();
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		@SuppressWarnings("unchecked")
		final List<String> list = (List<String>) element;
		return list.get(column);
	}
}