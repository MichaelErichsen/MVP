package net.myerichsen.hremvp.person.providers;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 11. feb. 2019
 *
 */
public class DescendantTreeContentProvider implements ITreeContentProvider {

	/*
	 * This is the method invoked by calling the setInput method on the tree viewer.
	 * In fact, the getElements method is called only in response to the tree
	 * viewer's setInput method and should answer with the appropriate domain
	 * objects of the inputElement. The getElements and getChildren methods operate
	 * in a similar way. Depending on your domain objects, you may have the
	 * getElements simply return the result of calling getChildren. The two methods
	 * are kept distinct because it provides a clean way to differentiate between
	 * the root domain object and all other domain objects.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return ArrayContentProvider.getInstance().getElements(inputElement);
	}

	/*
	 * The tree viewer calls its content provider’s getChildren method when it needs
	 * to create or display the child elements of the domain object, parent. This
	 * method should answer an array of domain objects that represent the unfiltered
	 * children of parent (more on filtering later).
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
//		List<String> ls = (List<String>) parentElement;
//		if (ls.get(1).equals("0")) {
//			return null;
//		}
		// TODO Auto-generated method stub
		// Find each child element
		return null;
	}

	/*
	 * The tree viewer calls its content provider’s getParent method when it needs
	 * to reveal collapsed domain objects programmatically and to set the expanded
	 * state of domain objects. This method should answer the parent of the domain
	 * object element.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		List<String> ls = (List<String>) element;
		// TODO Auto-generated method stub
		// Get parent element
		return null;
	}

	/*
	 * The tree viewer asks its content provider if the domain object represented by
	 * element has any children. This method is used by the tree viewer to determine
	 * whether or not a plus or minus should appear on the tree widget.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		List<String> ls = (List<String>) element;
		if (ls.get(1).equals("0"))
			return false;
		return true;
	}

}
