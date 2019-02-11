package net.myerichsen.hremvp.person.parts;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.person.providers.AncestorTreeContentProvider;
import net.myerichsen.hremvp.person.providers.PersonProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 11. jan. 2019
 *
 */
public class AncestorNavigator {
	private final IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private PersonProvider provider;
	private int personId = 1;
	private Spinner spinnerGenerations;
	private TreeViewer treeViewer;
	private TreeViewerColumn treeViewerColumn;

	/**
	 * Constructor
	 *
	 */
	public AncestorNavigator() {
		try {
			provider = new PersonProvider();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Label lblGenerations = new Label(parent, SWT.NONE);
		lblGenerations.setText("Generations");

		spinnerGenerations = new Spinner(parent, SWT.BORDER);
		spinnerGenerations.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				store.setValue("TREEGENERATIONS", spinnerGenerations.getSelection());
			}
		});
		spinnerGenerations.setSelection(store.getInt("TREEGENERATIONS"));

		treeViewer = new TreeViewer(parent, SWT.BORDER);
		treeViewer.setContentProvider(new AncestorTreeContentProvider());

		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn trclmnAncestors = treeViewerColumn.getColumn();
		trclmnAncestors.setWidth(444);
		try {
			treeViewerColumn.setLabelProvider(new ColumnLabelProvider());
			treeViewer.setInput(provider.getAncestorList(personId, spinnerGenerations.getSelection()));
		} catch (SQLException | MvpException e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}
		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribePersonPidUpdateTopic(@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int personPid) {
		LOGGER.fine("Received person id " + personPid);
		try {
			treeViewer.setInput(provider.getAncestorList(personPid, spinnerGenerations.getSelection()));
			treeViewer.refresh();
		} catch (SQLException | MvpException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
