package net.myerichsen.hremvp;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

/**
 * Cell editor for the new sex type wizard
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 23. feb. 2019
 *
 */
public class HreTypeLabelEditingSupport extends EditingSupport {
	private final TableViewer viewer;
	private final CellEditor editor;
	private int column;

	/**
	 * Constructor
	 *
	 * @param viewer
	 */
	public HreTypeLabelEditingSupport(TableViewer viewer, int column) {
		super(viewer);
		this.viewer = viewer;
		this.column = column;
		editor = new TextCellEditor(viewer.getTable());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
	 */
	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
	 */
	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object getValue(Object element) {
		return ((List<String>) element).get(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object,
	 * java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void setValue(Object element, Object userInputValue) {
		((List<String>) element).set(column, String.valueOf(userInputValue));
		viewer.update(element, null);
	}
}
