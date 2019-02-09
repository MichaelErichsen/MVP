package net.myerichsen.hremvp.person.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import net.myerichsen.hremvp.person.providers.AncestorTreeContentProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 10. jan. 2019
 *
 */
public class AncestorNavigator {
	public AncestorNavigator() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		final TreeViewer treeViewer = new TreeViewer(parent, SWT.BORDER);
		treeViewer.setContentProvider(new AncestorTreeContentProvider());
		final Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn trclmnAncestors = treeViewerColumn.getColumn();
		trclmnAncestors.setWidth(100);
		trclmnAncestors.setText("Ancestors");
		treeViewerColumn.setLabelProvider(new ColumnLabelProvider());

		treeViewer.setInput(new String[] { "Simon Scholz", "Lars Vogel", "Dirk Fauth", "Wim Jongman", "Tom Schindl" });
		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

}
