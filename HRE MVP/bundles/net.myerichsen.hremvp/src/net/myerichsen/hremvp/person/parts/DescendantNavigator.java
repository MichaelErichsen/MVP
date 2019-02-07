package net.myerichsen.hremvp.person.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 10. jan. 2019
 *
 */
public class DescendantNavigator {

	public DescendantNavigator() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		TreeViewer treeViewer = new TreeViewer(parent, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

}
