package net.myerichsen.hremvp.person.parts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Tree;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.person.providers.DescendantTreeContentProvider;
import net.myerichsen.hremvp.person.providers.DescendantTreeLabelProvider;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.person.providers.TreePerson;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 13. feb. 2019
 *
 */
public class DescendantNavigator {
	private final IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private PersonProvider provider;
	private int personPid = 2;
	private Spinner spinnerGenerations;
	private TreeViewer treeViewer;
	private List<TreePerson> treePersonList;

	/**
	 * Constructor
	 *
	 */
	public DescendantNavigator() {
		try {
			provider = new PersonProvider();
			treePersonList = new ArrayList<TreePerson>();
			TreePerson treePerson;

			for (List<String> list : provider.getDescendantList(personPid, 99999)) {
				treePerson = new TreePerson(list.get(0), list.get(1), 0, list.get(2));
				treePersonList.add(treePerson);
			}
		} catch (SQLException | MvpException e) {
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
		parent.setLayout(new GridLayout(2, false));

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
		treeViewer.setContentProvider(new DescendantTreeContentProvider(treePersonList));
		treeViewer.setLabelProvider(new DescendantTreeLabelProvider());

		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		treeViewer.setInput(treePersonList);
	}

	/**
	 * 
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * 
	 */
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
		treeViewer.setInput(treePersonList);
		treeViewer.refresh();
	}
}
