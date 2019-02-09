package net.myerichsen.hremvp.person.parts;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import net.myerichsen.hremvp.person.providers.DescendantTreeContentProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 9. feb. 2019
 *
 */
public class DescendantNavigator {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Constructor
	 *
	 */
	public DescendantNavigator() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setLayout(new GridLayout(2, false));

//		Label lblNewLabel = new Label(composite, SWT.NONE);
//		lblNewLabel.setText("Generations");
//
//		Spinner spinnerGenerations = new Spinner(composite, SWT.BORDER);
//		spinnerGenerations.setData("DESCENDANT_GENERATIONS", 5);
//		LOGGER.info("Generations: " + spinnerGenerations.getText());

		TreeViewer treeViewer = new TreeViewer(composite, SWT.BORDER);
		treeViewer.setContentProvider(new DescendantTreeContentProvider());
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
//		tree.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				TreeItem item = (TreeItem) e.item;
//				if (item.getItemCount() > 0) {
//					item.setExpanded(!item.getExpanded());
//					// update the viewer
//					treeViewer.refresh();
//				}
//			}
//		});

		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn trclmnDescendants = treeViewerColumn.getColumn();
		trclmnDescendants.setWidth(100);
		trclmnDescendants.setText("Descendants");

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
